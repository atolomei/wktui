package io.wktui.form.button;

import org.apache.wicket.Component;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.attributes.IAjaxCallListener;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;

import io.wktui.form.Form;
import io.wktui.form.FormState;
import io.wktui.form.field.TextField;
import wktui.base.BasePanel;
import wktui.base.Logger;

public class EditButtons<T> extends BasePanel {

	static private Logger logger = Logger.getLogger(EditButtons.class.getName());
	
    public static final String SPINNING ="fas fa-sync fa-spin fa-fw spinning";

    private static final long serialVersionUID = 1L;

    private Form<T> form;
    
    private AjaxLink<T> edit;
    private AjaxButton save;
    private AjaxButton cancel;
    
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
        
        save =  new AjaxButton("save") {
			private static final long serialVersionUID = 1L;
			@Override
			protected void onSubmit(AjaxRequestTarget target) {
				long start=System.currentTimeMillis();
				EditButtons.this.onSave(target);	
				long end=System.currentTimeMillis();
				if ( (end-start) < 600) {
					try {
	                    Thread.sleep(600 - (end-start));
	                } catch (InterruptedException e) {
	                    e.printStackTrace();
	                }
				}
			}
			
			
			@Override
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
						String s = null, s1=null;
						String id = component.getMarkupId();
							s1 = "document.getElementById('"+id+"').innerHTML = '"+(getLabel()!=null?getLabel().getObject():"")+"';";
							s ="setTimeout(function () {"+s1+"}, 100);";
						return s;
					}
					@Override
					public CharSequence getBeforeSendHandler(Component component) {
						return null;
					}
					@Override
					public CharSequence getBeforeHandler(Component component) {
						String s = EditButtons.this.getBeforeHandler();
						s += "document.getElementById('"+component.getMarkupId()+"').innerHTML = '<span class=\""+SPINNING+"\"></span>'";
						return s;																		
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
			}
        };
        
        cancel =  new AjaxButton("cancel") {
			private static final long serialVersionUID = 1L;
			@Override
			public void onSubmit(AjaxRequestTarget target) {
				EditButtons.this.onCancel(target);
			}
        };
        
        
        save.add( new AttributeModifier("class", getSaveClass()));
        cancel.add( new AttributeModifier("class", getCancelClass()));
        
        containerSave.add(save);
        containerSave.add(cancel);
    }
    
    
    
	protected IModel<String> getWorkingLabel() {
		return new StringResourceModel("saving", this, null);
	}
	
	public String getBeforeHandler() {
		return "";
	}

    
    protected Form<T> getForm() {
		return this.form;
	}

	protected void onCancel(AjaxRequestTarget target) {
	}

	protected void onSave(AjaxRequestTarget target) {
	}

	protected void onEdit(AjaxRequestTarget target) {
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
        return "btn  btn-outline-primary btn-sm";
    }    
    
}
