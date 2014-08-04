namespace MvcOSGi.Shell.Code52.i18n
{
    using System.Threading;
    using System.Web.Mvc;

    public class LanguageFilterAttribute : ActionFilterAttribute
    {
        public override void OnActionExecuting(ActionExecutingContext filterContext)
        {
            var cultureName = "zh-Hant"; // This is safe
            Thread.CurrentThread.CurrentCulture = new System.Globalization.CultureInfo(cultureName);
            Thread.CurrentThread.CurrentUICulture = Thread.CurrentThread.CurrentCulture;

            base.OnActionExecuting(filterContext);
        }
    }
}