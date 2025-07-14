package io.wktui.nav.menu;

import org.apache.wicket.model.IModel;

public class SeparatorMenuItem extends MenuItemPanel<Void> {

	private static final long serialVersionUID = 1L;

	public SeparatorMenuItem(String id) {
		super(id);
	}

	
	@Override
	public IModel<String> getLabel() {
		return null;
	}
	
	
	
	

}
