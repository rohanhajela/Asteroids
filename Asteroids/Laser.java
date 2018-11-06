public class Laser{
	private int x;
	private int y;
	private final double SPEED = 20;
	private double angle;
	private boolean destroyed;

	private int scale = 1;

	public Laser(int a, int b, double theta) {
		x = a;
		y = b;
		angle = theta;
		destroyed = false;
	}

	public void move() {
		this.x += scale*this.SPEED * Math.sin(angle);
		this.y += scale*this.SPEED * -1*Math.cos(angle);
	}

	public int getX () {
		return this.x;
	}

	public int getY () {
		return this.y;
	}

	public boolean getDestroyed () {
		return this.destroyed;
	}

	public double getAngle () {
		return this.angle;
	}

	public void destroy() {
		destroyed = true;
	}
}