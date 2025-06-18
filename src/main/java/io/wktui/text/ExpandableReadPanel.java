package io.wktui.text;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import io.wktui.model.TextCleaner;
import wktui.base.BasePanel;

public class ExpandableReadPanel extends BasePanel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    boolean isExpanded = false;
    
    
    private final IModel<String> text;
    Label label;
    WebMarkupContainer expandContainer;
    WebMarkupContainer textContainer;
    
    Label expandedLabel;
    AjaxLink<String> expandLink;
    
    public ExpandableReadPanel(String id, IModel<String> text) {
        super(id);
        this.text=text;
        setOutputMarkupId(true);
    }
    
    
    @Override
    public void onInitialize() {
        super.onInitialize();
        
        
        expandContainer = new WebMarkupContainer("expand-container") {
            private static final long serialVersionUID = 1L;
            public boolean isVisible() {
                return true;
            }
        };
        add(expandContainer);
        expandContainer.setOutputMarkupId(true);
        
        
        textContainer = new WebMarkupContainer("text-container") {
            private static final long serialVersionUID = 1L;
            public boolean isVisible() {
                return true;
            }
        };
        add(textContainer);
        textContainer.setOutputMarkupId(true);
        
        label = new Label ("text", Shorten( getText().getObject() ));
        label.setEscapeModelStrings(false);
        
        label.add( new AttributeModifier("class", isExpanded() ? "text-to-read" :  "text"));
        
        textContainer.addOrReplace(label);

        
        expandedLabel = new Label("expandLabel", getLinkLabel());

        expandLink = new AjaxLink<String> ("expand", getModel()) {

            private static final long serialVersionUID = 1L;
            
            @Override
            public void onClick(AjaxRequestTarget target) {
                
                setExpanded(!isExpanded());

                label = new Label ("text", isExpanded() ? new Model<String>( TextCleaner.clean(getText().getObject())): Shorten( getText().getObject() ));
                label.add( new AttributeModifier("class", isExpanded() ? "text text-to-read" :  "text"));
                label.setEscapeModelStrings(false);
                textContainer.addOrReplace(label);
                
                expandedLabel = new Label("expandLabel", getLinkLabel());
                expandLink.addOrReplace(expandedLabel);
                
                target.add(expandContainer);
                target.add(textContainer);
            }
        };

        
        expandLink.setOutputMarkupId(true);
        
        expandLink.addOrReplace(expandedLabel);
        expandContainer.addOrReplace(expandLink);
        
    }
    
    
    private IModel<String> getLinkLabel() {
        return isExpanded() ? getStringResourceModel("collapse") : getStringResourceModel("expand");
    }
    
    

    private IModel<String> getModel() {
        return text;
    }


    protected boolean isExpanded() {
        return this.isExpanded;
    }


    protected void setExpanded(boolean b) {
        this.isExpanded=b;
    }

    
    private IModel<String> Shorten(String text) {
        return new Model<String>( TextCleaner.clean(text, 200) );
    }


    public IModel<String> getText() {
        return text;
    }
    

}
