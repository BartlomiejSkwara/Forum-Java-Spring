package com.projekt.forum.dataTypes;

import java.util.ArrayList;

public class AlertManager {
    private ArrayList<Alert> alerts;

    public AlertManager(){
        alerts = new ArrayList<Alert>();
    }
    public ArrayList<Alert> getAlerts() {
        return alerts;
    }
    public int size(){
        return alerts.size();
    }
    public ArrayList<Alert> getAlertsAndClear(){
        ArrayList<Alert> temp = new ArrayList<>(alerts);
        clearAlerts();
        return temp;
    }
    public ArrayList<Alert> getErrorsAndClear(){
        ArrayList<Alert> temp = new ArrayList<>();
        alerts.forEach((alert)->{
            if(alert.getType().equals(Alert.AlertType.DANGER)){
                temp.add(alert);
            }
        });

        clearAlerts();
        return  temp;
    }
    public void addAlert(Alert alert){
        alerts.add(alert);
    }
    public void clearAlerts(){
        alerts.clear();
    }
}
