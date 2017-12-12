package model;

import variables.Variables;

public class Laser {
	private int x,y,duration;
	private boolean wide;

	public Laser(int x, int y) {
		super();
		this.x = x;
		this.y = y;
		this.duration = Variables.LASER_DEFAULT_DURATION;
		wide = true;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getDuration() {
		return duration;
	}
	
	public void lowerDuration(){
		duration--;
		if(duration%20>10) wide = true;
		else wide = false;
	}

	public boolean isWide() {
		return wide;
	}
	

}
