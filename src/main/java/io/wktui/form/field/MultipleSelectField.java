package io.wktui.form.field;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.value.IValueMap;


import wktui.base.Logger;

public class MultipleSelectField<T> extends Field<T> {

	
	private static final long serialVersionUID = 1L;

	static private Logger logger = Logger.getLogger(MultipleSelectField.class.getName());
	
	private IModel<List<T>> choices;
	private List<String> values;

	//private String value;
	private DropDownChoice<T> selector;

	public MultipleSelectField(String id, IModel<T> model) {
	    this(id, model, null);
	}
	
	public MultipleSelectField(String id, IModel<T> model, IModel<String> label) {
		super(id, model, label);
		setOutputMarkupId(true);
	}

	@Override
	public void editOn() {
		if (this.selector!=null)
			this.selector.setEnabled(true);
		super.editOn();
	}

	@Override
	public void editOff() {
		if (this.selector!=null)
			this.selector.setEnabled(false);
		super.editOff();
	}
	
	public void setValues(List<String> values) {
        this.values=values;
	}
	
	public List<String> getValues() {
	    return values;
	}
 
	
	@Override
	public void updateModel() {
	

      
        List<String> vals = null;
        
        try {

            if (getModel()==null) {
            	logger.warn("model is null for id -> " + getId());
            	return;
            }
 
            vals = getValues();
            
            if (vals!=null) {
                
                    /**
                     * if  ( (getModel().getObject()!=null && !getModel().getObject().equals(val)) || 
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
	        */
	       
            } 
	       
        }
        catch (Exception e) {
            logger.error(e,  getInput()!=null? getInput().toString(): "");
            getModel().detach();
        }
		
		
		
	}

	protected void onUpdate(T oldvalue, T newvalue) {
				if (getEditor()!=null) {
					getEditor().setUpdatedPart(getPart());
				}		
	}
	 
	protected String getPart() {
	   	return getFieldUpdatedPartName();
	}
	 
	  protected String getInputType() {
	        return null;
	  }
	
	@Override
	public void onInitialize() {
		super.onInitialize();
	
		if (getChoices()==null)
			throw new IllegalArgumentException ("choices is null");
		

        this.selector = newSelectField();
        super.addControl(selector);
	}

	
	
	
	private DropDownChoice<T> newSelectField() {
        
		 this.selector = new DropDownChoice<T>("input", getChoices()) {
			
			private static final long serialVersionUID = 1L;
			
			@Override
			public boolean isEnabled() {
				return true;
			}
			@Override
			public void validate() {
				super.validate();
				MultipleSelectField.this.validate();
			}
			@Override
			protected String getNullValidDisplayValue() {
				return MultipleSelectField.this.getNullValidDisplayValue();
			}
			@Override
			public boolean isNullValid() {
				return MultipleSelectField.this.isNullValid();
			}
			@Override
			public boolean isRequired()	{
				return MultipleSelectField.this.isRequired();
			}
			@Override
			protected void onComponentTag(final ComponentTag tag) {
				IValueMap attributes = tag.getAttributes();
				if (autofocus())
					attributes.putIfAbsent("autofocus", "");
				super.onComponentTag(tag);
			}
		};
		
		selector.setModel(new PropertyModel<T>(this, "value"));

		selector.setChoiceRenderer(new ChoiceRenderer<T>() {
			private static final long serialVersionUID = 1L;
			public String getIdValue(T value, int index) {
				return MultipleSelectField.this.getIdValue(value); 
			};
			public String getDisplayValue(T value) {
				return MultipleSelectField.this.getDisplayValue(value);
			};
		});

	     //if (getModel().getObject()!=null)
	     //       setValue( MultipleSelectField.this.getDisplayValue(getModel().getObject()));

	     return selector;
	     
	}

	protected String getIdValue(T value) {
		
		if (value==null)
			return null;
		
		/**
		if (value==null)
			return null;

		String id = null;

		if (value instanceof Identifiable && ((Identifiable)value).getId()!=null)
			id = ((Identifiable)value).getId().toString();
		else {
			id = DisplayNameExtractor.get(value).toLowerCase();
		}	
		return id;
		**/
		return value.toString();
	}
	
	
	public IModel<List<T>> getChoices() {
		return choices;
	}
	
	public void setChoices(IModel<List<T>> choices) {
		this.choices=choices;
	}

	
	public String getNullValidDisplayValue() {
		return "None";
	}
	
	public void onUpdate(AjaxRequestTarget target) {
	}
	
	@Override
	public void onDetach() {
		super.onDetach();
		if (getChoices()!=null)
			this.getChoices().detach();
	}
	
	protected String getDisplayValue(T value) {
		if (value==null)
			return null;
		return value.toString();
	}

	protected boolean equals(T value, Object object) {
		return value!=null && object!=null && value.equals(object);
	}

	
	
	
}
