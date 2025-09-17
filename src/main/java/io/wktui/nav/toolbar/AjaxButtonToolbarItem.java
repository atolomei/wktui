package io.wktui.nav.toolbar;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.model.IModel;

public abstract class AjaxButtonToolbarItem<T> extends ButtonToolbarItem<T> {

	private static final long serialVersionUID = 1L;
	
	private AjaxLink<T> link;
	
	public AjaxButtonToolbarItem()  {
		super("item");
	}

	public AjaxButtonToolbarItem(String id) {
		super(id);
	}

	public AjaxButtonToolbarItem(String id, IModel<String> title) {
		super(id, title);
 	}
	
	public AjaxButtonToolbarItem(IModel<String> title) {
		super("item", title);
	}
	
	public AjaxButtonToolbarItem(String id, IModel<T> model, IModel<String> title) {
		super(id, model, title);
	}
	public AjaxButtonToolbarItem(IModel<T> model, IModel<String> title) {
		super("item", model, title);
	}
	
	@Override
	protected AbstractLink createLink() {

		this.link = new AjaxLink<T> ( "link", getModel()) {
			private static final long serialVersionUID = 1L;
			@Override
			public void onClick(AjaxRequestTarget target) {
				AjaxButtonToolbarItem.this.onCick(target);
			}
		};
		
		return this.link;
	}

	protected abstract void onCick(AjaxRequestTarget target);

}
