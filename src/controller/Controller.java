package controller;

import model.Model;
import view.View;

public class Controller {
	private Model model;
	private View view;
	public Controller(Model model, View view) {
		super();
		this.model = model;
		this.view = view;
		setUpGame();
	}
	
	private void setUpGame(){
		model.setUpGame();
		view.setUpGame();
	}
	
}
