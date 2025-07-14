package io.wktui.form.field;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.IFormModelUpdateListener;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.ValidationErrorFeedback;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.util.string.interpolator.VariableInterpolator;
import org.apache.wicket.validation.IErrorMessageSource;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidationError;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

import io.wktui.form.Form;
import io.wktui.form.FormState;
import io.wktui.form.util.TitlePanel;
import wktui.base.BasePanel;
import wktui.base.InvisiblePanel;
import wktui.base.LabelPanel;

/**
 * 
 * readOnly -> can not be edited editable -> the field es currently editable
 * 
 * 
 * 
 * @param <T>
 */
public abstract class Field<T> extends BasePanel implements IFormModelUpdateListener {

    private static final long serialVersionUID = 1L;

    private int tab_index = -1;

    private boolean editable = true;   
    private boolean required = false;
    private boolean feedback = false;
    private boolean helpInfo = false;
    
    private Form<?> form;
    

    public Form<?> getForm() {
        return form;
    }
    
    public boolean isRequired() {
        return required;
    }

    public boolean isHelpInfo() {
        return helpInfo;
    }
    
    public boolean isEditEnabled() {
        return editable;
    }
    
    public boolean isEditMode() {
        if (getForm()==null)
            return true;
        return getForm().getFormState()==FormState.EDIT;
    }
    
    
    /** valor original */
    public IModel<T> model;
    
    private Model<String> subtitleModel;
    
    //public abstract T getValue();
    //public abstract IModel<T> getValueModel();
    //public abstract void setValueModel(T value);
    
    private WebMarkupContainer containerCol;
    private WebMarkupContainer containerBorder;
    
    
    /** valor ingresado */
    //private IModel<T> valueModel;

    private Component inputComponent;

    private IValidator<T> validator;
    private String property;
    private Width width = Width.W18;
    private List<Behavior> behaviors;
    private boolean autofocus = false;

    public enum Width {
        W01("col-lg-1"), W02("col-lg-2"), W03("col-lg-3"), W04("col-lg-4"), W05("col-lg-5"), W06("col-lg-6"), W07("col-lg-7"),
        W08("col-lg-8"), W09("col-lg-9"), W10("col-lg-10"), W11("col-lg-11"), W12("col-lg-12"), W13("col-lg-13"), W14("col-lg-14"),
        W15("col-lg-15"), W16("col-lg-16"), W17("col-lg-17"), W18("col-lg-18");

        private String css;

        private Width(String css) {
            this.css = css;
        }

        public String getCss() {
            return css;
        }
    };


    @Override
    public void onInitialize() {
        super.onInitialize();

        containerCol = new WebMarkupContainer("containerCol");

        add(containerCol);

        // class="border pt-4 pb-4 ps-4 pe-4"
        containerBorder = new WebMarkupContainer("containerBorder");
        containerCol.add(containerBorder);

        if (getTitleModel() != null) {
            containerBorder.add(new TitlePanel<String>("titleMarkupContainer", getTitleModel()));
        } else
            containerBorder.add(new InvisiblePanel("titleMarkupContainer"));

        if (getSubtitleModel() != null) {
            LabelPanel subTitleLabel = new LabelPanel("subtitleMarkupContainer", getSubtitleModel());
            containerBorder.add(subTitleLabel);
        } else
            containerBorder.add(new InvisiblePanel("subtitleMarkupContainer"));

        containerBorder.add(new InvisiblePanel("textBeforeMarkupContainer"));
        containerBorder.add(new InvisiblePanel("textAfterMarkupContainer"));

        containerBorder.add(new InvisiblePanel("helpMarkupContainer"));
        containerBorder.add(new InvisiblePanel("feedbackMarkupContainer"));
        containerBorder.add(new InvisiblePanel("errorMarkupContainer"));
    }

    public void addControl(WebMarkupContainer input) {
        containerBorder.add(input);
    }

    private IModel<String> titleModel;
    
    /**
     * 
     * 
     * @param id
     * @param model
     */
    public Field(String id, IModel<T> model, IModel<String> label) {
        super(id);
        setProperty(id);
        
        setModel(model);
        setTitleModel( label );
    }

    

    public void setTitleModel(IModel<String> titleModel) {
        this.titleModel = titleModel;
    }

    public IModel<String> getTitleModel() {
        return this.titleModel;
    }

    

    public void setSubtitleModel(Model<String> titleModel) {
        this.subtitleModel = titleModel;
    }

    public IModel<String> getSubtitleModel() {
        return this.subtitleModel;
    }

    //public void updateModel() {
    //    if (valueModel != null)
    //        setModel(getValueModel());
    //}

    public void cancel() {
        clearInput();
        // if (getModel()!=null)
        // setValue(getModel().getObject());
    }

    public void clearInput() {
        // feedback = false;
        // getFeedbackMessages().clear();
        // if (getInput()!=null && getInput() instanceof FormComponent)
        // ((FormComponent<?>)getInput()).clearInput();
    }

    public void add(IValidator<T> validator) {
        this.validator = validator;
    }

    public IValidator<T> getValidator() {
        return validator;
    }

    public Component getInput() {
        return inputComponent;
    }

    public void setModel(IModel<T> model) {
        this.model = model;
    }

    public IModel<T> getModel() {
        return this.model;
    }

    public int getTabIndex() {
        return this.tab_index;
    }

    public void setTabIndex(int t_index) {
        this.tab_index = t_index;
    }

    public void setReadOnly(boolean readOnlyStatus) {
        this.editable =! readOnlyStatus;
    }

