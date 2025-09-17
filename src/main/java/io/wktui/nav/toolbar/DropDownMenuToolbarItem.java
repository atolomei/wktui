package io.wktui.nav.toolbar;

import org.apache.wicket.model.IModel;

import io.wktui.nav.menu.DropDownMenu;
import io.wktui.nav.menu.MenuItemFactory;
import io.wktui.nav.menu.NavDropDownMenu;

public class DropDownMenuToolbarItem<T> extends ToolbarItem {
	
	private static final long serialVersionUID = 1L;
	
	private DropDownMenu<T> menu;
	private IModel<T> model;
	private IModel<String> label;
	
	public DropDownMenuToolbarItem(String id) {
		super(id);
		super.setAlign(Align.TOP_RIGHT);
	}
	
	
	public DropDownMenuToolbarItem(String id, IModel<T> model, Align align) {
		super(id);
		this.model=model;
		super.setAlign(align);

	}
	
	public DropDownMenuToolbarItem(String id, IModel<T> model, IModel<String> label, Align align) {
		super(id);
		this.model=model;
		this.label=label;
		super.setAlign(align);
	}

	public IModel<T> getModel() {
		return this.model;
	}
	
	public void setLabel(IModel<String> label) {
		this.label=label;
	}
	

	public void onDetach() {
		super.onDetach();
		
		if (this.model!=null)
			this.model.detach();
	}
	
	public void addItem(MenuItemFactory<T> item) {
		if (menu==null)
			menu = new NavDropDownMenu<T>("menu", getModel(), this.label);
		menu.addItem(item);
	}
	
	public void onInitialize() {
		super.onInitialize();
	
		if (menu==null) {
			menu = new NavDropDownMenu<T>("menu", getModel(), this.label);
		}
		add(menu);
	}



}
