import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicProgressBarUI;

public class KenoSim extends JPanel {

	// by Benjamin Kile

	// Random
	Random rn = new Random();
	// Decimal format
	DecimalFormat df = new DecimalFormat("#");
	// Executors
	ScheduledExecutorService drawExecutor, numBtnExecutor, pT;
	// Fonts
	Font f1 = new Font("default", 0, 10);
	Font f2 = new Font("default", 0, 11);
	// Colors
	Color customRed = new Color(255, 51, 51);
	Color customYellow = new Color(255, 255, 0);
	Color customOrange = new Color(255, 128, 0);
	Color customGreen = new Color(128, 255, 0);
	// String[]s
	String[] numBtnIndexArray = new String[80];
	String[] scoreBtnIndexArray = new String[11];
	String[] guessBtnIndexArray = new String[10];
	// ArrayLists
	ArrayList<Integer> numsList = new ArrayList<Integer>();
	ArrayList<Integer> pickList = new ArrayList<Integer>();
	ArrayList<Ball> guessList = new ArrayList<Ball>();
	ArrayList<Ball> ballList = new ArrayList<Ball>();
	// JPanels
	JPanel numberPanel = new JPanel();
	JPanel northPanel = new JPanel();
	JPanel southPanel = new JPanel();
	JPanel scorePanel = new JPanel();
	JPanel guessPanel = new JPanel();
	// JPanel btnPanel1 = new JPanel();
	// JPanel btnPanel2 = new JPanel();
	JPanel progressPanel = new JPanel();
	JPanel delayInputPanel = new JPanel();
	JPanel drawsInputPanel = new JPanel();
	JPanel miliDelayPanel = new JPanel();
	JPanel settingsPanel = new JPanel();
	JPanel fillPanelArray[] = new JPanel[10];
	// JButtons
	JButton numBtnArray[] = new JButton[80];
	JButton scoreBtnArray[] = new JButton[11];
	JButton guessBtnArray[] = new JButton[10];
	JButton numBtn, hitsBtn, guessBtn = new JButton();
	JButton startBtn = new JButton("Start");
	JButton stopBtn = new JButton("Clear");
	JButton milliPlusBtn = new JButton("+");
	JButton milliMinusBtn = new JButton("-");
	JButton autoGuessBtn = new JButton("!Guess");
	JButton grfBtn = new JButton("!GRF");
	JButton autoBtn = new JButton("Auto Mode");
	JButton manualBtn = new JButton("Manual Mode");
	JButton drawBtn = new JButton("Draw");
	// JLabels
	JLabel l1 = new JLabel();
	JLabel l2 = new JLabel();
	// JTextFields
	JTextField delayField = new JTextField(50);
	JTextField drawsField = new JTextField(50);
	JTextField drawsPerGuessField = new JTextField();
	// JProgressBars
	JProgressBar pBar = new JProgressBar(JProgressBar.HORIZONTAL);
	// booleans
	boolean splash;
	boolean auto = true;
	// boolean manual;
	// boolean auto;
	// longs
	private static final long serialVersionUID = 1L;
	// integers
	int miliDelay = 20;
	int drawCounter = 0;
	int yStartPoint;
	int draws = 50000;
	int hits;
	int guessCountdown;
	int autoGuess = 1;
	int grf = 1;
	int drawsPerGuess = 50;
	int zero, one, two, three, four, five, six, seven, eight, nine, ten;
	int numPicks = 10;
	int checkBeforeShutdown = 0;
	// doubles
	double graphBuffer;
	double seconds;
	double minutes;
	double hours;
	double days;
	// Strings
	String eta = "";

	public static void main(String[] args) {
		// build the frame
		JFrame kenoSimFrame = new JFrame("KenoSim_v1.3");
		kenoSimFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		KenoSim s = new KenoSim();
		kenoSimFrame.setResizable(false);
		kenoSimFrame.getContentPane().add(s);
		kenoSimFrame.pack();
		kenoSimFrame.setLocationRelativeTo(null);
		kenoSimFrame.setVisible(true);
	}

