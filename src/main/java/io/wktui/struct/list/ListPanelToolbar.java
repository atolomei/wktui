package io.wktui.struct.list;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;

import io.wktui.nav.menu.NavDropDownMenu;
import wktui.base.BasePanel;
import wktui.base.NumberFormatter;

public class ListPanelToolbar extends BasePanel {

	private static final long serialVersionUID = 1L;

	
	private NavDropDownMenu<Void> settingsMenu;
	private PagingNavigator navigator;
	private Label total;
	private Integer l_total;
	
	public ListPanelToolbar(String id, PagingNavigator navigator,  NavDropDownMenu<Void> menu) {
		super(id);

		this.settingsMenu=menu;
		this.navigator=navigator;
		
//		if (totalStr!=null)
//			this.total=new Label ("total", totalStr);
//		else {
//			this.total=new Label ("total", "");
//			this.total.setVisible(false);
//		}	
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
	}

	public Integer getTotal() {
		return l_total;
	}
	
	
}
