using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Drawing;
using System.Windows.Forms;

namespace BattleTool
{
    class SceneFactory
    {

        public static Dictionary<int,ScenePage> Pages=new Dictionary<int,ScenePage>();
        public const int PageWidth = 2880;
        public const int PageHeight = 1600;

        public static ScenePage newPage(Control target,int id)
        {
            ScenePage scene = new ScenePage();
            scene.Id = id;
            Panel panel = new Panel();
            panel.Size = new Size(PageWidth,PageHeight);
            panel.Location = new Point(0, 0);
            panel.BackColor = Color.Gray;
            target.Controls.Add(panel);
            panel.BringToFront();
            panel.Update();
            scene.Canvas = panel;
            return scene;
        }

    }
}