	public KenoSim() {
		// set up the main Panel
		setPreferredSize(new Dimension(1350, 600));
		setLayout(new BorderLayout());
		setBackground(Color.black);
		// set up the startBtn
		startBtn.setFocusable(false);
		startBtn.setForeground(customGreen);
		startBtn.setBackground(Color.black);
		startBtn.setToolTipText("Start Drawing");
		// stopBtn
		stopBtn.setFocusable(false);
		stopBtn.setEnabled(false);
		stopBtn.setBackground(Color.black);
		stopBtn.setForeground(Color.gray);
		stopBtn.setToolTipText("Stop and Clear all Data");
		// set up the miliPlusBtn
		milliPlusBtn.setFocusable(false);
		milliPlusBtn.setFont(f1);
		milliPlusBtn.setToolTipText("Increment Millisecond Delay by 10");
		milliPlusBtn.setBackground(Color.BLACK);
		milliPlusBtn.setForeground(Color.white);
		// set up the miliMinusBtn
		milliMinusBtn.setFocusable(false);
		milliMinusBtn.setToolTipText("Decrement Millisecond Delay by 10");
		milliMinusBtn.setBackground(Color.BLACK);
		milliMinusBtn.setForeground(Color.white);
		// set up autoGuessBTn
		autoGuessBtn.setFocusable(false);
		autoGuessBtn.setToolTipText("Turn Auto Guess On");
		autoGuessBtn.setBackground(Color.BLACK);
		autoGuessBtn.setForeground(Color.red);
		// set up grfBtn
		grfBtn.setFocusable(false);
		grfBtn.setEnabled(false);
		grfBtn.setToolTipText("Turn GRF On");
		//safsafsadfsafsfsfsf
		grfBtn.setBackground(Color.BLACK);
		grfBtn.setForeground(Color.red);
		// set up manual auto and draw buttons
		autoBtn.setEnabled(true);
		autoBtn.setBackground(Color.gray);
		autoBtn.setForeground(Color.black);
		autoBtn.setFocusable(false);
		manualBtn.setEnabled(true);
		manualBtn.setBackground(Color.black);
		manualBtn.setForeground(Color.gray);
		manualBtn.setFocusable(false);
		drawBtn.setBackground(Color.black);
		drawBtn.setForeground(Color.gray);
		drawBtn.setFocusable(false);
		// set up the delayField
		delayField.setHorizontalAlignment(JTextField.CENTER);
		delayField.setText("  " + String.valueOf(miliDelay));
		delayField.setBackground(Color.black);
		delayField.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		delayField.setFont(f1);
		delayField.setForeground(Color.red);
		delayField.setToolTipText("Milliseconds between Draws(MIN 2)");
		// set up the drawsField
		drawsField.setHorizontalAlignment(JTextField.CENTER);
		drawsField.setText(String.valueOf(NumberFormat.getNumberInstance(Locale.US).format(draws)));
		drawsField.setBackground(Color.black);
		drawsField.setBorder(BorderFactory.createLineBorder(Color.gray, 1, false));
		drawsField.setFont(f1);
		drawsField.setForeground(Color.orange);
		drawsField.setToolTipText("Number of Draws(MAX 2 Billion)");
		// set up the drawsPerGuessField
		drawsPerGuessField.setHorizontalAlignment(JTextField.CENTER);
		drawsPerGuessField.setFocusable(true);
		drawsPerGuessField.setFont(f1);
		drawsPerGuessField.setBorder(BorderFactory.createLineBorder(Color.gray, 1, false));
		drawsPerGuessField.setToolTipText("Draws Until Next Guess(MIN 50)");
		drawsPerGuessField.setText(String.valueOf(drawsPerGuess));
		drawsPerGuessField.setBackground(Color.BLACK);
		drawsPerGuessField.setForeground(customOrange);
		// set up the pBar
		pBar.setPreferredSize(new Dimension(710, 25));
		pBar.setStringPainted(false);
		pBar.setBorderPainted(false);
		pBar.setIndeterminate(true);
		pBar.setUI(new BasicProgressBarUI() {
			protected Color getSelectionBackground() {
				return customGreen;
			}
		});
		pBar.setBackground(Color.black);
		pBar.setForeground(customGreen);
		// set up the numberPanel
		numberPanel.setPreferredSize(new Dimension(505, 500));
		numberPanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		numberPanel.setLayout(new GridLayout(8, 10));
		// set up the northPanel
		northPanel.setPreferredSize(new Dimension(1200, 50));
		northPanel.setBackground(Color.black);
		northPanel.setLayout(new GridLayout(2, 0));
		northPanel.setBorder(BorderFactory.createLineBorder(Color.gray));
		// set up the southPanel
		southPanel.setPreferredSize(new Dimension(1200, 25));
		southPanel.setBackground(Color.black);
		southPanel.setLayout(new BorderLayout());
		// set up the scorePanel
		scorePanel.setPreferredSize(new Dimension(600, 50));
		scorePanel.setBackground(Color.black);
		scorePanel.setLayout(new GridLayout());
		southPanel.setBorder(BorderFactory.createLineBorder(Color.gray));
		// set up the guessPanel
		guessPanel.setPreferredSize(new Dimension(600, 50));
		guessPanel.setBackground(Color.black);
		guessPanel.setLayout(new GridLayout());
		/*
		 * //set up the btnPanel1 btnPanel1.setPreferredSize(new Dimension(900,50));
		 * btnPanel1.setLayout(new GridLayout());
		 */
		/*
		 * //set up the btnPanel2 btnPanel2.setPreferredSize(new Dimension(320,50));
		 * btnPanel2.setLayout(new GridLayout());
		 */
		// set up the progressPanel
		progressPanel.setBackground(Color.black);
		progressPanel.setLayout(new BorderLayout());
		// progressPanel.setPreferredSize(new Dimension(580,50));
		// set up the delayPanel
		delayInputPanel.setPreferredSize(new Dimension(150, 50));
		delayInputPanel.setLayout(new GridLayout());
		// set up the drawsInputPanel
		drawsInputPanel.setPreferredSize(new Dimension(75, 50));
		drawsInputPanel.setLayout(new GridLayout());
		drawsInputPanel.setBackground(Color.BLACK);
		// set up the miliDelayPanel
		miliDelayPanel.setBackground(Color.black);
		miliDelayPanel.setLayout(new GridLayout());
		// set up the settingsPanel
		settingsPanel.setPreferredSize(new Dimension(150, 525));
		settingsPanel.setBackground(Color.black);
		settingsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		settingsPanel.setLayout(new GridLayout(0, 1));
		// set up the actionListeners
		startBtn.addActionListener(new BtnListener());
		stopBtn.addActionListener(new BtnListener());
		milliPlusBtn.addActionListener(new BtnListener());
		milliMinusBtn.addActionListener(new BtnListener());
		autoGuessBtn.addActionListener(new BtnListener());
		grfBtn.addActionListener(new BtnListener());
		autoBtn.addActionListener(new BtnListener());
		manualBtn.addActionListener(new BtnListener());
		drawBtn.addActionListener(new BtnListener());
		// build panels
		add(northPanel, BorderLayout.NORTH);
		add(southPanel, BorderLayout.SOUTH);
		add(numberPanel, BorderLayout.WEST);
		add(settingsPanel, BorderLayout.EAST);
		settingsPanel.add(autoBtn);
		settingsPanel.add(startBtn);
		settingsPanel.add(stopBtn);
		miliDelayPanel.add(milliMinusBtn);
		miliDelayPanel.add(delayField);
		miliDelayPanel.add(milliPlusBtn);
		settingsPanel.add(autoGuessBtn);
		settingsPanel.add(grfBtn);
		settingsPanel.add(miliDelayPanel);
		settingsPanel.add(drawsField);
		settingsPanel.add(drawsPerGuessField);
		settingsPanel.add(manualBtn);
		settingsPanel.add(drawBtn);
		progressPanel.add(pBar);
		northPanel.add(guessPanel);
		northPanel.add(scorePanel);
		southPanel.add(progressPanel);
		populateLists();
		toggleModes(1);
		paintTimer();

	}

