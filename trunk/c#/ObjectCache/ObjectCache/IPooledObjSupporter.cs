using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BowlingCache
{
	public interface IPooledObjSupporter : IDisposable
	{
		void Reset();
	}
}
