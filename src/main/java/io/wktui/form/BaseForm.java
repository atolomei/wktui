package io.wktui.form;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;

public class BaseForm<T> extends Form<T> {

    private static final long serialVersionUID = 1L;

    public BaseForm(String id) {
        super(id);
    }
    
    public BaseForm(String id, IModel<T> model) {
        super(id, model);
    }

    @Override
    public void renderHead(final IHeaderResponse response) {
        super.renderHead(response);
    }
        
    
    
    
    
}
