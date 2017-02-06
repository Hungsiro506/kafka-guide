package Producer.CustomeSerializer;

public class Customer {
	private int customerID;
	private String customerName;
	public Customer(int ID,String name){
		this.customerID = ID;
		this.customerName = name;
	}
	public int getID(){
		return this.customerID;
	}
	public void setID(int ID){
		this.customerID = ID;
	}
	public String getName(){
		return this.customerName;
	}

}
