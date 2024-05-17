package io.wkyui.breadcrumb;


import java.util.Iterator;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;

import wktui.base.FrontEndEvent;
import wktui.base.FrontEndEventListener;


public class AjaxBCElement<T> extends AjaxLink<T> implements AjaxIBCElement  {
	private static final long serialVersionUID = 1L;
																								
	
	private IModel<T> sec_model;

	private IModel<String> label;
	private String resourceKey;
	private String key;
	private IModel<String> html_title;
	
	public AjaxBCElement(String resourceKey) {
		super("link");
		this.resourceKey = resourceKey;
		label = newLabel();
	}
	
	public AjaxBCElement() {
		super("link");
		label = newLabel();
	}
	
	public AjaxBCElement(String id, IModel<T> model) {
		super("link", model);
	}
	
	public AjaxBCElement(IModel<T> model, IModel<String> res_model) {
		super("link", model);
		
		sec_model= model;
		label=res_model;
	}
	public AjaxBCElement(IModel<String> res_model, String key) {
		super("link");
		label=res_model;
		this.key=key;
	}
	
	public AjaxBCElement(IModel<T> model, IModel<String> res_model, String key) {
		super("link", model);
		
		sec_model=model;
		label=res_model;
		this.key=key;
	}
	
	public IModel<String> getLabel() {
		if (label==null)
			return new Model<String>("null");
		return label;
	}
	
	@Override
	public void onDetach() {
		if(label!=null)
			label.detach();
		super.onDetach();
	}
	
	public AbstractLink getLink(String id) {
		return this;
	}

	public boolean isNewTab() {
		return false;
	}
	
	protected IModel<String> newLabel() {
		return  new ResourceModel(resourceKey);
	}

	@Override
	public void onClick(AjaxRequestTarget target) {
		//logger.debug("must implement this method");
	}

	@Override
	public boolean isAjax() {
		return true;
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
	
	/**
	 * Scans Page and all its components
	 * The first Component that listens to this event will handle it
	 **/
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
	
	protected boolean fire(FrontEndEvent event, Iterator<Component> components) {
		return fire(event, components, true);
	}

	@SuppressWarnings("unchecked")
	protected boolean fire(FrontEndEvent event, Iterator<Component> components, boolean stop_first_hit) {
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

	@Override
	public void onClick() {
		throw new IllegalStateException("must use onclick(target)");
	}
	
	public IModel<T> getSecModel() {
		return this.sec_model;
	}
	
	public void setKey(String key) {
		this.key=key;
	}
	
	public String getKey() {
		return this.key;
	}

	public void onInitialize() {
		super.onInitialize();
		if (this.getHTMLTitleAttribute()!=null)
			this.add( new AttributeModifier("title", getHTMLTitleAttribute()));
		
	}
	

	public void setHTMLTitleAttribute(IModel<String> ht) {
		html_title=ht;
	}
	
	@Override
	public IModel<String> getHTMLTitleAttribute() {
		return this.html_title;
	}
}
