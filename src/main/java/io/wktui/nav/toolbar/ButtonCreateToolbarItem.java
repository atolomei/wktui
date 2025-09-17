package io.wktui.nav.toolbar;

import org.apache.wicket.model.IModel;

public abstract class ButtonCreateToolbarItem<T> extends LinkButtonToolbarItem<T> {

	private static final long serialVersionUID = 1L;

	public ButtonCreateToolbarItem() {
		super("item", null, null);
	}

	
	public ButtonCreateToolbarItem(String id ) {
		super(id, null, null);
	}
	
	
	public ButtonCreateToolbarItem(String id, IModel<T> model) {
		super(id, model, null);
	}
	
	public ButtonCreateToolbarItem(String id, IModel<T> model, IModel<String> title) {
		super(id, model, title);
		 
	}
	
	@Override
	public IModel<String> getButtonLabel() {
		return getLabel("create");
	}

}
