using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using NetMX.Remote;
using NetMX;

namespace NetMXHelper
{
    public class JMXClient
    {
        private GameServerManager.Logger logger;

        public JMXClient(GameServerManager.Logger logger)
        {
            // TODO: Complete member initialization
            this.logger = logger;
        }

        public void run()
        {
            string strUrl = "tcp://127.0.0.1:1090/jmxconnector.tcp";
            INetMXConnector connector=
                NetMXConnectorFactory.NewNetMXConnector(new Uri(strUrl));
            IMBeanServerConnection connection= connector.MBeanServerConnection;
            //connection.Invoke();
            connector.Connect(null);
            logger.Info("jmx client started at " + strUrl);
        }

        public void invoke(string operation,object[] vars)
        {
        }
    }
}
