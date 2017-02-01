package program3;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;

public class Model {
	private double lowerRotateAngle = 45;
	private double upperRotateAngle = 0;
	
	// My shapes
	private double headRotateAngle = 0;
	private double torsoRotateAngle = 0;
	private double rightLegRotateAngle = 0;
	private double leftLegRotateAngle = 0;
	private double rightArmRotateAngle = 0;
	private double leftArmRotateAngle = 0;
	GL2 gl;
	
	public void draw(GL2 gl)
	{
		drawAxes(gl);
		this.gl = gl;
		
		// --------------- ORIGINAL CODE --------------------
		Cylinder upperArm = new Cylinder(gl, 0.1, 0.5);
		upperArm.setColor(1,  0,  0);
		TreeNode root = new TreeNode(upperArm);
		root.setName("Upper arm");
		root.setRotateVec(new Vec3D(0.0, 1.0, 0.0));
		root.setRotateAngle(upperRotateAngle);
		
		Cylinder lowerArm = new Cylinder(gl, 0.1, 0.6);
		lowerArm.setColor(0,  0,  1);
		TreeNode lowerArmNode = new TreeNode(lowerArm);
		lowerArmNode.setName("Lower arm");
		lowerArmNode.setTranslateVec(new Vec3D(0,0, 0.4));
		lowerArmNode.setRotateVec(new Vec3D(0.0, 1.0, 0.0));
		lowerArmNode.setRotateAngle(lowerRotateAngle);
		
		root.addChild(lowerArmNode);
		// --------------- END OF ORIGINAL CODE -----------------

		
		// --------------- Torso -----------------
		Cylinder torso = new Cylinder(gl, 0.1, 0.5);
		torso.setColor(1, 1, 1);
		TreeNode torsoNode = new TreeNode(torso);
		torsoNode.setName("Torso");
		torsoNode.setRotateVec(new Vec3D(0, 0, 1.0));
		torsoNode.setTranslateVec(new Vec3D(0, 0, -0.72));
		torsoNode.setRotateAngle(torsoRotateAngle);
		
		root.addChild(torsoNode);
//		
//		// ------------------  Mantis Head  ---------------
		MantisHead head = new MantisHead(gl, 0.45f);
		head.setColor(0.5, 0.5, 0.5);
		TreeNode headNode = new TreeNode(head);
		headNode.setName("Head");
		headNode.setRotateVec(new Vec3D(0, 0, 1.0));
		headNode.setRotateAngle(headRotateAngle);
		
		// --------------- Mantis Arms ---------------
		// Right arm
		MantisArm rightArm = new MantisArm(gl, 1);
		rightArm.setColor(0, 0, 1);
		TreeNode rightArmNode = new TreeNode(rightArm);
		rightArmNode.setTranslateVec(new Vec3D(0.4, -0.3, 0));
		rightArmNode.setRotateVec(new Vec3D(0.0, 1.0, 0.0));
		rightArmNode.setRotateAngle(rightArmRotateAngle);
		// Left arm
		MantisArm leftArm = new MantisArm(gl, 1);
		leftArm.setColor(0, 0, 1);
		TreeNode leftArmNode = new TreeNode(leftArm);
		leftArmNode.setTranslateVec(new Vec3D(-0.4, -0.3, 0));
		leftArmNode.setRotateVec(new Vec3D(0.0, 1.0, 0.0));
		leftArmNode.setRotateAngle(leftArmRotateAngle);
		
		// Creating tree
		//leftArmNode.addSibling(rightArmNode);
		//root.addChild(headNode);
		//root.addChild(headNode);
//		torsoNode.addChild(leftArmNode);
		//root.displayNodes(root);
		
		// Manually drawing without using a tree
		
		// Torso is the base shape (root)
		
		double torsoX = torsoNode.getTranslateVec().x;
		double torsoY = torsoNode.getTranslateVec().y;
		double torsoZ = torsoNode.getTranslateVec().z;
		// rotation
		double torsoRX = torsoNode.getRotateVec().x;
		double torsoRY = torsoNode.getRotateVec().y;
		double torsoRZ = torsoNode.getRotateVec().z;
		
		// Mantis head
		double headX = headNode.getTranslateVec().x;
		double headY = headNode.getTranslateVec().y;
		double headZ = headNode.getTranslateVec().z;
		// rotation
		double headRX = headNode.getRotateVec().x;
		double headRY = headNode.getRotateVec().y;
		double headRZ = headNode.getRotateVec().z;
		
		// right arm
		double rightArmX = rightArmNode.getTranslateVec().x;
		double rightArmY = rightArmNode.getTranslateVec().y;
		double rightArmZ = rightArmNode.getTranslateVec().z;
		// rotation
		double rightArmRX = rightArmNode.getRotateVec().x;
		double rightArmRY = rightArmNode.getRotateVec().y;
		double rightArmRZ = rightArmNode.getRotateVec().z;
		
		// Left arm
		double leftArmX = leftArmNode.getTranslateVec().x;
		double leftArmY = leftArmNode.getTranslateVec().y;
		double leftArmZ = leftArmNode.getTranslateVec().z;
		// rotation
		double leftArmRX = leftArmNode.getRotateVec().x;
		double leftArmRY = leftArmNode.getRotateVec().y;
		double leftArmRZ = leftArmNode.getRotateVec().z;
		
		// Now pushing, translating, popping, etc
		
		gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW); 
		// Copying top matrix
		gl.glPushMatrix();
			// Translating torso
			gl.glTranslated(torsoX, torsoY, torsoZ);
			gl.glRotated(torsoRotateAngle, torsoRX, torsoRY, torsoRZ);
			// Drawing torso
			torsoNode.draw(gl);
			
