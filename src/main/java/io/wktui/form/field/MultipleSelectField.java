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
import wktui.base.Logger;

public class MultipleSelectField<T> extends Field<T> {

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




	private static final long serialVersionUID = 1L;

	static private Logger logger = Logger.getLogger(MultipleSelectField.class.getName());
	
	private List<IModel<T>> list;
	private List<IModel<T>> selected;
	
	private MultipleSelectorPanel<T> multipleSeletor;
	
	private AjaxLink<Void> add;
	private AjaxLink<Void> close;
	private WebMarkupContainer addContainerButtons;
	
	private ListPanel<T> selectedPanel;
	
	
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

	public MultipleSelectField(String id, IModel<T> model) {
	    this(id, model, null);
	}
	
	public MultipleSelectField(String id, IModel<T> model, IModel<String> label) {
		super(id, model, label);
		setOutputMarkupId(true);
	}

	@Override
	public void editOn() {
	 
		super.editOn();
	}

	@Override
	public void editOff() {
		super.editOff();
	}
	
	protected void resetSelected() {
		this.selected = null;
	}

	@Override
	protected void addListeners() {
		super.addListeners();
	}
	
	
	@Override
	public void updateModel() {
	
        List<String> vals = null;
        
        try {

            if (getModel()==null) {
            	logger.warn("model is null for id -> " + getId());
            	return;
            }
 
      
	       
        }
        catch (Exception e) {
            logger.error(e,  getInput()!=null? getInput().toString(): "");
            getModel().detach();
        }
		
		
		
	}

	protected void onUpdate(T oldvalue, T newvalue) {
				if (getEditor()!=null) {
					getEditor().setUpdatedPart(getPart());
				}		
	}
	 
	protected String getPart() {
	   	return getFieldUpdatedPartName();
	}
	 
	  protected String getInputType() {
	        return null;
	  }
	
	@Override
	public void onInitialize() {
		super.onInitialize();
	
		 
 
	}

	
	
	 

	protected String getIdValue(T value) {
		
		if (value==null)
			return null;
		
	 
		return value.toString();
	}
	
	
	 

	
	public String getNullValidDisplayValue() {
		return "None";
	}
	
	public void onUpdate(AjaxRequestTarget target) {
	}
	
	 
	
	protected String getDisplayValue(T value) {
		if (value==null)
			return null;
		return value.toString();
	}

	protected boolean equals(T value, Object object) {
		return value!=null && object!=null && value.equals(object);
	}


	
	/**
	 * 
	 * 
	 * 
	 */

	protected void addSelectedPanel() {

		this.selectedPanel = new ListPanel<T>("items") {

			private static final long serialVersionUID = 1L;
 
			@Override
			protected WebMarkupContainer getListItemExpandedPanel(IModel<T> model, ListPanelMode mode) {
				return MultipleSelectField.this.getObjectListItemExpandedPanel(model, mode);

			}

			@Override
			protected Panel getListItemPanel(IModel<T> model) {

				 ObjectListItemPanel<T> panel = new  ObjectListItemPanel<T>("row-element", model, getListPanelMode()) {
					private static final long serialVersionUID = 1L;

					@Override
					protected IModel<String> getObjectTitle() {
						return Model.of("");
						//	return ArtExhibitionItemsPanel.this.getObjectTitle(getModel());
					}

					@Override
					protected String getImageSrc() {
						return null;
						//return MultipleSelectField.this.getObjectImageSrc(getModel());
					}

					@Override
					public void onClick() {
						//setResponsePage(new ArtExhibitionItemPage(getModel(), ArtExhibitionItemsPanel.this.getItems()));
					}

					@Override
					protected IModel<String> getInfo() {
						return null;
						//		return MultipleSelectField.this.getObjectInfo(getModel());
					}

					@Override
					protected WebMarkupContainer getObjectMenu() {
						//return ArtExhibitionItemsPanel.this.getMenu(getModel());
						return null;
					}

					@Override
					protected String getTitleIcon() {
						 
							return null;
					}

					@Override
					protected boolean isEqual(T o1, T o2) {
						// TODO Auto-generated method stub
						return false;
					}

				};
				return panel;
			}

			@Override
			public List<IModel<T>> getItems() {
				return MultipleSelectField.this.getList();
			}
		};
		add(selectedPanel);

		selectedPanel.setListPanelMode(ListPanelMode.TITLE);
		selectedPanel.setLiveSearch(false);
		selectedPanel.setSettings(true);
		selectedPanel.setHasExpander(true);
	}
	
	
	
	
	
	protected WebMarkupContainer getObjectListItemExpandedPanel(IModel<T> model, ListPanelMode mode) {
		return null;
	}

	
	private void addMultipleSelector() {

		this.addContainerButtons = new WebMarkupContainer("addContainerButtons");
		this.addContainerButtons.setOutputMarkupId(true);

		add(this.addContainerButtons);

		this.add = new AjaxLink<Void>("add") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				//setState(FormState.EDIT);
				target.add(addContainerButtons);
			}

			public boolean isVisible() {
				return true;	
				//return getState() == FormState.VIEW;
			}
		};

		this.close = new AjaxLink<Void>("close") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				//setState(FormState.VIEW);
				target.add(addContainerButtons);
				target.add(multipleSeletor);
			}

			public boolean isVisible() {
				return true;
				//return getState() == FormState.EDIT;
			}
		};

		this.addContainerButtons.add(add);
		this.addContainerButtons.add(close);

		this.multipleSeletor = new MultipleSelectorPanel<T>("multipleSelector", getList()) {

			private static final long serialVersionUID = 1L;

			protected IModel<String> getTitle() {
				return getLabel("artworks");
			}

			public boolean isVisible() {
				return true;
			}

			@Override
			protected void onClick(IModel<T> model) {
				// TODO Auto-generated method stub
			}

			@Override
			protected void onObjectSelect(IModel<T> model, AjaxRequestTarget target) {
				//ArtExhibitionItemsPanel.this.onObjectSelect(model, target);
			}

			@Override
			protected IModel<String> getObjectInfo(IModel<T> model) {
				return Model.of( "" );
				//String str = TextCleaner.clean(model.getObject().getInfo(), 280);
				//return new Model<String>(str);
			}

			@Override
			protected IModel<String> getObjectSubtitle(IModel<T> model) {
				return Model.of("getArtistStr(model.getObject())");
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
				// TODO Auto-generated method stub
				return false;
			}
		};

		this.multipleSeletor.setOutputMarkupId(true);

		this.addContainerButtons.add(this.multipleSeletor);
	}

	
	
	
	
}
