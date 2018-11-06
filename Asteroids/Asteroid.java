public class Asteroid {

	private double x;
	private double y;
	private double speedx;
	private double speedy;
	private int size;
	private boolean destroyed;


	public Asteroid() {
		x = 0;
		y = 0;
		speedx = 0;
		speedy = 0;
		size = 0;
	}

	private int radius = 100;

	public Asteroid(int sz, Spaceship p) {
		x = (Math.random()*1280);
		y = (Math.random()*800);

		while (Math.abs(x-p.getX()) < radius) {
			x = (Math.random()*1280);
		}

		while (Math.abs(y-p.getY()) < 100) {
			y = (Math.random()*800);
		}
		speedx = (Math.random()*20 - 10);
		speedy = (Math.random()*20 - 10);
		size = sz;
		destroyed = false;
	}

		public Asteroid(int sz) {
		x = (Math.random()*1280);
		y = (Math.random()*800);

		/*while (Math.abs(x-p.getX()) < radius) {
			x = (Math.random()*1280);
		}

		while (Math.abs(y-p.getY()) < 100) {
			y = (Math.random()*800);
		}*/
		speedx = (Math.random()*20 - 10);
		speedy = (Math.random()*20 - 10);
		size = sz;
		destroyed = false;
	}

	public void move() {
		this.x += this.speedx;
		this.y += this.speedy;

		
	}

	public double getX () {
		return this.x;
	}

	public double getY () {
		return this.y;
	}

	public boolean getDestroyed () {
		return this.destroyed;
	}

	public void setX (int a) {
		this.x = a;
	}

	public void setY (int a) {
		this.y = a;
	}

	public double getSpeedX () {
		return this.speedx;
	}

	public double getSpeedY () {
		return this.speedy;
	}

	public void destroy() {
		destroyed = true;
	}

}

