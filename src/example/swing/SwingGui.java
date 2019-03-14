package example.swing;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class SwingGui extends JFrame {
	private static final long serialVersionUID = 1L;

	private JTextField textPlayer1;
	private JTextField textPlayer2;

	JComboBox<String> comboBoxP1;
	JComboBox<String> comboBoxP2;

	public String getPlayer1Name() {
		return textPlayer1.getText();
	}

	public String getPlayer2Name() {
		return textPlayer2.getText();
	}

	public String getPlayer1Icon() {
		return (String) comboBoxP1.getSelectedItem();
	}

	public String getPlayer2Icon() {
		return (String) comboBoxP2.getSelectedItem();
	}

	public SwingGui() {
		setPreferredSize(new Dimension(400, 300));
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		getContentPane().setLayout(null);

		JLabel lblAaaGame = new JLabel("AAA Game");
		lblAaaGame.setFont(new Font("Segoe UI Semibold", Font.BOLD, 17));
		lblAaaGame.setBounds(10, 11, 100, 14);
		getContentPane().add(lblAaaGame);

		JLabel lblPlayer1 = new JLabel("Player 1");
		lblPlayer1.setBounds(10, 64, 46, 14);
		getContentPane().add(lblPlayer1);

		textPlayer1 = new JTextField();
		textPlayer1.setText("");
		textPlayer1.setBounds(66, 61, 86, 20);
		getContentPane().add(textPlayer1);
		textPlayer1.setColumns(10);

		JLabel lblPlayer2 = new JLabel("Player 2");
		lblPlayer2.setBounds(199, 61, 46, 14);
		getContentPane().add(lblPlayer2);

		textPlayer2 = new JTextField();
		textPlayer2.setBounds(255, 58, 86, 20);
		getContentPane().add(textPlayer2);
		textPlayer2.setColumns(10);

		JButton btnFight = new JButton("Fight !");
		btnFight.setBounds(117, 228, 128, 23);
		getContentPane().add(btnFight);

		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(177, 11, 12, 198);
		getContentPane().add(separator);

		final String[] items = new String[] { "pepe.png", "pepe1.png" };
		comboBoxP1 = new JComboBox<>();
		comboBoxP1.setModel(new DefaultComboBoxModel<>(items));
		comboBoxP1.setBounds(66, 93, 86, 23);
		getContentPane().add(comboBoxP1);

		comboBoxP2 = new JComboBox<>();
		comboBoxP2.setModel(new DefaultComboBoxModel<String>(items));
		comboBoxP2.setBounds(255, 94, 86, 23);
		getContentPane().add(comboBoxP2);

		pack();
	}
}
