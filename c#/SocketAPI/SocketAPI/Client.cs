using System;
using System.Net;
using System.Net.Sockets;

namespace TigerNetwork
{
	/// <summary>
	/// 기본 클라이언트 개체입니다. 이 개체는 스레드에 세이프 하지 않습니다.
	/// </summary>
	public class Client
	{
		Socket m_Socket;
		SocketAsyncManager m_SocketManager;
		IPEndPoint m_LastEndPoint;

		public event EventHandler<ReceiveEventArgs> Receive;
		public event EventHandler<CloseEventArgs> Close;
		public event EventHandler<ErrorEventArgs> Error;

		public IPAddress Address
		{
			get
			{
				if ( m_LastEndPoint != null )
				{
					return m_LastEndPoint.Address;
				}
				return null;
			}
		}

		public int Port
		{
			get
			{
				if ( m_LastEndPoint != null )
				{
					return m_LastEndPoint.Port;
				}
				return 0;
			}
		}

		public bool Connected
		{
			get
			{
				return this.m_Socket != null && this.m_Socket.Connected;
			}
		}

		public Client()
		{
		}

		public bool ConnectDNS( string address, int port )
		{
			return Connect( Dns.GetHostEntry( address ).AddressList[0], port );
		}

		public bool Connect( IPAddress ip, int port )
		{
			if ( m_Socket != null )
			{
				throw new InvalidOperationException( "Already activating!" );
			}
			m_LastEndPoint = new IPEndPoint( ip, port );

			m_Socket = new Socket( AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp );
 			try
 			{
				m_Socket.Connect( m_LastEndPoint );

				m_SocketManager = new SocketAsyncManager( m_Socket, 0 );

				m_SocketManager.Receive +=
					delegate( object sender, ReceiveEventArgs e )
					{
						OnReceive( e );
					};

				m_SocketManager.Close +=
					delegate( object sender, CloseEventArgs e )
					{
						OnClose( e );
						m_Socket = null;
					};

				m_SocketManager.Error +=
					delegate( object sender, ErrorEventArgs e )
					{
						OnError( e );
					};

				m_SocketManager.Start();

				return true;
			}
 			catch
 			{
 				m_Socket.Close();
 				m_Socket = null;
 
 				return false;
 			}
		}

		public void Stop()
		{
			if ( m_Socket == null )
			{
				return;
			}

			m_SocketManager.Stop( false );
			m_Socket = null;
		}

		public void Send( Packet packet )
		{
            using (packet)
            {
                m_SocketManager.Send(packet.GetBytes());
            }
		}

		protected virtual void OnReceive( ReceiveEventArgs e )
		{
			if ( Receive != null )
			{
				Receive( this, e );
			}
		}

		protected virtual void OnClose( CloseEventArgs e )
		{
			if ( Close != null )
			{
				Close( this, e );
			}
		}

		protected virtual void OnError( ErrorEventArgs e )
		{
			if ( Error != null )
			{
				Error( this, e );
			}
		}
	}
}