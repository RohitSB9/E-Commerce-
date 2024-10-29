public class CartItem
{

    private String    productOptions;
	private Product 	product;


    public CartItem(Product product, String productOptions)
	{
		this.product = product;
		this.productOptions = productOptions;
	}
    

    public Product getProduct()
	{
		return product;
	}
    
    public void setProduct(Product product)
	{
		this.product = product;
	}

    public String getproductOptions()
    {
        return productOptions;
    }

	public void setproductOptions(String productOptions)
	{
		this.productOptions = productOptions;
	}

    public void print()
	{
		System.out.printf("\nProduct Name: %12s Options: %8s", product.getName(), productOptions);
	}

    public boolean equals(Object other)
	{
		// Compare two Carttiem objects based on their product objects
		CartItem otheritem = (CartItem) other;
		return this.product.equals(otheritem.product);
	}
}
