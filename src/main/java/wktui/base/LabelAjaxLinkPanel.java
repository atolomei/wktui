package wktui.base;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

public abstract class LabelAjaxLinkPanel<T> extends  BasePanel {

	private static final long serialVersionUID = 1L;
	
	private final Label label;
	private final AjaxLink<Void> link;
	private final IModel<T> model;
	private final String iconCss;
	private WebMarkupContainer icon;
	
	
	public LabelAjaxLinkPanel(String id, Label label, IModel<T> model) {
			this(id, label, model, null);
	}	
	
	public LabelAjaxLinkPanel(String id, Label label, IModel<T> model, String iconCss) {
		super(id);
		
		if (label!=null && !label.getId().equals("label"))
			throw new IllegalArgumentException("Label id must be 'label'");

		if (label==null) {
			this.label=new Label("label","");
			this.label.setVisible(false);
		}
		else
			this.label=label;

		
		this.model=model;
		this.iconCss=iconCss;
		
		 this.link = new AjaxLink<Void>("link") {
	            private static final long serialVersionUID = 1L;
	            @Override
	            public void onClick(AjaxRequestTarget target) {
	                LabelAjaxLinkPanel.this.onClick(target);
	            }
	        };
	
	}

	public LabelAjaxLinkPanel(String id, IModel<String> labelStr) {
		super(id); 
		
		this.iconCss=null;
		this.model=null;
		
		this.label= new Label("label", labelStr);
		
		 this.link = new AjaxLink<Void>("link") {
	            private static final long serialVersionUID = 1L;
	            @Override
	            public void onClick(AjaxRequestTarget target) {
	                LabelAjaxLinkPanel.this.onClick(target);
	            }
	        };
	}
	
	@Override
	public void onInitialize() {
		super.onInitialize();
		
		if (this.label!=null)
			this.label.setEscapeModelStrings(false);
	
        add(link);
        
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

	public void onDetach() {
		super.onDetach();
		
		if (model!=null)
			model.detach();
	}
	
	protected abstract void onClick(AjaxRequestTarget target);

	public void setLinkStyle(String string) {
        link.add(new AttributeModifier("style", string));
    }
    
    public void setLinkCss(String string) {
        link.add(new AttributeModifier("class", string));
    }
    
    public void setLabelStyle(String string) {
		label.add(new AttributeModifier("style", string));
	}
    
    
	
	public void setCss(String string) {
		add(new AttributeModifier("class", string));
	}


	public IModel<T> getModel() {
		return model;
	}


	//public void setModel(IModel<T> model) {
	//	this.model = model;
	//}

	
}
