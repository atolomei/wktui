package io.wktui.event;

public class SimpleWicketEvent implements WicketEvent {

	final String name;
	
	public SimpleWicketEvent( String name) {
		this.name=name;
	}

	public String getName() { 
		return this.name;
	}

}
