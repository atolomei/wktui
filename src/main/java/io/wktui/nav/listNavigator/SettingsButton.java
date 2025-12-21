package io.wktui.nav.listNavigator;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;

import io.wktui.nav.menu.AjaxLinkMenuItem;
import io.wktui.nav.menu.MenuItemPanel;
import io.wktui.nav.menu.NavDropDownMenu;
import io.wktui.nav.toolbar.ToolbarItem;
import io.wktui.struct.list.ListPanelMode;

public class SettingsButton extends ToolbarItem {

	private static final long serialVersionUID = 1L;

	private NavDropDownMenu<Void> settingsMenu;

	
	public SettingsButton(String id) {
		super(id);
	}

	@Override
	public void onInitialize() {
		super.onInitialize();

		settingsMenu = new NavDropDownMenu<Void>("settings", null, null);
		settingsMenu.setOutputMarkupId(true);
		
		settingsMenu.setTitleCss("d-block-inline d-sm-block-inline d-md-block-inline d-lg-none d-xl-none d-xxl-none ps-1 pe-1");
		settingsMenu.setIconCss( "fa-light fa-gear d-block-inline d-sm-block-inline d-md-block-inline d-lg-block-inline d-xl-block-inline d-xxl-block-inline ps-1 pe-1");
		settingsMenu.setVisible(isSettings());
		settingsMenu.addItem(new io.wktui.nav.menu.MenuItemFactory<Void>() {

			private static final long serialVersionUID = 1L;

			@Override
			public MenuItemPanel<Void> getItem(String id) {

				return new AjaxLinkMenuItem<Void>(id) {

					private static final long serialVersionUID = 1L;

					@Override
					public void onClick(AjaxRequestTarget target) {
						/**
						if (getListPanelMode() == ListPanelMode.TITLE_TEXT_IMAGE)
							setListPanelMode(ListPanelMode.TITLE);
						else
							setListPanelMode(ListPanelMode.TITLE_TEXT_IMAGE);
						addListView();
						refresh(target);
					**/
					}

					@Override
					public IModel<String> getLabel() {
						return getLabel("image");
					}

					public String getIconCssClass() {
						//if (getListPanelMode()==ListPanelMode.TITLE)
						//	return null;
						return "fa-solid fa-check";
					}

					@Override
					public String getBeforeClick() {
						return null;
					}
				};
			}
		});

		this.settingsMenu.addItem(new io.wktui.nav.menu.MenuItemFactory<Void>() {

			private static final long serialVersionUID = 1L;

			@Override
			public MenuItemPanel<Void> getItem(String id) {

				return new AjaxLinkMenuItem<Void>(id) {

					private static final long serialVersionUID = 1L;

					@Override
					public void onClick(AjaxRequestTarget target) {
						//setLiveSearch(!isLiveSearch());
						//addLiveSearch();
						//refresh(target);
					}

					public String getIconCssClass() {
						//if (isLiveSearch())
						//	return "fa-solid fa-check";
						return null;
					}
					
					@Override
					public IModel<String> getLabel() {
						return getLabel("liveSearch");
					}

					@Override
					public String getBeforeClick() {
						return null;
					}
				};
			}
		});
		
	}
	
	public boolean isSettings() {
		return true;
	}
	
	
}
