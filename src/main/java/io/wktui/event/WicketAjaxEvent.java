package io.wktui.event;

import org.apache.wicket.ajax.AjaxRequestTarget;

public interface WicketAjaxEvent extends UIEvent {

	public AjaxRequestTarget getTarget();
	

	
	
}
