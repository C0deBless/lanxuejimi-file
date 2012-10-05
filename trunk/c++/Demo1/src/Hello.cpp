/*
 * Hello.cpp
 *
 *  Created on: 2012-10-4
 *      Author: trnnn
 */

#include <iostream>
#include <windows.h>
using namespace std;

int main() {
	double start = GetTickCount(), end(0);
	cout << start << "\n";
	Sleep(1000);
	end = GetTickCount();
	cout << end << "\n";
	double result = end - start;
	cout << result;
	return 0;
}

