package io.wktui.error;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;

import wktui.base.BasePanel;

public class ErrorPanel extends BasePanel {

	WebMarkupContainer container = new WebMarkupContainer("container");

	WebMarkupContainer titleContainer = new WebMarkupContainer("titleContainer");
	WebMarkupContainer textContainer = new WebMarkupContainer("textContainer");

	
	Label titleLabel;
	Label textLabel;
	
	IModel<String> title;
	IModel<String> text;

	
	public ErrorPanel(String id) {
		super(id);
	}

	
	public ErrorPanel(String id, IModel<String> titleModel, IModel<String> textModel) {
		super(id);
		this.title=titleModel;
		this.text=textModel;
	}
	
	public void onInitialize() {
		super.onInitialize();
		
		
	setOutputMarkupId(true);
		
	 
		add(container);
		
		AjaxLink<Void> closeButton =new AjaxLink<Void>("close") {
			private static final long serialVersionUID = 1L;
			@Override
			public void onClick(AjaxRequestTarget target) {
					//fire (new CloseErrorPanelEvent<>(target));
			}
		};
		
		closeButton.setVisible(isClose());

		container.add(closeButton);
		
		// loseButton.add(new AttributeModifier("class", getCss()));
		
		container.add( titleContainer );
		container.add( textContainer );
		
		titleContainer.setVisible(getTitle()!=null);
		textContainer.setVisible(getText()!=null);

		
		titleLabel = new Label("title", (getTitle()!=null? getTitle().getObject():""));
		titleContainer.add(titleLabel);
		titleLabel.setVisible(getTitle()!=null);
		titleLabel.setEscapeModelStrings(false);
		
		textLabel  = new Label("text", (getText()!=null? getText().getObject():""));
		textLabel.setEscapeModelStrings(false);
		textLabel.setVisible(text!=null);
		textContainer .add(textLabel);
		
	}

	
	
	

	private IModel<String> getTitle() {
		return title;
	}


	private IModel<String> getText() {
		return text;
	}

	
	private boolean is_close = false;

	public boolean isClose() {
		return is_close;
	}


	public void setClose(boolean is_close) {
		this.is_close = is_close;
	}
	
	private String getCss() {
		return null;
	}
}
