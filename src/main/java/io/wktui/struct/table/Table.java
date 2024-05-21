package io.wktui.struct.table;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import io.wktui.struct.list.ListItemPanel;
import wktui.base.BasePanel;
import wktui.base.ModelPanel;

public class Table<T> extends ModelPanel<T> {

	private static final long serialVersionUID = 1L;
	
	private ListView<IModel<T>> listView;
	
	public Table(String id, IModel<T> model) {
		super(id, model);

	}
	
	
	@Override
	public void onInitialize() {
		super.onInitialize();
		
		add(new TableHeader<T>(getModel()));
		
		this.listView = new ListView<IModel<T>>("list-items", new PropertyModel<List<IModel<T>>>(this, "items")) {
			private static final long serialVersionUID = 1L;
			@Override
			protected void populateItem(ListItem<IModel<T>> item) {
				
				//TableRow<T> panel = new TableRow<T>("row");
				
				//new Model<String>(item.getModelObject().getObject().toString())
				//LabelPanel p= new LabelPanel("row-element", new Model<String>(item.getModelObject().getObject().toString()));
				// item.add(getRowPanel("row-element", item.getModelObject(), item.getIndex()));
				
				//item.add(panel);
				//item.setOutputMarkupId(true);
			}
		};

		
		
		
	}
	
	
	
	
	
	
	@Override
	public void onDetach() {
		super.onDetach();

	
	}
	
		
}
