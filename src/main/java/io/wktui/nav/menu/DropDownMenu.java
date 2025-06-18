package io.wktui.nav.menu;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;

import wktui.base.BasePanel;

public class DropDownMenu<T> extends BasePanel {
	
	private static final long serialVersionUID = 1L;
	private IModel<T> model;
	private int index;
	private List<MenuItemFactory<T>> items = new ArrayList<MenuItemFactory<T>>();
	private ListView<MenuItemFactory<T>> itemsview;

	private boolean sort = false;
	private boolean popper = true;
	
	
	public DropDownMenu(String id) {
		this(id, null);
	}
	
	public DropDownMenu() {
		this("menu", null);
	}
	
	public DropDownMenu(IModel<T> model) {
			this("menu", model);
	}
	
	public DropDownMenu(String id, IModel<T> model) {
		super(id);
		setModel(model);
		setOutputMarkupId(true);
	}
	
	
	/**
	 * 
	 * 
	 */
	@Override
	public void onInitialize() {
		super.onInitialize();
		
		if (itemsview==null) {
			
			int n= 0;
			
			if (isSort()) {
				this.items.sort(new Comparator<MenuItemFactory<T>>() {
					@Override
					public int compare(MenuItemFactory<T> o1, MenuItemFactory<T> o2) {
						MenuItemPanel<T> item_1 = (MenuItemPanel<T>) o1.getItem(String.valueOf(n));
						MenuItemPanel<T> item_2 = (MenuItemPanel<T>) o2.getItem(String.valueOf(n));
						if (item_1!=null && item_2!=null) {
							IModel<String> l1=item_1.getLabel();
							IModel<String> l2=item_1.getLabel();
							if (l1!=null && l2!=null)
								return l1.getObject().compareToIgnoreCase(l2.getObject());
						}
						return 0;
					}
				});
			}

			itemsview = new ListView<MenuItemFactory<T>>("item", this.items) {
				private static final long serialVersionUID = 1L;
				public void populateItem(ListItem<MenuItemFactory<T>> item) {
					MenuItemPanel<T> panel = item.getModelObject().getItem("panel");
					//panel.setEscapeModelStrings(false);
					//item.setMarkupId("cpanel"+factoryitem.getIndex());
					item.setVisible(panel.isVisible());
					item.setIndex(getIndex());
					
					panel.setModel(new IModel<T>() {
                        private static final long serialVersionUID = 1L;
                        
                        public void setObject(T object) {
							DropDownMenu.this.getModel().setObject(object);
						}
						public T getObject() {
							return DropDownMenu.this.getModel().getObject();
						}
						public void detach() {
							if (DropDownMenu.this.getModel()!=null)
							DropDownMenu.this.getModel().detach();
						}
					});

					if (panel.getCssClass()!=null)
						item.add(new AttributeModifier("class", panel.getCssClass()));

					item.add(panel);
					item.setVisible(panel.isVisible());
				}
			};

			add(itemsview);
		}
	}

	public void setSort(boolean b) {
		this.sort=b;
	}
	
	public boolean isSort() {
		return  sort;
	}

	
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		
		if (isPopper()) {
				response.render(OnDomReadyHeaderItem.forScript(
						"tryBindPopperDropdown( $('#" + this.getMarkupId() + "'));"
				));
		}

	}

	
	public void setPopper( boolean b) {
		this.popper=b;
	}
	
	public boolean isPopper() {
		return popper;
	}
	
	public void setModel(IModel<T> model) {
		this.model = model;
	}
	
	
	public IModel<T> getModel() {
		return this.model;
	}
	
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public int getIndex() {
		return this.index;
	}
	
	public void addItem(MenuItemFactory<T> item) {
		this.items.add(item);
	}	 
	
	@Override
	public void onDetach() {
		super.onDetach();
		
		
		if (this.model!=null) 
			this.model.detach();
		
	}

}
