package model;

import java.awt.Color;

import variables.Variables;

public class Obstacle {
	private int x, y, width, height;
	private Color color;
	public Obstacle(int x, int y, int width, int height) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.color = Variables.OBSTACLE_DEFAULT_COLOR;
	}
	
	public Obstacle(int x, int y) {
		super();
		this.x = x;
		this.y = y;
		this.width = Variables.OBSTACLE_DEFAULT_WIDTH;
		this.height = Variables.OBSTACLE_DEFAULT_HEIGHT;
		this.color = Variables.OBSTACLE_DEFAULT_COLOR;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
}
