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
package slimevoid.tmf.fuel;

import java.util.HashMap;
import java.util.Map;

import slimevoid.tmf.minerals.items.ItemMineralMixedDust;

public class MixedDustNameRegistry {
	private static Map<String, String>	nameMapping	= new HashMap<String, String>();

	public static void addName(int meta, String name) {
		addName(ItemMineralMixedDust.getBurnTimeLevel(meta),
				ItemMineralMixedDust.getBurnSpeedLevel(meta),
				ItemMineralMixedDust.getBurnWidthLevel(meta),
				name);
	}

	public static void addName(int aL, int bL, int cL, String name) {
		synchronized (nameMapping) {
			nameMapping.put(getKey(	aL,
									bL,
									cL),
							name);
		}
	}

	public static String getName(int meta) {
		return getName(	ItemMineralMixedDust.getBurnTimeLevel(meta),
						ItemMineralMixedDust.getBurnSpeedLevel(meta),
						ItemMineralMixedDust.getBurnWidthLevel(meta));
	}

	public static String getName(int aL, int bL, int cL) {
		String out = null;
		synchronized (nameMapping) {
			out = nameMapping.get(getKey(	aL,
											bL,
											cL));
		}
		if (out != null) return out;

		return getDefaultName(	aL,
								bL,
								cL);
	}

	private static String getKey(int aL, int bL, int cL) {
		return aL + "," + bL + "," + cL;
	}

	private static String getDefaultName(int aL, int bL, int cL) {
		StringBuilder builder = new StringBuilder();

		boolean first = true;
		if (aL > 0) {
			builder.append(getALevel(	aL,
										first));
			first = false;
		}
		if (bL > 0) {
			builder.append(getBLevel(	bL,
										first));
			first = false;
		}
		if (cL > 0) {
			builder.append(getCLevel(	cL,
										first));
		}
		builder.append(" (");
		if (aL > 0) {
			builder.append(aL + "A");
		}
		if (bL > 0) {
			builder.append(bL + "B");
		}
		if (cL > 0) {
			builder.append(cL + "C");
		}
		builder.append(")");

		return Character.toUpperCase(builder.toString().charAt(0))
				+ builder.toString().substring(1);
	}

	private static String getALevel(int aL, boolean first) {
		String num = "";
		if (first || aL > 1) {
			num = getNumeric(aL);
		}
		String name = "axi";
		if (num.length() > 0 && num.substring(	num.length() - 1,
												num.length()).equals("a")) name = "xi";

		return num + name;
	}

	private static String getBLevel(int bL, boolean first) {
		String num = "";
		if (first || bL > 1) {
			num = getNumeric(bL);
		}
		String name = "ogen";
		if (num.length() > 0 && num.substring(	num.length() - 1,
												num.length()).equals("o")) name = "gen";

		return num + name;
	}

	private static String getCLevel(int cL, boolean first) {
		String num = "";
		if (first || cL > 1) {
			num = getNumeric(cL);
		}
		String name = "cyde";

		return num + name;
	}

	private static String getNumeric(int i) {
		switch (i) {
		case 1:
			return "mono";
		case 2:
			return "di";
		case 3:
			return "tri";
		case 4:
			return "tetra";
		case 5:
			return "penta";
		case 6:
			return "hexa";
		case 7:
			return "hepta";
		case 8:
			return "octa";
		case 9:
			return "nona";
		default:
			return "";
		}
	}
}
