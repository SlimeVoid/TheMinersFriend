package slimevoid.tmf.machines;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import slimevoid.tmf.core.TMFCore;
import slimevoid.tmf.items.ItemMineral;
import slimevoid.tmf.items.ItemMineralDust;

import argo.jdom.JdomParser;
import argo.jdom.JsonNode;
import argo.jdom.JsonRootNode;
import argo.jdom.JsonStringNode;
import argo.saj.InvalidSyntaxException;

public class JSONGrinderRecipesLoader {
	private static final JdomParser parser = new JdomParser();
	private static Map<String,ItemMineral> mineralMap = new HashMap<String,ItemMineral>();
	private static Map<String,ItemMineralDust> dustMap = new HashMap<String,ItemMineralDust>();
	
	static {
		mineralMap.put("acxium",  (ItemMineral) TMFCore.mineralAcxium);
		mineralMap.put("bisogen",  (ItemMineral) TMFCore.mineralBisogen);
		mineralMap.put("cydrine",  (ItemMineral) TMFCore.mineralCydrine);
		
		dustMap.put("acxium",  (ItemMineralDust) TMFCore.dustAcxium);
		dustMap.put("bisogen",  (ItemMineralDust) TMFCore.dustBisogen);
		dustMap.put("cydrine",  (ItemMineralDust) TMFCore.dustCydrine);
	}
	
	public static void loadFile(File json) {
		if ( !json.exists() || !json.isFile() || !json.canRead() )
			return;
		
		try {
			parseJSON(
					readFile(json)
			);
		} catch (InvalidSyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static String readFile(File file) throws IOException {
		BufferedReader reader = new BufferedReader( new FileReader (file));
		String         line = null;
		StringBuilder  stringBuilder = new StringBuilder();
		String         ls = System.getProperty("line.separator");
		
		while( ( line = reader.readLine() ) != null ) {
			stringBuilder.append( line );
			stringBuilder.append( ls );
		}
		
		return stringBuilder.toString();
	}
	
	private static void parseJSON(String json) throws InvalidSyntaxException {
		JsonRootNode root = parser.parse(json);

		for ( String mineral: mineralMap.keySet() ) {
			if ( root.isObjectNode(mineral) ) {
				for ( JsonStringNode mineralNode: root.getObjectNode(mineral).keySet() ) {
					String dust = mineralNode.getStringValue();
					if ( dustMap.containsKey(dust) ) {
						JsonNode node = root.getObjectNode(mineral).get(mineralNode);
						
						int min = -1;
						int max = -1;
						
						if ( node.isNumberValue(0) )
							min = Integer.parseInt(node.getNumberValue(0));
						if ( node.isNumberValue(1) )
							max = Integer.parseInt(node.getNumberValue(1));
						
						if ( min < 0 && max >= 0 )
							min = max;
						if ( max < 0 && min >= 0 )
							max = min;
						
						registerRecipe(mineral,min,max,dust);
					}
				}
			}
		}
	}
	
	private static void registerRecipe(String mineral, int min, int max, String dust) {
		GrinderRecipes.grinding().addRefinement(
				strToMineral(mineral),
				min, 
				max,
				strToDust(dust)
		);
	}
	private static ItemMineralDust strToDust(String dust) {
		return dustMap.get(dust);
	}
	private static ItemMineral strToMineral(String mineral) {
		return mineralMap.get(mineral);
	}
}
