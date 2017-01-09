
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

// Should draw a rectangle, square, and a circle
public class Shapes implements GLEventListener, ChangeListener 
{
	GLCanvas glcanvas;
	JPanel checkBoxPanel;
	int canvasWidth = 600;
	int canvasHeight = 500;
	
	boolean showRectangle = false;
	boolean showSquare = false;
	boolean showCircle = false;

	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		glcanvas.repaint();

	}

	@Override
	public void display(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
		// Use booleans!!!
		int shapeWidth = (canvasWidth / 3) - 25;
		int shapeHeight = 100;
		int shapeTopCoord = 100;
		
		final GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT);

		gl.glPushMatrix();
		
		if(showRectangle == true)
		{
			// drawing the triangle
			// Vertices for rectangle
			// Top left
			int rectVertex1x = 0;
			int rectVertex1y = shapeTopCoord;
			// Top right
			int rectVertex2x = rectVertex1x + shapeWidth;
			int rectVertex2y = shapeTopCoord;
			// Bottom left
			int rectVertex3x = 0;
			int rectVertex3y = shapeTopCoord + shapeHeight;
			// Bottom right
			int rectVertex4x = rectVertex2x;
			int rectVertex4y = rectVertex3y;
			
			
			
			gl.glBegin(GL2.GL_QUADS);
			gl.glColor3d(1.0, 0.0, 0.0);
			//gl.glVertex3i(rectVertex1x, rectVertex1y, 0);	// Top left vertex
			gl.glVertex3i(0, 0, 0);
			gl.glColor3d(0.0, 0.0, 1.0);
			//gl.glVertex3i(rectVertex3x, rectVertex3y, 0);	// Bottom left
			gl.glVertex3i(0, 100, 0);
			gl.glColor3d(1.0, 0.0, 1.0);
			//gl.glVertex3i(rectVertex4x, rectVertex4y, 0);	// Bottom right
			gl.glVertex3i(200, 100, 0);
			gl.glColor3d(0.0, 1.0, 0.0);
			//gl.glVertex3i(rectVertex2x, rectVertex2y, 0);	// Top right vertex
			gl.glVertex3i(200, 0, 0);


			gl.glEnd();
			
		}
		
		if(showSquare == true)
		{
			// drawing the triangle
			gl.glBegin(GL2.GL_TRIANGLES);
			gl.glColor3d(1.0, 0.0, 0.0);
			gl.glVertex3f(-0.50f, -0.50f, 0);
			gl.glColor3d(0.0, 1.0, 0.0);
			gl.glVertex3f(0.50f, -0.50f, 0);
			gl.glColor3d(0.0, 0.0, 1.0);
			gl.glVertex3f(0f, 0.50f, 0);
			gl.glEnd();
		}
		
		if(showCircle == true)
		{
			
		}
		gl.glFlush();
		gl.glPopMatrix();
		
		

		

	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub

	}

	public void init()
	{
		// getting the capabilities object of GL2 profile
		final GLProfile profile = GLProfile.get(GLProfile.GL2);

		GLCapabilities capabilities = new GLCapabilities(profile);
		glcanvas = new GLCanvas(capabilities);
		glcanvas.addGLEventListener(this);
		glcanvas.setSize(canvasWidth, canvasHeight);

		// creating frame
		final JFrame frame = new JFrame("Shapes");
		
		// Check box action
		CheckBoxAction boxAction = new CheckBoxAction();
		
		// Check Box panel
		checkBoxPanel = new JPanel();
		checkBoxPanel.setLayout(new FlowLayout());
		
		// Check boxes
		JCheckBox rectangleCheckBox = new JCheckBox("Show Rectangle");
		JCheckBox squareCheckBox = new JCheckBox("Show Square");
		JCheckBox circleCheckBox = new JCheckBox("Show Circle");
		// Adding check boxes to check box panel
		checkBoxPanel.add(rectangleCheckBox);
		checkBoxPanel.add(squareCheckBox);
		checkBoxPanel.add(circleCheckBox);
		
		// Adding boxes to listener
		rectangleCheckBox.addActionListener(boxAction);
		squareCheckBox.addActionListener(boxAction);
		circleCheckBox.addActionListener(boxAction);
		
		
		// Closing and disposing
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// adding canvas to frame
		frame.add(glcanvas);
		
		// Adding check box panel to bottom of frame
		frame.add(checkBoxPanel, BorderLayout.SOUTH);
		
		frame.setSize(840, 480);

		frame.setVisible(true);

	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		// TODO Auto-generated method stub

	}

	class CheckBoxAction implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			String cmd = e.getActionCommand();
			if (cmd.equals("Show Rectangle"))
			{
				showRectangle = !showRectangle;
			}

			if (cmd.equals( "Show Square"))
			{
				showSquare = !showSquare;
			}
			
			if(cmd.equals("Show Circle"))
			{
				showCircle = !showCircle;
			}
			
			// Repainting 
			glcanvas.repaint();
			//repaint();

		}
	}

	public static void main(String args[])
	{
		Shapes shapes = new Shapes();
		shapes.init();

	}


}
