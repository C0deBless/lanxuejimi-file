using System;
using System.Collections.Generic;
using System.IO;
using System.Text;

namespace TigerNetwork
{
	public interface IPacketStruct
	{
		/// <summary>
		/// 주어진 패킷에 해당 스트럭쳐를 씁니다.
		/// </summary>
		/// <param name="packet"></param>
		void WriteTo( Packet packet );

		/// <summary>
		/// 주어진 패킷에서 해당 스트럭쳐를 읽습니다.
		/// </summary>
		/// <param name="packet"></param>
		void ReadFrom( Packet packet );
	}

    /// <summary>
    /// packet format:
    /// ----------------------------------------------------------------------------
    /// | length(4 bytes) |   CRC16(2 bytes)  | cmd(4 bytes) |      data...        |
    /// ----------------------------------------------------------------------------
    ///                                       |---------- CRC check area ----------|
    /// </summary>
    public class Packet : IDisposable
    {
        enum Mode
        {
            Read,Write
        }
        private static readonly int PacketLengthSize = sizeof(int);
        private static readonly int PacketCmdSize = sizeof(short);
        private static readonly int PacketCRCSize = sizeof(ushort);
		public int NetworkKey;
        private short cmd;
        public short Cmd { get { return this.cmd; } }
		private bool isDisposed = false;

		#region profiling fields
		public long PacketCreatedTicks = 0;
		public long PacketQueueStartTicks = 0;
		public long PacketQueueEndTicks = 0;
		public long PacketExecuteStartTicks = 0;
		public long PacketExecuteEndTicks = 0;
		#endregion

		private Mode mode;

        public int SerializeSize
        {
            get
            {
                if(this.mode == Mode.Write)
                    return (int)(PacketLengthSize + PacketCRCSize + PacketCmdSize + this.StreamPosition);
                else
                {
                    return (int)(PacketLengthSize + PacketCRCSize + PacketCmdSize + this.stream.Capacity);
                }
            }
        }

		public bool IsDisposed
		{
			get
			{
				return this.isDisposed;
			}
		}

        public int CurrentReadingOffset
        {
            get
            {
                return this.StreamPosition;
            }
            set
            {
                this.StreamPosition = value;
            }
        }

        private MemoryStream stream;
        private BinaryReader reader;
        private BinaryWriter writer;

        public int StreamPosition
        {
            get
            {
                return (int)this.stream.Position;
            }
            set
            {
                this.stream.Position = value;
            }
        }

        public Packet(int cmd, byte[] data)
        {
            this.cmd = (short)cmd;
            this.stream = new MemoryStream(data);
            this.reader = new BinaryReader(stream);
            this.mode = Mode.Read;
        }

        public Packet(int cmd, int initialBufferSize)
        {
            this.cmd = (short)cmd;
            this.stream = new MemoryStream(initialBufferSize);
            this.writer = new BinaryWriter(stream);
            this.mode = Mode.Write;
        }

		public Packet(int cmd)
			: this(cmd, 256)
		{

		}

        private void CheckReadingStatus()
        {
            if (this.mode != Mode.Read)
            {
                throw new InvalidOperationException("cannot read data from this packet");
            }
        }

        private void CheckWritingStatus()
        {
            if (this.mode != Mode.Write)
            {
                throw new InvalidOperationException("cannot read data from this packet");
            }
        }

        internal static Packet TryParse(byte[] buffer, int length)
        {
            if (length < PacketLengthSize)
            {
                return null;
            }

            int totalLength = BitConverter.ToInt32(buffer, 0);
            if (totalLength > length)
            {
                return null;
            }

            if (totalLength < PacketLengthSize + PacketCmdSize + PacketCRCSize)
            {
                throw new TigerNetworkException("received illegal packet length");
            }

            ushort crc = BitConverter.ToUInt16(buffer, PacketLengthSize);
            ushort currentCRC = CRC16.Calculate(buffer, PacketLengthSize + PacketCRCSize, totalLength - PacketLengthSize - PacketCRCSize);
            if (crc != currentCRC)
            {
                throw new TigerNetworkException("CRC16 check failed, crc:" + crc + ", currentCRC:" + currentCRC + ", packetSize:" + totalLength);
            }

            short cmd = BitConverter.ToInt16(buffer, PacketLengthSize + PacketCRCSize);

            int dataSize = totalLength - PacketLengthSize - PacketCmdSize - PacketCRCSize;
            byte[] data = new byte[dataSize];
            Buffer.BlockCopy(buffer, PacketLengthSize + PacketCRCSize + PacketCmdSize, data, 0, dataSize);
            return new Packet(cmd, data);
        }

        #region write data
        public void Write(bool value)
        {
            this.CheckWritingStatus();
            this.writer.Write(value);
        }

        public void Write(sbyte value)
        {
            this.CheckWritingStatus();
            this.writer.Write(value);
        }

        public void Write(byte value)
        {
            this.CheckWritingStatus();
            this.writer.Write(value);
        }

