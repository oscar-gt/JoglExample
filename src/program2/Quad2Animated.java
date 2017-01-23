package program2;
import java.awt.*;
import java.awt.event.*;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicArrowButton;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.fixedfunc.*;
import com.jogamp.opengl.glu.GLU;
//import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.FPSAnimator;

public class Quad2Animated implements GLEventListener, MouseListener {
	private static String TITLE = "Multi View";
	private static final int CANVAS_WIDTH = 640;
	private static final int CANVAS_HEIGHT = 480;
	private static final int FPS = 30;
	private String canvasName;

	int numberOfSquares = 3;
	int numberOfCircles = 4;

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
	boolean moveRedSquare = false;
	boolean moveGreenSquare = false;
	boolean moveBlueSquare = false;
	boolean moveRedCircle = false;
	boolean moveBlueCircle = false;
	boolean moveGreenCircle = false;
	boolean moveYellowCircle = false;

	static GLCanvas upperCanvas;




	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {

			public void run() {

				// Create my two OpenGL Canvas
				upperCanvas = new GLCanvas();

				GLCanvas lowerCanvas = new GLCanvas();
				upperCanvas.addGLEventListener(new Quad2Animated("upperCanvas"));
				lowerCanvas.addGLEventListener(new Quad2Animated("lowerCanvas"));

				upperCanvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
				lowerCanvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));

				final FPSAnimator upperCanvasAnimator = new FPSAnimator(upperCanvas, FPS, true);
				final FPSAnimator lowerCanvasAnimator = new FPSAnimator(lowerCanvas, FPS, true);

				final JFrame frame = new JFrame();


				// Adding the canvases. Will be drawn one above the other because I just want to print messages in console.
				frame.getContentPane().setLayout(new GridLayout(2, 2));
				//////////////// MAKE UPPER RIGHT HAVE RADIO BUTTONS
				JPanel radioButtonPanel = new JPanel();
				radioButtonPanel.setLayout(new FlowLayout());
				// Radio buttons
				JRadioButton redSquare = new JRadioButton("Red Square");
				JRadioButton greenSquare = new JRadioButton("Green Square");
				JRadioButton blueSquare = new JRadioButton("Blue Square");
				// Circles
				JRadioButton redCircle = new JRadioButton("Red Circle");
				JRadioButton greenCircle = new JRadioButton("Green Circle");
				JRadioButton blueCircle = new JRadioButton("Blue Circle");
				JRadioButton yellowCircle = new JRadioButton("Yellow Circle");
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

				//////////	Adding radio buttons to action listener.
				// Need the next line to be able to use action listener
				Quad2Animated dummyInstance = new Quad2Animated("Outer");
				RadioButtonAction radioAction = dummyInstance.new RadioButtonAction();
				redSquare.addActionListener(radioAction);
				greenSquare.addActionListener(radioAction);
				blueSquare.addActionListener(radioAction);
				redCircle.addActionListener(radioAction);
				blueCircle.addActionListener(radioAction);
				greenCircle.addActionListener(radioAction);
				yellowCircle.addActionListener(radioAction);

				////////// 	Arrow button functionality	//////////
				JPanel arrowPanel = new JPanel();
				arrowPanel.setLayout(new FlowLayout());

				// Arrow buttons to move shapes
				BasicArrowButton xUp = new BasicArrowButton(BasicArrowButton.NORTH);
				BasicArrowButton xDown = new BasicArrowButton(BasicArrowButton.SOUTH);
				
				////////////////////////// might not need arrow buttons///////////////////
				//arrowPanel.add(xUp);
				//arrowPanel.add(xDown);

				// Mouse listener to allow us to move out shapes
				// Mouse listener to get coordinates to place our shapes.
				upperCanvas.addMouseListener(dummyInstance);

				// Partitioning window into 4 sections
				JPanel upperRight = new JPanel();
				upperRight.setLayout(new GridLayout(1, 2));

				upperRight.add(radioButtonPanel);
				upperRight.add(arrowPanel);

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

	public Quad2Animated(String name)
	{
		canvasName = name;
	}


	public void init(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();   
		glu = GLU.createGLU(gl);
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		gl.glClearDepth(1.0f);
		gl.glEnable(GL.GL_DEPTH_TEST);

		// eye, center, up
		if (this.toString().equals("upperCanvas"))
		{
			glu.gluLookAt(-1, 1, 2, 0, 0, 0, 0, 1, 0);
		}
		else {
			glu.gluLookAt(1, 2, 1, 0, 0, 0, 0, 1, 0);
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
			glu.gluPerspective(80.0, aspect, 0.1, 100.0);

			//System.out.println(this.canvasName);
		}

	}

	public void display(GLAutoDrawable drawable) {

		GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);

		// Do not load identity if you want cummulative rotations
		//gl.glLoadIdentity();            
		//gl.glRotated(1,  0, 1, 0);    

		drawSquares(gl);

		drawCircles(gl);
		drawAxes(gl);

