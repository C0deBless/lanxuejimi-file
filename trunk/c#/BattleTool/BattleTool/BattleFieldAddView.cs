using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace BattleTool
{
    public partial class BattleFieldAddView : Form
    {
        private int seaId;
        BattleFieldView battleView;

        public BattleFieldAddView(int seaId,BattleFieldView battleView)
        {
            this.seaId = seaId;
            InitializeComponent();
            this.battleView = battleView;
            this.TB_SEAID.Text = this.seaId.ToString();
        }

        private void BTN_ADD_BF_Click(object sender, EventArgs e)
        {

        }
    }
}
