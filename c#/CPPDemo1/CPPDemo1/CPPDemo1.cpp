// CPPDemo1.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"
#include <iostream>

using namespace std;

class Class{
private:
	
public:
	int x = 2;
	Class();
	Class(Class& c);
};

Class::Class(){

}

Class::Class(Class& c){
	cout << "copy contructor invoked" << endl;
}

void method(Class& c){
	c.x = 1;
	cout << "method invoked" << endl;
}

int _tmain(int argc, _TCHAR* argv[])
{
	Class c;
	cout << "x = " << c.x << endl;
	method(c);
	cout << "x = " << c.x << endl;
	cout << sizeof c << endl;
	getchar();
	return 0;
}


