package io.wktui.form.field;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;


import wktui.base.Logger;

public class ZoneIdField extends ChoiceField<ZoneId> {

	private static final long serialVersionUID = 1L;

	static private Logger logger = Logger.getLogger(ZoneIdField.class.getName());

	private static Map<String, String> result;

	static private List<ZoneId> list;
	
	static {
		if (list==null) {
			list = new ArrayList<ZoneId>();
			for (String z: ZoneId.getAvailableZoneIds()) {
				if (! (z.startsWith("System") || z.startsWith("Etc")))
					list.add(ZoneId.of(z));
			}
			list.sort(new Comparator<ZoneId>() {
				@Override
				public int compare(ZoneId o1, ZoneId o2) {
					return o1.getId().compareToIgnoreCase(o2.getId());
				}
			});
			result = new HashMap<String, String>();
			List<String> zoneList = new ArrayList<>(ZoneId.getAvailableZoneIds());
		    LocalDateTime dt = LocalDateTime.now();
		    for (String zoneId : zoneList) {
		        	ZoneId zone = ZoneId.of(zoneId);
		            ZonedDateTime zdt = dt.atZone(zone);
		            ZoneOffset zos = zdt.getOffset();
		            //replace Z to +00:00
		            String offset = zos.getId().replaceAll("Z", "+00:00");
		            result.put(zone.toString(), offset);
		    }
		}
    }
	
 public ZoneIdField(String id, IModel<ZoneId> propertyModel, IModel<String> label ) {
		super(id, propertyModel, label);
		IModel<List<ZoneId>> choices = new io.wktui.model.ListModel<ZoneId>(new Model<Panel>(this), "zones");
		super.setChoices(choices);
	}
	
	public Map<String, String> getAllZoneIds() {
		return result;
	}

	public List<ZoneId> getZones() {
		return list;
	}
	
	@Override
	public void updateModel() {
		super.updateModel();
		
	}
	
	@Override
	public void reload() {
		logger.error("reload not done");
	}
	
	protected String getDisplayValue(ZoneId value) {
		//logger.debug(value.getDisplayName(TextStyle.FULL_STANDALONE, getLocale()));
		return value.getId();
	}
	
}
