package io.wktui.struct.list;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;


import wktui.base.ModelPanel;

public class ListItemPanel<T> extends ModelPanel<T> {

	private static final long serialVersionUID = 1L;

	
	ListPanelMode mode;
	
	public ListItemPanel(String id, IModel<T> model,ListPanelMode mode) {
		super(id, model);
		this.mode=mode;
	}
	
	@Override
	public void onInitialize() {
			super.onInitialize();
			
			Link<T> link = new Link<T>("link", getModel()) {
                private static final long serialVersionUID = 1L;
                @Override
                public void onClick() {
                    ListItemPanel.this.onClick();
                }
			};
			
			add(link);
			link.add( new Label("label", getLabel()));
	}

	protected void onClick() {
    }

    public IModel<String> getLabel() {
		return new Model<String>(getModel().getObject().toString());
	}
    
    public boolean isImageVisible() {
		return this.mode==ListPanelMode.TITLE_TEXT_IMAGE;
	}
 
    public boolean isTextVisible() {
		return this.mode==ListPanelMode.TITLE_TEXT_IMAGE || this.mode==ListPanelMode.TITLE_TEXT;
	}
}
