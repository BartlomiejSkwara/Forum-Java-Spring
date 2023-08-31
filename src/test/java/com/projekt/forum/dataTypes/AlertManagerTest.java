package com.projekt.forum.dataTypes;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AlertManagerTest {

    private AlertManager alertManager;
    @BeforeEach
    void prepWork(){
        alertManager = new AlertManager();
    }
    @Test
    void getAlertsEmpty() {
        Assertions.assertNotNull(alertManager);
        Assertions.assertEquals(0, alertManager.getAlerts().size());

    }

    @Test
    void addAlert() {
        alertManager.addAlert(new Alert("sd", Alert.AlertType.WARNING));
        alertManager.addAlert(new Alert("sda", Alert.AlertType.WARNING));

        Assertions.assertNotNull(alertManager);
        Assertions.assertTrue( alertManager.getAlerts().size()>0);

    }
    @Test
    void getAlertsAndClear() {
        alertManager.addAlert(new Alert("sd", Alert.AlertType.WARNING));
        alertManager.addAlert(new Alert("sda", Alert.AlertType.WARNING));
        Assertions.assertNotNull(alertManager);
        Assertions.assertTrue(alertManager.getAlertsAndClear().size()>0);
        Assertions.assertEquals( 0,alertManager.getAlerts().size());

    }


    @Test
    void clearAlerts() {
        alertManager.addAlert(new Alert("sd", Alert.AlertType.WARNING));
        alertManager.addAlert(new Alert("sda", Alert.AlertType.WARNING));
        Assertions.assertNotNull(alertManager);
        alertManager.clearAlerts();
        Assertions.assertEquals( 0,alertManager.getAlerts().size());
    }
}