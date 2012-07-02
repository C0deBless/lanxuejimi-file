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
    public partial class BattleFieldView : Form
    {
        DataManager dataManager = new DataManager();
        public BattleFieldView()
        {
            InitializeComponent();
            dataManager.loadSeaData();
            Dictionary<int,string> seaList= dataManager.SeaList;
            int count = seaList.Values.Count;
               
            foreach(var item in seaList)
            {
                TreeNode tn = new TreeNode(item.Key + ":" + item.Value, new TreeNode[]{});
                this.TV_BATTLEFIELD_LIST.Nodes.Add(tn);
                tn.ContextMenuStrip = this.CMX_ADD_BATTLEFIELD;
            }
       
        }

        private void CMX_ADD_BATTLEFIELD_ItemClicked(object sender, ToolStripItemClickedEventArgs e)
        {
           // TreeNode tn=(TreeNode)(sender as ContextMenuStrip).SourceControl;

            TreeNode tn=TV_BATTLEFIELD_LIST.SelectedNode;
            string text=e.ClickedItem.Text;
            
            if (text.Equals("전장추가"))
            {
                string[] strs=tn.Text.Split(':');
                int seaId = Convert.ToInt32(strs[0]);
                new BattleFieldAddView(seaId,this).Show();
            }
        }
    }
}
