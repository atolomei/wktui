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
import org.apache.wicket.model.util.ListModel;

import wktui.base.InvisiblePanel;



public class BooleanField extends ChoiceField<Boolean> {

    private static final long serialVersionUID = 1L;
 

	static private final List<Boolean> b_list = new ArrayList<Boolean>();
	static {
		 b_list.add(Boolean.TRUE );
		 b_list.add(Boolean.FALSE);
	}

	@Override
	public void onInitialize() {
	    super.onInitialize();
	}

	@Override
	public IModel<List<Boolean>> getChoices() {
		return new ListModel<Boolean> (b_list);
	}
	
	@Override
	protected String getDisplayValue(Boolean value) {
		if (value==null)
			return null;
		if (value.booleanValue())
			return getTrueStr();
		return getFalseStr();
	}


	/***
	 * @param id
	 */
	public BooleanField(String id) {
		this(id, null, null);
	}
	
	public BooleanField(String id, IModel<Boolean> model) {
		this(id, model, null);
	}
 
	
	public BooleanField(String id, IModel<Boolean> model, IModel<String> title ) {
		super(id, model, title);
		setOutputMarkupId(true);
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
			//Boolean value = input.equals(TRUE) ? true : false;
			//getModel().setObject(Boolean.valueOf(value));
	 
		}
	}

	
	protected String getFalseStr() {
		return new StringResourceModel("false", this, null).getString();
	}

	protected String getTrueStr() {
		return new StringResourceModel("true", this, null).getString();
	}


	
 
	
}
