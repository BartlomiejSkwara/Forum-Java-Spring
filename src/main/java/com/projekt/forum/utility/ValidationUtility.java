package com.projekt.forum.utility;


import com.projekt.forum.dataTypes.Alert;
import com.projekt.forum.dataTypes.AlertManager;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

@Component
public class ValidationUtility {
    public static final String URL_REGEX = "^(?!.*[\\\"'<>/\\\\\\\\]|.*&#(?:34|38|39|60|62);).*$";
    public static final String UNIVERSAL_REGEX = "^(?!.*[\"'<>/]|.*&#(?:34|38|39|60|62);).*$";


    public boolean ConvertValidationErrors(BindingResult bindingResult, AlertManager alertManager){
        for(ObjectError err:bindingResult.getAllErrors()){
            alertManager.addAlert(new Alert(err.getDefaultMessage(), Alert.AlertType.DANGER));
        }
        return alertManager.getAlerts().size()==0;
    }

}
