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
	
	
	String labelCss;
	String iconCss;
	IModel<String> title;
	
	private WebMarkupContainer icon;
	
	
	private WebMarkupContainer titlePanel;


	public void setTitlePanel(WebMarkupContainer titlePanel) {
		if (titlePanel!=null) {
			this.titlePanel=titlePanel;
			addOrReplace(titlePanel);
		}
		else {
			this.titlePanel=titlePanel;
		}
	}
	
	
	protected WebMarkupContainer getTitlePanel() {
		return titlePanel;
	}


	
	public NavDropDownMenu(String id) {
		super(id, null);
	}
	
	public NavDropDownMenu(String id, IModel<T> model ) {
		super(id, model);
		this.title=null;
	}
	
	public NavDropDownMenu(String id, IModel<T> model, IModel<String> title) {
		super(id, model);
		this.title=title;
	}
 
	
	@Override
	public void onInitialize() {
		super.onInitialize();
		
		if (getTitlePanel()==null) {
			this.titlePanel=new TitleFragment("titlePanel");
			addOrReplace(titlePanel);
		}
	}
	
	 

	public void addItem(MenuItemFactory<T> item) {
			super.addItem(item);
	}
	
	
	
	public void setTitle(IModel<String> label) {
		this.title=label;
	}
	
	public IModel<String> getTitle() {
		return title;
	}

	public String getLabelCss() {
		return labelCss;
	}

	public void setLabelCss(String labelCss) {
		this.labelCss = labelCss;
	}

	public String getIconCss() {
		return iconCss;
	}

	public void setIconCss(String iconCss) {
		this.iconCss = iconCss;
	}

	
	/**
	 * 
	 * Title Fragment
	 * 
	 */
	public class TitleFragment extends Fragment {

		Label f_label;
		
		public TitleFragment(String id) {
			super(id, "titleFragment", NavDropDownMenu.this);
		}
		private static final long serialVersionUID = 1L;
 

		public void onInitialize() {
			super.onInitialize();
			
			this.f_label = new Label("label", getTitle()) {
				private static final long serialVersionUID = 1L;
				public boolean isVisible() {
					return getTitle()!=null;
				}
			};
			
			if (getLabelCss()!=null)
				f_label.add( new AttributeModifier("class", getLabelCss()));
		
			add(f_label);
		
			icon = new WebMarkupContainer("icon");
			add(icon);
			
			if (getIconCss()!=null)
				icon.add( new AttributeModifier("class", getIconCss()));
			
			icon.setVisible(getIconCss()!=null);
		}
		
	}

}
