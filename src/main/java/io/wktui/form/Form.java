package io.wktui.form;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.model.IModel;


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
    
    private FormState state = FormState.VIEW;
    
    
    public Form(String id) {
        super(id);
    }
    
    public Form(String id, IModel<T> model) {
        super(id, model);
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

}
