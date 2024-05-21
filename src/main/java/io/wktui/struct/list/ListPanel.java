package io.wktui.struct.list;

import java.util.List;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import wktui.base.BasePanel;
import wktui.base.InvisiblePanel;


public class ListPanel<T> extends BasePanel {

	private static final long serialVersionUID = 1L;
	
	private List<IModel<T>> listModel;
	private ListView<IModel<T>> listView;
	private IModel<String> title;
	
	private WebMarkupContainer titleContainer;
	private WebMarkupContainer listItemContainer;
	private WebMarkupContainer bottom;
	
	private WebMarkupContainer toolbar;
	
	private boolean hasExpander = false;
	
	
	public ListPanel(String id) {
		super(id);
		setOutputMarkupId(true);
	}
	
	
	@Override
	public void onInitialize() {
		super.onInitialize();
						
		this.toolbar 	= new InvisiblePanel("toolbar");
		this.bottom 	= new InvisiblePanel("bottom");
		
		addOrReplace(toolbar);
		addOrReplace(bottom);
		
		if (this.title==null) {		
			this.titleContainer = new InvisiblePanel("title-container");
		}
		else {
			this.titleContainer = new WebMarkupContainer("title-container");
			Label title = new Label("title", getTitle());
			this.titleContainer.add(title);	
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
				}					
				else {
					ListItemPanel<T> panel = new ListItemPanel<T>("row-element", item.getModelObject());
					item.add(panel);
				}
				
				// new Model<String>(item.getModelObject().getObject().toString())
				// LabelPanel p= new LabelPanel("row-element", new Model<String>(item.getModelObject().getObject().toString()));
				// item.add(getRowPanel("row-element", item.getModelObject(), item.getIndex()));
				
				
				item.setOutputMarkupId(true);
			}
		};
		this.listItemContainer.add(this.listView);
	}
	
	
	public boolean hasExpander() {
		return this.hasExpander;
	}
	
	public void setHasExpander( boolean b) {
		this.hasExpander=b;
	}
	
	public IModel<String> getTitle() {
		return this.title;
	}
	
	@Override
	public void onDetach() {
		super.onDetach();

		if (this.listModel!=null)
			this.listModel.forEach(i-> i.detach());
	}
	
	public void setListModel(List<IModel<T>> listModel) {
		this.listModel=listModel;
	}
	
	
	public List<IModel<T>> getItems() {
		return this.listModel;
	}
	

}
