package wktui.base;

import org.apache.wicket.extensions.markup.html.tabs.ITab;

public interface INamedTab extends ITab {

	default public String getName() { return null; }
	
}
