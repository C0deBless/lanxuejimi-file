// stdafx.h : include file for standard system include files,
//  or project specific include files that are used frequently, but
//      are changed infrequently
//

#if !defined(AFX_STDAFX_H__75EEB3D6_AF32_4399_9D3A_3712DB81BEA9__INCLUDED_)
#define AFX_STDAFX_H__75EEB3D6_AF32_4399_9D3A_3712DB81BEA9__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#ifndef _WIN32_WINNT 
# define _WIN32_WINNT 0x500 
#endif

#define VC_EXTRALEAN		// Exclude rarely-used stuff from Windows headers

#include <afxwin.h>         // MFC core and standard components
#include <afxext.h>         // MFC extensions
#include <afxdtctl.h>		// MFC support for Internet Explorer 4 Common Controls
#ifndef _AFX_NO_AFXCMN_SUPPORT
#include <afxcmn.h>			// MFC support for Windows Common Controls
#endif // _AFX_NO_AFXCMN_SUPPORT

//--xx2008_10_16--
#include <winsock2.h>         // MFC extensions
//--xx2008_10_16--
#include <afxsock.h>		// MFC socket extensions

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_STDAFX_H__75EEB3D6_AF32_4399_9D3A_3712DB81BEA9__INCLUDED_)
