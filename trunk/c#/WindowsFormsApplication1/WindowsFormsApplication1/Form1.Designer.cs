namespace WindowsFormsApplication1
{
    partial class Form1
    {
        /// <summary>
        /// 必需的设计器变量。
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// 清理所有正在使用的资源。
        /// </summary>
        /// <param name="disposing">如果应释放托管资源，为 true；否则为 false。</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows 窗体设计器生成的代码

        /// <summary>
        /// 设计器支持所需的方法 - 不要
        /// 使用代码编辑器修改此方法的内容。
        /// </summary>
        private void InitializeComponent()
        {
            this.textBox1 = new System.Windows.Forms.TextBox();
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            this.label5 = new System.Windows.Forms.Label();
            this.label6 = new System.Windows.Forms.Label();
            this.textBox_L = new System.Windows.Forms.TextBox();
            this.textBox_WN = new System.Windows.Forms.TextBox();
            this.textBox_WR = new System.Windows.Forms.TextBox();
            this.textBox_NF = new System.Windows.Forms.TextBox();
            this.textBox_FIX = new System.Windows.Forms.TextBox();
            this.textBox7 = new System.Windows.Forms.TextBox();
            this.label7 = new System.Windows.Forms.Label();
            this.label9 = new System.Windows.Forms.Label();
            this.textBox_D = new System.Windows.Forms.TextBox();
            this.SuspendLayout();
            // 
            // textBox1
            // 
            this.textBox1.Location = new System.Drawing.Point(14, 24);
            this.textBox1.Name = "textBox1";
            this.textBox1.Size = new System.Drawing.Size(123, 21);
            this.textBox1.TabIndex = 0;
            this.textBox1.Text = "OD###X# L=##M";
            this.textBox1.TextChanged += new System.EventHandler(this.textBox1_TextChanged);
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(14, 9);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(53, 12);
            this.label1.TabIndex = 1;
            this.label1.Text = "管道信息";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(108, 71);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(74, 12);
            this.label2.TabIndex = 2;
            this.label2.Text = "直管长度(M)";
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(161, 9);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(53, 12);
            this.label3.TabIndex = 3;
            this.label3.Text = "弯头数量";
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(290, 71);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(77, 12);
            this.label4.TabIndex = 4;
            this.label4.Text = "弯头弯曲半径";
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Location = new System.Drawing.Point(260, 9);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(77, 12);
            this.label5.TabIndex = 5;
            this.label5.Text = "相关阀门数量";
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.label6.Location = new System.Drawing.Point(371, 9);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(97, 12);
            this.label6.TabIndex = 6;
            this.label6.Text = "其他修正值(mm)";
            // 
            // textBox_L
            // 
            this.textBox_L.Location = new System.Drawing.Point(111, 86);
            this.textBox_L.Name = "textBox_L";
            this.textBox_L.Size = new System.Drawing.Size(81, 21);
            this.textBox_L.TabIndex = 4;
            this.textBox_L.Text = "0";
            this.textBox_L.TextChanged += new System.EventHandler(this.textBox_L_TextChanged);
            // 
            // textBox_WN
            // 
            this.textBox_WN.Location = new System.Drawing.Point(161, 24);
            this.textBox_WN.Name = "textBox_WN";
            this.textBox_WN.Size = new System.Drawing.Size(81, 21);
            this.textBox_WN.TabIndex = 1;
            this.textBox_WN.Text = "0";
            this.textBox_WN.TextChanged += new System.EventHandler(this.textBox_WN_TextChanged);
            // 
            // textBox_WR
            // 
            this.textBox_WR.Location = new System.Drawing.Point(293, 86);
            this.textBox_WR.Name = "textBox_WR";
            this.textBox_WR.Size = new System.Drawing.Size(81, 21);
            this.textBox_WR.TabIndex = 6;
            this.textBox_WR.Text = "0";
            this.textBox_WR.TextChanged += new System.EventHandler(this.textBox_WR_TextChanged);
            // 
            // textBox_NF
            // 
            this.textBox_NF.Location = new System.Drawing.Point(262, 24);
            this.textBox_NF.Name = "textBox_NF";
            this.textBox_NF.Size = new System.Drawing.Size(81, 21);
            this.textBox_NF.TabIndex = 2;
            this.textBox_NF.Text = "0";
            this.textBox_NF.TextChanged += new System.EventHandler(this.textBox_NF_TextChanged);
            // 
            // textBox_FIX
            // 
            this.textBox_FIX.Location = new System.Drawing.Point(373, 24);
            this.textBox_FIX.Name = "textBox_FIX";
            this.textBox_FIX.Size = new System.Drawing.Size(81, 21);
            this.textBox_FIX.TabIndex = 3;
            this.textBox_FIX.Text = "0";
            this.textBox_FIX.TextChanged += new System.EventHandler(this.textBox_FIX_TextChanged);
            // 
            // textBox7
            // 
            this.textBox7.Location = new System.Drawing.Point(475, 120);
            this.textBox7.Name = "textBox7";
            this.textBox7.Size = new System.Drawing.Size(81, 21);
            this.textBox7.TabIndex = 7;
            // 
            // label7
            // 
            this.label7.AutoSize = true;
            this.label7.Location = new System.Drawing.Point(385, 123);
            this.label7.Name = "label7";
            this.label7.Size = new System.Drawing.Size(74, 12);
            this.label7.TabIndex = 13;
            this.label7.Text = "管道总长(M)";
            // 
            // label9
            // 
            this.label9.AutoSize = true;
            this.label9.Location = new System.Drawing.Point(209, 71);
            this.label9.Name = "label9";
            this.label9.Size = new System.Drawing.Size(29, 12);
            this.label9.TabIndex = 15;
            this.label9.Text = "管径";
            // 
            // textBox_D
            // 
            this.textBox_D.Location = new System.Drawing.Point(201, 86);
            this.textBox_D.Name = "textBox_D";
            this.textBox_D.Size = new System.Drawing.Size(81, 21);
            this.textBox_D.TabIndex = 5;
            this.textBox_D.TextChanged += new System.EventHandler(this.textBox_D_TextChanged);
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(579, 162);
            this.Controls.Add(this.textBox_D);
            this.Controls.Add(this.label9);
            this.Controls.Add(this.label7);
            this.Controls.Add(this.textBox7);
            this.Controls.Add(this.textBox_FIX);
            this.Controls.Add(this.textBox_NF);
            this.Controls.Add(this.textBox_WR);
            this.Controls.Add(this.textBox_WN);
            this.Controls.Add(this.textBox_L);
            this.Controls.Add(this.label6);
            this.Controls.Add(this.label5);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.textBox1);
            this.HelpButton = true;
            this.MaximizeBox = false;
            this.MaximumSize = new System.Drawing.Size(595, 200);
            this.MinimumSize = new System.Drawing.Size(595, 200);
            this.Name = "Form1";
            this.Text = "管道长度计算器";
            this.Activated += new System.EventHandler(this.Form1_Activated);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.TextBox textBox1;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.Label label6;
        private System.Windows.Forms.TextBox textBox_L;
        private System.Windows.Forms.TextBox textBox_WN;
        private System.Windows.Forms.TextBox textBox_WR;
        private System.Windows.Forms.TextBox textBox_NF;
        private System.Windows.Forms.TextBox textBox_FIX;
        private System.Windows.Forms.TextBox textBox7;
        private System.Windows.Forms.Label label7;
        private System.Windows.Forms.Label label9;
        private System.Windows.Forms.TextBox textBox_D;
    }
}

