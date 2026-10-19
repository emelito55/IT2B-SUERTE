package com.suerte.ui;

import com.suerte.dao.ScenarioDao;
import com.suerte.model.ScenarioEntity;

import java.util.List;
import java.util.Scanner;

public class ScenarioMenu {

    private final ScenarioDao scenarioDao = new ScenarioDao();
    private final Scanner scanner;

    public ScenarioMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    public void showMenu() {
        boolean running = true;

        while (running) {
            printMainMenu();

            int choice = readInt("Enter choice: ");

            switch (choice) {
                case 1 -> addNewAsset();
                case 2 -> viewIssuedAssets();
                case 3 -> {
                    System.out.println("\nExiting system... Thank you!");
                    running = false;
                }
                default -> System.out.println(
                        "\nInvalid choice! Please select 1, 2, or 3."
                );
            }
        }
    }

    private void printMainMenu() {
        System.out.println("\n====================================================");
        System.out.println("             IT ASSET EQUIPMENT TRACKER");
        System.out.println("====================================================");
        System.out.println("[1] Add New Asset");
        System.out.println("[2] View Issued Assets");
        System.out.println("[3] Exit System");
        System.out.println("====================================================");
    }

    private void addNewAsset() {
        System.out.println("\n--- ADD NEW IT ASSET ---");

        String deviceName = readText("Enter Device Name: ");
        String assignedTo = readText("Enter Assigned Employee Name: ");
        String serialNo = readText("Enter Serial Number: ");
        String condition = readCondition();

        ScenarioEntity asset = new ScenarioEntity(
                0, deviceName, assignedTo, serialNo, condition
        );

        if (scenarioDao.addAsset(asset)) {
            System.out.println("\nSUCCESS: IT asset logged into system!");
        } else {
            System.out.println(
                    "\nFAILED: Asset was not saved. Please try again."
            );
        }
    }

    private void viewIssuedAssets() {
        List<ScenarioEntity> assets = scenarioDao.getAllAssets();

        System.out.println("\n");
        System.out.println(
                "=============================================================="
                        + "=================="
        );
        System.out.println("                         IT EQUIPMENT ASSETS");
        System.out.println(
                "=============================================================="
                        + "=================="
        );

        System.out.printf(
                "| %-3s | %-20s | %-20s | %-14s | %-15s |%n",
                "ID", "DEVICE NAME", "ASSIGNED TO",
                "SERIAL NO", "CONDITION"
        );

        System.out.println(
                "+-----+----------------------+----------------------+"
                        + "----------------+-----------------+"
        );

        for (ScenarioEntity asset : assets) {
            System.out.printf(
                    "| %-3d | %-20s | %-20s | %-14s | %-15s |%n",
                    asset.id(),
                    shorten(asset.deviceName(), 20),
                    shorten(asset.assignedTo(), 20),
                    shorten(asset.serialNo(), 14),
                    asset.condition()
            );
        }

        System.out.println(
                "+-----+----------------------+----------------------+"
                        + "----------------+-----------------+"
        );

        System.out.println("Total Records Displayed: " + assets.size());
    }

    private String readCondition() {
        while (true) {
            String condition = readText(
                    "Enter Condition (NEW / GOOD / REPAIR_NEEDED): "
            ).toUpperCase();

            if (condition.equals("NEW")
                    || condition.equals("GOOD")
                    || condition.equals("REPAIR_NEEDED")) {
                return condition;
            }

            System.out.println(
                    "Invalid condition! Enter NEW, GOOD, or REPAIR_NEEDED."
            );
        }
    }

    private String readText(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (!input.isEmpty()) {
                return input;
            }

            System.out.println("Input cannot be empty. Please try again.");
        }
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Enter a whole number.");
            }
        }
    }

    private String shorten(String value, int maxLength) {
        if (value.length() <= maxLength) {
            return value;
        }

        return value.substring(0, maxLength - 3) + "...";
    }
}
