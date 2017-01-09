

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class SwingFaceDemo extends JFrame  {
	
	private static final int face_diameter = 200;
	private static final int x_face = 100;
	private static final int y_face = 100;
	private static final int eye_width = 20;
	private static final int x_right_eye = x_face + 55;
	private static final int y_right_eye = y_face + 60;
	private static final int x_left_eye = x_face + 130;
	private static final int y_left_eye = y_face + 60;
	Color faceColor = Color.green;
	JPanel buttonPanel;
	
	private boolean wink = false;
	
	
	
	public static void main(String[] args)
	{
		SwingFaceDemo drawing = new SwingFaceDemo();
		drawing.setVisible(true);
		
	}
	
	public SwingFaceDemo()
	{
		super("Graphics Demo");
		setSize(400, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setBackground(Color.white);
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		
		JButton winkButton = new JButton("wink");
		WinkAction winkAction = new WinkAction();
		winkButton.addActionListener(winkAction);
		buttonPanel.add(winkButton);
		
		JButton colorButton = new JButton("Choose Color");
		colorButton.addActionListener(winkAction);
		buttonPanel.add(colorButton);
		
		add(buttonPanel, BorderLayout.SOUTH);
		
	}

	public void paint(Graphics g)
	{
		super.paint(g);
		g.setColor(faceColor);
		g.fillOval(100, 100, 200, 200);
		g.setColor(Color.black);
		if (!wink)
			g.fillOval(x_right_eye, y_right_eye, eye_width+5, 15 );
		else
			g.drawLine(x_right_eye, y_right_eye, x_right_eye+eye_width, y_right_eye );
		
		g.fillOval(x_left_eye, y_left_eye, eye_width+5, 15 );
		
		g.drawLine(x_face + 50,  y_face + 150, x_face+50+100, y_face + 150);
	}
	
	class WinkAction implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			String cmd = e.getActionCommand();
			if (cmd.equals("wink"))
			{
				wink = !wink;
			}
			
			if (e.getActionCommand().equals( "Choose Color"))
			{
				Color color = JColorChooser.showDialog(null,  "JCOLORCHOOSER", faceColor);
				if (color != null) // a color was chosen
				{
					faceColor = color;
				}
			}
			repaint();
			
		}
	}
}
