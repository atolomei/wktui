package wktui.base;

import org.apache.wicket.behavior.Behavior;

public abstract class FrontEndEventListener<T extends FrontEndEvent> extends Behavior {
				
	private static final long serialVersionUID = 1L;
	
	private Class<?> eventclass;
	
	public FrontEndEventListener(T event) {
		this.eventclass = event.getClass();
	}
	
	public boolean handles(Class<T> claz) {
		return this.eventclass.equals(claz);
	}
	
	public boolean handle(FrontEndEvent event) {
		return eventclass.isInstance(event);
	}
	
	
	public Class<?> getEventClass() {
		return this.eventclass;
	}
	
	public abstract void onEvent(T event);
	
}
