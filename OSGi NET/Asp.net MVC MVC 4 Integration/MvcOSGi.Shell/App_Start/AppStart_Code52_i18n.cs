using System.Web;
using MvcOSGi.Shell.Code52.i18n;

[assembly: PreApplicationStartMethod(typeof(MvcOSGi.Shell.App_Start.Code52_i18n), "Start")]
namespace MvcOSGi.Shell.App_Start {

    using System.Web.Mvc;
    using System.Web.Routing;

    public class Code52_i18n {
        public static void Start() {
            //RouteTable.Routes.MapRoute("Language", "i18n/Code52.i18n.language.js", new { controller = "Language", action = "Language" });
			GlobalFilters.Filters.Add(new LanguageFilterAttribute());
        }
    }
}
