package io.wktui.nav.breadcrumb;

import org.apache.wicket.model.IModel;

import wktui.base.BasePanel;

public abstract class BreadcrumbBasePanel extends BasePanel implements IBreacrumbPanel {

    private static final long serialVersionUID = 1L;

    public BreadcrumbBasePanel(String id) {
        super(id);
    }
    
    public abstract IModel<String> getLabel();
    public abstract void onClick();
    
    protected String pad(String s) {

        if (s==null)
            return "";
        if (s.length()<BreadCrumb.LIMIT)
            return s;
        return s.substring(0,BreadCrumb.LIMIT)+"...";
    }


}
