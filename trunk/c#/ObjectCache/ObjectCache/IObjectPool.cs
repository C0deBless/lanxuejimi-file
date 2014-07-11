using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BowlingCache
{
	public delegate void CallBackObjPool();

	public interface IObjectPool
	{
		bool Initialize(Type objType, object[] cArgs, int minNum, int maxNum);
		object RentObject();
		void GiveBackObject(int objHashCode);
		void Dispose();
		int MinObjCount { get; }
		int MaxObjCount { get; }
		int CurObjCount { get; }
		int IdleObjCount { get; }
		event CallBackObjPool PoolShrinked;
		event CallBackObjPool MemoryUseOut;
	}
}
