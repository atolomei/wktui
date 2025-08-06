package io.wktui.struct.list;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import io.wktui.form.button.EditButtons;

import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;

import wktui.base.BasePanel;
import wktui.base.InvisiblePanel;
import wktui.base.Logger;

/**
 * 
 * liveSearch
 * 
 * viewmode -> image y.n, text y.n
 * 
 * 
 * @param <T>
 */
public class ListPanel<T> extends BasePanel {

	private static final long serialVersionUID = 1L;

	static private Logger logger = Logger.getLogger(ListPanel.class.getName());

	static final public int MINIMUN_SIZE_FOR_LIVE_SEARCH = 5;
	
	private List<IModel<T>> listModel;
	private List<IModel<T>> initialListModel;
	
	private ListView<IModel<T>> listView;

	private IModel<String> title;

	private WebMarkupContainer titleContainer;
	private WebMarkupContainer listItemContainer;
	private WebMarkupContainer bottom;
	private WebMarkupContainer liveSearchContainer;
	private WebMarkupContainer toolbar;
	private WebMarkupContainer liveSearchTitleContainer;

	private Form<?> form;
	private Label totalLabel;
	private Label initialTotalLabel;
	private Label liveSearchTitle;
	private String stringValue;

	private org.apache.wicket.markup.html.form.TextField<?> input;

	private boolean hasExpander = false;
	
	private ListPanelMode mode = ListPanelMode.IMAGE_TITLE_TEXT;
	private Boolean liveSearch;
	
	
	public ListPanel(String id) {
		super(id);
		setOutputMarkupId(true);
	}

	public ListPanel(String id, List<IModel<T>> list) {
		super(id);
		setOutputMarkupId(true);
		setListModel(list);
	}

	public List<IModel<T>> getList() {
		return this.listModel;
	}


	public String getStringValue() {
		return stringValue;
	}

	public void setStringValue(String value) {
		this.stringValue = value;
	}
	public Integer getTotalItems() {
		return Integer.valueOf( getItems().size());
	}
	
	public Integer getInitialTotalItems() {
		return Integer.valueOf( getInitialItems().size());
	}
	

	protected org.apache.wicket.markup.html.form.TextField<?> newTextField() {

		org.apache.wicket.markup.html.form.TextField<?> input = new org.apache.wicket.markup.html.form.TextField<String>(
				"input", new PropertyModel<String>(this, "stringValue")) {

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

	private void addLiveSearch() {
		
		if (liveSearch==null)
			setLiveSearch( getItems().size() >= MINIMUN_SIZE_FOR_LIVE_SEARCH );
		
		if (!isLiveSearch()) {
			this.liveSearchContainer = new InvisiblePanel("liveSearchContainer");
			addOrReplace(liveSearchContainer);
			return;
		}
		
		this.liveSearchContainer = new WebMarkupContainer("liveSearchContainer");
		this.liveSearchContainer.setOutputMarkupId(true);

		this.liveSearchTitle = new Label("live-search-title", getLiveSearchTitle());

		this.liveSearchTitleContainer =  new WebMarkupContainer("liveSearchTitleContainer");
		this.liveSearchTitleContainer.setVisible(getLiveSearchTitle()!=null);
		this.liveSearchTitleContainer.add(liveSearchTitle);
		this.liveSearchContainer.add(liveSearchTitleContainer);
		
		this.initialListModel = new ArrayList<IModel<T>>();
		getItems().forEach( item -> {initialListModel.add(item);});
		
		AjaxLink<Void> resetLink = new AjaxLink<Void>("reset") {
			private static final long serialVersionUID = 1L;
			@Override
			public void onClick(AjaxRequestTarget target) {
					setItems(getInitialItems());
					ListPanel.this.setStringValue("");
					target.add(ListPanel.this.input);
					target.add(ListPanel.this.totalLabel);
					target.add(ListPanel.this.listItemContainer);
			}
		};
		
		this.totalLabel = new Label("total", new PropertyModel<Integer>(this, "totalItems"));
		this.totalLabel.setOutputMarkupId(true);			
		this.liveSearchContainer.add(this.totalLabel);
		this.initialTotalLabel = new Label("initialTotal", new PropertyModel<Integer>(this, "initialTotalItems"));
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
		addOrReplace(liveSearchContainer);
	}

	
	@Override
	public void onInitialize() {
		super.onInitialize();
		
		
		
		
		
		this.toolbar = new InvisiblePanel("toolbar");
		this.bottom = new InvisiblePanel("bottom");

		addOrReplace(toolbar);
		addOrReplace(bottom);

		
		addLiveSearch();
		
		
		if (this.title == null) {
			this.titleContainer = new InvisiblePanel("title-container");
	
		} else {
			this.titleContainer = new WebMarkupContainer("title-container");
			if (this.title == null) {
				this.titleContainer.add(new InvisiblePanel("title"));

			} else {
				Label title = new Label("title", getTitle());
				this.titleContainer.add(title);
			}
			/**
			 * settingsLink = new AjaxLink<Void>("settings") { private static final long
			 * serialVersionUID = 1L; public boolean isVisible() { return isSettings(); }
			 * 
			 * @Override public void onClick(AjaxRequestTarget target) { } };
			 *           this.titleContainer.add(settingsLink);
			 **/
		}

		addOrReplace(this.titleContainer);

		this.listItemContainer = new WebMarkupContainer("list-items-container");
		addOrReplace(this.listItemContainer);

		this.listView = new ListView<IModel<T>>("list-items", new PropertyModel<List<IModel<T>>>(this, "items")) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<IModel<T>> item) {

				if (hasExpander()) {
					ListItemExpanderPanel<T> panel = new ListItemExpanderPanel<T>("row-element", item.getModelObject());
					item.add(panel);
				} else {

					Panel panel = getListItemPanel(item.getModelObject());

					item.add(panel);
				}
				item.setOutputMarkupId(true);
			}
		};
		
		
		this.listItemContainer.add(this.listView);
		this.listItemContainer.setOutputMarkupId(true);
	}


	
	// public void setSettings(boolean settings) {
//		this.isSettings=settings;
	// }

