package io.wktui.nav.menu;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;

public abstract class TitleMenuItem<T> extends MenuItemPanel<T> {

	private static final long serialVersionUID = 1L;
	
	IModel<String> label;
	
	
	public TitleMenuItem(String id) {
		super(id);
	}
	
	public TitleMenuItem(String id, IModel<String> label) {
		super(id);
		this.label = label;
	}

	
	
	public IModel<String> getLabel() {
		return label;
	}
	
	@Override
	public void onInitialize() {
		super.onInitialize();
		
		WebMarkupContainer lcontainer = new WebMarkupContainer ("lcontainer");
		add(lcontainer);
		
	 	WebMarkupContainer icon = new WebMarkupContainer ("icon") {
			private static final long serialVersionUID = 1L;
			public boolean isVisible() {
				return  getIconCssClass()!=null;
			}
		};
		
		Label wlabel = new Label("label", getLabel());
		wlabel.setEscapeModelStrings(false);
		
		lcontainer.add(icon);
		lcontainer.add(wlabel);
	}

}

