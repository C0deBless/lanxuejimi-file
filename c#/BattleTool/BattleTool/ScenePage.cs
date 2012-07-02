using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Drawing;
using System.Windows.Forms;

namespace BattleTool
{
    class ScenePage
    {
        public HashSet<SceneObject> Objects=new HashSet<SceneObject>();
        public Panel Canvas;
        public Graphics Hand;
        public int Id;

        private void DrawGrid()
        {
            Rectangle rec = new Rectangle(0, 0, Canvas.Width, Canvas.Height);
            int width = rec.Width;
            int height = rec.Height;
            int ox = rec.Left;
            int oy = rec.Top;
            //graphic.TranslateTransform(ox, oy);
            //MessageBox.Show(width + "x" + height);
            Pen pen = new Pen(Color.Blue);
            int xSize = 144;
            int dx = width / xSize;
            int ySize = 80;
            int dy = height / ySize;
            //MessageBox.Show(dx + "x" + dy);
            int dWidth = xSize * dx;
            int dHeight = ySize * dy;
            for (int i = 1; i <= xSize; i++)
            {
                Hand.DrawLine(pen, new Point(dx * i, 0), new Point(dx * i, dHeight));
            }

            for (int i = 1; i <= ySize; i++)
            {
                Hand.DrawLine(pen, new Point(0, dy * i), new Point(dWidth, dy * i));
            }

            pen.Dispose();
        }

        public void Draw()
        {
            this.Hand = this.Canvas.CreateGraphics();
            DrawGrid();
            //Bitmap bm = new Bitmap(@"D:\trunk\3D\Battle\PNG Data\200001\200001_Rotate\200001_Rotate_0000.png");
            //Hand.DrawImage(bm, bm.Width, bm.Height);

            //Bitmap bm2 = new Bitmap(@"D:\trunk\3D\Battle\PNG Data\200002\200002_rotate\200002_Rotate_0003.png");
            //Hand.DrawImage(bm2, bm.Width, bm.Height);
        }

        public static void Dispose(Graphics graphic)
        {
            graphic.Dispose();
        }

    }
}