		gl.glFlush();

	}

	public void dispose(GLAutoDrawable drawable) { }

	@Override
	public String toString() {
		return this.canvasName;
	}

	private void drawSquares(GL2 gl)
	{
		float size = 0.15f;
		//giving different colors to different sides
		// ------------- Blue Square -------------
		gl.glBegin(GL2.GL_QUADS); // Start Drawing The Cube
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

	private void drawCircles(GL2 gl)
	{

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

	// RadioButtonAction class Will change booleans that control whether we 
	// move our shapes or not.
	class RadioButtonAction implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			String cmd = e.getActionCommand();

			// Seeing what radio button (shape) is selected
			if (cmd.equals("Red Square"))
			{
				moveRedSquare = true;
				moveGreenSquare = false;
				moveBlueSquare = false;
				moveRedCircle = false;
				moveBlueCircle = false;
				moveGreenCircle = false;
				moveYellowCircle = false;
				System.out.println(cmd + " radio button pressed!!");
			}

			if (cmd.equals( "Green Square"))
			{
				moveRedSquare = false;
				moveGreenSquare = true;
				moveBlueSquare = false;
				moveRedCircle = false;
				moveBlueCircle = false;
				moveGreenCircle = false;
				moveYellowCircle = false;
				System.out.println(cmd + " radio button pressed!!");

			}

			if(cmd.equals("Blue Square"))
			{
				moveRedSquare = false;
				moveGreenSquare = false;
				moveBlueSquare = true;
				moveRedCircle = false;
				moveBlueCircle = false;
				moveGreenCircle = false;
				moveYellowCircle = false;
				System.out.println(cmd + " radio button pressed!!");
			}

			if(cmd.equals("Red Circle"))
			{
				moveRedSquare = false;
				moveGreenSquare = false;
				moveBlueSquare = false;
				moveRedCircle = true;
				moveBlueCircle = false;
				moveGreenCircle = false;
				moveYellowCircle = false;
				System.out.println(cmd + " radio button pressed!!");
			}

			if(cmd.equals("Blue Circle"))
			{
				moveRedSquare = false;
				moveGreenSquare = false;
				moveBlueSquare = false;
				moveRedCircle = false;
				moveBlueCircle = true;
				moveGreenCircle = false;
				moveYellowCircle = false;
				System.out.println(cmd + " radio button pressed!!");
			}

			if(cmd.equals("Green Circle"))
			{
				moveRedSquare = false;
				moveGreenSquare = false;
				moveBlueSquare = false;
				moveRedCircle = false;
				moveBlueCircle = false;
				moveGreenCircle = true;
				moveYellowCircle = false;
				System.out.println(cmd + " radio button pressed!!");
			}

			if(cmd.equals("Yellow Circle"))
			{
				moveRedSquare = false;
				moveGreenSquare = false;
				moveBlueSquare = false;
				moveRedCircle = false;
				moveBlueCircle = false;
				moveGreenCircle = false;
				moveYellowCircle = true;
				System.out.println(cmd + " radio button pressed!!");
			}

			// Repainting 
			//glcanvas.repaint();

		}
	} // End of RadioButtonAction class

	// ---------------------- ArrowButtonAction ---------------------
	// Class controls the movement of a selected shape
	class ArrowButtonAction implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			String cmd = e.getActionCommand();
			// Seeing if we checked a box
			if (cmd.equals("Red Square"))
			{

			}



			// Repainting 
			//glcanvas.repaint();

		}
	} // End of RadioButtonAction class

	// ----------------- MouseListener Methods --------------------
	@Override
	public void mouseClicked(MouseEvent e) {
		// First getting the mouse click coordinates
		int x = e.getX();
		int y = e.getY();
		// Changing coordinates into floats
		float xf = (float)( (x/ (319.0f) ) - 1.0f  );
		float yf = (float)( (-y/ (239.0f) ) + 1.0f);

		// verbose for debugging
		boolean verbose = true;

		// Checking which button was pressed.
		if(moveRedSquare)
		{

			redSquareX = xf;
			redSquareY = yf;
			if(verbose)
			{
				System.out.println("Moving red square to coord: " + xf + ", " + yf);

			}
			moveRedSquare = false;

		}

		if(moveGreenSquare)
		{
			greenSquareX = xf;
			greenSquareY = yf;
			if(verbose)
			{
				System.out.println("Moving green square to coord: " + xf + ", " + yf);

			}
			moveGreenSquare = false;

		}

		if(moveBlueSquare)
		{

			blueSquareX = xf;
			blueSquareY = yf;
			if(verbose)
			{
				System.out.println("Moving blue square to coord: " + blueSquareX + ", " + blueSquareY);

			}
			moveBlueSquare = false;

		}

		// ----------------- Circles -------------------
		if(moveRedCircle)
		{
			redCircleX = xf;
			redCircleY = yf;
			moveRedCircle = false;
			if(verbose)
			{
				System.out.println("Moving redCircle to coord: " + xf + ", " + yf);

			}

		}

		if(moveBlueCircle)
		{
			blueCircleX = xf;
			blueCircleY = yf;
			moveBlueCircle = false;
			if(verbose)
			{
				System.out.println("Moving blueCircle to coord: " + xf + ", " + yf);

			}

		}

		if(moveGreenCircle)
		{
			greenCircleX = xf;
			greenCircleY = yf;
			moveGreenCircle = false;
			if(verbose)
			{
				System.out.println("Moving greenCircle to coord: " + xf + ", " + yf);

			}

		}

		if(moveYellowCircle)
		{
			yellowCircleX = xf;
			yellowCircleY = yf;
			moveYellowCircle = false;
			if(verbose)
			{
				System.out.println("Moving yellowCircle to coord: " + xf + ", " + yf);

			}

		}
		// Repainting 
		upperCanvas.repaint();

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



} // End of Quad2Animated class

