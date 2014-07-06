package be.aga.dominionSimulator;

public class LogHandler {
	private boolean haveToLog;
	private StringBuilder myLog;
	private int logIndentation = 0;
	private int logPlayerIndentation = 0;

	public LogHandler() {
		myLog = new StringBuilder();
	}

	/**
	 * @param aString
	 */
	public void addToLog(String aString) {
		for (int i = 0; i < logPlayerIndentation; i++) {
			myLog.append("&nbsp;&nbsp;&nbsp;");
		}
		for (int i = 0; i < logIndentation; i++) {
			myLog.append("...&nbsp;");
		}
		myLog.append(aString).append("<BR>");
	}
	
	public void addToLog(String... strings) {
		for (int i = 0; i < logPlayerIndentation; i++) {
			myLog.append("&nbsp;&nbsp;&nbsp;");
		}
		for (int i = 0; i < logIndentation; i++) {
			myLog.append("...&nbsp;");
		}
		for(String aString:strings) {
			myLog.append(aString);
		}
		myLog.append("<BR>");
	}

	public void addToStartOfLog(String string) {
		myLog.insert(0, "<BR>");
		myLog.insert(0, string);
	}

	public void setLog(String string) {
		myLog = new StringBuilder(string);
	}

	public void setHaveToLog(boolean haveToLog) {
		this.haveToLog = haveToLog;
	}

	public boolean getHaveToLog() {
		return haveToLog;
	}

	public void setLogPlayerIndentation(int logPlayerIndentation) {
		this.logPlayerIndentation = logPlayerIndentation;
	}

	public void increaselogPlayerIndentation() {
		logPlayerIndentation++;
	}

	public void increaseLogIndentation() {
		logIndentation++;
	}

	public void decreaseLogIndentation() {
		logIndentation--;
	}

	public String getMyLog() {
		return myLog.toString();
	}
}
