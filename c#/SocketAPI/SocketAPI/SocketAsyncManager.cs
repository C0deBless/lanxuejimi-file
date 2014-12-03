using System;
using System.Collections.Generic;
using System.Threading;
using System.Net;
using System.Net.Sockets;

namespace TigerNetwork
{
	/// <summary>
	/// 연결이 완료된 소켓에 대해서 읽기 및 쓰기, 접속 해제를 비동기로 지원하는 1회용 클래스입니다.
	/// 소켓이 닫히면 이 클래스의 멤버는 어떤것도 작동하지 않게 됩니다.
	/// 다중 스레드에 세이프합니다.
	/// </summary>
	public class SocketAsyncManager
	{
		int m_ID;
		Socket m_Socket;
		IPEndPoint m_IPEndPoint;
		int m_Closed;

		bool m_Sending;
		Queue<byte[]> m_SendQueue;
		AsyncCallback m_SendReady;
		byte[] m_SendBuffer;
		int m_SendPosition;

		byte[] m_RecvBuffer;
		AsyncCallback m_RecvReady;
		FlexibleMemory m_RecvAccBuffer;

		public event EventHandler<ReceiveEventArgs> Receive;
		public event EventHandler<CloseEventArgs> Close;
		public event EventHandler<ErrorEventArgs> Error;

		public int ID
		{
			get
			{
				return m_ID;
			}
		}

		public IPAddress IPAddress
		{
			get
			{
				return m_IPEndPoint.Address;
			}
		}

		public int Port
		{
			get
			{
				return m_IPEndPoint.Port;
			}
		}

		public SocketAsyncManager( Socket socket, int id )
		{
			// field initialize
			m_Socket = socket;
			m_IPEndPoint = (IPEndPoint)m_Socket.RemoteEndPoint;
			m_Closed = 0;
			m_ID = id;

			m_Sending = false;
			m_SendQueue = new Queue<byte[]>();
			m_SendReady = new AsyncCallback( SendReady );

			m_RecvBuffer = new byte[1024];
			m_RecvReady = new AsyncCallback( ReceiveReady );
			m_RecvAccBuffer = new FlexibleMemory();
		}

		public void Start()
		{
			m_Socket.BeginReceive( m_RecvBuffer, 0, m_RecvBuffer.Length, SocketFlags.None, m_RecvReady, null );
		}

		public void Stop( bool ignoreUnsentData )
		{
			// 이미 Closing 중 혹은 완료되었으면 돌아간다.
			if ( Interlocked.Exchange( ref m_Closed, 1 ) != 0 )
			{
				return;
			}

			if ( ignoreUnsentData )
			{
				m_SendQueue.Clear();
				m_Sending = false;
			}
			else
			{
				while ( m_Sending )
				{
					Thread.Sleep( 0 );
				}
			}

			m_Socket.Close();

			if ( Close != null )
			{
				Close( this, new CloseEventArgs( m_ID ) );
			}
		}

		public void Send(Packet packet)
		{
			if (packet.IsDisposed)
			{
				throw new ArgumentException("packet data already disposed, cmd:" + packet.Cmd);
			}
			using (packet)
			{
				this.Send(packet.GetBytes());
			}
		}

		public void Send( byte[] buffer )
		{
			Send( buffer, 0, buffer.Length );
		}

		public void Send( byte[] buffer, int length )
		{
			Send( buffer, 0, length );
		}

		public void Send( byte[] buffer, int index, int length )
		{
			if ( m_Closed != 0 )
			{
				return;
			}

			if ( length <= 0 )
			{
				return;
			}

			byte[] clone = new byte[length];
			Array.Copy( buffer, index, clone, 0, length );

			lock ( m_SendQueue )
			{
				if ( m_Sending )
				{
					// Async 하게 동작중인 스레드가 있으므로 예약만 해둔다
					m_SendQueue.Enqueue( clone );
				}
				else
				{
					// 예약한 작업이 없다. BeginSend를 시작한다
					m_Sending = true;
					m_SendBuffer = clone;
					m_SendPosition = 0;

					BeginSend();
				}
			}
		}

