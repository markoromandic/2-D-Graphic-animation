package view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRaster;
import model.Ball;
import model.Coordinate;
import model.Laser;
import model.Model;
import model.Obstacle;
import model.Particle;
import rafgfxlib.GameFrame;
import rafgfxlib.ImageViewer;
import rafgfxlib.Util;
import variables.Variables;

public class GameScreen extends GameFrame {

	private Model model;
	public GameScreen(String title, int sizeX, int sizeY, Model model) {
		super(title, sizeX, sizeY);
		this.model=model;
		setUpdateRate(60);
		startThread();
	}

	@Override
	public void handleWindowInit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleWindowDestroy() {
		// TODO Auto-generated method stub
		
	}
	
	private int helpAlpha, lowerPerCalc, helpLaserWidth, laserAlphaPart, helpLaserBrojac;;
	@Override
	public void render(Graphics2D g, int sw, int sh) {
		g.drawImage(model.getRaster().getBackground(), 0,0, Variables.WINDOW_DEFAULT_WIDTH, Variables.WINDOW_DEFAULT_HEIGHT , null);

		for(Particle p:model.getListOfParticles()){
			if(p.isObstacle()) g.setColor(new Color(Variables.PARTICLE_DEFAULT_COLOR_OBSTACLE_RED, Variables.PARTICLE_DEFAULT_COLOR_OBSTACLE_GREEN, Variables.PARTICLE_DEFAULT_COLOR_OBSTACLE_BLUE, p.getAlpha()));
			else g.setColor(new Color(Variables.PARTICLE_DEFAULT_COLOR_RED, Variables.PARTICLE_DEFAULT_COLOR_GREEN, Variables.PARTICLE_DEFAULT_COLOR_BLUE, p.getAlpha()));
			g.fillRect(p.getX()-2, p.getY()-2, 4, 4);
		}
		
		for(Ball b:model.getListOfBalls()){
			helpAlpha = 100;
			lowerPerCalc = 100/b.getSizeOfTrace();
			for(Coordinate c:b.getTrace()){
				g.setColor(new Color(Variables.BALL_DEFAULT_TRACE_COLOR_RED, Variables.BALL_DEFAULT_TRACE_COLOR_GREEN, Variables.BALL_DEFAULT_TRACE_COLOR_BLUE, helpAlpha));
				g.fillRect(c.getX()-1, c.getY()-1, 2, 2);
				helpAlpha -= lowerPerCalc;
			}
			g.setColor(b.getColor());
			g.fillOval(b.getX()-b.getRadius()/2, b.getY()-b.getRadius()/2, b.getRadius(), b.getRadius());
		}
		for(Obstacle o:model.getListOfObstacle()){
			g.setColor(o.getColor());
			g.drawRect(o.getX()-o.getWidth()/2, o.getY()-o.getHeight()/2, o.getWidth(), o.getHeight());
		}
		
		for(Laser l:model.getListOfLasers()){
			if(l.isWide()) helpLaserWidth = Variables.LASER_DEFAULT_WIDE_TRUE;
			else helpLaserWidth = Variables.LASER_DEFAULT_WIDE_FALSE;
			laserAlphaPart = 100/(helpLaserWidth/2);
			helpLaserBrojac = 1;
			for(int i = l.getX()-helpLaserWidth/2; i<=l.getX();i++){
				g.setColor(new Color(Variables.LASER_DEFAULT_COLOR_RED, Variables.LASER_DEFAULT_COLOR_GREEN, Variables.LASER_DEFAULT_COLOR_BLUE, helpLaserBrojac*laserAlphaPart));
				g.drawLine(i, 0, i, Variables.WINDOW_DEFAULT_HEIGHT);
				helpLaserBrojac++;
			}
			for(int i = l.getX(); i<=l.getX()+helpLaserWidth/2;i++){
				g.setColor(new Color(Variables.LASER_DEFAULT_COLOR_RED, Variables.LASER_DEFAULT_COLOR_GREEN, Variables.LASER_DEFAULT_COLOR_BLUE, helpLaserBrojac*laserAlphaPart));
				g.drawLine(i, 0, i, Variables.WINDOW_DEFAULT_HEIGHT);
				helpLaserBrojac--;
			}
			helpLaserBrojac = 1;
			for(int i = l.getY()-helpLaserWidth/2; i<=l.getY();i++){
				g.setColor(new Color(Variables.LASER_DEFAULT_COLOR_RED, Variables.LASER_DEFAULT_COLOR_GREEN, Variables.LASER_DEFAULT_COLOR_BLUE, helpLaserBrojac*laserAlphaPart));
				g.drawLine(0, i, Variables.WINDOW_DEFAULT_WIDTH, i);
				helpLaserBrojac++;
			}
			for(int i = l.getY(); i<=l.getY()+helpLaserWidth/2;i++){
				g.setColor(new Color(Variables.LASER_DEFAULT_COLOR_RED, Variables.LASER_DEFAULT_COLOR_GREEN, Variables.LASER_DEFAULT_COLOR_BLUE, helpLaserBrojac*laserAlphaPart));
				g.drawLine(0, i, Variables.WINDOW_DEFAULT_WIDTH, i);
				helpLaserBrojac--;
			}
		}
		if(createObstacle){
			g.setColor(Variables.OBSTACLE_DEFAULT_COLOR_PREVIEW);
			g.drawRect(Math.min(xDown, xPreview), Math.min(yDown, yPreview), Math.max(xDown, xPreview)-Math.min(xDown, xPreview), Math.max(yDown, yPreview)-Math.min(yDown, yPreview));
		}
		
	}

	@Override
	public void update() {

		// TODO Auto-generated method stub
		if(createLaser){
			createLaser = false;
			model.laser(laserCor);
		}
		model.calculateFrame();
	}

	private int xDown, yDown, xUp, yUp, obsWidth, obsHeight, xPreview, yPreview;
	private boolean bPressed = false, createObstacle=false, lPressed = false, createLaser = false;
	Coordinate laserCor;
	@Override
	public void handleMouseDown(int x, int y, GFMouseButton button) {
		if(bPressed){
			model.setGenerateBall(true);
		} else if(lPressed){
			laserCor = new Coordinate(x, y, 0);
			createLaser = true;
		} else{
			xDown = x;
			yDown = y;
			xPreview = x;
			yPreview = y;
			createObstacle = true;
		}
	}

	@Override
	public void handleMouseUp(int x, int y, GFMouseButton button) {
		// TODO Auto-generated method stub
		if(createObstacle){
			createObstacle = false;
			xUp = x;
			yUp = y;
			obsWidth = Math.abs(xUp - xDown);
			obsHeight = Math.abs(yUp - yDown);
			model.addObstacle((xDown+xUp)/2, (yDown+yUp)/2, obsWidth, obsHeight);
		}
	}

	@Override
	public void handleMouseMove(int x, int y) {
		// TODO Auto-generated method stub
		if(createObstacle){
			xPreview = x;
			yPreview = y;
		}
	}

	@Override
	public void handleKeyDown(int keyCode) {
//		System.out.println("SMT PRESSED");
		if((char)keyCode=='B'){
			bPressed=true;
		} else if((char)keyCode=='L'){
//			System.out.println("L PRESSED");
			lPressed=true;
		}
	}

	@Override
	public void handleKeyUp(int keyCode) {
		// TODO Auto-generated method stub
//		System.out.println("SMT UP");
		bPressed = false;
		lPressed = false;
	}

}
