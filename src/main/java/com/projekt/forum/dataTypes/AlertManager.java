package com.projekt.forum.dataTypes;

import java.util.ArrayList;

public class AlertManager {
    private ArrayList<Alert> alerts;
    private boolean containsDanger;

    public AlertManager(){
        alerts = new ArrayList<Alert>();
        containsDanger = false;
    }
    public ArrayList<Alert> getAlerts() {
        return alerts;
    }
    public int size(){
        return alerts.size();
    }

    public boolean containsDanger(){
        return containsDanger;
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
        if (alert.getType().equals(Alert.AlertType.DANGER))
            containsDanger=true;

        alerts.add(alert);
    }
    public void clearAlerts(){
        containsDanger = false;
        alerts.clear();
    }
}
