package wktui.base;

import org.apache.wicket.markup.html.WebMarkupContainer;
 

public class InvisibleIcon extends WebMarkupContainer {

	private static final long serialVersionUID = 1L;

	public InvisibleIcon(String id) {
		super(id);
	}
	
	public boolean isVisible() {
		return false;
	}
}
