package io.wktui.nav.breadcrumb;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.pages.RedirectPage;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IDetachable;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import io.wktui.nav.menu.LinkMenuItem;
import io.wktui.nav.menu.MenuItemFactory;
import io.wktui.nav.menu.MenuItemPanel;
import io.wktui.nav.menu.NavDropDownMenu;
import wktui.base.BasePanel;
import wktui.base.ModelPanel;

public class BreadCrumb<T> extends ModelPanel<T> {

	            
	private static final long serialVersionUID = 1L;
	
	static private Logger logger = Logger.getLogger(BreadCrumb.class.getName());
	
	public static final int LIMIT = 40;
	
	private List<BreadcrumbBasePanel> list;
	private IModel<T> model;
	private WebMarkupContainer breadcrumbContainer;
	
	private String base_css = "breadcrumb-item";
	private String active_css = "active";
	
	
	public BreadCrumb() {
		this ("breadcrumb", null);
	}
	
	public BreadCrumb(String id, IModel<T> model) {
		super(id, model);
		this.model=model;
		list = new ArrayList<BreadcrumbBasePanel>();
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
			
		for (BreadcrumbBasePanel panel: list) 
			    panel.detach();
		
		} catch (Exception e) {
			//logger.error(e);
		}
	}
	
	public void onInitialize() {
		super.onInitialize();
		
		addComponents();
	}
	
	
	
	
	public BreadCrumb<T> addElement(BreadcrumbBasePanel p) {
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
		
		ListView<BreadcrumbBasePanel> v = new ListView<BreadcrumbBasePanel>("breadcrumb-element", list) {
			private static final long serialVersionUID = 1L;
			@Override
			protected void populateItem(ListItem<BreadcrumbBasePanel> item) {
				Panel element = item.getModelObject();
				item.add(element);
				if ((item.getIndex()==(list.size()-1)) && (getActiveCss()!=null)) { 
					item.add(new AttributeModifier("class", base_css+ " " + getActiveCss()));
					item.getModelObject().add(new AttributeModifier("aria-current", "page"));
				}
				else
				    item.add(new AttributeModifier("class", base_css));
					
			}	
		};
		
		
		breadcrumbContainer = new WebMarkupContainer("breadcrumbContainer");
		addOrReplace(breadcrumbContainer);
		breadcrumbContainer.add(v);
		
		
	      NavDropDownMenu<BreadcrumbBasePanel> menu = new NavDropDownMenu<BreadcrumbBasePanel>("menu", null, new Model<String>("Navegación"));

	       for( int counter=0; counter<list.size(); counter++) {
	          
	          final int final_counter = counter;
	          
	          menu.addItem( new MenuItemFactory<BreadcrumbBasePanel>() {
                private static final long serialVersionUID = 1L;
                            @Override
                            public MenuItemPanel<BreadcrumbBasePanel> getItem(String id) {
                                return new LinkMenuItem<BreadcrumbBasePanel>(id) {
                                    private static final long serialVersionUID = 1L;
                                    @Override
                                    public IModel<String> getLabel() {
                                       return list.get(final_counter).getLabel();
                                    }
                                    
                                    @Override
                                    public void onClick() {
                                        list.get(final_counter).onClick();
                                    }
                                };
                            }
	          });
	      }
	        
	      add(menu);
		
		
		
		
 	}
	
}
