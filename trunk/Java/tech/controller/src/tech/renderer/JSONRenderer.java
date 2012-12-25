package tech.renderer;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;

import tech.listener.HTTPRequestContext;

public final class JSONRenderer extends AbstractHTTPResponseRenderer {

    private static final Logger LOGGER = Logger.getLogger(JSONRenderer.class.getName());
    private String callback = "callback";


    public void setCallback(final String callback) {
        this.callback = callback;
    }

    @Override
    public void render(final HTTPRequestContext context) {
        final HttpServletResponse response = context.getResponse();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

//        try {
//            final PrintWriter writer = response.getWriter();
//
//            if (!isJSONP) {
//                writer.println(jsonObject);
//            } else {
//                writer.print(callback + "(" + jsonObject + ")");
//            }
//
//            writer.close();
//        } catch (final Exception e) {
//            LOGGER.log(Level.SEVERE, "FreeMarker renders error", e);
//
//            try {
//                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//                return;
//            } catch (final IOException ex) {
//                LOGGER.log(Level.SEVERE, "Can not send error 500!", ex);
//            }
//        }
    }
}
