package tech.renderer;

import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;

import tech.listener.HTTPRequestContext;

public final class PNGRenderer extends AbstractHTTPResponseRenderer {

    private static final Logger LOGGER = Logger.getLogger(PNGRenderer.class.getName());

    @Override
    public void render(final HTTPRequestContext context) {
        try {
            final HttpServletResponse response = context.getResponse();
            response.setContentType("image/png");

            final OutputStream outputStream = response.getOutputStream();
//            outputStream.write(image.getData());
            outputStream.close();
        } catch (final IOException e) {
            LOGGER.log(Level.SEVERE, "Render failed", e);
        }
    }
}
