package io.wktui.nav.toolbar;

import org.apache.wicket.model.IModel;

import io.wktui.nav.menu.DropDownMenu;
import io.wktui.nav.menu.MenuItemFactory;
import io.wktui.nav.menu.NavDropDownMenu;
import io.wktui.nav.menu.NavDropDownMenu.TitleFragment;

public class DropDownMenuToolbarItem<T> extends ToolbarItem {
	
	private static final long serialVersionUID = 1L;
	
	private NavDropDownMenu<T> menu;
	private IModel<T> model;
	private IModel<String> title;
	
	public DropDownMenuToolbarItem(String id) {
		super(id);
		super.setAlign(Align.TOP_RIGHT);
	}
	
	public DropDownMenuToolbarItem(String id, Align align) {
		super(id);
		super.setAlign(align);
	}
	

	public DropDownMenuToolbarItem(String id, IModel<T> model) {
		super(id);
		this.model=model;
		super.setAlign(Align.TOP_LEFT);
	}
	
	public DropDownMenuToolbarItem(String id, IModel<T> model, Align align) {
		super(id);
		this.model=model;
		super.setAlign(align);

	}
	
	public DropDownMenuToolbarItem(String id, IModel<T> model, IModel<String> label, Align align) {
		super(id);
		this.model=model;
		this.title=label;
		super.setAlign(align);
	}

	public IModel<T> getModel() {
		return this.model;
	}
	
	public void setTitle(IModel<String> label) {
		this.title=label;
	}
	
	public IModel<String> getTitle() {
		return this.title;
	}
	

	public void onDetach() {
		super.onDetach();
		
		if (this.model!=null)
			this.model.detach();
	}
	
	
	
	
	public void addItem(MenuItemFactory<T> item) {
		if (menu==null) {
			menu = new NavDropDownMenu<T>("menu", getModel()) {
				private static final long serialVersionUID = 1L;
				public IModel<String> getTitle() {
					return DropDownMenuToolbarItem.this.getTitle();
				}
			};
		};
		menu.addItem(item);
	}
	
	public void onInitialize() {
		super.onInitialize();
	
		if (menu==null) {
			menu = new NavDropDownMenu<T>("menu", getModel()) {
				private static final long serialVersionUID = 1L;
				public IModel<String> getTitle() {
					return DropDownMenuToolbarItem.this.getTitle();
				}
			};
		}
		add(menu);
	}

	protected void addTitlePanel() {
		menu.addTitlePanel();
	}
	

}
