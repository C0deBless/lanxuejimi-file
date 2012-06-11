using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading.Tasks;

namespace zhr
{
    class Program
    {
        static void Main(string[] args)
        {
            DownloadHelper dh = new DownloadHelper();
            string str=dh.GetPage("http://wwww.baidu.com", "http://wwww.baidu.com", "utf-8");
            Console.WriteLine(str);
            Console.Read();
        }
    }
    public class DownloadHelper
    {
        /// <summary>     
        /// 网站Cookies     
        /// </summary>    
        private string _cookieHeader = string.Empty;
        /// <summary>     
        /// 网站编码     
        /// </summary>     
        private string _code = string.Empty;

        private Dictionary<string, string> _para = new Dictionary<string, string>();

        private string _pageContent = string.Empty;

        public string CookieHeader
        {
            get
            {
                return _cookieHeader;
            }
            set
            {
                _cookieHeader = value;
            }
        }

        public string Code
        {
            get { return _code; }
            set { _code = value; }
        }

        public string PageContent
        {
            get { return _pageContent; }
            set { _pageContent = value; }
        }

        public Dictionary<string, string> Para
        {
            get { return _para; }
            set { _para = value; }
        }

        public DownloadHelper() { }

        public DownloadHelper(string code)
        {
            this._code = code;
        }

        /// <summary>     
        /// 功能描述：模拟登录页面，提交登录数据进行登录，并记录Header中的cookie    
        /// </summary>     
        /// <param name="strURL">登录数据提交的页面地址</param>     
        /// <param name="strArgs">用户登录数据</param>     
        /// <param name="strReferer">引用地址</param>     
        /// <param name="code">网站编码</param>     
        /// <returns>可以返回页面内容或不返回</returns>     
        public string PostData(string strURL, string strArgs, string strReferer, string code, string method)
        {
            return PostData(strURL, strArgs, strReferer, code, method, string.Empty);
        }

        public string PostData(string strURL, string strArgs, string strReferer, string code, string method, string contentType)
        {
            try
            {
                string strResult = "";
                HttpWebRequest myHttpWebRequest = (HttpWebRequest)WebRequest.Create(strURL);
                myHttpWebRequest.AllowAutoRedirect = true;
                myHttpWebRequest.KeepAlive = true;
                myHttpWebRequest.Accept = "image/gif, image/x-xbitmap, image/jpeg, image/pjpeg, application/vnd.ms-excel, application/msword, application/x-shockwave-flash, */*";
                myHttpWebRequest.Referer = strReferer;
                myHttpWebRequest.UserAgent = "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; Maxthon; .NET CLR 2.0.50727)";
                if (string.IsNullOrEmpty(contentType))
                {
                    myHttpWebRequest.ContentType = "application/x-www-form-urlencoded";
                }
                else
                {
                    myHttpWebRequest.ContentType = contentType;
                }
                myHttpWebRequest.Method = method;
                myHttpWebRequest.Headers.Add("Accept-Encoding", "gzip, deflate");
                if (myHttpWebRequest.CookieContainer == null)
                {
                    myHttpWebRequest.CookieContainer = new CookieContainer();
                }
                if (this.CookieHeader.Length > 0)
                {
                    myHttpWebRequest.Headers.Add("cookie:" + this.CookieHeader);
                    myHttpWebRequest.CookieContainer.SetCookies(new Uri(strURL), this.CookieHeader);
                }

                byte[] postData = Encoding.GetEncoding(code).GetBytes(strArgs);
                myHttpWebRequest.ContentLength = postData.Length;

                System.IO.Stream PostStream = myHttpWebRequest.GetRequestStream();
                PostStream.Write(postData, 0, postData.Length);
                PostStream.Close();

                HttpWebResponse response = null;
                System.IO.StreamReader sr = null;
                response = (HttpWebResponse)myHttpWebRequest.GetResponse();

                if (myHttpWebRequest.CookieContainer != null)
                {
                    this.CookieHeader = myHttpWebRequest.CookieContainer.GetCookieHeader(new Uri(strURL));
                }

                sr = new System.IO.StreamReader(response.GetResponseStream(), Encoding.GetEncoding(code));    //    //utf-8     
                strResult = sr.ReadToEnd();
                sr.Close();
                response.Close();
                return strResult;

            }

            catch (Exception ex)
            {
                //Utilities.Document.Create("C://error.log", strArgs, true, Encoding.UTF8);  
            }

            return string.Empty;
        }

        /// <summary>     
        /// 功能描述：在PostLogin成功登录后记录下Headers中的cookie，然后获取此网站上其他页面的内容    
        /// </summary>     
        /// <param name="strURL">获取网站的某页面的地址</param>     
        /// <param name="strReferer">引用的地址</param>     
        /// <returns>返回页面内容</returns>     
        public string GetPage(string strURL, string strReferer, string code)
        {
            return GetPage(strURL, strReferer, code, string.Empty);
        }

