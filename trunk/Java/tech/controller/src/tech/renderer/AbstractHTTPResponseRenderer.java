package tech.renderer;

import tech.listener.HTTPRequestContext;

public abstract class AbstractHTTPResponseRenderer {

	public abstract void render(final HTTPRequestContext context);
}
