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
    public void addAlert(Alert alert){
        alerts.add(alert);
    }
    public void clearAlerts(){
        alerts.clear();
    }
}
