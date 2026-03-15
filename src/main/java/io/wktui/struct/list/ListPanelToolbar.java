package io.wktui.struct.list;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;

import io.wktui.nav.menu.NavDropDownMenu;
import wktui.base.BasePanel;
import wktui.base.NumberFormatter;

public abstract class ListPanelToolbar extends BasePanel {

	private static final long serialVersionUID = 1L;

	private AjaxLink<Void> sLink;
	
	private NavDropDownMenu<Void> settingsMenu;
	private PagingNavigator navigator;
	private Label total;
	private Integer l_total;
	WebMarkupContainer container = new WebMarkupContainer("container");
	
	private String css;
	
	public boolean isSearchButton() {
		return true;
	}
	
	public  abstract void onClick( AjaxRequestTarget target);
	
	
	public ListPanelToolbar(String id, PagingNavigator navigator,  NavDropDownMenu<Void> menu) {
		super(id);

		this.settingsMenu=menu;
		this.navigator=navigator;
	}

	
	public void onInitialize() {
		super.onInitialize();
		
		add(container);
		
		if (getCss() != null)
			container.add( new org.apache.wicket.AttributeModifier("class", getCss()) );
		
		container.add(this.settingsMenu);
		container.add( this.navigator);
		
		this.total = new Label( "total", NumberFormatter.formatNumber(getTotal())) {
			private static final long serialVersionUID = 1L;
			public boolean isVisible() {
				return getTotal()!=null;
			}
		};
		
		container.add(this.total);
	
		sLink = new AjaxLink<Void>("searchLink") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				ListPanelToolbar.this.onClick( target );
			}

			@Override
			public boolean isVisible() {
				return isSearchButton();
			}
		};
		
		container.add(sLink);
	
	}

	public Integer getTotal() {
		return l_total;
	}

	public String getCss() {
		return css;
	}

	public void setCss(String css) {
		this.css = css;
	}
	
	
}
