package io.wktui.form.field;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.value.IValueMap;

import wktui.base.Logger;


public class UploadFileField<T> extends Field<T> {
            
	private static final long serialVersionUID = 1L;

	static private Logger logger = Logger.getLogger(UploadFileField.class.getName());
	
	private String value;
	private org.apache.wicket.markup.html.form.TextField<String> input;
	
	

	@Override
	public void reload() {
		logger.error("reload not done");
	}
	
	
	public UploadFileField(String id, IModel<T> model) {
	    this(id, model, null);
	}
	
	public UploadFileField(String id, IModel<T> model, IModel<String> label) {
		super(id, model, label);
		setOutputMarkupId(true);
	}

	public void setValue(String value) {
        this.value=value;
	}
	
	public String getValue() {
	    return value;
	}
	
	@Override
	public void editOn() {
    	setUpdated(false);

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
	  	        
        	setUpdated(false);
        	
	        if (getModel().getObject()!=null)
	            setValue(getModel().getObject().toString());
	        else
	            setValue("");
	        org.apache.wicket.markup.html.form.TextField<String> input = newTextField();
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
    protected org.apache.wicket.markup.html.form.TextField<String> newTextField() {
	        
        input = new org.apache.wicket.markup.html.form.TextField<String>("input", new PropertyModel<String>(this, "value")) {
	        
	            private static final long serialVersionUID = 1L;

                @Override
	            public void validate() {
                    UploadFileField.this.validate();
	                super.validate();
	            }
	            @Override
	            public boolean isEnabled() {
	                return UploadFileField.this.isEditMode();
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
	                
	                String overridedName = UploadFileField.this.getInputName();
	                
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
	            logger.debug(e.getClass().getName() + " | " +  Thread.currentThread().getStackTrace()[1].getMethodName()+ " |  id. " + UploadFileField.this.getId());
	         }
	        
	         
	         
	         
	        return input;
	    }

    protected void onUpdate(T oldvalue, T newvalue) {
		if (getEditor()!=null) {
			getEditor().setUpdatedPart(getPart());
		}
	}
    
    protected String getPart() {
    	return getId();
		// return ((Label)get("label-container:label")).getDefaultModelObjectAsString().toLowerCase();
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
    
    	//return ((FormComponent<?>)getInput()).getInput();
        //return input.getInput();
    	//logger.debug( getId()+ " -> " + input.getValue() +  " - " +   getValue());
        
    	
    	//logger.debug( input.getInnermostModel().getObject());
		
    	//logger.debug( input.getDefaultModelObjectAsString() );
    	//logger.debug( input.getInput() );;
    	//logger.debug( input.getModel().getObject() );
    	
    	return input.getValue();
    }
    
    
    @Override
    public void updateModel() {
    
        Object val = null;
        
        logger.debug( "update model -> " + getId() );
        
        try {

            if (getModel()==null) {
            	logger.warn("model is null for id -> " + getId());
            	return;
            }
            
            //val = getInputValue();
            val = getValue();
            
            if (val!=null) {
                
                    if  ( (getModel().getObject()!=null && !getModel().getObject().equals(val)) || 
                          (getModel().getObject()==null && val!=null && !"".equals(val))) {
                    	setUpdated(true);

                        onUpdate(getModel().getObject(), (T) val);
                        getModel().setObject( (T) val);
                        logger.debug( "update -> " + getId() + ": " + val.toString());
                }
            }
            else {
                if (getModel().getObject()!=null) {
                    getModel().setObject(null);
                	setUpdated(true);

                    onUpdate(getModel().getObject(), null);
                }
            }
        } 
        catch (Exception e) {
        	setUpdated(false);
            logger.error(e,  getInput()!=null? getInput().toString(): "");
            getModel().detach();
        }
    }

    
    
    protected String getInputType() {
        return "text";
    }

	//@Override
	//public IModel<T> makeValueModel(T value) {
	//		return new Model<T>(value.toString());
	//}

} 