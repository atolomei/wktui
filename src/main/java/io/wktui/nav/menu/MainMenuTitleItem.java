package io.wktui.nav.menu;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;

/**
 * Title entry of the {@link MainMenu}: bold, uppercase, not clickable.
 */
public class MainMenuTitleItem extends MenuItemPanel<Void> {

	private static final long serialVersionUID = 1L;

	private final IModel<String> label;

	public MainMenuTitleItem(String id, IModel<String> label) {
		super(id);
		this.label = label;
	}

	@Override
	public IModel<String> getLabel() {
		return label;
	}

	@Override
	public void onInitialize() {
		super.onInitialize();
		add(new Label("label", getLabel()).setEscapeModelStrings(false));
	}

	@Override
	public void onDetach() {
		super.onDetach();
		if (label != null)
			label.detach();
	}
}
