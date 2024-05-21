package io.wktui.nav.breadcrumb;

import org.apache.wicket.ajax.AjaxRequestTarget;

public interface AjaxIBCElement extends IBCElement {

	default public boolean isAjax() {return true;}
	public void onClick(AjaxRequestTarget target);
	
}
