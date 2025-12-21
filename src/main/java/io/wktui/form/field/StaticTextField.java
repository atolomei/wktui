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
	
	LabelPanel textPanel;
	Label text;
	
	
	@Override
	public void onInitialize() {
	        super.onInitialize();
	        
	        load();
	}
	
	
	private void load() {
	
		
		String s = getModel().getObject()!=null ? getModel().getObject().toString() : "";
		
		
		text = new Label("label", Model.of(s));
        
		textPanel = new LabelPanel("input", text);

		//if (getModel().getObject()==null)
		//	textPanel.setStyle("min-height: 1.5em;");
			
		textPanel.add( new AttributeModifier("class", getCss()));
		
		super.addControl(textPanel);

	}
	
	public String getCss() {
		return "form-control text-center text-md-start text-lg-start text-xl-start text-xxl-start static-text";
	}
	
	
	@Override
	public void onConfigure() {
		super.onConfigure();
		// logger.debug("onConfigure() -> " + getId());
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

	//@Override
	//public IModel<T> makeValueModel(T value) {
	//		return new Model<T>(value.toString());
	//}

} 