package com.suerte;

import com.suerte.dao.DatabaseConnection;
import com.suerte.ui.ScenarioMenu;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        DatabaseConnection.initializeDatabase();

        try (Scanner scanner = new Scanner(System.in)) {
            ScenarioMenu menu = new ScenarioMenu(scanner);
            menu.showMenu();
        }
    }
}
