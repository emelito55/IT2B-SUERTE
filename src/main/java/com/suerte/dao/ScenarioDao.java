
package com.suerte.dao;

import com.suerte.model.ScenarioEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScenarioDao {

    public boolean addAsset(ScenarioEntity asset) {
        String sql = """
                INSERT INTO assets
                    (device_name, assigned_to, serial_no, condition)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, asset.deviceName());
            statement.setString(2, asset.assignedTo());
            statement.setString(3, asset.serialNo());
            statement.setString(4, asset.condition());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            if (e.getMessage() != null
                    && e.getMessage().toLowerCase().contains("unique")) {
                System.out.println("ERROR: Serial number already exists.");
            } else {
                System.out.println("Database error: " + e.getMessage());
            }

            return false;
        }
    }

    public List<ScenarioEntity> getAllAssets() {
        List<ScenarioEntity> assets = new ArrayList<>();

        String sql = """
                SELECT id, device_name, assigned_to,
                       serial_no, condition
                FROM assets
                ORDER BY id
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {
                assets.add(new ScenarioEntity(
                        result.getInt("id"),
                        result.getString("device_name"),
                        result.getString("assigned_to"),
                        result.getString("serial_no"),
                        result.getString("condition")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }

        return assets;
    }
}
