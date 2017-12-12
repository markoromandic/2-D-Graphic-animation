package model;

public class Particle {
	private int x, y, speedX, speedY, alpha, alphaReduction, duration;
	private boolean obstacle;

	public Particle(int x, int y, int speedX, int speedY, int duration) {
		super();
		this.x = x;
		this.y = y;
		this.speedX = speedX;
		this.speedY = speedY;
		this.alpha = 100;
		this.alphaReduction = 100/duration;
		this.duration = duration;
		this.obstacle=false;
	}
	
	public Particle(int x, int y, int speedX, int speedY, int duration, boolean obstacle) {
		super();
		this.x = x;
		this.y = y;
		this.speedX = speedX;
		this.speedY = speedY;
		this.alpha = 100;
		this.alphaReduction = 100/duration;
		this.duration = duration;
		this.obstacle=obstacle;
	}
	
	public void calculatePosition(){
		x+=speedX;
		y+=speedY;
		duration--;
		alpha-=alphaReduction;
		alpha = Math.max(0, alpha);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getAlpha() {
		return alpha;
	}

	public int getDuration() {
		return duration;
	}

	public boolean isObstacle() {
		return obstacle;
	}
	
	
	
}
