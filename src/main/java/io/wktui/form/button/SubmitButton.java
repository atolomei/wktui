package io.wktui.form.button;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxCallListener;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.attributes.IAjaxCallListener;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;

import io.wktui.form.Form;
import wktui.base.BasePanel;

public abstract class SubmitButton<T> extends BasePanel {

    private static final long serialVersionUID = 1L;

    private Form<T> form;

    private String style;
    private String css;
    
    private Label label;

    
    private IModel<T> model;
    
    private AjaxButton button;
    
    
    public SubmitButton(String id) {
        this(id, null, null);
    }
    
    public SubmitButton(String id, Form<T> form) {
        super(id);
        this.model=null;
        this.form=form;
    }
    
    public SubmitButton(String id, IModel<T> model, Form<T> form) {
        super(id);
        this.model=model;
        this.form=form;
    }
  
    @Override
    public void onInitialize() {
        super.onInitialize();
        
        this.button = new AjaxButton("submitButton", this.form) {
            private static final long serialVersionUID = 1L;
            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                SubmitButton.this.onSubmit(target);     
            }
            
            @Override
            protected void onError(AjaxRequestTarget target) {
            
                
            }
            
            protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
                super.updateAjaxAttributes(attributes);
                IAjaxCallListener listener = new IAjaxCallListener() {
                    @Override
                    public CharSequence getSuccessHandler(Component component) {
                        return null;
                    }
                    @Override
                    public CharSequence getPrecondition(Component component) {
                        return null;
                    }
                    @Override
                    public CharSequence getFailureHandler(Component component) {
                        return null;

                    }
                    @Override
                    public CharSequence getCompleteHandler(Component component) {
                        return null;
                    }
                    @Override
                    public CharSequence getBeforeSendHandler(Component component) {
                        return null;
                    }
                    @Override
                    public CharSequence getBeforeHandler(Component component) {
                        //String s = "document.getElementById('"+component.getMarkupId()+"').innerHTML = '<span class=\"far fa-sync glyphicon-refresh-animate\"></span> "+getWorkingLabel().getObject()+"'";
                        //return s;
                        return null;
                    }
                    @Override
                    public CharSequence getAfterHandler(Component component) {
                        return null;
                    }
                    @Override
                    public CharSequence getDoneHandler(Component component) {
                        return null;
                    }
                    @Override
                    public CharSequence getInitHandler(Component component) {
                        return null;
                    }
                };
                attributes.getAjaxCallListeners().add(listener);
                AjaxCallListener myAjaxCallListener = new AjaxCallListener() {
                    @Override 
                    public CharSequence getBeforeHandler(Component component) { 
                        //return "if (typeof(tinyMCE) != \"undefined\") tinyMCE.triggerSave(true,true)";
                        return null;
                    }
                };
                attributes.getAjaxCallListeners().add(myAjaxCallListener);
            }
        };
        add(button);
     
        label = new Label("submit", getLabel());
        button.add(label)
        ;
    }
    

    public IModel<String> getLabel() {
    	return new StringResourceModel("button.submit");
    			
    }
    
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

    
    protected String getEditClass() {
        return "btn btn-primary btn-sm";
    }
    
    protected String getSaveClass() {
        return "btn btn-primary btn-sm";
    }
    
    protected String getCancelClass() {
        return "btn btn-default btn-sm";
    }
    
    
    @Override
    public boolean isEnabled() {
        return true;
        //return isEditionEnabled();
    }


    protected abstract void onSubmit(AjaxRequestTarget target);
        
    protected void onError(AjaxRequestTarget target) {
        /**
        getForm().visitChildren(Field.class, new IVisitor<Field<?>, Void>() {
            @Override
            public void component(Field<?> field, IVisit<Void> visit) {
                if (field.hasErrorMessage()) {
                    target.focusComponent(field.getInput());
                }
            } 
        });
        **/
    }
      
    
    //protected IModel<String> getWorkingLabel() {
    //    return new StringResourceModel("button.submiting", ContentEditor.this, null);
    //}
    
}
