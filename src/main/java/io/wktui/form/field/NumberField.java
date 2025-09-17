package io.wktui.form.field;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.value.IValueMap;
import org.apache.wicket.validation.ValidationError;

import wktui.base.Logger;


public class NumberField<T extends Number & Comparable<T>> extends TextField<T> {
            
	private static final long serialVersionUID = 1L;

	static private Logger logger = Logger.getLogger(NumberField.class.getName());
	 
	public NumberField(String id, IModel<T> model) {
	    this(id, model, null);
	}
	
	public NumberField(String id, IModel<T> model, IModel<String> label) {
		super(id, model, label);
		setOutputMarkupId(true);
	}
	
	@Override
	public void validate() {
		super.validate();
		if (getInput().hasErrorMessage()) {
			error(new ValidationError("This field must be a number"));
			//setFeedback();
			return;
		}
	}
	
	
	protected boolean isNumber(Object input) {
		if (input == null || "".equals(input))
			return false;
		return isDigits(input.toString());
	}
	
	private boolean isDigits(String argument) {
		for (int c = 0; c < argument.length(); c++) {
			if (!Character.isDigit(argument.charAt(c))) {
				return false;
			}
		}
		return true;
	}
	
	protected T getNumber(Object input) {

		if (getModel().getObject() instanceof Long) {
			if (!isNumber(input))
				return (T)(new Long(0));
			return (T)Long.valueOf(input.toString());
		}

		else if (getModel().getObject() instanceof Integer) {
			if (!isNumber(input))
				return (T)(new Integer(0));
			return (T)Integer.valueOf(input.toString());
		}

		else if (getModel().getObject() instanceof Double) {
			if (!isNumber(input))
				return (T)(new Double(0));
			return (T)Double.valueOf(input.toString());
		}

		else if (getModel().getObject() instanceof Float) {
			if (!isNumber(input))
				return (T)(new Float(0));
			return (T)Float.valueOf(input.toString());
		}

		else {
			logger.error("not Long | Integer | Float | Double ");
		}

		return null;
	}
	
	
	 
	
	@Override
    protected org.apache.wicket.markup.html.form.TextField<T> newTextField() {
	        
		org.apache.wicket.markup.html.form.NumberTextField<T> input = new org.apache.wicket.markup.html.form.NumberTextField<T>("input", new PropertyModel<T>(this, "value")) {
	        
	            private static final long serialVersionUID = 1L;

                @Override
	            public void validate() {
                    NumberField.this.validate();
	                super.validate();
	            }
	            @Override
	            public boolean isEnabled() {
	                return NumberField.this.isEditMode();
	            }
	            
	            @Override
				@SuppressWarnings("unchecked")
				public void convertInput() {
					try {
						String value = getInput();

						if (getModel().getObject() instanceof Long) {
							
							Long number = Long.valueOf(value);
							setConvertedInput((T) number);
							
						} else if (getModel().getObject() instanceof Integer) {
							Integer number = Integer.valueOf(value);
							setConvertedInput((T) number);
							
						} else if (getModel().getObject() instanceof Float) {
							Float number = Float.valueOf(value);
							setConvertedInput((T) number);
							
						} else if (getModel().getObject() instanceof Double) {
							Double number = Double.valueOf(value);
							setConvertedInput((T) number);
							
						} else {
							logger.error("not Long | Integer");
						}
					} catch (NumberFormatException e) {
						error("NumberFormatException" + e.getMessage());
					}
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
	                
	                String overridedName = NumberField.this.getInputName();
	                
	                if(overridedName != null)
	                    return overridedName;

	                return super.getInputName();
	            }
	        };

	    	input.add(new AttributeModifier("type", "number"));
	    	
	        input.setOutputMarkupId(true);
	        
	        if (getTabIndex()>0)
	            input.add(new AttributeModifier("tabindex", getTabIndex()));
	        
	        
	         try {
	            if (getPlaceHolderLabel()!=null && getPlaceHolderLabel().getObject()!=null) 
	                input.add(new AttributeModifier("placeholder", getPlaceHolderLabel()));
	         }
	         catch (java.util.MissingResourceException e) {
	            logger.debug(e.getClass().getName() + " | " +  Thread.currentThread().getStackTrace()[1].getMethodName()+ " |  id. " + NumberField.this.getId());
	         }
	        
	         
	         
	         
	        return input;
	    }
 
    
     
	@Override
    protected String getInputType() {
        return "number";
    }


} 