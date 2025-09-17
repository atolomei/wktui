package io.wktui.nav.toolbar;

import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;

public abstract class LinkButtonToolbarItem<T> extends ButtonToolbarItem<T> {

	private static final long serialVersionUID = 1L;
	
	private Link<T> link;
	
	public LinkButtonToolbarItem()  {
		super("item");
	}

	public LinkButtonToolbarItem(String id) {
		super(id);
	}

	public LinkButtonToolbarItem(String id, IModel<String> title) {
		super(id, title);
 	}
	
	public LinkButtonToolbarItem(IModel<String> title) {
		super("item", title);
	}
	
	public LinkButtonToolbarItem(String id, IModel<T> model, IModel<String> title) {
		super(id, model, title);
	}
	public LinkButtonToolbarItem(IModel<T> model, IModel<String> title) {
		super("item", model, title);
	}
	
	@Override
	protected AbstractLink createLink() {
		
		this.link = new Link<T>("link", getModel()) {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				LinkButtonToolbarItem.this.onClick();
			}
		};
	
		return link;
		
	}

	protected abstract void onClick();  
	 

}
