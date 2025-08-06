package wktui.base;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

public abstract class LabelLinkPanel extends  BasePanel {

	private static final long serialVersionUID = 1L;
	
	private Label label;
	private Link<Void> link;
	
	public LabelLinkPanel(String id, Label label) {
		super(id);
		this.label=label;
		
		   link = new Link<Void>("link") {
	            private static final long serialVersionUID = 1L;
	            @Override
	            public void onClick() {
	                LabelLinkPanel.this.onClick();
	            }
	        };
	
		if (!label.getId().equals("label"))
				throw new IllegalArgumentException("Label id must be 'label'");
	}

	public LabelLinkPanel(String id, IModel<String> d) {
		super(id);
		this.label= new Label("label", d);
		
		  link = new Link<Void>("link") {
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
		this.label.setEscapeModelStrings(false);
	
        add(link);
		link.add(this.label);
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