	public void populateLists() {

		// build the numBtnIndexArray
		for (int i = 0; i < 80; ++i) {
			numBtnIndexArray[i] = String.valueOf(i + 1);
		}
		// build the 80 numBtns
		for (int i = 0; i < numBtnArray.length; ++i) {
			numBtn = new JButton(numBtnIndexArray[i]);
			numBtn.addActionListener(new PickListener());
			numBtn.setBackground(Color.black);
			numBtn.setForeground(Color.black);
			numBtn.setBorderPainted(false);
			numBtn.setFocusPainted(false);
			numBtn.setFont(f1);
			numberPanel.add(numBtn);
			numBtnArray[i] = numBtn;
		}
		// build the guessBtns
		for (int i = 0; i < guessBtnArray.length; ++i) {
			guessBtn = new JButton(guessBtnIndexArray[i]);
			guessBtn.setFont(f1);
			guessBtn.setFocusPainted(false);
			guessBtn.setRolloverEnabled(false);
			guessBtn.setBackground(Color.black);
			guessPanel.add(guessBtn);
			guessBtnArray[i] = guessBtn;
		}
		// build the hitsBtns
		for (int i = 0; i < scoreBtnArray.length; ++i) {
			hitsBtn = new JButton(scoreBtnIndexArray[i]);
			hitsBtn.setBackground(Color.black);
			hitsBtn.setForeground(Color.gray);
			hitsBtn.setEnabled(false);
			hitsBtn.setBorderPainted(false);
			hitsBtn.setFocusPainted(false);
			scorePanel.add(hitsBtn);
			scoreBtnArray[i] = hitsBtn;
		}
		// build the numsList
		for (int i = 1; i < 81; ++i) {
			numsList.add(i);
		}
		// build the ballList
		for (int i = 1; i < 81; ++i) {
			Ball b = new Ball();
			b.value = i;
			ballList.add(b);
		}
		Collections.shuffle(ballList);
		// populate the pickList
		for (int i = 0; i < 10; i++) {
			pickList.add(ballList.get(i).value);
			// numBtnArray[ballList.get(i).value-1].setBackground(Color.GREEN);
		}
		// run the splash
		splash = true;
		Runnable r = new Runnable() {
			int i = -1;

			public void run() {
				i++;
				numBtnArray[i].setForeground(Color.white);
				numBtnArray[i].setBorderPainted(true);
				numBtnArray[i].setFocusPainted(true);
				numBtnArray[i].setBackground(customRed);
				if (i > 0) {
					numBtnArray[i - 1].setBackground(Color.black);

				}
				if (i > 78) {
					splash = false;
					numBtnExecutor.shutdown();
					numBtnArray[78].setBackground(Color.black);
					numBtnArray[79].setBackground(Color.black);
					for (int i = 0; i < pickList.size(); i++) {
						// System.out.println(pickList.get(i));
						numBtnArray[pickList.get(i) - 1].setBackground(customYellow);
						numBtnArray[pickList.get(i) - 1].setForeground(Color.black);

					}
					pBar.setIndeterminate(false);
				}
			}

		};
		numBtnExecutor = Executors.newScheduledThreadPool(1);
		numBtnExecutor.scheduleAtFixedRate(r, 0, 23, TimeUnit.MILLISECONDS);
	}

	public void paintTimer() {

		Runnable r = new Runnable() {
			public void run() {
				handleScore();
				repaint();
			}
		};

		pT = Executors.newScheduledThreadPool(1);
		pT.scheduleAtFixedRate(r, 0, 16, TimeUnit.MILLISECONDS);

	}