        public string GetPage(string strURL, string strReferer, string code, string contentType)
        {
            string strResult = "";
            HttpWebRequest myHttpWebRequest = (HttpWebRequest)WebRequest.Create(strURL);
            myHttpWebRequest.AllowAutoRedirect = true;
            myHttpWebRequest.KeepAlive = false;
            myHttpWebRequest.Accept = "*/*";
            myHttpWebRequest.Referer = strReferer;
            myHttpWebRequest.Headers.Add("Accept-Encoding", "gzip, deflate");
            myHttpWebRequest.UserAgent = "Mozilla/4.0 (compatible; MSIE 6.0; MSIE 7.0; MSIE 8.0 Windows NT 5.1; SV1; Maxthon; .NET CLR 2.0.50727)";
            if (string.IsNullOrEmpty(contentType))
            {
                myHttpWebRequest.ContentType = "application/x-www-form-urlencoded";
            }

            else
            {
                myHttpWebRequest.ContentType = contentType;
            }

            myHttpWebRequest.Method = "GET";

            if (myHttpWebRequest.CookieContainer == null)
            {
                myHttpWebRequest.CookieContainer = new CookieContainer();
            }

            if (this.CookieHeader.Length > 0)
            {
                myHttpWebRequest.Headers.Add("cookie:" + this.CookieHeader);

                myHttpWebRequest.CookieContainer.SetCookies(new Uri(strURL), this.CookieHeader);
            }

            HttpWebResponse response = null;
            System.IO.StreamReader sr = null;
            response = (HttpWebResponse)myHttpWebRequest.GetResponse();
            Stream streamReceive;
            string gzip = response.ContentEncoding;
            if (string.IsNullOrEmpty(gzip) || gzip.ToLower() != "gzip")
            {
                streamReceive = response.GetResponseStream();
            }
            else
            {
                streamReceive = new System.IO.Compression.GZipStream(response.GetResponseStream(), System.IO.Compression.CompressionMode.Decompress);
            }

            sr = new System.IO.StreamReader(streamReceive, Encoding.GetEncoding(code));
            if (response.ContentLength > 1)
            {
                strResult = sr.ReadToEnd();
            }
            else
            {
                char[] buffer = new char[256];
                int count = 0;
                StringBuilder sb = new StringBuilder();
                while ((count = sr.Read(buffer, 0, buffer.Length)) > 0)
                {
                    sb.Append(new string(buffer));
                }
                strResult = sb.ToString();
            }

            sr.Close();
            response.Close();
            return strResult;
        }

        public void SaveLink(string strURL, string strReferer, string code)
        {
            HttpWebRequest myHttpWebRequest = (HttpWebRequest)WebRequest.Create(strURL);
            myHttpWebRequest.AllowAutoRedirect = true;
            myHttpWebRequest.KeepAlive = false;
            myHttpWebRequest.Accept = "*/*";
            myHttpWebRequest.Referer = strReferer;
            myHttpWebRequest.Headers.Add("Accept-Encoding", "gzip, deflate");
            myHttpWebRequest.UserAgent = "Mozilla/4.0 (compatible; MSIE 6.0; MSIE 7.0; MSIE 8.0 Windows NT 5.1; SV1; Maxthon; .NET CLR 2.0.50727)";
            myHttpWebRequest.ContentType = "application/x-www-form-urlencoded";
            myHttpWebRequest.Method = "GET";
            if (myHttpWebRequest.CookieContainer == null)
            {
                myHttpWebRequest.CookieContainer = new CookieContainer();
            }

            if (this.CookieHeader.Length > 0)
            {
                myHttpWebRequest.Headers.Add("cookie:" + this.CookieHeader);
                myHttpWebRequest.CookieContainer.SetCookies(new Uri(strURL), this.CookieHeader);
            }

            HttpWebResponse response = null;
            response = (HttpWebResponse)myHttpWebRequest.GetResponse();
            long count = response.ContentLength;
        }

        public void DownloadPage(string strURL, string strReferer, string code, string contentType, string path)
        {
            HttpWebRequest myHttpWebRequest = (HttpWebRequest)WebRequest.Create(strURL);
            myHttpWebRequest.AllowAutoRedirect = true;
            myHttpWebRequest.KeepAlive = false;
            myHttpWebRequest.Accept = "*/*";
            myHttpWebRequest.Referer = strReferer;
            myHttpWebRequest.Headers.Add("Accept-Encoding", "gzip, deflate,");
            myHttpWebRequest.UserAgent = "Mozilla/4.0 (compatible; MSIE 6.0; MSIE 7.0; MSIE 8.0; Windows NT 5.1; SV1; Maxthon; .NET CLR 2.0.50727)";
            if (string.IsNullOrEmpty(contentType))
            {
                myHttpWebRequest.ContentType = "application/x-www-form-urlencoded";
            }
            else
            {
                myHttpWebRequest.ContentType = contentType;
            }

            myHttpWebRequest.Method = "GET";

            if (myHttpWebRequest.CookieContainer == null)
            {
                myHttpWebRequest.CookieContainer = new CookieContainer();
            }

            if (this.CookieHeader.Length > 0)
            {
                myHttpWebRequest.Headers.Add("cookie:" + this.CookieHeader);
                myHttpWebRequest.CookieContainer.SetCookies(new Uri(strURL), this.CookieHeader);
            }

            HttpWebResponse response = null;
            response = (HttpWebResponse)myHttpWebRequest.GetResponse();
            Stream stream = response.GetResponseStream();
            byte[] arrByte = new byte[1024];
            int startPos = 0;

            FileStream fStream = new FileStream(path, FileMode.OpenOrCreate, FileAccess.Write);
            while (true)
            {
                int readCnt = stream.Read(arrByte, 0, 1024);
                if (readCnt == 0)
                    break;
                fStream.Write(arrByte, 0, readCnt);
                startPos += readCnt;
            }

            stream.Close();
            fStream.Close();
        }
    }  
}
