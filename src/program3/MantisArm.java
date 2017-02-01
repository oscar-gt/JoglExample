package program3;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLU;

public class MantisArm implements Shape
{
	// --------- Default Values --------
	private float defaultLength = 0.3f;
	private float defaultWidth = 0.15f;
	private float defaultHeight = 0.15f;

	private float scaleFactor;
	private float armLength = defaultLength;
	private float armWidth = defaultWidth;
	private float armHeight = defaultHeight;

	private GL2 gl;

	private double r = 1, g = 1, b = 1;

	public MantisArm(GL2 gl, int scale)
	{
		this.gl = gl;
		this.scaleFactor = scale;
		// Scaling our arm
		if(scale != 1)
		{
			armLength = armLength * scaleFactor;
			armWidth = armWidth * scaleFactor;
			armHeight = armHeight * scaleFactor;
		}

	}

	@Override
	public void draw() 
	{
		// Commenting next line and tring GLU glu = new GLU()
		//GLU glu = GLU.createGLU(gl);
		//GLU glu = new GLU();
//		gl.glColor3d(r, g, b);

		// Drawing cube 
		GL2 gl = this.gl;

		float size = 0.2f;

		//giving different colors to different sides
		gl.glBegin(GL2.GL_QUADS); // Start Drawing The Cube
		gl.glColor3f(1f,0f,0f); //red color
		gl.glVertex3f(size, size, -size); // Top Right Of The Quad (Top)
		gl.glVertex3f( -size, size, -size); // Top Left Of The Quad (Top)
		gl.glVertex3f( -size, size, size ); // Bottom Left Of The Quad (Top)
		gl.glVertex3f( size, size, size ); // Bottom Right Of The Quad (Top)

		gl.glColor3f( 0f,1f,0f ); //green color
		gl.glVertex3f( size, -size, size ); // Top Right Of The Quad (Bottom of cube)
		gl.glVertex3f( -size, -size, size ); // Top Left Of The Quad
		gl.glVertex3f( -size, -size, -size ); // Bottom Left Of The Quad
		gl.glVertex3f( size, -size, -size ); // Bottom Right Of The Quad 

		gl.glColor3f( 0f,0f,1f ); //blue color
		gl.glVertex3f( size, size, size ); // Top Right Of The Quad (Front)
		gl.glVertex3f( -size, size, size ); // Top Left Of The Quad (Front)
		gl.glVertex3f( -size, -size, size ); // Bottom Left Of The Quad
		gl.glVertex3f( size, -size, size ); // Bottom Right Of The Quad 

		gl.glColor3f( 1f,1f,0f ); //yellow (red + green)
		gl.glVertex3f( size, -size, -size ); // Bottom Left Of The Quad
		gl.glVertex3f( -size, -size, -size ); // Bottom Right Of The Quad
		gl.glVertex3f( -size, size, -size ); // Top Right Of The Quad (Back)
		gl.glVertex3f( size, size, -size ); // Top Left Of The Quad (Back)

		gl.glColor3f( 1f,0f,1f ); //purple (red + green)
		gl.glVertex3f( -size, size, size ); // Top Right Of The Quad (Left)
		gl.glVertex3f( -size, size, -size ); // Top Left Of The Quad (Left)
		gl.glVertex3f( -size, -size, -size ); // Bottom Left Of The Quad
		gl.glVertex3f( -size, -size, size ); // Bottom Right Of The Quad 

		gl.glColor3f( 0f,1f, 1f ); //sky blue (blue +green)
		gl.glVertex3f( size, size, -size ); // Top Right Of The Quad (Right)
		gl.glVertex3f( size, size, size ); // Top Left Of The Quad
		gl.glVertex3f( size, -size, size ); // Bottom Left Of The Quad
		gl.glVertex3f( size, -size, -size ); // Bottom Right Of The Quad
		gl.glEnd(); // Done Drawing The Quad


	}

	@Override
	public void setColor(double r, double g, double b) 
	{
		this.r = r;
		this.g = g;
		this.b = b;

	}

}
