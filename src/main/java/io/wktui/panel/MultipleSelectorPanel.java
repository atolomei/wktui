package io.wktui.panel;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import io.wktui.struct.list.ListPanel;
import io.wktui.struct.list.ListPanelMode;
import wktui.base.BasePanel;
 
import wktui.base.LabelAjaxLinkPanel;

import wktui.base.Logger;

public abstract class MultipleSelectorPanel<T> extends BasePanel {

	@SuppressWarnings("unused")
	static private Logger logger = Logger.getLogger(MultipleSelectorPanel.class.getName());

	private static final long serialVersionUID = 1L;

	private List<IModel<T>> list;
	private WebMarkupContainer titleContainer;
	private WebMarkupContainer itemsContainer;
	private ListPanel<T> listPanel;
	private IModel<String> title;

	protected abstract IModel<String> getObjectSubtitle(IModel<T> model);
	protected abstract String getObjectImageSrc(IModel<T> model);
	protected abstract IModel<String> getObjectInfo(IModel<T> model);
	protected abstract void onClick(IModel<T> model);
	protected abstract void onObjectSelect(IModel<T> model, AjaxRequestTarget target);
	protected abstract boolean isEqual(T o1, T o2);

	
	
	public MultipleSelectorPanel(String id, List<IModel<T>> list) {
		this(id, list, null);
	}

	public MultipleSelectorPanel(String id, List<IModel<T>> list, IModel<String> title) {
		super(id);
		this.list = list;
		this.title = title;
		setOutputMarkupId(true);
	}

	@Override
	public void onInitialize() {
		super.onInitialize();

		itemsContainer = new WebMarkupContainer("itemsContainer");
		itemsContainer.setOutputMarkupId(true);
		add(itemsContainer);
		addTitle();
		renderPanel();
	}

	
	private void addTitle() {
		titleContainer = new WebMarkupContainer("titleContainer") {
			private static final long serialVersionUID = 1L;

			public boolean isVisible() {
				return getTitle()!=null;
			}
		};
		
		titleContainer.setOutputMarkupId(true);
		titleContainer.setVisible(getTitle() != null);
		add(titleContainer);
		titleContainer.add(( new Label("title", (getTitle()!=null)?getTitle():"")).setVisible(getTitle()!=null));
	}
	
	
	@Override
	public void onDetach() {
		super.onDetach();
		if (this.list != null)
			this.list.forEach(i -> i.detach());
	}

	public List<IModel<T>> getList() {
		return list;
	}

	public void setList(List<IModel<T>> list) {
		this.list = list;
	}

	protected boolean isExpander() {
		return true;
	}

	protected IModel<String> getObjectTitle(IModel<T> model) {
		return Model.of(model.getObject().toString());
	}

	protected WebMarkupContainer getObjectMenu(IModel<T> model) {
		LabelAjaxLinkPanel<T> b = new LabelAjaxLinkPanel<T>("menu", null, model, "fa-duotone fa-plus") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				onObjectSelect(getModel(), target);
			}
		};
		b.setLinkCss("btn btn-sm btn-outline-primary");
		return b;
	}

	private void renderPanel() {

		this.listPanel = new ListPanel<T>("items", getList()) {

			private static final long serialVersionUID = 1L;

			
			@Override
			public IModel<String> getItemLabel(IModel<T> model) {
				return MultipleSelectorPanel.this.getObjectTitle(model);
			}
			
			@Override
			protected Panel getListItemExpandedPanel(IModel<T> model, ListPanelMode mode) {
				return MultipleSelectorPanel.this.getObjectListItemExpandedPanel(model, mode);
			}

			@Override
			protected Panel getListItemPanel(IModel<T> model, ListPanelMode mode) {

				ObjectListItemPanel<T> panel = new ObjectListItemPanel<T>("row-element", model, mode) {

					private static final long serialVersionUID = 1L;

					
					
					@Override
					protected WebMarkupContainer getObjectMenu() {
						return MultipleSelectorPanel.this.getObjectMenu(getModel());
					}

					@Override
					public void onClick() {
						MultipleSelectorPanel.this.onClick(getModel());
					}

					protected IModel<String> getInfo() {
						return MultipleSelectorPanel.this.getObjectInfo(getModel());
					}

					protected IModel<String> getObjectTitle() {
						return MultipleSelectorPanel.this.getObjectTitle(getModel());
					}

					protected IModel<String> getObjectSubtitle() {
						if (getMode() == ListPanelMode.TITLE)
							return null;
						return MultipleSelectorPanel.this.getObjectSubtitle(getModel());
					}

					@Override
					protected String getImageSrc() {
						return MultipleSelectorPanel.this.getObjectImageSrc(getModel());
					}

					@Override
					protected boolean isEqual(T o1, T o2) {
						return MultipleSelectorPanel.this.isEqual(o1, o2);
					}

				};
				return panel;
			}
		};

		this.listPanel.setHasExpander(isExpander());
		this.listPanel.setSettings(true);
		this.listPanel.setLiveSearch(true);
		this.itemsContainer.addOrReplace(this.listPanel);
	}

	protected IModel<String> getTitle() {
		return title;
	}

	protected Panel getObjectListItemExpandedPanel(IModel<T> model, ListPanelMode mode) {

		return new ObjectListItemExpandedPanel<T>("expanded-panel", model, mode) {

			private static final long serialVersionUID = 1L;

			@Override
			protected IModel<String> getInfo() {
				return MultipleSelectorPanel.this.getObjectInfo(getModel());
			}

			@Override
			protected IModel<String> getObjectSubtitle() {
				return MultipleSelectorPanel.this.getObjectSubtitle(getModel());
			}

			@Override
			protected String getImageSrc() {
				return MultipleSelectorPanel.this.getObjectImageSrc(getModel());
			}
		};
	}

}
