
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
	// Panel that will hold our check boxes
	JPanel checkBoxPanel;
	// Dimensions of canvas
	int canvasWidth = 600;
	int canvasHeight = 500;
	// Booleans to indicate whether check boxes have been clicked
	boolean showRectangle = false;
	boolean showSquare = false;
	boolean showCircle = false;

	@Override
	public void stateChanged(ChangeEvent e) 
	{
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		glcanvas.repaint();

	}

	@Override
	public void display(GLAutoDrawable drawable)
	{
		// TODO Auto-generated method stub
		// Variables to 
		int shapeWidth = (canvasWidth / 3) - 25;
		int shapeHeight = 100;
		int shapeTopCoord = 100;
		
		final GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT);

		gl.glPushMatrix();
		
		if(showRectangle == true)
		{
			float centerX = -0.75f;
			float centerY = 0.0f;
			float w = 0.15f;
			float h = 0.3f;
			
			gl.glBegin(GL2.GL_QUADS);
			gl.glColor3d(1.0, 0.0, 0.0);
			gl.glVertex3f(-w + centerX, h + centerY, 0.0f);
			
			gl.glColor3d(0.0, 1.0, 0.0);
			gl.glVertex3f( -w + centerX,  -h + centerY,  0.0f);
			
			gl.glColor3d(0.0, 0.0, 1.0);
			gl.glVertex3f(w + centerX, -h + centerY,  0.0f);
			
			gl.glColor3d(1.0, 1.0, 0.0);
			gl.glVertex3f(w + centerX, h + centerY,  0.0f);


			gl.glEnd();
			
		}
		
		if(showSquare == true)
		{
			// drawing the triangle
//			gl.glBegin(GL2.GL_TRIANGLES);
//			gl.glColor3d(1.0, 0.0, 0.0);
//			gl.glVertex3f(-0.50f, -0.50f, 0);
//			gl.glColor3d(0.0, 1.0, 0.0);
//			gl.glVertex3f(0.50f, -0.50f, 0);
//			gl.glColor3d(0.0, 0.0, 1.0);
//			gl.glVertex3f(0f, 0.50f, 0);
//			gl.glEnd();
			
			// Creates diamond
//			gl.glBegin(GL2.GL_POINTS);
//			gl.glVertex3f(0.5f, 0.0f, 0.0f); // right most point
//			gl.glVertex3f( 0.0f,  0.5f,  0.0f); // Upper point
//			gl.glVertex3f(0.0f, -0.5f,  0.0f); // Bottom pt
//			gl.glVertex3f(-0.5f, -0.0f,  0.0f); // Left pt
//			// End, don't comment next line!!!!!!!!
//			gl.glEnd();
			
			gl.glBegin(GL2.GL_QUADS);
			gl.glColor3d(1.0, 0.0, 0.0);
			gl.glVertex3f(-.25f, .25f, 0.0f); // right most point
			
			gl.glColor3d(0.0, 1.0, 0.0);
			gl.glVertex3f( -.25f,  -.25f,  0.0f); // Upper point
			
			gl.glColor3d(0.0, 0.0, 1.0);
			gl.glVertex3f(.25f, -.25f,  0.0f); // Bottom pt
			
			gl.glColor3d(1.0, 1.0, 0.0);
			gl.glVertex3f(.25f, .25f,  0.0f); // Left pt
			// End, don't comment next line!!!!!!!!
			gl.glEnd();
			
			int coordX = 10;
			int coordY = 10;
			
		}
		
		if(showCircle == true)
		{
			
			int triangles = 36;
			float radius = (float).3;
			float xCenter = (float)0.7;
			float yCenter = (float)0.0;
			float x, y;
			
			gl.glBegin(GL2.GL_TRIANGLE_FAN);
			gl.glVertex2f(xCenter, yCenter);
			for(int i =0; i <= triangles; i++)
			{
				float angle = (float) (2 * Math.PI * i / triangles);
				x = xCenter + radius * ((float)Math.cos(angle));
				y = yCenter + radius * (float)Math.sin(angle);
				gl.glVertex2f(x,y);
				
				// Coloring
				if(i%6 == 0)
			        gl.glColor3f(1.0f, 0.0f, 0.0f);

			    if (i%6 == 1)
			        gl.glColor3f(1.0f, 0.5f, 0.0f);

			    if (i%6 == 2)
			    	gl.glColor3f(1.0f, 1.0f, 0.0f);

			    if (i%6 == 3)
			    	gl.glColor3f(0.0f, 1.0f, 0.0f);

			    if (i%6 == 4)
			    	gl.glColor3f(0.0f, 0.0f, 1.0f);

			    if (i%6 == 5)
			    	gl.glColor3f(1.0f, 0.0f, 1.0f);
			}
			gl.glEnd(); 
		}
		gl.glFlush();
		gl.glPopMatrix();
		
		

		

	}

	@Override
	public void dispose(GLAutoDrawable drawable) 
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void init(GLAutoDrawable drawable) 
	{
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
		
		frame.setSize(600, 600);

		frame.setVisible(true);

	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, 
						int width, int height) 
	{
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
