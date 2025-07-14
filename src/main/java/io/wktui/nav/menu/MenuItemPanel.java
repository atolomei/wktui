package io.wktui.nav.menu;

import org.apache.wicket.model.IModel;


import wktui.base.BasePanel;

public abstract class MenuItemPanel<T> extends BasePanel implements MenuItem {
	
	private static final long serialVersionUID = 1L;
	
	private IModel<T> model;
	private int index;
	private String iconcss = null;  
	

	public MenuItemPanel(String id) {
		this(id, null,  null);
	}
	
	public MenuItemPanel(String id, IModel<T> model) {
		this(id, model, null);
		
	}
	
	public MenuItemPanel(String id, String iconcss) {
		this(id, null, iconcss);
		
	}

	public MenuItemPanel(String id, IModel<T> model, String iconcss) {
		super(id);
		setOutputMarkupId(true);
		this.model=model;
		this.iconcss=iconcss;
	}

	
	public IModel<T> getModel() {
		return model;
	}
	
	public T getModelObject() {
		return getModel().getObject();
	}
	
	public void setModel(IModel<T> model)  {
		this.model = model;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public int getIndex() {
		return this.index;
	}
	
	/**
	 * Css of the menu item <li>
	 * 
	 */
	public String getCssClass() {
		return null;
	}
	
	/**
	 * Css of the icon  <i>
	 * 
	 */
	public String getIconCssClass() {
		return iconcss; 	
	}
	
	public void setIconCssClass(String c) {
		iconcss=c; 	
	}
	
	public String getIcon() {
		return null;
	}
	
	public String getUrl() {
		return null;
	}
	
	public void onDetach() {
		super.onDetach();
		if (this.model!=null) 
			this.model.detach();
	}
	
	public String getTarget() {
		return null;
	}
	


}
