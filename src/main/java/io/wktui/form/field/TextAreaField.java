package io.wktui.form.field;

import java.io.Serializable;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.value.IValueMap;

import wktui.base.Logger;

public class TextAreaField<T extends Serializable> extends Field<T> {

	private static final long serialVersionUID = 1L;

	static private Logger logger = Logger.getLogger(TextAreaField.class.getName());

	private String value;
	private TextArea<String> input;
	private int rows = 10;

	public TextAreaField(String id, IModel<T> model) {
		this(id, model, null, -1);
	}

	public TextAreaField(String id, IModel<T> model, IModel<String> label, int rows) {
		super(id, model, label);
		setOutputMarkupId(true);
		if (rows > 0)
			this.rows = rows;
	}

	@Override
	public void reload() {
		if (getModel().getObject() != null)
			setValue(getModel().getObject().toString());
		else
			setValue("");
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public void onInitialize() {
		super.onInitialize();
		
    	setUpdated(false);

    	
		if (getModel().getObject() != null)
			setValue(getModel().getObject().toString());
		else
			setValue("");
		TextArea<String> input = newTextAreaField();
		super.addControl(input);
	}

	/**
	 * @return
	 */
	protected TextArea<String> newTextAreaField() {

		input = new TextArea<String>("input", new PropertyModel<String>(this, "value")) {

			private static final long serialVersionUID = 1L;

			@Override
			public void validate() {
				TextAreaField.this.validate();
				super.validate();
			}

			@Override
			public boolean isEnabled() {
				return TextAreaField.this.isEditMode();
			}

			protected void onComponentTag(final ComponentTag tag) {

				IValueMap attributes = tag.getAttributes();

				if (getInputType() != null)
					attributes.put("type", getInputType());
				else
					attributes.put("type", "text");

				if (getAutoComplete() != null)
					attributes.put("autocomplete", getAutoComplete());

				if (autofocus())
					attributes.putIfAbsent("autofocus", "");

				super.onComponentTag(tag);
			}

			@Override
			public String getInputName() {

				String overridedName = TextAreaField.this.getInputName();

				if (overridedName != null)
					return overridedName;

				return super.getInputName();
			}
		};

		input.setOutputMarkupId(true);

		input.setEscapeModelStrings(false);

		if (getTabIndex() > 0)
			input.add(new AttributeModifier("tabindex", getTabIndex()));

		try {
			if (getPlaceHolderLabel() != null && getPlaceHolderLabel().getObject() != null)
				input.add(new AttributeModifier("placeholder", getPlaceHolderLabel()));
		} catch (java.util.MissingResourceException e) {
			logger.debug(e.getClass().getName() + " | " + Thread.currentThread().getStackTrace()[1].getMethodName() + " |  id. " + TextAreaField.this.getId());
		}

		input.add(new AttributeModifier("rows", String.valueOf(getRows())));

		return input;
	}

	protected int getRows() {
		return rows;
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

	public void onUpdate(AjaxRequestTarget target) {

	}

	public void validate() {
		logger.debug("validate");
	}

	@Override
	public Component getInput() {
		return input;
	}

	public Object getInputValue() {
		// return ((FormComponent<?>)getInput()).getInput();
		// return input.getInput();
		return input.getValue();
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

			val = getInputValue();

			//logger.debug("update -> " + getId() + ": " + (val != null ? val.toString() : "null"));

			if (val != null) {

				if ((getModel().getObject() != null && !getModel().getObject().equals(val)) || (getModel().getObject() == null && val != null && !"".equals(val))) {
					updated = true;
		        	setUpdated(true);
					onUpdate(getModel().getObject(), (T) val);
					getModel().setObject((T) val);
				}
			} else {
				if (getModel().getObject() != null) {
					updated = true;
		        	setUpdated(true);
					getModel().setObject(null);
					onUpdate(getModel().getObject(), null);
				}
			}

			logger.debug("update -> " + getId() + ": " + (val != null ? val.toString() : "null") + " | updated -> " + updated);

		} catch (Exception e) {
        	setUpdated(false);
			logger.error(e, getInput() != null ? getInput().toString() : "");
			getModel().detach();
		}
	}

	protected void onUpdate(T oldvalue, T newvalue) {
		if (getEditor() != null) {
			getEditor().setUpdatedPart(getPart());
		}
	}

	protected String getPart() {
		return getFieldUpdatedPartName();
	}

	protected String getInputType() {
		return "text";
	}

}