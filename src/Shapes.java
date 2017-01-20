// Oscar Garcia-Telles
// CSS 451
// Program 1
//
// In this assignment, we create a simple GUI that allows us
// to display a rectangle, square, and/or circle. 

// NOTE: This program was pieced together using ideas from the
// SimpleTriangle.java and SwingFaceDemo.java example programs.

// The extra credit features were implemented. Buttons at the top of 
// the GUI were included to allow the placement of the shapes. 
// One click of the placement allows the user to select coordinates for 
// the shape. 

// Once the coordinates are chosen by clicking on the canvas,
// the shape will be rendered at that location (if the check box is checked). 
// If the shape's checkbox is not checked, the shape will appear in the
// selected coordinates when its box is checked. 

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

//import java.awt.event.MouseListener;

// Should draw a rectangle, square, and a circle
public class Shapes implements GLEventListener, ChangeListener, 
MouseListener
{
	GLCanvas glcanvas;
	// Panel that will hold our check boxes
	JPanel checkBoxPanel;

	// Dimensions of canvas
	int canvasWidth = 600;
	int canvasHeight = 600;

	// Booleans to indicate whether check boxes have been clicked
	boolean showRectangle = false;
	boolean showSquare = false;
	boolean showCircle = false;

	// Default location of shapes. Variables may be changed by user (Extra
	// credit feature).
	float rectangleCenterX = -0.75f;
	float rectangleCenterY = 0.0f;
	float squareCenterX = 0.0f;
	float squareCenterY = 0.0f;
	float circleCenterX = 0.7f;
	float circleCenterY = 0.0f;
	// Booleans to see if we're waiting for user to click a 
	// location for a shape (Extra credit feature)
	boolean waitingToPlaceRectangle = false;
	boolean waitingToPlaceSquare = false;
	boolean waitingToPlaceCircle = false;


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

		final GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT);

		gl.glPushMatrix();

		if(showRectangle == true)
		{
			float centerX = rectangleCenterX;
			float centerY = rectangleCenterY;
			// Dimensions of our rectangle
			float w = 0.15f;
			float h = 0.3f;

			// Creating rectangle
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

			float centerX = squareCenterX;
			float centerY = squareCenterY;
			// Square dimensions
			float w = 0.25f;
			float h = w;

			gl.glBegin(GL2.GL_QUADS);
			// Creating vertices and colors
			gl.glColor3d(1.0, 0.0, 0.0);
			gl.glVertex3f(-w + centerX, h + centerY, 0.0f);

			gl.glColor3d(0.0, 1.0, 0.0);
			gl.glVertex3f( -w + centerX,  -h + centerY,  0.0f);

			gl.glColor3d(0.0, 0.0, 1.0);
			gl.glVertex3f(w + centerX, -h + centerY,  0.0f); 

			gl.glColor3d(1.0, 1.0, 0.0);
			gl.glVertex3f(w + centerX, h + centerY,  0.0f); 
			// End, don't comment next line!!!!!!!!
			gl.glEnd();

		}

		if(showCircle == true)
		{

			int triangles = 36;
			float radius = (float).3;
			float xCenter = circleCenterX;
			float yCenter = circleCenterY;
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

	// Initializing our main elements.
	// First, the checkbox elements are created. Then, the extra credit 
	// features are created. 
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

		// Check Box panel to house our check boxes
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

		// Adding check boxes to listener
		rectangleCheckBox.addActionListener(boxAction);
		squareCheckBox.addActionListener(boxAction);
		circleCheckBox.addActionListener(boxAction);

		// ------------- EXTRA CREDIT: PLACING OUR SHAPES -----------
		// ------------- WHEREVER WE WANT                 -----------
		// Panel to house buttons that place the shapes
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		
		// Action listener for the buttons that 
		// enable us to place the shapes wherever we want
		PlaceShapeAction placeShapeAction = new PlaceShapeAction();
		// Buttons to place rectangle, square, circle
		JButton placeRectangleButton = new JButton("Set Rectangle Coord");
		JButton placeSquareButton = new JButton("Set Square Coord");
		JButton placeCircleButton = new JButton("Set Circle Coord");
		// Adding buttons to new panel
		buttonPanel.add(placeRectangleButton);
		buttonPanel.add(placeSquareButton);
		buttonPanel.add(placeCircleButton);
		// Enabling listener
		placeRectangleButton.addActionListener(placeShapeAction);
		placeSquareButton.addActionListener(placeShapeAction);
		placeCircleButton.addActionListener(placeShapeAction);
		
		// Mouse listener to get coordinates to place our shapes.
		glcanvas.addMouseListener((MouseListener) this);

		// Closing and disposing
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// adding canvas to frame
		frame.add(glcanvas);

		// Adding check box panel to bottom of frame
		frame.add(checkBoxPanel, BorderLayout.SOUTH);

		// Adding button panel
		frame.add(buttonPanel, BorderLayout.NORTH);

		frame.setSize(600, 600);

		frame.setVisible(true);

	}


	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, 
			int width, int height) 
	{
		// TODO Auto-generated method stub

	}

	// CheckBoxAction class Will change booleans that control whether we 
	// see our shapes or not.
	class CheckBoxAction implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			String cmd = e.getActionCommand();

			// Seeing if we checked a box
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

		}
	}

	// PlaceShapeAction class changes the boolean variables
	// that control the placement of our shapes by the user.
	// The mouse action listener will use these booleans
	// to determine of coordinates are to be captured to set
	// the coordinates of the shapes. 
	class PlaceShapeAction implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			String cmd = e.getActionCommand();

			// Now seeing if we're selecting a location for a 
			// shape. In case our user clicks different buttons
			// before clicking a proper coordinate, the other
			// two booleans are reset to false. 
			if(cmd.equals("Set Rectangle Coord"))
			{
				waitingToPlaceRectangle = true;
				waitingToPlaceSquare = false;
				waitingToPlaceCircle = false;
			}
			if(cmd.equals("Set Square Coord"))
			{
				waitingToPlaceRectangle = false;
				waitingToPlaceSquare = true;
				waitingToPlaceCircle = false;
			}
			if(cmd.equals("Set Circle Coord"))
			{
				waitingToPlaceRectangle = false;
				waitingToPlaceSquare = false;
				waitingToPlaceCircle = true;
			}

		}
	}

	// ------------------ Mouse Events ----------------
	@Override
	public void mouseClicked(MouseEvent e) {
		// First getting the mouse click coordinates
		int x = e.getX();
		int y = e.getY();
		// Changing coordinates into floats
		float xf = (float)( (x/299.0f) - 1.0f  );
		float yf = (float)( (-y/299.0f) + 1.0f);
		// Checking which button was pressed.
		if(waitingToPlaceRectangle)
		{
			waitingToPlaceRectangle = false;
			rectangleCenterX = xf;
			rectangleCenterY = yf;

		}

		if(waitingToPlaceSquare)
		{
			// Update center of square
			squareCenterX = xf;
			squareCenterY = yf;
			waitingToPlaceSquare = false;
		}

		if(waitingToPlaceCircle)
		{
			// Update center of circle
			circleCenterX = xf;
			circleCenterY = yf;
			waitingToPlaceCircle = false;

		}
		
		// Repainting 
		glcanvas.repaint();

		return;

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}


	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	// ------------------- Main -------------------
	public static void main(String args[])
	{
		Shapes shapes = new Shapes();
		shapes.init();

	}
}