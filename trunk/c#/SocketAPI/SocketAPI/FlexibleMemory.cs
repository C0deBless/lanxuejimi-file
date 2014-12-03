using System;

namespace TigerNetwork
{
	/// <summary>
	/// 자동으로 할당과 해제가 되는 메모리 관리 클래스입니다.
	/// </summary>
	class FlexibleMemory
	{
		/// <summary>
		/// 초기 생성 사이즈
		/// </summary>
		int m_InitBufferSize;

		/// <summary>
		/// 버퍼 크기
		/// </summary>
		byte[] m_Memory;

		/// <summary>
		/// 현재 버퍼 사용량
		/// </summary>
		int m_CurrentPosition;

		/// <summary>
		/// 버퍼를 다시 축소하기 위해 세는 카운트 (계속 큰 것이 들어오는데 Pop되었다고 바로 메모리 해제하는것도 낭비)
		/// 줄어드는 rate는 3번마다 1/2로 줄어든다 (1K 밑으로는 떨어지지 않는다)
		/// </summary>
		int m_TrimCount;

		/// <summary>
		/// 내부 데이터 버퍼를 가져옵니다.
		/// </summary>
		public byte[] Data
		{
			get
			{
				return m_Memory;
			}
		}

		/// <summary>
		/// 현재 사용중인 메모리 용량을 가져오거나, 강제로 설정합니다.
		/// 메모리 사용량을 강제로 설정하는 경우, 현재 값보다 크면 새로 할당되는 메모리에는 어떤 값이 있을지 알 수 없습니다.
		/// </summary>
		public int Position
		{
			get
			{
				return m_CurrentPosition;
			}
			set
			{
				Reserve( value - m_CurrentPosition );
				m_CurrentPosition = value;
			}
		}

		/// <summary>
		/// 현재 할당되어 있는 내부 버퍼의 크기를 가져오거나 설정합니다.
		/// 크기를 설정할 때, 현재의 Capacity보다 적은 값으로 설정할 경우 아무 동작도 하지 않습니다.
		/// </summary>
		public int Capacity
		{
			get
			{
				return m_Memory.Length;
			}
			set
			{
				Reserve( value - m_CurrentPosition );
			}
		}

		/// <summary>
		/// 새 메모리 관리 인스턴스를 생성합니다.
		/// </summary>
		public FlexibleMemory()
			: this( 1024 )
		{
		}

		/// <summary>
		/// 새 메모리 관리 인스턴스를 생성합니다.
		/// </summary>
		/// <param name="basicBufferSize"></param>
		public FlexibleMemory( int minimumBufferSize )
		{
			m_InitBufferSize = minimumBufferSize;
			m_Memory = new byte[m_InitBufferSize];
			m_CurrentPosition = 0;
			m_TrimCount = 0;
		}

		/// <summary>
		/// 주어진 만큼을 사용할 수 있도록 메모리를 할당합니다.
		/// </summary>
		public void Reserve( int length )
		{
			int newLength = m_Memory.Length;
			if ( newLength < m_CurrentPosition + length )
			{
				while ( newLength < m_CurrentPosition + length )
				{
					newLength = newLength * 2;
				}

				byte[] newMemory = new byte[newLength];
				Buffer.BlockCopy( m_Memory, 0, newMemory, 0, m_CurrentPosition );

				m_Memory = newMemory;
				m_TrimCount = 0;
			}
		}

		/// <summary>
		/// 주어진 배열에서 일정 길이 만큼을 내부 메모리로 복사합니다.
		/// 내부 메모리가 부족할 경우 자동으로 할당합니다.
		/// </summary>
		/// <param name="addBuffer"></param>
		/// <param name="length"></param>
		public void Push( byte[] value )
		{
			Push( value, value.Length );
		}

		/// <summary>
		/// 주어진 배열에서 일정 길이 만큼을 내부 메모리로 복사합니다.
		/// 내부 메모리가 부족할 경우 자동으로 할당합니다.
		/// </summary>
		/// <param name="addBuffer"></param>
		/// <param name="length"></param>
		public void Push( Array value, int bytesCount )
		{
			if ( value != null && bytesCount > 0 )
			{
				Reserve( bytesCount );

				Buffer.BlockCopy( value, 0, m_Memory, m_CurrentPosition, bytesCount );
				m_CurrentPosition += bytesCount;
			}
		}

		/// <summary>
		/// 내부 버퍼에서 주어진 길이 만큼을 앞부분에서 제거합니다.
		/// 내부 메모리가 많은 경우 자동으로 축소합니다.
		/// </summary>
		/// <param name="length"></param>
		/// <returns></returns>
		public void Pop( int length )
		{
			if ( length == 0 )
			{
				return;
			}

			if ( length > m_CurrentPosition )
			{
				throw new ArgumentException( string.Format( "Invalid range {0}", length ), "value" );
			}

			// 버퍼가 불필요하게 많이 할당되어있는지 판단한다.
			if ( m_Memory.Length >= m_CurrentPosition * 2 && m_Memory.Length > m_InitBufferSize )
			{
				++m_TrimCount;
			}
			else
			{
				m_TrimCount = 0;
			}

			// 사용된 메모리만큼 제거한다.
			if ( m_TrimCount == 3 )
			{
				m_TrimCount = 0;

				byte[] newMemory = new byte[m_Memory.Length / 2];
				Buffer.BlockCopy( m_Memory, length, newMemory, 0, m_CurrentPosition - length );
				m_Memory = newMemory;
			}
			else
			{
				Buffer.BlockCopy( m_Memory, length, m_Memory, 0, m_CurrentPosition - length );
			}

			m_CurrentPosition -= length;
		}
	}
}
