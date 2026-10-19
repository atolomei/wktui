package io.wktui.nav.menu;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.ResourceReference;

import wktui.base.BasePanel;

/**
 * Global hamburger main menu.
 *
 * <p>
 * Renders a hamburger toggle button (to be placed inside the global top bar)
 * plus a sliding side menu. Entries are added programmatically via
 * {@link MenuItemFactory} instances ({@link #addItemFactory(MenuItemFactory)}),
 * or with the convenience methods {@link #addLink(IModel, String)} and
 * {@link #addTitle(IModel)}.
 * </p>
 *
 * <p>
 * Behaviour (implemented in MainMenu.js / MainMenu.css):
 * </p>
 * <ul>
 * <li>md and larger: 380px wide, fixed below the top bar, pushes the page
 * {@code main} content to the right when opened.</li>
 * <li>sm and smaller: 100% wide, in the normal flow, pushes the content
 * down.</li>
 * <li>Uses all the vertical space available below the top bar, with internal
 * scroll if needed.</li>
 * <li>Closes when clicking the hamburger again or anywhere outside the
 * menu.</li>
 * </ul>
 *
 * Usage:
 *
 * <pre>
 * MainMenu menu = new MainMenu("mainMenu");
 * menu.addLink(Model.of("Home"), "/home");
 * menu.addTitle(Model.of("Reports"));
 * menu.addLink(Model.of("Visits"), "/reports/visits");
 * add(menu);
 * </pre>
 */
public class MainMenu extends BasePanel {

	private static final long serialVersionUID = 1L;

	private static final ResourceReference CSS = new CssResourceReference(MainMenu.class, "MainMenu.css");
	private static final ResourceReference JS = new JavaScriptResourceReference(MainMenu.class, "MainMenu.js");

	private final List<MenuItemFactory<?>> factories = new ArrayList<>();

	public MainMenu(String id) {
		super(id);
		setOutputMarkupId(true);
	}

	/** Adds an entry via its factory. */
	public MainMenu addItemFactory(MenuItemFactory<?> factory) {
		this.factories.add(factory);
		return this;
	}

	/** Convenience: adds a normal navigation entry. */
	public MainMenu addLink(final IModel<String> label, final String url) {
		return addItemFactory(new MenuItemFactory<Void>() {
			private static final long serialVersionUID = 1L;

			@Override
			public MenuItemPanel<Void> getItem(String id) {
				return new MainMenuLinkItem(id, label, url);
			}
		});
	}

	/** Convenience: adds a title entry (bold, uppercase). */
	public MainMenu addTitle(final IModel<String> label) {
		return addItemFactory(new MenuItemFactory<Void>() {
			private static final long serialVersionUID = 1L;

			@Override
			public MenuItemPanel<Void> getItem(String id) {
				return new MainMenuTitleItem(id, label);
			}
		});
	}

	@Override
	public void onInitialize() {
		super.onInitialize();

		WebMarkupContainer toggle = new WebMarkupContainer("toggle");
		toggle.setMarkupId(getMarkupId() + "-toggle");
		add(toggle);

		WebMarkupContainer menu = new WebMarkupContainer("menu");
		menu.setMarkupId(getMarkupId() + "-menu");
		add(menu);

		List<MenuItemFactory<?>> sorted = new ArrayList<>(factories);
		sorted.sort(Comparator.comparingInt(MenuItemFactory::getOrder));

		menu.add(new ListView<MenuItemFactory<?>>("items", sorted) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<MenuItemFactory<?>> item) {
				item.add(item.getModelObject().getItem("item"));
			}
		});
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		response.render(CssHeaderItem.forReference(CSS));
		response.render(JavaScriptHeaderItem.forReference(JS));
		response.render(OnDomReadyHeaderItem.forScript(
				"wktuiMainMenu.init('" + getMarkupId() + "-toggle', '" + getMarkupId() + "-menu');"));
	}

	@Override
	public void onDetach() {
		super.onDetach();
		for (MenuItemFactory<?> f : factories)
			f.detach();
	}
}
