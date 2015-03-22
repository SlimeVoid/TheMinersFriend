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
package net.slimevoid.tmf.blocks.machines.recipes;

import java.util.HashMap;
import java.util.Map;

import net.slimevoid.tmf.blocks.ores.BlockTMFOre;
import net.slimevoid.tmf.core.TMFCore;
import net.slimevoid.tmf.core.lib.ConfigurationLib;
import net.slimevoid.tmf.core.lib.JSONLoaderTMF;
import net.slimevoid.tmf.core.lib.ResourceLib;
import net.slimevoid.tmf.items.minerals.ItemMineral;
import argo.jdom.JdomParser;
import argo.jdom.JsonNode;
import argo.jdom.JsonRootNode;
import argo.jdom.JsonStringNode;
import argo.saj.InvalidSyntaxException;

public class JSONRefineryRecipesLoader extends JSONLoaderTMF {

    public JSONRefineryRecipesLoader() {
        super(ResourceLib.RECIPE_PATH_JSON, ResourceLib.RECIPES_REFINERY);
    }

    private static final JdomParser         parser     = new JdomParser();
    private static Map<String, BlockTMFOre> oreMap     = new HashMap<String, BlockTMFOre>();
    private static Map<String, ItemMineral> mineralMap = new HashMap<String, ItemMineral>();

    static {
        oreMap.put("arkite",
                   (BlockTMFOre) ConfigurationLib.arkiteOre);
        oreMap.put("bistite",
                   (BlockTMFOre) ConfigurationLib.bistiteOre);
        oreMap.put("crokere",
                   (BlockTMFOre) ConfigurationLib.crokereOre);
        oreMap.put("dernite",
                   (BlockTMFOre) ConfigurationLib.derniteOre);
        oreMap.put("egioclase",
                   (BlockTMFOre) ConfigurationLib.egioclaseOre);

        mineralMap.put("acxium",
                       (ItemMineral) ConfigurationLib.mineralAcxium);
        mineralMap.put("bisogen",
                       (ItemMineral) ConfigurationLib.mineralBisogen);
        mineralMap.put("cydrine",
                       (ItemMineral) ConfigurationLib.mineralCydrine);
    }

    @Override
    protected void parseJSON(String json) throws InvalidSyntaxException {
        JsonRootNode root = parser.parse(json);

        for (String ore : oreMap.keySet()) {
            if (root.isObjectNode(ore)) {
                System.out.println("Reading JSON node: " + ore);
                for (JsonStringNode mineralNode : root.getObjectNode(ore).keySet()) {
                    String mineral = mineralNode.getStringValue();
                    if (mineralMap.containsKey(mineral)) {
                        System.out.println("Reading JSON child-node: "
                                           + mineral);
                        JsonNode node = root.getObjectNode(ore).get(mineralNode);

                        int min = -1;
                        int max = -1;

                        if (node.isNumberValue(0)) min = Integer.parseInt(node.getNumberValue(0));
                        if (node.isNumberValue(1)) max = Integer.parseInt(node.getNumberValue(1));

                        if (min < 0 && max >= 0) min = max;
                        if (max < 0 && min >= 0) max = min;

                        registerRecipe(ore,
                                       min,
                                       max,
                                       mineral);
                    }
                }
            }
        }
    }

    private static void registerRecipe(String ore, int min, int max, String mineral) {
        RefineryRecipes.refining().addRefinement(strToOre(ore),
                                                 min,
                                                 max,
                                                 strToMineral(mineral));
    }

    private static BlockTMFOre strToOre(String ore) {
        return oreMap.get(ore);
    }

    private static ItemMineral strToMineral(String mineral) {
        return mineralMap.get(mineral);
    }
}
