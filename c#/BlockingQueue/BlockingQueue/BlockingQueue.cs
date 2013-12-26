using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace BlockingQueue
{
    /// <summary>
    /// 큐가 비어있을때 Pull 에서 자동으로 스레드 블록할수 있는 Queue입니다.
    /// 이 클래스는 Push(T item), Pull(), Pull(int timeout) 메소드는 스레드 세이프합니다.
    /// </summary>
    /// <typeparam name="T"></typeparam>
    public class BlockingQueue<T> :Queue<T>
    {

        private ManualResetEvent resetEvent = new ManualResetEvent(false);

        public void Push(T item)
        {
            lock (this)
            {
                base.Enqueue(item);
                resetEvent.Set();
            }
        }

        public T Pull()
        {
            return this.Pull(Timeout.Infinite);
        }

       /// <summary>
       /// 큐가 비여있으면 블록상태유지
       /// </summary>
       /// <param name="timeout"></param>
       /// <returns></returns>
        public T Pull(int timeout)
        {
            DateTime startTime = DateTime.Now;

            lock (this)
            {
                if (this.Count > 0)
                {
                    T item = this.Dequeue();
                    resetEvent.Reset();
                    return item;
                }
            }
            {
                while (true)
                {
                    resetEvent.WaitOne();
                    if (timeout != Timeout.Infinite)
                    {
                        DateTime awakeTime = DateTime.Now;
                        long ticks = awakeTime.Ticks - startTime.Ticks;
                        if (ticks / 10000 > timeout)
                        {
                            resetEvent.Reset();
                            throw new TimeoutException();
                        }
                    }
                    lock (this)
                    {
                        if (this.Count > 0)
                        {
                            T item = this.Dequeue();
                            resetEvent.Reset();
                            return item;
                        }
                    }
                    resetEvent.Reset();
                }
                
            }
            
        }
    }
}
