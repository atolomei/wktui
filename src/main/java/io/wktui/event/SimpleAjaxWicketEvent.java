package io.wktui.event;

import org.apache.wicket.ajax.AjaxRequestTarget;

public class SimpleAjaxWicketEvent implements WicketAjaxEvent {

	final String name;
	final AjaxRequestTarget target;
	
	public SimpleAjaxWicketEvent( String name, AjaxRequestTarget target) {
		this.name=name;
		this.target=target;
	}

	public String getName() { 
		return this.name;
	}
	
	@Override
	public AjaxRequestTarget getTarget() {
		return target;
	}
	
	public String toString() {
		return getClass().getSimpleName()+"{ \"name\": " + getName()+"}";
	}

}
