package view;

import model.Model;
import variables.Variables;

public class View {
	private Model model;
	private GameScreen gameScreen;

	public View(Model model) {
		super();
		this.model = model;
	}
	
	public void setUpGame(){
		gameScreen = new GameScreen(Variables.WINDOW_DEFAULT_NAME, Variables.WINDOW_DEFAULT_WIDTH, Variables.WINDOW_DEFAULT_HEIGHT, model);
		gameScreen.initGameWindow();
	}
	
}
