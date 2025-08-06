package io.wktui.form.button;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;

import io.wktui.form.Form;
import io.wktui.form.FormState;
import io.wktui.form.field.TextField;
import wktui.base.BasePanel;
import wktui.base.Logger;

public class EditButtons<T> extends BasePanel {

	static private Logger logger = Logger.getLogger(EditButtons.class.getName());
	
    private static final long serialVersionUID = 1L;

    private Form<T> form;
    
    AjaxLink<T> edit;
    AjaxLink<T> save;
    AjaxLink<T> cancel;
    
    
    
    private String style;
    private String css;
    
    private IModel<T> model;
    
    private WebMarkupContainer containerCol;
    private WebMarkupContainer containerBorder;
    private WebMarkupContainer containerEdit;
    private WebMarkupContainer containerSave;
    
    
    public EditButtons(String id) {
    	this(id, null, null);
    }
        
 	
    public EditButtons(String id, Form<T> form) {
    	this(id, form, null);
    }
    
    
    public EditButtons(String id, IModel<T> model) {
    	this(id, null, null);
    }
    
  
    public EditButtons(String id, Form<T> form, IModel<T> model) {
        super(id);
        this.setOutputMarkupId(true);
        this.model=model;
        this.form=form;
    }
  
    
    public void onDetach() {
    	super.onDetach();
    	
    	if (this.model!=null)
    		this.model.detach();
    }
    
    		
    public  IModel<T> getModel() {
    	return this.model;
    }
    
    
    public boolean isEditMode() {
    	return getForm()!=null && getForm().getFormState()==FormState.EDIT;
    }
    
    public void onInitialize() {
        super.onInitialize();
        
        containerCol = new WebMarkupContainer("containerCol");
        add(containerCol);
        
        containerBorder = new WebMarkupContainer("containerBorder");
        containerCol.add(containerBorder);

        containerEdit = new WebMarkupContainer("containerEdit") {
			private static final long serialVersionUID = 1L;
			public boolean isVisible() {
        		return getForm().getFormState()==FormState.VIEW;
        	}
        	
        };
        containerBorder.add(containerEdit);

        containerSave = new WebMarkupContainer("containerSave") {
			private static final long serialVersionUID = 1L;
			public boolean isVisible() {
        		return isEditMode();
        	}
        };
        
        containerBorder.add(containerSave);
        
        edit =  new AjaxLink<T>("edit", getModel()) {
			private static final long serialVersionUID = 1L;
	
			public boolean isVisible() {
        		return !isEditMode();
        	}
			@Override
			public void onClick(AjaxRequestTarget target) {
				EditButtons.this.onEdit( target );
			}
        	
        };
        
        containerEdit.add(edit);
        
        save =  new AjaxLink<T>("save", getModel()) {
			private static final long serialVersionUID = 1L;
			@Override
			public void onClick(AjaxRequestTarget target) {
				EditButtons.this.onSave( target );
			}
        	
        };
        
        cancel =  new AjaxLink<T>("cancel", getModel()) {
			private static final long serialVersionUID = 1L;
			@Override
			public void onClick(AjaxRequestTarget target) {
				EditButtons.this.onCancel( target );
			}
        };
        
        containerSave.add(save);
        containerSave.add(cancel);
    }
    
    protected Form<T> getForm() {
		return this.form;
	}

	protected void onCancel(AjaxRequestTarget target) {
		logger.debug("onCancel");
	}

	protected void onSave(AjaxRequestTarget target) {
		logger.debug("onSave");
	}

	protected void onEdit(AjaxRequestTarget target) {
		logger.debug("onEdit");
		getForm().setFormState( FormState.EDIT);
		target.add(getForm());
		target.add(this);
	}
	
	public String getStrStyle() {
        return style;
    }

    public void setStrStyle(String s) {
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
    
}
