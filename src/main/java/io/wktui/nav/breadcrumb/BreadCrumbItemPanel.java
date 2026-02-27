package io.wktui.nav.breadcrumb;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import wktui.base.BasePanel;

public class BreadCrumbItemPanel extends BreadcrumbBasePanel {
	
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
		
		link.add((new Label("label", new Model<String>( pad(bce.getLabel().getObject())) )).setEscapeModelStrings(true));
		
		 
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


    @Override
    public IModel<String> getLabel() {
        return new Model<String>(this.element.getLabel().getObject());
    }

    @Override
	protected IModel<String> getLabel(int limit) {
		return Model.of( pad( getLabel().getObject(), limit));
	}

    @Override
    public void onClick() {
        this.element.onClick();
    }
}
