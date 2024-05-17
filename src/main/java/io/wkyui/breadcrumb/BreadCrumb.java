package io.wkyui.breadcrumb;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;


import wktui.base.BasePanel;

public class BreadCrumb<T> extends BasePanel {

	
	private static final long serialVersionUID = 1L;
	
	private List<Panel> list;
	private IModel<T> model;
	
	private String base_css = "breadcrumb-item";
	private String active_css = "active";
	
	
	public BreadCrumb() {
		this ("breadcrumb", null);
	}
	
	public BreadCrumb(String id, IModel<T> model) {
		super(id, model);
		this.model=model;
		list = new ArrayList<Panel>();
	}

	public IModel<T> getModel() {
		return this.model;
	}
	
	public void onDetach() {
		super.onDetach();
		try {
		
		if (model!=null)
			model.detach();
			
		if (list==null)
			return;
			
		for (Panel panel: list) 
			panel.detach();
		} catch (Exception e) {
			//logger.error(e);
		}
	}
	
	public void onInitialize() {
		super.onInitialize();
		
		addComponents();
	}
	
	
	
	
	public BreadCrumb<T> addElement(Panel p) {
		list.add(p);
		return this;
	}
	
	public BreadCrumb<T> addElement(BCElement b) {
		list.add(new BreadCrumbItemPanel(b));
		return this;
	}
	
	//public BreadCrumb<T> addElement(AjaxIBCElement b) {
	//	list.add(new MenuAjaxBCElement(b));
	//	return this;
	//}
	
	
	public void setActiveCss(String s) {
		this.active_css=s;
	}
	
	protected String getActiveCss() {
		return active_css;
	}

	protected void addComponents() {
		
		ListView<Panel> v = new ListView<Panel>("breadcrumb-element", list) {
			private static final long serialVersionUID = 1L;
			@Override
			protected void populateItem(ListItem<Panel> item) {
				Panel element = item.getModelObject();
				item.add(element);
				if ((item.getIndex()==(list.size()-1)) && (getActiveCss()!=null)) 
					item.add(new AttributeModifier("class", base_css+ " " + getActiveCss()));
				else
					item.add(new AttributeModifier("class", base_css));
					
			}	
		};
		
		
		add(v);
 	}
	
}
