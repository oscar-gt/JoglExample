package interactiveCamera;

import javax.swing.JFrame;

@SuppressWarnings("unused")
public class InteractiveCamera {
	
	public static void main(String[] args) {
		Model model = new Model();
		View view = new View("Interactive Camera", model);
		view.init();

		Controller controller = new Controller(view);

	}
}
