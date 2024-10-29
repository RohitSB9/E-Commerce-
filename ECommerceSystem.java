import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.ProtocolFamily;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;



/*
 * Models a simple ECommerce system. Keeps track of products for sale, registered customers, product orders and
 * orders that have been shipped to a customer
 */
public class ECommerceSystem
{
	TreeMap<String, Product> products = new TreeMap<String, Product>();
	HashMap<Product, Integer> statistacs = new HashMap<Product, Integer>();
	ArrayList<Customer> customers = new ArrayList<Customer>();	

	ArrayList<ProductOrder> orders   			= new ArrayList<ProductOrder>();
	ArrayList<ProductOrder> shippedOrders = new ArrayList<ProductOrder>();
	ArrayList<CartItem> cartitems = new ArrayList<CartItem>();

	// These variables are used to generate order numbers, customer id's, product id's 
	int orderNumber = 500;
	int customerId = 900;
	int productId = 700;

	// General variable used to store an error message when something is invalid (e.g. customer id does not exist)  
	String errMsg = null;

	// Random number generator
	Random random = new Random();

	public ECommerceSystem()
	{
		products = filereader("products.txt");
		
	

		// Create some customers
		customers.add(new Customer(generateCustomerId(),"Inigo Montoya", "1 SwordMaker Lane, Florin"));
		customers.add(new Customer(generateCustomerId(),"Prince Humperdinck", "The Castle, Florin"));
		customers.add(new Customer(generateCustomerId(),"Andy Dufresne", "Shawshank Prison, Maine"));
		customers.add(new Customer(generateCustomerId(),"Ferris Bueller", "4160 Country Club Drive, Long Beach"));
	}
	private TreeMap<String, Product> filereader(String Filename)
	{
		ArrayList<Product> product = new ArrayList<>();
		ArrayList<String> lines =  new ArrayList<String>();
		File myFile = new File(Filename); 
		try 
		{
			Scanner in = new Scanner(myFile);
			while (in.hasNextLine()) 
			{
				String line = in.nextLine();		
				line = line.trim();					// Check if line is not empty, if not then add to the Arraylist lines
				if (!line.isEmpty())
				{
					lines.add(line);
				}
			}
			// iterate through the arraylist lines and create products based on the format of each category
			for (int i = 0; i < lines.size();i+=4)
			{
				if (lines.get(i).equals("CLOTHING")) // parse the words in line to make sure they match what is required of Product
				{									// Then add the product created to an arraylist of products.
					product.add(new Product(lines.get(i+1), generateProductId(), Double.parseDouble(lines.get(i+2)), Integer.parseInt(lines.get(i+3)), Product.Category.CLOTHING));
					
				}
				else if (lines.get(i).equals("COMPUTERS"))
				{
					product.add(new Product(lines.get(i+1), generateProductId(), Double.parseDouble(lines.get(i+2)), Integer.parseInt(lines.get(i+3)), Product.Category.COMPUTERS));
					
				}
				else if (lines.get(i).equals("FURNITURE"))
				{
					product.add(new Product(lines.get(i+1), generateProductId(), Double.parseDouble(lines.get(i+2)), Integer.parseInt(lines.get(i+3)), Product.Category.FURNITURE));
					
				}
				else if (lines.get(i).equals("BOOKS"))
				{
					int titlename = lines.get(i+4).indexOf(":", 0);
					int authorname = lines.get(i+4).indexOf(":", titlename+1);
			
					product.add(new Book(lines.get(i+1), generateProductId(), Double.parseDouble(lines.get(i+2)), Integer.parseInt(lines.get(i+3).substring(0, 1)), Integer.parseInt(lines.get(i+3).substring(2)), lines.get(i+4).substring(0, titlename), lines.get(i+4).substring(titlename+1, authorname), Integer.parseInt(lines.get(i+4).substring(authorname+1))));
					i++;
				}
			}

			in.close();
			
		} 
		catch (FileNotFoundException e) 
		{
			System.out.println("File not found");
		}
		for (Product p : product)
		{
			products.put(p.getId(), p);		// Add the products in the arraylist product to the products map, key being product id and value the product
			statistacs.put(p, 0);			// Add the products in the arraylist product to the stats map and initalize each product to a order count of 0
		}
		return products;
	}

