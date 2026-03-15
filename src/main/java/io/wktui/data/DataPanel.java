package io.wktui.data;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;

 
 
import io.wktui.form.field.Field;
import io.wktui.form.util.TitlePanel;
import wktui.base.InvisiblePanel;
import wktui.base.LabelPanel;
import wktui.base.Logger;
import wktui.base.ModelPanel;


/***
 */
public abstract class DataPanel<T> extends ModelPanel<T> {

    private static final long serialVersionUID = 1L;
    
    static private Logger logger = Logger.getLogger(DataPanel.class.getName());
    
    private String titleCss;
    private WebMarkupContainer containerCol;
    private WebMarkupContainer containerBorder;
    private Panel helpPanel;
    private  String css;
    private  Component data;
  	
    private IModel<String> panelTitleModel;
    private Model<String> panelSubtitleModel;

    
    
    public DataPanel(String id) {
       this(id, null, null);
    }
    
    public DataPanel(String id, IModel<T> model, IModel<String> labelModel) {
        super(id, model);

        setTitleModel(labelModel);

        containerCol = new WebMarkupContainer("containerCol");
        add(containerCol);

        
        containerBorder = new WebMarkupContainer("containerBorder");
        containerCol.add(containerBorder);
    }

    private int tab_index = -1;
    public int getTabIndex() {
        return this.tab_index;
    }

    public void setTabIndex(int t_index) {
        this.tab_index = t_index;
    }


	public String getTitleCss() {
		return titleCss;
	}

	public void setTitleCss(String titleCss) {
		this.titleCss = titleCss;
	}
    
   @Override
   public void onInitialize() {
       super.onInitialize();

       if (getTitleModel() != null) {
       	TitlePanel<String> t= new TitlePanel<String>("titleMarkupContainer", getTitleModel());
           if (getTitleCss()!=null)
          	t.setCss(getTitleCss());
           containerBorder.add(t);
       } else
           containerBorder.add(new InvisiblePanel("titleMarkupContainer"));

       if (getSubtitleModel() != null) {
           LabelPanel subTitleLabel = new LabelPanel("subtitleMarkupContainer", getSubtitleModel());
           containerBorder.add(subTitleLabel);
       } else
           containerBorder.add(new InvisiblePanel("subtitleMarkupContainer"));

       containerBorder.add(new InvisiblePanel("textBeforeMarkupContainer"));
       containerBorder.add(new InvisiblePanel("textAfterMarkupContainer"));
       containerBorder.setOutputMarkupId(true);
       
       if( getHelpPanel()==null)
       	containerBorder.add(new InvisiblePanel("help"));
       else
       	containerBorder.add(getHelpPanel());

       
    
        	
       containerBorder.add(new InvisiblePanel("errorMarkupContainer"));
   }
   
   
   public Panel getHelpPanel() {
   	return helpPanel;
   }
   
	public void setHelpPanel(Panel panel) {
		if (!panel.getId().equals("help"))
   		throw new RuntimeException("id must be -> 'help'");
		
		helpPanel=panel;
   	containerBorder.addOrReplace(helpPanel);
   	
	}


	public void setCss( String css) {
		this.css=css;
		
		if (this.data!=null) {
			this.data.add( new AttributeModifier("class", css));
		}
	}
	

    
    
	public String getCss() {
		return css;
	}
   public void addControl(WebMarkupContainer input) {
   	this.data=input;
   	if (getCss()!=null)
   		this.data.add( new AttributeModifier("class", getCss()));
       containerBorder.addOrReplace(input);
   }

   public void setTitleModel(IModel<String> titleModel) {
       this.panelTitleModel = titleModel;
   }

   public IModel<String> getTitleModel() {
       return this.panelTitleModel;
   }

   public void setSubtitleModel(Model<String> titleModel) {
       this.panelSubtitleModel = titleModel;
   }

   public IModel<String> getSubtitleModel() {
       return this.panelSubtitleModel;
   }

  
  
	@Override
    public MarkupContainer add(final Component... children) {
    	//for (Component child : children) {
    		//if (child instanceof Field)
    		//	((Field<T>) child).editOff();
    	//}
    	return super.add( children );
    }

     
    public Component getData() {
        return data;
    }

   
	public abstract void reload();

    
    @Override
    public void renderHead(final IHeaderResponse response) {
        super.renderHead(response);
    }
    
    @Override
    protected void onComponentTag(final ComponentTag tag) {
        super.onComponentTag(tag);
    } 
    
    
	public void updateReload() {
		this.visitChildren(Field.class, new IVisitor<Field<?>, Void>() {
			@Override
			public void component(Field<?> field, IVisit<Void> visit) {
				field.reload();
			}
		});
	}
	
	public void updateModel() {
		this.visitChildren(Field.class, new IVisitor<Field<?>, Void>() {
			@Override
			public void component(Field<?> field, IVisit<Void> visit) {
				field.updateModel();
			}
		});

	}
		
}

