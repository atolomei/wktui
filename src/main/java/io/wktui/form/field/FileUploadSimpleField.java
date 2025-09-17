package io.wktui.form.field;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.resource.UrlResourceReference;
import org.apache.wicket.util.file.Files;
import org.apache.wicket.util.value.IValueMap;

import io.wktui.form.FormState;
import io.wktui.media.InvisibleImage;
import wktui.base.InvisiblePanel;
import wktui.base.Logger;


public class FileUploadSimpleField<T> extends Field<T> {
            
	private static final long serialVersionUID = 1L;

	static private Logger logger = Logger.getLogger(FileUploadSimpleField.class.getName());
	
    private FileUploadField input;
	 
	
	private WebMarkupContainer thumbnailContainer;
	private WebMarkupContainer imageContainer;

	private WebMarkupContainer fileNameContainer;
	
	private String workDir;
	private String fileName = null;

	private  boolean b_thumbnail = true;
	
	public FileUploadSimpleField(String id, IModel<String> label) {
		super(id, null, label);
		setOutputMarkupId(true);
	}

	
	public FileUploadSimpleField(String id, IModel<T> model, IModel<String> label) {
		super(id, model, label);
		setOutputMarkupId(true);
	}

	//public void setValue(T value) {
    //    this.value=value;
	//}
	
	//public T getValue() {
	 //   return value;
	//}
	
	@Override
	public void editOn() {
		this.input.setEnabled(true);
		super.editOn();
	}

	@Override
	public void editOff() {
		this.input.setEnabled(false);
		super.editOff();
	}
	
	@Override
	public void onBeforeRender() {
		super.onBeforeRender();
	
	}
	
	@Override
	public void onInitialize() {
	        super.onInitialize();
	  	 
	        this.input = newFileUploadField();
	        super.addControl(input);
	        
	        thumbnailContainer = new WebMarkupContainer("thumbnailContainer") {
	        	private static final long serialVersionUID = 1L;
				public boolean isVisible() {
	        		return isThumbnail() && (getImage()!=null);
	        	}
	        };
	        
	        imageContainer = new WebMarkupContainer("imageContainer") {
	        	private static final long serialVersionUID = 1L;
				public boolean isVisible() {
	        		return isThumbnail() && (getImage()!=null);
	        	}
	        };

	        fileNameContainer = new WebMarkupContainer("fileNameContainer") {
	        	private static final long serialVersionUID = 1L;
				public boolean isVisible() {
	        		return isThumbnail() && (getImage()!=null && getFileName()!=null);
	        	}
	        };
	        
	   	 if (getImage()==null) {
	   		    Image image = new Image("image", new UrlResourceReference(Url.parse("")));
	   		    image.setVisible(false);
	   		    imageContainer.addOrReplace(image);
	      }
		 else {
			   	imageContainer.addOrReplace(getImage());
		 }
		 
	   	 Label fileNameLabel = new Label("fileName", new Model<String>() {
			private static final long serialVersionUID = 1L;
			 public String getObject() {
	   			 return getFileName()!=null?getFileName():"";
	   		 }
	   	 });
		 fileNameLabel.setEscapeModelStrings(false);
		 fileNameContainer.addOrReplace(fileNameLabel);

        thumbnailContainer.add(imageContainer);
        thumbnailContainer.add(fileNameContainer);
	        
        super.addControl(thumbnailContainer);

	}
	
	public Image getImage() {
		return null;
	}
	
	
	public void setThumbnail(  boolean b_thumbnail ) {
		this.b_thumbnail= b_thumbnail;
	}
	
	public boolean isThumbnail() {
		return  b_thumbnail;
	}

	
	@Override
	public void onConfigure() {
		super.onConfigure();
	}
	
