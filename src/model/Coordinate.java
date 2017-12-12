package model;

public class Coordinate {
	private int x, y, speedWhenHit;
	private boolean obstacle;

	public Coordinate(int x, int y, int speedWhenHit) {
		super();
		this.x = x;
		this.y = y;
		this.obstacle=false;
		this.speedWhenHit=Math.abs(speedWhenHit);
	}
	public Coordinate(int x, int y, boolean obs, int speedWhenHit) {
		super();
		this.x = x;
		this.y = y;
		this.obstacle=obs;
		this.speedWhenHit = Math.abs(speedWhenHit);
	}
	public Coordinate(Coordinate c) {
		super();
		this.x = c.getX();
		this.y = c.getY();
		this.obstacle=false;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	public boolean isObstacle() {
		return obstacle;
	}
	public void setObstacle(boolean obstacle) {
		this.obstacle = obstacle;
	}
	public int getSpeedWhenHit() {
		return speedWhenHit;
	}
	
	
}
