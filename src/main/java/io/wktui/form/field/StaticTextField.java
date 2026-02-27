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

import wktui.base.LabelPanel;
import wktui.base.Logger;


public class StaticTextField<T extends Serializable> extends Field<T> {
            
	private static final long serialVersionUID = 1L;

	static private Logger logger = Logger.getLogger(StaticTextField.class.getName());
	
	private LabelPanel textPanel;
// private Label text;
	
	public StaticTextField(String id, IModel<T> model) {
	    this(id, model, null);
	}
	public StaticTextField(String id, IModel<T> model, IModel<String> label) {
		super(id, model, label);
		setOutputMarkupId(true);
	}
	
	@Override
	public void editOn() {
		super.editOn();
	}

	@Override
	public void editOff() {
		super.editOff();
	}
	
	
	@Override
	public void onInitialize() {
	        super.onInitialize();
        	setUpdated(false);
	        load();
	}
	
	public void setValue( T value ) {
		if (value==null)
			return;
		getModel().setObject(value);
	}
	 
	
	private void load() {
	
	 
		
		textPanel = new LabelPanel("input", 
				new Model<String>() {
					public String getObject() {
							return 	StaticTextField.this.getModel().getObject() !=null ? 
									StaticTextField.this.getModel().getObject().toString() : "";
					}
		});

		textPanel.add(new AttributeModifier("class", getCss()));
		super.addControl(textPanel);
	}
	
	public String getCss() {
		return "form-control text-center text-md-start text-lg-start text-xl-start text-xxl-start static-text";
	}
	
	@Override
	public void onConfigure() {
		super.onConfigure();
	}

    protected void onUpdate(T oldvalue, T newvalue) {
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
        return textPanel;
    }
    
    @Override
    public void onDetach() {
    	super.onDetach();
    }
    
    @Override
    public void updateModel() {
    }
       
    protected String getInputType() {
        return "text";
    }

	@Override
	public void reload() {
		load();
	}
 	 
} 