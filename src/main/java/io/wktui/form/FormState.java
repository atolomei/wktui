package io.wktui.form;

import java.util.Locale;
import java.util.ResourceBundle;


public enum FormState {

    VIEW     (1, "view"), 
    EDIT     (2, "edit");
    
    private int id;    
    private String label;
    
    private FormState(int code, String label) {
        this.label = label;
        this.id = code; 
    }
    
    public String toString() {
        return ("id: " + String.valueOf( getId()) + " | label: "+ getLabel());
    }
    
    public String getCode() {
        return label;
    }
    
    
    public String getDisplayName() {
        return getLabel();
    }
    
    public String getLabel() {
        return getLabel(Locale.getDefault());
    }
    
    
    public String getLabel(Locale locale) {
        ResourceBundle res = ResourceBundle.getBundle(FormState.this.getClass().getName(), locale);
        return res.getString(this.label);
    }
    
    public int getId() {
        return id;
    }
    
}
