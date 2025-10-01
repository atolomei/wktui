package io.wktui.nav.breadcrumb;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.pages.RedirectPage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class HREFBCElement extends BreadcrumbBasePanel implements IBCElement {

    private static final long serialVersionUID = 1L;

    private String url;
    protected IModel<String> label;
    private IModel<String> html_title;

    public HREFBCElement(String url) {
        super("bc-menu-item");
        this.url = url;
    }

    public HREFBCElement(String url, IModel<String> label) {
        super("bc-menu-item");
        this.url = url;
        this.label = label;
    }

    public HREFBCElement(String id, String url, IModel<String> label) {
        super(id);
        this.url = url;
        this.label = label;
    }

    @Override
    public IModel<String> getLabel() {
        return label;
    }

    public String getUrl() {
        return this.url;
    }

    @Override
    public boolean isNewTab() {
        return false;
    }

    protected void setLabel(IModel<String> label) {
        this.label = label;
    }

    @Override
    public void onClick() {
        setResponsePage( new RedirectPage(this.url));
    }

    public void onInitialize() {
        super.onInitialize();
        WebMarkupContainer ln = new WebMarkupContainer("link");
        ln.add(new AttributeModifier("href", this.url));
        add(ln);
        Label la = new Label("label", new Model<String>(pad(this.label.getObject())));
        la.setEscapeModelStrings(false);
        ln.add(la);

        if (this.getHTMLTitleAttribute() != null)
            this.add(new AttributeModifier("title", getHTMLTitleAttribute()));

    }

    public void setHTMLTitleAttribute(IModel<String> ht) {
        html_title = ht;
    }

    @Override
    public IModel<String> getHTMLTitleAttribute() {
        return this.html_title;
    }

	@Override
	protected IModel<String> getLabel(int limit) {
		return Model.of( pad( getLabel().getObject(), limit));
	}

}
