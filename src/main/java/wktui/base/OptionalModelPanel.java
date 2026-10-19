package wktui.base;

import java.util.Optional;

import org.apache.wicket.model.IModel;

/**
 * Panel with an optional model. The model is stored internally as a nullable
 * {@link IModel} field (NOT as {@link Optional}) because
 * {@code java.util.Optional} is not {@link java.io.Serializable} and would
 * break Wicket page serialization.
 */
public class OptionalModelPanel<T> extends BasePanel {
	
	
	private static final long serialVersionUID = 1L;
	
	/** nullable: null means "no model" */
	private IModel<T> model;
	
	

	public OptionalModelPanel(String id,  Optional<IModel<T>> model) {
		super(id);
		this.model = (model != null) ? model.orElse(null) : null;
	}

	public void setModel(IModel<T> model)  {
		this.model = model;
	}
	
	public Optional<IModel<T>> getOptionalModel() {
		return Optional.ofNullable(model);
	}
	
	public void onDetach() {
		super.onDetach();
		if (this.model != null)
			this.model.detach();
	}
	
	
}
