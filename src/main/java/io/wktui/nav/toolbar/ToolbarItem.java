package io.wktui.nav.toolbar;


import org.apache.wicket.model.IModel;


import wktui.base.BasePanel;

public class ToolbarItem extends BasePanel {

	private static final long serialVersionUID = 1L;

	public enum Justify {
		LEFT,
		RIGHT;
	};

	public enum Align {
		TOP_RIGHT,
		TOP_LEFT,
		TOP_NONE,
		BOTTOM_LEFT,
		BOTTOM_RIGHT,
		BOTTOM_NONE
	};
	
	private Justify justify;
	private Align align;
	private boolean isIcon;
	private String iconCss;
	
	public ToolbarItem(String id) {
		super(id);
	
		align=Align.TOP_LEFT;
		 justify=Justify.LEFT;
	}

	
	
	public boolean isIcon() {
		return this.isIcon;
	}
	
	public Align getAlign() {
		return align;
	}

	public Justify getJustify() {
		return this.justify;
	}
	
	public void setJustify(Justify just) {
		this.justify=just;
	}

	protected String getIconCss() {
		return iconCss; 
	}

	public void setAlign(Align align) {
		this.align=align;
	}
	
	
	

}
