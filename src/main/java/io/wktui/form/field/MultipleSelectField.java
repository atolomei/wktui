package io.wktui.form.field;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
 
import io.wktui.panel.MultipleSelectorPanel;
import io.wktui.panel.ObjectListItemPanel;
import io.wktui.struct.list.ListPanel;
import io.wktui.struct.list.ListPanelMode;
import wktui.base.LabelAjaxLinkPanel;
import wktui.base.Logger;

public abstract class MultipleSelectField<T> extends Field<T> {

	private static final long serialVersionUID = 1L;

	static private Logger logger = Logger.getLogger(MultipleSelectField.class.getName());

	private List<IModel<T>> list;
	private List<IModel<T>> selected;

	private MultipleSelectorPanel<T> multipleSeletor;
	private ListPanel<T> selectedPanel;
	
	private AjaxLink<Void> add;
	private AjaxLink<Void> close;
	private WebMarkupContainer addContainerButtons;
	private boolean addEnabled = false;
	private int hashSelectedInitial = -1;

	public MultipleSelectField(String id, List<IModel<T>> selected, IModel<String> label, List<IModel<T>> choices) {
		super(id, null, label);
		setSelected(selected);
		setList(choices);
		setOutputMarkupId(true);
	}

	public List<IModel<T>> getList() {
		return list;
	}

	public List<IModel<T>> getSelected() {
		return selected;
	}

	public void setList(List<IModel<T>> list) {
		this.list = list;
	}

	public void setSelected(List<IModel<T>> selected) {
		this.selected = selected;
	}

	@Override
	public void onDetach() {
		super.onDetach();

		if (this.list != null)
			this.list.forEach(t -> t.detach());

		if (selected != null)
			this.selected.forEach(t -> t.detach());
	}

	@Override
	public void reload() {
		logger.error("reload not done");
	}

	@Override
	public void editOn() {
		setUpdated(false);
		super.editOn();
	}

	@Override
	public void editOff() {
		super.editOff();
		if (this.addEnabled) {
			addEnabled = false;
		}
	}

	@Override
	public void onInitialize() {
		super.onInitialize();

		setUpdated(false);

		StringBuilder str = new StringBuilder();
		getSelected().forEach(i -> str.append(String.valueOf(i)));
		hashSelectedInitial = str.toString().hashCode();

		addSelectedPanel();
		addMultipleSelector();
	}

	public String getNullValidDisplayValue() {
		return "None";
	}

	public void onUpdate(AjaxRequestTarget target) {
	}
	
	@Override
	public void updateModel() {

		try {
			StringBuilder str = new StringBuilder();
			getSelected().forEach(i -> str.append(String.valueOf(i)));
			
			int newHashSelectedInitial = str.toString().hashCode();
			boolean isChanged = (newHashSelectedInitial != hashSelectedInitial);
			setUpdated(isChanged);

			if (isChanged) {
				if (getModel()!=null)
					onUpdate(getModel().getObject(), null);
				else
					onUpdate(null, null);
			}

		} catch (Exception e) {
			logger.error(e, getInput() != null ? getInput().toString() : "");
			if (getModel()!=null)
				getModel().detach();
		}
	}

	protected void onUpdate(T oldvalue, T newvalue) {
		if (getEditor() != null) {
			getEditor().setUpdatedPart(getPart());
		}
	}

	protected String getPart() {
		return getFieldUpdatedPartName();
	}

	protected String getInputType() {
		return null;
	}

	protected void resetSelected() {
		this.selected = null;
	}

	@Override
	protected void addListeners() {
		super.addListeners();
	}

	


	protected String getDisplayValue(T value) {
		if (value == null)
			return null;
		return value.toString();
	}

	protected boolean equals(T value, Object object) {
		return value != null && object != null && value.equals(object);
	}

	protected String getIdValue(T value) {
		if (value == null)
			return null;
		return value.toString();
	}