	// public boolean isSettings() {
//		return this.isSettings;
	// }

	
	protected IModel<String> getLiveSearchTitle() {
		return getLabel("live-search");
	}

	protected void onKey(AjaxRequestTarget target, String jsKeycode) {

		try {

			Integer code = Integer.valueOf(jsKeycode);
			char character = (char) code.intValue();
			
			
			if (code==13) {
				setStringValue("");
				setItems(getInitialItems());
		
				target.add(this.listItemContainer);
				target.add(input);
				target.add(this.totalLabel);
			}
			
			
			else if (code==8 && input.getValue()!=null && input.getValue().length()>0) {
				setStringValue(input.getValue().substring(0, input.getValue().length()-1));

				target.add(this.listItemContainer);
				target.add(this.totalLabel);
			
			}
			// delete
			// left, right, up, down
			// back
			
			else if (code==46) {
				setStringValue("");
				setItems(getInitialItems());
				target.add(this.listItemContainer);
				target.add(input);
				target.add(this.totalLabel);
			}
			
			else if (code==127) {
				setStringValue("");
				setItems(getInitialItems());
				target.add(this.listItemContainer);
				target.add(this.totalLabel);
				target.add(input);
			}
			
			else if (code==37 || code==39) {
				setStringValue("");
				setItems(getInitialItems());
				target.add(this.listItemContainer);
				target.add(input);
			}

			if (code>=32 && code<127) 
				setStringValue(input.getValue() + String.valueOf(character).toLowerCase());

			if (getStringValue().length()>2) {
				List<IModel<T>> list = ListPanel.this.filter(getInitialItems(), getStringValue());
				ListPanel.this.setItems(list);
				target.add(this.listItemContainer);
				target.add(this.totalLabel);
			}
			
			
			
		} catch (Exception e) {
			logger.error(e);
		}
		
	}

	protected void setItems(List<IModel<T>> list) {
		this.listModel=list;
	}
	
	protected List<IModel<T>> getInitialItems() {
		return this.initialListModel;
	}

	protected List<IModel<T>> filter(List<IModel<T>> initialList, String filter) {
		return getItems();
	}

	
	protected Panel getListItemPanel(IModel<T> modelObject) {
		ListItemPanel<T> panel = new ListItemPanel<T>("row-element", modelObject) {
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

	public IModel<String> getItemLabel(IModel<T> model) {
		return new Model<String>(model.getObject().toString());
	}

	public boolean hasExpander() {
		return this.hasExpander;
	}

	public void setHasExpander(boolean b) {
		this.hasExpander = b;
	}

	public IModel<String> getTitle() {
		return this.title;
	}

	@Override
	public void onDetach() {
		super.onDetach();

		if (this.listModel != null)
			this.listModel.forEach(i -> i.detach());
		
		
		if (this.initialListModel != null)
			this.initialListModel.forEach(i -> i.detach());
		
	}

	public void setListModel(List<IModel<T>> listModel) {
		this.listModel = listModel;
	}

	public List<IModel<T>> getItems() {
		return this.listModel;
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

	protected void onClick(IModel<T> model) {
	}


}

