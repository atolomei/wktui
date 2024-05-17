package io.wktui.nav.menu;

import java.io.Serializable;


public interface MenuItemFactory<T> extends Serializable {
	
		public abstract MenuItemPanel<T> getItem(String id);
		public default void detach() {};
		public default int getOrder() {return 0;}
	
}
