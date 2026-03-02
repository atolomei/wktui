package io.wktui.form.field;


import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
 
import org.apache.wicket.util.value.IValueMap;
 

 
public class PasswordField extends TextField<String> {
	
	private static final long serialVersionUID = 1L;

	private org.apache.wicket.markup.html.form.PasswordTextField passwordInput = null;
	
	private boolean showPasswordLink = false;

	
	public PasswordField(String id, IModel<String> model) {
	        super(id, model);
	    	setOutputMarkupId(true);
	}
	    
	
	public PasswordField(String id, IModel<String> model, IModel<String> label) {
		super(id, model, label);
		setOutputMarkupId(true);
	}

	
	public boolean isShowPasswordLink() {
	        return showPasswordLink;
	}
	    

	@Override
	protected org.apache.wicket.markup.html.form.TextField<String> newTextField() {
		
	    passwordInput = new org.apache.wicket.markup.html.form.PasswordTextField("input", new PropertyModel<String>(this,"value")) {
			
			@Override
			public void validate() {
				super.validate();
				PasswordField.this.validate();
			}
			
			@Override
			public boolean isEnabled() {
				return true;
			}
			
			protected void onComponentTag(final ComponentTag tag) {
				IValueMap attributes = tag.getAttributes();
				// ensure it renders as password type
				attributes.put("type", "password");
				super.onComponentTag(tag);
			}

			@Override
			public String getInputName() {
				String overridedName = PasswordField.this.getInputName();
				if(overridedName != null)
					return overridedName;

				return super.getInputName();
			}
			
			@Override
			protected String[] getInputTypes()
			{
				return new String[] {"password", "text"};
			}
		};
		
		// make the input produce an id so JS/Ajax can reference it
		passwordInput.setOutputMarkupId(true);

		if (getPlaceHolderLabel() != null && getPlaceHolderLabel().getObject() != null) {
			passwordInput.add(new AttributeModifier("placeholder", PasswordField.this.getPlaceHolderLabel()));
		}
		
		return passwordInput;
	}
}