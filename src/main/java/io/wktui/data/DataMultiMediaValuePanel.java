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

public class DataMultiMediaValuePanel<T> extends DataPanel<T> {

	private static final long serialVersionUID = 1L;

	static private Logger logger = Logger.getLogger( DataMultiMediaValuePanel.class.getName());

	private ListPanel<T> data;
	private IModel<String> placeHolder;
	private List<IModel<T>> items;
	
	
	public  DataMultiMediaValuePanel(String id ) {
		this(id, null, null);
	}

	public  DataMultiMediaValuePanel(String id, IModel<T> model) {
		this(id, model, null);
	}

	public DataMultiMediaValuePanel(String id, IModel<T> model, IModel<String> label) {
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
				return DataMultiMediaValuePanel.this.getObjectTitle(model);
			}

			@Override
			protected WebMarkupContainer getListItemExpandedPanel(IModel<T> model, ListPanelMode mode) {
				return DataMultiMediaValuePanel.this.getObjectListItemExpandedPanel(model, mode);
			}

			@Override
			protected Panel getListItemPanel(IModel<T> model) {

				
				DataMediaPanel<T> panel = new DataMediaPanel<T>("row-element", model) {
					
					private static final long serialVersionUID = 1L;

					@Override
					public IModel<String> getResourceTitleModel() {
						logger.debug("getResourceTitleModel -> " + DataMultiMediaValuePanel.this.getTitleModel(getModel()).getObject() );
						return DataMultiMediaValuePanel.this.getTitleModel(getModel());
					}

					@Override
					public IModel<String> getResourceSubtitleModel() {
						logger.debug("getResourceSubtitleModel -> " + DataMultiMediaValuePanel.this.getSubtitleModel(getModel()).getObject() );
						return DataMultiMediaValuePanel.this.getSubtitleModel(getModel());
					}
					
					@Override
					public String getFileUrl() {
						return DataMultiMediaValuePanel.this.getFileUrl(getModel());
					}
					
					@Override
					public String getIconCss() {
						return DataMultiMediaValuePanel.this.getIconCss(getModel());
					}
					

					//@Override
					//public void onClick() {
					//	DataMultiMediaValuePanel.this.onClick(getModel());
					//}

					//@Override
					//protected IModel<String> getInfo() {
				    //		return DataMultiMediaValuePanel.this.getObjectInfo(getModel());
					//}

					//@Override
					//protected WebMarkupContainer getObjectMenu() {
					//	return DataMultiMediaValuePanel.this.getMenu(getModel());
					//}

					//@Override
					//protected String getTitleIcon() {
				    //			return null;
					//}

				};
				return panel;
				
				
				/**
				
				DataObjectListItemPanel<T> panel = new DataObjectListItemPanel<T>("row-element", model, getListPanelMode()) {
					
					private static final long serialVersionUID = 1L;

					@Override
					protected IModel<String> getObjectTitle() {
						return DataMultiMediaValuePanel.this.getObjectTitle(getModel());
					}

					@Override
					protected String getImageSrc() {
						return DataMultiMediaValuePanel.this.getObjectImageSrc(getModel());
					}

					@Override
					public void onClick() {
						DataMultiMediaValuePanel.this.onClick(getModel());
					}

					@Override
					protected IModel<String> getInfo() {
						return DataMultiMediaValuePanel.this.getObjectInfo(getModel());
					}

					@Override
					protected WebMarkupContainer getObjectMenu() {
						return DataMultiMediaValuePanel.this.getMenu(getModel());
					}

					@Override
					protected String getTitleIcon() {
							return null;
					}

				};
				return panel;
				
				**/
				
				
			}

			@Override
			public List<IModel<T>> getItems() {
				return  DataMultiMediaValuePanel.this.getItems();
			}
		};

		
		super.addControl(data);

		data.setListPanelMode(ListPanelMode.TITLE);
		data.setLiveSearch(false);
		data.setSettings(true);
		data.setHasExpander(false);

		data.setOutputMarkupId(true);

		if (getTabIndex() > 0)
			data.add(new AttributeModifier("tabindex", getTabIndex()));

		if (getPlaceHolderLabel() != null && getPlaceHolderLabel().getObject() != null)
			data.add(new AttributeModifier("placeholder", getPlaceHolderLabel()));


		super.addControl(data);
	}


	private String getDefaultIconCss(String fileName) {
		 
		if ( FSUtil.isPdf(fileName)) 
			return FAIcons.PDF;
		
		if ( FSUtil.isExcel(fileName)) 
			return FAIcons.MS_EXCEL;
			
		if ( FSUtil.isWord(fileName)) 
			return FAIcons.MS_WORD;

		if ( FSUtil.isPowerpoint(fileName)) 
			return FAIcons.MS_POWERPOINT;

		if ( FSUtil.isCompressed(fileName)) 
			return FAIcons.COMPRESSED;

		if ( FSUtil.isAudio(fileName)) 
			return FAIcons.AUDIO;
			
		if ( FSUtil.isVideo(fileName)) 
			return FAIcons.VIDEO;
			
		if ( FSUtil.isText(fileName)) 
			return FAIcons.TEXT;
		
		return FAIcons.FILE;
		
	}
	
	protected String getIconCss(IModel<T> model) {
		return "fs-2 " + getDefaultIconCss( model.getObject().toString() );
	}
	

	protected IModel<String> getTitleModel(IModel<T> model) {
		return Model.of( model.getObject().toString() );
	}

	protected IModel<String> getSubtitleModel(IModel<T> model) {
		return Model.of( "subtitle - " + model.getObject().toString() );
	}

	
	protected String getFileUrl(IModel<T> model) {
		
		return null;
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


}