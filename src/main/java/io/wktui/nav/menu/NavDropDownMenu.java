package io.wktui.nav.menu;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;

import wktui.base.BasePanel;

public class NavDropDownMenu<T> extends DropDownMenu<T> {


	private static final long serialVersionUID = 1L;
	
	Label label;
	IModel<String> title;
	
	
	public NavDropDownMenu(String id, IModel<T> model, IModel<String> title) {
		super(id, model);
		this.title=title;
  
	}
	
	@Override
	public IModel<T> getModel() {
		return (IModel<T>) super.getModel(); 
	}
	
	@Override
	public void onInitialize() {
		super.onInitialize();
	
		label = new Label("label", getLabel()) {
			private static final long serialVersionUID = 1L;
			public boolean isVisible() {
				return getLabel()!=null;
			}
		};
		
		add(label);
	}
	
	
	public void addItem(MenuItemFactory<T> item) {
			super.addItem(item);
	}
	
	
	
	public void setLabel(IModel<String> label) {
		this.title=label;
	}
	
	public IModel<String> getLabel() {
		return title;
	}

}
