package com.easymode.demo2;

public class Demo9 {
	public static void main(String[] args) {
		Animal ani = new Animal();
		ani.eat();

		Cat cat = new Cat();
		cat.eat();

		Dog dog = new Dog();
		dog.eat();

		int a = Animal.a;
		Animal.test();
	}
}

class Animal {
	static int a = 10;
	int height = 170;
	int age = 26;

	public static void test() {
		System.out.println("测试方法");
	}

	protected void eat() {
		System.out.println("动物在吃");
	}
}

class Cat extends Animal {

	// 重写
	public void eat() {
		System.out.println("猫在吃");
	}
}

class Dog extends Animal {
	public void eat() {
		System.out.println("狗在吃");
	}
}