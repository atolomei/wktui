package io.wktui.nav.menu;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;


/**
 * 
 * @param <T>
 */
public abstract class LinkMenuItem<T> extends MenuItemPanel<T> {

	private static final long serialVersionUID = 1L;

	private Link<T> link;
	
	public LinkMenuItem(String id) {
		super(id, null, null);
	}
	
	public LinkMenuItem(String id, IModel<T> model) {
		super(id, model, null);
	}
	
	
	public LinkMenuItem(String id, IModel<T> model, int index) {
		super(id, model, null);
		setIndex(index);
	}
	
	
	
	public void onClick()  {
	}
	
	public boolean isEnabled() {
		return true;
	}
	
	public String getTarget() {
		return null;
	}
	
	public String getBeforeClick() {
		return null;
	}

	public String getCssClass() {
		return null;
	}
	

	@Override
	public void onInitialize() {
		super.onInitialize();
		
		WebMarkupContainer lcontainer = new WebMarkupContainer ("lcontainer");
		add(lcontainer);
		
		AbstractLink link = getNewLink("link");
		lcontainer.add(link);
		
		WebMarkupContainer icon = new WebMarkupContainer ("icon") {
			private static final long serialVersionUID = 1L;
			public boolean isVisible() {
				return  getIconCssClass()!=null;
			}
		};
		
		Label label = new Label("label", getLabel());
		link.add(icon);
		link.add(label);
		 
	}
	
	
	
	protected AbstractLink getNewLink(String id) {
		
			link = new Link<T>(id, getModel()) {

			private static final long serialVersionUID = 1L;
			
			public void onClick() {
					LinkMenuItem.this.onClick();
			}
			protected CharSequence getOnClickScript(final CharSequence url) {
				//return getBeforeClick();
				return null;
			}
			@Override
			protected void onComponentTag(ComponentTag tag) {
				super.onComponentTag(tag);
				if (getBeforeClick()!=null)
				tag.put("onclick", getBeforeClick());
				if (getTarget()!=null)
					tag.put("target", getTarget());
			}
		};
		return link;
	}
	
	
	
	
}
