package hello

import groovy.transform.CompileStatic
import groovy.transform.TypeChecked


class Hello {
	static main(args) {
		def hello=new Hello();
		for (int i = 0; i < 3; i++) {
			hello.test();
		}
	}

	@CompileStatic
	test(){
		long d1=System.nanoTime();
		def book=new Book("Hello","TRNNN");
		long d2=System.nanoTime();
		println(d2-d1);
	}
}

@CompileStatic
class Book{
	String name
	String author
	def doSomething(){
		return this.name+"-"+this.author;
	}
}