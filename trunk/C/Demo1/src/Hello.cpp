/*
 * Hello.cpp
 *
 *  Created on: 2012-10-5
 *      Author: trnnn
 */

#include <iostream>
using namespace std;

int main() {

	int array[5] = { 0, 1, 2, 3, 4 };
	int var = 0;
	for (var = 0; var < 8; ++var) {
		cout << array[var] << "\n";
	}
	cout << "Hello world";
	return 0;
}
