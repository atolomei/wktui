package io.wktui.struct.list;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import io.wktui.error.ErrorPanel;
import io.wktui.nav.menu.AjaxLinkMenuItem;
import io.wktui.nav.menu.MenuItemPanel;
import io.wktui.nav.menu.NavDropDownMenu;

import wktui.base.BasePanel;
import wktui.base.InvisiblePanel;
import wktui.base.LabelPanel;
import wktui.base.Logger;

/**
 * liveSearch
 * @param <T>
 */
public class ListPanel<T> extends BasePanel {

	private static final long serialVersionUID = 1L;

	static private Logger logger = Logger.getLogger(ListPanel.class.getName());

	static final public int MINIMUN_SIZE_FOR_LIVE_SEARCH = 5;
	static final int DEFAULT_PAGE_SIZE = 30;

	public static final String ITEM_EXPAND = "item-expand";

	private List<IModel<T>> workingListModel;

	private final List<IModel<T>> itemsListModel;
	
	private PageableListView<IModel<T>> listView;

	private IModel<String> title;

	private int pageSize = DEFAULT_PAGE_SIZE;

	private WebMarkupContainer titleContainer;
	private WebMarkupContainer listItemContainer;
	private WebMarkupContainer bottom;
	private WebMarkupContainer liveSearchContainer;
	private WebMarkupContainer toolbar;
	private WebMarkupContainer liveSearchTitleContainer;
	private WebMarkupContainer frame;
	private WebMarkupContainer toolbarContainer;

	
	
	
	

	private Form<?> form;
	private Label totalLabel;
	private Label initialTotalLabel;
	private Label liveSearchTitle;
	private String stringValue;

	private org.apache.wicket.markup.html.form.TextField<?> input;

	private boolean hasItemMenu = false;
	private boolean hasExpander = false;
	private boolean isSettings = true;

	private ListPanelMode mode;
	private Boolean liveSearch;

	private NavDropDownMenu<Void> settingsMenu;
	private PagingNavigator navigator;

	/**
	 * 
	 * 
	 * @param id
	 */
	public ListPanel(String id) {
		super(id);
		setOutputMarkupId(true);
		this.itemsListModel = null;
	}

	public ListPanel(String id, List<IModel<T>> list) {
		super(id);
		setOutputMarkupId(true);
		this.itemsListModel = list;
	}

	boolean requiresReload = true;

	@Override
	public void onInitialize() {
		super.onInitialize();

		setDefaults();

		this.frame = new WebMarkupContainer("frame");
		this.frame.setOutputMarkupId(true);
		add(this.frame);

		this.bottom = new InvisiblePanel("bottom");
		this.frame.addOrReplace(this.bottom);

		this.listItemContainer = new WebMarkupContainer("list-items-container");
		this.listItemContainer.setOutputMarkupId(true);

		this.frame.addOrReplace(this.listItemContainer);

	}

	public void setToolbar(WebMarkupContainer toolbar) {
		this.toolbar = toolbar;
	}

	public int gePageSize() {
		return this.pageSize;
	}

	@Override
	public void onBeforeRender() {
		super.onBeforeRender();

		setWorkingItems(getItems());
		addTitle();
		addSettingsTitleBar();
		addLiveSearch();
		addListView();
		addToolbar();
	}

	public List<IModel<T>> getWorkingItems() {
		return this.workingListModel;
	}

	public String getStringValue() {
		return stringValue;
	}

	public void setStringValue(String value) {
		this.stringValue = value;
	}

	public Integer getTotalItems() {
		return Integer.valueOf(getItems().size());
	}

	public Integer getTotalWorkingItems() {
		return Integer.valueOf(getWorkingItems().size());
	}

	public void setListPanelMode(ListPanelMode mode) {
		this.mode = mode;
		if (this.listItemContainer != null)
			this.listItemContainer.add(new AttributeModifier("class", this.mode.getCss()));
	}

