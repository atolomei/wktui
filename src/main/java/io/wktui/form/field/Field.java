package io.wktui.form.field;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.IFormModelUpdateListener;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.ValidationErrorFeedback;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.util.string.interpolator.VariableInterpolator;
import org.apache.wicket.validation.IErrorMessageSource;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidationError;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

import io.wktui.editor.Editor;
import io.wktui.form.Form;
import io.wktui.form.FormState;
import io.wktui.form.util.TitlePanel;
import wktui.base.BasePanel;
import wktui.base.InvisiblePanel;
import wktui.base.LabelPanel;

/**
 * 
 * readOnly -> can not be edited editable -> the field es currently editable
 * 
 * 
 * 
 * @param <T>
 */
public abstract class Field<T> extends BasePanel implements IFormModelUpdateListener {
 	    
    private static final long serialVersionUID = 1L;

    private int tab_index = -1;

    private boolean readOnly = false;   
    private boolean editEnabled = false;   
    private boolean required = false;
    private boolean feedback = false;
    private boolean helpInfo = false;

    private IModel<String> titleModel;

     
    private Panel helpPanel;
    
    private Editor<?> editor;
    private Form<?> form;
    
    
    /** valor original */
    private IModel<T> model;
    
    /** valor ingresado */
    private IModel<T> valueModel;
    
    private String fieldUpdatedPartName;
    
     
    private Model<String> subtitleModel;

    private WebMarkupContainer containerCol;
    private WebMarkupContainer containerBorder;

    private WebMarkupContainer feedbackContainer;

    
    //private Component inputComponent;

    private IValidator<T> validator;
    private String property;
    private List<Behavior> behaviors;
    private boolean autofocus = false;
    private WebMarkupContainer feedbackmk = null;
    
    private  Component input;
    private  String css;
	
    
    private boolean isUpdated = false;
	
	public boolean isUpdated() {
		return this.isUpdated;
	}
	
	public void setUpdated(boolean b) {
		this.isUpdated = b;
	}
	
	
    /**
     * @param id
     * @param model
     */
    public Field(String id, IModel<T> model, IModel<String> label) {
    	this( id, model, label, null);
    }
    	
    public Field(String id, IModel<T> model, IModel<String> label, String name) {
        super(id);
        setProperty(id);
        setModel(model);
        setTitleModel(label);
        if (name==null)
        	this.fieldUpdatedPartName=id;
        else
        	this.fieldUpdatedPartName=name;
    
        containerCol = new WebMarkupContainer("containerCol");
        add(containerCol);

        // class="border pt-4 pb-4 ps-4 pe-4"
        containerBorder = new WebMarkupContainer("containerBorder");
        containerCol.add(containerBorder);
        
    }

    public String getFieldUpdatedPartName() {
    	return this.fieldUpdatedPartName;
    }
	
	public void validate() {
	}
	
    public boolean isRequired() {
        return required;
    }

    public boolean isHelpInfo() {
        return helpInfo;
    }
    
    public boolean isEditEnabled() {
        return editEnabled;
    }
    
    public boolean isEditMode() {
        if (getForm()==null)
            return true;
        return getForm().getFormState()==FormState.EDIT;
    }
        
    
    private String titleCss;
    
    @Override
    public void onInitialize() {
        super.onInitialize();
 
        if (getTitleModel() != null) {
        	TitlePanel<String> t= new TitlePanel<String>("titleMarkupContainer", getTitleModel());
            if (getTitleCss()!=null)
            	t.setCss(getTitleCss());
            containerBorder.add(t);
        } else
            containerBorder.add(new InvisiblePanel("titleMarkupContainer"));

        
        
        if (getSubtitleModel() != null) {
            LabelPanel subTitleLabel = new LabelPanel("subtitleMarkupContainer", getSubtitleModel());
            containerBorder.add(subTitleLabel);
        } else
            containerBorder.add(new InvisiblePanel("subtitleMarkupContainer"));

        containerBorder.add(new InvisiblePanel("textBeforeMarkupContainer"));
        containerBorder.add(new InvisiblePanel("textAfterMarkupContainer"));
        containerBorder.setOutputMarkupId(true);
        
        if( getHelpPanel()==null)
        	containerBorder.add(new InvisiblePanel("help"));
        else
        	containerBorder.add(getHelpPanel());

        
        WebMarkupContainer c=getFeedbackPanel();
        
        if (c==null)
        	containerBorder.addOrReplace(new InvisiblePanel("feedback"));
        else
         	containerBorder.addOrReplace(c);
         	
        containerBorder.add(new InvisiblePanel("errorMarkupContainer"));
    }
  
