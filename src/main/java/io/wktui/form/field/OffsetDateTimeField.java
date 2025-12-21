package io.wktui.form.field;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.util.value.IValueMap;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;


import wktui.base.Logger;

public class OffsetDateTimeField extends TextField<OffsetDateTime> {
            
	private static final long serialVersionUID = 1L;

	protected static final ResourceReference LOC = new CssResourceReference(OffsetDateTimeField.class, "moment-with-locales.js");
	protected static final ResourceReference DTCSS = new CssResourceReference(OffsetDateTimeField.class,"bootstrap-datetimepicker.min.css");
	protected static final ResourceReference DTJS = new CssResourceReference(OffsetDateTimeField.class,"bootstrap-datetimepicker.min.js");
	
	static private Logger logger = Logger.getLogger(OffsetDateTimeField.class.getName());
	
	private ZoneId zid;
	

	public class DateValidator implements IValidator<OffsetDateTime> {
		
		public void validate(final IValidatable<OffsetDateTime> validatable) {

			Object dateobject = validatable.getValue();
			
			if (dateobject==null) 
				return;
			
			OffsetDateTime date = ((OffsetDateTime)dateobject);
			
			ZoneId zid = getZoneId();
			
			if (zid==null)
				zid = ZoneId.systemDefault();
			
			OffsetDateTime dateTime = OffsetDateTime.ofInstant(date.toInstant(), zid);
			OffsetDateTime last_date = OffsetDateTime.of(9999, 12, 31, 23, 59, 59, 0, ZoneOffset.from(dateTime));
			
			if (dateTime.getYear()<70) {
				OffsetDateTime correctedDateTime = OffsetDateTime.of(dateTime.getYear()+2000,
					dateTime.getMonthValue(),
					dateTime.getDayOfMonth(),
					dateTime.getHour(),
					dateTime.getMinute(),
					dateTime.getSecond(),
					0,
					ZoneOffset.from(dateTime));
					setValue(OffsetDateTime.from(correctedDateTime.plusDays(2).toInstant()));
			}
			else if (dateTime.getYear()<100) {
				OffsetDateTime correctedDateTime = OffsetDateTime.of(dateTime.getYear()+1900,
					dateTime.getMonthValue(),
					dateTime.getDayOfMonth(),
					dateTime.getHour(),
					dateTime.getMinute(),
					dateTime.getSecond(),
					0,
					ZoneOffset.from(dateTime));
				setValue(OffsetDateTime.from(correctedDateTime.plusDays(2).toInstant()));
			}
			else if (dateTime.isAfter(last_date)) {				
				validatable.error(new ValidationError(this, "invalid-date-high"));
			}			
		}	
	}
	

	public void setZoneId(ZoneId z) {
		this.zid = z;
	}

	public ZoneId getZoneId() {
		return this.zid;
	}
	
	
	
	public OffsetDateTimeField(String id, IModel<OffsetDateTime> model) {
	    this(id, model, null);
	}
	
	public OffsetDateTimeField(String id, IModel<OffsetDateTime> model, IModel<String> label) {
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
	  	        
	       // if (getModel().getObject()!=null)
	       //     setValue(getModel().getObject());

	       // this.input = newTextField();
	       // super.addControl(input);
	        
	}
	

	 

    protected void onUpdate(OffsetDateTime oldvalue, OffsetDateTime newvalue) {
		if (getEditor()!=null) {
			getEditor().setUpdatedPart(getPart());
		}
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
	public void renderHead(final IHeaderResponse response) {
		super.renderHead(response);
		response.render(JavaScriptHeaderItem.forReference(LOC));
		response.render(CssHeaderItem.forReference(DTCSS));
		response.render(JavaScriptHeaderItem.forReference(DTJS));
	}
    	 
	@Override
	public void onConfigure() {
		super.onConfigure();

		if (getValidator() == null) {
			add(new DateValidator());
		}
	}
    @Override
    public void onDetach() {
    	super.onDetach();
    }
    
    protected String getInputType() {
        return "text";
    }
 
    
    public Object getInputValue() {
		
		String value = ((FormComponent<?>)getInput()).getInput();
		
		
		if (value==null || "".equals(value))
			return null;
		
		try {
				// --
				// Dia Mes Ano -> en el OffsetDateTime del usuario a las 00:00:00
				// --
				SimpleDateFormat dateformat = new SimpleDateFormat(getDatePattern());
				Date date = dateformat.parse(value);
				
				LocalDate ldate = date.toInstant().atZone(ZoneOffset.UTC).toLocalDate();
				
				ZoneId zid = getZoneId();
				
				if (zid==null)
					zid=ZoneId.systemDefault();

				ZonedDateTime zdt = ldate.atStartOfDay(zid);
				
				OffsetDateTime dvalue = zdt.toOffsetDateTime();

				return dvalue;
				
		}
		catch (Exception e) {
			logger.error(e);
			return null;
		}
		
	}
    
    /**
    @Override
	protected org.apache.wicket.markup.html.form.TextField<?> newTextField() {

		DateTextField input = new DateTextField("input", new PropertyModel<Date>(this, "dateValue"),
				getDefaultDatePattern()) {
			@Override
			public void validate() {
				super.validate();
				OffsetDateTimeField.this.validate();
			}

			@Override
			public boolean isEnabled() {
				return  true;
			}
		};
		
		input.add(new KeyboardBehavior() {
			protected void onKey(AjaxRequestTarget target, String jsKeycode) {
				OffsetDateTimeField.this.onKey(target, jsKeycode);
			}
		});

		return input;
	}
    
    **/
    
	protected String getDatePattern() {
		return getDefaultDatePattern();
	}
	
	protected String getDefaultDatePattern() {
		//String pattern = "es".equals(WebSession.get().getLocale().getLanguage()) ? "dd/MM/yyyy" : "MM/dd/yyyy";
		String pattern = "MM/dd/yyyy";
		return pattern;
	}



} 