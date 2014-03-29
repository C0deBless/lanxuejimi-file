package socket;

public class RecursionDemo {

	public static void main(String[] args) {
		add(100);
	}

	public static void add(int a) {
		int b = a+1;
		System.out.println(b);
		if(b > 1000){
			return;
		}
		add(b);
	}
}