	protected WebMarkupContainer getObjectMenu(IModel<T> model) {
		
		if (!this.isEditEnabled())
			return null;
		
		LabelAjaxLinkPanel<T> b = new LabelAjaxLinkPanel<T>("menu", null, model, "fa-duotone fa-minus") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				onObjectRemove(getModel(), target);
			}
		};
		b.setLinkCss("btn btn-sm btn-outline-primary");
		return b;
	}

	protected void addSelectedPanel() {

		this.selectedPanel = new ListPanel<T>("items") {

			private static final long serialVersionUID = 1L;

			protected boolean isToolbar() {
				return false;
			}

			@Override
			protected WebMarkupContainer getListItemExpandedPanel(IModel<T> model, ListPanelMode mode) {
				return MultipleSelectField.this.getObjectListItemExpandedPanel(model, mode);

			}

			@Override
			protected Panel getListItemPanel(IModel<T> model) {

				ObjectListItemPanel<T> panel = new ObjectListItemPanel<T>("row-element", model, getListPanelMode()) {
					private static final long serialVersionUID = 1L;

					@Override
					protected IModel<String> getObjectTitle() {
						return MultipleSelectField.this.getObjectTitle(getModel());
					}

					@Override
					protected String getImageSrc() {
						return null;
						// return MultipleSelectField.this.getObjectImageSrc(getModel());
					}

					@Override
					public void onClick() {
						// setResponsePage(new ArtExhibitionItemPage(getModel(),
						// ArtExhibitionItemsPanel.this.getItems()));
					}

					@Override
					protected IModel<String> getInfo() {
						return MultipleSelectField.this.getObjectInfo(getModel());
					}

					@Override
					protected WebMarkupContainer getObjectMenu() {
						return MultipleSelectField.this.getObjectMenu(getModel());
					}

					@Override
					protected String getTitleIcon() {
						return null;
					}

					@Override
					protected boolean isEqual(T o1, T o2) {
						return MultipleSelectField.this.isEqual(o1, o2);
					}
				};
				return panel;
			}

			@Override
			public List<IModel<T>> getItems() {
				return MultipleSelectField.this.getSelected();
			}
		};
		super.addControl(selectedPanel);

		selectedPanel.setListPanelMode(ListPanelMode.TITLE);
		selectedPanel.setLiveSearch(false);
		selectedPanel.setSettings(false);
		selectedPanel.setHasExpander(false);
	}

	protected IModel<String> getObjectTitle(IModel<T> model) {
		return Model.of(model.getObject().toString());
	}

	protected WebMarkupContainer getObjectListItemExpandedPanel(IModel<T> model, ListPanelMode mode) {
		return null;
	}

	private void addMultipleSelector() {

		this.addContainerButtons = new WebMarkupContainer("addContainerButtons");
		this.addContainerButtons.setOutputMarkupId(true);

		super.addControl(this.addContainerButtons);

		this.add = new AjaxLink<Void>("add") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				// setState(FormState.EDIT);
				addEnabled = true;
				target.add(addContainerButtons);
			}

			public boolean isVisible() {
				return isEditEnabled() && (!addEnabled);
			}
		};

		this.close = new AjaxLink<Void>("close") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				// setState(FormState.VIEW);
				addEnabled = false;
				target.add(addContainerButtons);
				target.add(multipleSeletor);
			}

			public boolean isVisible() {
				return addEnabled;
			}
		};

		this.addContainerButtons.add(add);
		this.addContainerButtons.add(close);

		this.multipleSeletor = new MultipleSelectorPanel<T>("multipleSelector", getList()) {

			private static final long serialVersionUID = 1L;

			protected IModel<String> getTitle() {
				return MultipleSelectField.this.getTitle();
			}

			public boolean isVisible() {
				return addEnabled;
			}

			@Override
			protected IModel<String> getObjectTitle(IModel<T> model) {
				return MultipleSelectField.this.getObjectTitle(model);
			}

			@Override
			protected void onClick(IModel<T> model) {
				MultipleSelectField.this.onClick(model);
			}

			@Override
			protected void onObjectSelect(IModel<T> model, AjaxRequestTarget target) {
				MultipleSelectField.this.onObjectSelect(model, target);
			}

			@Override
			protected IModel<String> getObjectInfo(IModel<T> model) {
				return MultipleSelectField.this.getObjectInfo(model);
			}

			@Override
			protected IModel<String> getObjectSubtitle(IModel<T> model) {
				return MultipleSelectField.this.getObjectSubtitle(model);
			}

			@Override
			protected boolean isExpander() {
				return true;
			}

			@Override
			protected String getObjectImageSrc(IModel<T> model) {
				return null;
			}

			@Override
			protected boolean isEqual(T o1, T o2) {
				return MultipleSelectField.this.isEqual(o1, o2);
			}
		};

		this.multipleSeletor.setOutputMarkupId(true);
		this.addContainerButtons.add(this.multipleSeletor);
	}

	protected void onClick(IModel<T> model) {
	}

	protected boolean isEqual(T o1, T o2) {
		return o1.hashCode()==o2.hashCode();
	}

	protected IModel<String> getTitle() {
		return null;
	}

	protected IModel<String> getObjectSubtitle(IModel<T> model) {
		return null;
	}

	protected IModel<String> getObjectInfo(IModel<T> model) {
		return null;
	}

	protected void onObjectRemove(IModel<T> model, AjaxRequestTarget target) {
		selected.remove(model);
		target.add(MultipleSelectField.this);
	}

	protected void onObjectSelect(IModel<T> model, AjaxRequestTarget target) {
		selected.add(model);
		target.add(MultipleSelectField.this);
	}

}
