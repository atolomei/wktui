package io.wktui.nav.menu;

import java.io.Serializable;

import org.apache.wicket.model.IModel;

public interface MenuItem extends Serializable {

	public IModel<String> getLabel();
	public boolean isVisible();
	public String getCssClass();
	
	
}
