package model;

import java.awt.Color;
import java.awt.image.WritableRaster;
import java.util.ArrayList;

import rafgfxlib.Util;
import variables.Variables;

public class Ball {
	private int x,y,radius,sizeOfTrace;
	private int speedX, speedY;
	private Color color;
	private ArrayList<Coordinate> trace, hits;
	private boolean hitWall = false;
	private boolean hitObstacle = false;
	private Raster raster;

	public Ball(int x, int y, int radius, int speedX, int speedY, ArrayList<Coordinate> hits, Raster raster) {
		super();
		this.x = x;
		this.y = y;
		this.radius = radius;
		this.speedX = speedX;
		this.speedY = speedY;
		this.color = Variables.BALL_DEFAULT_COLOR;
		this.hits = hits;
		this.raster = raster;
		initializeTrace();
	}
	
	public Ball(int x, int y, int speedX, int speedY, ArrayList<Coordinate> hits) {
		super();
		this.x = x;
		this.y = y;
		this.radius = Variables.BALL_DEFAULT_RADIUS;
		this.speedX = speedX;
		this.speedY = speedY;
		this.color = Variables.BALL_DEFAULT_COLOR;
		this.hits = hits;
		initializeTrace();
	}

	public Ball(int x, int y, int radius, ArrayList<Coordinate> hits) {
		super();
		this.x = x;
		this.y = y;
		this.radius = radius;
		this.speedX = Variables.BALL_DEFAULT_SPEED;
		this.speedY = Variables.BALL_DEFAULT_SPEED;
		this.color = Variables.BALL_DEFAULT_COLOR;
		this.hits = hits;
		initializeTrace();
	}
	
	public Ball(int x, int y, ArrayList<Coordinate> hits) {
		super();
		this.x = x;
		this.y = y;
		this.radius = Variables.BALL_DEFAULT_RADIUS;
		this.speedX = Variables.BALL_DEFAULT_SPEED;
		this.speedY = Variables.BALL_DEFAULT_SPEED;
		this.color = Variables.BALL_DEFAULT_COLOR;
		this.hits = hits;
		initializeTrace();
	}
	
	public Ball(int radius, ArrayList<Coordinate> hits) {
		super();
		this.x = Variables.BALL_DEFAULT_START_POSITION_X;
		this.y = Variables.BALL_DEFAULT_START_POSITION_Y;
		this.radius = radius;
		this.speedX = Variables.BALL_DEFAULT_SPEED;
		this.speedY = Variables.BALL_DEFAULT_SPEED;
		this.color = Variables.BALL_DEFAULT_COLOR;
		this.hits = hits;
		initializeTrace();
	}
	
	public Ball(ArrayList<Coordinate> hits) {
		super();
		this.x = Variables.BALL_DEFAULT_START_POSITION_X;
		this.y = Variables.BALL_DEFAULT_START_POSITION_Y;
		this.radius = Variables.BALL_DEFAULT_RADIUS;
		this.speedX = Variables.BALL_DEFAULT_SPEED;
		this.speedY = Variables.BALL_DEFAULT_SPEED;
		this.color = Variables.BALL_DEFAULT_COLOR;
		this.hits = hits;
		initializeTrace();
	}
	
	public void initializeTrace(){
		trace = new ArrayList<Coordinate>();
		this.sizeOfTrace = Math.max(2, Math.abs(speedX))*Variables.BALL_DEFAULT_TRACE_SIZE;
	}
	
	public void caluclatePosition(ArrayList<Obstacle> obstacleList) {
        for (int i = 0; i < Math.abs(speedX); i++) {
            //TRACE///////
            int created = 0;
            if (trace.size() == 0) {
                trace.add(new Coordinate(x, y, speedX));
                created = 1;
            } else if (trace.size() < sizeOfTrace) {
                Coordinate helpCor = trace.get(trace.size() - 1);
                trace.add(new Coordinate(helpCor));
                created = 1;
            }
            for (int tr = trace.size() - 1 - created; tr > 0; tr--) {
                trace.get(tr).setX(trace.get(tr - 1).getX());
                trace.get(tr).setY(trace.get(tr - 1).getY());
            }
            /////////////

            if (speedX > 0) x++;
            else if (speedX < 0) x--;
            if (speedY > 0) y++;
            else if (speedY < 0) y--;

            ////TRACE///
            trace.get(0).setX(x);
            trace.get(0).setY(y);
            ///////////

            if (x - radius / 2 == 0 || x + radius / 2 == Variables.WINDOW_DEFAULT_WIDTH) {
                speedX *= -1;
                hits.add(new Coordinate(x, y, speedX));
                hitWall = true;
                onWallHit();
            }
            if (y - radius / 2 == 0 || y + radius / 2 == Variables.WINDOW_DEFAULT_HEIGHT) {
                speedY *= -1;
                hits.add(new Coordinate(x, y, speedX));
                onWallHit();
            }

            for (Obstacle obs : obstacleList) {
                if (x - radius / 2 == obs.getX() + obs.getWidth() / 2 + 1 || x + radius / 2 == obs.getX() - obs.getWidth() / 2 - 1) {
                    if (y - radius / 2 <= obs.getY() + obs.getHeight() / 2 && y + radius / 2 >= obs.getY() - obs.getHeight() / 2) {
                        speedX *= -1;
                        hits.add(new Coordinate(x, y, true, speedX));
                        onObstacleHit();
                    }
                } else if (x - radius / 2 == obs.getX() - obs.getWidth() / 2 - 1 || x + radius / 2 == obs.getX() + obs.getWidth() / 2 + 1) {
                    if (y - radius / 2 <= obs.getY() + obs.getHeight() / 2 && y + radius / 2 >= obs.getY() - obs.getHeight() / 2) {
                        speedX *= -1;
                        hits.add(new Coordinate(x, y, true, speedX));
                        onObstacleHit();
                    }
                }
                if (y - radius / 2 == obs.getY() + obs.getHeight() / 2 + 1 || y + radius / 2 == obs.getY() - obs.getHeight() / 2 - 1) {
                    if (x - radius / 2 <= obs.getX() + obs.getWidth() / 2 && x + radius / 2 >= obs.getX() - obs.getWidth() / 2) {
                        speedY *= -1;
                        hits.add(new Coordinate(x, y, true, speedX));
                        onObstacleHit();
                    }
                } else if (y - radius / 2 == obs.getY() - obs.getHeight() / 2 - 1 || y + radius / 2 == obs.getY() + obs.getHeight() / 2 + 1) {
                    if (x - radius / 2 <= obs.getX() + obs.getWidth() / 2 && x + radius / 2 >= obs.getX() - obs.getWidth() / 2) {
                        speedY *= -1;
                        hits.add(new Coordinate(x, y, true, speedX));
                        onObstacleHit();
                    }
                }
            }
        }


    }

	//WHAT HAPPENS WHEN THE BALL HITS THE WALL
	public void onWallHit(){
	    raster.wallHit();
	}

	//WHAT HAPPENS WHEN THE BALL HITS THE OBSTACLE
	private void onObstacleHit(){
	    raster.obstacleHit();
	}
	public int getSpeedX() {
		return speedX;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}

	public int getSpeedY() {
		return speedY;
	}

	public void setSpeedY(int speedY) {
		this.speedY = speedY;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public int getRadius() {
		return radius;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public ArrayList<Coordinate> getTrace() {
		return trace;
	}

	public int getSizeOfTrace() {
		return sizeOfTrace;
	}
}
