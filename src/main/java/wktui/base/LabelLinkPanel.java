package wktui.base;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import io.wktui.media.InvisibleImage;

public abstract class LabelLinkPanel extends  BasePanel {

	private static final long serialVersionUID = 1L;
	
	private Label subtitle;
	private String subtitleCss;
	private String subtitleStyle;

	private Label label;
	private String labelCss;
	private String labelStyle;

	private Link<Void> link;
	private String linkCss;
	
	private WebMarkupContainer icon;
	private String iconCss;

	private Image image;
	private String imageCss;
	
	private WebMarkupContainer labelContainer;
	private WebMarkupContainer sutitleContainer;
	
	private WebMarkupContainer imageIconContainer;
	
	
	
	
	public LabelLinkPanel(String id) {
		this( id, null);
		
	}

	public void setLabel( IModel<String> l) {
		this.label.setDefaultModelObject(l.getObject());
		this.label.setVisible(true);
	}
	

	public void setSubtitle( IModel<String> l) {
		this.subtitle.setDefaultModelObject(l.getObject());
		this.subtitle.setVisible(true);
	}
	
	
	public LabelLinkPanel(String id, Label label) {
		this(id, label, null);
	}

	public LabelLinkPanel(String id, Label label, Label subtitle) {
		super(id);

		this.label=new Label("label", "");
		this.label.setVisible(false);
		
		this.subtitle=new Label("subtitle", "");
		this.subtitle.setVisible(false);
		
		ini();
	
	}
	
	/**
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
		ini();
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
	*/
	
	
	
	
	
	private void ini() {
		
		if (!label.getId().equals("label"))
			throw new IllegalArgumentException("Label id must be 'label'");
	
		if (!subtitle.getId().equals("subtitle"))
			throw new IllegalArgumentException("subtitle id must be 'subtitle'");
	
		this.link = new Link<Void>("link") {
	            private static final long serialVersionUID = 1L;
	            @Override
	            public void onClick() {
	                LabelLinkPanel.this.onClick();
	            }
	        };
	
		

	        imageIconContainer = new WebMarkupContainer("imageIconContainer") {
				private static final long serialVersionUID = 1L;
				public boolean isVisible() {
					return (getImage()!=null) || (getIconCss()!=null);
				}
			};
			 
			link.add(this.imageIconContainer);

	        
	        
		labelContainer = new WebMarkupContainer("labelContainer") {
			private static final long serialVersionUID = 1L;
			public boolean isVisible() {
				return getLabel()!=null && getLabel().isVisible();
			}
		};
		labelContainer.add(this.label);
		link.add(this.labelContainer);
		
		sutitleContainer = new WebMarkupContainer("subtitleContainer") {
			private static final long serialVersionUID = 1L;
			public boolean isVisible() {
				return getSubtitle()!=null && getSubtitle().isVisible();
			}
		};
		sutitleContainer.add(this.subtitle);
		link.add(this.sutitleContainer);
		
		add(this.link);
		
	}
	
	
	@Override
	public void onInitialize() {
		super.onInitialize();
		
		if (this.label.isVisible()) {
			this.label.setEscapeModelStrings(false);
		}
		if (this.subtitle.isVisible()) {
			this.subtitle.setEscapeModelStrings(false);
		}

		if (labelCss!=null) {
			label.add(new AttributeModifier("class", this.label));
		}
		
		if (subtitleCss!=null) {
			subtitle.add(new AttributeModifier("class", this.subtitle));
		}
	  
		
		if (labelStyle!=null) {
			label.add(new AttributeModifier("style", this.labelStyle));
		}
		
		if (subtitleStyle!=null) {
			subtitle.add(new AttributeModifier("style", this.subtitleStyle));
		}
		
		
		
		icon = new WebMarkupContainer ("icon");
		if (iconCss!=null) {
			icon.add(new AttributeModifier("class", this.iconCss));
		}
		else {
			icon.setVisible(true);
		}
		imageIconContainer.add(icon);
	
	
		if (getImage()!=null) {
			Image i=getImage();
			if (getImageCss()!=null) {
				i.add(new AttributeModifier("class", this.getImageCss()));
			}
			imageIconContainer.add(i);
		}
		else {
			imageIconContainer.add(new InvisibleImage("image"));
		}
	
		
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

	public Label getLabel() {
		return label;
	}

	public Label getSubtitle() {
		return subtitle;
	}
 

	public void setSubtitle(Label subtitle) {
		this.subtitle = subtitle;
	} 
	 


	public String getLabelCss() {
		return labelCss;
	}


	public Link<Void> getLink() {
		return link;
	}


	public String getLinkCss() {
		return linkCss;
	}


	public WebMarkupContainer getIcon() {
		return icon;
	}


	public String getIconCss() {
		return iconCss;
	}


	public Image getImage() {
		return image;
	}


	public String getImageCss() {
		return imageCss;
	}


	public void setLabel(Label label) {
		this.label = label;
	}


	public void setLabelCss(String labelCss) {
		this.labelCss = labelCss;
	}


	public void setLink(Link<Void> link) {
		this.link = link;
	}


	public void setIcon(WebMarkupContainer icon) {
		this.icon = icon;
	}


	public void setIconCss(String iconCss) {
		this.iconCss = iconCss;
	}


	public void setImage(Image image) {
		this.image = image;
	}


	public void setImageCss(String imageCss) {
		this.imageCss = imageCss;
	}



	public String getSubtitleCss() {
		return subtitleCss;
	}



	public String getSubtitleStyle() {
		return subtitleStyle;
	}



	public String getLabelStyle() {
		return labelStyle;
	}



	public void setSubtitleStyle(String subtitleStyle) {
		this.subtitleStyle = subtitleStyle;
	}



	public void setLabelStyle(String labelStyle) {
		this.labelStyle = labelStyle;
	}



	public void setSubtitleCss(String subtitleCss) {
		this.subtitleCss = subtitleCss;
	}

	
}
