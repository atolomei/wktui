package io.wktui.struct.list;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
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
		
		add(this.settingsMenu);
		add( this.navigator);
		
		this.total = new Label( "total", NumberFormatter.formatNumber(getTotal())) {
			private static final long serialVersionUID = 1L;
			public boolean isVisible() {
				return getTotal()!=null;
			}
		};
		
		add(this.total);
	
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
		
		add(sLink);
	
	}

	public Integer getTotal() {
		return l_total;
	}
	
	
}
