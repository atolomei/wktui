package wktui.base;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

public class LabelPanel extends Panel {

	private static final long serialVersionUID = 1L;
	
	Label label;
	
	
	public LabelPanel(String id, Label label) {
		super(id);
		this.label=label;
		if (!label.getId().equals("label"))
				throw new IllegalArgumentException("Label id must be 'label'");
	}

	public LabelPanel(String id, IModel<String> d) {
		super(id);
		this.label= new Label("label", d);
	}
	
	@Override
	public void onInitialize() {
		super.onInitialize();
		this.label.setEscapeModelStrings(false);
		add(this.label);
	}

	public void setStyle(String string) {
		label.add(new AttributeModifier("style", string));
	}
	
	public void setCss(String string) {
		label.add(new AttributeModifier("class", string));
	}

	
}
