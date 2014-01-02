using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace EasySocket
{
    public class Packet
    {
        private short cmd;
        public short Cmd { get { return this.cmd; } }

        private byte[] data;

        public byte[] Data { get { return this.data; } }

        public Packet(short cmd, byte[] data)
        {
            this.cmd = cmd;
            this.data = data;
        }
    }

}

