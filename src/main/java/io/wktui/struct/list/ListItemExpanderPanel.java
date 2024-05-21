package io.wktui.struct.list;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import wktui.base.InvisiblePanel;
import wktui.base.LabelPanel;
import wktui.base.ModelPanel;

public class ListItemExpanderPanel<T> extends ModelPanel<T> {

	private static final long serialVersionUID = 1L;

	private boolean isExpanded = false;
	
	private Panel expandedPanel;
	
	private WebMarkupContainer icon = new WebMarkupContainer("icon");
	private AjaxLink<T> ex;
	
	
	public ListItemExpanderPanel(String id, IModel<T> model) {
		super(id, model);
	}
	
	@Override
	public void onInitialize() {
			super.onInitialize();
			
			icon = new WebMarkupContainer("icon");
			
			LabelPanel ma=new LabelPanel("main-panel", getLabel());
			add( ma);
			
			ex = new AjaxLink<T>("expand", ListItemExpanderPanel.this.getModel()) {
				private static final long serialVersionUID = 1L;
				@Override
				public void onClick(AjaxRequestTarget target) {

					setExpanded(!isExpanded());
					icon = new WebMarkupContainer("icon");
					
					if (isExpanded()) {
							icon.add(new AttributeModifier("class", getIconExpanded()));
							expandedPanel = getExpandedPanel();							
					}
					else {
							icon.add(new AttributeModifier("class", getIconCollapsed()));
							expandedPanel = new InvisiblePanel("expanded-panel");
					}
						
						ListItemExpanderPanel.this.addOrReplace(expandedPanel);
						ex.addOrReplace(icon);
						target.add(ListItemExpanderPanel.this);
				}
			};
			add(ex);
			
			
			if (isExpanded()) {
				icon.add(new AttributeModifier("class", getIconExpanded()));
				expandedPanel = getExpandedPanel();							
			}
			else {
				icon.add(new AttributeModifier("class", getIconCollapsed()));
				expandedPanel = new InvisiblePanel("expanded-panel");
			}
			ListItemExpanderPanel.this.addOrReplace(expandedPanel);
			ex.addOrReplace(icon);
	}
	
	
	
	public Panel getExpandedPanel() {
		return new LabelPanel("expanded-panel", new Model<String>("expanded"));
	}

	public String getIconCollapsed() {
		return "icon-collapsed";
	}

	public String getIconExpanded() {
		return "icon-expanded";
	}
	
	public IModel<String> getLabel() {
		return new Model<String>(getModel().getObject().toString());
	}
	
	
	public void setExpanded(boolean b) {
		this.isExpanded=b;
	}
	
	public boolean isExpanded() {
		return this.isExpanded;
	}
	
}
