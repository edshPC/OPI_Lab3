package utils;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.ConverterException;
import jakarta.faces.convert.FacesConverter;

@FacesConverter("customNumberConverter")
public class CustomNumberConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        s = s.replaceAll(",", ".");
        if(s.length() >= 16){
            FacesMessage msg =
                    new FacesMessage("Must be less than 16 characters long",
                            "Must be less than 16 characters long");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ConverterException(msg);
        }
        try
        {
            return Double.parseDouble(s);
        }
        catch(NumberFormatException e)
        {
            FacesMessage msg =
                    new FacesMessage("Must be a number",
                            "Must be a number");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ConverterException(msg);        }
    }


    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        if(o != null){
            return o.toString();
        }
        return "";
    }
}
