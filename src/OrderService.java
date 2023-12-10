import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class OrderService {

	public static Order createOrder() {
	    try (Connect connection = Connect.getConnection()) {
	        // Insert a new order with status 'Pending'
	        String insertOrderQuery = "INSERT INTO orders (status) VALUES ('Pending')";
	        try (PreparedStatement preparedStatement = connection.prepare(insertOrderQuery)) {
	            preparedStatement.executeUpdate();
	            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
	            if (generatedKeys.next()) {
	                int orderId = generatedKeys.getInt(1);
	                return new Order(orderId, "Pending", new ArrayList<>());
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } catch (Exception e1) {
	        e1.printStackTrace();
	    }
	    return null;
	}


	public static void addItemToOrder(Order order, MenuItem menuItem, int quantity) {
	    try (Connect connection = Connect.getConnection()) {
	        // Insert order item
	        String insertOrderItemQuery = "INSERT INTO order_items (order_id, menu_item_id, quantity) VALUES (?, ?, ?)";
	        try (PreparedStatement preparedStatement = connection.prepare(insertOrderItemQuery)) {
	            // Pastikan order_id yang akan dimasukkan sudah ada di tabel orders
	            if (orderExists(connection, order.getId())) {
	                preparedStatement.setInt(1, order.getId());
	                preparedStatement.setInt(2, menuItem.getId());
	                preparedStatement.setInt(3, quantity);
	                preparedStatement.executeUpdate();

	                // Update order object
	                OrderItem orderItem = new OrderItem(menuItem, quantity);
	                order.getOrderItems().add(orderItem);
	            } else {
	                System.out.println("Order does not exist.");
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } catch (Exception e1) {
	        e1.printStackTrace();
	    }
	}

	private static boolean orderExists(Connect connection, int orderId) throws SQLException {
	    // Periksa apakah orderId sudah ada di tabel orders
	    String checkOrderQuery = "SELECT * FROM orders WHERE id = ?";
	    try (PreparedStatement checkOrderStatement = connection.prepare(checkOrderQuery)) {
	        checkOrderStatement.setInt(1, orderId);
	        try (ResultSet resultSet = checkOrderStatement.executeQuery()) {
	            return resultSet.next();
	        }
	    }
	}




    public static void submitOrder(Order order) {
        try (Connect connection = Connect.getConnection()) {
            // Update order status to 'Submitted'
            String updateOrderQuery = "UPDATE orders SET status = 'Submitted' WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepared(updateOrderQuery, 0)) {
                preparedStatement.setInt(1, order.getId());
                preparedStatement.executeUpdate();

                // Update order object
                order.setStatus("Submitted");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }
}
