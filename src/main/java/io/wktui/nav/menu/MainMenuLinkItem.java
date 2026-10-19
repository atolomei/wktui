package io.wktui.nav.menu;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.model.IModel;

/**
 * Normal (non-title) entry of the {@link MainMenu}: a plain link to a url.
 */
public class MainMenuLinkItem extends MenuItemPanel<Void> {

	private static final long serialVersionUID = 1L;

	private final IModel<String> label;
	private final String url;

	public MainMenuLinkItem(String id, IModel<String> label, String url) {
		super(id);
		this.label = label;
		this.url = url;
	}

	@Override
	public IModel<String> getLabel() {
		return label;
	}

	@Override
	public String getUrl() {
		return url;
	}

	@Override
	public void onInitialize() {
		super.onInitialize();

		ExternalLink link = new ExternalLink("link", getUrl()) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onComponentTag(ComponentTag tag) {
				super.onComponentTag(tag);
				if (getTarget() != null)
					tag.put("target", getTarget());
			}
		};
		link.add(new Label("label", getLabel()).setEscapeModelStrings(false));
		add(link);
	}

	@Override
	public void onDetach() {
		super.onDetach();
		if (label != null)
			label.detach();
	}
}