	public ListPanelMode getListPanelMode() {
		return this.mode;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getPageSize() {
		return this.pageSize;
	}

	public void setSettings(boolean settings) {
		this.isSettings = settings;
	}

	public IModel<String> getItemLabel(IModel<T> model) {
		return new Model<String>(model.getObject().toString());
	}

	public boolean hasExpander() {
		return this.hasExpander;
	}

	public void setHasExpander(boolean b) {
		this.hasExpander = b;
	}

	public boolean hasItemMenu() {
		return this.hasItemMenu;
	}

	public void setItemMenu(boolean b) {
		this.hasItemMenu = b;
	}

	public IModel<String> getTitle() {
		return this.title;
	}

	@Override
	public void onDetach() {
		super.onDetach();

		if (this.itemsListModel != null)
			this.itemsListModel.forEach(i -> i.detach());

		if (this.workingListModel != null)
			this.workingListModel.forEach(i -> i.detach());
	}

	public void setTitle(IModel<String> title) {
		this.title = title;
	}

	public List<IModel<T>> getItems() {
		return this.itemsListModel;
	}

	public ListPanelMode getMode() {
		return mode;
	}

	public void setMode(ListPanelMode mode) {
		this.mode = mode;
	}

	public boolean isLiveSearch() {
		return liveSearch;
	}

	public void setLiveSearch(boolean liveSearch) {
		this.liveSearch = Boolean.valueOf(liveSearch);

	}

	protected WebMarkupContainer getToolbar() {
		if (this.toolbar == null)
			this.toolbar = new InvisiblePanel("toolbar");
		return this.toolbar;
	}

	protected boolean isToolbar() {
		return (this.toolbar != null) && this.toolbar.isVisible();
	}

	protected org.apache.wicket.markup.html.form.TextField<?> newTextField() {

		org.apache.wicket.markup.html.form.TextField<?> input = new org.apache.wicket.markup.html.form.TextField<String>("input", new PropertyModel<String>(this, "stringValue")) {

			private static final long serialVersionUID = 1L;

			@Override
			public void validate() {
				super.validate();
			}

			@Override
			public boolean isEnabled() {
				return true;
			}

			protected void onComponentTag(final ComponentTag tag) {
				super.onComponentTag(tag);
			}
		};

		input.setOutputMarkupId(true);

		return input;
	}

	protected void addToolbar() {
		
		toolbarContainer = new WebMarkupContainer("toolbarContainer") {
			private static final long serialVersionUID = 1L;
			public boolean isVisible() {
				return isToolbar();
			}
		};

		this.navigator = new PagingNavigator("navigator", this.listView);

		int size = getWorkingItems().size();

		if (getPageSize() >= size)
			this.navigator.setVisible(false);

		ListPanelToolbar toolbar = new ListPanelToolbar("toolbar", this.navigator, this.settingsMenu) {
			private static final long serialVersionUID = 1L;
			public Integer getTotal() {
				return ListPanel.this.getTotalItems();
			};

			public boolean isSearchButton() {
				return true;
			}

			public void onClick(AjaxRequestTarget target) {
				setLiveSearch(!isLiveSearch());
				addLiveSearch();
				refresh(target);
			}
		};

		setToolbar(toolbar);

		this.toolbarContainer.addOrReplace(getToolbar());
		this.listItemContainer.addOrReplace(toolbarContainer);
		this.listItemContainer.add(new AttributeModifier("class", this.getListPanelMode().getCss() + (this.isToolbar() ? " hastoolbar" : "")));

	}

	protected WebMarkupContainer getListItemExpandedPanel(IModel<T> model, ListPanelMode mode) {
		return new LabelPanel("expanded-panel", new Model<String>(model.getObject().toString()));
	}

	protected boolean isSettings() {
		return this.isSettings;
	}

	protected IModel<String> getLiveSearchTitle() {
		return getLabel("live-search");
	}

	protected void onKey(AjaxRequestTarget target, String jsKeycode) {

		try {

			Integer code = Integer.valueOf(jsKeycode);
			char character = (char) code.intValue();

			if (code == 13) {
				setStringValue("");
				setWorkingItems(getItems());

				target.add(this.listItemContainer);
				target.add(input);
				target.add(this.totalLabel);
			}

			else if (code == 8 && input.getValue() != null && input.getValue().length() > 0) {
				setStringValue(input.getValue().substring(0, input.getValue().length() - 1));

				target.add(this.listItemContainer);
				target.add(this.totalLabel);

			}
			// delete
			// left, right, up, down
			// back

			else if (code == 46) {
				setStringValue("");
				setWorkingItems(getItems());
				target.add(this.listItemContainer);
				target.add(input);
				target.add(this.totalLabel);
			}

			else if (code == 127) {
				setStringValue("");
				setWorkingItems(getItems());
				target.add(this.listItemContainer);
				target.add(this.totalLabel);
				target.add(input);
			}

			else if (code == 37 || code == 39) {
				setStringValue("");
				setWorkingItems(getItems());
				target.add(this.listItemContainer);
				target.add(input);
			}

			else if (code >= 32 && code < 127)
				setStringValue(input.getValue() + String.valueOf(character).toLowerCase());

			if (getStringValue() != null && getStringValue().length() > 2) {
				List<IModel<T>> list = ListPanel.this.filter(getItems(), getStringValue());
				ListPanel.this.setWorkingItems(list);
				target.add(this.listItemContainer);
				target.add(this.totalLabel);
			}

		} catch (Exception e) {
			logger.error(e);
		}

	}

	protected void setWorkingItems(List<IModel<T>> list) {
		this.workingListModel = new ArrayList<IModel<T>>();
		list.forEach(item -> {
			workingListModel.add(item);
		});

	}

	protected List<IModel<T>> filter(List<IModel<T>> initialList, String filter) {
		List<IModel<T>> list = new ArrayList<IModel<T>>();
		final String str = filter.trim().toLowerCase();
		initialList.forEach(s -> {
			
			String t=ListPanel.this.getItemLabel(s).getObject();
			
			
			if ( t.toLowerCase().contains(str)) {
				list.add(s);
			}
		});
		return list;
	}

	
	protected Panel getListItemPanel(IModel<T> model) {
		return getListItemPanel(model, getListPanelMode());
	}

	protected Panel getListItemPanel(IModel<T> modelObject, ListPanelMode mode) {

		ListItemPanel<T> panel = new ListItemPanel<T>("row-element", modelObject, mode) {
			private static final long serialVersionUID = 1L;

			public IModel<String> getLabel() {
				return ListPanel.this.getItemLabel(modelObject);
			}

			protected void onClick() {
				ListPanel.this.onClick(modelObject);
			}
		};
		return panel;
	}

	protected void onClick(IModel<T> model) {
	}

	private void addTitle() {
		if ((this.title == null) && !isSettings()) {
			this.titleContainer = new InvisiblePanel("title-container");

		} else {
			this.titleContainer = new WebMarkupContainer("title-container");
			if (this.title == null) {
				this.titleContainer.add(new InvisiblePanel("title"));
				this.titleContainer.setVisible(false);
			} else {
				Label title = new Label("title", getTitle());
				title.setEscapeModelStrings(false);
				this.titleContainer.add(title);
			}
		}

		this.titleContainer.setOutputMarkupId(true);
		addOrReplace(this.titleContainer);

	}

	private void addLiveSearch() {

		if (liveSearch == null)
			setLiveSearch(true);

		if (!isLiveSearch()) {
			this.liveSearchContainer = new InvisiblePanel("liveSearchContainer");
			frame.addOrReplace(liveSearchContainer);
			return;
		}

		this.liveSearchContainer = new WebMarkupContainer("liveSearchContainer");
		this.liveSearchContainer.setOutputMarkupId(true);

		this.liveSearchTitle = new Label("live-search-title", getLiveSearchTitle());

		this.liveSearchTitleContainer = new WebMarkupContainer("liveSearchTitleContainer");
		this.liveSearchTitleContainer.setVisible(getLiveSearchTitle() != null);
		this.liveSearchTitleContainer.add(liveSearchTitle);
		this.liveSearchContainer.add(liveSearchTitleContainer);

		AjaxLink<Void> resetLink = new AjaxLink<Void>("reset") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				setWorkingItems(getItems());
				ListPanel.this.setStringValue("");
				target.add(ListPanel.this.input);
				target.add(ListPanel.this.totalLabel);
				target.add(ListPanel.this.listItemContainer);
			}
		};

