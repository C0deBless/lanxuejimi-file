package draw;


public class Vector {
	public static final Vector ORIGEN_VECTOR = new Vector(1, 0);
	public double x;
	public double y;

	public Vector() {
	}

	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Vector add(Vector v) {
		Vector v2 = new Vector();
		v2.x = x + v.x;
		v2.y = y + v.y;
		return v2;
	}

	public Vector substract(Vector v) {
		Vector v2 = new Vector();
		v2.x = x - v.x;
		v2.y = y - v.y;
		return v2;
	}

	public double length() {
		return Math.pow(this.lengthSQ(), 0.5);
	}

	public double lengthSQ() {
		return (x * x + y * y);
	}

	public void normalize() {
		double length = this.length();
		if (length == 0) {
			// logger.error("Vector.normalize, error, x:{}, y:{}", x, y);
			// throw new IllegalStateException("vector length is Zero");
			this.x = 0;
			this.y = 0;
		} else {
			this.x /= length;
			this.y /= length;
		}
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public Vector scale(double scale) {
		this.x *= scale;
		this.y *= scale;
		return this;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public Vector clone() {
		return new Vector(x, y);
	}

	public Vector rotate90() {
		double x1 = y;
		double y1 = -x;
		this.x = x1;
		this.y = y1;
		return this;
	}

	// rotate clockwise
	public Vector rotate(double angle) {
		double x1 = this.x * Math.cos(angle) - this.y * Math.sin(angle);
		double y1 = this.y * Math.cos(angle) + this.x * Math.sin(angle);
		this.x = x1;
		this.y = y1;
		return this;
	}

	public Vector rotateAndClone(double angle) {
		this.rotate(angle);
		return this.clone();
	}

	public Vector cloneAndRotate(double angle) {
		Vector v = this.clone();
		return v.rotate(angle);
	}

	public double dotProduct(Vector v) {
		return v.x * x + v.y * y;
	}

	public void incrementBy(Vector v) {
		this.x += v.x;
		this.y += v.y;
	}

	public double angle(Vector v) {
		double length1 = this.length();
		double length2 = v.length();
		double angle = Math.acos(this.dotProduct(v) / (length1 * length2));
		return angle;
	}

	public Vector normalVector() {
		return new Vector(-y, x);
	}

	public double angleClockwise() {
		return this.angleClockwise(ORIGEN_VECTOR);
	}

	public double angleClockwise(Vector v) {
		Vector normalVector = this.normalVector();
		double dot = normalVector.dotProduct(v);
		if (dot < 0) {
			return this.angle(v);
		} else {
			return -this.angle(v);
		}
	}

	public String toString() {
		return x + "," + y;
	}

	public boolean isZero() {
		return this.x == 0 && this.y == 0;
	}

	public Vector rotate(double cx, double cy, double degree) {
		double r = Math.PI / 180;
		double radian = degree * r;
		double dx = (Math.cos(radian) * (x - cx))
				- (Math.sin(radian) * (y - cy));
		double dy = (Math.sin(radian) * (x - cx))
				+ (Math.cos(radian) * (y - cy));
		return new Vector(dx + cx, dy + cy);

	}

	public Vector add(double x, double y) {
		return new Vector(this.x + x, this.y + y);
	}
}
