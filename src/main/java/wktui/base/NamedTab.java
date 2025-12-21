package wktui.base;

import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;

public abstract class NamedTab extends AbstractTab implements INamedTab {

	final String name;
	final String moreInfo;

	public String getName() {
		return name;
	}
	public NamedTab(IModel<String> title, String name)  {
				this(title, name, null);
	}
	
	
	public NamedTab(IModel<String> title, String name, String moreInfo) {
		super(title);
		this.name=name;
		this.moreInfo=moreInfo;
	}

	public String getMoreInfo() {
		return this.moreInfo;
	}
}
