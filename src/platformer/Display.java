package platformer;

import java.awt.GridLayout;

import javax.swing.JFrame;

public class Display extends JFrame {

	private static final long serialVersionUID = 170193057820449579L;
	public Main panel;

	public Display() {
		panel = new Main(this);
		setLayout(new GridLayout(1, 1, 0, 0));
		add(panel);
	}

	public static void main(String args[]) {

	}
}