	public void handleScore() {

		if (pickList.size() > 0) {
			scoreBtnArray[0].setText("0/" + pickList.size() + ": " + zero + " ");
			scoreBtnArray[1].setText("1/" + pickList.size() + ": " + one + " ");
			scoreBtnArray[0].setToolTipText(String.valueOf(zero));
			scoreBtnArray[1].setToolTipText(String.valueOf(one));
		}
		if (pickList.size() > 1) {
			scoreBtnArray[2].setText("2/" + pickList.size() + ": " + two + " ");
			scoreBtnArray[2].setToolTipText(String.valueOf(two));
		}
		if (pickList.size() > 2) {
			scoreBtnArray[3].setText("3/" + pickList.size() + ": " + three + " ");
			scoreBtnArray[3].setToolTipText(String.valueOf(three));
		}
		if (pickList.size() > 3) {
			scoreBtnArray[4].setText("4/" + pickList.size() + ": " + four + " ");
			scoreBtnArray[4].setToolTipText(String.valueOf(four));
		}
		if (pickList.size() > 4) {
			scoreBtnArray[5].setText("5/" + pickList.size() + ": " + five + " ");
			scoreBtnArray[5].setToolTipText(String.valueOf(five));
		}
		if (pickList.size() > 5) {
			scoreBtnArray[6].setText("6/" + pickList.size() + ": " + six + " ");
			scoreBtnArray[6].setToolTipText(String.valueOf(six));
		}
		if (pickList.size() > 6) {
			scoreBtnArray[7].setText("7/" + pickList.size() + ": " + seven + " ");
			scoreBtnArray[7].setToolTipText(String.valueOf(seven));
		}
		if (pickList.size() > 7) {
			scoreBtnArray[8].setText("8/" + pickList.size() + ": " + eight + " ");
			scoreBtnArray[8].setToolTipText(String.valueOf(eight));
		}
		if (pickList.size() > 8) {
			scoreBtnArray[9].setText("9/" + pickList.size() + ": " + nine + " ");
			scoreBtnArray[9].setToolTipText(String.valueOf(nine));
		}
		if (pickList.size() > 9) {
			scoreBtnArray[10].setText("10/" + pickList.size() + ": " + ten + " ");
			scoreBtnArray[10].setToolTipText(String.valueOf(ten));
		}
	}

