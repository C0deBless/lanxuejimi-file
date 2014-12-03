using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Demo1
{
	enum Direction
	{
		Left,
		Right,
	}

	enum Position
	{
		Top,
		Bottom,
	}
	class Program
	{
		private const int GridSize = 1;
		static void Main(string[] args)
		{
			Exec(3, 3, 2, Position.Bottom, Direction.Left);
			Console.Read();
		}

		static void Exec(int x, int y, int layer, Position pos, Direction dir)
		{
			
			int currentX = x;
			int currentY = y;

			int dirFactor = dir == Direction.Left ? 1 : -1;

			if (pos == Position.Top)
			{
				currentY = y - GridSize * layer;
			}
			else
			{
				currentY = y + GridSize * layer;
			}

			layer--;

			for (int i = 1; i <= layer + 2; i++)
			{
				// currentX, currentY do something;
				Handle(currentX, currentY);

				if (i < layer + 2)
				{
					currentX -= dirFactor * GridSize;
				}
				else
				{
					currentY -= dirFactor * GridSize;
				}
			}

			for (int i = 1; i <= layer * 2 + 2; i++)
			{
				// currentX, currentY do something;
				Handle(currentX, currentY);

				if (i < layer * 2 + 2)
				{
					currentY -= dirFactor * GridSize;
				}
				else
				{
					currentX += dirFactor * GridSize;
				}
			}

			for (int i = 1; i <= layer * 2 + 2; i++)
			{
				// currentX, currentY do something;
				Handle(currentX, currentY);

				if (i < layer * 2 + 2)
				{
					currentX += dirFactor * GridSize;
				}
				else
				{
					currentY += dirFactor * GridSize;
				}
			}

			for (int i = 1; i <= layer * 2 + 2; i++)
			{
				// currentX, currentY do something;
				Handle(currentX, currentY);

				if (i < layer * 2 + 2)
				{
					currentY += dirFactor * GridSize;
				}
				else
				{
					currentX -= dirFactor * GridSize;
				}
			}

			for (int i = 1; i <= layer; i++)
			{
				// currentX, currentY do something;
				Handle(currentX, currentY);

				currentX -= dirFactor * GridSize;
			}
		}

		static void Handle(int x, int y)
		{
			Console.WriteLine("output x:{0}, y:{1}", x, y);
		}
	}
}
