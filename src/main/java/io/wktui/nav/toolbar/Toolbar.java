package io.wktui.nav.toolbar;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;

import io.wktui.nav.toolbar.ToolbarItem.Align;
import wktui.base.BasePanel;

public class Toolbar extends BasePanel {

	private static final long serialVersionUID = 1L;

	private List<ToolbarItem> listLeft;
	private List<ToolbarItem> listRight;
	private List<ToolbarItem> list;

	WebMarkupContainer toolbarFrame;
	
	WebMarkupContainer itemsLeftContainer;  
	WebMarkupContainer itemsRightContainer; 
	
	public Toolbar(String id) {
		super(id);
		list=new ArrayList<ToolbarItem>();
	}
	
	public Toolbar(String id, List<ToolbarItem> list) {
		super(id);
		this.list=list;
	}

	public void setItems(List<ToolbarItem> list) {
		this.list=list;
	}


	public List<ToolbarItem> getItems() {
		return this.list;
	}
	
	public void addItem( ToolbarItem item, Align align) {

		item.setAlign(align);
		list.add(item);
	}
	
	
	public void addItem(ToolbarItem t) {

		if (t.getAlign()==null)
			t.setAlign(Align.TOP_LEFT);
	
		list.add(t);
		
	}
	
	public void onInitialize() {
		super.onInitialize();
		load();
	}
	protected List<ToolbarItem> getRightItems() {
		return this.listRight;
	}

	
	protected List<ToolbarItem> getLeftItems() {
		return this.listLeft;
	}

	protected String getToolbarCss() {
	 	return "navbar";
	}

	private void load() {
		
		this.listLeft=new ArrayList<ToolbarItem>();
		this.listRight=new ArrayList<ToolbarItem>();

		
		this.list.forEach( item -> {
			
			Align a=item.getAlign();
			
			if (a==Align.TOP_LEFT)
				listLeft.add(item);
			else if (a==Align.TOP_RIGHT)
				listRight.add(item);
		});
		
		this.toolbarFrame = new WebMarkupContainer("toolbarFrame");
		
		this.toolbarFrame.add(new AttributeModifier("class", new Model<String>() {
			private static final long serialVersionUID = 1L;
			public String getObject() {
				return getToolbarCss();
			}
		}));

		this.itemsLeftContainer  	=  new WebMarkupContainer("itemsLeftContainer") {
			private static final long serialVersionUID = 1L;

			public boolean isVisible() {
				return getLeftItems().size()>0;
			}
		};
		
		this.itemsRightContainer  	=  new WebMarkupContainer("itemsRightContainer") {
			private static final long serialVersionUID = 1L;

			public boolean isVisible() {
				return getRightItems().size()>0;
			}
		};
		
		addOrReplace(this.toolbarFrame);
		
		this.toolbarFrame.addOrReplace( itemsLeftContainer);
		this.toolbarFrame.addOrReplace( itemsRightContainer);
		
		this.itemsLeftContainer.add(new ListView<ToolbarItem>("itemLeft", getLeftItems()) {
			private static final long serialVersionUID = 1L;

			public void populateItem(ListItem<ToolbarItem> item) {
				item.add(item.getModelObject());
			}
		});
		
		
		this.itemsRightContainer.add(new ListView<ToolbarItem>("itemRight", getRightItems()) {
			private static final long serialVersionUID = 1L;

			public void populateItem(ListItem<ToolbarItem> item) {
				item.add(item.getModelObject());
			}
		});
	}
	
	
	
}
