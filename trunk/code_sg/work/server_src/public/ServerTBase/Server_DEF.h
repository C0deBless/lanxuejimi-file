//#include "Server_DEF.h"
#ifndef _xxSERVER_DEF_H_
#define _xxSERVER_DEF_H_

//#ifdef	_DEBUG
////--only for Visual Studio Workspace read code
//#define	VSW_code(a)	void declare_##a() {}
//#else
//#define	VSW_code(a)
//#endif

//--this Process thread
#define	this_ace_debug()	ACE_DEBUG ((LM_DEBUG, "[p%@](P%P)(t%t)\n", this))//;

class Server_DEF  
{
public:
	Server_DEF();
	virtual ~Server_DEF();

};

#endif //--_xxSERVER_DEF_H_
