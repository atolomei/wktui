package wktui.base;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
 
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

public class LabelPanel extends Panel {

	private static final long serialVersionUID = 1L;
	
	private Label label;
	private Label subtitle;
	
	private WebMarkupContainer labelContainer;
	private WebMarkupContainer sutitleContainer;
	
	public LabelPanel(String id, Label label) {
		super(id);
		this.label=label;
		if (!label.getId().equals("label"))
				throw new IllegalArgumentException("Label id must be 'label'");
	}

	public LabelPanel(String id, Label label, Label subtitle) {
		super(id);
		this.label=label;
		this.subtitle=subtitle;

		if (!subtitle.getId().equals("subtitle"))
			throw new IllegalArgumentException("subtitle id must be 'subtitle'");

		
		if (!label.getId().equals("label"))
				throw new IllegalArgumentException("Label id must be 'label'");
	}
	
	
	public LabelPanel(String id, IModel<String> d) {
		super(id);
		this.label= new Label("label", d);
	}
	
	public LabelPanel(String id, IModel<String> l, IModel<String> s) {
		super(id);
		this.label= new Label("label", l);
		this.subtitle= new Label("subtitle", s);
	}
	
	
	@Override
	public void onInitialize() {
		super.onInitialize();
		
		labelContainer = new WebMarkupContainer("labelContainer") {
			private static final long serialVersionUID = 1L;
			public boolean isVisible() {
				return getLabel()!=null;
			}
		};
		
		sutitleContainer = new WebMarkupContainer("subtitleContainer") {
			private static final long serialVersionUID = 1L;
			public boolean isVisible() {
				return getSubtitle()!=null;
			}
		};
		
		add(labelContainer);
		add(sutitleContainer);

		if (this.label!=null) {
			this.label.setEscapeModelStrings(false);
			labelContainer.add(this.label);
		}
		
		if (this.subtitle!=null) {
			this.subtitle.setEscapeModelStrings(false);
			sutitleContainer.add(this.subtitle);
		}
		
	}

	public void setLabelStyle(String string) {
		label.add(new AttributeModifier("style", string));
	}
	
	public void setLabelCss(String string) {
		label.add(new AttributeModifier("class", string));
	}

	public void setSubtitletyle(String string) {
		subtitle.add(new AttributeModifier("style", string));
	}
	
	public void setSubtitleCss(String string) {
		subtitle.add(new AttributeModifier("class", string));
	}

	
	
	public Label getLabel() {
		return label;
	}

	public Label getSubtitle() {
		return subtitle;
	}

	public void setLabel(Label label) {
		this.label = label;
	}

	public void setSubtitle(Label subtitle) {
		this.subtitle = subtitle;
	}

	
}
