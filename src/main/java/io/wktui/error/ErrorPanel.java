package io.wktui.error;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import io.wktui.event.CloseErrorPanelAjaxEvent;

import org.apache.wicket.AttributeModifier;

import wktui.base.BasePanel;

public class ErrorPanel extends BasePanel {

	private static final long serialVersionUID = 1L;

	private WebMarkupContainer container = new WebMarkupContainer("container");

	private WebMarkupContainer titleContainer = new WebMarkupContainer("titleContainer");
	private WebMarkupContainer textContainer = new WebMarkupContainer("textContainer");
	private WebMarkupContainer closeIcon = new WebMarkupContainer("closeIcon");

	private Label titleLabel;
	private Label textLabel;

	private IModel<String> title;
	private IModel<String> text;

	private boolean is_close = false;

	public ErrorPanel(String id) {
		super(id);
	}

	public ErrorPanel(String id, IModel<String> textModel) {
		super(id);
		this.text = textModel;
	}
	
	public ErrorPanel(String id, IModel<String> titleModel, IModel<String> textModel) {
		super(id);
		this.title = titleModel;
		this.text = textModel;
	}

	
	public ErrorPanel(String id, Exception e ) {
		this(id, e, false);
	}
	
	public ErrorPanel(String id, Exception e, boolean isClose ) {
		super(id);
		this.title = new Model<String>(e.getClass().getSimpleName());;
		this.is_close=isClose;
		 if (e.getMessage()!=null)
			 this.text = new Model<String>( e.getMessage());
	}
	
	
	
		
	public void onInitialize() {
		super.onInitialize();

		setOutputMarkupId(true);

		add(container);

		AjaxLink<Void> closeButton = new AjaxLink<Void>("close") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				fire (new CloseErrorPanelAjaxEvent("close", target));
			}
		};

		closeButton.setVisible(isClose());
		closeButton.add(closeIcon);

		closeIcon.add(new AttributeModifier("class", getCloseIconCss()));
		container.add(closeButton);

		if (getCss() != null)
			container.add(new AttributeModifier("class", getCss()));

		container.add(titleContainer);
		container.add(textContainer);

		titleContainer.setVisible(getTitle() != null);
		textContainer.setVisible(getText() != null);

		titleLabel = new Label("title", (getTitle() != null ? getTitle().getObject() : ""));
		titleContainer.add(titleLabel);
		titleLabel.setVisible(getTitle() != null);
		titleLabel.setEscapeModelStrings(false);

		textLabel = new Label("text", (getText() != null ? getText().getObject() : ""));
		textLabel.setEscapeModelStrings(false);
		textLabel.setVisible(text != null);
		textContainer.add(textLabel);
	}

	public String getCloseIconCss() {
		return "fal fa-times iconError";
	}

	public boolean isClose() {
		return is_close;
	}

	public void setClose(boolean is_close) {
		this.is_close = is_close;
	}

	public String getCss() {
		return null;
	}

	private IModel<String> getTitle() {
		return title;
	}

	private IModel<String> getText() {
		return text;
	}

}
