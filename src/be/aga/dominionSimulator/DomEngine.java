package be.aga.dominionSimulator;

import java.awt.Component;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import org.apache.log4j.Logger;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import be.aga.dominionSimulator.enums.DomBotType;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;
import be.aga.dominionSimulator.enums.DomSet;
import be.aga.dominionSimulator.gui.DomBarChart;
import be.aga.dominionSimulator.gui.DomGameFrame;
import be.aga.dominionSimulator.gui.DomGui;
import be.aga.dominionSimulator.gui.DomLineChart;
import be.aga.dominionSimulator.gui.StatusBar;

public class DomEngine {
	public static double NUMBER_OF_GAMES = 1000;
	private static final Logger LOGGER = Logger.getLogger(DomEngine.class);
	public static final boolean addAppender = true;

	private ArrayList<DomPlayer> players = new ArrayList<DomPlayer>();
	private long findWinnerTime = 0;
	private ArrayList<DomPlayer> bots;
	private long boardResetTime = 0;
	private long checkGameFinishTime = 0;
	private long playerTurnTime = 0;
	private DomGui myGui;
	private String myLastFile;
	private double myTotalTime;
	private int emptyPilesEndingCount = 0;
	private StatusBar myStatusBar;
	private DomGameFrame myGameFrame;
	private LogHandler logHandler;

	public DomEngine() {
		this.logHandler = new LogHandler();
		loadSystemBots();
		createSimpleCardStrategiesBots();
		myGui = new DomGui(this, logHandler);
		myGui.setVisible(true);
	}

	private void createSimpleCardStrategiesBots() {
		for (DomCardName theCard : DomCardName.values()) {
			if (theCard.hasCardType(DomCardType.Kingdom)) {
				DomPlayer theBot = getBot("Big Money Ultimate").getCopy(
						theCard.toString());
				if (getBot(theBot.name) != null)
					continue;
				theBot.setDescription("This bot has been generated by the computer without any optimization. "
						+ "XXXXIt just buys a single Action card and money");
				theBot.setAuthor("Computer");
				theBot.addBuyRuleFor(theCard);
				theBot.getTypes().remove(DomBotType.Optimized);
				theBot.setComputerGenerated();
				bots.add(theBot);
			}
		}
	}

	public void loadSystemBots() {
		try {
			InputSource src = new InputSource(getClass().getResourceAsStream(
					"DomBots.xml"));
			// InputSource src = new InputSource(new FileInputStream(new
			// File("..."));
			XMLHandler saxHandler = new XMLHandler();
			XMLReader rdr = XMLReaderFactory.createXMLReader();
			rdr.setContentHandler(saxHandler);
			rdr.parse(src);
			bots = saxHandler.getBots();
		} catch (Exception e) {
			JOptionPane
					.showMessageDialog(myGui,
							"You'll need to download Java 1.6 at www.java.com to run this program!!!");
		}
		Collections.sort(bots);
	}

	public DomPlayer loadUserBotsFromXML(InputSource anXMLSource) {
		try {
			XMLHandler saxHandler = new XMLHandler();
			XMLReader rdr = XMLReaderFactory.createXMLReader();
			rdr.setContentHandler(saxHandler);
			rdr.parse(anXMLSource);
			ArrayList<DomPlayer> theNewPlayers = saxHandler.getBots();
			for (DomPlayer thePlayer : theNewPlayers) {
				thePlayer.addType(DomBotType.Bot);
				thePlayer.addType(DomBotType.UserCreated);
				addUserBot(thePlayer);
			}
			return bots.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane
					.showMessageDialog(
							myGui,
							"Bot creation failed! Make sure you have a valid XML in your clipboard",
							"", JOptionPane.ERROR_MESSAGE);
		}
		return null;
	}

	private void showCharts() {
		myGui.setBarChart(new DomBarChart(players));
		myGui.setVPLineChart(new DomLineChart(players, "VP"));
		myGui.setMoneyLineChart(new DomLineChart(players, "Money"));
		myGui.validate();
	}

