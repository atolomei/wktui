package io.wktui.struct.list;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import wktui.base.BasePanel;
import wktui.base.ModelPanel;

public class ListItemPanel<T> extends ModelPanel<T> {

	private static final long serialVersionUID = 1L;

	public ListItemPanel(String id, IModel<T> model) {
		super(id, model);
	}
	
	@Override
	public void onInitialize() {
			super.onInitialize();
			add( new Label("label", getLabel()));
	}

	public IModel<String> getLabel() {
		return new Model<String>(getModel().getObject().toString());
	}
}