        public void Write(short value)
        {
            this.CheckWritingStatus();
            this.writer.Write(value);
        }

        public void Write(ushort value)
        {
            this.CheckWritingStatus();
            this.writer.Write(value);
        }

        public void Write(int value)
        {
            this.CheckWritingStatus();
            this.writer.Write(value);
        }

        public void Write(int[] value)
        {
            this.CheckWritingStatus();
            if (value == null || value.Length == 0)
            {
                this.writer.Write(0);
            }
            else
            {
                this.writer.Write(value.Length);
                foreach (int i in value)
                {
                    this.writer.Write(i);
                }
            }
        }

		public void Write(byte[] value)
		{
			this.CheckWritingStatus();
			if (value == null || value.Length == 0)
			{
				this.writer.Write(0);
			}
			else
			{
				this.writer.Write(value.Length);
				foreach (byte i in value)
				{
					this.writer.Write(i);
				}
			}
		}

		public void Write(List<int> value)
		{
			this.CheckWritingStatus();
			if (value == null || value.Count == 0)
			{
				this.writer.Write(0);
			}
			else
			{
				this.writer.Write(value.Count);
				foreach (int i in value)
				{
					this.writer.Write(i);
				}
			}
		}

        public void Write(uint value)
        {
            this.CheckWritingStatus();
            this.writer.Write(value);
        }

		public void Write(uint[] array)
		{
			this.CheckWritingStatus();
			if (array == null || array.Length == 0)
			{
				this.writer.Write(0);
			}
			else
			{
				this.writer.Write(array.Length);
				foreach (uint value in array)
				{
					this.writer.Write(value);
				}
			}
		}

		public void Write(List<uint> list)
		{
			this.CheckWritingStatus();
			if (list == null || list.Count == 0)
			{
				this.writer.Write(0);
			}
			else
			{
				this.writer.Write(list.Count);
				foreach (uint value in list)
				{
					this.writer.Write(value);
				}
			}
		}

        public void Write(long value)
        {
            this.CheckWritingStatus();
            this.writer.Write(value);
        }

        public void Write(ulong value)
        {
            this.CheckWritingStatus();
            this.writer.Write(value);
        }

        public void Write(decimal value)
        {
            this.CheckWritingStatus();
            this.writer.Write(value);
        }

        public void Write(float value)
        {
            this.CheckWritingStatus();
            this.writer.Write(value);
        }

        public void Write(double value)
        {
            this.CheckWritingStatus();
            this.writer.Write(value);
        }

        public void Write(DateTime value)
        {
            this.CheckWritingStatus();
            this.writer.Write(value.Ticks);
        }

        public void Write(string value)
        {
            this.CheckWritingStatus();
			
			byte[] data = Encoding.UTF8.GetBytes(value);
			int len = data.Length;
			this.writer.Write(len);
			foreach (byte b in data)
			{
				this.writer.Write(b);
			}
        }

        public void Write(string[] value)
        {
            this.CheckWritingStatus();
            if (value == null || value.Length == 0)
            {
                this.writer.Write(0);
            }
            else
            {
                this.writer.Write(value.Length);
                foreach (string str in value)
                {
                    this.writer.Write(str);
                }
            }
        }

		public void Write(long[] value)
		{
			this.CheckWritingStatus();
			if (value == null || value.Length == 0)
			{
				this.writer.Write(0);
			}
			else
			{
				this.writer.Write(value.Length);
				foreach (long l in value)
				{
					this.writer.Write(l);
				}
			}
		}

        public void Write(IPacketStruct ips)
        {
            this.CheckWritingStatus();
            ips.WriteTo(this);
        }
        #endregion

        #region read data
        public bool ReadBoolean()
        {
            this.CheckReadingStatus();
            return this.reader.ReadBoolean();
        }

        public sbyte ReadSByte()
        {
            this.CheckReadingStatus();
            return this.reader.ReadSByte();
        }

        public byte ReadByte()
        {
            this.CheckReadingStatus();
            return this.reader.ReadByte();
        }

		public byte[] ReadByteArray()
		{
			this.CheckReadingStatus();
			int length = this.reader.ReadInt32();
			byte[] value = new byte[length];
			for (int i = 0; i < length; i++)
			{
				value[i] = this.reader.ReadByte();
			}
			return value;
		}

		public char ReadChar()
		{
			this.CheckReadingStatus();
			return this.reader.ReadChar();
		}

        public short ReadInt16()
        {
            this.CheckReadingStatus();
            return this.reader.ReadInt16();
        }

		public short ReadShort()
		{
			return this.ReadInt16();
		}

        public ushort ReadUInt16()
        {
            this.CheckReadingStatus();
            return this.reader.ReadUInt16();
        }

		public short[] ReadInt16Array()
		{
			this.CheckReadingStatus();
			int length = this.reader.ReadInt32();
			short[] value = new short[length];
			for (int i = 0; i < length; i++)
			{
				value[i] = this.reader.ReadInt16();
			}
			return value;
		}

