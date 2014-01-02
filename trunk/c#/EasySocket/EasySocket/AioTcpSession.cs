using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Net.Sockets;
using System.Net;
using System.Threading;
using System.IO;
using System.Net.Security;

namespace EasySocket
{
    public class AioTcpSession : SessionBase
    {
        public event EventHandler<Packet> OnPacketReceived;

        public AioTcpSession(Socket socket)
            : base(socket, new SocketHandler())
        {
            this.ReceiveCompleted += AioTcpSession_ReceiveCompleted;
        }

        private void AioTcpSession_ReceiveCompleted(object sender, SocketEventArgs e)
        {
            byte[] data = e.Data;
            int length = e.DataLength;

            byte[] cmdBytes = new byte[2];
            cmdBytes[0] = data[0];
            cmdBytes[1] = data[1];
            short cmd = BitConverter.ToInt16(cmdBytes, 0);

            byte[] packetData = new byte[data.Length - 2];
            for (int i = 0; i < data.Length - 2; i++)
            {
                packetData[i] = data[i + 2];
            }

            Packet packet = new Packet(cmd, packetData);
            if (this.OnPacketReceived != null)
            {
                this.OnPacketReceived(this, packet);
            }
        }

        public static AioTcpSession Connect(IPEndPoint endpoint)
        {
            Socket socket = new Socket(SocketType.Stream, ProtocolType.Tcp);
            socket.Connect(endpoint);
            AioTcpSession session = new AioTcpSession(socket);
            return session;
        }

        public static AioTcpSession ConnectAsync(IPEndPoint endpoint)
        {
            Socket socket = new Socket(SocketType.Stream, ProtocolType.Tcp);
            SocketAsyncState state = new SocketAsyncState();
            state.IsAsync = true;
            state.Completed = false;
            socket.BeginConnect(endpoint, (result) =>
            {
                socket.EndConnect(result);
                state.Completed = true;
            }, state).AsyncWaitHandle.WaitOne();

            while (!state.Completed)
            {
                Thread.Sleep(1);
            }

            AioTcpSession session = new AioTcpSession(socket);
            return session;
        }

        public void PendingRead()
        {
            Handler.BeginReceive(Stream, EndReceive, this);
        }

    }
}