		/// <summary>
		/// (private) 예외가 발생하였을 때 이를 사용자에게 알리고 소켓을 닫습니다.
		/// </summary>
		void ProcessError( Exception exception )
		{
			// 종료중 혹은 종료완료 뒤에는 처리하지 않는다
			if ( m_Closed != 0 )
			{
				return;
			}

			// 에러 이벤트를 발생시킨다
			if ( Error != null )
			{
				Error( this, new ErrorEventArgs( m_ID, exception ) );
			}

			// 예외가 발생하면 소켓을 닫는다.
			Stop( true );
		}

		void BeginSend()
		{
			try
			{
				m_Socket.BeginSend(
					m_SendBuffer,
					m_SendPosition,
					m_SendBuffer.Length - m_SendPosition,
					SocketFlags.None,
					m_SendReady,
					null );
			}
			catch ( Exception exception )
			{
				ProcessError( exception );
			}
		}

		void SendReady( IAsyncResult result )
		{
			try
			{
				if ( !m_Socket.Connected )
				{
					return;
				}

				SocketError errorCode;
				int sent = m_Socket.EndSend( result, out errorCode );
				if ( sent == 0 )
				{
					// 0 byte를 보낸 것은 연결이 끊겼다는 의미.
					Stop( true );
					return;
				}
				if ( errorCode != SocketError.Success )
				{
					System.Diagnostics.Debug.Assert( false );
					return;
				}

				m_SendPosition += sent;
				if ( m_SendPosition < m_SendBuffer.Length )
				{
					BeginSend();
					return;
				}
			}
			catch ( Exception exception )
			{
				ProcessError( exception );
				return;
			}

			lock ( m_SendQueue )
			{
				if ( m_SendQueue.Count > 0 )
				{
					// 예약된 작업이 있다. 연속해서 Sending한다
					m_SendBuffer = m_SendQueue.Dequeue();
					m_SendPosition = 0;
					BeginSend();
				}
				else
				{
					// 예약된 작업이 없으므로 Async routine 을 그만둔다.
					m_Sending = false;
				}
			}
		}

		void ReceiveReady( IAsyncResult result )
		{
			try
			{
				if ( !m_Socket.Connected )
				{
					return;
				}

				SocketError errorCode;
				int size = m_Socket.EndReceive( result, out errorCode );
				if ( size == 0 )
				{
					// 0 byte를 받은 것은 연결이 끊겼다는 의미
					Stop( true );
					return;
				}
				if ( errorCode != SocketError.Success )
				{
					System.Diagnostics.Debug.Assert( false );
					return;
				}

				// 네트워크 버퍼에 받은 값을 붙인다.
				m_RecvAccBuffer.Push( m_RecvBuffer, size );

				Packet packet = Packet.TryParse( m_RecvAccBuffer.Data, m_RecvAccBuffer.Position );

				// 받은 자료를 복사하여 이벤트를 호출한다.
				while ( packet != null )
				{
					int packetSize = packet.SerializeSize;

					if ( Receive != null )
					{
						packet.PacketCreatedTicks = DateTime.Now.Ticks;
						// 이 이벤트에서 돌아오기 전까지 다음 Receive를 준비하지 않으므로 주의해야 한다.
						Receive( this, new ReceiveEventArgs( m_ID, packet ) );
					}

					m_RecvAccBuffer.Pop( packetSize );

					packet = Packet.TryParse( m_RecvAccBuffer.Data, m_RecvAccBuffer.Position );
				}

				// 비동기 읽기 작업을 다시 시작한다.
				m_Socket.BeginReceive( m_RecvBuffer, 0, m_RecvBuffer.Length, SocketFlags.None, m_RecvReady, null );
			}
			catch ( Exception exception )
			{
				ProcessError( exception );
				return;
			}
		}
	}
}
