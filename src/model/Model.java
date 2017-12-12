package model;

import java.util.ArrayList;
import java.util.Random;

import variables.Variables;

public class Model {

	private ArrayList<Ball> listOfBalls,laserToRemove;
	private ArrayList<Obstacle> listOfObstacle;
	private ArrayList<Coordinate> listOfHits;
	private ArrayList<Particle> listOfParticles, toRemove;
	private ArrayList<Laser> listOfLasers, listOfLasersToRemove;
	private boolean generateBall=false;
	private Raster raster;
	
	public Model() {
		super();
		this.listOfBalls = new ArrayList<>();
		this.listOfObstacle = new ArrayList<>();
		this.listOfHits = new ArrayList<>();
		this.listOfParticles = new ArrayList<>();
		this.toRemove = new ArrayList<>();
		this.laserToRemove = new ArrayList<>();
		this.listOfLasers = new ArrayList<>();
		this.listOfLasersToRemove = new ArrayList<>();
	    this.raster = new Raster(this);
	}
	
	public void setUpGame(){
		for(int i=0; i<Variables.DEFAULT_NUMBER_OF_BALLS; i++){
			addRandomBall();
		}
	}
	
	public void addRandomBall(){
		Random r = new Random();
		int speed;
		int konst1, konst2;
		int radius = Math.max(4, r.nextInt(10));
		if(radius%2==1) radius = 4;
		speed = Math.max(1, r.nextInt(8));
		if(r.nextBoolean())
			konst1 = 1;
		else konst1 = -1;
		if(r.nextBoolean())
			konst2 = 1;
		else konst2 = -1;
		addBall(r.nextInt(800), r.nextInt(800), Math.max(3, r.nextInt(10)), speed*konst1, speed*konst2);
	}

	public void addBall(){
		listOfBalls.add(new Ball(listOfHits));
	}

	public void addBall(int x, int y){
		listOfBalls.add(new Ball(x, y, listOfHits));
	}
	
	public void addBall(int x, int y, int radius, int speedX, int speedY){
		listOfBalls.add(new Ball(x, y, radius, speedX, speedY, listOfHits, raster));
	}



	public void addObstacle(int x, int y){
		listOfObstacle.add(new Obstacle(x, y));
	}
	
	public void addObstacle(int x, int y, int width, int height){
		listOfObstacle.add(new Obstacle(x, y, width, height));
	}
	
	public void calculateFrame(){
		if(generateBall){
			addRandomBall();
			generateBall=false;
		}
		Random r = new Random();
		for(Ball currentBall:listOfBalls){
			currentBall.caluclatePosition(listOfObstacle);
		}
		
		for(Particle p:listOfParticles){
			p.calculatePosition();
			if(p.getDuration()<=0){
				toRemove.add(p);
			}
		}
		listOfParticles.removeAll(toRemove);
		toRemove.clear();
		for(Coordinate c:listOfHits){
			for(int i=0; i<Variables.DEFAULT_NUMBER_OF_PARTICLES; i++){
				int helpSpeedX = c.getSpeedWhenHit();
				int helpSpeedY = c.getSpeedWhenHit();
				int helpFactor = r.nextInt(3);
				if(r.nextBoolean())helpSpeedX-=helpFactor;
				else helpSpeedX+=helpFactor;
				helpFactor = r.nextInt(3);
				if(r.nextBoolean())helpSpeedY-=helpFactor;
				else helpSpeedY+=helpFactor;
				if(r.nextBoolean()) helpSpeedX*=-1;
				if(r.nextBoolean()) helpSpeedY*=-1;
				int helpDuration = Math.max(20,r.nextInt(90));
				if(c.isObstacle()) listOfParticles.add(new Particle(c.getX(), c.getY(), helpSpeedX, helpSpeedY, helpDuration, true));
				else listOfParticles.add(new Particle(c.getX(), c.getY(), helpSpeedX, helpSpeedY, helpDuration));
			}
		}
		listOfHits.clear();
		for(Laser l:listOfLasers){
			l.lowerDuration();
			if(l.getDuration()<=0){
				listOfLasersToRemove.add(l);
			}
		}
		listOfLasers.removeAll(listOfLasersToRemove);
		listOfLasersToRemove.clear();
	}
	
	public void laser(Coordinate laserCor){
		listOfLasers.add(new Laser(laserCor.getX(), laserCor.getY()));
		for(Ball b:listOfBalls){
			if((b.getX()>=laserCor.getX()-Variables.LASER_DEFAULT_WIDTH_FOR_CHECK && b.getX()<=laserCor.getX()+Variables.LASER_DEFAULT_WIDTH_FOR_CHECK) || (b.getY()>=laserCor.getY()-Variables.LASER_DEFAULT_WIDTH_FOR_CHECK && b.getY()<=laserCor.getY()+Variables.LASER_DEFAULT_WIDTH_FOR_CHECK)){
				laserToRemove.add(b);
			}
		}
		listOfBalls.removeAll(laserToRemove);
		laserToRemove.clear();
	}

	public ArrayList<Ball> getListOfBalls() {
		return listOfBalls;
	}

	public ArrayList<Obstacle> getListOfObstacle() {
		return listOfObstacle;
	}

	public ArrayList<Coordinate> getListOfHits() {
		return listOfHits;
	}

	public ArrayList<Particle> getListOfParticles() {
		return listOfParticles;
	}

	public void setGenerateBall(boolean generateBall) {
		this.generateBall = generateBall;
	}

	public ArrayList<Laser> getListOfLasers() {
		return listOfLasers;
	}

    public Raster getRaster() {
        return raster;
    }
}
