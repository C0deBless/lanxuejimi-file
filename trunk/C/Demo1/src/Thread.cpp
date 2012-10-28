#include <windows.h>
#include "Thread.h"

using namespace std;

HANDLE CreateThread(LPSECURITY_ATTRIBUTES lpThreadAttributes, DWORD dwStackSize,
		LPTHREAD_START_ROUTINE lpStartAddress, LPVOID lpParameter,
		DWORD dwCreationFlags, LPDWORD lpThreadId);

Thread::Thread() {
//	hThread = CreateThread();
}

void Thread::start() {

}

Thread::~Thread() {

}