	public void startGame(int args) {

		checkBeforeShutdown++;

		if (args == 1) {
			toggleModes(1);
			pBar.setStringPainted(true);

			try {
				miliDelay = Integer.valueOf(delayField.getText().trim());
			} catch (NumberFormatException e1) {
				miliDelay = 20;
				delayField.setText(String.valueOf(miliDelay));
			}
			try {
				draws = Integer.valueOf(drawsField.getText().replace(",", "").trim());
				if (draws > 2000000000) {
					draws = 2000000000;
				}
			} catch (NumberFormatException e1) {
				draws = 50000;
				drawsField.setText(String.valueOf(NumberFormat.getNumberInstance(Locale.US).format(draws)));
			}
			if (miliDelay < 2) {
				miliDelay = 2;
			}
			try {
				drawsPerGuess = Integer.valueOf(drawsPerGuessField.getText());
				if (drawsPerGuess < 10) {
					drawsPerGuess = 10;
				}
				drawsPerGuessField
						.setText(String.valueOf(NumberFormat.getNumberInstance(Locale.US).format(drawsPerGuess)));
			} catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				drawsPerGuess = 10;
			}

			drawCounter = 0;
			delayField.setText(String.valueOf(miliDelay));
			drawsField.setText(String.valueOf(NumberFormat.getNumberInstance(Locale.US).format(draws)));
			drawsPerGuessField.setText(String.valueOf(NumberFormat.getNumberInstance(Locale.US).format(drawsPerGuess)));
			startBtn.setEnabled(false);
			stopBtn.setEnabled(true);
			stopBtn.setForeground(customRed);
			milliPlusBtn.setEnabled(false);
			milliMinusBtn.setEnabled(false);
			drawsField.setEnabled(false);
			drawsPerGuessField.setEnabled(false);
			delayField.setEnabled(false);

			Runnable drawRunnable = new Runnable() {

				public void run() {
					int i = -1;
					hits = 0;
					graphBuffer += .25;
					int j = -1;
					do {
						j++;
						if (pickList.contains(j + 1) == false) {
							numBtnArray[j].setBackground(Color.black);
						} else {
							numBtnArray[j].setBackground(Color.YELLOW);
							numBtnArray[j].setForeground(Color.black);
						}
					} while (j < 79);
					drawCounter++;
					if (drawCounter > draws) {
						try {
							drawExecutor.shutdown();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						startBtn.setEnabled(true);
						milliPlusBtn.setEnabled(true);
						milliMinusBtn.setEnabled(true);
					}
					Collections.shuffle(numsList);

					i = -1;
					do {
						i++;
						ballList.get(numsList.get(i) - 1).frequency++;
						// System.out.println(ballList.get(numsList.get(i)-1).value + ": " +
						// ballList.get(numsList.get(i)-1).frequency);
						if (pickList.contains(numsList.get(i)) == true) {
							numBtnArray[numsList.get(i) - 1].setBackground(Color.GREEN);
							numBtnArray[numsList.get(i) - 1].setForeground(Color.red);
							hits += 1;
						}
						if (pickList.contains(numsList.get(i)) == false) {
							numBtnArray[numsList.get(i) - 1].setBackground(customRed);
						}
					} while (i < 19);

					seconds = (miliDelay * ((draws - drawCounter) * .001) + (.5));
					minutes = seconds * 0.0166667;
					hours = minutes * 0.0166667;
					days = hours * 0.0416667;

					eta = "";

					if (days > 1) {
						eta += df.format(days) + " D, ";
					}
					if (hours > 1) {
						eta += df.format(hours % 24) + " Hr, ";
					}
					if (minutes > 1) {
						eta += df.format(minutes % 60) + " Min, ";
					}
					if (seconds > 1) {
						if (seconds > 9) {
							eta += df.format(seconds % 60) + " Sec ";
						} else {
							eta += 0 + df.format(seconds % 60) + " Sec ";
						}

					}

					/*
					 * g.drawString("Days: " + df.format(days), 700, 70); g.drawString("Hours: " +
					 * df.format(hours % 24), 800, 70); g.drawString("Minutes: " + df.format(minutes
					 * % 60), 900, 70); g.drawString("Seconds: " + df.format(seconds % 60), 1000,
					 * 70);
					 */

					double numerator = drawCounter;
					double donominator = draws;
					double percent = Math.round((Double.valueOf(numerator / donominator) * 100) * 10.0) / 10.0;
					pBar.setValue((int) percent);

					if (percent == 48) {
						pBar.setUI(new BasicProgressBarUI() {
							protected Color getSelectionBackground() {
								return customGreen;
							}

							protected Color getSelectionForeground() {
								return Color.black;
							}
						});
					}

					if (hits == 0) {

						zero++;
					}
					if (hits == 1) {
						one++;
					}
					if (hits == 2) {
						two++;
					}
					if (hits == 3) {
						three++;
					}
					if (hits == 4) {
						four++;
					}
					if (hits == 5) {
						five++;
					}
					if (hits == 6) {
						six++;
					}
					if (hits == 7) {
						seven++;
					}
					if (hits == 8) {
						eight++;
					}
					if (hits == 9) {
						nine++;
					}
					if (hits == 10) {
						ten++;
					}
					guessCountdown += 1;
					// Comparison method violates its general contract! bug
					try {
						if (guessCountdown > drawsPerGuess) {
							guessCountdown = 0;
							guessList.clear();
							guessList.addAll(ballList);
							Collections.sort(guessList, new Comparator<Ball>() {
								@Override
								public int compare(Ball w1, Ball w2) {
									if (w1.frequency < w2.frequency) {
										return 1;
									} else {
										return -1;
									}
								}
							});
							// ******************************************
							if ((autoGuess % 2) == 0) {
								numPicks = pickList.size();
								pickList.clear();
								i = -1;
								do {
									i++;
									numBtnArray[i].setForeground(Color.white);
								} while (i < 79);
								i = -1;
								do {
									i++;
									int x = guessList.get(i).value;
									pickList.add(x);
								} while (i < numPicks - 1);
								// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

								if ((grf % 2) == 0) {
									drawsPerGuess = rn.nextInt(300 - 10) + 10;
								} else {
									try {
										drawsPerGuess = Integer.valueOf(drawsPerGuessField.getText());
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
										drawsPerGuess = 50;
									}
								}
								drawsPerGuessField.setText(String.valueOf(drawsPerGuess));
								// System.out.println(drawsPerGuess);
								// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
								// System.out.println(pickList);
								// ******************************************
							}
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					i = -1;
					if (guessList.size() > 0) {
						do {
							i++;
							guessBtnArray[i].setText(String.valueOf(guessList.get(i).value));
							if (i < 6) {
								guessBtnArray[i].setForeground(Color.GREEN);
							} else {
								guessBtnArray[i].setForeground(Color.ORANGE);
							}
						} while (i < 9);
					}
					// repaint();
				}
			};
			drawExecutor = Executors.newScheduledThreadPool(1);
			drawExecutor.scheduleAtFixedRate(drawRunnable, 0, miliDelay, TimeUnit.MILLISECONDS);
			graphBuffer = 0;
			int i = -1;
			do {
				i++;
				ballList.get(i).frequency = 0;
			} while (i < 79);

		}

		if (args == 0) {
			// toggleModes(0);
			int i = -1;
			hits = 0;
			graphBuffer += .25;
			int j = -1;
			do {
				j++;
				if (pickList.contains(j + 1) == false) {
					numBtnArray[j].setBackground(Color.black);
				} else {
					numBtnArray[j].setBackground(Color.YELLOW);
					numBtnArray[j].setForeground(Color.black);
				}
			} while (j < 79);
			drawCounter++;
			Collections.shuffle(numsList);

			i = -1;
			do {
				i++;
				ballList.get(numsList.get(i) - 1).frequency++;
				// System.out.println(ballList.get(numsList.get(i)-1).value + ": " +
				// ballList.get(numsList.get(i)-1).frequency);
				if (pickList.contains(numsList.get(i)) == true) {
					numBtnArray[numsList.get(i) - 1].setBackground(Color.GREEN);
					numBtnArray[numsList.get(i) - 1].setForeground(Color.red);
					hits += 1;
				}
				if (pickList.contains(numsList.get(i)) == false) {
					numBtnArray[numsList.get(i) - 1].setBackground(customRed);
				}
			} while (i < 19);

			if (hits == 0) {
				zero++;
			}
			if (hits == 1) {
				one++;
			}
			if (hits == 2) {
				two++;
			}
			if (hits == 3) {
				three++;
			}
			if (hits == 4) {
				four++;
			}
			if (hits == 5) {
				five++;
			}
			if (hits == 6) {
				six++;
			}
			if (hits == 7) {
				seven++;
			}
			if (hits == 8) {
				eight++;
			}
			if (hits == 9) {
				nine++;
			}
			if (hits == 10) {
				ten++;
			}
		}
	}

	public void stopGame() {
		pBar.setStringPainted(false);
		// bug?
		// ************************************************
		pBar.setValue(-1);
		// ************************************************
		drawsField.setEnabled(true);
		drawsPerGuessField.setEnabled(true);
		delayField.setEnabled(true);
		stopBtn.setEnabled(false);
		startBtn.setEnabled(true);
		guessList.clear();
		drawCounter = 0;
		zero = 0;
		one = 0;
		two = 0;
		three = 0;
		four = 0;
		five = 0;
		six = 0;
		seven = 0;
		eight = 0;
		nine = 0;
		ten = 0;

		int i = -1;
		do {
			i++;
			guessBtnArray[i].setText("");
		} while (i < 9);
		i = -1;
		do {
			i++;
			scoreBtnArray[i].setText("");
		} while (i < 9);

		try {
			i = -1;
			do {
				i++;
				ballList.get(i).frequency = 0;
			} while (i < 79);
			pBar.setValue(0);
			startBtn.setEnabled(true);
			milliPlusBtn.setEnabled(true);
			milliMinusBtn.setEnabled(true);
			try {
				drawExecutor.shutdown();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			pickList.clear();
			guessList.clear();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		i = -1;
		do {
			i++;
			if (pickList.contains(i + 1) == false) {
				numBtnArray[i].setBackground(Color.black);
				numBtnArray[i].setForeground(Color.WHITE);
			} else {
				numBtnArray[i].setBackground(Color.YELLOW);
				numBtnArray[i].setForeground(Color.black);
			}
		} while (i < 79);
	}

	public void toggleModes(int args) {

		if (args == 1) {
			auto = true;
			System.out.println("Auto");
			startBtn.setEnabled(true);
			stopBtn.setEnabled(true);
			autoGuessBtn.setEnabled(true);
			grfBtn.setEnabled(true);
			milliPlusBtn.setEnabled(true);
			milliMinusBtn.setEnabled(true);
			delayField.setEnabled(true);
			drawsField.setEnabled(true);
			drawsPerGuessField.setEnabled(true);
			drawBtn.setEnabled(false);
			manualBtn.setBackground(Color.black);
			manualBtn.setForeground(Color.gray);
			autoBtn.setBackground(Color.gray);
			autoBtn.setForeground(Color.black);

		} else {
			auto = false;
			System.out.println("Manual");
			stopGame();
			startBtn.setEnabled(false);
			stopBtn.setEnabled(false);
			autoGuessBtn.setEnabled(false);
			grfBtn.setEnabled(false);
			milliPlusBtn.setEnabled(false);
			milliMinusBtn.setEnabled(false);
			delayField.setEnabled(false);
			drawsField.setEnabled(false);
			drawsPerGuessField.setEnabled(false);
			drawBtn.setEnabled(true);
			manualBtn.setBackground(Color.gray);
			manualBtn.setForeground(Color.black);
			autoBtn.setBackground(Color.black);
			autoBtn.setForeground(Color.gray);
			drawBtn.setForeground(Color.green);

		}

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setFont(f1);
		int xPad = 20;
		int graphX = 510;
		int graphY = 0;
		int i = -1;
		///////////////////////////////////////////////////////////////////////////////////////////////////// ????????????????????????????
		/*
		 * if ((autoGuess % 2) == 0) { g.setColor(Color.GREEN);
		 * g.drawString("Auto Guess On", 1100, 70); } else { g.setColor(Color.red);
		 * g.drawString("Auto Guess Off", 1100, 70); }
		 */
		g.setColor(Color.gray);
		// draw the hit counters
		if (drawCounter > 0) {
			g.drawString("Draw: " + String.valueOf(NumberFormat.getNumberInstance(Locale.US).format(drawCounter - 1)),
					510, 70);
		}
		if (auto == true && (!eta.equals(""))) {
			g.drawString("Remaining: " + eta, 875, 70);

			/*
			 * g.drawString("Days: " + df.format(days), 700, 70); g.drawString("Hours: " +
			 * df.format(hours % 24), 800, 70); g.drawString("Minutes: " + df.format(minutes
			 * % 60), 900, 70); g.drawString("Seconds: " + df.format(seconds % 60), 1000,
			 * 70);
			 */
		}

		g.setColor(Color.white);
		g.setFont(f2);
		// draw divider lines
		g.drawLine(400, 150, 1200, 150);
		g.drawLine(400, 280, 1200, 280);
		g.drawLine(400, 400, 1200, 400);
		g.drawLine(400, 520, 1200, 520);
		g.drawLine(1210, 600, 1210, 0);
		g.setFont(f1);
		// draw the numbers to represent ball 1-20
		for (int j = 1; j < 21; ++j) {
			g.drawString(String.valueOf(j), 490 + xPad, 170);
			xPad += 35;
		}

		// *********************************************************************************************************
		// ***************************************Bug***************************************************************
		// *********************************************************************************************************
		// *********************************************************************************************************
		graphY = 151;
		yStartPoint = (int) (150 - ballList.get(0).frequency + graphBuffer);
		if (yStartPoint < 95) {
			yStartPoint = 95;
		}
		do {// loop around y axis
			graphY -= 1; // bring y down by one
			if (yStartPoint < 130) {
				g.setColor(customGreen);
			}
			if (yStartPoint < 115) {
				g.setColor(customOrange);
			}
			if (yStartPoint < 96) {
				g.setColor(customRed);
			}
			g.drawLine(510, graphY, 540, graphY);
			g.setColor(Color.black);
			if (yStartPoint < 96) {
				g.setColor(Color.green);
			}
			g.setFont(f2);
			if (ballList.get(0).frequency < 10000) {
				g.drawString(
						String.valueOf(NumberFormat.getNumberInstance(Locale.US).format(ballList.get(0).frequency)),
						graphX, 145);
			} else {
				if (ballList.get(0).value % 2 == 0) {
					g.drawString(
							String.valueOf(NumberFormat.getNumberInstance(Locale.US).format(ballList.get(0).frequency)),
							graphX, 185);
				} else {
					g.drawString(
							String.valueOf(NumberFormat.getNumberInstance(Locale.US).format(ballList.get(0).frequency)),
							graphX, 145);
				}
			}
			g.setFont(f1);
			g.setColor(Color.GREEN);
		} while (graphY > yStartPoint);
		// *********************************************************************************************************
		// ***************************************Bug***************************************************************
		// *********************************************************************************************************
		// *********************************************************************************************************

		// begin draw ball 1-20 graph
		do {// loop around x axis
			i++;
			yStartPoint = (int) (150 - ballList.get(i).frequency + graphBuffer);
			if (yStartPoint < 95) {
				yStartPoint = 95;
			}
			do {// loop around y axis
				graphY -= 1; // bring y down by one
				if (yStartPoint < 130) {
					g.setColor(customGreen);
				}
				if (yStartPoint < 115) {
					g.setColor(customOrange);
				}
				if (yStartPoint < 96) {
					g.setColor(customRed);
				}
				g.drawLine(graphX, graphY, graphX + 30, graphY);
				g.setColor(Color.gray);
				if (yStartPoint < 96) {
					g.setColor(Color.green);
				}
				g.setFont(f2);
				if (ballList.get(i).frequency < 10000) {
					g.drawString(
							String.valueOf(NumberFormat.getNumberInstance(Locale.US).format(ballList.get(i).frequency)),
							graphX, 145);
				} else {
					if (ballList.get(i).value % 2 == 0) {
						g.drawString(
								String.valueOf(
										NumberFormat.getNumberInstance(Locale.US).format(ballList.get(i).frequency)),
								graphX, 185);
					} else {
						g.drawString(
								String.valueOf(
										NumberFormat.getNumberInstance(Locale.US).format(ballList.get(i).frequency)),
								graphX, 145);
					}
				}
				g.setFont(f1);
				g.setColor(Color.GREEN);
			} while (graphY > yStartPoint);
			graphX += 35;
			graphY = 150;
		} while (graphX < 1200);
		xPad = 20;
		// end draw ball 1-20 graph
		g.setColor(Color.WHITE);
		// draw the numbers to represent ball 20-40
		for (int j = 21; j < 41; ++j) {
			g.drawString(String.valueOf(j), 490 + xPad, 300);
			xPad += 35;
		}
		xPad = 20;
		graphX = 510;
		graphY = 280;
		// begin draw ball 20-40 graph
		do {// loop around x axis
			i++;
			yStartPoint = (int) (280 - ballList.get(i).frequency + graphBuffer);
			if (yStartPoint < 220) {
				yStartPoint = 220;
			}
			do {// loop around y axis
				graphY -= 1; // bring y down by one
				if (yStartPoint < 260) {
					g.setColor(customGreen);
				}
				if (yStartPoint < 255) {
					g.setColor(customOrange);
				}
				if (yStartPoint < 221) {
					g.setColor(customRed);
				}
				g.drawLine(graphX, graphY, graphX + 30, graphY);
				g.setColor(Color.gray);
				if (yStartPoint < 221) {
					g.setColor(Color.green);
				}
				g.setFont(f2);
				if (ballList.get(i).frequency < 10000) {
					g.drawString(
							String.valueOf(NumberFormat.getNumberInstance(Locale.US).format(ballList.get(i).frequency)),
							graphX, 275);
				} else {
					if (ballList.get(i).value % 2 == 0) {
						g.drawString(
								String.valueOf(
										NumberFormat.getNumberInstance(Locale.US).format(ballList.get(i).frequency)),
								graphX, 315);
					} else {
						g.drawString(
								String.valueOf(
										NumberFormat.getNumberInstance(Locale.US).format(ballList.get(i).frequency)),
								graphX, 275);
					}
				}
				g.setFont(f1);
				g.setColor(Color.GREEN);
			} while (graphY > yStartPoint);
			graphX += 35;
			graphY = 280;
		} while (graphX < 1200);
		// end draw ball 20-40 graph
		g.setColor(Color.WHITE);
		// draw the numbers to represent ball 40-60
		for (int j = 41; j < 61; ++j) {
			g.drawString(String.valueOf(j), 490 + xPad, 420);
			xPad += 35;
		}
		xPad = 20;
		graphX = 510;
		graphY = 400;
		// begin draw ball 40-60 graph
		do {// loop around x axis
			i++;
			yStartPoint = (int) (400 - ballList.get(i).frequency + graphBuffer);
			if (yStartPoint < 340) {
				yStartPoint = 340;
			}
			do {// loop around y axis
				graphY -= 1; // bring y down by one
				if (yStartPoint < 380) {
					g.setColor(customGreen);
				}
				if (yStartPoint < 365) {
					g.setColor(customOrange);
				}
				if (yStartPoint < 341) {
					g.setColor(customRed);
				}
				g.drawLine(graphX, graphY, graphX + 30, graphY);
				g.setColor(Color.gray);
				if (yStartPoint < 341) {
					g.setColor(Color.green);
				}
				g.setFont(f2);
				if (ballList.get(i).frequency < 10000) {
					g.drawString(
							String.valueOf(NumberFormat.getNumberInstance(Locale.US).format(ballList.get(i).frequency)),
							graphX, 395);
				} else {
					if (ballList.get(i).value % 2 == 0) {
						g.drawString(
								String.valueOf(
										NumberFormat.getNumberInstance(Locale.US).format(ballList.get(i).frequency)),
								graphX, 435);
					} else {
						g.drawString(
								String.valueOf(
										NumberFormat.getNumberInstance(Locale.US).format(ballList.get(i).frequency)),
								graphX, 395);
					}
				}
				g.setFont(f1);
				g.setColor(Color.GREEN);
			} while (graphY > yStartPoint);
			graphX += 35;
			graphY = 400;
		} while (graphX < 1200);
		// end draw ball 40-60 graph
		g.setColor(Color.WHITE);
		// draw the numbers to represent ball 60-80
		for (int j = 61; j < 81; ++j) {
			g.drawString(String.valueOf(j), 490 + xPad, 540);
			xPad += 35;
		}
		graphX = 510;
		graphY = 520;
		// begin draw ball 60-80 graph
		do {// loop around x axis
			i++;
			yStartPoint = (int) (520 - ballList.get(i).frequency + graphBuffer);
			if (yStartPoint < 460) {
				yStartPoint = 460;
			}
			do {// loop around y axis
				graphY -= 1; // bring y down by one
				if (yStartPoint < 500) {
					g.setColor(customGreen);
				}
				if (yStartPoint < 485) {
					g.setColor(customOrange);
				}
				if (yStartPoint < 461) {
					g.setColor(customRed);
				}
				g.drawLine(graphX, graphY, graphX + 30, graphY);
				g.setColor(Color.gray);
				if (yStartPoint < 461) {
					g.setColor(Color.green);
				}
				g.setFont(f2);
				if (ballList.get(i).frequency < 10000) {
					g.drawString(
							String.valueOf(NumberFormat.getNumberInstance(Locale.US).format(ballList.get(i).frequency)),
							graphX, 515);
				} else {
					if (ballList.get(i).value % 2 == 0) {
						g.drawString(
								String.valueOf(
										NumberFormat.getNumberInstance(Locale.US).format(ballList.get(i).frequency)),
								graphX, 558);
					} else {
						g.drawString(
								String.valueOf(
										NumberFormat.getNumberInstance(Locale.US).format(ballList.get(i).frequency)),
								graphX, 515);
					}
				}
				g.setFont(f1);
				g.setColor(Color.GREEN);
			} while (graphY > yStartPoint);
			graphX += 35;
			graphY = 520;
		} while (graphX < 1200);
		// end draw ball
	}

	class Ball {
		int value;
		int frequency;
	}

	class BtnListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {

			/*
			 * zero = 0; one = 0; two = 0; three = 0; four = 0; five = 0; six = 0; seven =
			 * 0; eight = 0; nine = 0; ten = 0;
			 */
			guessCountdown = 0;

			if (e.getSource().equals(startBtn)) {
				startGame(1);
			}
			if (e.getSource().equals(stopBtn)) {
				auto = false;
				stopGame();
			}
			if (e.getSource().equals(milliPlusBtn)) {
				if (miliDelay < 10) {
					miliDelay = 10;
				} else {
					miliDelay += 10;
				}

				delayField.setText("  " + String.valueOf(miliDelay));
			}
			if ((e.getSource().equals(milliMinusBtn))) {
				miliDelay -= 10;
				if (miliDelay < 10) {
					miliDelay = 2;
				}
				delayField.setText("  " + String.valueOf(miliDelay));
			}
			if (e.getSource().equals(autoGuessBtn)) {
				autoGuess += 1;
				if ((autoGuess % 2) == 0) {
					autoGuessBtn.setForeground(Color.green);
					autoGuessBtn.setText("Guess");
					grfBtn.setEnabled(true);
				} else {
					pickList.clear();
					autoGuessBtn.setForeground(Color.red);
					autoGuessBtn.setText("!Guess");
					grfBtn.setEnabled(false);
					int i = -1;
					do {
						i++;
						numBtnArray[i].setForeground(Color.white);
						numBtnArray[i].setBackground(Color.black);
					} while (i < 79);
					for (i = 0; i < 10; i++) {
						scoreBtnArray[i].setText(" ");
					}
				}
			}
			if (e.getSource().equals(grfBtn)) {
				grf += 1;
				if ((grf % 2) == 0) {
					grfBtn.setForeground(Color.green);
					grfBtn.setText("GRF");
				} else {
					pickList.clear();
					grfBtn.setForeground(Color.red);
					grfBtn.setText("!GRF");
					int i = -1;
					do {
						i++;
						int x = guessList.get(i).value;
						pickList.add(x);
					} while (i < 10);
				}
			}
			if (e.getSource().equals(autoBtn)) {
				toggleModes(1);
			}
			if (e.getSource().equals(manualBtn)) {
				toggleModes(0);
			}
			if (e.getSource().equals(drawBtn)) {
				System.out.println("Draw");
				startGame(0);
			}

			// repaint();
		}
	}

	class PickListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			zero = 0;
			one = 0;
			two = 0;
			three = 0;
			four = 0;
			five = 0;
			six = 0;
			seven = 0;
			eight = 0;
			nine = 0;
			ten = 0;

			if ((pickList.size() < 10) && (pickList.contains(Integer.valueOf(e.getActionCommand())) == false)) {
				pickList.add(Integer.valueOf((e.getActionCommand())));
				numBtnArray[Integer.valueOf(e.getActionCommand()) - 1].setBackground(customYellow);
				numBtnArray[Integer.valueOf(e.getActionCommand()) - 1].setForeground(Color.BLACK);
			} else {
				numBtnArray[Integer.valueOf(e.getActionCommand()) - 1].setBackground(Color.black);
				numBtnArray[Integer.valueOf(e.getActionCommand()) - 1].setForeground(Color.WHITE);
				int i = -1;
				do {
					i++;
					scoreBtnArray[i].setText("");
				} while (i < 10);
				pickList.remove(Integer.valueOf(e.getActionCommand()));
			}
			// System.out.println(pickList);
			repaint();
		}
	}
}