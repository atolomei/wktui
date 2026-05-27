package io.wktui.form.field;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import wktui.base.LabelPanel;
import wktui.base.Logger;

public class StaticTextField<T extends Serializable> extends Field<T> {

	private static final long serialVersionUID = 1L;

	static private Logger logger = Logger.getLogger(StaticTextField.class.getName());

	private LabelPanel textPanel;

	/** Display mode used when the model object is an {@link OffsetDateTime}. */
	private OffsetDateTimeField.LabelDisplayMode labelDisplayMode = OffsetDateTimeField.LabelDisplayMode.TIMESTAMP;
	private ZoneId zoneId;
	private Locale locale;

	public StaticTextField(String id, IModel<T> model) {
		this(id, model, null);
	}

	public StaticTextField(String id, IModel<T> model, IModel<String> label) {
		super(id, model, label);
		setOutputMarkupId(true);
	}

	// --- OffsetDateTime display configuration ---

	public void setLabelDisplayMode(OffsetDateTimeField.LabelDisplayMode mode) {
		this.labelDisplayMode = mode;
	}

	public OffsetDateTimeField.LabelDisplayMode getLabelDisplayMode() {
		return labelDisplayMode;
	}

	public void setZoneId(ZoneId zoneId) {
		this.zoneId = zoneId;
	}

	public ZoneId getZoneId() {
		return zoneId;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public Locale getFieldLocale() {
		return locale;
	}

	/**
	 * Formats an {@link OffsetDateTime} value according to the configured
	 * {@link OffsetDateTimeField.LabelDisplayMode}, locale and time zone.
	 */
	protected String formatOffsetDateTime(OffsetDateTime value) {
		if (value == null)
			return "";
		ZoneId zone = zoneId != null ? zoneId : ZoneId.systemDefault();
		Locale loc = locale != null ? locale : Locale.getDefault();
		ZonedDateTime zdt = value.atZoneSameInstant(zone);
		switch (labelDisplayMode) {
		case DATE:
			return zdt.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).withLocale(loc));
		case DATETIME:
			return zdt.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withLocale(loc));
		case TIMESTAMP:
		default:
			return value.toString();
		}
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

	public void setValue(T value) {
		if (value == null)
			return;
		getModel().setObject(value);
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

	private void load() {

		textPanel = new LabelPanel("input", new Model<String>() {
			public String getObject() {
				T obj = StaticTextField.this.getModel().getObject();
				if (obj == null)
					return "";
				if (obj instanceof OffsetDateTime) {
					return StaticTextField.this.formatOffsetDateTime((OffsetDateTime) obj);
				}
				return obj.toString();
			}
		});

		textPanel.add(new AttributeModifier("class", getCss()));
		super.addControl(textPanel);
	}

}
