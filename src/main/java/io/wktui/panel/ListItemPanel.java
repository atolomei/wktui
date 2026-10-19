package io.wktui.panel;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;

public interface ListItemPanel<T> {

	public boolean isEqual(T o1, T o2);

	public IModel<String> getObjectTitle();

	public IModel<String> getInfo();
	
	
	public WebMarkupContainer getObjectMenu();
	public void onClick();
	
	
	public  String getTitleIcon();
	public String getImageSrc();
	
	
}
