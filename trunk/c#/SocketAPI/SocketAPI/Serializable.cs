using System;
using System.Collections.Generic;
using System.Text;

namespace TigerNetwork
{
	public interface Serializable
	{
		 void Serialize(Packet packet);
	}
}
