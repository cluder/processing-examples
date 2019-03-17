package example.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

class SwingGui extends JFrame {
	private static final long serialVersionUID = 1L;

	private JTextField textPlayer;

	JComboBox<String> comboBox;
	Color bgColor = Color.BLACK;

	public String getPlayerName() {
		return textPlayer.getText();
	}

	public String getPlayerIcon() {
		return (String) comboBox.getSelectedItem();
	}

	public Color getBgColor() {
		return bgColor;
	}

	public SwingGui() {
		setPreferredSize(new Dimension(230, 230));
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		getContentPane().setLayout(null);

		JLabel lblAaaGame = new JLabel("Settings");
		lblAaaGame.setFont(new Font("Segoe UI Semibold", Font.BOLD, 17));
		lblAaaGame.setBounds(10, 11, 100, 35);
		getContentPane().add(lblAaaGame);

		JLabel lblPlayer = new JLabel("Player 1");
		lblPlayer.setBounds(10, 64, 46, 14);
		getContentPane().add(lblPlayer);

		textPlayer = new JTextField();
		textPlayer.setText("");
		textPlayer.setBounds(65, 57, 109, 20);
		getContentPane().add(textPlayer);
		textPlayer.setColumns(10);

		final String[] items = new String[] { "pepe.png", "pepe1.png" };
		comboBox = new JComboBox<>();
		comboBox.setModel(new DefaultComboBoxModel<>(items));
		comboBox.setBounds(65, 89, 109, 23);
		getContentPane().add(comboBox);

		JButton btnPickColor = new JButton("Change background color");
		btnPickColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color col = JColorChooser.showDialog(btnPickColor, "Choose background colro", Color.black);
				if (col != null) {
					bgColor = col;
				}
			}
		});
		btnPickColor.setBounds(10, 135, 194, 35);
		getContentPane().add(btnPickColor);

		pack();
	}
}