    public void editOn() {
    	if (!isReadOnly()) {
	    	this.editEnabled=true;
	    	super.setEnabled(true);
    	}
  	}

  
   

    public Panel getHelpPanel() {
    	return helpPanel;
    }
    
	public void setHelpPanel(Panel panel) {
		if (!panel.getId().equals("help"))
    		throw new RuntimeException("id must be -> 'help'");
		
		helpPanel=panel;
    	containerBorder.addOrReplace(helpPanel);
    	
	}
    
	public void setFeedback(WebMarkupContainer fd) {
    	
    	if (!fd.getId().equals("feedback"))
    		throw new RuntimeException("id must be -> 'feedback'");
    	
    	feedbackmk=fd;
    	containerBorder.addOrReplace(feedbackmk);
    }
    
    
    protected WebMarkupContainer getFeedbackPanel() {
    	if (this.feedbackmk==null)
    		this.feedbackmk = new InvisiblePanel("feedback");
    	return this.feedbackmk;
    }
    
    
	public void editOff() {
    	this.editEnabled=false;
		super.setEnabled(false);
	}
    

	public void setCss( String css) {
		this.css=css;
		
		if (this.input!=null) {
			this.input.add( new AttributeModifier("class", css));
		}
	}
	
	public String getCss() {
		return css;
	}

	
    public void addControl(WebMarkupContainer input) {
    	this.input=input;
    	if (getCss()!=null)
    		this.input.add( new AttributeModifier("class", getCss()));
        containerBorder.addOrReplace(input);
    }

    public void setTitleModel(IModel<String> titleModel) {
        this.titleModel = titleModel;
    }

    public IModel<String> getTitleModel() {
        return this.titleModel;
    }

    public void setSubtitleModel(Model<String> titleModel) {
        this.subtitleModel = titleModel;
    }

    public IModel<String> getSubtitleModel() {
        return this.subtitleModel;
    }

   
    
    
   
    public void cancel() {
        clearInput();
         
    }

    public void clearInput() {
      
    	
         if (getInput()!=null && getInput() instanceof FormComponent)
        	 ((FormComponent<?>)getInput()).clearInput();
    }

    public void add(IValidator<T> validator) {
        this.validator = validator;
    }

    public IValidator<T> getValidator() {
        return validator;
    }

    public Component getInput() {
       return input;
   }

    public void setModel(IModel<T> model) {
        this.model = model;
    }

    public IModel<T> getModel() {
        return this.model;
    }

    public int getTabIndex() {
        return this.tab_index;
    }

    public void setTabIndex(int t_index) {
        this.tab_index = t_index;
    }

    public void setReadOnly(boolean readOnlyStatus) {
        this.readOnly =  readOnlyStatus;
    }

    public boolean isReadOnly() {
        return this.readOnly;
    }

    public void setProperty(String name) {
        this.property = name;
    }

    public String getProperty() {
        return this.property;
    }

    public void setRequired(boolean value) {
        this.required = value;
    }

    public String helpIcon() {
        return "far fa-info-circle";
    }

    public boolean isNullValid() {
        return false;
    }
 

    public boolean hasFeedback() {
        return feedback;
    }

    public IModel<T> getValueModel() {
        return valueModel;
    }
 
  
    @SuppressWarnings("unchecked")
    public void setFieldValue(String value) {
        String[] values = { value };
        ((org.apache.wicket.markup.html.form.TextField<T>) getInput()).setModelValue(values);
    }

    
    
    public void setError(ValidationError error) {
        error(error);
        feedback = true;
    }

    public void addInputBehavior(Behavior behavior) {
        if (behaviors == null)
            behaviors = new ArrayList<Behavior>();
        behaviors.add(behavior);
    }
    
    
    @Override
	public void onConfigure() {
		super.onConfigure();
		if (isEditEnabled())
		 editOn();
		else
		 editOff();
	}

    @Override
    public void onBeforeRender() {
        super.onBeforeRender();
 
    }

    @Override
    public void onAfterRender() {
        super.onAfterRender();
        setAutoFocus(false);
    }