	private String generateOrderNumber()
	{
		return "" + orderNumber++;
	}

	private String generateCustomerId()
	{
		return "" + customerId++;
	}

	private String generateProductId()
	{
		return "" + productId++;
	}

	public String getErrorMessage()
	{
		return errMsg;
	}

	public void printAllProducts()
	{
		for (Map.Entry<String,Product> entry : products.entrySet())
		{
			entry.getValue().print();
	   	}
	}

	public void printAllBooks()
	{
		for (Map.Entry<String,Product> entry : products.entrySet())
		{
			if (entry.getValue().getCategory() == Product.Category.BOOKS)
			{
				entry.getValue().print();
			}
	   	}
	
	}

	public ArrayList<Book> booksByAuthor(String author)
	{
		ArrayList<Book> books = new ArrayList<Book>();
		for (Map.Entry<String,Product> entry : products.entrySet())
		{
			if (entry.getValue().getCategory() == Product.Category.BOOKS)
			{
				Book book = (Book) entry.getValue();
				if (book.getAuthor().equals(author))
				{
					books.add(book);
				}
			}
		}
		return books;
	}

	public void printAllOrders()
	{
		for (ProductOrder o : orders)
			o.print();
	}

	public void printAllShippedOrders()
	{
		for (ProductOrder o : shippedOrders)
			o.print();
	}

	public void printCustomers()
	{
		for (Customer c : customers)
			c.print();
	}
	/*
	 * Given a customer id, print all the current orders and shipped orders for them (if any)
	 */
	public boolean printOrderHistory(String customerId) throws UnknowncustomerException
	{
		// Make sure customer exists
		int index = customers.indexOf(new Customer(customerId));
		if (index == -1)
		{
			errMsg = "Customer " + customerId + " Not Found";
			throw new UnknowncustomerException(errMsg);
		}	
		System.out.println("Current Orders of Customer " + customerId);
		for (ProductOrder order: orders)
		{
			if (order.getCustomer().getId().equals(customerId))
				order.print();
		}
		System.out.println("\nShipped Orders of Customer " + customerId);
		for (ProductOrder order: shippedOrders)
		{
			if (order.getCustomer().getId().equals(customerId))
				order.print();
		}
		return true;
	}

	public String orderProduct(String productId, String customerId, String productOptions) throws UnknowncustomerException, UnknownproductException, InvalidproductoptionsException, ProductoutofstockException
	{
		// Get customer
		int index = customers.indexOf(new Customer(customerId));
		if (index == -1)
		{
			errMsg = "Customer " + customerId + " Not Found";
			throw new UnknowncustomerException(errMsg);
		}
		Customer customer = customers.get(index);

		// Get product 
		Product product = null;
		for (Map.Entry<String,Product> entry : products.entrySet())
		{
			if (entry.getKey().equals(productId))
			{
				product = entry.getValue();
			}
		}

		if (product == null)
		{
			errMsg = "Product " + productId + " Not Found";
			throw new UnknownproductException(errMsg);
		}

		// Check if the options are valid for this product (e.g. Paperback or Hardcover or EBook for Book product)
		if (!product.validOptions(productOptions))
		{
			errMsg = "Product " + product.getName() + " ProductId " + productId + " Invalid Options: " + productOptions;
			throw new InvalidproductoptionsException(errMsg);
		}
		// Is it in stock?
		if (product.getStockCount(productOptions) == 0)
		{
			errMsg = "Product " + product.getName() + " ProductId " + productId + " Out of Stock";
			throw new ProductoutofstockException(errMsg);
		}
		// Create a ProductOrder
		ProductOrder order = new ProductOrder(generateOrderNumber(), product, customer, productOptions);
		product.reduceStockCount(productOptions);
		addcount(product);

		// Add to orders and return
		orders.add(order);

		return order.getOrderNumber();
	}

	/*
	 * Create a new Customer object and add it to the list of customers
	 */

	public boolean createCustomer(String name, String address) throws InvalidcustomernameException, InvalidcustomeraddressException
	{
		// Check to ensure name is valid
		if (name == null || name.equals(""))
		{
			errMsg = "Invalid Customer Name " + name;
			throw new InvalidcustomernameException(errMsg);
		}
		// Check to ensure address is valid
		if (address == null || address.equals(""))
		{
			errMsg = "Invalid Customer Address " + address;
			throw new InvalidcustomeraddressException(errMsg);
		}
		Customer customer = new Customer(generateCustomerId(), name, address);
		customers.add(customer);
		return true;
		
	}

