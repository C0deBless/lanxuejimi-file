﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;

namespace EasySocket
{
    /// <summary>
    /// Socket异步操作状态
    /// </summary>
    public class SocketAsyncResult : IAsyncResult
    {
        /// <summary>
        /// 实例化Socket异步操作状态
        /// </summary>
        /// <param name="state"></param>
        public SocketAsyncResult(object state)
        {
            AsyncState = state;
            AsyncWaitHandle = new AutoResetEvent(false);
            CompletedSynchronously = false;
        }

        /// <summary>
        /// 获取用户定义的对象，它限定或包含关于异步操作的信息。
        /// </summary>
        public object AsyncState { get; private set; }

        /// <summary>
        /// 获取用于等待异步操作完成的 System.Threading.WaitHandle。
        /// </summary>
        public WaitHandle AsyncWaitHandle { get; private set; }

        /// <summary>
        /// 获取一个值，该值指示异步操作是否同步完成。
        /// </summary>
        public bool CompletedSynchronously { get; internal set; }

        /// <summary>
        /// 获取一个值，该值指示异步操作是否已完成。
        /// </summary>
        public bool IsCompleted { get; internal set; }
    }
}
