import java.util.ArrayList;

public class Cart
{
    private ArrayList<CartItem> cartitems;
    private CartItem cItem;
    private String customerId;

    public Cart(CartItem cItem, String customerId)
    {
        this.cItem = cItem;
        this.customerId = customerId;
    }


	public ArrayList<CartItem> getcart()
	{
		return cartitems;
	}
	
	public ArrayList<CartItem> addtocart(CartItem cItem)
	{
		cartitems.add(cItem);
		return cartitems;
	}

	public ArrayList<CartItem> removefromcart(CartItem cItem)
	{
		cartitems.remove(cItem);
		return cartitems;
	}

    public String getcustomerId()
    {
        return customerId;
    }
    public void setCustomerId(String customerId)
    {
        this.customerId = customerId;
    }

    public CartItem getcartitem(CartItem cItem)
	{
		return cItem;
	}
	public void setcartitem(CartItem cItem)
	{
		this.cItem = cItem;
	}

    public void print()
	{
		System.out.printf("\nCartitems:  %3s CustomerId: %3s Product: ", this.customerId, this.getcartitem(cItem));
	}
}
