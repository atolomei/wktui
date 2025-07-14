package wktui.bootstrap;

import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

public class Bootstrap {

	
	public static CssResourceReference getCssResourceReference() {
		return new CssResourceReference(Bootstrap.class, "./bootstrap-5.3.3-dist/css/bootstrap.css");
	}
	
	
	public static JavaScriptResourceReference getJavaScriptResourceReference() {
		return new JavaScriptResourceReference(Bootstrap.class,"./bootstrap-5.3.3-dist/js/bootstrap.bundle.js");
	}


}
