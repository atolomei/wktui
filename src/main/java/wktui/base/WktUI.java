package wktui.base;

import org.apache.wicket.request.resource.CssResourceReference;

import wktui.bootstrap.Bootstrap;

public class WktUI {

	public static CssResourceReference getCssResourceReference() {
		return new CssResourceReference(Bootstrap.class, "./css/wktui.css");
	}
}
