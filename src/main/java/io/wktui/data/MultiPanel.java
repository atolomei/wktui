package io.wktui.data;

import java.util.List;
import java.util.logging.Logger;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.Link;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.resource.UrlResourceReference;
 
import io.wktui.event.UIEvent;
import wktui.base.INamedTab;
import wktui.base.InvisiblePanel;
import wktui.base.ModelPanel;

public abstract class MultiPanel<T> extends ModelPanel<T> {

	static private Logger logger = Logger.getLogger(MultiPanel.class.getName());

	private static final long serialVersionUID = 1L;

	private WebMarkupContainer frame = new WebMarkupContainer("frame");

	private String startingTab = null;
	private List<INamedTab> tabs;
	private WebMarkupContainer currentPanel;
	private int currentIndex = 0;

	protected abstract List<INamedTab> getInternalTabs();

	public MultiPanel(String id) {
		this(id, null);
	}

	public MultiPanel(String id, IModel<T> model) {
		super(id, model);
		setOutputMarkupId(true);

	}

	@Override
	public void onInitialize() {
		super.onInitialize();

		frame.setOutputMarkupId(true);
		frame.add(new org.apache.wicket.AttributeModifier("class", getCss()));
		add(frame);

		tabs = getTabs();

		if (this.startingTab != null) {
			int tabOrder = getTab(this.startingTab);
			this.currentIndex = tabOrder;
		}
		currentPanel = tabs.get(getCurrentIndex()).getPanel("internalPanel");
		frame.addOrReplace(currentPanel);
	}

	public void setStartTab(String tabName) {
		this.startingTab = tabName;
	}

	public String getStartTab() {
		return this.startingTab;
	}

	public void togglePanel(String name, AjaxRequestTarget target) {
		togglePanel(getTab(name), target);
	}

	public void togglePanel(int panelOrder, AjaxRequestTarget target) {

		if (this.currentIndex == panelOrder) {
			target.add(this.frame);
			return;
		}

		this.currentIndex = panelOrder;
		this.currentPanel = getTabs().get(getCurrentIndex()).getPanel("internalPanel");
		this.frame.addOrReplace(this.currentPanel);
		target.add(this.frame);

	}

	protected int getCurrentIndex() {
		return this.currentIndex;
	}

	public String getStartingTab() {
		return startingTab;
	}

	public void setStartingTab(String startingTab) {
		this.startingTab = startingTab;
	}

	public void setCurrentIndex(int currentIndex) {
		this.currentIndex = currentIndex;
	}

	@Override
	public void onDetach() {
		super.onDetach();
	}

	protected WebMarkupContainer getCurrentPanel() {
		return this.currentPanel;
	}

	protected String getCss() {
		return null;
	}

	public void addListeners() {
		super.addListeners();
	}

	private List<INamedTab> getTabs() {
		if (tabs == null)
			tabs = getInternalTabs();
		return tabs;
	}

	private int getTab(String name) {

		List<INamedTab> tabs = getTabs();

		int current = 0;
		int selected = 0;
		for (INamedTab tab : tabs) {
			if (tab.getName().equals(name)) {
				selected = current;
				break;
			}
			current++;
		}
		return selected;
	}

}
