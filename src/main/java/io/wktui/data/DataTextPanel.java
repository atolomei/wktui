package io.wktui.data;

import java.io.Serializable;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import wktui.base.LabelPanel;
import wktui.base.Logger;

public class DataTextPanel<T extends Serializable> extends DataPanel<T> {

	private static final long serialVersionUID = 1L;

	static private Logger logger = Logger.getLogger( DataTextPanel.class.getName());

	private T value;
	private LabelPanel data;
	private IModel<String> placeHolder;

	public  DataTextPanel(String id, IModel<T> model) {
		this(id, model, null);
	}

	public DataTextPanel(String id, IModel<T> model, IModel<String> label) {
		super(id, model, label);
		setOutputMarkupId(true);
	}

	public void setValue(T value) {
		this.value = value;
	}

	public T getValue() {
		return value;
	}

 

	@Override
	public void onInitialize() {
		super.onInitialize();

		if (getModel().getObject() != null)
			setValue(getModel().getObject());

		Label label = new Label("label",  new PropertyModel<T>(this, "value"));
		
		data = new LabelPanel("data",  label);

		data.setOutputMarkupId(true);

		if (getTabIndex() > 0)
			data.add(new AttributeModifier("tabindex", getTabIndex()));

		if (getPlaceHolderLabel() != null && getPlaceHolderLabel().getObject() != null)
			data.add(new AttributeModifier("placeholder", getPlaceHolderLabel()));


		super.addControl(data);
	}

	@Override
	public void onConfigure() {
		super.onConfigure();
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
	public Component getData() {
		return data;
	}

	 

	@Override
	public void onDetach() {
		super.onDetach();
	}

	@Override
	public void updateModel() {

		Object val = null;
		boolean updated = false;

		try {

			if (getModel() == null) {
				logger.warn("model is null for id -> " + getId());
				return;
			}
			logger.debug("update -> " + getId() + ": " + (val != null ? val.toString() : "null") + " | updated -> " + updated);

		} catch (Exception e) {
			getModel().detach();
		}  
	}
	
	
	protected String getInputType() {
		return "text";
	}

	@Override
	public void reload() {
		setValue(getModel().getObject());
	}

	protected IModel<String> getPlaceHolderLabel() {
		return this.placeHolder;
	}

	public void setPlaceHolderLabel(IModel<String> label) {
		this.placeHolder = label;
	}


}