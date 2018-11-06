public class Spaceship {

	private int x;
	private int y;
	private double speed;
	private double angle;
	private int scale = 1;
	private final double MAXSPEED = 10; 
	private boolean destroyed;


	public Spaceship(int xloc, int yloc, double theta) {
		x = xloc;
		y = yloc;
		speed = 0;
		angle = theta;
		destroyed = false;
	}

	public void move() {
		this.x += scale*this.speed * -1*Math.sin(angle);
		this.y += scale*this.speed * Math.cos(angle);
	}

	public int getX () {
		return this.x;
	}

	public int getY () {
		return this.y;
	}

	public void setX (int a) {
		this.x = a;
	}

	public void setY (int a) {
		this.y = a;
	}

	public double getSpeed () {
		return this.speed;
	}

	public double getAngle () {
		return this.angle;
	}

	public boolean getDestroyed() {
		return this.destroyed;
	}


	public void updateSpeed(double x) {
		this.speed += x;

		if (this.speed > MAXSPEED) {
			this.speed = MAXSPEED;
		}
		else if (this.speed < -1*MAXSPEED) {
			this.speed = -1*MAXSPEED;
		}
	} 

	public void updateAngle(double x) {
		this.angle += x;
	}

	public Laser shoot() {
		return new Laser(this.x,this.y,this.angle);
	}  

	public void destroy() {
		this.destroyed = true;
	}
	
	public void respawn() {
		this.destroyed = false;
	}

}