	/**
	 * @return
	 */
    protected FileUploadField newFileUploadField() {
	        
    	FileUploadField input = new FileUploadField("input") {
	        
	            private static final long serialVersionUID = 1L;

	            @Override
	            public void validate() {
                    FileUploadSimpleField.this.validate();
	                super.validate();
	            }
	            @Override
	            public boolean isEnabled() {
	                return FileUploadSimpleField.this.isEditMode();
	            }
	            
	            protected void onComponentTag(final ComponentTag tag) {
                    
	                IValueMap attributes = tag.getAttributes();
	                
	                if (getInputType()!=null)           
	                    attributes.put("type",  getInputType());
	                else
	                    attributes.put("type",  "file");
	                    
	                if (getAutoComplete()!=null)
	                    attributes.put("autocomplete", getAutoComplete());
	                
	                if (autofocus())
	                    attributes.putIfAbsent("autofocus", "");

	                super.onComponentTag(tag);
	            }

	            @Override
	            public String getInputName() {
	                
	                String overridedName = FileUploadSimpleField.this.getInputName();
	                
	                if(overridedName != null)
	                    return overridedName;

	                return super.getInputName();
	            }
	            
	            @Override
	            public boolean isVisible() {
	            	return (FileUploadSimpleField.this.isEditMode() || (getImage()==null));
	            }
	        };

	        input.setOutputMarkupId(true);

	        input.setLabel(new Model<String>("subir"));
	        
	        
	        if (getTabIndex()>0)
	            input.add(new AttributeModifier("tabindex", getTabIndex()));
	        
	        
	         try {
	            if (getPlaceHolderLabel()!=null && getPlaceHolderLabel().getObject()!=null) 
	                input.add(new AttributeModifier("placeholder", getPlaceHolderLabel()));
	         }
	         catch (java.util.MissingResourceException e) {
	            logger.debug(e.getClass().getName() + " | " +  Thread.currentThread().getStackTrace()[1].getMethodName()+ " |  id. " + FileUploadSimpleField.this.getId());
	         }
	         
	        return input;
	    }

    protected void onUpdate(T oldvalue, T newvalue) {
		if (getEditor()!=null) {
			getEditor().setUpdatedPart(getPart());
		}
	}
    protected String getPart() {
    	return getFieldUpdatedPartName();
	}
    
    protected IModel<String> getPlaceHolderLabel() {
        return getLabel("file");
    }

    protected Object getAutoComplete() {
        return null;
    }

    protected String getInputName() {
        return null;
    }

    public void validate() {
        logger.debug("validate " + getId());
    }

    @Override
    public Component getInput() {
        return input;
    }
    
    
    public Object getInputValue() {
    	return input.getValue();
    }
    
    
    public List<InputStream> getInputStreams() {

    	List<InputStream> list = new ArrayList<InputStream>();
    	
    	 List<FileUpload> uploads = input.getFileUploads();
    	 if (uploads != null && !uploads.isEmpty()) {
    		 
    		  for (FileUpload upload : uploads) {
    			  try {
					list.add( upload.getInputStream() );
				  } catch (IOException e) {

					  e.printStackTrace();
				  }
    		  }
    	 }
    	 return list;
    }

    public void close() {
    	
    	if (input==null)
    		return;
    	
    	 List<FileUpload> uploads = input.getFileUploads();
    
    	 if (uploads != null && !uploads.isEmpty()) {
    		 for (FileUpload upload : uploads) {
   					upload.closeStreams();
	   		  }
    	 }
    }
    
    
    @Override
    public void updateModel() {

    	logger.debug( "update model -> " + getId() );

      
  
        try {
        
            this.input.updateModel(); 
            
            List<FileUpload> uploads = input.getFileUploads();

            if (processFileUploads(uploads)) {
                logger.debug( "update -> " + getId() + ": " + this.getFileName());
                onUpdate(null, null);
            }
        } 
        catch (Exception e) {
            logger.error(e,  getInput()!=null? getInput().toString(): "");
            if (getModel()!=null)
            	getModel().detach();
        }
    }

       
    protected boolean processFileUploads(List<FileUpload> uploads) {
    	
        if (uploads != null && !uploads.isEmpty()) {
        
        	for (FileUpload upload : uploads) {
            
        		try {
                
                	logger.debug("name -> " + upload.getClientFileName());
                	logger.debug("Size -> " + upload.getSize());
                	
                	File tempFile = upload.writeToTempFile();
                    logger.debug("Uploaded file saved to: " + tempFile.getAbsolutePath());
                    return true;
                    
                } catch (Exception e) {
                    error("Error saving file: " + e.getMessage());
                    return false;
                }
            }
        } else {
            info("No file uploaded.");
            return false;
        }
	
        return false;
        
    }

	protected String getInputType() {
        return "file";
    }

	public String getWorkDir() {
		return workDir;
	}

	public void setWorkDir(String workDir) {
		this.workDir = workDir;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

} 