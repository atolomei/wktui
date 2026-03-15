package wktui.base;

import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;

public abstract class NamedTab extends AbstractTab implements INamedTab {

	private static final long serialVersionUID = 1L;

	final String name;
	final String moreInfo;

	
	public NamedTab(String name)  {
		this(null, name, null);
	}

	
	public NamedTab(IModel<String> title, String name)  {
				this(title, name, null);
	}
	
	
	public NamedTab(IModel<String> title, String name, String moreInfo) {
		super(title);
		this.name=name;
		this.moreInfo=moreInfo;
	}


	public String getName() {
		return name;
	}
	
	public String getMoreInfo() {
		return this.moreInfo;
	}
}
