package io.wktui.form.field;

import java.util.ArrayList;
import java.util.List;


import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.StringResourceModel;

import wktui.base.InvisiblePanel;



public class BooleanField extends Field<Boolean> {

    private static final long serialVersionUID = 1L;

    private final String TRUE;
    private final String FALSE;

	private Object [] parameters;
	
	private Boolean BT = Boolean.valueOf(true);
	private Boolean BF = Boolean.valueOf(false);
	
	private List<String> choices;

	@Override
	public void onInitialize() {
	    super.onInitialize();
	    
	    add(new InvisiblePanel("titleMarkupContainer"));    
	    add(new InvisiblePanel("subtitleMarkupContainer"));
	    add(new InvisiblePanel("textBeforeMarkupContainer"));
	    
	    add(new InvisiblePanel("textAfterMarkupContainer"));
	    add(new InvisiblePanel("helpMarkupContainer"));
	    add(new InvisiblePanel("feedbackMarkupContainer"));
	    add(new InvisiblePanel("errorMarkupContainer"));
	    
	}
	
	
	/***
	 * @param id
	 */
	public BooleanField(String id) {
		this(id, null, null, null);
	}
	
	public BooleanField(String id, IModel<Boolean> model) {
		this(id, model, null, null);
	}
	
	public BooleanField(String id, IModel<Boolean> model, IModel<String> title, Object[] parameters) {
		super(id, model, title);
		
		setOutputMarkupId(true);
		
		this.parameters=parameters;

		TRUE  = getTrueStr();
		FALSE = getFalseStr();
		
	}
	
	
	IModel<Boolean> valueModel;
	
    //@Override
    /**public Boolean getValue() {
        if (valueModel!=null)
            return valueModel.getObject();
        return null;
    }



    @Override
    public IModel<Boolean> getValueModel() {
        return valueModel;
    }
**/


    //@Override
    //public void setValueModel(Boolean value) {
    //    Boolean fieldValue = Boolean.valueOf(value.booleanValue());
    //    valueModel = new Model<Boolean>(fieldValue);
    //}

    public Object [] getParameters() {
		return this.parameters;
	}

	public void onUpdate(AjaxRequestTarget target) {
	}
	

	@Override
	public void updateModel() {
		
		if (getInput()==null) 
			return;
			
		IModel<?> model = getInput().getDefaultModel();
		Object input = model.getObject();
		if (input!=null) {
			Boolean value = input.equals(TRUE) ? true : false;
			
			//if (getModel().getObject()!=null && !getModel().getObject().equals(value) || getModel().getObject()==null && input!=null) {
			//	onUpdate(getModel().getObject(), value);
//				if (getEditor()!=null) 
//					getEditor().setUpdatedPart(((Label)get("label")).getDefaultModelObjectAsString().toLowerCase() + " (" + getValue().toString()+")");
//				logger.debug(getId() + " -> " + getValue().toString());
				//getModel().setObject(getValue());
			//}
		}
	}

	
	
	public List<String> getChoices() {
		
		if (choices!=null)
			return choices;
		
		choices = new ArrayList<String>();
		choices.add(TRUE);
		choices.add(FALSE);
		
		return choices;
	}
	

	public Component getInput() {
		//if (getDisposition()==null || getDisposition()==Disposition.HORIZONTAL) {
		//	return get("horizontal-layout:control:input");
		//}
		//else {
			return get("control:input");
		//}
	}
	
	
	@Override
	public void onBeforeRender() {
		super.onBeforeRender();
	}
	
	public IModel<String> getLabel(Object[] parameters) {
		try {
			StringResourceModel model = new StringResourceModel("property."+getProperty(), BooleanField.this, null);
			model.setParameters(parameters);
			return model;
		} 
		catch (java.util.MissingResourceException e) {
			return null;
		} 
		catch (Exception e2) {
			return null;
		}
	}
	
	public IModel<String> getLabel() {
		try {
			IModel<String> model = new StringResourceModel("property."+getProperty(), BooleanField.this, null);
			return model;
		} 
		catch (java.util.MissingResourceException e) {
			return null;
		} 
		catch (Exception e2) {
			return null;
		}
	}
	
	//protected void onUpdate(Boolean oldvalue, Boolean newvalue) {
	//}


	protected void onHelp(AjaxRequestTarget target) {
	}
	
	protected String getFalseStr() {
		return new StringResourceModel("false", this, null).getString();
	}

	protected String getTrueStr() {
		return new StringResourceModel("true", this, null).getString();
	}



	
	//protected String getDisplayValue(String value) {
	//	return value;
	//}
	
}
