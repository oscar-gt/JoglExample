package program3;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

public class MantisHead implements Shape
{

	// --------- Default Values --------
	private float defaultLength = 0.3f;
	private float defaultWidth = 0.15f;
	private float defaultHeight = 0.15f;

	private float scaleFactor;
	private float headLength = defaultLength;
	private float headWidth = defaultWidth;
	private float headHeight = defaultHeight;

	private GL2 gl;

	private double r = 1, g = 1, b = 1;

	public MantisHead(GL2 glInput, float scale)
	{
		this.gl = glInput;
		this.scaleFactor = scale;
		// Scaling our arm
		if(scale != 1)
		{
			headLength = headLength * scaleFactor;
			headWidth = headWidth * scaleFactor;
			headHeight = headHeight * scaleFactor;
		}

	}
	@Override
	public void draw() {
		GLU glu = GLU.createGLU(gl);
		gl.glColor3d(r, g, b);
		
		double radius = scaleFactor;
		int stacks = 20;
		int slices = 20;
		
		GLUquadric quadric = glu.gluNewQuadric();
		
		// Cylinder (quadric, baseRadius, topRadius, height, slices, stacks)
		glu.gluQuadricDrawStyle(quadric, GLU.GLU_LINE);
		
		glu.gluSphere(quadric, radius, slices, stacks);

		// Drawing cube 

		float size = 0.45f;
		
		

		//giving different colors to different sides
//		gl.glBegin(GL2.GL_QUADS); // Start Drawing The Cube
//		gl.glColor3f(1f,0f,0f); //red color
//		gl.glVertex3f(size, size, -size); // Top Right Of The Quad (Top)
//		gl.glVertex3f( -size, size, -size); // Top Left Of The Quad (Top)
//		gl.glVertex3f( -size, size, size ); // Bottom Left Of The Quad (Top)
//		gl.glVertex3f( size, size, size ); // Bottom Right Of The Quad (Top)
//
//		gl.glColor3f( 0f,1f,0f ); //green color
//		gl.glVertex3f( size, -size, size ); // Top Right Of The Quad (Bottom of cube)
//		gl.glVertex3f( -size, -size, size ); // Top Left Of The Quad
//		gl.glVertex3f( -size, -size, -size ); // Bottom Left Of The Quad
//		gl.glVertex3f( size, -size, -size ); // Bottom Right Of The Quad 
//
//		gl.glColor3f( 0f,0f,1f ); //blue color
//		gl.glVertex3f( size, size, size ); // Top Right Of The Quad (Front)
//		gl.glVertex3f( -size, size, size ); // Top Left Of The Quad (Front)
//		gl.glVertex3f( -size, -size, size ); // Bottom Left Of The Quad
//		gl.glVertex3f( size, -size, size ); // Bottom Right Of The Quad 
//
//		gl.glColor3f( 1f,1f,0f ); //yellow (red + green)
//		gl.glVertex3f( size, -size, -size ); // Bottom Left Of The Quad
//		gl.glVertex3f( -size, -size, -size ); // Bottom Right Of The Quad
//		gl.glVertex3f( -size, size, -size ); // Top Right Of The Quad (Back)
//		gl.glVertex3f( size, size, -size ); // Top Left Of The Quad (Back)
//
//		gl.glColor3f( 1f,0f,1f ); //purple (red + green)
//		gl.glVertex3f( -size, size, size ); // Top Right Of The Quad (Left)
//		gl.glVertex3f( -size, size, -size ); // Top Left Of The Quad (Left)
//		gl.glVertex3f( -size, -size, -size ); // Bottom Left Of The Quad
//		gl.glVertex3f( -size, -size, size ); // Bottom Right Of The Quad 
//
//		gl.glColor3f( 0f,1f, 1f ); //sky blue (blue +green)
//		gl.glVertex3f( size, size, -size ); // Top Right Of The Quad (Right)
//		gl.glVertex3f( size, size, size ); // Top Left Of The Quad
//		gl.glVertex3f( size, -size, size ); // Bottom Left Of The Quad
//		gl.glVertex3f( size, -size, -size ); // Bottom Right Of The Quad
//		gl.glEnd(); // Done Drawing The Quad

	}

	@Override
	public void setColor(double r, double g, double b) {
		this.r = r;
		this.g = g;
		this.b = b;

	}
	

}