	private void printResults() {
		int theTotalWins = 0;
		int theTotalTies = 0;
		for (DomPlayer thePlayer : players) {
			theTotalWins += thePlayer.getWins();
			theTotalTies += thePlayer.getTies();
		}
		LOGGER.info("=============================");
		LOGGER.info("Games");
		LOGGER.info("=============================");

		double theAverageTurns = ((int) (players.get(0).getSumTurns() * 10 / NUMBER_OF_GAMES)) / 10.0;
		LOGGER.info("Average turns = " + theAverageTurns);

		showRunTimes();

		for (DomPlayer thePlayer : players) {
			if (NUMBER_OF_GAMES > 1) {
				myGui.showWinPercentage(thePlayer, thePlayer.getWins() * 100
						/ (theTotalWins + theTotalTies / 2));
				myGui.showTiePercentage(thePlayer.getTies() * 100
						/ (theTotalWins + theTotalTies / 2));
				myGui.showAverageTurns(theAverageTurns);
				myGui.show3EmptyPilesEndings(emptyPilesEndingCount
						/ NUMBER_OF_GAMES * 100);
				myGui.showTime(myTotalTime);
			}
			LOGGER.info(thePlayer + " has " + thePlayer.getWins() * 100
					/ (theTotalWins + theTotalTies / 2) + "% wins ("
					+ thePlayer.getWins() + ")" + " and " + thePlayer.getTies()
					* 100 / (theTotalWins + theTotalTies / 2) + "% ties ("
					+ thePlayer.getTies() + ")");
			LOGGER.info("Empty Piles Endings : " + emptyPilesEndingCount
					/ NUMBER_OF_GAMES * 100 + "%");
		}
	}

	/**
     * 
     */
	private void showRunTimes() {
		long theTotalActionTime = 0;
		long theTotalBuyTime = 0;
		long theTotalCountVPTime = 0;
		for (DomPlayer thePlayer : players) {
			theTotalActionTime += thePlayer.actionTime;
			theTotalBuyTime += thePlayer.buyTime;
			theTotalCountVPTime += thePlayer.countVPTime;
		}
		LOGGER.info("Action time : " + theTotalActionTime);
		LOGGER.info("Buy time: " + theTotalBuyTime);
		LOGGER.info("count VPs time: " + theTotalCountVPTime);
		LOGGER.info("player turn time: " + playerTurnTime);
		LOGGER.info("find winner time: " + findWinnerTime);
		LOGGER.info("board reset time: " + boardResetTime);
		LOGGER.info("check game finish time: " + checkGameFinishTime);
	}

	/**
	 * @param aString
	 * @return
	 */
	private DomPlayer getBot(String aString) {
		for (DomPlayer thePlayer : bots) {
			if (thePlayer.toString().equals(aString))
				return thePlayer;
		}
		return null;
	}

	public static void main(String[] args) {
		new DomEngine();
	}

	public Object[] getBotArray() {
		return bots.toArray();
	}

