package io.wktui.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import io.wktui.struct.list.ListPanel;
import io.wktui.struct.list.ListPanelMode;
import wktui.base.LabelPanel;
import wktui.base.Logger;

public class DataGridMultiValuePanel<K,V> extends DataPanel<KV<K,V>> {

	private static final long serialVersionUID = 1L;

	static private Logger logger = Logger.getLogger( DataGridMultiValuePanel.class.getName());

	private  ListView<KV<K,V>> listView;

	
	private List<KV<K,V>> items;
	
	
	public DataGridMultiValuePanel(String id ) {
		super(id, null, null);
		setOutputMarkupId(true);
	}
	
	
	public DataGridMultiValuePanel(String id,   IModel<String> label) {
		super(id, null, label);
		setOutputMarkupId(true);
	}
	public void onDetach() {
		super.onDetach();
		
		if (items!=null)
			items.forEach( i-> i.detach());
	}
	
	
	@Override
	public void onInitialize() {
		super.onInitialize();
		addListView();
	}
	
	@Override
	public void reload() {
	}
	
	private void addListView() {

		/**
		this.listView = new   ListView< IModel< KV<K,V> >  >("rows", new PropertyModel<    List<   IModel< KV<K,V>> > >(this, "items"), getPageSize()) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<IModel<KV<K,V>>> item) {
			}
		};
		**/

		WebMarkupContainer main = new WebMarkupContainer("main");
		
		WebMarkupContainer table = new WebMarkupContainer("table");
		main.add(table);
		
		WebMarkupContainer th = new WebMarkupContainer("tableHead") {
		
			public boolean isVisible() {
				return getKeyLabel() != null || getValueLabel() != null;
			}
		};
		
		table.add(th);
		

		Label l_key 	= new Label("keyLabel", getKeyLabel()		);
		Label l_value	= new Label("valueLabel", getValueLabel() 	);

		th.add(l_key);
		th.add(l_value);
		
		
		
		this.listView = new ListView< KV<K,V>  >("rows", new PropertyModel<    List<  KV<K,V> > >(this, "items") ) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem< KV<K,V> > item) {
				
				
				
				KV<K,V> kv = item.getModelObject();
				
				
				Label l_key = new Label("key", new Model<>(kv.getKeyModel().getObject().toString()));

				
				List<String> values = new ArrayList<>();
				
				kv.getListValueModel().forEach( v -> 
					values.add( v.getObject().toString()));
						
				String val = String.join( ", ", values );
				
				Label l_values = new Label("value", Model.of(val));

				item.add(l_key);
				item.add( l_values);
			}
		};
		
		table.add(listView);
		
		super.addControl( main );
		
	}


	protected IModel<String> getValueLabel() {
		// TODO Auto-generated method stub
		return null;
	}


	protected IModel<String> getKeyLabel() {
		return null;
	}

	
	public List<KV<K, V>> getItems() {
		return items;
	}


	public void setItems(List<KV<K, V>> items) {
		this.items = items;
	}


	//protected long getPageSize() {
	// 	return 30;
	//}
	
 


}