    @Override
    public void onDetach() {
        super.onDetach();

        if (valueModel != null)
            valueModel.detach();

        if (this.model != null)
        	this.model.detach();
    }
    

    public void addBehaviors() {
        if (behaviors != null) {
            for (Behavior behavior : behaviors) {
                getInput().add(behavior);
            }
        }
    }

    public void setAutoFocus(boolean value) {
        autofocus = value;
    }

  
    protected boolean autofocus() {
        return autofocus;
    }

    
    protected Editor<?> getEditor() {
		if (this.editor==null) {
			MarkupContainer parent = getParent();
			while (this.editor==null && parent!=null) {
				if (parent instanceof Editor) {
					this.editor = (Editor<?>)parent;
				}
				else
					parent = parent.getParent();
			}
		}
		return this.editor;
	}	
	
    
    

	protected Form<?> getForm() {

		if (this.form!=null)
			return this.form;
				
		MarkupContainer parent = getParent();
		while (parent!=null) {
			if (parent instanceof Form) {
				this.form=(Form<?>)parent;
				return this.form;
			}
			else
				parent = parent.getParent();
		}
		return null;
	}

	
    public abstract void updateModel();
	public abstract void reload();

	public String getTitleCss() {
		return titleCss;
	}

	public void setTitleCss(String titleCss) {
		this.titleCss = titleCss;
	}
	
	protected void internalOnUpdate( T oldValue, T newValue ) {
		if (getEditor() != null) {
			getEditor().setUpdatedPart(getPart());
		}
		this.setUpdated(true);
		this.onUpdate(oldValue, newValue);
	}

	protected  abstract void onUpdate(T oldvalue, T newvalue);
	
	protected String getPart() {
		return getFieldUpdatedPartName();
	}
    
    /**
     * public void validate() { feedback = false; getFeedbackMessages().clear();
     * final Object input = getInputValue(); if (isRequired() && (input==null ||
     * "".equals(input))) { error((new
     * ValidationError()).addKey("requiredvalidator.message")); feedback = true;
     * return; } if (validator!=null) { IValidatable<T> validatable = new
     * IValidatable<T>() { @SuppressWarnings("unchecked") public T getValue() {
     * return (T)input; }; public void error(IValidationError error) {
     * Field.this.error(error); feedback = true; }; public boolean isValid() {
     * return true; } public IModel<T> getModel() { return null; } };
     * validator.validate(validatable); } }
     **/

    /**
     * public void validateModel() { getFeedbackMessages().clear(); final Object
     * input = getModel().getObject(); if (isRequired() && (input==null ||
     * "".equals(input))) { error((new
     * ValidationError()).addKey("requiredvalidator.message")); feedback = true;
     * return; } if (validator!=null) { IValidatable<T> validatable = new
     * IValidatable<T>() { @SuppressWarnings("unchecked") public T getValue() {
     * return (T)input; }; public void error(IValidationError error) {
     * Field.this.error(error); feedback = true; }; public boolean isValid() {
     * return true; } public IModel<T> getModel() { return null; } };
     * validator.validate(validatable); } }
     * 
     * protected Object getInputValue() { return
     * ((FormComponent<?>)getInput()).getInput(); }
     * 
     * protected void setFeedback() { this.feedback = true; }
     * 
     * protected void setFeedback(boolean value) { this.feedback = value; }
     **/

    /**
     * 
     * @Override protected void onComponentTag(ComponentTag tag) {
     *           super.onComponentTag(tag); if (Field.this.hasFeedback()) { if
     *           (Field.this.hasErrorMessage()) { tag.put("class",
     *           (tag.getAttribute("class")!=null ? tag.getAttribute("class"): "" )
     *           +" has-error has-feedback"); } else { tag.put("class",
     *           (tag.getAttribute("class")!=null ? tag.getAttribute("class"): "" )
     *           + " has-success has-feedback"); } } else { tag.put("class",
     *           tag.getAttribute("class")); } }
     **/

   

    /**
     * private IModel<String> getLabel(){ try { return new
     * StringResourceModel("property."+getProperty(), this, null); } catch
     * (java.util.MissingResourceException e) { return new
     * Model<String>(getProperty()); } catch (Exception e2) { return new
     * Model<String>(getProperty()); } }
     **/
}
