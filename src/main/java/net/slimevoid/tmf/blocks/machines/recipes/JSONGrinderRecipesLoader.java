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

import net.slimevoid.tmf.core.TMFCore;
import net.slimevoid.tmf.core.lib.JSONLoaderTMF;
import net.slimevoid.tmf.core.lib.ResourceLib;
import net.slimevoid.tmf.items.ItemTMF;
import net.slimevoid.tmf.items.minerals.ItemMineral;
import net.slimevoid.tmf.items.minerals.ItemMineralDust;
import net.slimevoid.tmf.items.minerals.ItemMineralIngot;
import net.slimevoid.tmf.items.minerals.ItemMineralNugget;
import argo.jdom.JdomParser;
import argo.jdom.JsonNode;
import argo.jdom.JsonRootNode;
import argo.jdom.JsonStringNode;
import argo.saj.InvalidSyntaxException;

public class JSONGrinderRecipesLoader extends JSONLoaderTMF {

    public JSONGrinderRecipesLoader() {
        super(ResourceLib.RECIPE_PATH_JSON, ResourceLib.RECIPES_GRINDER);
    }

    private static final JdomParser             parser     = new JdomParser();
    private static Map<String, ItemTMF>         mineralMap = new HashMap<String, ItemTMF>();
    private static Map<String, ItemMineralDust> dustMap    = new HashMap<String, ItemMineralDust>();

    static {
        mineralMap.put("acxium",
                       (ItemMineral) TMFCore.mineralAcxium);
        mineralMap.put("bisogen",
                       (ItemMineral) TMFCore.mineralBisogen);
        mineralMap.put("cydrine",
                       (ItemMineral) TMFCore.mineralCydrine);
        mineralMap.put("acxiumNugget",
                       (ItemMineralNugget) TMFCore.nuggetAcxium);
        mineralMap.put("bisogenNugget",
                       (ItemMineralNugget) TMFCore.nuggetBisogen);
        mineralMap.put("cydrineNugget",
                       (ItemMineralNugget) TMFCore.nuggetCydrine);
        mineralMap.put("acxiumIngot",
                       (ItemMineralIngot) TMFCore.ingotAcxium);
        mineralMap.put("bisogenIngot",
                       (ItemMineralIngot) TMFCore.ingotBisogen);
        mineralMap.put("cydrineIngot",
                       (ItemMineralIngot) TMFCore.ingotCydrine);

        dustMap.put("acxium",
                    (ItemMineralDust) TMFCore.dustAcxium);
        dustMap.put("bisogen",
                    (ItemMineralDust) TMFCore.dustBisogen);
        dustMap.put("cydrine",
                    (ItemMineralDust) TMFCore.dustCydrine);
    }

    @Override
    protected void parseJSON(String json) throws InvalidSyntaxException {
        JsonRootNode root = parser.parse(json);

        for (String mineral : mineralMap.keySet()) {
            if (root.isObjectNode(mineral)) {
                for (JsonStringNode mineralNode : root.getObjectNode(mineral).keySet()) {
                    String dust = mineralNode.getStringValue();
                    if (dustMap.containsKey(dust)) {
                        JsonNode node = root.getObjectNode(mineral).get(mineralNode);

                        int min = -1;
                        int max = -1;

                        if (node.isNumberValue(0)) min = Integer.parseInt(node.getNumberValue(0));
                        if (node.isNumberValue(1)) max = Integer.parseInt(node.getNumberValue(1));

                        if (min < 0 && max >= 0) min = max;
                        if (max < 0 && min >= 0) max = min;

                        registerRecipe(mineral,
                                       min,
                                       max,
                                       dust);
                    }
                }
            }
        }
    }

    private static void registerRecipe(String mineral, int min, int max, String dust) {
        GrinderRecipes.grinding().addRefinement(strToMineral(mineral),
                                                min,
                                                max,
                                                strToDust(dust));
    }

    private static ItemMineralDust strToDust(String dust) {
        return dustMap.get(dust);
    }

    private static ItemTMF strToMineral(String mineral) {
        return mineralMap.get(mineral);
    }
}
