package wktui.base;


import java.util.Iterator;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.WebRequest;


public abstract class BasePanel extends Panel {
	
	
	private static final long serialVersionUID = 1L;
	
	
	public BasePanel(String id) {
			super(id);
		addListeners();
	}

	
	
	@SuppressWarnings("unchecked")
	public void fireScanAll(FrontEndEvent event) {
		for (FrontEndEventListener<FrontEndEvent> listener : getPage().getBehaviors(FrontEndEventListener.class)) {
			if (listener.handle(event)) {
				listener.onEvent(event);
			}
		}
		fire(event, getPage().iterator(), false);
	}

	public boolean fire(FrontEndEvent event, Iterator<Component> components) {
		return fire(event, components, true);
	}
	
	@SuppressWarnings("unchecked")
	public boolean fire(FrontEndEvent event, Iterator<Component> components, boolean stop_first_hit) {
		boolean handled = false;
		while (components.hasNext()) {
			Component component = components.next();
			for (FrontEndEventListener<FrontEndEvent> listener : component.getBehaviors(FrontEndEventListener.class)) {
				if (listener.handle(event)) {
					listener.onEvent(event);
					if (stop_first_hit) {
						handled = true;
						break;
					}
				}
			}
			if (!handled) {
				if (component instanceof MarkupContainer) {
					handled = fire (event, ((MarkupContainer)component).iterator(), stop_first_hit);
				}
			}
			else {
				break;
			}
		}
		return handled;
	}
	

	@SuppressWarnings("unchecked")
	public void fire(FrontEndEvent event) {
		boolean handled=false;
		for (FrontEndEventListener<FrontEndEvent> listener : getPage().getBehaviors(FrontEndEventListener.class)) {
			if (listener.handle(event)) {
				listener.onEvent(event);
					handled = true;
					break;
				}
			}
		if (!handled) 
			fire(event, getPage().iterator());
	}
	
	/**
	 * 
	 * URL from HTTP Request received
	 * Wicket based
	 * 
	 * @return
	 */
	protected String getServerUrl() {
		String protocol =((WebRequest)RequestCycle.get().getRequest()).getUrl().getProtocol();
		String host =((WebRequest)RequestCycle.get().getRequest()).getUrl().getHost();
		Integer iport =((WebRequest)RequestCycle.get().getRequest()).getUrl().getPort(); 
		String port = (iport.equals(80) || iport.equals(443) ? "":  ( ":" + iport.toString()) );
		return protocol +"://" + host + port;
	}
	
	protected void addListeners() {
	}
	
	protected IModel<String> getLabel(String key) {
		return new StringResourceModel(key, this, null);
	}
	
	protected String getLabelString(String key) {
		return getLabel(key).getObject();
	}
	
	protected String getLabelString(String key, String... parameter) {
		return getLabel(key, parameter).getObject();
	}
	
	protected IModel<String> getLabel(String key, String... parameter) {
		StringResourceModel model = new StringResourceModel(key, this, null);
		model.setParameters((Object[])parameter);
		return model;
	}
	

}
