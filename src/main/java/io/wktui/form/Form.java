package io.wktui.form;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;

 
 
import io.wktui.form.field.Field;
import wktui.base.Logger;


/***
 * 
 * [View] [Edit]
 * 
 * View -> Edit
 * 
 * 2 States
 * ViewMode EditMode -> Edit  -> Save | Cancel 
 * 
 * EditMode
 * 
 * 
 * 
 */
public class Form<T> extends BaseForm<T> {

    private static final long serialVersionUID = 1L;
    
    static private Logger logger = Logger.getLogger(Form.class.getName());
    
    
    private FormState state = FormState.VIEW;
    
    
    public Form(String id) {
        super(id);
    }
    
    public Form(String id, IModel<T> model) {
        super(id, model);
    }

     
	@Override
    public MarkupContainer add(final Component... children) {
    	//for (Component child : children) {
    		//if (child instanceof Field)
    		//	((Field<T>) child).editOff();
    	//}
    	return super.add( children );
    }

    public FormState getFormState() {
        return this.state;
    }
    
    @Override
    public void renderHead(final IHeaderResponse response) {
        super.renderHead(response);
    }
    
    @Override
    protected void onComponentTag(final ComponentTag tag) {
        super.onComponentTag(tag);
    }

	public void setFormState(FormState state) {
		this.state=state;
	}

	
	public void updateReload() {
		this.visitChildren(Field.class, new IVisitor<Field<?>, Void>() {
			@Override
			public void component(Field<?> field, IVisit<Void> visit) {
				field.reload();
			}
		});
	}
	
	public void updateModel() {
		this.visitChildren(Field.class, new IVisitor<Field<?>, Void>() {
			@Override
			public void component(Field<?> field, IVisit<Void> visit) {
				field.updateModel();
			}
		});

	}
	
	/**
	 * Method to override if you want to do something special when an error occurs (other than
	 * simply displaying validation errors).
	 */
	protected void onError() {
		
		logger.debug("on error");
	}
	
}
