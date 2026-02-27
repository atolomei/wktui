package io.wktui.event;

import org.apache.wicket.ajax.AjaxRequestTarget;

public class HelpAjaxEvent extends SimpleAjaxWicketEvent {

	public HelpAjaxEvent(String name, AjaxRequestTarget target) {
		super(name, target);
	}

	public HelpAjaxEvent(String name, AjaxRequestTarget target, String moreInfo) {
		super(name, target, moreInfo);
	}

	
}
