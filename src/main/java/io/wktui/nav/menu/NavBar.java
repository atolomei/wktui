package io.wktui.nav.menu;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;

import wktui.base.BasePanel;
import wktui.base.ModelPanel;

public class NavBar<T> extends ModelPanel<T> {

	
	private static final long serialVersionUID = 1L;

	private WebMarkupContainer noCollapseLeft;
	private WebMarkupContainer collapse;
	
	private List<WebMarkupContainer> l_noCollapseLeft;
	private List<WebMarkupContainer> l_collapse;
	
	private boolean requiresReload = true;

	
	
	public NavBar(String id) {
			this(id, null);
	}
	
	
	public NavBar(String id, IModel<T> model) {
		super(id, model);
		this.l_noCollapseLeft = new ArrayList<WebMarkupContainer>();
		this.l_collapse = new ArrayList<WebMarkupContainer>();
		setOutputMarkupId(true);
	}
	
	@Override
	public void onInitialize() {
		super.onInitialize();
	
		
		
		noCollapseLeft = new WebMarkupContainer("noCollapseLeft") {
			private static final long serialVersionUID = 1L;
			public boolean isVisible() {
				return  (l_noCollapseLeft.size()>0);
			};
		};
		
		
		collapse = new WebMarkupContainer("collapse") {
			private static final long serialVersionUID = 1L;
			public boolean isVisible() {
				return  (l_collapse.size()>0);
			};
		};
		
		
		add(noCollapseLeft);
		add(collapse);
		
		load();
	
	}

	
	
	
	private void load() {
	
		this.noCollapseLeft.addOrReplace(new ListView<WebMarkupContainer>("element", l_noCollapseLeft) {
			private static final long serialVersionUID = 1L;
			@Override
			protected void populateItem(ListItem<WebMarkupContainer> item) {
				WebMarkupContainer element = item.getModelObject();
				item.add(element);
			}	
		});


		this.collapse.addOrReplace(new ListView<WebMarkupContainer>("element", l_collapse) {
			private static final long serialVersionUID = 1L;
			@Override
			protected void populateItem(ListItem<WebMarkupContainer> item) {
				WebMarkupContainer element = item.getModelObject();
				item.add(element);
			}	
		});
		
		requiresReload = false;
	}
	
	
	@Override
	public void onBeforeRender() {
		super.onBeforeRender();
		
		if (requiresReload) 
			load();


	}
	
	
	
	public void addNoCollapseLeft(WebMarkupContainer panel) {

		if (!panel.getId().equals("item"))
				throw new IllegalArgumentException("id must be 'item'");
		
		l_noCollapseLeft.add(panel);
		requiresReload = true;
	}

	public void addCollapse(WebMarkupContainer panel) {

		if (!panel.getId().equals("item"))
			throw new IllegalArgumentException("id must be 'item'");

		l_collapse.add(panel);
		requiresReload = true;		
	}
	
	
	
}
