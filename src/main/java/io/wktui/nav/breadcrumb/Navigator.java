package io.wktui.nav.breadcrumb;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.pages.RedirectPage;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IDetachable;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import io.wktui.nav.listNavigator.ListNavigator;
import io.wktui.nav.menu.LinkMenuItem;
import io.wktui.nav.menu.MenuItemFactory;
import io.wktui.nav.menu.MenuItemPanel;
import io.wktui.nav.menu.NavDropDownMenu;
import wktui.base.BasePanel;
import wktui.base.ModelPanel;

public class Navigator<T> extends BasePanel {

	private static final long serialVersionUID = 1L;

	static private Logger logger = Logger.getLogger(Navigator.class.getName());

	public static final int LIMIT = 40;

	 
	private IModel<T> model;
	private List<IModel<T>> list;
    
	private Link<T> prev;
	private Link<T> next;
    
    int current = 0;
    
	 
	private String active_css = "active";

	 public Navigator(String id, List<IModel<T>> list) {
	        this(id, 0, list);
	    }
	    
	    
	    public Navigator(String id, int current, List<IModel<T>> list) {
	        super(id);
	        this.list=list;
	        this.current=current;
	    }

	public IModel<T> getModel() {
		return this.model;
	}

	public void onDetach() {
		super.onDetach();
		try {

			if (model != null)
				model.detach();
			
			if (this.list!=null)
			  this.list.forEach( t -> t.detach());

		} catch (Exception e) {
			// logger.error(e);
		}
	}

	public void onInitialize() {
		super.onInitialize();

		
		  this.prev = new Link<T>("prev", prev()) {
	            private static final long serialVersionUID = 1L;
	            @Override
	            public void onClick() {
	                if (current>0)
	                   Navigator.this.navigate(current-1);
	            }
	        };
	        add(prev);
	        //Label p_title = new Label("title", getLabel( prev() ));
	        //this.prev.add(p_title);

	        if (current==0)
	            this.prev.setVisible(false);
	        
	        
	        
	        
	        this.next = new Link<T>("next", next()) {
	            private static final long serialVersionUID = 1L;
	            @Override
	            public void onClick() {
	                if (current< getList().size()-1)
	                   Navigator.this.navigate(current+1);
	            }
	        };
	        add(next);
	       // Label n_title = new Label("title", getLabel( next() ));
	       // this.next.add(n_title);
	        
	        if (current==list.size()-1)
	            this.next.setVisible(false);
	}
 
	public void setActiveCss(String s) {
		this.active_css = s;
	}

	protected String getActiveCss() {
		return active_css;
	}

	  
    public IModel<T> prev() {
        if (this.current>0) {
            return getList().get( current-1);
        }
        return getList().get(current);
    }

    public  List<IModel<T>> getList() {
        return this.list;
    }

    public IModel<T> next() {
        
        if (this.current<getList().size()-1) {
            return getList().get(this.current+1);
        }
        return getList().get(this.current);
    }
    
    protected void navigate(int current) {
    }


}
