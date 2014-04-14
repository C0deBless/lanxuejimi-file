// Demo1.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"
#include <thread>
#include <iostream>
#include "windows.h"

void my_thread()
{
	while (true){
		puts("hello, world");
		Sleep(1000);
	}
}

int _tmain(int argc, _TCHAR* argv[])
{
	std::thread t(my_thread);
	t.join();
	std::cin.get();
	return 0;
}

