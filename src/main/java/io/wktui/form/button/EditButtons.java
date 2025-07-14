package io.wktui.form.button;

import org.apache.wicket.ajax.markup.html.AjaxLink;

import io.wktui.form.Form;
import wktui.base.BasePanel;

public class EditButtons<T> extends BasePanel {

    private static final long serialVersionUID = 1L;

    private Form<T> form;
    
    AjaxLink<Void> edit;
    AjaxLink<Void> save;
    AjaxLink<Void> cancel;
    
    
    private int mode;
    
    private String style;
    private String css;
    
    
    public EditButtons(String id) {
        super(id);
    }
    
    public void onInitialize() {
        super.onInitialize();
        
        
    }


    
    public String getStrStyle() {
        return style;
    }

    
    public void setStrStyle( String s) {
        style=s;
    }

    protected String getEditClass() {
        return "btn btn-primary btn-sm";
    }
    
    protected String getSaveClass() {
        return "btn btn-primary btn-sm";
    }
    
    protected String getCancelClass() {
        return "btn btn-default btn-sm";
    }
    

   

    
}
