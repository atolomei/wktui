package io.wktui.event;

import org.apache.wicket.ajax.AjaxRequestTarget;

public class SimpleAjaxWicketEvent implements WicketAjaxEvent {

	final String moreinfo;
	final String name;
	final AjaxRequestTarget target;
	

	public SimpleAjaxWicketEvent( String name, AjaxRequestTarget target) {
	this(name, target, null);
	}
	
	public SimpleAjaxWicketEvent( String name, AjaxRequestTarget target, String moreinfo) {
		
		if (name==null)
			 this.name=getClass().getSimpleName();
		else
			this.name=name;
		
		this.target=target;
		this.moreinfo=moreinfo;
				
	}

	public String getName() { 
		return this.name;
	}
	
	@Override
	public AjaxRequestTarget getTarget() {
		return target;
	}
	
	public String getMoreInfo() {
		return this.moreinfo;
	}

	public String toString() {
		return getClass().getSimpleName()+"{ \"name\": " + getName()+"}";
	}

}
