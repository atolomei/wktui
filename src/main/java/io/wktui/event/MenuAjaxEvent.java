package io.wktui.event;

import org.apache.wicket.ajax.AjaxRequestTarget;

public class MenuAjaxEvent extends SimpleAjaxWicketEvent {

	public MenuAjaxEvent(String name, AjaxRequestTarget target) {
		super(name, target);
	}

	public MenuAjaxEvent(String name, AjaxRequestTarget target, String moreInfo) {
		super(name, target, moreInfo);
	}

	
}
