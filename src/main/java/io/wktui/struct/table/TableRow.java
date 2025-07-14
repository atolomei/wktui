package io.wktui.struct.table;

import org.apache.wicket.model.IModel;

import wktui.base.BasePanel;
import wktui.base.ModelPanel;

public class TableRow<T> extends ModelPanel<T> {

	public TableRow(String id, IModel<T> model) {
		super(id, model);
	}
	
	public TableRow(IModel<T> model) {
		super("row", model);
	}

	
}
