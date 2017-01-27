// Oscar Garcia-Telles
// 26 January 2017
// CSS 451 Program 1

// NOTE: the code for this program was based on the Quad2Animation.java 
// code that was provided. 

// This program draws 3 squares and 4 circles. Selecting a shape with 
// its radio button will start an animation. The shape will move
// on the canvas.

// The major modifications include the addition of radio button functionality, 
// and the implementation of the drawSquares(), drawCircles(), setSquareCoord(),
// setCircleCoord(), and calculateCoordinates() methods. 

package program2;
import java.awt.*;
import java.awt.event.*;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.fixedfunc.*;
import com.jogamp.opengl.glu.GLU;
//import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.FPSAnimator;

public class SquaresAndCirclesAnimation implements GLEventListener {
	private static String TITLE = "Multi View";
	private static final int CANVAS_WIDTH = 640;
	private static final int CANVAS_HEIGHT = 480;
	private static final int FPS = 60;
	private String canvasName;

	// Controls whether we translate shape or not
	static boolean animate = false;	
	// Units to move a shape in animation
	float translateFactor = 0.03f; 

	// Default coordinates of the centers of our shapes.
	// Shapes should be on different, parallel planes
	float redSquareX = -0.75f;
	float redSquareY = 0.0f;
	float redSquareZ = -0.03f;

	float greenSquareX = -0.6f; 
	float greenSquareY = 0.0f;
	float greenSquareZ = -0.02f;

	float blueSquareX =  -0.20f;
	float blueSquareY = 0.0f;
	float blueSquareZ = -0.01f;

	// Default coord for the centers of our circles
	float redCircleX = 0.0f;
	float redCircleY = 0.75f;
	float redCircleZ = -0.20f;

	float greenCircleX = 0.0f;
	float greenCircleY = 0.6f;
	float greenCircleZ = -0.1f;

	float blueCircleX = 0.0f;
	float blueCircleY = 0.45f;
	float blueCircleZ = 0.0f;

	float yellowCircleX = 0.0f;
	float yellowCircleY = 0.30f;
	float yellowCircleZ = 0.1f;


	// Booleans to control our shapes
	static boolean moveRedSquare = false;
	static boolean moveGreenSquare = false;
	static boolean moveBlueSquare = false;
	static boolean moveRedCircle = false;
	static boolean moveBlueCircle = false;
	static boolean moveGreenCircle = false;
	static boolean moveYellowCircle = false;

	// Booleans to check what direction they're moving
	boolean blueSquareIncr = true;
	boolean greenSquareIncr = true;
	boolean redSquareIncr = true;
	boolean blueCircleIncr = true;
	boolean greenCircleIncr = true;
	boolean redCircleIncr = true;
	boolean yellowCircleIncr = true;

	static GLCanvas upperCanvas;

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {

			public void run() {

				// Create my two OpenGL Canvas
				upperCanvas = new GLCanvas();

				GLCanvas lowerCanvas = new GLCanvas();
				upperCanvas.addGLEventListener(new SquaresAndCirclesAnimation("upperCanvas"));
				lowerCanvas.addGLEventListener(new SquaresAndCirclesAnimation("lowerCanvas"));

				upperCanvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
				lowerCanvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));

				final FPSAnimator upperCanvasAnimator = new FPSAnimator(upperCanvas, FPS, true);
				final FPSAnimator lowerCanvasAnimator = new FPSAnimator(lowerCanvas, FPS, true);

				final JFrame frame = new JFrame();


				// Adding the canvases. Will be drawn one above the other 
				// because I just want to print messages in console.
				frame.getContentPane().setLayout(new GridLayout(2, 2));

				// Radio buttons to select a shape
				JPanel radioButtonPanel = new JPanel();
				radioButtonPanel.setLayout(new GridLayout(8, 1));
				// Radio buttons
				JRadioButton redSquare = new JRadioButton("Red Square");
				JRadioButton greenSquare = new JRadioButton("Green Square");
				JRadioButton blueSquare = new JRadioButton("Blue Square");
				// Circles
				JRadioButton redCircle = new JRadioButton("Red Circle");
				JRadioButton greenCircle = new JRadioButton("Green Circle");
				JRadioButton blueCircle = new JRadioButton("Blue Circle");
				JRadioButton yellowCircle = new JRadioButton("Yellow Circle");
				
				// Labeling panel
				JLabel radioButtonPanelLabel = new JLabel("Select a Shape"); 
				radioButtonPanel.add(radioButtonPanelLabel);
				
