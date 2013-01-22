package slimevoid.tmf.blocks.machines;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import slimevoid.tmf.blocks.ores.BlockTMFOre;
import slimevoid.tmf.core.TMFCore;
import slimevoid.tmf.items.ItemMineral;

import argo.jdom.JdomParser;
import argo.jdom.JsonNode;
import argo.jdom.JsonRootNode;
import argo.jdom.JsonStringNode;
import argo.saj.InvalidSyntaxException;

public class JSONRefineryRecipesLoader {
	private static final JdomParser parser = new JdomParser();
	private static Map<String,BlockTMFOre> oreMap = new HashMap<String,BlockTMFOre>();
	private static Map<String,ItemMineral> mineralMap = new HashMap<String,ItemMineral>();
	
	static {
		oreMap.put("arkite", (BlockTMFOre) TMFCore.arkiteOre);
		oreMap.put("bistite", (BlockTMFOre) TMFCore.bistiteOre);
		oreMap.put("crokere", (BlockTMFOre) TMFCore.crokereOre);
		oreMap.put("dernite", (BlockTMFOre) TMFCore.derniteOre);
		oreMap.put("egioclase", (BlockTMFOre) TMFCore.egioclaseOre);

		mineralMap.put("acxium",  (ItemMineral) TMFCore.mineralAcxium);
		mineralMap.put("bisogen",  (ItemMineral) TMFCore.mineralBisogen);
		mineralMap.put("cydrine",  (ItemMineral) TMFCore.mineralCydrine);
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

		for ( String ore: oreMap.keySet() ) {
			if ( root.isObjectNode(ore) ) {
				for ( JsonStringNode mineralNode: root.getObjectNode(ore).keySet() ) {
					String mineral = mineralNode.getStringValue();
					if ( mineralMap.containsKey(mineral) ) {
						JsonNode node = root.getObjectNode(ore).get(mineralNode);
						
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
						
						registerRecipe(ore,min,max,mineral);
					}
				}
			}
		}
	}
	
	private static void registerRecipe(String ore, int min, int max, String mineral) {
		RefineryRecipes.refining().addRefinement(
				strToOre(ore),
				min, 
				max,
				strToMineral(mineral)
		);
	}
	private static BlockTMFOre strToOre(String ore) {
		return oreMap.get(ore);
	}
	private static ItemMineral strToMineral(String mineral) {
		return mineralMap.get(mineral);
	}
}
