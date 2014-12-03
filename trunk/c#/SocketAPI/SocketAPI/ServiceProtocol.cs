using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace BowlingCommon
{
	public enum ServiceProtocol
	{
		TitleScenePacketStart = 1,
		Login = 2,
		TitleScenePacketEnd  = 20,
		MainScenePacketStart = 21,

		MainScenePacketEnd = 300,
		CommonPacketStart = 301,

		CommonPacketEnd = 400,
	}
}
