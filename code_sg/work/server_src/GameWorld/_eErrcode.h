// _eErrcode.h: interface for the _eErrcode class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX__EERRCODE_H__D1C40B86_C8E4_43CE_BA66_52DAB729C17F__INCLUDED_)
#define AFX__EERRCODE_H__D1C40B86_C8E4_43CE_BA66_52DAB729C17F__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

class _eErrcode {};

//--错误代码/负数
enum EErrcode
{
	EE_ERROR_CODE	= -1,//--默认错误

	EE_NOMONEY_GOLD	= -2,//--金币不够

	EE_ECode1	= -10000,//--

	//--MisDaily
	EE_MISDAILY_BUY5,//--最多增加5个任务，已经够了
	EE_MISDAILY_NULL,//--没有任务

};

#endif // !defined(AFX__EERRCODE_H__D1C40B86_C8E4_43CE_BA66_52DAB729C17F__INCLUDED_)
