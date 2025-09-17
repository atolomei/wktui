package io.wktui.editor;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;


public interface Editor<T> {

	public boolean isReadOnly();		 		// si el editor es readonly. no se puede editar

	public List<String> getUpdatedParts();
	public void setUpdatedPart(String updatedPart);
	
	
	
	/**
	public void update(AjaxRequestTarget target);
	public void edit(AjaxRequestTarget target);
	
	public Form<?> getForm();
	
	public IModel<T> getModel();
	public T getModelObject();
	
	public void update(T object);
	
	public boolean isEditionEnabled();   		// si esta en modo edit(T) o view(F) 
	public boolean isReadOnly();		 		// si el editor es readonly. no se puede editar
	
	public boolean isFullWidth();
	
	//public List<UpdatedField> getUpdatedFields();
	//public void setUpdatedField(UpdatedField updatedField);
	
	public List<String> getUpdatedParts();
	public void setUpdatedPart(String updatedPart);
	
	*/
	
}
