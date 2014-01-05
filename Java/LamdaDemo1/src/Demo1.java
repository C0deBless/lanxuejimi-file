public class Demo1 {

	public static void main(String[] args) {
		Service s = (int a, int b) -> {
			return a + b;
		};
		int c = s.add(1, 2);
		System.out.println(c);

	}
}

interface Service {
	public int add(int a, int b);
}