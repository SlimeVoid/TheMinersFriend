/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version. This program is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details. You should have received a copy of the GNU
 * Lesser General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>
 */
package slimevoid.tmf.blocks.machines.recipes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import slimevoid.tmf.core.TMFCore;
import slimevoid.tmf.core.lib.EnumBlocks;
import slimevoid.tmf.items.minerals.ItemMineral;
import argo.jdom.JdomParser;
import argo.jdom.JsonNode;
import argo.jdom.JsonRootNode;
import argo.jdom.JsonStringNode;
import argo.saj.InvalidSyntaxException;

public class JSONRefineryRecipesLoader {
	private static final JdomParser			parser		= new JdomParser();
	private static Map<String, Integer>		oreMap		= new HashMap<String, Integer>();
	private static Map<String, ItemMineral>	mineralMap	= new HashMap<String, ItemMineral>();

	static {
		oreMap.put(	"arkite",
					EnumBlocks.ORE_ARKITE.getId());
		oreMap.put(	"bistite",
					EnumBlocks.ORE_BISTITE.getId());
		oreMap.put(	"crokere",
					EnumBlocks.ORE_CROKERE.getId());
		oreMap.put(	"dernite",
					EnumBlocks.ORE_DERNITE.getId());
		oreMap.put(	"egioclase",
					EnumBlocks.ORE_EGIOCLASE.getId());

		mineralMap.put(	"acxium",
						(ItemMineral) TMFCore.mineralAcxium);
		mineralMap.put(	"bisogen",
						(ItemMineral) TMFCore.mineralBisogen);
		mineralMap.put(	"cydrine",
						(ItemMineral) TMFCore.mineralCydrine);
	}

	public static void loadFile(File json) {
		if (!json.exists() || !json.isFile() || !json.canRead()) return;

		try {
			parseJSON(readFile(json));
		} catch (InvalidSyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String readFile(File file) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = null;
		StringBuilder stringBuilder = new StringBuilder();
		String ls = System.getProperty("line.separator");

		while ((line = reader.readLine()) != null) {
			stringBuilder.append(line);
			stringBuilder.append(ls);
		}

		return stringBuilder.toString();
	}

	private static void parseJSON(String json) throws InvalidSyntaxException {
		JsonRootNode root = parser.parse(json);

		for (String ore : oreMap.keySet()) {
			if (root.isObjectNode(ore)) {
				for (JsonStringNode mineralNode : root.getObjectNode(ore).keySet()) {
					String mineral = mineralNode.getStringValue();
					if (mineralMap.containsKey(mineral)) {
						JsonNode node = root.getObjectNode(ore).get(mineralNode);

						int min = -1;
						int max = -1;

						if (node.isNumberValue(0)) min = Integer.parseInt(node.getNumberValue(0));
						if (node.isNumberValue(1)) max = Integer.parseInt(node.getNumberValue(1));

						if (min < 0 && max >= 0) min = max;
						if (max < 0 && min >= 0) max = min;

						registerRecipe(	ore,
										min,
										max,
										mineral);
					}
				}
			}
		}
	}

	private static void registerRecipe(String ore, int min, int max, String mineral) {
		RefineryRecipes.refining().addRefinement(	strToOre(ore),
													min,
													max,
													strToMineral(mineral));
	}

	private static int strToOre(String ore) {
		return oreMap.get(ore);
	}

	private static ItemMineral strToMineral(String mineral) {
		return mineralMap.get(mineral);
	}
}
