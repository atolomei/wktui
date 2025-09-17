package io.wktui.form.field;

import java.io.Serializable;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.value.IValueMap;

import wktui.base.Logger;


public class AutoCompleteField<T>  extends Field<T> {
            
	private static final long serialVersionUID = 1L;

	static private Logger logger = Logger.getLogger(AutoCompleteField.class.getName());
	
	private org.apache.wicket.markup.html.form.TextField<T> input;
	
	public AutoCompleteField(String id, IModel<T> model) {
	    this(id, model, null);
	}
	
	public AutoCompleteField(String id, IModel<T> model, IModel<String> label) {
		super(id, model, label);
		setOutputMarkupId(true);
	}

	/**public void setValue(T value) {
        this.value=value;
	}
	
	public T getValue() {
	    return value;
	}
	**/
	
	@Override
	public void editOn() {
		this.input.setEnabled(true);
		super.editOn();
	}

	@Override
	public void editOff() {
		this.input.setEnabled(false);
		super.editOff();
	}
	
	
	@Override
	public void onInitialize() {
	        super.onInitialize();
	  	        
	        //if (getModel().getObject()!=null)
	        //    setValue(getModel().getObject());

	        this.input = newTextField();
	        super.addControl(input);
	        
	}
	

	@Override
	public void onConfigure() {
		super.onConfigure();
		// logger.debug("onConfigure() -> " + getId());
	}
	/**
	 * @return
	 */
    protected org.apache.wicket.markup.html.form.TextField<T> newTextField() {
	        
    	org.apache.wicket.markup.html.form.TextField<T> input = new org.apache.wicket.markup.html.form.TextField<T>("input", new PropertyModel<T>(this, "value")) {
	        
	            private static final long serialVersionUID = 1L;

                @Override
	            public void validate() {
                    AutoCompleteField.this.validate();
	                super.validate();
	            }
	            @Override
	            public boolean isEnabled() {
	                return AutoCompleteField.this.isEditMode();
	            }
	            
	            protected void onComponentTag(final ComponentTag tag) {
                    
	                IValueMap attributes = tag.getAttributes();
	                
	                if (getInputType()!=null)           
	                    attributes.put("type",  getInputType());
	                else
	                    attributes.put("type",  "text");
	                    
	                if (getAutoComplete()!=null)
	                    attributes.put("autocomplete", getAutoComplete());
	                
	                if (autofocus())
	                    attributes.putIfAbsent("autofocus", "");

	                super.onComponentTag(tag);
	            }

	            @Override
	            public String getInputName() {
	                
	                String overridedName = AutoCompleteField.this.getInputName();
	                
	                if(overridedName != null)
	                    return overridedName;

	                return super.getInputName();
	            }
	        };

	        input.setOutputMarkupId(true);
	        
	        if (getTabIndex()>0)
	            input.add(new AttributeModifier("tabindex", getTabIndex()));
	        
	        
	         try {
	            if (getPlaceHolderLabel()!=null && getPlaceHolderLabel().getObject()!=null) 
	                input.add(new AttributeModifier("placeholder", getPlaceHolderLabel()));
	         }
	         catch (java.util.MissingResourceException e) {
	            logger.debug(e.getClass().getName() + " | " +  Thread.currentThread().getStackTrace()[1].getMethodName()+ " |  id. " + AutoCompleteField.this.getId());
	         }
	        
	         
	         
	         
	        return input;
	    }

    protected void onUpdate(T oldvalue, T newvalue) {
		if (getEditor()!=null) {
			getEditor().setUpdatedPart(getPart());
		}
	}
    
    protected String getPart() {
    	return getFieldUpdatedPartName();
	}
    
    protected IModel<String> getPlaceHolderLabel() {
        return null;
    }

    protected Object getAutoComplete() {
        return null;
    }

    protected String getInputName() {
        return null;
    }

    public void validate() {
        logger.debug("validate " + getId());
    }

    @Override
    public Component getInput() {
        return input;
    }
    
    
    public Object getInputValue() {
    
     
    	
    	return input.getValue();
    }
    
    @Override
    public void onDetach() {
    	super.onDetach();
    }
    
    
    @Override
    public void updateModel() {
    
        Object val = null;
        
        try {

            if (getModel()==null) {
            	logger.warn("model is null for id -> " + getId());
            	return;
            }
 
           // val = getValue();
            
            if (val!=null) {
                
                    if  ( (getModel().getObject()!=null && !getModel().getObject().equals(val)) || 
                          (getModel().getObject()==null && val!=null && !"".equals(val))) {
                    
                        onUpdate(getModel().getObject(), (T) val);
                        logger.debug( "update -> " + getId() + ": " + val.toString());
                        getModel().setObject( (T) val);
                        
                    }
            }
            else {
                if (getModel().getObject()!=null) {
                    getModel().setObject(null);
                    onUpdate(getModel().getObject(), null);
                }
            }
        } 
        catch (Exception e) {
            logger.error(e,  getInput()!=null? getInput().toString(): "");
            getModel().detach();
        }
    }

       
    protected String getInputType() {
        return "text";
    }

	 

} 