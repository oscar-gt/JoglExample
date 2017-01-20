package interactiveCamera;

import com.jogamp.opengl.GL2;

public class Model {
	public void drawCube(GL2 gl)
    {
    	float size = 0.2f;
    	
    	//giving different colors to different sides
          gl.glBegin(GL2.GL_QUADS); // Start Drawing The Cube
//        gl.glColor3f(1f,0f,0f); //red color
//        gl.glVertex3f(size, size, -size); // Top Right Of The Quad (Top)
//        gl.glVertex3f( -size, size, -size); // Top Left Of The Quad (Top)
//        gl.glVertex3f( -size, size, size ); // Bottom Left Of The Quad (Top)
//        gl.glVertex3f( size, size, size ); // Bottom Right Of The Quad (Top)
//  		
//        gl.glColor3f( 0f,1f,0f ); //green color
//        gl.glVertex3f( size, -size, size ); // Top Right Of The Quad
//        gl.glVertex3f( -size, -size, size ); // Top Left Of The Quad
//        gl.glVertex3f( -size, -size, -size ); // Bottom Left Of The Quad
//        gl.glVertex3f( size, -size, -size ); // Bottom Right Of The Quad 

        gl.glColor3f( 0f,0f,1f ); //blue color
        gl.glVertex3f( size, size, size ); // Top Right Of The Quad (Front)
        gl.glVertex3f( -size, size, size ); // Top Left Of The Quad (Front)
        gl.glVertex3f( -size, -size, size ); // Bottom Left Of The Quad
        gl.glVertex3f( size, -size, size ); // Bottom Right Of The Quad 

//        gl.glColor3f( 1f,1f,0f ); //yellow (red + green)
//        gl.glVertex3f( size, -size, -size ); // Bottom Left Of The Quad
//        gl.glVertex3f( -size, -size, -size ); // Bottom Right Of The Quad
//        gl.glVertex3f( -size, size, -size ); // Top Right Of The Quad (Back)
//        gl.glVertex3f( size, size, -size ); // Top Left Of The Quad (Back)
//
//        gl.glColor3f( 1f,0f,1f ); //purple (red + green)
//        gl.glVertex3f( -size, size, size ); // Top Right Of The Quad (Left)
//        gl.glVertex3f( -size, size, -size ); // Top Left Of The Quad (Left)
//        gl.glVertex3f( -size, -size, -size ); // Bottom Left Of The Quad
//        gl.glVertex3f( -size, -size, size ); // Bottom Right Of The Quad 
//
//        gl.glColor3f( 0f,1f, 1f ); //sky blue (blue +green)
//        gl.glVertex3f( size, size, -size ); // Top Right Of The Quad (Right)
//        gl.glVertex3f( size, size, size ); // Top Left Of The Quad
//        gl.glVertex3f( size, -size, size ); // Bottom Left Of The Quad
//        gl.glVertex3f( size, -size, -size ); // Bottom Right Of The Quad
        gl.glEnd(); // Done Drawing The Quad
    }

}