	public ProductOrder shipOrder(String orderNumber) throws InvalidordernumberException
	{
		// Check if order number exists
		int index = orders.indexOf(new ProductOrder(orderNumber,null,null,""));
		if (index == -1)
		{
			errMsg = "Order " + orderNumber + " Not Found";
			throw new InvalidordernumberException(errMsg);
		}
		ProductOrder order = orders.get(index);
		orders.remove(index);
		shippedOrders.add(order);
		return order;
	}

	/*
	 * Cancel a specific order based on order number
	 */
	public boolean cancelOrder(String orderNumber) throws InvalidordernumberException
	{
		// Check if order number exists
		int index = orders.indexOf(new ProductOrder(orderNumber,null,null,""));
		if (index == -1)
		{
			errMsg = "Order " + orderNumber + " Not Found";
			throw new InvalidordernumberException(errMsg);
		}
		ProductOrder order = orders.get(index);
		orders.remove(index);
		subcount(order.getProduct()); //decrement # of times ordered by 1
		return true;
	}

	public String addtoCart(String productId, String customerId, String productOptions) throws UnknowncustomerException, UnknownproductException, InvalidproductoptionsException, ProductoutofstockException
	{	// add a product to the customers cart
		// Get customer
		int index = customers.indexOf(new Customer(customerId));
		if (index == -1)
		{
			errMsg = "Customer " + customerId + " Not Found";
			throw new UnknowncustomerException(errMsg);
		}
		Customer customer = customers.get(index);
		
		// Get product 
		Product product = null;
		for (Map.Entry<String,Product> entry : products.entrySet())
		{
			if (entry.getKey().equals(productId))
			{
				product = entry.getValue();
			}
		}

		if (product == null)
		{
			errMsg = "Product " + productId + " Not Found";
			throw new UnknownproductException(errMsg);
		}
		
		// Check if the options are valid for this product (e.g. Paperback or Hardcover or EBook for Book product)
		if (!product.validOptions(productOptions))
		{
			errMsg = "Product " + product.getName() + " ProductId " + productId + " Invalid Options: " + productOptions;
			throw new InvalidproductoptionsException(errMsg);
		}
		// Is it in stock?
		if (product.getStockCount(productOptions) == 0)
		{
			errMsg = "Product " + product.getName() + " ProductId " + productId + " Out of Stock";
			throw new ProductoutofstockException(errMsg);
		}
		// Create a ProductOrder
		CartItem cartorder = new CartItem(product, productOptions);
		// Add to cartitmes
		cartitems.add(cartorder);
		
		return "Product has been added to cart";
		
	}
	
	//Remove a specific item from cart
	
   public String remCartItem(String customerId, String productId) throws UnknowncustomerException
   {	// Remove an item in a customers cart based on the product id
	   // Check if customer exists exists
	   int index = customers.indexOf(new Customer(customerId));
		if (index == -1)
		{
			errMsg = "Customer " + customerId + " Not Found";
			throw new UnknowncustomerException(errMsg);
		}
		Customer customer = customers.get(index);
		for (CartItem citem : cartitems)
			if (citem.getProduct().getId().equals(productId))
			{
				cartitems.remove(citem);
				return "Item has been removed from cart";
			}
		
		return "Could not find Product id: " + productId;
   }


	public void printCart()
	{
		for (CartItem citem : cartitems) // iterate through cartitems and print each
			citem.print();
	}

	public String orderItems(String customerId) throws UnknowncustomerException
	{	// Get customer
		int index = customers.indexOf(new Customer(customerId));
		if (index == -1)
		{
			errMsg = "Customer " + customerId + " Not Found";
			throw new UnknowncustomerException(errMsg);
		}
		Customer customer = customers.get(index);
		while (cartitems.size() !=0)    		// If cart is not empty order all items in cart
		{
			ProductOrder order = new ProductOrder(generateOrderNumber(), cartitems.get(0).getProduct(), customer, cartitems.get(0).getproductOptions());
			cartitems.get(0).getProduct().reduceStockCount(cartitems.get(0).getproductOptions());
			cartitems.remove(cartitems.get(0));
			orders.add(order);
			addcount(order.getProduct());				// add count to the number of times product has been ordered for stats
		}
		return "All items from cart have been ordered";
	}

