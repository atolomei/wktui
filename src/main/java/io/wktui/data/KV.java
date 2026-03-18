package io.wktui.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.model.IDetachable;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class KV<K,V> implements IDetachable {

	public IModel<K>			keyModel;
	public List<IModel<V>> 		listValueModel;

	
	
	public KV(K key, List<V> listValue) {
		
		if (! (key instanceof Serializable))
			throw new IllegalArgumentException("key must be Serializable");
		
		this.keyModel = new Model((Serializable) key);
		
		this.listValueModel = new ArrayList<>();
		if (listValue!=null)
			listValue.forEach ( i ->  {
				
				if (! (i instanceof Serializable))
					throw new IllegalArgumentException("list must contain Serializable items");

				listValueModel.add(new Model((Serializable) i)); 
			});
	}
	
	
	
	public KV(IModel<K> keyModel, List<IModel<V>> listValueModel) {
		this.keyModel = keyModel;
		this.listValueModel = listValueModel;
	}
	
	@Override
	public void detach() {
		
		keyModel.detach();
		
		if (listValueModel!=null)
			for (IModel<V> m : listValueModel)
				m.detach();
	}

	
	public IModel<K> getKeyModel() {
		return keyModel;
	}
	
	public List<IModel<V>> getListValueModel() {
		return listValueModel;
	}
	
}