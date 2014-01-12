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
