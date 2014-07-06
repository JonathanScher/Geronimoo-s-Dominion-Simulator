package be.aga.dominionSimulator;

public class LogHandler {
	private boolean haveToLog;
	private String myLog = "";
	private int logIndentation = 0;
	private int logPlayerIndentation = 0;

	/**
	 * @param aString
	 */
	public void addToLog(String aString) {
		StringBuilder theBuilder = new StringBuilder(myLog);
		for (int i = 0; i < logPlayerIndentation; i++) {
			theBuilder.append("&nbsp;&nbsp;&nbsp;");
		}
		for (int i = 0; i < logIndentation; i++) {
			theBuilder.append("...&nbsp;");
		}
		theBuilder.append(aString).append("<BR>");
		myLog = theBuilder.toString();
	}

	public void addToStartOfLog(String string) {
		StringBuilder theBuilder = new StringBuilder();
		theBuilder.append(string).append("<BR>").append(myLog);
		myLog = theBuilder.toString();
	}

	public void setLog(String string) {
		myLog = string;
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
		return myLog;
	}
}
