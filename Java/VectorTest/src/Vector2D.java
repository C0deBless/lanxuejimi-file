public class Vector2D {
	public double x;
	public double y;

	public Vector2D() {

	}

	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double length() {
		double d = Math.pow(x, 2) + Math.pow(y, 2);
		return Math.pow(d, 0.5);
	}

	public Vector2D add(Vector2D v) {
		Vector2D vec = new Vector2D();
		vec.x = this.x + v.x;
		vec.y = this.y + v.y;
		return vec;
	}

	public static double angle(Vector2D v1, Vector2D v2) {
		double d = v1.x * v2.x + v1.y * v2.y;
		float cos = (float) (d / (v1.length() * v2.length()));
		if (cos > 1) {
		}
		return Math.acos(cos);
	}

	public static Vector2D getMirrorVector(Vector2D vm, Vector2D vv) {
		double mx = vm.x;
		double my = vm.y;
		double vx = vv.x;
		double vy = vv.y;
		double M = Math.sqrt(Math.pow(mx, 2) + Math.pow(my, 2));
		double D = -vx * mx - vy * my;
		double A = 2 * D / Math.pow(M, 2);
		double x = vx + mx * A;
		double y = vy + my * A;
		Vector2D vec = new Vector2D(x, y);
		return vec;
	}

	public static Vector2D getVectorReflection(Vector2D v1, Vector2D v2) {
		double d1 = v1.x * v2.x + v1.y * v2.y;
		double d2 = d1 / Math.pow(v2.length(), 2);
		Vector2D v = new Vector2D();
		v.x = v2.x * d2;
		v.y = v2.y * d2;
		return v;
	}

	public static void main(String args[]) {
		Vector2D vm = new Vector2D(1, 0);
		Vector2D vv = new Vector2D(-1, -1);
		Vector2D vec = getMirrorVector(vm, vv);
		Vector2D vec2 = getVectorReflection(vv, vm);
		System.out.println(vec.x + "," + vec.y);
		System.out.println(vec2.x + "," + vec2.y);
	}
}
