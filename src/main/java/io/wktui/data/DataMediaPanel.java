package io.wktui.data;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

 
import wktui.base.InvisiblePanel;
import wktui.base.Logger;

/**
 * A read-only data panel that displays a media item (PDF, image, etc.) with:
 * <ul>
 * <li>Left side: a FontAwesome icon {@code <i>} <em>or</em> a thumbnail
 * {@code <img>}, both wrapped in a link that opens the file URL. When an image
 * (thumbnail) is set the icon is hidden; when no image is set the icon is
 * shown.</li>
 * <li>Right side: two rows — a title (also a link to the file) and an optional
 * subtitle.</li>
 * </ul>
 *
 * <p>
 * Override {@link #getFileUrl()} to supply the URL of the file to open.<br/>
 * Override {@link #getThumbnailUrl()} to supply the {@code src} for the
 * thumbnail {@code <img>}.<br/>
 * Set the icon FontAwesome CSS class via {@link #setIconCss(String)} (e.g.
 * {@code "fa fa-file-pdf-o"}).
 * </p>
 *
 * <p>
 * Whether the thumbnail or the icon is shown depends on
 * {@link #hasThumbnail()}: override it to return {@code true} when a thumbnail
 * is available.
 * </p>
 */
public class DataMediaPanel<T> extends DataPanel<T> {

	private static final long serialVersionUID = 1L;

	static private Logger logger = Logger.getLogger(DataMediaPanel.class.getName());

	// ---------------------------------------------------------------
	// State
	// ---------------------------------------------------------------

	String css;
	
	/** FontAwesome class applied to the {@code <i>} tag. */
	private String iconCss = null;

	private IModel<String> resourceTitleModel;
	private IModel<String> resourceSubtitleModel;

	// ---------------------------------------------------------------
	// Wicket components
	// ---------------------------------------------------------------
	private WebMarkupContainer mainContainer;
	
	private WebMarkupContainer data;
	private WebMarkupContainer imageContainer;
	private WebMarkupContainer titleTextContainer;

	private ExternalLink imageLink;
	private ExternalLink iconLink;
	private ExternalLink titleLink;

	private Image thumbnailImage;
	private WebMarkupContainer iconElement;

	private WebMarkupContainer subtitleContainer;

	// ---------------------------------------------------------------
	// Constructors
	// ---------------------------------------------------------------

	public DataMediaPanel(String id, IModel<T> model) {
		this(id, model, null);
	}

	public DataMediaPanel(String id, IModel<T> model, IModel<String> label) {
		this(id, model, label, null, null);
	}

	public DataMediaPanel(String id, IModel<T> model, IModel<String> label, IModel<String> titleModel, IModel<String> subtitleModel) {
		super(id, model, label);
		this.resourceTitleModel = titleModel;
		this.resourceSubtitleModel = subtitleModel;
		setOutputMarkupId(true);
	}

	// ---------------------------------------------------------------
	// Lifecycle
	// ---------------------------------------------------------------

	@Override
	public void onInitialize() {
		super.onInitialize();

		
		mainContainer = new WebMarkupContainer("mainContainer");
		
	  	data = new WebMarkupContainer("data");
		data.setOutputMarkupId(true);

		// ---- left column: image / icon container -------------------
		imageContainer = new WebMarkupContainer("imageContainer");

		// image-link: visible only when thumbnail is available
		imageLink = new ExternalLink("image-link", new PropertyModel<>(this, "fileUrl")) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isVisible() {
				return hasThumbnail();
			}
		};
		
		imageLink.add(new AttributeModifier("target", "_blank"));

