package io.wktui.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import io.wktui.struct.list.ListPanel;
import io.wktui.struct.list.ListPanelMode;
import wktui.base.LabelPanel;
import wktui.base.Logger;

public class DataMultiValuePanel<T> extends DataPanel<T> {

	private static final long serialVersionUID = 1L;

	static private Logger logger = Logger.getLogger( DataMultiValuePanel.class.getName());

	private ListPanel<T> data;
	private IModel<String> placeHolder;
	private List<IModel<T>> items;
	
	
	public  DataMultiValuePanel(String id ) {
		this(id, null, null);
	}

	public  DataMultiValuePanel(String id, IModel<T> model) {
		this(id, model, null);
	}

	public DataMultiValuePanel(String id, IModel<T> model, IModel<String> label) {
		super(id, model, label);
		setOutputMarkupId(true);
	}

 

	@Override
	public void onInitialize() {
		super.onInitialize();
		
		this.data = new ListPanel<T>("data") {

			private static final long serialVersionUID = 1L;

			@Override
			public IModel<String> getItemLabel(IModel<T> model) {
				return DataMultiValuePanel.this.getObjectTitle(model);
			}

			@Override
			protected WebMarkupContainer getListItemExpandedPanel(IModel<T> model, ListPanelMode mode) {
				return DataMultiValuePanel.this.getObjectListItemExpandedPanel(model, mode);
			}

			@Override
			protected Panel getListItemPanel(IModel<T> model) {

				DataObjectListItemPanel<T> panel = new DataObjectListItemPanel<T>("row-element", model, getListPanelMode()) {
					private static final long serialVersionUID = 1L;

					@Override
					protected IModel<String> getObjectTitle() {
						return DataMultiValuePanel.this.getObjectTitle(getModel());
					}

					@Override
					protected String getImageSrc() {
						return DataMultiValuePanel.this.getObjectImageSrc(getModel());
					}

					@Override
					public void onClick() {
						DataMultiValuePanel.this.onClick(getModel());
					}

					@Override
					protected IModel<String> getInfo() {
						return DataMultiValuePanel.this.getObjectInfo(getModel());
					}

					@Override
					protected WebMarkupContainer getObjectMenu() {
						return DataMultiValuePanel.this.getMenu(getModel());
					}

					@Override
					protected String getTitleIcon() {
							return null;
					}

				};
				return panel;
			}

			@Override
			public List<IModel<T>> getItems() {
				return  DataMultiValuePanel.this.getItems();
			}
		};

		
		super.addControl(data);

		data.setListPanelMode(ListPanelMode.TITLE);
		data.setLiveSearch(false);
		data.setSettings(true);
		data.setHasExpander(false);
		data.setToolbarVisible(isToolbarVisible());
		

		data.setOutputMarkupId(true);

		if (getTabIndex() > 0)
			data.add(new AttributeModifier("tabindex", getTabIndex()));

		if (getPlaceHolderLabel() != null && getPlaceHolderLabel().getObject() != null)
			data.add(new AttributeModifier("placeholder", getPlaceHolderLabel()));


		super.addControl(data);
	}


	public boolean isToolbarVisible() {
		return this.toolbarVisible;
	}

	
	protected void onClick(IModel<T> model) {}

	protected WebMarkupContainer getMenu(IModel<T> model) {
		return null;
	}

	protected IModel<String> getObjectInfo(IModel<T> model) {
		return null;
	}

	protected String getObjectImageSrc(IModel<T> model) {
		return null;
	}

	protected WebMarkupContainer getObjectListItemExpandedPanel(IModel<T> model, ListPanelMode mode) {
		return null;
	}

	protected IModel<String> getObjectTitle(IModel<T> model) {
		return Model.of( model.getObject().toString() );
	}


	public void  setItems(List<IModel<T>> items) {
		this.items = items;
	}
		
	protected List<IModel<T>> getItems() {
		if (items == null)
			items = new ArrayList<IModel<T>>();
		return items;
	}

	@Override
	public void onConfigure() {
		super.onConfigure();
	}
	 

	@Override
	public Component getData() {
		return data;
	}

	public void onDetach() {
		super.onDetach();
		if (items != null)
			items.forEach(i -> i.detach());
	}
	

	@Override
	public void updateModel() {

		Object val = null;
		boolean updated = false;

		try {

			if (getModel() == null) {
				logger.warn("model is null for id -> " + getId());
				return;
			}
			logger.debug("update -> " + getId() + ": " + (val != null ? val.toString() : "null") + " | updated -> " + updated);

		} catch (Exception e) {
			getModel().detach();
		}  
	}
	
	
	protected String getInputType() {
		return "text";
	}

	@Override
	public void reload() {
		//setValue(getModel().getObject());
	}

	protected IModel<String> getPlaceHolderLabel() {
		return this.placeHolder;
	}

	public void setPlaceHolderLabel(IModel<String> label) {
		this.placeHolder = label;
	}

	boolean toolbarVisible = true;
	
	public void setToolbarVisible(boolean b) {
		 toolbarVisible = b;
		 if (data!=null)
			 data.setToolbarVisible(b);
		 
	}	


}