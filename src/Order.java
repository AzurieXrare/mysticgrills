import java.util.List;

public class Order {
    private int id;
    private String status;
    private List<OrderItem> orderItems;
	public Order(int id, String status, List<OrderItem> orderItems) {
		super();
		this.id = id;
		this.status = status;
		this.orderItems = orderItems;
	}
	public Order() {
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	
	 public void addOrderItem(MenuItem menuItem, int quantity) {
	        // Use OrderService to add an item to the order
	        OrderService.addItemToOrder(this, menuItem, quantity);
	    }

	    public void submitOrder() {
	        // Use OrderService to submit the order
	        OrderService.submitOrder(this);
	    }
    
}