			gl.glPushMatrix();
				gl.glTranslated(rightArmX, rightArmY, rightArmZ);
				gl.glRotated(rightArmRotateAngle, rightArmRZ, rightArmRY, rightArmRZ);
				rightArmNode.draw(gl);;
				gl.glPopMatrix();
			// Back to torso coordinates
			gl.glPushMatrix();
				gl.glTranslated(leftArmX, leftArmY, leftArmZ);
				gl.glRotated(leftArmRotateAngle, leftArmRZ, leftArmRY, leftArmRZ);
				leftArmNode.draw(gl);;
				gl.glPopMatrix();
			// Back to torso coord
			gl.glPushMatrix(); // saving to add head on top of torso
				gl.glTranslated(headX, headY, headZ);
				// Now in head coord ?????????????
				gl.glRotated(headRotateAngle, headRZ, headRY, headRZ);
				headNode.draw(gl);
				
				gl.glPopMatrix();
			// Back to torso
			gl.glPopMatrix(); // Popped all the way
				
				
		

		
		
	}
	
	public void drawAxes(GL2 gl) {
		// drawing the axes
		float offset = .75f;
			gl.glBegin(GL2.GL_LINES);
			gl.glColor3d(1.0, 0.0, 0.0);
			gl.glVertex3f(-offset, 0, 0);
			gl.glVertex3f(offset, 0, 0);
			gl.glColor3d(0.0, 1.0, 0.0);
			gl.glVertex3f(0, -offset, 0);
			gl.glVertex3f(0, offset, 0);
			gl.glColor3d(0.0, 0, 1.0);
			gl.glVertex3f(0, 0, -offset);
			gl.glVertex3f(0, 0, offset);
		gl.glEnd();

	}
	
	public void setUpperRotate(double dx, double dy)
	{
		upperRotateAngle += dx;
	}
	
	public void setLowerRotate(double dx, double dy)
	{
		lowerRotateAngle += dx;
	}
	
	public void setHeadRotate(double dx, double dy)
	{
		headRotateAngle += dx;
	}
	
	public void setTorsoRotate(double dx, double dy)
	{
		torsoRotateAngle += dx;
	}
}
