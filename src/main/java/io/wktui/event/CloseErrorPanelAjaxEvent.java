package io.wktui.event;

import org.apache.wicket.ajax.AjaxRequestTarget;

public class CloseErrorPanelAjaxEvent extends SimpleAjaxWicketEvent {

	public CloseErrorPanelAjaxEvent(String name, AjaxRequestTarget target) {
		super(name, target);
	}

	public CloseErrorPanelAjaxEvent(String name, AjaxRequestTarget target, String moreInfo) {
		super(name, target, moreInfo);
	}

	
}
