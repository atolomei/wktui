package wktui.base;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

public abstract class LabelLinkPanel extends  BasePanel {

	private static final long serialVersionUID = 1L;
	
	private Label label;
	private Link<Void> link;
	private WebMarkupContainer icon;
	private String iconCss;
	
	public LabelLinkPanel(String id, Label label) {
		this(id, label, null);
	}

	
	public LabelLinkPanel(String id, IModel<String> labelStr) {
		this(id, labelStr, null);
	}
		
	
	public LabelLinkPanel(String id, Label label, String icoCss) {
		super(id);
		
		this.iconCss=icoCss;
		
		if (label==null) {
			this.label=new Label("label", "");
			this.label.setVisible(false);
		}
		else
			this.label=label;
			
		
		
		this.link = new Link<Void>("link") {
	            private static final long serialVersionUID = 1L;
	            @Override
	            public void onClick() {
	                LabelLinkPanel.this.onClick();
	            }
	        };
	
		if (!label.getId().equals("label"))
				throw new IllegalArgumentException("Label id must be 'label'");
	}

	
	
	public LabelLinkPanel(String id, IModel<String> labelStr, String icoCss) {
		super(id);
		
		
		this.iconCss=icoCss;
		this.label= new Label("label", labelStr);
		
		if (labelStr==null)
			this.label.setVisible(false);
		
		 this.link = new Link<Void>("link") {
	            private static final long serialVersionUID = 1L;
	            @Override
	            public void onClick() {
	                LabelLinkPanel.this.onClick();
	            }
	        };

	}
	
	@Override
	public void onInitialize() {
		super.onInitialize();


		if (this.label.isVisible())
			this.label.setEscapeModelStrings(false);
	
        add(this.link);
		link.add(this.label);
	

		icon = new WebMarkupContainer ("icon");

		if (iconCss!=null) {
			icon.add(new AttributeModifier("class", this.iconCss));
		}
		else {
			icon.setVisible(true);
		}
		link.add(icon);
	}

	
	
	protected abstract void onClick();

	public void setLinkStyle(String string) {
        link.add(new AttributeModifier("style", string));
    }
    
    public void setLinkCss(String string) {
        link.add(new AttributeModifier("class", string));
    }
    
    public void setStyle(String string) {
		label.add(new AttributeModifier("style", string));
	}
	
	public void setCss(String string) {
		label.add(new AttributeModifier("class", string));
	}

	
}
