package io.wktui.form.util;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import wktui.base.InvisiblePanel;

/**
 * 
 * @param <T>
 */
public class TitlePanel<T> extends Panel {

    private static final long serialVersionUID = 1L;

    private WebMarkupContainer titleContainer;
    private WebMarkupContainer titleContainerCol;
    private WebMarkupContainer titleSuffix;
    private WebMarkupContainer readonly;
    
    private WebMarkupContainer helpinfo;

    private IModel<T> model;
    private IModel<String> suffixModel;
    
    private boolean isReadOnly = false;
    private boolean isHelp = false;
    private String css;
    
    
    public void setReadOnly( boolean b) {
        this.isReadOnly=b;
    }
    
    public boolean isReadOnly() {
        return this.isReadOnly;
    }
    

    public boolean isHelpInfo() {
        return isHelp;
    }

    
    public TitlePanel(String id, IModel<T> model) {
        super(id, model);
        this.model=model;
    }
    
    public void onDetach() {
        super.onDetach();
        if (model!=null)
            model.detach();
    }
    
    @Override
    public void onInitialize() {
        super.onInitialize();
        
        this.titleContainer = new WebMarkupContainer("titleContainer") {
            private static final long serialVersionUID = 1L;
            public boolean isVisible() {
                return getModel()!=null;
            }
        };
        
        this.titleContainerCol = new WebMarkupContainer("titleContainerCol") {
            private static final long serialVersionUID = 1L;
            public boolean isVisible() {
                return getModel()!=null;
            }
        };
        
        add(this.titleContainer);
        this.titleContainer.add(titleContainerCol);
        
        if (getCss()!=null)
        	this.titleContainer.add(new org.apache.wicket.AttributeModifier("class", getCss())); 
        
        
        if (this.getModel()!=null && this.getModel().getObject()!=null)
            this.titleContainerCol.add(new Label("title", getModel().getObject().toString()));
        else
            this.titleContainerCol.add(new InvisiblePanel("title"));
        
        if (this.getSuffixModel()!=null && this.getSuffixModel().getObject()!=null)
            this.titleContainerCol.add(new Label("titleSuffix", getSuffixModel().getObject().toString()));
        else
            this.titleContainerCol.add(new InvisiblePanel("titleSuffix"));
        
        this.titleContainerCol.add(new InvisiblePanel("readonly"));
        
        
        if (this.isHelpInfo()) {
            
            this.helpinfo = new WebMarkupContainer("helpInfo") {
                private static final long serialVersionUID = 1L;
                public boolean isVisible() {
                    return true;
                }
            };
            this.titleContainerCol.add(this.helpinfo);
        }
        else     
            this.titleContainerCol.add(new InvisiblePanel("helpInfo"));
    }
    
    

    public IModel<T> getModel() {
        return this.model;
    }


    public IModel<String> getSuffixModel() {
        return this.suffixModel;
    }

	public String getCss() {
		return css;
	}

	public void setCss(String css) {
		this.css = css;
	}


}
