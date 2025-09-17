package io.wktui.event;

public interface UIEvent {

	public default boolean distributable() {
		return false;
	}
	
}
