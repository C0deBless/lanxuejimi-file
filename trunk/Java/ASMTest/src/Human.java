import java.nio.ByteBuffer;

public class Human {
	private String name;
	private int age;

	public Human() {

	}

	// 序列化的时候 把需要序列化的数据 放到ByteBuffer 里头就可以了
	public void serialize(ByteBuffer buffer) {

		// 插入 name
		// 插入字符串的时候先插入字符串的长度，后面跟着字符串变字节数组后的数据
		int size = 0;
		if (name != null) {
			byte[] bytes = name.getBytes();
			size = bytes.length;
			buffer.putInt(size);
			buffer.put(bytes);
		} else {
			buffer.putInt(size);
		}

		buffer.putInt(age);
	}

	public static Human deserialize(ByteBuffer buffer) {

		return null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public static void main(String[] args) {
		Human hu=null;
		test(hu);
		System.out.println(hu.getName());
	}

	public static void test(Human hu) {
		hu=new Human();
		hu.setName("name");
	}
}
