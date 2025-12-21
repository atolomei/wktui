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

public class ChoiceField<T> extends Field<T> {

	private static final long serialVersionUID = 1L;

	static private Logger logger = Logger.getLogger(ChoiceField.class.getName());

	private IModel<List<T>> choices;
	private int indexSelected = -1;
	private IModel<T> valueModel;
	private DropDownChoice<T> selector;

	@Override
	public void reload() {
		logger.error("reload not done");
	}

	public ChoiceField(String id, IModel<T> model) {
		this(id, model, null);
	}

	public ChoiceField(String id, IModel<T> model, IModel<String> label) {
		super(id, model, label);
		setOutputMarkupId(true);
		this.valueModel = model;
	}

	@Override
	public void editOn() {
		if (this.selector != null)
			this.selector.setEnabled(true);
		super.editOn();
	}

	@Override
	public void editOff() {
		if (this.selector != null)
			this.selector.setEnabled(false);
		super.editOff();
	}

	@Override
	public void updateModel() {

		T val = null;

		boolean updated = false;

		try {

			if (getModel() == null) {
				logger.warn("model is null for id -> " + getId());
				return;
			}

			val = getValue();
			
			if (val==null) {
				if (indexSelected!=-1) {
					logger.debug("update -> " + getId() + ": null");
					onUpdate(getModel().getObject(), val);
					getModel().setObject(val);
					return;
					
				}
				return;
			}
	
					
			logger.debug(val.toString());
			logger.debug((getModel().getObject() != null)? getModel().getObject() : "null");
		
			int newIndexSelected = -1;
			for (int index = 0; index < getChoices().getObject().size(); index++) {
				if (getChoices().getObject().get(index).equals(val)) {
					 newIndexSelected=index;
					break;
				}
			}
			

			if (indexSelected==newIndexSelected) {
					return;
			}
			
			if (newIndexSelected!=indexSelected) {
				onUpdate(getModel().getObject(), val);
				logger.debug("update -> " + getId() + ": " + val.toString());
				getModel().setObject(val);
				return;
			}
			
			
		} catch (Exception e) {
			logger.error(e, getInput() != null ? getInput().toString() : "");
			getModel().detach();
		}
	}
	
	@Override
	public void onInitialize() {
		super.onInitialize();

		if (getChoices() == null)
			throw new IllegalArgumentException("choices is null");

		if (getModel() != null && getModel().getObject() != null) {
			for (int index = 0; index < getChoices().getObject().size(); index++) {
				if (getChoices().getObject().get(index).equals(getModel().getObject())) {
					indexSelected=index;
					break;
				}
			}
		}

		this.selector = newSelectField();
		super.addControl(selector);
	}

	public T getValue() {
		if (valueModel != null)
			return valueModel.getObject();
		return null;
	}

	public void setValue(T value) {
		if (valueModel != null)
			valueModel.setObject(value);
	}

	public void onDetach() {
		super.onDetach();

		if (valueModel != null) {
			valueModel.detach();
		}

		if (getChoices() != null) {
			this.getChoices().detach();
		}
	}

	public IModel<List<T>> getChoices() {
		return choices;
	}

	public void setChoices(IModel<List<T>> choices) {
		this.choices = choices;
	}

	public String getNullValidDisplayValue() {
		return "None";
	}

	public void onUpdate(AjaxRequestTarget target) {
	}

	protected String getDisplayValue(T value) {
		if (value == null)
			return null;
		return value.toString();
	}

	protected boolean equals(T value, Object object) {
		return value != null && object != null && value.equals(object);
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
		return null;
	}
	
	protected String getIdValue(T value) {
		if (value == null)
			return null;
		return value.toString();
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
				ChoiceField.this.validate();
			}

			@Override
			protected String getNullValidDisplayValue() {
				return ChoiceField.this.getNullValidDisplayValue();
			}

			@Override
			public boolean isNullValid() {
				return ChoiceField.this.isNullValid();
			}

			@Override
			public boolean isRequired() {
				return ChoiceField.this.isRequired();
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
				return ChoiceField.this.getIdValue(value);
			};

			public String getDisplayValue(T value) {
				return ChoiceField.this.getDisplayValue(value);
			};
		});
		
		return selector;
	}

}
