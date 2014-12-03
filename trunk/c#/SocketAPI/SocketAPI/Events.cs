using System;

namespace TigerNetwork
{
	public class ConnectEventArgs : EventArgs
	{
		int m_ID;
		SocketAsyncManager socketManager;

		public int ID
		{
			get
			{
				return m_ID;
			}
		}

		public SocketAsyncManager SocketManager
		{
			get
			{
				return this.socketManager;
			}
		}

		public ConnectEventArgs(int id, SocketAsyncManager socketManager)
		{
			m_ID = id;
			this.socketManager = socketManager;
		}
	}

	/// <summary>
	/// 네트워크로부터 데이터를 송신하였을 때 호출되는 이벤트의 인자 형식입니다.
	/// </summary>
	public class ReceiveEventArgs : EventArgs
	{
		int m_ID;
		Packet m_Packet;

		public int ID
		{
			get
			{
				return m_ID;
			}
		}

		public Packet Packet
		{
			get
			{
				return m_Packet;
			}
		}

		public ReceiveEventArgs( int id, Packet packet )
		{
			m_ID = id;
			m_Packet = packet;
		}
	}

	public class CloseEventArgs : EventArgs
	{
		int m_ID;

		public int ID
		{
			get
			{
				return m_ID;
			}
		}

		public CloseEventArgs( int id )
		{
			m_ID = id;
		}
	}

	public class StopEventArgs : EventArgs
	{

	}

	public class ErrorEventArgs : EventArgs
	{
		int m_ID;
		Exception m_Exception;

		public int ID
		{
			get
			{
				return m_ID;
			}
		}

		public Exception Exception
		{
			get
			{
				return m_Exception;
			}
		}

		public ErrorEventArgs( int id, Exception exception )
		{
			m_ID = id;
			m_Exception = exception;
		}
	}
}