	/**
	 * @param thePlayers
	 * @param keepOrder
	 * @param aNumber
	 * @param aShowLog
	 */
	public void startSimulation(List<DomPlayer> thePlayers, boolean keepOrder,
			int aNumber, boolean aShowLog) {
		emptyPilesEndingCount = 0;
		NUMBER_OF_GAMES = aNumber;
		logHandler.setLog("<BR><HR><B>Game Log</B><BR>");
		long theStartTime = System.currentTimeMillis();
		players.clear();
		players.addAll(thePlayers);
		DomBoard theBoard = null;
		for (int i = 0; i < NUMBER_OF_GAMES; i++) {
			if (!keepOrder) {
				Collections.shuffle(players);
			}
			logHandler.setHaveToLog(false);
			DomGame theGame = new DomGame(theBoard, players, logHandler);
			logHandler.setHaveToLog(aShowLog);
			theGame.run();
			if (logHandler.getHaveToLog()) {
				writeEndOfGameLog(theGame);
			}
			playerTurnTime += theGame.playerTurnTime;
			checkGameFinishTime += theGame.checkGameFinishTime;
			emptyPilesEndingCount += theGame.emptyPilesEnding ? 1 : 0;
			long theTime = System.currentTimeMillis();
			theGame.determineWinners();
			findWinnerTime += System.currentTimeMillis() - theTime;
			theBoard = theGame.getBoard();
			theTime = System.currentTimeMillis();
			// LOGGER.info("Game : "+ i);
			// LOGGER.info("--------------");
			// LOGGER.info("Board: "+ theBoard);
			// LOGGER.info(players.get(0) + " : "+ players.get(0).getDeck());
			// LOGGER.info(players.get(1) + " : "+ players.get(1).getDeck());
			theBoard.reset();
			boardResetTime += System.currentTimeMillis() - theTime;
		}
		// restoring the player order:
		players.clear();
		players.addAll(thePlayers);

		myTotalTime = ((System.currentTimeMillis() - theStartTime) / 100) / 10.0;
		LOGGER.info("Board after all games: " + theBoard);
		LOGGER.info("Totale run tijd : " + myTotalTime);

		printResults();
		if (!logHandler.getHaveToLog())
			showCharts();
	}

	private void writeEndOfGameLog(DomGame theGame) {
		logHandler.addToLog("</i>");
		logHandler.addToLog("!!!!!!!Game ends!!!!!!!!");
		logHandler.addToLog("");
		logHandler.addToStartOfLog("the Empty Piles : "
				+ theGame.getEmptyPiles());
		if (!theGame.getTrashedCards().isEmpty())
			logHandler.addToStartOfLog("the Trashed Cards : "
					+ theGame.getTrashedCards());
		logHandler.addToStartOfLog("");
		String theEmbargoedStuff = theGame.getBoard().getEmbargoInfo();
		if (theEmbargoedStuff != null) {
			logHandler.addToLog("");
			logHandler.addToLog("Embargo Tokens on: " + theEmbargoedStuff);
		}
	}

	/**
	 * @return
	 */
	public ArrayList<DomPlayer> getPlayers() {
		return players;
	}

	public void addUserBot(DomPlayer theNewPlayer) {
		for (DomPlayer theBot : bots) {
			if (theBot.name.equals(theNewPlayer.name)) {
				bots.remove(theBot);
				break;
			}
		}
		bots.add(0, theNewPlayer);
		myGui.refreshBotSelectors(theNewPlayer);
	}

	public void deleteBot(DomPlayer selectedItem) {
		bots.remove(selectedItem);
		myGui.refreshBotSelectors(null);
	}

