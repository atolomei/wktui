package io.wktui.form.field;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.value.IValueMap;

import io.wktui.model.ListModel;
import wktui.base.Logger;

public class LocaleField extends ChoiceField<Locale> {

	private static final long serialVersionUID = 1L;

	static private Logger logger = Logger.getLogger(LocaleField.class.getName());

	private List<Locale> list;

	public LocaleField(String id, IModel<Locale> model, IModel<String> label) {
		super(id, model, label);
		super.setChoices(new ListModel<Locale>(new Model<Panel>(this), "locales"));
	}

	@Override
	public void onInitialize() {
		super.onInitialize();

		Locale locale = getModel().getObject();

		logger.debug(locale);

	}

	public List<Locale> getLocales() {

		if (list != null)
			return list;

		list = new ArrayList<Locale>();

		for (Locale locale : Locale.getAvailableLocales()) {
			list.add(locale);
		}

		list.sort(new Comparator<Locale>() {
			@Override
			public int compare(Locale o1, Locale o2) {
				Locale ba = getLocale();
				if (ba == null)
					ba = Locale.getDefault();
				String a = o1.getDisplayLanguage(ba) + " " + o1.getDisplayCountry(ba);
				String b = o2.getDisplayLanguage(ba) + " " + o2.getDisplayCountry(ba);
				return a.compareToIgnoreCase(b);
			}
		});
		return list;
	}

	protected String getDisplayValue(Locale value) {
		Locale ba = getLocale();
		if (ba == null)
			ba = Locale.getDefault();
		return value.getDisplayLanguage(ba) + " " + value.getDisplayCountry(ba);
	}

	public void onDetach() {
		super.onDetach();
		list = null;
	}

}
