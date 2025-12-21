package io.wktui.nav.menu;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;

public abstract class TitleMenuItem<T> extends MenuItemPanel<T> {

	private static final long serialVersionUID = 1L;

	public TitleMenuItem(String id) {
		super(id);
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
		
		Label label = new Label("label", getLabel());
		lcontainer.add(icon);
		lcontainer.add(label);
	}

}

