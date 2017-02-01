package program3;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

public class Controller {
	View view;
	Model model;
	MouseListener mouseListener;
	int mouseStartX = 0;
	int mouseStartY = 0;

	public Controller(View view)
	{
		this.view = view;
		mouseListener = new GLCanvasMouseAdapter();   
		model = view.getModel();

		view.glcanvas.addMouseListener(mouseListener); 	
		view.glcanvas.addMouseMotionListener((MouseMotionListener) mouseListener);
		view.glcanvas.addMouseWheelListener((MouseWheelListener)mouseListener);
	}

	public class GLCanvasMouseAdapter implements MouseListener, MouseMotionListener, MouseWheelListener
	{

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub

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
		public void mousePressed(MouseEvent event) {
			mouseStartX = event.getX();
			mouseStartY = event.getY();			
		}

		@Override
		public void mouseReleased(MouseEvent event) {
			//			int diff = event.getX() - mouseStartX;
			//			double dx = ((double)diff)/ view.getWIDTH();
			//			view.cameraPan(dx, 0);
		}

		@Override
		public void mouseMoved(MouseEvent event) {			
		}


		@Override
		public void mouseDragged(MouseEvent event) {
			double dx = event.getX() - mouseStartX;
			double dy = event.getY() - mouseStartY;

			// Capturing keystroke!!!!!!!

			if (SwingUtilities.isRightMouseButton(event))
			{
				//				if (!event.isControlDown())
				//					model.setLowerRotate(dx, dy);
				//				else 
				//					model.setUpperRotate(dx, dy);
				//				view.glcanvas.repaint();

				if(view.torsoSelected())
				{
					model.setTorsoRotate(dx,  dy);
				}

				if(view.headSelected())
				{
					model.setHeadRotate(dx,  dy);
				}
				
				// REPAINT!!!!!!!!!!!!!!!!!!!!!!!!
				view.glcanvas.repaint();

			}
			else if (SwingUtilities.isLeftMouseButton(event))
			{
				view.cameraRotate(dx, dy);

			}			
			mouseStartX = event.getX();
			mouseStartY = event.getY();
		}


		@Override
		public void mouseWheelMoved(MouseWheelEvent event) {
			int clicks = event.getWheelRotation();
			view.cameraZoom(clicks);

		}
	}


}
