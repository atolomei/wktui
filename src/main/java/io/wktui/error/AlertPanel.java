package io.wktui.error;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.AttributeModifier;

import wktui.base.BasePanel;
import wktui.base.ModelPanel;

public class AlertPanel<T> extends ModelPanel<T> {

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

	
	static public final String HELP_INFO  = "fa-duotone fa-circle-info";
	static public final String ATTENTION  = "fa-solid fa-triangle-exclamation" ; //"fa-duotone fa-hexagon-exclamation";
	static public final String ICON_SIGNED  = "fa-duotone fa-signature-lock";
	
	
	static public final int INFO = 10;
	static public final int SUCCESS = 20;
	static public final int WARNING = 30;
	static public final int DANGER = 40;
	static public final int NEUTRAL = 50;

	static public final int TEXT = 60;

	private int alert_type = INFO;
	
	 
	String css;
	String icon;
	
	WebMarkupContainer alertContainer;
	WebMarkupContainer iconcontainer;
	WebMarkupContainer textcontainer;
	
	/**
	public AlertPanel(String id) {
		super(id, null);
	}

	public AlertPanel(String id, IModel<T> model, IModel<String> textModel) {
		super(id, model);
		alertContainer=new WebMarkupContainer("alert-container");
		this.text = textModel;
	}**/
	
	
	public AlertPanel(String id) {
		this( id, AlertPanel.INFO, null, null, null, null);
	}

	public AlertPanel(String id, int type) {
		this( id, type, null, null, null, null);
	}

	
	public AlertPanel(String id, IModel<String> textModel) {
		this( id, AlertPanel.INFO, null, null, null, textModel);
	}
	
	public AlertPanel(String id,  int alertType, IModel<String> textModel) {
		this( id, alertType, null, null, null, textModel);
	}
	
	
	public AlertPanel(String id, int alertType, String iconType, IModel<T> model, IModel<String> titleModel, IModel<String> textModel) {
		super(id, model);
		
		this.alert_type=alertType;
		this.title = titleModel;
		this.text = textModel;
		this.icon= iconType;
		
		alertContainer=new WebMarkupContainer("alert-container");
		setOutputMarkupId(false);
	}

	
	public void setIcon(String icon) {
		this.icon=icon;
		
		if (this.isInitialized()) {
			
			WebMarkupContainer icon_w=new WebMarkupContainer("icon");
			icon_w.add( new AttributeModifier("class", icon));
			iconcontainer=new WebMarkupContainer("icon-container");
			iconcontainer.setVisible(this.icon!=null);
			alertContainer.addOrReplace(iconcontainer);
			
			textcontainer=new WebMarkupContainer("text-container");
			alertContainer.addOrReplace(textcontainer);
			textcontainer.setVisible(text!=null || title!=null);
			textcontainer.add(new AttributeModifier("style", (icon!=null ? "float:left; width: calc(100% - 72px);":"float:left; width:100%;")));
			Label ti = new Label("title", (title!=null? title.getObject():""));
			textcontainer.addOrReplace(ti);
			ti.setVisible(title!=null);
			ti.setEscapeModelStrings(false);
			Label te  = new Label("text", (getText()!=null? getText():""));
			te.setEscapeModelStrings(false);
			te.setVisible(text!=null);
			textcontainer.addOrReplace(te);
			
		}
	}
	
	public void onInitialize() {
		super.onInitialize();
	
		setOutputMarkupId(true);	

		WebMarkupContainer au=new WebMarkupContainer("alert-outside-container");
		add(au);
		au.add(alertContainer);
		
		alertContainer.add(new AttributeModifier("class", () -> getCss()));
		
		iconcontainer=new WebMarkupContainer("icon-container"); 
		alertContainer.add(iconcontainer);
		iconcontainer.setVisible(this.icon!=null);
		WebMarkupContainer icon_w=new WebMarkupContainer("icon");
		if (icon!=null)
			icon_w.add( new AttributeModifier("class", icon));
		else
			icon_w.setVisible(false);
		iconcontainer.addOrReplace(icon_w);
	
		
		textcontainer=new WebMarkupContainer("text-container");
		alertContainer.add(textcontainer);
		textcontainer.setVisible( getText()!=null || title!=null);
		textcontainer.add(new AttributeModifier("style", (icon!=null ? "float:left; width: calc(100% - 72px);":"float:left; width:100%;")));
		Label ti = new Label("title", (title!=null? title.getObject():""));
		textcontainer.add(ti);
		ti.setVisible(title!=null);
		ti.setEscapeModelStrings(false);
		Label te  = new Label("text", getText());
		te.setEscapeModelStrings(false);
		te.setVisible( getText()!=null);
		textcontainer.add(te);
	
}
	 
 
	public IModel<String> getText() {
		return text;
	}

	public String getCss() {
		if (css==null)
			 return getDefaultCss();
		return css;
	}
	
	protected int getType() {
		return this.alert_type;
	}

	protected String getDefaultCss() {
		if (getType()==INFO) return  "alert alert-info";
		if (getType()==WARNING) return  "alert alert-warning";
		if (getType()==DANGER) return  "alert alert-danger";
		if (getType()==NEUTRAL) return  "alert alert-nobck";
		if (getType()==SUCCESS) return  "alert alert-success";
		if (getType()==TEXT) return  "alert alert-text";
		return "alert alert-info";
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

	 
	 
}
