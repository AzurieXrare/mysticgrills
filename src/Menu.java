import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

public class Menu {

    private Main mainApp;
    private Order currentOrder;
    private List<MenuItem> menuItems;

    public Menu(Main mainApp, Order currentOrder) {
        this.mainApp = mainApp;
        this.currentOrder = currentOrder;
        this.menuItems = createDummyMenu(); // Replace with your actual menu items
    }

    public GridPane getGrid() {
        GridPane grid = new GridPane();
        grid.setAlignment(javafx.geometry.Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label menuLabel = new Label("Menu Screen");

        // Display menu items with name and quantity input fields
        for (int i = 0; i < menuItems.size(); i++) {
            MenuItem menuItem = menuItems.get(i);

            TextField itemNameField = new TextField();
            itemNameField.setPromptText("Item Name");

            TextField quantityField = new TextField();
            quantityField.setPromptText("Quantity");

            Button addToOrderButton = new Button("Add to Order");
            addToOrderButton.setOnAction(e -> {
                // Add the selected menu item and quantity to the order
                addToOrder(itemNameField.getText(), menuItem, parseQuantity(quantityField.getText()));
            });

            grid.add(itemNameField, 0, i + 1);
            grid.add(quantityField, 1, i + 1);
            grid.add(addToOrderButton, 2, i + 1);
        }

        Button viewOrderButton = new Button("View Order");
        viewOrderButton.setOnAction(e -> {
            mainApp.showOrderSummary(currentOrder);
        });

        grid.add(menuLabel, 0, 0, 3, 1); // Span multiple columns for the label
        grid.add(viewOrderButton, 0, menuItems.size() + 2, 3, 1); // Span multiple columns for the button

        return grid;
    }

    private List<MenuItem> createDummyMenu() {
        // Replace this with actual menu item creation logic
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem(1, "Item 1", 10.99));
//        menuItems.add(new MenuItem(2, "Item 2", 8.99));
        // Add more menu items as needed
        return menuItems;
    }

    private void addToOrder(String itemName, MenuItem menuItem, int quantity) {
        if (quantity > 0) {
            currentOrder.addOrderItem(new MenuItem(menuItem.getId(), itemName, menuItem.getPrice()), quantity);
            System.out.println("Added to order: " + itemName + " - Quantity: " + quantity);
        } else {
            System.out.println("Invalid quantity.");
        }
    }

    private int parseQuantity(String quantityText) {
        try {
            return Integer.parseInt(quantityText);
        } catch (NumberFormatException e) {
            return -1; // Indicates an invalid quantity
        }
    }
}
