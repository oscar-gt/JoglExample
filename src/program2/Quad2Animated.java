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
 
 public class Quad2Animated implements GLEventListener {
	private static String TITLE = "Multi View";
        private static final int CANVAS_WIDTH = 640;
        private static final int CANVAS_HEIGHT = 480;
        private static final int FPS = 30;
        private String canvasName;
        
        // Default coordinates of shapes
        
        // Booleans to control our shapes
        boolean moveRedSquare = false;
        boolean moveGreenSquare = false;
        boolean moveBlueSquare = false;
        boolean moveRedCircle = false;
        boolean moveBlueCircle = false;
        boolean moveGreenCircle = false;
        boolean moveBlackCircle = false;
        
        
        
     
        


        public static void main(String[] args) {

            SwingUtilities.invokeLater(new Runnable() {

                public void run() {

                    // Create my two OpenGL Canvas
                    GLCanvas upperCanvas = new GLCanvas();
                    
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
                    JRadioButton blackCircle = new JRadioButton("Blacks Circle");
                    // Adding radio buttons to panel
                    radioButtonPanel.add(redSquare);
                    radioButtonPanel.add(greenSquare);
                    radioButtonPanel.add(blueSquare);
                    radioButtonPanel.add(redCircle);
                    radioButtonPanel.add(greenCircle);
                    radioButtonPanel.add(blueCircle);
                    radioButtonPanel.add(blackCircle);
                    // Grouping radio buttons
                    ButtonGroup radioButtonGroup = new ButtonGroup();
                    radioButtonGroup.add(redSquare);
                    radioButtonGroup.add(greenSquare);
                    radioButtonGroup.add(blueSquare);
                    radioButtonGroup.add(redCircle);
                    radioButtonGroup.add(greenCircle);
                    radioButtonGroup.add(blueCircle);
                    radioButtonGroup.add(blackCircle);
                    
                    //RadioButtonAction radioAction = new RadioButtonAction();
                    
                    JPanel arrowPanel = new JPanel();
                    arrowPanel.setLayout(new FlowLayout());
                    
                    // Arrow buttons to move shapes
                    BasicArrowButton xUp = new BasicArrowButton(BasicArrowButton.NORTH);
                    BasicArrowButton xDown = new BasicArrowButton(BasicArrowButton.SOUTH);
                    arrowPanel.add(xUp);
                    arrowPanel.add(xDown);
                   
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
            	glu.gluLookAt(-1, 0, 2, 0, 0, 0, 0, 1, 0);
            }
            else {
            	glu.gluLookAt(0, 2, 2, 0, 0, 0, 0, 1, 0);
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
            	System.out.println(this.canvasName);
            }
            else {
            	glu.gluPerspective(80.0, aspect, 0.1, 100.0);

            	System.out.println(this.canvasName);
            }

        }

        public void display(GLAutoDrawable drawable) {
        	
            GL2 gl = drawable.getGL().getGL2();
            gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
            gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
            
            // Do not load identity if you want cummulative rotations
            //gl.glLoadIdentity();            
            //gl.glRotated(1,  0, 1, 0);    
            
            drawCube(gl);
            //drawTriangle(gl);
            
            gl.glFlush();
        }

        public void dispose(GLAutoDrawable drawable) { }

        @Override
        public String toString() {
            return this.canvasName;
        }
        
        private void drawCube(GL2 gl)
        {
        	float size = 0.5f;
        	//giving different colors to different sides
            gl.glBegin(GL2.GL_QUADS); // Start Drawing The Cube
//            gl.glColor3f(1f,0f,0f); //red color
//            gl.glVertex3f(size, size, -size); // Top Right Of The Quad (Top)
//            gl.glVertex3f( -size, size, -size); // Top Left Of The Quad (Top)
//            gl.glVertex3f( -size, size, size ); // Bottom Left Of The Quad (Top)
//            gl.glVertex3f( size, size, size ); // Bottom Right Of The Quad (Top)
//      		
//            gl.glColor3f( 0f,1f,0f ); //green color
//            gl.glVertex3f( size, -size, size ); // Top Right Of The Quad
//            gl.glVertex3f( -size, -size, size ); // Top Left Of The Quad
//            gl.glVertex3f( -size, -size, -size ); // Bottom Left Of The Quad
//            gl.glVertex3f( size, -size, -size ); // Bottom Right Of The Quad 

            gl.glColor3f( 0f,0f,1f ); //blue color
            gl.glVertex3f( size, size, size ); // Top Right Of The Quad (Front)
            gl.glVertex3f( -size, size, size ); // Top Left Of The Quad (Front)
            gl.glVertex3f( -size, -size, size ); // Bottom Left Of The Quad
            gl.glVertex3f( size, -size, size ); // Bottom Right Of The Quad 

//            gl.glColor3f( 1f,1f,0f ); //yellow (red + green)
//            gl.glVertex3f( size, -size, -size ); // Bottom Left Of The Quad
//            gl.glVertex3f( -size, -size, -size ); // Bottom Right Of The Quad
//            gl.glVertex3f( -size, size, -size ); // Top Right Of The Quad (Back)
//            gl.glVertex3f( size, size, -size ); // Top Left Of The Quad (Back)
//
//            gl.glColor3f( 1f,0f,1f ); //purple (red + green)
//            gl.glVertex3f( -size, size, size ); // Top Right Of The Quad (Left)
//            gl.glVertex3f( -size, size, -size ); // Top Left Of The Quad (Left)
//            gl.glVertex3f( -size, -size, -size ); // Bottom Left Of The Quad
//            gl.glVertex3f( -size, -size, size ); // Bottom Right Of The Quad 
//
//            gl.glColor3f( 0f,1f, 1f ); //sky blue (blue +green)
//            gl.glVertex3f( size, size, -size ); // Top Right Of The Quad (Right)
//            gl.glVertex3f( size, size, size ); // Top Left Of The Quad
//            gl.glVertex3f( size, -size, size ); // Bottom Left Of The Quad
//            gl.glVertex3f( size, -size, -size ); // Bottom Right Of The Quad
            gl.glEnd(); // Done Drawing The Quad
        }
        
        private void drawTriangle(GL2 gl)
        {
        	gl.glBegin(GL.GL_TRIANGLES);
	            gl.glColor3d(1, 0, 0);
	            gl.glVertex3f(0.0f, 1.0f, 0.0f);
	            gl.glColor3d(0, 1, 0);
	            gl.glVertex3f(-1.0f, -1.0f, 0.0f);
	            gl.glColor3d(0, 0, 1);
	            gl.glVertex3f(1.0f, -1.0f, 0.0f);
            gl.glEnd();
        }
        
        // CheckBoxAction class Will change booleans that control whether we 
    	// see our shapes or not.
    	class RadioButtonAction implements ActionListener
    	{
    		public void actionPerformed(ActionEvent e)
    		{
    			String cmd = e.getActionCommand();

    			// Seeing if we checked a box
    			if (cmd.equals("Red Square"))
    			{
    				moveRedSquare = true;
    			}

    			if (cmd.equals( "Show Square"))
    			{
    				
    			}

    			if(cmd.equals("Show Circle"))
    			{
    				
    			}

    			// Repainting 
    			//glcanvas.repaint();

    		}
    	}
        
        
        
        
    } 