	// Function used to increment number of times a product has beeen ordered
	public void addcount(Product product)
	{
		for (Map.Entry<Product,Integer> entry : statistacs.entrySet())
		{
			if (entry.getKey().equals(product))
			{
				statistacs.put(product, entry.getValue()+1);
			}
		}
	}
	// function to decrement the number of times a product has been ordered
	public void subcount(Product product)
	{
		for (Map.Entry<Product,Integer> entry : statistacs.entrySet())
		{
			if (entry.getKey().equals(product))
			{
				statistacs.put(product, entry.getValue()-1);
			}
		}
	}

	public void printstats()
	{
		// Sort the statistacs map in order from most to least
		ArrayList<Integer> tempvalues = new ArrayList<>();
		List<Map.Entry<Product, Integer> > list = new LinkedList<Map.Entry<Product, Integer> >(statistacs.entrySet());
 

        Collections.sort(list, new Comparator<Map.Entry<Product, Integer> >() 
		{
            public int compare(Map.Entry<Product, Integer> p1, Map.Entry<Product, Integer> p2)
            {
                return (p2.getValue()).compareTo(p1.getValue());
            }
		
		});

		HashMap<Product, Integer> temp = new LinkedHashMap<>();
        for (Map.Entry<Product, Integer> entry : list)
		{
            temp.put(entry.getKey(), entry.getValue());
        }

		// print the sorted list
		for (Map.Entry<Product, Integer> entry : temp.entrySet())
		{
			System.out.printf("\nId: %-5s Category: %-9s Name: %-20s Price: %7.1f Times Ordered: %-20s", entry.getKey().getId(), entry.getKey().getCategory(), entry.getKey().getName(), entry.getKey().getPrice(), entry.getValue());
		}
	
	}

	// Sort products by increasing price
	public void PrintByPrice()
	{
		ArrayList<Product> tempprods = new ArrayList<>();
		for (Map.Entry<String,Product> entry : products.entrySet())
		{
			tempprods.add(entry.getValue());
		}
		Collections.sort(tempprods, new PriceComparator());
		for (Product p : tempprods)
		{
			p.print();
		}
	}

	private class PriceComparator implements Comparator<Product>
	{
		public int compare(Product a, Product b)
		{
			if (a.getPrice() > b.getPrice()) return 1;
			if (a.getPrice() < b.getPrice()) return -1;	
			return 0;
		}
	}

	// Sort products alphabetically by product name
	public void PrintByName()
	{
		ArrayList<Product> tempproducts = new ArrayList<>();
		for (Map.Entry<String,Product> entry : products.entrySet())
		{
			tempproducts.add(entry.getValue());
		}
		Collections.sort(tempproducts, new NameComparator());
		for (Product p : tempproducts)
		{
			p.print();
		}
	}

	private class NameComparator implements Comparator<Product>
	{
		public int compare(Product a, Product b)
		{
			return a.getName().compareTo(b.getName());
		}
	}

	// Sort products alphabetically by product name
	public void sortCustomersByName()
	{
		Collections.sort(customers);
	}
}

class UnknowncustomerException extends Exception
{ 
    public UnknowncustomerException(String errorMessage) {
        super(errorMessage);
	}
}

class UnknownproductException extends Exception 
{ 
    public UnknownproductException(String errorMessage) {
        super(errorMessage);
	}
}

class InvalidproductoptionsException extends Exception 
{ 
    public InvalidproductoptionsException(String errorMessage) {
        super(errorMessage);
	}
}

class ProductoutofstockException extends Exception 
{ 
    public ProductoutofstockException(String errorMessage) {
        super(errorMessage);
	}
}

class InvalidcustomernameException extends Exception 
{ 
    public InvalidcustomernameException(String errorMessage) {
        super(errorMessage);
	}
}

class InvalidcustomeraddressException extends Exception 
{ 
    public InvalidcustomeraddressException(String errorMessage) {
        super(errorMessage);
	}
}

class InvalidordernumberException extends Exception 
{ 
    public InvalidordernumberException(String errorMessage) {
        super(errorMessage);
	}
}