package main;

import controller.Controller;
import model.Model;
import view.View;

public class Main {

	public static void main(String[] args) {
		Model model = new Model();
		View view = new View(model);
		new Controller(model, view);
	}

}
