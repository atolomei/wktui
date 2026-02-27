package io.wktui.nav.toolbar;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.AbstractLink;
 
import org.apache.wicket.model.IModel;

import org.apache.wicket.AttributeModifier;


public abstract class ButtonToolbarItem<T> extends ToolbarItem {

	private static final long serialVersionUID = 1L;
	private IModel<T> model;
	private AbstractLink link;
	private Label label;
	private IModel<String> title;
 
	private WebMarkupContainer icon;
	
	public ButtonToolbarItem()  {
		super("item");
	}

	public ButtonToolbarItem(String id) {
		super(id);
	}

	public ButtonToolbarItem(String id, IModel<String> title) {
		super(id);
		this.title=title;
	}
	
	public ButtonToolbarItem(IModel<String> title) {
		super("item");
		this.title=title;
	}
	
	public ButtonToolbarItem(String id, IModel<T> model, IModel<String> title) {
		super(id);
		this.title= title;
		this.model=model;
	}
	public ButtonToolbarItem(IModel<T> model, IModel<String> title) {
		super("item");
		this.title= title;
		this.model=model;
	}
	
	
	protected abstract AbstractLink createLink();
	
	@Override
	public void onInitialize() {
		super.onInitialize();
		
		link = createLink();
		add(link);
		
		if (getButtonCss()!=null)
			link.add( new AttributeModifier("class", getButtonCss()));
			
		label = new Label("label", getButtonLabel()) {
			private static final long serialVersionUID = 1L;
			public boolean isVisible() {
				return getButtonLabel()!=null;
			}
		};
		
		if (getLabelCss()!=null)
			label.add( new AttributeModifier("class", getLabelCss()));

		link.add(label);

		
		icon = new WebMarkupContainer("icon");
		
		if (getIconCss()!=null)
			icon.add( new AttributeModifier("class", getIconCss() + (getButtonLabel()!=null? " me-1" : "")));

		
		icon.setVisible(getIconCss()!=null);

		link.add(icon);
	
	
		if (getButtonTitle()!=null)
			link.add(new AttributeModifier("title", getButtonTitle()));
	}
	
	
	protected String getButtonCss() {
		return "btn btn-sm btn-outline-primary mt-1";
	}
	
	protected String getLabelCss() {
		return null;
	}

	protected String getIconCss() {
		return null;
	}
	
	public IModel<String> getButtonTitle() {
		return null;
	}
	
	public IModel<String> getButtonLabel() {
		return this.title;
	}

	
	@Override
	public void onDetach() {
		super.onDetach();
		
		if (model!=null)
			model.detach();
	}
	
	
	public IModel<T> getModel() {
		return model;
	}
}