		thumbnailImage = new Image("image") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onComponentTag(org.apache.wicket.markup.ComponentTag tag) {
				super.onComponentTag(tag);
				String url = getThumbnailUrl();
				tag.put("src", url != null ? url : "");
			}
		};
		
		
		thumbnailImage.add(new AttributeModifier("class", "thumbnail"));
		imageLink.add(thumbnailImage);
		imageContainer.add(imageLink);

		
		if (!hasThumbnail()) {
			if (getIconCss() == null) {
				setIconCss( getDefaultIconCss( getResourceTitleModel().getObject()) );
			}
		}
		// icon-link: visible only when NO thumbnail is available
		iconLink = new ExternalLink("icon-link", new PropertyModel<>(this, "fileUrl")) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isVisible() {
				return !hasThumbnail();
			}
		};
		iconLink.add(new AttributeModifier("target", "_blank"));

		iconElement = new WebMarkupContainer("icon");
		iconElement.add(new AttributeModifier("class", new PropertyModel<>(this, "iconCss")));
		iconLink.add(iconElement);
		imageContainer.add(iconLink);

		data.add(imageContainer);

		// ---- right column: title + subtitle ------------------------
		titleTextContainer = new WebMarkupContainer("titleTextContainer");

		
		// title-link
		titleLink = new ExternalLink("title-link", new PropertyModel<>(this, "fileUrl")) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isVisible() {
				return getResourceTitleModel() != null;
			}
		};
		titleLink.add(new AttributeModifier("target", "_blank"));

		Label titleLabel = new Label("title", getResourceTitleModel() != null ? getResourceTitleModel() : Model.of(""));
		
		titleLabel.setEscapeModelStrings(false);
		titleLink.add(titleLabel);
		titleTextContainer.add(titleLink);

		// subtitle-container
		subtitleContainer = new WebMarkupContainer("subtitle-container") {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isVisible() {
				return getResourceSubtitleModel() != null;
			}
		};
		
		
		Label subtitleLabel = new Label("subtitle", getResourceSubtitleModel() != null ? getResourceSubtitleModel() : Model.of(""));
		subtitleLabel.setEscapeModelStrings(false);
		subtitleContainer.add(subtitleLabel);
		titleTextContainer.add(subtitleContainer);

		data.add(titleTextContainer);

		// ---- menu placeholder (invisible by default) ---------------
		data.add(new InvisiblePanel("menu"));

		
		if (getCss() != null)
			data.add(new AttributeModifier("class", getCss()));
		
		mainContainer.add(data);
		super.addControl(mainContainer);
	}

	// ---------------------------------------------------------------
	// Methods to override
	// ---------------------------------------------------------------

	private String getDefaultIconCss(String fileName) {
		 
		if ( FSUtil.isPdf(fileName)) 
			return FAIcons.PDF;
		
		if ( FSUtil.isExcel(fileName)) 
			return FAIcons.MS_EXCEL;
			
		if ( FSUtil.isWord(fileName)) 
			return FAIcons.MS_WORD;

		if ( FSUtil.isPowerpoint(fileName)) 
			return FAIcons.MS_POWERPOINT;

		if ( FSUtil.isCompressed(fileName)) 
			return FAIcons.COMPRESSED;

		if ( FSUtil.isAudio(fileName)) 
			return FAIcons.AUDIO;
			
		if ( FSUtil.isVideo(fileName)) 
			return FAIcons.VIDEO;
			
		if ( FSUtil.isText(fileName)) 
			return FAIcons.TEXT;
		
		return FAIcons.FILE;
		
	}

	/**
	 * Returns {@code true} when a thumbnail image is available. When {@code true}
	 * the thumbnail is shown and the icon is hidden; when {@code false} the icon is
	 * shown and the image link is hidden. Override to provide dynamic logic
	 * (default: {@code false}).
	 */
	public boolean hasThumbnail() {
		return false;
	}

	/**
	 * Returns the URL of the file to open when the icon/thumbnail or title is
	 * clicked. Override to supply a dynamic URL.
	 */
	public String getFileUrl() {
		return "#";
	}

	/**
	 * Returns the {@code src} URL for the thumbnail {@code <img>}. Only relevant
	 * when {@link #hasThumbnail()} returns {@code true}. Override to supply a
	 * dynamic thumbnail URL.
	 */
	public String getThumbnailUrl() {
		return "";
	}

	@Override
	public Component getData() {
		return data;
	}

	@Override
	public void reload() {
		// stateless display panel — nothing to reload by default
	}

	@Override
	public void updateModel() {
		// read-only display panel — no model update needed
	}

	// ---------------------------------------------------------------
	// Getters / setters
	// ---------------------------------------------------------------

	public String getIconCss() {
		return iconCss;
	}

	public void setIconCss(String iconCss) {
		this.iconCss = iconCss;
		if (iconElement != null)
			iconElement.add(new AttributeModifier("class", iconCss));
	}

	public IModel<String> getResourceTitleModel() {
		return resourceTitleModel;
	}

	public void setResourceTitleModel(IModel<String> titleModel) {
		this.resourceTitleModel = titleModel;
		if (titleLink != null)
			((Label) titleLink.get("title")).setDefaultModel(titleModel);
	}

	public String getCss() {
		return css;
	}

	public void setCss(String css) {
		this.css = css;
	}

	public IModel<String> getResourceSubtitleModel() {
		return resourceSubtitleModel;
	}

	public void setResourceSubtitleModel(IModel<String> subtitleModel) {
		this.resourceSubtitleModel = subtitleModel;
		if (subtitleContainer != null)
			((Label) subtitleContainer.get("subtitle")).setDefaultModel(subtitleModel);
	}
}
