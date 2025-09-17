package wktui.base;

import org.apache.wicket.behavior.Behavior;

import io.wktui.event.UIEvent;

public abstract class UIEventListener<T extends UIEvent> extends Behavior {
				
	private static final long serialVersionUID = 1L;
	
	private Class<?> eventclass;
	
	public UIEventListener(T event) {
		this.eventclass = event.getClass();
	}
	
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
	
}
