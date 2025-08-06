package wktui.base;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;


public class LogoPanel<T> extends ModelPanel<T> {

	private static final long serialVersionUID = 1L;
	
	Image logo;
	Link<T> imageLink;
	

	public LogoPanel(String id,  Image logo) {
		super(id, null);
		this.logo=logo;
	}

	
	public LogoPanel(String id, IModel<T> model, Image logo) {
		super(id, model);
		this.logo=logo;
	}

	
	@Override
	public void onInitialize() {
		super.onInitialize();

		
		  Link<T> imageLink = new Link<>("link", getModel()) {
	            private static final long serialVersionUID = 1L;
	            @Override
	            public void onClick() {
	            	LogoPanel.this.onClick();
	            }
	        };
	        add(imageLink);

	    
	        imageLink.add(logo);
	        
	
		
	
	}

	  public void onClick() {

	    }
	
}