	public void saveUserBots() {
		JFileChooser fileChooser = new JFileChooser(myLastFile);
		fileChooser.setFileFilter(getFileFilter());
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		if (fileChooser.showSaveDialog(myGui) == JFileChooser.APPROVE_OPTION) {
			Writer output = null;
			try {
				String theWriteFile = fileChooser.getCurrentDirectory()
						.getAbsolutePath() + "\\";
				theWriteFile += fileChooser.getSelectedFile().getName();
				if (!theWriteFile.endsWith(".xml")) {
					theWriteFile += ".xml";
				}
				myLastFile = theWriteFile.replaceAll("\\\\", "/");
				;
				output = new BufferedWriter(new FileWriter(myLastFile));
				output.write(getXMLForAllUserBots());
				output.close();
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(myGui, "Error Writing File",
						"error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private String getXMLForAllUserBots() {
		String newline = System.getProperty("line.separator");
		StringBuilder theXML = new StringBuilder();
		theXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append(
				newline);
		theXML.append("<playerCollection>").append(newline);
		for (DomPlayer thePlayer : bots) {
			if (thePlayer.isUserCreated()) {
				theXML.append(thePlayer.getXML()).append(newline);
			}
		}
		theXML.append("</playerCollection>");
		return theXML.toString();
	}

	public void loadUserBots() {
		JFileChooser fileChooser = new JFileChooser(myLastFile);
		fileChooser.setFileFilter(getFileFilter());
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		if (fileChooser.showOpenDialog(myGui) == JFileChooser.APPROVE_OPTION) {
			Reader input = null;
			try {
				String theReadFile = fileChooser.getCurrentDirectory()
						.getAbsolutePath() + "\\";
				theReadFile += fileChooser.getSelectedFile().getName();
				myLastFile = theReadFile.replaceAll("\\\\", "/");
				LOGGER.info(myLastFile);
				input = new BufferedReader(new FileReader(myLastFile));
				loadUserBotsFromXML(new InputSource(input));
				input.close();
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(myGui, "Error Reading File",
						"error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private FileFilter getFileFilter() {
		return new FileFilter() {
			@Override
			public String getDescription() {
				return "*.xml";
			}

			@Override
			public boolean accept(File f) {
				return f.getName().endsWith(".xml") || f.isDirectory();
			}
		};
	}

	public void orderBots() {
		Collections.sort(bots);
	}

	public Object[] getBots(Object[] domBotTypes) {
		ArrayList<DomPlayer> theBots = new ArrayList<DomPlayer>();
		for (DomPlayer player : bots) {
			int i;
			for (i = 0; i < domBotTypes.length; i++) {
				if (!player.hasType(domBotTypes[i]))
					break;
			}
			if (i >= domBotTypes.length)
				theBots.add(player);
		}
		Collections.sort(theBots);
		return theBots.toArray();
	}

	public void setSelectedBot(Object selectedValue) {
		myGui.refreshBotSelectors((DomPlayer) selectedValue);
	}

	public boolean doesBotExist(DomPlayer domPlayer) {
		for (DomPlayer player : bots) {
			if (domPlayer == player)
				return true;
		}
		return false;
	}

	public ArrayList<DomCardName> getBoardCards() {
		ArrayList<DomCardName> theCards = new ArrayList<DomCardName>();
		theCards.addAll(DomSet.Common.getCards());
		theCards.addAll(DomBoard.getRandomBoard());
		return theCards;
	}

	public ArrayList<DomCard> getHumanPlayerHand() {
		ArrayList<DomCard> theCards = new ArrayList<DomCard>();
		int i = 0;
		for (DomCardName cardName : DomBoard.getRandomBoard()) {
			theCards.add(cardName.createNewCardInstance());
		}
		// for (DomCardName cardName : DomBoard.getRandomBoard()) {
		// theCards.add(cardName.createNewCardInstance());
		// }
		// for (DomCardName cardName : DomBoard.getRandomBoard()) {
		// theCards.add(cardName.createNewCardInstance());
		// }
		// for (DomCardName cardName : DomBoard.getRandomBoard()) {
		// theCards.add(cardName.createNewCardInstance());
		// }
		// for (DomCardName cardName : DomBoard.getRandomBoard()) {
		// theCards.add(cardName.createNewCardInstance());
		// }
		// for (DomCardName cardName : DomBoard.getRandomBoard()) {
		// theCards.add(cardName.createNewCardInstance());
		// }
		return theCards;
	}

	public ArrayList<DomCard> getCardsInPlay() {
		ArrayList<DomCard> theCards = new ArrayList<DomCard>();
		theCards.add(DomCardName.Woodcutter.createNewCardInstance());
		theCards.add(DomCardName.Woodcutter.createNewCardInstance());
		theCards.add(DomCardName.Woodcutter.createNewCardInstance());
		return theCards;
	}

	public Component getStatusBar() {
		myStatusBar = new StatusBar();
		myStatusBar.setText("Alles goed");
		return myStatusBar;
	}

	public void setGameFrame(DomGameFrame domGameFrame) {
		myGameFrame = domGameFrame;
	}

	public DomGui getGui() {
		return myGui;
	}

	public void setSelectedBoard(Object[] selectedValues) {
		// TODO Auto-generated method stub

	}
}