package io.wktui.struct.list;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IDetachable;
import org.apache.wicket.model.IModel;

import io.wktui.event.SimpleAjaxWicketEvent;
 

public class ListPanelWicketEvent<T> extends SimpleAjaxWicketEvent implements IDetachable {
	
	private static final long serialVersionUID = 1L;
	private final IModel<T> model;
	private final boolean expanded;
	
	public ListPanelWicketEvent(String name, AjaxRequestTarget target, IModel<T> model, boolean expanded)  {
		super(name, target);
		this.model=model;
		this.expanded=expanded;
	}
	
	public boolean isExpanded() {
		return this.expanded;
	}
	
	
	public IModel<T> getModel() {
		return this.model;
	}

	
	public void detach() {
		if (this.model!=null)
			model.detach();
	}
}
