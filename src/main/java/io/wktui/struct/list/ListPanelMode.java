package io.wktui.struct.list;

public enum ListPanelMode {

	TITLE ("mode-title"),
	TITLE_TEXT ("mode-title-text"),
	TITLE_TEXT_IMAGE ("mode-title-image");

	String css;
	
	private ListPanelMode(String css) {
		this.css=css;
	}
	
	public String getCss() {
			return css;
	}
}
