package tech.renderer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StaticResources {

	static final List<String> staticPaths = new ArrayList<>();

	public static void init() {
		staticPaths.clear();
		staticPaths.add("/script");
		staticPaths.add("/style");
		staticPaths.add("/error");
		staticPaths.add("/dic");
		staticPaths.add("/res");
		staticPaths.add("/component");
	}

	public static boolean isStaticPath(String contextPath, String requestUrl) {
		requestUrl = requestUrl.replace(contextPath, "");
		if (requestUrl.equals("/")) {
			return false;
		}
		for (String str : staticPaths) {
			if (requestUrl.startsWith(str)) {
				return true;
			}
		}
		return false;
	}

	public static void processStaticResourceRequeset(
			HttpServletRequest request, HttpServletResponse response) {
		try {
			String requestURI = request.getRequestURI();
			String contextPath = request.getServletContext().getContextPath();
			requestURI = requestURI.replace(contextPath, "");
			request.getServletContext().getNamedDispatcher("default")
					.forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}
}
