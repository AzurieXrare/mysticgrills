import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class OrderSummary {

    private Main mainApp;
    private Order currentOrder;

    public OrderSummary(Main mainApp, Order currentOrder) {
        this.mainApp = mainApp;
        this.currentOrder = currentOrder;
    }

    public GridPane getGrid() {
        GridPane grid = new GridPane();
        grid.setAlignment(javafx.geometry.Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label orderSummaryLabel = new Label("Order Summary");
        // Display order items and details here

        Button submitOrderButton = new Button("Submit Order");
        Button backToMenuButton = new Button("Back to Menu");

        submitOrderButton.setOnAction(e -> {
            // Add logic to submit the order
            currentOrder.submitOrder();
            mainApp.showMenuScreen(new Order(0, null, null));
        });

        backToMenuButton.setOnAction(e -> {
            mainApp.showMenuScreen(currentOrder);
        });

        grid.add(orderSummaryLabel, 0, 0);
        // Display order details in the grid

        grid.add(submitOrderButton, 0, 1);
        grid.add(backToMenuButton, 1, 1);

        return grid;
    }
}
