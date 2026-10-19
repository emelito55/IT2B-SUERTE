
package com.suerte.model;

public record ScenarioEntity(
        int id,
        String deviceName,
        String assignedTo,
        String serialNo,
        String condition
) {
}
