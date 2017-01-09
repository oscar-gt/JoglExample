
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;


import javax.swing.JFrame;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SimpleTriangle implements GLEventListener, ChangeListener {
	GLCanvas glcanvas;

	@Override
	public void display(GLAutoDrawable drawable) {

		final GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT);

		gl.glPushMatrix();

		
		// drawing the triangle
		gl.glBegin(GL2.GL_TRIANGLES);
		gl.glColor3d(1.0, 0.0, 0.0);
		gl.glVertex3f(-0.50f, -0.50f, 0);
		gl.glColor3d(0.0, 1.0, 0.0);
		gl.glVertex3f(0.50f, -0.50f, 0);
		gl.glColor3d(0.0, 0.0, 1.0);
		gl.glVertex3f(0f, 0.50f, 0);
		gl.glEnd();

		gl.glFlush();

		gl.glPopMatrix();

	}

	@Override
	public void dispose(GLAutoDrawable arg0) {
		// method body
	}

	@Override
	public void init(GLAutoDrawable arg0) {
		// method body
	}

	@Override
	public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4) {
		// method body
	}

	public void init() {
		// getting the capabilities object of GL2 profile
		final GLProfile profile = GLProfile.get(GLProfile.GL2);

		GLCapabilities capabilities = new GLCapabilities(profile);
		glcanvas = new GLCanvas(capabilities);
		glcanvas.addGLEventListener(this);
		int canvasW = 500; // Originally 400
		int canvasH = 400;	// Originally 400
		glcanvas.setSize(canvasW, canvasH);

		// creating frame
		final JFrame frame = new JFrame("Simple Triangle");
		

		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// adding canvas to frame
		frame.add(glcanvas);
		frame.setSize(840, 480);

		frame.setVisible(true);
	}

	public static void main(String[] args) {
		// The canvas

		SimpleTriangle b = new SimpleTriangle();
		b.init();
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		glcanvas.repaint();
		
	}


}