package slimevoid.tmf.core;

import slimevoid.lib.data.Logger;

public class LoggerTMF extends Logger {
	
	private static Logger instance;

	@Override
	protected String getLoggerName() {
		return "TMF";
	}
	
	public static Logger getInstance(String name) {
		if (instance == null)
			instance = new LoggerTMF();

		instance.setName(name);

		return instance;
	}
}
