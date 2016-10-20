package secur;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class window extends JFrame implements ActionListener {

	private JPanel contentPane;
	public static final int Encryption = 1;
	public static final int Decryption = 2;
	private Integer typeAction = Encryption;
	private Integer method = 1;
	private JTextArea textInp;
	private JTextArea textOut;
	JTextField textKey;
	JSpinner textShift;
	JPanel jp_status;

	/**
	 * Launch the application.
	 **/
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window frame = new window();
					frame.setTitle(TEXT.POLIBIY);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 **/
	public window() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		setMinimumSize(new Dimension(600, 450));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		this.setBackground(Color.LIGHT_GRAY);

		GridBagLayout grid = new GridBagLayout();
		contentPane.setLayout(grid);
		GridBagConstraints c = new GridBagConstraints();

		JMenuBar menuBar = new JMenuBar();
		JMenu menuFile = new JMenu();
		menuFile.setText(TEXT.FILE);
		JMenuItem itemOpen = new JMenuItem();
		itemOpen.setText(TEXT.OPEN);
		itemOpen.addActionListener(this);

		itemOpen.setName("open");
		itemOpen.setIcon(new ImageIcon("res/open.png"));
		menuFile.add(itemOpen);
		JMenuItem itemSave = new JMenuItem();
		itemSave.setText(TEXT.SAVE);
		itemSave.addActionListener(this);
		itemSave.setName("save");
		itemSave.setIcon(new ImageIcon("res/save.png"));
		menuFile.add(itemSave);
		JMenuItem itemExit = new JMenuItem();
		itemExit.setText(TEXT.EXIT);
		itemExit.addActionListener(this);
		itemExit.setName("exit");
		menuFile.add(itemExit);

		menuBar.add(menuFile);

		JMenu menuSquare = new JMenu();
		menuSquare.setText(TEXT.SQUARE);

		JMenuItem standartSquare = new JMenuItem();
		standartSquare.setText(TEXT.STANDARTSQUARE);
		standartSquare.addActionListener(this);
		standartSquare.setName("stsquare");
		menuSquare.add(standartSquare);

		JMenuItem currentSquare = new JMenuItem();
		currentSquare.setText(TEXT.CURRENTSQUARE);
		currentSquare.addActionListener(this);
		currentSquare.setName("currsquare");
		menuSquare.add(currentSquare);

		menuBar.add(menuSquare);
		setJMenuBar(menuBar);

		ButtonGroup typeGroup = new ButtonGroup();
		JRadioButton rButtonEnc = new JRadioButton(TEXT.ENCRYPT);
		rButtonEnc.setSelected(true);
		rButtonEnc.addActionListener(this);
		typeGroup.add(rButtonEnc);
		rButtonEnc.setName("encrypt");

		JRadioButton rButtonDec = new JRadioButton(TEXT.DECRYPT);
		rButtonDec.addActionListener(this);
		typeGroup.add(rButtonDec);
		rButtonDec.setName("decrypt");

		ButtonGroup methodGroup = new ButtonGroup();
		JRadioButton itemMethod1 = new JRadioButton();
		itemMethod1.setText(TEXT.METHOD1);
		methodGroup.add(itemMethod1);
		itemMethod1.setSelected(true);
		itemMethod1.addActionListener(this);
		itemMethod1.setName("method1");

		JRadioButton itemMethod2 = new JRadioButton();
		itemMethod2.setText(TEXT.METHOD2);
		methodGroup.add(itemMethod2);
		itemMethod2.addActionListener(this);
		itemMethod2.setName("method2");

		JRadioButton itemMethod3 = new JRadioButton();
		itemMethod3.setText(TEXT.METHOD3);
		methodGroup.add(itemMethod3);
		itemMethod3.addActionListener(this);
		itemMethod3.setName("method3");

		Box boxTypes = new Box(Box.WIDTH);
		boxTypes.setBorder(new TitledBorder(new LineBorder(Color.WHITE), TEXT.ACTION));
		boxTypes.add(rButtonEnc);
		boxTypes.add(rButtonDec);

		c.insets = new Insets(0, 10, 0, 0);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 2;
		getContentPane().add(boxTypes, c);

		Box boxMethods = new Box(Box.WIDTH);
		boxMethods.setBorder(new TitledBorder(new LineBorder(Color.WHITE), TEXT.METHOD));
		boxMethods.add(itemMethod1);
		boxMethods.add(itemMethod2);
		boxMethods.add(itemMethod3);

		c.insets = new Insets(0, 10, 0, 0);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 2;
		getContentPane().add(boxMethods, c);

		JCheckBox checkBoxSetKey = new JCheckBox(TEXT.USEKEY);
		checkBoxSetKey.addActionListener(this);
		checkBoxSetKey.setName("usekey");

		textKey = new JTextField();
		textKey.setEnabled(false);
		textKey.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				Polibiy.setKey(((JTextField) e.getSource()).getText());
			}

			@Override
			public void focusGained(FocusEvent e) {
			}
		});

		Box boxKey = new Box(Box.WIDTH);
		boxKey.setBorder(new TitledBorder(new LineBorder(Color.WHITE), TEXT.KEY));
		boxKey.add(checkBoxSetKey);
		boxKey.add(textKey);
		c.insets = new Insets(0, 10, 0, 0);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		getContentPane().add(boxKey, c);

		textShift = new JSpinner(new SpinnerNumberModel(new Integer(1), // value
				new Integer(1), // min
				new Integer(11), // max
				new Integer(1) // step
		));

		Box boxShift = new Box(Box.HEIGHT);
		boxShift.setBorder(new TitledBorder(new LineBorder(Color.WHITE), TEXT.SHIFT));
		boxShift.add(textShift);
		c.insets = new Insets(0, 10, 0, 0);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		getContentPane().add(boxShift, c);

		JButton buttonAction = new JButton(TEXT.BUTTON);
		buttonAction.addActionListener(this);
		buttonAction.setName("action");
		buttonAction.setSize(20, 20);
		c.insets = new Insets(5, 10, 10, 0);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.WEST;
		c.gridx = 3;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		getContentPane().add(buttonAction, c);

		JButton buttonCopy = new JButton(TEXT.BUTTONCOPY);
		buttonCopy.addActionListener(this);
		buttonCopy.setName("copy");
		buttonCopy.setSize(20, 20);
		c.insets = new Insets(5, 10, 10, 0);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.gridx = 3;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		getContentPane().add(buttonCopy, c);

		textInp = new JTextArea();
		textInp.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {
				if (method == 3) {
					textShift.setModel(new SpinnerNumberModel(new Integer((int) textShift.getValue()), // value
							new Integer(0), // min
							new Integer(textInp.getText().length()), // max
							new Integer(1) // step
					));
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
			}

			@Override
			public void keyPressed(KeyEvent arg0) {
			}
		});
		textInp.setSize(200, 200);
		textInp.setBorder(new LineBorder(Color.BLACK));
		textInp.setLineWrap(true);
		JScrollPane areaScrollPane = new JScrollPane(textInp);
		areaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		areaScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		areaScrollPane.setBorder(new TitledBorder(new LineBorder(Color.WHITE), TEXT.INPUT));

		textOut = new JTextArea();
		textOut.setSize(200, 200);
		textOut.setBorder(new LineBorder(Color.BLACK));
		textOut.setLineWrap(true);
		JScrollPane areaScrollPaneOut = new JScrollPane(textOut);
		areaScrollPaneOut.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		areaScrollPaneOut.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		areaScrollPaneOut.setBorder(new TitledBorder(new LineBorder(Color.WHITE), TEXT.RESULT));

		Box boxArea = new Box(Box.HEIGHT);
		boxArea.add(areaScrollPane);
		boxArea.add(areaScrollPaneOut);
		c.insets = new Insets(0, 10, 10, 10);
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 6;
		c.gridheight = 1;
		c.weightx = 1.0f;
		c.weighty = 1.0f;
		getContentPane().add(boxArea, c);

		jp_status = new JPanel();
		jp_status.setLayout(new BorderLayout());
		jp_status.add(new JLabel(new ImageIcon("res/information.png"), JLabel.LEFT), BorderLayout.CENTER);

		jp_status.setBorder(new BevelBorder(BevelBorder.LOWERED));

		c.insets = new Insets(0, 0, 0, 0);
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.SOUTH;
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 6;
		c.weightx = 1.0f;
		c.weighty = 0.005f;
		
		getContentPane().add(jp_status, c);

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		switch (((AbstractButton) arg0.getSource()).getName()) {
		case "action":
			new Thread(new Runnable() {
				public void run() {
					switch (method + 3 * (typeAction - 1)) {
					case 1:
						textOut.setText(Polibiy.methodFirstEncryption(textInp.getText(), (int) textShift.getValue()));
						break;
					case 2:
						textOut.setText(Polibiy.methodSecondEncryption(textInp.getText()));
						break;
					case 3:
						textOut.setText(Polibiy.methodThirdEncryption(textInp.getText(), (int) textShift.getValue()));
						break;
					case 4:
						textOut.setText(Polibiy.methodFirstDecryption(textInp.getText(), (int) textShift.getValue()));
						break;
					case 5:
						textOut.setText(Polibiy.methodSecondDecryption(textInp.getText()));
						break;
					case 6:
						textOut.setText(Polibiy.methodThirdDecryption(textInp.getText(), (int) textShift.getValue()));
						break;
					default:
						break;
					}
				}
			}).start();

			break;
		case "open":
			new Thread(new Runnable() {
				public void run() {

					JFileChooser fileopen = new JFileChooser();
					int ret = fileopen.showDialog(null, TEXT.FILE_OPEN);
					if (ret == JFileChooser.APPROVE_OPTION) {
						File file = fileopen.getSelectedFile();
						((JLabel) jp_status.getComponent(0)).setText(TEXT.FILE_OPEN_PROCESS + file.getPath());
						try {
							String text = read(file);
							textInp.setText(text);
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}
						((JLabel) jp_status.getComponent(0)).setText(TEXT.FILE_OPENED + file.getPath());
					}
				}
			}).start();
			break;
		case "save":
			new Thread(new Runnable() {
				public void run() {
					JFileChooser fileopen = new JFileChooser();
					int ret = fileopen.showDialog(null, TEXT.FILE_SAVE);
					if (ret == JFileChooser.APPROVE_OPTION) {
						File file = fileopen.getSelectedFile();
						((JLabel) jp_status.getComponent(0)).setText(TEXT.FILE_SAVE_PROCESS + file.getPath());
						write(file, textOut.getText());
						((JLabel) jp_status.getComponent(0)).setText(TEXT.FILE_SAVED + file.getPath());
					}

				}
			}).start();
			break;
		case "exit":
			System.exit(0);
			break;
		case "encrypt":
			typeAction = Encryption;
			break;
		case "decrypt":
			typeAction = Decryption;
			break;
		case "method1":
			method = 1;
			textShift.setEnabled(true);
			textShift.setModel(new SpinnerNumberModel(new Integer(1), // value
					new Integer(1), // min
					new Integer(11), // max
					new Integer(1) // step
			));
			break;
		case "method2":
			method = 2;
			textShift.setEnabled(false);
			break;
		case "method3":
			method = 3;
			textShift.setEnabled(true);
			textShift.setModel(new SpinnerNumberModel(new Integer(0), // value
					new Integer(0), // min
					new Integer(textInp.getText().length()), // max
					new Integer(1) // step
			));
			break;
		case "copy":
			textInp.setText(textOut.getText());
			textOut.setText("");
			break;
		case "usekey":
			JCheckBox box = (JCheckBox) arg0.getSource();
			textKey.setEnabled(box.isSelected());
			if (!box.isSelected()) {
				Polibiy.setKey("");
			}
			break;
		case "stsquare":
			showKey(TEXT.STANDARTSQUARE, Polibiy.getStandartSquare());
			break;
		case "currsquare":
			showKey(TEXT.CURRENTSQUARE, Polibiy.getSquare());
			break;
		default:
			break;
		}

	}

	public void showKey(String title, String key) {
		StringBuffer text = new StringBuffer();
		for (int i = 0; i < key.length(); i++) {
			text.append(key.charAt(i) + " ");
			if (i % 12 == 11) {
				text.append("\n");
			}
		}
		JOptionPane.showMessageDialog(null, text.toString(), title, JOptionPane.INFORMATION_MESSAGE);
	}

	public static String read(File file) throws FileNotFoundException {
		StringBuilder sb = new StringBuilder();

		try {
			BufferedReader in = new BufferedReader(new FileReader(file.getAbsoluteFile()));
			try {
				String s;

				while ((s = in.readLine()) != null) {
					sb.append(s);
					sb.append("\n");
				}
				sb.deleteCharAt(sb.length() - 1);
			} finally {
				in.close();
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, TEXT.FILE_ERROR, TEXT.FILE_ERROR_OPEN, JOptionPane.ERROR_MESSAGE);
		}
		return sb.toString();
	}

	public static void write(File file, String text) {

		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			PrintWriter out = new PrintWriter(file.getAbsoluteFile());
			try {
				out.print(text);
			} finally {
				out.close();
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, TEXT.FILE_ERROR, TEXT.FILE_ERROR_SAVE, JOptionPane.ERROR_MESSAGE);
		}
	}
}