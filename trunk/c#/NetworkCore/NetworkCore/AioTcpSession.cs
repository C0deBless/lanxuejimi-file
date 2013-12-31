using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Net.Sockets;
using System.Net;
using System.Threading;
using System.IO;
using System.Net.Security;

namespace NetworkCore
{
    /// <summary>
    /// TCP客户端
    /// </summary>
    public class AioTcpSession : SessionBase
    {

        /// <summary>
        /// 实例化TCP客户端。
        /// </summary>
        public AioTcpSession(Socket socket)
            : base(socket, new SocketHandler())
        {

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
            //等待异步全部处理完成
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
