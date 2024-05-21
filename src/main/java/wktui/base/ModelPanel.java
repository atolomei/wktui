package wktui.base;

import org.apache.wicket.model.IDetachable;
import org.apache.wicket.model.IModel;

public class ModelPanel<T> extends BasePanel {
	
	
	private static final long serialVersionUID = 1L;
	
	IModel<T> model;
	

	public ModelPanel(String id, IModel<T> model) {
		super(id);
		this.model=model;
	}

	
	public IModel<T> getModel() {
		return model;
	}
	
	public void onDetach() {
		super.onDetach();
		if (this.model!=null)
			this.model.detach();
	}


	
	
}
