package io.wktui.struct.list;

import java.util.List;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import wktui.base.BasePanel;
import wktui.base.InvisiblePanel;


public class SimpleGridPanel<T> extends BasePanel {

	private static final long serialVersionUID = 1L;
	
	private List<IModel<T>> listModel;
	private ListView<IModel<T>> listView;
	private IModel<String> title;
	
	private WebMarkupContainer titleContainer;
	private WebMarkupContainer listItemContainer;
	private WebMarkupContainer bottom;
	
	private WebMarkupContainer toolbar;
	
	
	public SimpleGridPanel(String id) {
		super(id);
		setOutputMarkupId(true);
	}
	
	public SimpleGridPanel(String id, List<IModel<T>> list) {
        super(id);
        setOutputMarkupId(true);
        setListModel(list);
    }
	
	
	@Override
	public void onInitialize() {
		super.onInitialize();
						
		this.toolbar 	= new InvisiblePanel("toolbar");
		this.bottom 	= new InvisiblePanel("bottom");
		
		addOrReplace(toolbar);
		addOrReplace(bottom);
		
		if (this.title==null) 		
			this.titleContainer = new InvisiblePanel("title-container");
		
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
				    Panel panel = getListItemPanel(item.getModelObject());
					item.add(panel);
				item.setOutputMarkupId(true);
			}
		};
		this.listItemContainer.add(this.listView);
	}
	
	
	protected Panel getListItemPanel(IModel<T> modelObject) {
	    ListItemPanel<T> panel = new ListItemPanel<T>("row-element", modelObject) {
            private static final long serialVersionUID = 1L;
            public IModel<String> getLabel() {
                return SimpleGridPanel.this.getItemLabel(modelObject);
            }
            protected void onClick() {
                SimpleGridPanel.this.onClick(modelObject);
            }
        };
        return panel;
    }

    protected void onClick(IModel<T> model) {
    }

    public IModel<String> getItemLabel(IModel<T> model) {
        return new Model<String>(model.getObject().toString());
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