				// Adding radio buttons to panel
				radioButtonPanel.add(redSquare);
				radioButtonPanel.add(greenSquare);
				radioButtonPanel.add(blueSquare);
				radioButtonPanel.add(redCircle);
				radioButtonPanel.add(greenCircle);
				radioButtonPanel.add(blueCircle);
				radioButtonPanel.add(yellowCircle);
				// Grouping radio buttons
				ButtonGroup radioButtonGroup = new ButtonGroup();
				radioButtonGroup.add(redSquare);
				radioButtonGroup.add(greenSquare);
				radioButtonGroup.add(blueSquare);
				radioButtonGroup.add(redCircle);
				radioButtonGroup.add(greenCircle);
				radioButtonGroup.add(blueCircle);
				radioButtonGroup.add(yellowCircle);
				

				// Implementing action listeners for radio buttons.
				// Since there's no instance of SquaresAndCirclesAnimation.java,
				// the action listeners must be set up in the following manner.
				redSquare.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						animate = true;
						moveRedSquare = true;
						moveGreenSquare = false;
						moveBlueSquare = false;
						moveRedCircle = false;
						moveBlueCircle = false;
						moveGreenCircle = false;
						moveYellowCircle = false;
					}

				});

				blueSquare.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						animate = true;
						moveRedSquare = false;
						moveGreenSquare = false;
						moveBlueSquare = true;
						moveRedCircle = false;
						moveBlueCircle = false;
						moveGreenCircle = false;
						moveYellowCircle = false;
					}

				});

				greenSquare.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						animate = true;
						moveRedSquare = false;
						moveGreenSquare = true;
						moveBlueSquare = false;
						moveRedCircle = false;
						moveBlueCircle = false;
						moveGreenCircle = false;
						moveYellowCircle = false;
					}

				});

				// ----------------- Circles ------------------

				// Green Circle
				greenCircle.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						animate = true;
						moveRedSquare = false;
						moveGreenSquare = false;
						moveBlueSquare = false;
						moveRedCircle = false;
						moveBlueCircle = false;
						moveGreenCircle = true;
						moveYellowCircle = false;
					}

				});

				// Blue Circle
				blueCircle.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						animate = true;
						moveRedSquare = false;
						moveGreenSquare = false;
						moveBlueSquare = false;
						moveRedCircle = false;
						moveBlueCircle = true;
						moveGreenCircle = false;
						moveYellowCircle = false;
					}

				});

				// Red Circle
				redCircle.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						animate = true;
						moveRedSquare = false;
						moveGreenSquare = false;
						moveBlueSquare = false;
						moveRedCircle = true;
						moveBlueCircle = false;
						moveGreenCircle = false;
						moveYellowCircle = false;
					}

				});

				// Yellow Circle
				yellowCircle.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						animate = true;
						moveRedSquare = false;
						moveGreenSquare = false;
						moveBlueSquare = false;
						moveRedCircle = false;
						moveBlueCircle = false;
						moveGreenCircle = false;
						moveYellowCircle = true;
					}

				});


				// Partitioning window into 4 sections
				JPanel upperRight = new JPanel();
				upperRight.setLayout(new FlowLayout());

				upperRight.add(radioButtonPanel);

				JPanel lowerLeft = new JPanel();
				frame.getContentPane().add(upperCanvas);
				//frame.getContentPane().add(radioButtonPanel);
				frame.getContentPane().add(upperRight);
				frame.getContentPane().add(lowerLeft);
				frame.getContentPane().add(lowerCanvas);


				frame.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosing(WindowEvent e) {

						new Thread() {
							@Override
							public void run() {
								// stop the world
								if (upperCanvasAnimator.isStarted() || lowerCanvasAnimator.isStarted()){
									upperCanvasAnimator.stop();
									lowerCanvasAnimator.stop();
								}
								System.exit(0);
							}
						}.start();
					}
				});
				frame.setTitle(TITLE);
				frame.pack();
				frame.setVisible(true);

				// Should start my two canvas
				upperCanvasAnimator.start();// Should call init method for upperCanvas, am I right?
				lowerCanvasAnimator.start();// Should call init method for lowerCanvas, am I right?
			} /////////// END OF PUBLIC VOID RUN()
		}); ///////// END OF SWINGUTILITIES....()
	}


	private GLU glu;  // for the GL Utility

	public SquaresAndCirclesAnimation(String name)
	{
		canvasName = name;
	}


	// I've modified this method for the different perspectives 
	public void init(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();   
		glu = GLU.createGLU(gl);
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		gl.glClearDepth(1.0f);
		gl.glEnable(GL.GL_DEPTH_TEST);

		// eye, center, up
		if (this.toString().equals("upperCanvas"))
		{
			glu.gluLookAt(-1.5, 1.5, 2, 0, 0, 0, 0, 1, 0);
		}
		else {
			glu.gluLookAt(1.5, 1.5, -1, 0, 0, 0, 0, 1, 0);
		}
	}

	// Called once for each canvas 
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		GL2 gl = drawable.getGL().getGL2();

		if (height == 0) height = 1;
		float aspect = (float)width / height;

		gl.glViewport(0, 0, width, height);

		gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
		gl.glLoadIdentity();
		if (this.toString().equals("upperCanvas"))
		{
			glu.gluPerspective(45.0, aspect, 0.1, 10.0);
			//System.out.println(this.canvasName);
		}
		else {
			glu.gluPerspective(50.0, aspect, 0.1, 10.0);

			//System.out.println(this.canvasName);
		}

	}

	// Calls drawSquares() and drawCircles() for the animation
	public void display(GLAutoDrawable drawable) {

		GL2 gl = drawable.getGL().getGL2();
		
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT); 
		gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);					
          
		// Animating. Once any radio button is clicked, 
		// the animation starts.
		if(animate == true)
		{
			// Drawing our figures and updating their coordinates
			drawSquares(gl, translateFactor);
			drawCircles(gl, translateFactor);
			drawAxes(gl);
		}
		// Else the user hasn't clicked a radio button..
		else
		{
			drawSquares(gl, 0); // Translation of 0
			drawCircles(gl, 0);
			drawAxes(gl);
		}

		gl.glFlush();


	}

	public void dispose(GLAutoDrawable drawable) { }

	@Override
	public String toString() {
		return this.canvasName;
	}


	// t parameter is the translation factor for the animation.
	private void drawSquares(GL2 gl, float t)
	{
		// Size of our squares
		float size = 0.15f;
		// First updating square coordinates
		setSquareCoordinates(t);

		// Creating our squares now

		// -------------- Blue Square ------------------
		gl.glBegin(GL2.GL_QUADS); 
		gl.glColor3f( 0f,0f,1f ); //blue color
		// Vertices in the order top right, top left, bott left, bott right
		gl.glVertex3f( blueSquareX + size, blueSquareY + size, blueSquareZ);
		gl.glVertex3f( blueSquareX - size, blueSquareY + size, blueSquareZ);
		gl.glVertex3f( blueSquareX - size, blueSquareY - size, blueSquareZ ); 
		gl.glVertex3f( blueSquareX + size, blueSquareY - size, blueSquareZ ); 
		gl.glEnd(); // Done Drawing The Quad

		// ------------- Green Square -------------
		gl.glBegin(GL2.GL_QUADS); // Start Drawing The Cube
		gl.glColor3f( 0f,1f,0f ); //blue color
		// Vertices in the order top right, top left, bott left, bott right
		gl.glVertex3f( greenSquareX + size, greenSquareY + size, greenSquareZ);
		gl.glVertex3f( greenSquareX - size, greenSquareY + size, greenSquareZ);
		gl.glVertex3f( greenSquareX - size, greenSquareY - size, greenSquareZ ); 
		gl.glVertex3f( greenSquareX + size, greenSquareY - size, greenSquareZ ); 
		gl.glEnd(); // Done Drawing The Quad

		// ------------- Red Square -------------
		gl.glBegin(GL2.GL_QUADS); // Start Drawing The Cube
		gl.glColor3f( 1f,0f,0f ); //blue color
		// Vertices in the order top right, top left, bott left, bott right
		gl.glVertex3f( redSquareX + size, redSquareY + size, redSquareZ);
		gl.glVertex3f( redSquareX - size, redSquareY + size, redSquareZ);
		gl.glVertex3f( redSquareX - size, redSquareY - size, redSquareZ ); 
		gl.glVertex3f( redSquareX + size, redSquareY - size, redSquareZ ); 
		gl.glEnd(); // Done Drawing The Quad
	}

	// t parameter is the amount the shape will be moved in each frame
	private void drawCircles(GL2 gl, float t)
	{
		// First updating circle coordinates
		setCircleCoordinates(t);


		// Drawing circles
		int triangles = 36;
		float radius = (float).2;
		float x, y;

		// ------------  Red Circle  -----------------
		gl.glBegin(GL2.GL_TRIANGLE_FAN);
		gl.glColor3f( 1f,0f,0f ); //red color
		gl.glVertex3f(redCircleX, redCircleY, redCircleZ);
		for(int i =0; i <= triangles; i++)
		{
			float angle = (float) (2 * Math.PI * i / triangles);
			x = redCircleX + radius * ((float)Math.cos(angle));
			y = redCircleY + radius * (float)Math.sin(angle);
			gl.glColor3f( 1f,0f,0f ); //red color
			gl.glVertex3f(x,y, redCircleZ);

		}
		gl.glEnd(); 

		// ------------- Blue circle  ---------------
		gl.glBegin(GL2.GL_TRIANGLE_FAN);
		gl.glColor3f( 0f,0f,1f ); //blue color
		gl.glVertex3f(blueCircleX, blueCircleY, blueCircleZ);
		for(int i =0; i <= triangles; i++)
		{
			float angle = (float) (2 * Math.PI * i / triangles);
			x = blueCircleX + radius * ((float)Math.cos(angle));
			y = blueCircleY + radius * (float)Math.sin(angle);
			gl.glColor3f( 0f,0f,1f ); //blue color
			gl.glVertex3f(x,y, blueCircleZ);

		}
		gl.glEnd();

		// ------------- Green circle  ---------------
		gl.glBegin(GL2.GL_TRIANGLE_FAN);
		gl.glColor3f( 0f,1f,0f ); //green color
		gl.glVertex3f(greenCircleX, greenCircleY, greenCircleZ);
		for(int i =0; i <= triangles; i++)
		{
			float angle = (float) (2 * Math.PI * i / triangles);
			x = greenCircleX + radius * ((float)Math.cos(angle));
			y = greenCircleY + radius * (float)Math.sin(angle);
			gl.glColor3f( 0f,1f,0f ); //green color
			gl.glVertex3f(x,y, greenCircleZ);

		}
		gl.glEnd();

		// ------------- Yellow circle  ---------------
		gl.glBegin(GL2.GL_TRIANGLE_FAN);
		gl.glColor3f( 1f,1f,0f ); //yellow color
		gl.glVertex3f(yellowCircleX, yellowCircleY, yellowCircleZ);
		for(int i =0; i <= triangles; i++)
		{
			float angle = (float) (2 * Math.PI * i / triangles);
			x = yellowCircleX + radius * ((float)Math.cos(angle));
			y = yellowCircleY + radius * (float)Math.sin(angle);
			gl.glColor3f( 1f,1f,0f ); //yellow color
			gl.glVertex3f(x,y, yellowCircleZ);

		}
		gl.glEnd();
	}


	// Method will update the coordinates of our squares, 
	// parameter t is the float amount of translation (could be negative)
	private void setSquareCoordinates(float t)
	{
		// Updating coordinates for the shape being moved
		if(moveBlueSquare)
		{
			float prev = blueSquareX;
			// If increasing, will add translation factor
			if(blueSquareIncr)
			{
				blueSquareX = calculateCoord(blueSquareX, t, blueSquareIncr);
			}
			// If in decreasing direction, will continue to subtract
			else
			{
				blueSquareX = calculateCoord(blueSquareX, -t, blueSquareIncr);
			}

			// Will need to check if we've reached a boundary and need 
			// to change direction
			if(blueSquareX - prev > 0)
			{
				blueSquareIncr = true;
			}
			else
			{
				blueSquareIncr = false;
			}
		}

		// ------------ Green Square ------------
		if(moveGreenSquare)
		{
			float prev = greenSquareX;
			if(greenSquareIncr)
			{
				greenSquareX = calculateCoord(greenSquareX, t, greenSquareIncr);
			}
			else
			{
				greenSquareX = calculateCoord(greenSquareX, -t, greenSquareIncr);
			}

			if(greenSquareX - prev > 0)
			{
				greenSquareIncr = true;
			}
			else
			{
				greenSquareIncr = false;
			}
		}

		// ------------ Red Square ---------------
		if(moveRedSquare)
		{
			float prev = redSquareX;
			if(redSquareIncr)
			{
				redSquareX = calculateCoord(redSquareX, t, redSquareIncr);
			}
			else
			{
				redSquareX = calculateCoord(redSquareX, -t, redSquareIncr);
			}

			if(redSquareX - prev > 0)
			{
				redSquareIncr = true;
			}
			else
			{
				redSquareIncr = false;
			}
		}

	}

	// Updates the coordinates of our circles
	private void setCircleCoordinates(float t)
	{
		// Updating coordinates for the shape being moved

		// ----------- Blue Circle --------------
		if(moveBlueCircle)
		{
			float currentCoord = blueCircleY;
			float prev = currentCoord;
			if(blueCircleIncr)
			{
				blueCircleY = calculateCoord(blueCircleY, t, blueCircleIncr);
			}
			else
			{
				blueCircleY = calculateCoord(blueCircleY, -t, blueCircleIncr);
			}

			if(blueCircleY - prev > 0)
			{
				blueCircleIncr = true;
			}
			else
			{
				blueCircleIncr = false;
			}
		}

		// ----------- Green Circle --------------
		if(moveGreenCircle)
		{
			float currentCoord = greenCircleY;
			boolean currentShapeIncr = greenCircleIncr;
			float prev = currentCoord;
			if(currentShapeIncr)
			{
				greenCircleY = calculateCoord(currentCoord, t, currentShapeIncr);
			}
			else
			{
				greenCircleY = calculateCoord(currentCoord, -t, currentShapeIncr);
			}

			if(greenCircleY - prev > 0)
			{
				greenCircleIncr = true;
			}
			else
			{
				greenCircleIncr = false;
			}
		}

		// ----------- Red Circle --------------
		if(moveRedCircle)
		{
			float currentCoord = redCircleY;
			boolean currentShapeIncr = redCircleIncr;
			float prev = currentCoord;
			if(currentShapeIncr)
			{
				redCircleY = calculateCoord(currentCoord, t, currentShapeIncr);
			}
			else
			{
				redCircleY = calculateCoord(currentCoord, -t, currentShapeIncr);
			}

			if(redCircleY - prev > 0)
			{
				redCircleIncr = true;
			}
			else
			{
				redCircleIncr = false;
			}
		}

		// ----------- Yellow Circle --------------
		if(moveYellowCircle)
		{
			float currentCoord = yellowCircleY;
			boolean currentShapeIncr = yellowCircleIncr;
			float prev = currentCoord;
			if(currentShapeIncr)
			{
				yellowCircleY = calculateCoord(currentCoord, t, currentShapeIncr);
			}
			else
			{
				yellowCircleY = calculateCoord(currentCoord, -t, currentShapeIncr);
			}

			if(yellowCircleY - prev > 0)
			{
				yellowCircleIncr = true;
			}
			else
			{
				yellowCircleIncr = false;
			}
		}


	} // end of setCircleCoord

	// This method checks of we're at a boundary or not and returns
	// the appropriate updated coordinate
	private float calculateCoord(float currentCoord, float t, boolean incr)
	{
		float increasedVal = currentCoord + t; // t could be negative!!!

		float coordinateBoundary = 1f;

		// Coordinate increasing and reached positive boundary
		if(incr && increasedVal > coordinateBoundary)
		{
			return currentCoord - t;
		}

		// Coordinate decreasing and reached negative boundary
		if(!incr && increasedVal < -coordinateBoundary)
		{
			// If it is decreasing, t will be negative, so must subtract
			// to start increasing
			return currentCoord - t;
		}

		// Else, we are not at the boundary. 
		// We can just add t, which could be pos or neg
		return currentCoord + t;
	}




	// We will show our X, Y, Z axis
	private void drawAxes(GL2 gl)
	{
		gl.glBegin(GL.GL_LINES);
		// ------------- Blue x axis -------------
		gl.glColor3d(0, 0.5, 1); 
		gl.glVertex3f(-1.0f, 0.0f, 0.0f);
		gl.glVertex3f(1.0f, 0.0f, 0.0f);
		// Arrow for x axis
		gl.glVertex3f(1.0f, 0.0f, 0.0f);
		gl.glVertex3f(0.7f, 0.3f, 0.0f);

		gl.glVertex3f(1.0f, 0.0f, 0.0f);
		gl.glVertex3f(0.7f, -0.3f, 0.0f);

		// ---------- Green y axis -------------
		gl.glColor3d(0.2, 1, 0.2); 
		gl.glVertex3f(0.0f, -1.0f, 0.0f);
		gl.glVertex3f(0.0f, 1.0f, 0.0f);
		// Arrow for x axis
		gl.glVertex3f(0.0f, 1.0f, 0.0f);
		gl.glVertex3f(0.3f, 0.7f, 0.0f);

		gl.glVertex3f(0.0f, 1.0f, 0.0f);
		gl.glVertex3f(-0.3f, 0.7f, 0.0f);

		// ---------- Red z axis -------------
		gl.glColor3d(1, 0.3, 0.2); 
		gl.glVertex3f(0.0f, 0.0f, -1.0f);
		gl.glVertex3f(0.0f, 0.0f, 1.0f);
		// Arrow for z axis
		gl.glVertex3f(0.0f, 0.0f, 1.0f);
		gl.glVertex3f(0.0f, 0.3f, 0.7f);

		gl.glVertex3f(0.0f, 0.0f, 1.0f);
		gl.glVertex3f(0.0f, -0.3f, 0.7f);

		// Done adding axes
		gl.glEnd();

	}

} // End of outermost class

