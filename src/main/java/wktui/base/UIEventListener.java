package wktui.base;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.apache.wicket.behavior.Behavior;

import io.wktui.event.UIEvent;
 

public abstract class UIEventListener<T extends UIEvent> extends Behavior {
				
	private static final long serialVersionUID = 1L;
	

	static private Logger logger = Logger.getLogger( UIEventListener.class.getName());

	
	private Class<?> eventclass;
	
	public UIEventListener() {
		
		try {
			Type superclass = getClass().getGenericSuperclass();
			Type tType = ((ParameterizedType)superclass).getActualTypeArguments()[0];
			String typename = tType.toString();
			if (typename.startsWith("class ")) {
				typename = typename.substring(6); 
			}
			if (typename.indexOf("<")>0) {
				typename = typename.substring(0, typename.indexOf("<"));
			}
			@SuppressWarnings("unchecked")
			Class<T> eventclass = (Class<T>)Class.forName(typename);
			this.eventclass = eventclass;
		}
		catch (Exception e) {
			logger.error(e);
		}
	}
	
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
