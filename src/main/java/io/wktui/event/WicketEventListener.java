package io.wktui.event;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.apache.wicket.behavior.Behavior;

import wktui.base.Logger;
import wktui.base.UIEventListener;


public abstract class WicketEventListener<T extends UIEvent> extends UIEventListener<T> {

private static final long serialVersionUID = 1L;
	
	static private Logger logger = Logger.getLogger( WicketEventListener.class.getName());


	private Class<?> eventclass;
	
	
	public WicketEventListener(T event) {
		super(event);
	}
	
	public WicketEventListener() {
		super();
	}
	
	/**
	public boolean handles(Class<T> claz) {
		return this.eventclass.equals(claz);
	}
	
	public boolean handle(UIEvent event) {
		return eventclass.isInstance(event);
	}
	
	
	public Class<?> getEventClass() {
		return this.eventclass;
	}
	
	public abstract void onEvent(T event);
	*/
	
}