        public int ReadInt32()
        {
            this.CheckReadingStatus();
            return this.reader.ReadInt32();
        }

		public int ReadInt()
		{
			return this.ReadInt32();
		}

        public int[] ReadInt32Array()
        {
            this.CheckReadingStatus();
            int length = this.reader.ReadInt32();
            int[] value = new int[length];
            for (int i = 0; i < length; i++)
            {
                value[i] = this.reader.ReadInt32();
            }
            return value;
        }

		public List<int> ReadInt32List()
		{
			this.CheckReadingStatus();
			int length = this.reader.ReadInt32();
			List<int> value = new List<int>(length);
			for (int i = 0; i < length; i++)
			{
				value.Add(this.reader.ReadInt32());
			}
			return value;
		}

        public uint ReadUInt32()
        {
            this.CheckReadingStatus();
            return this.reader.ReadUInt32();
        }

		public uint[] ReadUInt32Array()
		{
			this.CheckReadingStatus();
			int length = this.reader.ReadInt32();
			uint[] array = new uint[length];
			for (int i = 0; i < length; i++)
			{
				array[i] = this.reader.ReadUInt32();
			}
			return array;
		}

        public long ReadInt64()
        {
            this.CheckReadingStatus();
            return this.reader.ReadInt64();
        }

		public long ReadLong()
		{
			return this.ReadInt64();
		}

        public ulong ReadUInt64()
        {
            this.CheckReadingStatus();
            return this.reader.ReadUInt64();
        }

        public decimal ReadDecimal()
        {
            this.CheckReadingStatus();
            return this.reader.ReadDecimal();
        }

        public float ReadSingle()
        {
            this.CheckReadingStatus();
            return this.reader.ReadSingle();
        }

        public double ReadDouble()
        {
            this.CheckReadingStatus();
            return this.reader.ReadDouble();
        }

        public DateTime ReadDateTime()
        {
            this.CheckReadingStatus();
            return new DateTime(this.reader.ReadInt64());
        }

        public string ReadString()
        {
            this.CheckReadingStatus();
			int len = this.ReadInt32();
			byte[] data = new byte[len];
			for (int i = 0; i < len; i++)
			{
				data[i] = this.ReadByte();
			}
			return Encoding.UTF8.GetString(data);
        }

        public string[] ReadStringArray()
        {
            this.CheckReadingStatus();
            int length = this.reader.ReadInt32();
            string[] array = new string[length];
            for (int i = 0; i < length; i++)
            {
                array[i] = this.reader.ReadString();
            }
            return array;
        }

		public long[] ReadInt64Array()
		{
			this.CheckReadingStatus();
			int length = this.reader.ReadInt32();
			long[] array = new long[length];
			for (int i = 0; i < length; i++)
			{
				array[i] = this.reader.ReadInt64();
			}
			return array;
		}

        public void Read(out string value)
        {
            this.CheckReadingStatus();
            value = this.reader.ReadString();
        }

        public T ReadPacketStruct<T>()
            where T : IPacketStruct, new()
        {
            this.CheckReadingStatus();
            T value = new T();
            value.ReadFrom(this);
            return value;
        }

        public void Read<T>(out T value)
            where T : IPacketStruct, new()
        {

            this.CheckReadingStatus();
            value = ReadPacketStruct<T>();
        }

        #endregion

        public void Dispose()
        {
            if (reader != null)
                this.reader.Close();

            if (writer != null)
                this.writer.Close();

            this.stream.Close();
			this.isDisposed = true;
        }

        public byte[] GetBytes()
        {
            int currentPosition = this.StreamPosition;
            int packetSize = currentPosition + PacketCRCSize + PacketCmdSize + PacketLengthSize;

            byte[] crcCheckBuffer= new byte[currentPosition + PacketCmdSize];
            Buffer.BlockCopy(BitConverter.GetBytes(this.cmd), 0, crcCheckBuffer, 0, PacketCmdSize);
            this.StreamPosition = 0;
            this.stream.Read(crcCheckBuffer, PacketCmdSize, currentPosition);
            ushort crc = CRC16.Calculate(crcCheckBuffer);

            byte[] data = new byte[packetSize];
            Buffer.BlockCopy(BitConverter.GetBytes(packetSize), 0, data, 0, PacketLengthSize);
            Buffer.BlockCopy(BitConverter.GetBytes(crc), 0, data, PacketLengthSize, PacketCRCSize);
            Buffer.BlockCopy(crcCheckBuffer, 0, data, PacketLengthSize + PacketCRCSize, crcCheckBuffer.Length);

            return data;
        }

		public void Write<T>(List<T> list) where T : Serializable
		{
			if (list == null || list.Count <= 0)
			{
				this.Write(0);
				return;
			}

			this.Write(list.Count);
			foreach (T t in list)
			{
				t.Serialize(this);
			}
		}


	}
}