		this.totalLabel = new Label("total", new PropertyModel<Integer>(this, "totalWorkingItems"));
		this.totalLabel.setOutputMarkupId(true);
		this.liveSearchContainer.add(this.totalLabel);

		this.initialTotalLabel = new Label("initialTotal", new PropertyModel<Integer>(this, "totalItems"));
		this.initialTotalLabel.setOutputMarkupId(true);
		this.liveSearchContainer.add(this.initialTotalLabel);

		this.liveSearchContainer.add(resetLink);

		this.form = new Form<>("form");
		this.form.setOutputMarkupId(true);

		this.liveSearchContainer.add(form);

		this.input = newTextField();

		this.input.add(new KeyboardBehavior(true) {
			private static final long serialVersionUID = 1L;

			protected void onKey(AjaxRequestTarget target, String jsKeycode) {
				ListPanel.this.onKey(target, jsKeycode);
			}
		});
		this.form.add(input);
		frame.addOrReplace(liveSearchContainer);
	}

	private void refresh(AjaxRequestTarget target) {
		target.add(this.titleContainer);
		target.add(this.settingsMenu);
		target.add(this.frame);
	}

	private void addSettingsTitleBar() {

		settingsMenu = new NavDropDownMenu<Void>("settings", null, null);
		settingsMenu.setOutputMarkupId(true);

		settingsMenu.setTitleCss("d-block-inline d-sm-block-inline d-md-block-inline d-lg-none d-xl-none d-xxl-none ps-1 pe-1");
		settingsMenu.setIconCss("fa-light fa-gear d-block-inline d-sm-block-inline d-md-block-inline d-lg-block-inline d-xl-block-inline d-xxl-block-inline ps-1 pe-1");
		settingsMenu.setVisible(isSettings());
		settingsMenu.addItem(new io.wktui.nav.menu.MenuItemFactory<Void>() {

			private static final long serialVersionUID = 1L;

			@Override
			public MenuItemPanel<Void> getItem(String id) {

				return new AjaxLinkMenuItem<Void>(id) {

					private static final long serialVersionUID = 1L;

					@Override
					public void onClick(AjaxRequestTarget target) {
						if (getListPanelMode() == ListPanelMode.TITLE_TEXT_IMAGE)
							setListPanelMode(ListPanelMode.TITLE);
						else
							setListPanelMode(ListPanelMode.TITLE_TEXT_IMAGE);
						addListView();
						refresh(target);
					}

					@Override
					public IModel<String> getLabel() {
						return getLabel("expanded-view");
					}

					public String getIconCssClass() {
						if (getListPanelMode() == ListPanelMode.TITLE)
							return null;
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
						setLiveSearch(!isLiveSearch());
						addLiveSearch();
						refresh(target);
					}

					public String getIconCssClass() {
						if (isLiveSearch())
							return "fa-solid fa-check";
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

	private void addListView() {

		this.listView = new PageableListView<IModel<T>>("list-items", new PropertyModel<List<IModel<T>>>(this, "workingItems"), getPageSize()) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<IModel<T>> item) {

				if (hasExpander()) {

					Panel mainPanel = getListItemPanel(item.getModelObject());
					ListItemExpanderPanel<T> panel = new ListItemExpanderPanel<T>("row-element", item.getModelObject(), mainPanel) {
						private static final long serialVersionUID = 1L;

						public WebMarkupContainer getExpandedPanel() {
							return ListPanel.this.getListItemExpandedPanel(item.getModelObject(), getListPanelMode());
						}
					};
					item.add(panel);
				} else {
					Panel panel = getListItemPanel(item.getModelObject());
					item.add(panel);
				}
				item.setOutputMarkupId(true);
			}
		};

		this.listItemContainer.addOrReplace(this.listView);

		ErrorPanel nitems = new ErrorPanel("noItems", null, getLabel("no-items")) {
			private static final long serialVersionUID = 1L;

			public boolean isVisible() {
				return getWorkingItems() != null && getWorkingItems().size() == 0;
			}

			@Override
			public String getCss() {
				return "pt-2 pb-2 alert border rounded-top-0";
			}
		};

		this.listItemContainer.addOrReplace(nitems);
	}

	private void setDefaults() {
		this.mode = ListPanelMode.TITLE;
		this.isSettings = true;
		liveSearch = Boolean.valueOf(false);
	}

}
