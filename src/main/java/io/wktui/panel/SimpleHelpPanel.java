package io.wktui.panel;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.resource.UrlResourceReference;

import io.wktui.event.UIEvent;
import io.wktui.struct.list.ListPanel;
import io.wktui.struct.list.ListPanelMode;
import io.wktui.struct.list.ListPanelWicketEvent;

import wktui.base.InvisiblePanel;
import wktui.base.ModelPanel;

public class SimpleHelpPanel<T> extends ModelPanel<T> {

	private static final long serialVersionUID = 1L;
	
	private WebMarkupContainer textContainer;
	 
	private IModel<String> linklabel;
	private IModel<String> text;

	AjaxLink<Void> link;
	Label textLabel;
	
	public SimpleHelpPanel(String id) {
			this(id, null);
	}
		
	public SimpleHelpPanel(String id, IModel<T> model) {
		super(id, model);
		
		this.textContainer = new WebMarkupContainer("textContainer");
		add(textContainer);
		textContainer.setVisible(false);
		textContainer.setOutputMarkupId(true);
		
		link= new AjaxLink<> ("helpLink") {

				@Override
				public void onClick(AjaxRequestTarget target) {
					
					if (textContainer.isVisible())
						textContainer.setVisible(false);
					else
					textContainer.setVisible(true);
					
					target.add(SimpleHelpPanel.this.getParent());
				}
				
			};
			
			add(link);
			
			
	}

	@Override
	protected void addListeners() {
		super.addListeners();

		}

	@Override
	public void onBeforeRender() {
		super.onBeforeRender();
	}


	@Override
	public void onInitialize() {
		super.onInitialize();

		this.setOutputMarkupId(true);
		
		
		Label la= new Label( "help", (getLinkLabel()!=null) ? getLinkLabel().getObject() : getLabel("help"));
		la.setEscapeModelStrings(false);
		link.addOrReplace(la);
		
		textLabel= new Label( "text", (getHelpText()!=null) ? getHelpText().getObject() : "");
		textLabel.setEscapeModelStrings(false);
		textContainer.addOrReplace(textLabel);
		
	}

	

	public WebMarkupContainer getTextContainer() {
		return textContainer;
	}

	public IModel<String> getHelpText() {
		return text;
	}

	public void setTextContainer(WebMarkupContainer textContainer) {
		this.textContainer = textContainer;
	}

	public void setHelpText(IModel<String> text) {
		this.text = text;
		textLabel= new Label( "text", this.text.getObject());
		
		String s = this.text.getObject();
		
		textLabel.setEscapeModelStrings(false);
		textContainer.addOrReplace(textLabel);
	}

	public IModel<String> getLinkLabel() {
		return linklabel;
	}

	public void setLinkLabel(IModel<String> label) {
		this.linklabel = label;
		
		String s = this.linklabel .getObject();

		
		if (link!=null) {
			link.addOrReplace(new Label( "help", this.linklabel.getObject()));
		}
		
	}


}
