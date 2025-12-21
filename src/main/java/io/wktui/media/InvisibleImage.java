package io.wktui.media;

import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.request.resource.ResourceReference;

public class InvisibleImage extends Image {
			
	
private static final long serialVersionUID = 1L;

private static final ResourceReference EMPTY_IMAGE = new PackageResourceReference(InvisibleImage.class, "null");
	
	public InvisibleImage(String id) {
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
