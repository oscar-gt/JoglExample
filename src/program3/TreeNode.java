package program3;

import java.util.ArrayList;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;


/*
 * Node for a SceneGraph tree.
 * Simplified, in that transformations are included with geometry.
 * Assumes rotation before translation.
 * Appearance and geometry are within draw method.
 */
public class TreeNode {
	private ArrayList<TreeNode> children = new ArrayList<TreeNode>();
	private TreeNode sibling = null;
	private Vec3D translateVec = new Vec3D();
	private Vec3D rotateVec = new Vec3D();
	private double rotateAngle = 0;
	private Shape shape;

	private String name;

	public TreeNode(Shape shape)
	{
		this.shape = shape;
	}

	public Vec3D getTranslateVec() {
		return translateVec;
	}

	public void setTranslateVec(Vec3D translateVec) {
		this.translateVec = translateVec;
	}

	public Vec3D getRotateVec() {
		return rotateVec;
	}

	public void setRotateVec(Vec3D rotateVec) {
		this.rotateVec = rotateVec;
	}

	public void setRotateAngle(double angle)
	{
		rotateAngle = angle;
	}


	/*
	 * Pre-order traversal, drawing children last
	 */
	// Modify so that it also draws siblings!!!!!!!!!!!!!!!!!!!!!
	public void draw()
	{
		shape.draw();
	}
	public void draw(GL2 gl)
	{
		shape.draw();
		// CODE THAT I WORKED ON BUT DIDINT WORK
//		gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW); 
//		gl.glPushMatrix();
//		gl.glTranslated(translateVec.x, translateVec.y, translateVec.z);
//		gl.glRotated(rotateAngle, rotateVec.x, rotateVec.y, rotateVec.z);
//
//		shape.draw();
//
//		for (TreeNode node: children)
//		{
//			node.draw(gl);
//			if(node.sibling != null)
//			{
//				node.sibling.draw(gl);
//			}
//
//		}
//		if(this.sibling != null)
//		{
//			this.sibling.draw(gl);
//		}
//
//
//		gl.glPopMatrix();

	}


	public void addChild(TreeNode node)
	{
		// -------- ORIGINAL CODE -----------
				if (!children.isEmpty())
					children.get(children.size() - 1).sibling = node;
				children.add(node);
		// END OF ORIGINAL CODE ------------

		//children.add(node);
	}

	public void addSibling(TreeNode sib)
	{
		this.sibling = sib;
	}

	public void displayNodes(TreeNode root)
	{
		if(root == null)
		{
			return;
		}
		else
		{
			System.out.print(root.name + " ");
			// Show children
			if(root.children.isEmpty() == false)
			{
				int childrenSize = root.children.size();
				System.out.println("number of children: " + childrenSize);
				System.out.println("");
				// Children exist, so print
				for(int i = 0; i < childrenSize; i ++)
				{
					System.out.print("inside for loop, i = " + i + ", ");
					displayNodes(root.children.get(i));
					// Displaying siblings
					if(root.children.get(i).sibling != null)
					{
						root.children.get(i).displaySiblings();
						System.out.println("");
					}
					System.out.println("");
				}

			}
			// No children
		}


	}

	public void displaySiblings()
	{
		System.out.println(" siblings: ");
		TreeNode curr = this.sibling;
		System.out.print(curr.name);
		if(curr.sibling != null)
		{
			curr.displaySiblings();
		}
	}

	public void setName(String n)
	{
		this.name = n;
	}

}
