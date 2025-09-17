package io.wktui.nav.breadcrumb;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.pages.RedirectPage;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IDetachable;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import io.wktui.nav.menu.LinkMenuItem;
import io.wktui.nav.menu.MenuItemFactory;
import io.wktui.nav.menu.MenuItemPanel;
import io.wktui.nav.menu.NavDropDownMenu;
import wktui.base.BasePanel;
import wktui.base.InvisiblePanel;
import wktui.base.ModelPanel;

public class BreadCrumb<T> extends ModelPanel<T> {

	private static final long serialVersionUID = 1L;

	static private Logger logger = Logger.getLogger(BreadCrumb.class.getName());

	public static final int LIMIT = 40;

	private List<BreadcrumbBasePanel> list;
	private IModel<T> model;
	
	private WebMarkupContainer breadcrumbContainer;
	private WebMarkupContainer navigator;
	private WebMarkupContainer mobileNavigator;

	
	private String base_css = "breadcrumb-item";
	private String active_css = "active";

	public BreadCrumb() {
		this("breadcrumb", null);
	}

	public WebMarkupContainer getNavigator() {
		return this.navigator;
	}
	
	
	public void setNavigator(WebMarkupContainer  nav) {
		if (this.navigator==null) {
			this.navigator=nav;
		}
		else {
			this.navigator=nav;
			addOrReplace(this.navigator);
		}
	}
	public WebMarkupContainer getMobileNavigator() {
		return this.mobileNavigator;
	}
	
	
	public void setMobileNavigator(WebMarkupContainer  nav) {
		if (this.mobileNavigator==null) {
			this.mobileNavigator=nav;
		}
		else {
			this.mobileNavigator=nav;
			addOrReplace(this.mobileNavigator);
		}
	}
	
	
	public BreadCrumb(String id, IModel<T> model) {
		super(id, model);
		this.model = model;
		list = new ArrayList<BreadcrumbBasePanel>();
	}

	public IModel<T> getModel() {
		return this.model;
	}

	public void onDetach() {
		super.onDetach();
		try {

			if (model != null)
				model.detach();

			if (list == null)
				return;

			for (BreadcrumbBasePanel panel : list)
				panel.detach();

		} catch (Exception e) {
			// logger.error(e);
		}
	}

	public void onInitialize() {
		super.onInitialize();

		addComponents();
	}

	public BreadCrumb<T> addElement(BreadcrumbBasePanel p) {
		list.add(p);
		return this;
	}

	public BreadCrumb<T> addElement(BCElement b) {
		list.add(new BreadCrumbItemPanel(b));
		return this;
	}

	// public BreadCrumb<T> addElement(AjaxIBCElement b) {
	// list.add(new MenuAjaxBCElement(b));
	// return this;
	// }

	public void setActiveCss(String s) {
		this.active_css = s;
	}

	protected String getActiveCss() {
		return active_css;
	}

	protected void addComponents() {

		ListView<BreadcrumbBasePanel> v = new ListView<BreadcrumbBasePanel>("breadcrumb-element", list) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<BreadcrumbBasePanel> item) {
				Panel element = item.getModelObject();
				item.add(element);
				if ((item.getIndex() == (list.size() - 1)) && (getActiveCss() != null)) {
					item.add(new AttributeModifier("class", base_css + " " + getActiveCss()));
					item.getModelObject().add(new AttributeModifier("aria-current", "page"));
				} else
					item.add(new AttributeModifier("class", base_css));

			}
		};

		this.breadcrumbContainer = new WebMarkupContainer("breadcrumbContainer");
		this.breadcrumbContainer.add(v);

		addOrReplace(this.breadcrumbContainer);

		NavDropDownMenu<BreadcrumbBasePanel> menu = new NavDropDownMenu<BreadcrumbBasePanel>("menu", null,
				getLabel("navigation"));

		for (int counter = 0; counter < list.size(); counter++) {

			final int final_counter = counter;

			menu.addItem(new MenuItemFactory<BreadcrumbBasePanel>() {
				private static final long serialVersionUID = 1L;

				@Override
				public MenuItemPanel<BreadcrumbBasePanel> getItem(String id) {
					return new LinkMenuItem<BreadcrumbBasePanel>(id) {
						private static final long serialVersionUID = 1L; 

						@Override
						public IModel<String> getLabel() {
							return list.get(final_counter).getLabel();
						}

						@Override
						public void onClick() {
							list.get(final_counter).onClick();
						}
					};
				}
			});
		}

		add(menu);

		if (getNavigator()==null) {
			setNavigator(new InvisiblePanel("navigator"));
		}
		
		this.breadcrumbContainer.addOrReplace(getNavigator());
		
		if (getMobileNavigator()==null) {
			setMobileNavigator(new InvisiblePanel("navigator"));
		}
		addOrReplace(getMobileNavigator());

		
	}
	

	
}
