package io.wktui.error;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.AttributeModifier;

import wktui.base.BasePanel;
import wktui.base.ModelPanel;

public class AlertHelpPanel<T> extends AlertPanel<T> {

	private static final long serialVersionUID = 1L;
	 
	public AlertHelpPanel(String id, IModel<String> textModel) {
		super(id, AlertPanel.HELP, textModel);
	}
	 
	 
}
