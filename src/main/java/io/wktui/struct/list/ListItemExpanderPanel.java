package io.wktui.struct.list;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
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
	
	private WebMarkupContainer expandedPanel;
	
	private WebMarkupContainer icon = new WebMarkupContainer("icon");
	private AjaxLink<T> ex;
	private WebMarkupContainer mainPanel;
	
	
	public ListItemExpanderPanel(String id, IModel<T> model, WebMarkupContainer mainPanel) {
		super(id, model);
		super.setOutputMarkupId(true);
		this.mainPanel=mainPanel;
	}
	
	
	
	@Override
	public void onInitialize() {
			super.onInitialize();
			
			icon = new WebMarkupContainer("icon");
			
			add( getMainPanel() );
			
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
					
					fire (new ListPanelWicketEvent<T>( ListPanel.ITEM_EXPAND, target , ListItemExpanderPanel.this.getModel(), isExpanded()));
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
	
	
	
	public WebMarkupContainer getMainPanel() {
		//LabelPanel ma=new LabelPanel("main-panel", getLabel());
		//return ma;
		return mainPanel;
	}

	
	public WebMarkupContainer getExpandedPanel() {
		return new LabelPanel("expanded-panel", new Model<String>("expanded"));
	}

	public String getIconCollapsed() {
		return "fa-solid fa-caret-down";
	}
	

	public String getIconExpanded() {
		return "fa-solid fa-caret-up";
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
