package io.wktui.form.button;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
 
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.attributes.IAjaxCallListener;
 
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
 

import io.wktui.form.Form;
import wktui.base.BasePanel;

public abstract class LinkSubmitButton<T> extends BasePanel {

    private static final long serialVersionUID = 1L;

    private Form<T> form;

    private String style;
   
    
    private Label label;

    private String icon;
    private String iconStyle;
    
    private IModel<T> model;
    
    private Button button;
    
    WebMarkupContainer c_h;
    WebMarkupContainer c_v;
    
    private String rowCss;
    private String colCss;
    
    
    WebMarkupContainer w_icon;
    
    
    public LinkSubmitButton(String id) {
        this(id, null, null);
    }
    
    public LinkSubmitButton(String id, Form<T> form) {
        super(id);
        this.model=null;
        this.form=form;
    }
    
    public LinkSubmitButton(String id, IModel<T> model, Form<T> form) {
        super(id);
        this.model=model;
        this.form=form;
    }
  
    @Override
    public void onInitialize() {
        super.onInitialize();
        
        c_h = new WebMarkupContainer ("hContainer");
        add(c_h);
        
        c_v = new WebMarkupContainer ("vContainer");
        c_h.add(c_v);
        
        
        if (getRowCss()!=null) {
        	c_h.add( new org.apache.wicket.AttributeModifier("class", getRowCss()));
        }
        
        if (getColCss()!=null) {
        	c_v.add( new org.apache.wicket.AttributeModifier("class", getColCss()));
        }
     
        
        this.button = new Button("submitButton") {
            private static final long serialVersionUID = 1L;
            @Override
            public void onSubmit() {
                LinkSubmitButton.this.onSubmit();     
            }
        };
        
        c_v.add(button);
     
        label = new Label("submit", getLabel()) {
        	
        	/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public boolean isVisible() {
        		return getLabel()!=null;
        	}
        };
        
        
        button.add(label);
        
        w_icon = new  WebMarkupContainer("icon" ) {
        	 
			private static final long serialVersionUID = 1L;

			public boolean isVisible() {
        		return getIcon()!=null;
        	}
        };
        button.add(w_icon);
        
        if (getIcon()!=null) {
        	w_icon.add( new org.apache.wicket.AttributeModifier("class", getIcon()));
        }
        
        if (getIconStyle()!=null) {
        	w_icon.add( new org.apache.wicket.AttributeModifier("style", getIconStyle()));
        }
        
        
        if (getSaveCss()!=null) 
        	button.add( new org.apache.wicket.AttributeModifier("class", getSaveCss()));
       
        if (getStrStyle()!=null) 
        	button.add( new org.apache.wicket.AttributeModifier("style", getStrStyle()));
        
    }
   
    public IModel<String> getLabel() {
    	return new StringResourceModel("button.submit");
    			
    }
    
    public String getBeforeHandler() {
		return "";
	}
    
    
    @Override
    public void onDetach() {
        super.onDetach();
        
        if (this.model!=null)
            this.model.detach();
    }
    

    public String getStrStyle() {
        return style;
    }

    public void setStrStyle( String s) {
        style=s;
    }

    protected String getRowCss() {
        return rowCss;
    }
 
    protected String getColCss() {
        return colCss;
    }

    
    
    protected String getSaveCss() {
        return "btn btn-primary btn-sm";
    }
    
    
    @Override
    public boolean isEnabled() {
        return true;
        //return isEditionEnabled();
    }


    protected abstract void onSubmit();
        
    protected void onError() {
    }

	public String getIconStyle() {
		return iconStyle;
	}

	public void setIconStyle(String iconStyle) {
		this.iconStyle = iconStyle;
	}

	public void setRowCss(String rowCss) {
		this.rowCss = rowCss;
	}

	public void setColCss(String colCss) {
		this.colCss = colCss;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
      
	protected IModel<String> getWorkingLabel() {
		return new StringResourceModel("saving", this, null);
	}
    
}
