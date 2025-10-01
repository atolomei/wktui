package io.wktui.nav.menu;

import org.apache.wicket.model.IModel;

public class SeparatorMenuItem<T> extends MenuItemPanel<T> {

	private static final long serialVersionUID = 1L;

	public SeparatorMenuItem(String id) {
		super(id);
	}

	public SeparatorMenuItem(String id, IModel<T> model) {
		super(id, model);
	}
	
	@Override
	public IModel<String> getLabel() {
		return null;
	}
	
	//@Override
	//public String getCssClass()  {
//		return "border-top float-start w-100";
//	}
	

}
