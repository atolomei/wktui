package io.wktui.nav.breadcrumb;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;



import wktui.base.BasePanel;

public class BreadCrumbItemPanel extends BasePanel {
				
	
	private static final long serialVersionUID = 1L;

	private BCElement element;
	
	public BreadCrumbItemPanel(BCElement bce) {
		super("bc-menu-item");
		
		this.element=bce;
		
		Link<Void> link = new Link<Void>("link") {
			private static final long serialVersionUID = 1L;
			@Override
			public void onClick() {
				 element.onClick();
			}
		};
		
		link.add((new Label("label", bce.getLabel())).setEscapeModelStrings(false));
		
		 
		if (bce.getHTMLTitleAttribute()!=null)
			link.add(new AttributeModifier("title", bce.getHTMLTitleAttribute()));
		
		if (bce.isNewTab())
			link.add(new AttributeModifier("target", "_blank"));
		
		add(link);
	}
	
	@Override
	public void onDetach() {
		super.onDetach();
		
		if (this.element!=null)
			this.element.detach();
	}
}
