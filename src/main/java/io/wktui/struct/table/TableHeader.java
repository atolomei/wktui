package io.wktui.struct.table;

import org.apache.wicket.model.IModel;

import wktui.base.BasePanel;
import wktui.base.ModelPanel;

public class TableHeader<T> extends ModelPanel<T> {
	
	private static final long serialVersionUID = 1L;

	public TableHeader(IModel<T> model) {
		super("header", model);
	}
	
	public TableHeader(String id, IModel<T> model) {
		super(id, model);
	}
	
	

}