    public boolean isReadOnly() {
        return !this.editable;
    }

    public void setProperty(String name) {
        this.property = name;
    }

    public String getProperty() {
        return this.property;
    }

    public void setRequired(boolean value) {
        this.required = value;
    }

    public String helpIcon() {
        return "far fa-info-circle";
    }

    public boolean isNullValid() {
        return false;
    }

    // public String getMessage() {
    // if (hasErrorMessage()) {
    // ValidationErrorFeedback error =
    // (ValidationErrorFeedback)((ValidationError)getFeedbackMessages().first().getMessage()).getErrorMessage(new
    // MessageSource());
    // return (String)error.getMessage();
    // }
    // return null;
    // }

    public boolean hasFeedback() {
        return feedback;
    }

    //public IModel<T> getValueModel() {
    //    return valueModel;
    //}

    //public void setValueModel(IModel<T> value) {
    //    valueModel = value;
    //}

    @SuppressWarnings("unchecked")
    public void setFieldValue(String value) {
        String[] values = { value };
        ((org.apache.wicket.markup.html.form.TextField<T>) getInput()).setModelValue(values);
    }

    public Width getWidth() {
        return width;
    }

    public void setWidth(Width width) {
        this.width = width;
    }

    public void setError(ValidationError error) {
        error(error);
        feedback = true;
    }

    public void addInputBehavior(Behavior behavior) {
        if (behaviors == null)
            behaviors = new ArrayList<Behavior>();
        behaviors.add(behavior);
    }

    @Override
    public void onBeforeRender() {
        super.onBeforeRender();

        // if (getModel() == null) {
        // Editor<?> editor = getEditor();
        // if (editor!=null) {
        // IModel<T> model = new PropertyModel<T>(editor.getModel(), getProperty());
        // setModel(model);
        // setValue(model.getObject());
        // }
        // }
    }

    @Override
    public void onAfterRender() {
        super.onAfterRender();
        setAutoFocus(false);
    }

    @Override
    public void onDetach() {
        // if (value!=null && (!(value instanceof Serializable))) {
        // valuemodel = getModel(value);
        // valuemodel.detach();
        // value = null;
        // }
        super.onDetach();

        //if (valueModel != null)
        //    valueModel.detach();

        if (model != null)
            model.detach();
    }

    public void addBehaviors() {
        if (behaviors != null) {
            for (Behavior behavior : behaviors) {
                getInput().add(behavior);
            }
        }
    }

    public void setAutoFocus(boolean value) {
        autofocus = value;
    }

    /**
     * protected IModel<T> getModel(T value) { if (value instanceof Identifiable) {
     * return new ObjectModel<T>(value); } return null; }
     **/

    protected boolean autofocus() {
        return autofocus;
    }

    /**
     * public void validate() { feedback = false; getFeedbackMessages().clear();
     * final Object input = getInputValue(); if (isRequired() && (input==null ||
     * "".equals(input))) { error((new
     * ValidationError()).addKey("requiredvalidator.message")); feedback = true;
     * return; } if (validator!=null) { IValidatable<T> validatable = new
     * IValidatable<T>() { @SuppressWarnings("unchecked") public T getValue() {
     * return (T)input; }; public void error(IValidationError error) {
     * Field.this.error(error); feedback = true; }; public boolean isValid() {
     * return true; } public IModel<T> getModel() { return null; } };
     * validator.validate(validatable); } }
     **/

    /**
     * public void validateModel() { getFeedbackMessages().clear(); final Object
     * input = getModel().getObject(); if (isRequired() && (input==null ||
     * "".equals(input))) { error((new
     * ValidationError()).addKey("requiredvalidator.message")); feedback = true;
     * return; } if (validator!=null) { IValidatable<T> validatable = new
     * IValidatable<T>() { @SuppressWarnings("unchecked") public T getValue() {
     * return (T)input; }; public void error(IValidationError error) {
     * Field.this.error(error); feedback = true; }; public boolean isValid() {
     * return true; } public IModel<T> getModel() { return null; } };
     * validator.validate(validatable); } }
     * 
     * protected Object getInputValue() { return
     * ((FormComponent<?>)getInput()).getInput(); }
     * 
     * protected void setFeedback() { this.feedback = true; }
     * 
     * protected void setFeedback(boolean value) { this.feedback = value; }
     **/

    /**
     * 
     * @Override protected void onComponentTag(ComponentTag tag) {
     *           super.onComponentTag(tag); if (Field.this.hasFeedback()) { if
     *           (Field.this.hasErrorMessage()) { tag.put("class",
     *           (tag.getAttribute("class")!=null ? tag.getAttribute("class"): "" )
     *           +" has-error has-feedback"); } else { tag.put("class",
     *           (tag.getAttribute("class")!=null ? tag.getAttribute("class"): "" )
     *           + " has-success has-feedback"); } } else { tag.put("class",
     *           tag.getAttribute("class")); } }
     **/

    // protected void setValueModel(IModel<T> model) {
    // this.valuemodel = model;
    // }

    // protected IModel<T> getValueModel() {
    // return this.valuemodel;
//	}

    // protected boolean isRequiredMark() {
    // return isRequired();
    // }

    /**
     * private IModel<String> getLabel(){ try { return new
     * StringResourceModel("property."+getProperty(), this, null); } catch
     * (java.util.MissingResourceException e) { return new
     * Model<String>(getProperty()); } catch (Exception e2) { return new
     * Model<String>(getProperty()); } }
     **/
}
