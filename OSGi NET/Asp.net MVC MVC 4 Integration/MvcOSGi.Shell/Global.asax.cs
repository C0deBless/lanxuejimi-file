using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Http;
using System.Web.Mvc;
using System.Web.Optimization;
using System.Web.Routing;
using MvcOSGi.Shell;
using MvcOSGi.Shell.Extension;
using MvcOSGi.Shell.Models;
using UIShell.Extension;
using UIShell.OSGi;
using UIShell.OSGi.Core.Service;
using UIShell.OSGi.MvcCore;
[assembly: PreApplicationStartMethod(typeof(MvcApplication), "StartBundleRuntime")]
namespace MvcOSGi.Shell
{
    // Note: For instructions on enabling IIS6 or IIS7 classic mode, 
    // visit http://go.microsoft.com/?LinkId=9394801
   
    public class MvcApplication : HttpApplication
    {
        public ApplicationViewModel ViewModel { get; set; }
        private ExtensionHooker _extensionHooker;

        public static void StartBundleRuntime()
        {
            //Start OSGi
            var bootstapper = new Bootstrapper();
            bootstapper.StartBundleRuntime();
        }

        protected void Application_Start()
        {
            //Register Razor view engine for bundle.
            ViewEngines.Engines.Add(new BundleRuntimeViewEngine(new BundleRazorViewEngineFactory()));
            //Register WebForm view engine.
            ViewEngines.Engines.Add(new BundleRuntimeViewEngine(new BundleWebFormViewEngineFactory()));

            AreaRegistration.RegisterAllAreas();

            WebApiConfig.Register(GlobalConfiguration.Configuration);
            FilterConfig.RegisterGlobalFilters(GlobalFilters.Filters);
            RouteConfig.RegisterRoutes(RouteTable.Routes);
            BundleConfig.RegisterBundles(BundleTable.Bundles);
            
            MonitorExtension();

            //Register the default controller provider source.
            DefaultControllerConfig.Register(typeof(MvcApplication).Assembly);
        }

        private void MonitorExtension()
        {
            ViewModel = new ApplicationViewModel();

            //Register pages in Shell project.
            ViewModel.MainMenuItems.Add(new MenuItem
                                            {
                                                Text = "Home",
                                                URL="/"
                                            });

            BundleRuntime.Instance.AddService<ApplicationViewModel>(ViewModel);
            _extensionHooker = new ExtensionHooker(BundleRuntime.Instance.GetFirstOrDefaultService<IExtensionManager>());
            _extensionHooker.HookExtension("MainMenu",new MainMenuExtensionHandler(ViewModel));
            _extensionHooker.HookExtension("SidebarMenu", new SidebarExtensionHandler(ViewModel));
        }
    }
}