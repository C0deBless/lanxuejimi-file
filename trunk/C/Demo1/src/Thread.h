/*
 * Thread.h
 *
 *  Created on: 2012-10-28
 *      Author: Qingfeng
 */

#ifndef THREAD_H_
#define THREAD_H_

class Thread {
	HANDLE hThread;
public:
	Thread();
	void start();
	bool setThreadPriorigy(int nPriority);
	void exit(DWORD dwExitCode);
	bool terminate(DWORD dwExitCode);
	virtual ~Thread();
};

#endif /* THREAD_H_ */
