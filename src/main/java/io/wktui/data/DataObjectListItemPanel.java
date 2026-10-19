package io.wktui.data;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import io.wktui.panel.ObjectListItemPanel;
import io.wktui.struct.list.ListPanelMode;

public abstract class DataObjectListItemPanel<T> extends ObjectListItemPanel<T> {

	private static final long serialVersionUID = 1L;


	public DataObjectListItemPanel(String id, IModel<T> model, ListPanelMode mode) {
		super(id, model, mode);
	}

	public IModel<String> getObjectTitle() {
			return new Model<String>(getModel().getObject().toString());
	}

	@Override
	public boolean isEqual(T o1, T o2) {
		return o1.hashCode() == DataObjectListItemPanel.this.getModel().getObject().hashCode();
	}

	public IModel<String> getInfo() {
		return new Model<String>(getModel().getObject().toString());
	}

}
