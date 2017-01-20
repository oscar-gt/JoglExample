package interactiveCamera;

//import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
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
	private double near = 0.3, far = 10.0;
	private double cameraAngleX = 0, cameraAngleY = 0;

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
		// SET MULTIPLE VIEWPORTS WITH glViewport(int, int, int, int)
		
		///////////////////// FIRST VIEWPORT /////////////////
		int firstWindowW = WIDTH/2;
		int firstWindowH = HEIGHT/2;
		gl.glViewport(0, HEIGHT/2, firstWindowW, firstWindowH); // TOP LEFT CORNER OF CANVAS
		

		// Set projection, view volume
		gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glFrustum(-1.0, 1.0, -1.0, 1.0, near, far);

		// Enable depth testing (ADDED)
		gl.glEnable(GL.GL_DEPTH_TEST);
		gl.glClearColor(rColor, gColor, bColor, 0.0f);
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		
		// First viewport camera
		glu.gluLookAt(camX, camY, camZ, lookX, lookY, lookZ, upX, upY, upZ);
		gl.glRotated(cameraAngleX, 0, 1, 0);
		gl.glRotated(cameraAngleY, 1, 0, 0);
		
		//////////////////// SECOND VIEWPORT ////////////////////
		gl.glViewport(0, 0, WIDTH/2, HEIGHT/2);
		gl.glLoadIdentity();
		// Frustum and camera settings
		//gl.glFrustum(-1.0, 1.0, -1.0, 1.0, near, far);
		// CHANGE LOOKAT FOR SECOND VIEWPORT
		glu.gluLookAt(camX + 0.5, camY - 0.5, camZ + 0.3, 
				lookX, lookY, lookZ, upX, upY, upZ);
		// Enable depth testing (ADDED)
		//gl.glEnable(GL.GL_DEPTH_TEST);
//		gl.glClearColor(rColor, gColor, bColor, 0.0f);
//		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		// /////////////// END OF SECOND VIEWPORT
		

		gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
		gl.glLoadIdentity();

		// set camera

		// eye, center, up
//		glu.gluLookAt(camX, camY, camZ, lookX, lookY, lookZ, upX, upY, upZ);
//		gl.glRotated(cameraAngleX, 0, 1, 0);
//		gl.glRotated(cameraAngleY, 1, 0, 0);

		model.drawCube(gl);

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
		glcanvasRight.setSize(WIDTH/2, HEIGHT/2);
		

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		double xMult = 1.0;
		double yMult = 1.0;
		setSize(WIDTH, HEIGHT);
		setLayout(new GridLayout(1,2));
		add(glcanvas);
		//add(glcanvasRight);

		setVisible(true);

	}

	@Override
	public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4) {
		glcanvas.repaint();

	}

	public void cameraPan(double dx, double dy) {
		
	}

	public void cameraZoom(int clicks) {
		
	}
	
	public void cameraRotate(double dx, double dy)
	{
		
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

}
