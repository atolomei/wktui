package io.wktui.nav.listNavigator;

import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import wktui.base.BasePanel;

public class ListNavigator<T> extends BasePanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	List<IModel<T>> list;
    
    Link<T> prev;
    Link<T> next;
    
    int current = 0;
    
    WebMarkupContainer prevContainer;
    WebMarkupContainer nextContainer;
    
    
    
    public ListNavigator(String id, List<IModel<T>> list) {
        this(id, 0, list);
    }
    
    
    public ListNavigator(String id, int current, List<IModel<T>> list) {
        super(id);
        this.list=list;
        this.current=current;
    }

    
    @Override
    public void onInitialize() {
        super.onInitialize();
        
        prevContainer = new WebMarkupContainer ("prevContainer") {
        	 
			private static final long serialVersionUID = 1L;

			public boolean isVisible() {
        		return prev.isVisible();
        	}
        };
        add(prevContainer);
        
     
        
       nextContainer = new WebMarkupContainer ("nextContainer") {
       	 
			private static final long serialVersionUID = 1L;

			public boolean isVisible() {
        		return next.isVisible();
        	}
        };
        add(nextContainer);

        
        this.prev = new Link<T>("prev", prev()) {
            private static final long serialVersionUID = 1L;
            @Override
            public void onClick() {
                if (current>0)
                    ListNavigator.this.navigate(current-1);
            }
        };
        prevContainer.add(prev);
        Label p_title = new Label("title", getLabel( prev() ));
        this.prev.add(p_title);

        if (current==0)
            this.prev.setVisible(false);
        
      
        
        this.next = new Link<T>("next", next()) {
            private static final long serialVersionUID = 1L;
            @Override
            public void onClick() {
                if (current< getList().size()-1)
                    ListNavigator.this.navigate(current+1);
            }
        };
        
        nextContainer.add(next);
        
        Label n_title = new Label("title", getLabel( next() ));
        this.next.add(n_title);
        
        if (current==list.size()-1)
            this.next.setVisible(false);
        
        prevContainer.add( AttributeModifier.append("class", 
        		next.isVisible() ?
        				"col-xxl-6 col-xl-6 col-lg-6 col-xs-12 col-md-12 col-sm-12 pt-2 pb-2 text-lg-start text-xl-start text-xxl-start text-center" :
  						"col-xxl-12 col-xl-12 col-lg-12 col-xs-12 col-md-12 col-sm-12 pt-2 pb-2 text-lg-start text-xl-start text-xxl-start text-center"
        		)
        );

        nextContainer.add( AttributeModifier.append("class", 
        		prev.isVisible() ?
        				"col-xxl-6 col-xl-6 col-lg-6 col-xs-12 col-md-12 col-sm-12 pt-2 pb-2 text-lg-start text-xl-start text-xxl-start text-center" :
  						"col-xxl-12 col-xl-12 col-lg-12 col-xs-12 col-md-12 col-sm-12 pt-2 pb-2 text-lg-end text-xl-end text-xxl-end text-center"
        		)
        );

        
        /**
        prev.add( AttributeModifier.append("class", 
        		next.isVisible() ?
        				"col-xxl-6 col-xl-6 col-lg-6 col-xs-12 col-md-12 col-sm-12 pt-2 pb-2 text-lg-start text-xl-start text-xxl-start text-center" :
  						"col-xxl-12 col-xl-12 col-lg-12 col-xs-12 col-md-12 col-sm-12 pt-2 pb-2 text-lg-start text-xl-start text-xxl-start text-center"
        		)
        );
        **/
        
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
    
    @Override
    public void onDetach() {
        super.onDetach();
        
		if (this.list!=null)
			  this.list.forEach( t -> t.detach());

    }
    

    protected IModel<String> getLabel(IModel<T> model) {
        return new Model<String>( model.getObject().getClass().getSimpleName() );
    }
    
    
    protected void navigate(int current) {
    }

    
    
    
}
