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
package slimevoid.tmf.core.lib;

import slimevoid.tmf.core.TMFCore;
import slimevoidlib.util.json.JSONLoader;

public abstract class JSONLoaderTMF extends JSONLoader {

	public JSONLoaderTMF(String location, String filename) {
		super(TMFCore.class, location, filename);
	}

	@Override
	protected String getModID() {
		return CoreLib.MOD_ID;
	}

}
