package platformer;

import java.awt.Frame;
import java.awt.Window;

import javax.swing.JFrame;

@SuppressWarnings("unused")
public class Just_Jump {

	public static Display f = new Display();
	public static int width = 1000;
	public static int height = 600;

	public static void main(String[] args) {
		f = new Display();
		f.setSize(width, height);
		f.setResizable(true);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setTitle("...");
		f.setLocationRelativeTo(null);
	}

}
