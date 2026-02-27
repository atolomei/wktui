package io.wktui.nav.menu;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.model.IModel;

import wktui.base.BasePanel;

public class NavDropDownMenu<T> extends DropDownMenu<T> {

	private static final long serialVersionUID = 1L;

	String titleStyle;
	String subtitleStyle;

	
	String titleCss;
	String subtitleCss;
	
	
	
	String iconCss;
	
	IModel<String> title;
	IModel<String> subtitle;
	
	private WebMarkupContainer icon;
	private WebMarkupContainer titlePanel;

	public NavDropDownMenu(String id) {
		super(id, null);
	}

	public NavDropDownMenu(String id, IModel<T> model) {
		super(id, model);
		this.title = null;
	}

	public NavDropDownMenu(String id, IModel<T> model, IModel<String> title) {
		super(id, model);
		this.title = title;
	}

	@Override
	public void onInitialize() {
		super.onInitialize();
		if (getTitlePanel() == null) {
			addTitlePanel();
		}
	}

	public void addTitlePanel() {
		this.titlePanel = new TitleFragment("titlePanel");
		addOrReplace(titlePanel);
	}
	
	public void addItem(MenuItemFactory<T> item) {
		super.addItem(item);
	}

	
	public void setSubtitle(IModel<String> label) {
		this.subtitle = label;
	}

	public IModel<String> getSubtitle() {
		return  subtitle;
	}
	
	
	public void setTitle(IModel<String> label) {
		this.title = label;
	}

	public IModel<String> getTitle() {
		return title;
	}

 
 

	public String getIconCss() {
		return iconCss;
	}

	public void setIconCss(String iconCss) {
		this.iconCss = iconCss;
	}

	public void setTitlePanel(WebMarkupContainer titlePanel) {
		if (titlePanel != null) {
			this.titlePanel = titlePanel;
			addOrReplace(titlePanel);
		} else {
			this.titlePanel = titlePanel;
		}
	}

	protected WebMarkupContainer getTitlePanel() {
		return titlePanel;
	}

	public String getTitleStyle() {
		return titleStyle;
	}

	public String getSubtitleStyle() {
		return subtitleStyle;
	}

	public String getTitleCss() {
		return titleCss;
	}

	public String getSubtitleCss() {
		return subtitleCss;
	}

	public WebMarkupContainer getIcon() {
		return icon;
	}

	public void setTitleStyle(String titleStyle) {
		this.titleStyle = titleStyle;
	}

	public void setSubtitleStyle(String subtitleStyle) {
		this.subtitleStyle = subtitleStyle;
	}

	public void setTitleCss(String titleCss) {
		this.titleCss = titleCss;
	}

	public void setSubtitleCss(String subtitleCss) {
		this.subtitleCss = subtitleCss;
	}

	public void setIcon(WebMarkupContainer icon) {
		this.icon = icon;
	}

	/**
	 * 
	 * Title Fragment
	 * 
	 */
	public class TitleFragment extends Fragment {

		Label f_title;
		Label f_subtitle;
		
		WebMarkupContainer titleSubtitleContainer;
		WebMarkupContainer subtitleContainer;
	 	WebMarkupContainer titleContainer;
	 	
		
		public TitleFragment(String id) {
			super(id, "titleFragment", NavDropDownMenu.this);
		}

		private static final long serialVersionUID = 1L;

		public void onInitialize() {
			super.onInitialize();
			
			titleSubtitleContainer =new WebMarkupContainer("titleSubtitleContainer") {
				private static final long serialVersionUID = 1L;

				public boolean isVisible() {
					return (getSubtitle() != null) || (getTitle()!=null);
				}
			};
			add(titleSubtitleContainer);
			
			
			subtitleContainer =new WebMarkupContainer("subtitleContainer") {
				private static final long serialVersionUID = 1L;

				public boolean isVisible() {
					return getSubtitle() != null;
				}
			};
			titleSubtitleContainer.add(subtitleContainer);
			
			
			titleContainer =new WebMarkupContainer("titleContainer") {
				private static final long serialVersionUID = 1L;

				public boolean isVisible() {
					return getTitle() != null;
				}
			};
			
			titleSubtitleContainer.add(titleContainer);

			//if (getSubtitle()!=null) {
			//	titleSubtitleContainer.add( new AttributeModifier( "style","display-inline:block; float:left; text-align:right; margin-right:6px; margin-top:-6px;"));
			//			}
			
			this.f_title = new Label("title", getTitle()) {
				private static final long serialVersionUID = 1L;

				public boolean isVisible() {
					return getTitle() != null;
				}
			};

			if (getTitleCss() != null)
				f_title.add(new AttributeModifier("class", getTitleCss()));

			if (getTitleStyle() != null)
				f_title.add(new AttributeModifier("class", getTitleStyle()));

			
			titleContainer.add(f_title);
			
			this.f_subtitle = new Label("subtitle", getSubtitle()) {
				private static final long serialVersionUID = 1L;

				public boolean isVisible() {
					return getSubtitle() != null;
				}
			};

			if (getSubtitleCss() != null)
				f_subtitle.add(new AttributeModifier("class", getSubtitleCss()));

			
			if (getSubtitleStyle() != null)
				f_subtitle.add(new AttributeModifier("class", getSubtitleStyle()));

			
			subtitleContainer.add(f_subtitle);

			WebMarkupContainer imageIconContainer = new WebMarkupContainer( "imageIconContainer") {
				 
				private static final long serialVersionUID = 1L;

				public boolean isVisible() {
					return getIconCss() != null;
				}
			};
					
			icon = new WebMarkupContainer("icon");
			imageIconContainer.add(icon);
			
			add(imageIconContainer);

		if (getIconCss() != null)
				icon.add(new AttributeModifier("class", getIconCss()));
			
			icon.setVisible(getIconCss() != null);
		
		}

	}

}
