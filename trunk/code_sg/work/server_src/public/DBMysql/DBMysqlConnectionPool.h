// DBMysqlConnectionPool.h: interface for the DBMysqlConnectionPool class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_DBMYSQLCONNECTIONPOOL_H__ED6CFC6B_4D88_4641_9D05_580C2D7AE7D6__INCLUDED_)
#define AFX_DBMYSQLCONNECTIONPOOL_H__ED6CFC6B_4D88_4641_9D05_580C2D7AE7D6__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include <ace/Task.h>

//class DBMysqlConnectionPool;
//typedef ACE_Singleton <DBMysqlConnectionPool, ACE_Null_Mutex>
//DBMysqlCPool;
#include "DBMysqlConnection.h"


#if _MSC_VER == 1200	//--VC6

//--only for edit
class DBMysqlConnectionPool
{
protected:
	void init(
		const char* db = "test_db"
		, const char* server = "localhost"
		, const char* user = "test"
		, const char* password = "123"
		)
	{
	}
public:
	//DBMysqlConnectionPool();
	virtual ~DBMysqlConnectionPool();
};

#else	//--not VC6

//--xx2008_10_30--cpool.cpp - ConnectionPool example.
// Define a concrete ConnectionPool derivative.  Takes connection
// parameters as inputs to its ctor, which it uses to create the
// connections we're called upon to make.  Note that we also declare
// a global pointer to an object of this type, which we create soon
// after startup; this should be a common usage pattern, as what use
// are multiple pools?
//--class SimpleConnectionPool : public mysqlpp::ConnectionPool
class DBMysqlConnectionPool : public MysqlConnectionPool  
{
	ACE_Thread_Mutex ace_mutex_;
	bool init_;// = false;//--init flag
public:
protected:
	void init(
		const char* db = "test_db"
		, const char* server = "localhost"
		, const char* user = "test"
		, const char* password = "123"
		)
	{
		db_ = db;
		server_	= server;
		user_	= user;
		password_=password;
		
		init_ = true;
	}
public:
	DBMysqlConnection* get() { return (DBMysqlConnection*)grab(); }
	void free(DBMysqlConnection* conn) { release(conn); }
public:
	// The object's only constructor
//--	SimpleConnectionPool(const char* db, const char* server,
//--			const char* user, const char* password) :
	DBMysqlConnectionPool() : conns_in_use_(0)
//--	conns_in_use_(0),
//--	db_(db ? db : ""),
//--	server_(server ? server : ""),
//--	user_(user ? user : ""),
//--	password_(password ? password : "")
	{
		db_ = "test_db";
		server_	= "localhost";
		user_	= "test";
		password_	= "123";
		//--need user init(...) first
		init_ = false;
	}

	~DBMysqlConnectionPool();
//--	// The destructor.  We _must_ call ConnectionPool::clear() here,
//--	// because our superclass can't do it for us.
//--	~SimpleConnectionPool()
//--	{
//--		clear();
//--	}

	// Do a simple form of in-use connection limiting: wait to return
	// a connection until there are a reasonably low number in use
	// already.  Can't do this in create() because we're interested in
	// connections actually in use, not those created.  Also note that
	// we keep our own count; ConnectionPool::size() isn't the same!
	mysqlpp::Connection* grab()
	{
		ACE_Guard<ACE_Thread_Mutex> guard(ace_mutex_);
		if (!init_) return 0;
//--		while (conns_in_use_ > 8) {
//--			cout.put('R'); cout.flush(); // indicate waiting for release
//--			sleep(1);
//--		}

		++conns_in_use_;
//--		return mysqlpp::ConnectionPool::grab();
		return MysqlConnectionPool::grab();
	}

	// Other half of in-use conn count limit
	void release(const mysqlpp::Connection* pc)
	{
		ACE_Guard<ACE_Thread_Mutex> guard(ace_mutex_);
		if (!pc) return;
//--		mysqlpp::ConnectionPool::release(pc);
		MysqlConnectionPool::release(pc);
		--conns_in_use_;
	}

protected:
	// Superclass overrides
	mysqlpp::Connection* create()
	{
		ACE_Guard<ACE_Thread_Mutex> guard(ace_mutex_);
		if (!init_) return 0;
//--		// Create connection using the parameters we were passed upon
//--		// creation.  This could be something much more complex, but for
//--		// the purposes of the example, this suffices.
//--		cout.put('C'); cout.flush(); // indicate connection creation
//--		return new mysqlpp::Connection(
		return new DBMysqlConnection(
				this, 
				db_.empty() ? 0 : db_.c_str(),
				server_.empty() ? 0 : server_.c_str(),
				user_.empty() ? 0 : user_.c_str(),
				password_.empty() ? "" : password_.c_str());
	}

	void destroy(mysqlpp::Connection* cp)
	{
		ACE_Guard<ACE_Thread_Mutex> guard(ace_mutex_);
		if (!cp) return;
//--		// Our superclass can't know how we created the Connection, so
//--		// it delegates destruction to us, to be safe.
//--		cout.put('D'); cout.flush(); // indicate connection destruction
		delete cp;
	}

	unsigned int max_idle_time()
	{
		// Set our idle time at an example-friendly 3 seconds.  A real
		// pool would return some fraction of the server's connection
		// idle timeout instead.
		return 3;
	}

private:
	// Number of connections currently in use
	unsigned int conns_in_use_;

	// Our connection parameters
	std::string db_, server_, user_, password_;
};

#endif	//--not VC6

#endif // !defined(AFX_DBMYSQLCONNECTIONPOOL_H__ED6CFC6B_4D88_4641_9D05_580C2D7AE7D6__INCLUDED_)
