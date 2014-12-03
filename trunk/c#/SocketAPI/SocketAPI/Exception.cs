using System;
using System.Collections.Generic;
using System.Text;

namespace TigerNetwork
{
	/// <summary>
	/// 서버에서 클라이언트로 전달되는 예외입니다.
	/// 서버에서 이 예외 이외의 다른 예외가 발생하면 해당 클라이언트와의 접속이 끊깁니다.
	/// </summary>
	public class TigerNetworkException : ApplicationException
	{
		public TigerNetworkException()
			: base()
		{
		}

		public TigerNetworkException( string message )
			: base( message )
		{
		}

		public TigerNetworkException( string message, params object[] args )
			: base( string.Format( message, args ) )
		{
		}

		public TigerNetworkException( string message, Exception innerException )
			: base( message, innerException )
		{
		}
	}
}
