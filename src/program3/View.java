package program3;

import java.awt.BorderLayout;
//import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.glu.GLU;


public class View extends JFrame implements GLEventListener, ChangeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	GLCanvas glcanvas, glcanvasRight;
	Model model;
	private int WIDTH = 500;
	private int HEIGHT = 500;
	private double camX = 0, camY = 0, camZ = 1;
	private double upX = 0, upY = 1, upZ = 0;
	private double lookX = 0, lookY = 0, lookZ = 0;
	private GLU glu;
	private float rColor = 0, gColor = 0, bColor = 0;
	private double near = 0.1, far = 10.0;
	private double cameraAngleX = 0, cameraAngleY = 90;
	
	// Used to control our movement
	private boolean headSelected = false;
	private boolean torsoSelected = false;
	private boolean rightArmSelected = false;
	private boolean leftArmSelected = false;

	public View(String title, Model model) {
		super(title);
		this.model = model;
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		glcanvas.repaint();

	}

	public void display() {
		display(glcanvas);
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		final GL2 gl = drawable.getGL().getGL2();
		gl.glViewport(0, 0, WIDTH, HEIGHT);

		// Set projection, view volume
		gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
		gl.glLoadIdentity();
		//gl.glFrustum(-1.0, 1.0, -1.0, 1.0, near, far);
		gl.glOrtho(-2, 2, -2, 2, near, far );

		// Enable depth testing
		gl.glEnable(GL.GL_DEPTH_TEST);
		gl.glClearColor(rColor, gColor, bColor, 0.0f);
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

		gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
		gl.glLoadIdentity();

		// set camera

		// eye, center, up
		glu.gluLookAt(camX, camY, camZ, lookX, lookY, lookZ, upX, upY, upZ);
		gl.glRotated(cameraAngleX, 0, 1, 0);
		gl.glRotated(cameraAngleY, 1, 0, 0);

		model.draw(gl);
		
	}

	@Override
	public void dispose(GLAutoDrawable arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		glu = GLU.createGLU(gl);

	}

	public void init() {
		// Initialize the canvas
		final GLProfile profile = GLProfile.get(GLProfile.GL2);
		GLCapabilities capabilities = new GLCapabilities(profile);
		glcanvas = new GLCanvas(capabilities);
		glcanvas.addGLEventListener(this);
		glcanvas.setSize(WIDTH, HEIGHT);
		
		glcanvasRight = new GLCanvas(capabilities);
		glcanvasRight.addGLEventListener(this);
		glcanvasRight.setSize(WIDTH, HEIGHT);
		
		// ---------------------- Adding radio buttons for moving different shapes
		JPanel radioButtonPanel = new JPanel();
		radioButtonPanel.setLayout(new GridLayout(8, 1));
		JRadioButton torsoRadioButton = new JRadioButton("Torso");
		JRadioButton headRadioButton = new JRadioButton("Head");
		// Grouping radio buttons
		ButtonGroup radioButtonGroup = new ButtonGroup();
		radioButtonGroup.add(torsoRadioButton);
		radioButtonGroup.add(headRadioButton);
		// Adding buttons to panel
		radioButtonPanel.add(torsoRadioButton);
		radioButtonPanel.add(headRadioButton);
		// Creating action listener
		RadioButtonAction radioButtonAction = new RadioButtonAction();
		// Enabling listener
		torsoRadioButton.addActionListener(radioButtonAction);
		headRadioButton.addActionListener(radioButtonAction);
		
		// --------------  Partitioning frame  -------------------
		//getContentPane().setLayout(new GridLayout(2, 2));

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(WIDTH, HEIGHT);
		//setLayout(new GridLayout(1,2));
		add(glcanvas);
		add(radioButtonPanel, BorderLayout.EAST);

		setVisible(true);

	}

	@Override
	public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4) {
		glcanvas.repaint();

	}

	public void cameraPan(double dx, double dy) {
		camX -= (dx/WIDTH) *2.0;
		lookX -= (dx/WIDTH) *2.0;
		
		camY += (dy/WIDTH) *2.0;
		lookY += (dy/WIDTH) *2.0;
		glcanvas.repaint();
		
	}

	public void cameraZoom(int clicks) {
		camZ += clicks/20.0;
		glcanvas.repaint();
	}
	
	
	// Note, this is not trackball action, just quick and dirty rotation
	public void cameraRotate(double dx, double dy)
	{
		cameraAngleX += dx * 0.3;		
		cameraAngleY += dy * 0.3;
		glcanvas.repaint();
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public int getWIDTH() {
		return WIDTH;
	}

	public void setWIDTH(int wIDTH) {
		WIDTH = wIDTH;
	}

	public int getHEIGHT() {
		return HEIGHT;
	}

	public void setHEIGHT(int hEIGHT) {
		HEIGHT = hEIGHT;
	}
	
	public boolean headSelected()
	{
		return headSelected;
	}
	
	public boolean torsoSelected()
	{
		return torsoSelected;
	}
	
	class RadioButtonAction implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) 
		{
			String cmd = e.getActionCommand();
			// Checking which radio button was pressed
			if(cmd.equals("Torso"))
			{
				System.out.println("Torso radio button selected!!");
				headSelected = false;
				torsoSelected = true;
				rightArmSelected = false;
				leftArmSelected = false;
				
			}
			
			if(cmd.equals("Head"))
			{
				System.out.println("Head radio button selected!!");
				headSelected = true;
				torsoSelected = false;
				rightArmSelected = false;
				leftArmSelected = false;
			}
			
		}
		
	}

}
