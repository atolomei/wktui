package io.wktui.media;

import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.request.resource.ResourceReference;

public class InvisibleImagePanel extends Image {
			
	
private static final long serialVersionUID = 1L;

private static final ResourceReference EMPTY_IMAGE = new PackageResourceReference(InvisibleImagePanel.class, null);
	
	public InvisibleImagePanel(String id) {
		super(id, EMPTY_IMAGE);
	}
	
	@Override
	protected boolean shouldAddAntiCacheParameter()	{
		return false;
	}
	
	@Override
	public boolean isVisible() {
		return false;
	}
}
