import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;

// Simulation of a Simple E-Commerce System (like Amazon)

public class ECommerceUserInterface
{
	public static void main(String[] args) 
	{	
		while (true)
		{

			try 
			{
				
				// Create the system
				ECommerceSystem amazon = new ECommerceSystem();
		
				Scanner scanner = new Scanner(System.in);
				System.out.print(">");
		
				// Process keyboard actions
				while (scanner.hasNextLine())
				{
					String action = scanner.nextLine();
		
					if (action == null || action.equals("")) 
					{
						System.out.print("\n>");
						continue;
					}
					else if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT"))
						return;
		
					else if (action.equalsIgnoreCase("PRODS"))	// List all products for sale
					{
						amazon.printAllProducts(); 
					}
					else if (action.equalsIgnoreCase("BOOKS"))	// List all books for sale
					{
						amazon.printAllBooks(); 
					}
					else if (action.equalsIgnoreCase("BOOKSBYAUTHOR"))	// ship an order to a customer
					{
						String author = "";
		
						System.out.print("Author: ");
						if (scanner.hasNextLine())
							author = scanner.nextLine();
		
						ArrayList<Book> books = amazon.booksByAuthor(author);
						Collections.sort(books);
						for (Book book: books)
							book.print();
					}
					else if (action.equalsIgnoreCase("CUSTS")) 	// List all registered customers
					{
						amazon.printCustomers();	
					}
					else if (action.equalsIgnoreCase("ORDERS")) // List all current product orders
					{
						amazon.printAllOrders();	
					}
					else if (action.equalsIgnoreCase("SHIPPED"))	// List all orders that have been shipped
					{
						amazon.printAllShippedOrders();	
					}
					else if (action.equalsIgnoreCase("NEWCUST"))	// Create a new registered customer
					{
						String name = "";
						String address = "";
		
						System.out.print("Name: ");
						if (scanner.hasNextLine())
							name = scanner.nextLine();
		
						System.out.print("\nAddress: ");
						if (scanner.hasNextLine())
							address = scanner.nextLine();
		
						boolean success = amazon.createCustomer(name, address);
						if (!success)
							System.out.println(amazon.getErrorMessage());
					}
					else if (action.equalsIgnoreCase("SHIP"))	// ship an order to a customer
					{
						String orderNumber = "";
		
						System.out.print("Order Number: ");
						if (scanner.hasNextLine())
							orderNumber = scanner.nextLine();
		
						ProductOrder order = amazon.shipOrder(orderNumber);
						if (order != null)
						{
							order.print();
						}
						else 
						{
							System.out.println(amazon.getErrorMessage());
						}
					}
					else if (action.equalsIgnoreCase("CUSTORDERS")) // List all the current orders and shipped orders for this customer
					{
						String customerId = "";
		
						System.out.print("Customer Id: ");
						if (scanner.hasNextLine())
							customerId = scanner.nextLine();
		
						// Prints all current orders and all shipped orders for this customer
						boolean validCustomer = amazon.printOrderHistory(customerId);
						if (!validCustomer)
						{
							System.out.println(amazon.getErrorMessage());
						}
					}
					else if (action.equalsIgnoreCase("ORDER")) // order a product for a certain customer
					{
						String productId = "";
						String customerId = "";
		
						System.out.print("Product Id: ");
						if (scanner.hasNextLine())
							productId = scanner.nextLine();
		
						System.out.print("\nCustomer Id: ");
						if (scanner.hasNextLine())
							customerId = scanner.nextLine();
		
						String orderNumber = amazon.orderProduct(productId, customerId, "");
						if (orderNumber != null)
						{
							System.out.println("Order #" + orderNumber);
						}
						else
						{
							System.out.println(amazon.getErrorMessage());
						}
					}
					else if (action.equalsIgnoreCase("ORDERBOOK")) // order a book for a customer, provide a format (Paperback, Hardcover or EBook)
					{
						String productId = "";
						String customerId = "";
						String format = "";
		
						System.out.print("Product Id: ");
						if (scanner.hasNextLine())
							productId = scanner.nextLine();
		
						System.out.print("\nCustomer Id: ");
						if (scanner.hasNextLine())
							customerId = scanner.nextLine();
		
						System.out.print("\nFormat [Paperback Hardcover EBook]: ");
						if (scanner.hasNextLine())
							format = scanner.nextLine();
		
						String orderNumber = amazon.orderProduct(productId, customerId, format);
						if (orderNumber != null)
						{
							System.out.println("Order #" + orderNumber);
						}
						else
						{
							System.out.println(amazon.getErrorMessage());
						}
					}
					else if (action.equalsIgnoreCase("ORDERSHOES")) // order a book for a customer, provide a format (Paperback, Hardcover or EBook)
					{
						String productId = "";
						String customerId = "";
						String sizeColor = "";
		
						System.out.print("Product Id: ");
						if (scanner.hasNextLine())
							productId = scanner.nextLine();
		
						System.out.print("\nCustomer Id: ");
						if (scanner.hasNextLine())
							customerId = scanner.nextLine();
		
						System.out.print("\nSize (6, 7, 8, 9, 10) and Color (Black or Brown): ");
						if (scanner.hasNextLine())
							sizeColor = scanner.nextLine();
		
						String orderNumber = amazon.orderProduct(productId, customerId, sizeColor);
						if (orderNumber != null)
						{
							System.out.println("Order #" + orderNumber);
						}
						else
						{
							System.out.println(amazon.getErrorMessage());
						}
					}
					else if (action.equalsIgnoreCase("CANCEL")) // Cancel an existing order
					{
						String orderNumber = "";
		
						System.out.print("Order Number: ");
						if (scanner.hasNextLine())
							orderNumber = scanner.nextLine();
		
						boolean success = amazon.cancelOrder(orderNumber);
						if (!success)
						{
							System.out.println(amazon.getErrorMessage());
						}
					}
					else if (action.equalsIgnoreCase("ADDTOCART")) // Adds an item to cart
					{
						String productid = "";
						String customerID = "";
						String productOptions = "";
		
						System.out.print("Product Id: ");
						// get product id
						if (scanner.hasNextLine())						//Checks if scanner has a next string value. if does then makes that productId.
							productid = scanner.nextLine();
						System.out.print("\nCustomer Id: ");
						// get customer id
						if (scanner.hasNextLine())						//Checks if scanner has a next string value. if does then makes that customerId.
							customerID = scanner.nextLine();
						System.out.print("\nFormat [Paperback Hardcover EBook]: ");
						// get book forma and store in options string
						if (scanner.hasNextLine())						//Checks if scanner has a next string value. if does then makes that options.
							productOptions = scanner.nextLine();
						
						// Adds Product to cart
							// CALL METHOD BELOW
						System.out.println(amazon.addtoCart(productid, customerID, productOptions)); 
					}
					else if (action.equalsIgnoreCase("REMCARTITEM")) // Removes an item from cart
					{
						String customerID = "";
						String productid = "";
						
						System.out.print("\nCustomer Id: ");
						// get customer id
						if (scanner.hasNextLine())						//Checks if scanner has a next string value. if does then makes that customerId.
							customerID = scanner.nextLine();
						System.out.print("Product Id: ");
						// get product id
						if (scanner.hasNextLine())						//Checks if scanner has a next string value. if does then makes that productId.
							productid = scanner.nextLine();
							// Removes product from cart
							// CALL METHOD BELOW
						System.out.println(amazon.remCartItem(customerID, productid));
					}
					else if (action.equalsIgnoreCase("PRINTCART"))
					{
						String customerId = "";
						System.out.print("\nCustomer Id: ");
						// get customer id
						if (scanner.hasNextLine())						//Checks if scanner has a next string value. if does then makes that customerId.
							customerId = scanner.nextLine();
						// Prints all products in cart
						// CALL METHOD BELOW
						amazon.printCart();
					}
					else if (action.equalsIgnoreCase("ORDERITEMS"))
					{
						String customerId = "";
						System.out.print("\nCustomer Id: ");
						// get customer id
						if (scanner.hasNextLine())						//Checks if scanner has a next string value. if does then makes that customerId.
							customerId = scanner.nextLine();
						// Creates product order for each product in cart
						// CALL METHOD BELOW
						System.out.println(amazon.orderItems(customerId));
					}
					else if (action.equalsIgnoreCase("PRINTBYPRICE")) // sorts and prints products by price
					{
						amazon.PrintByPrice();
					}
					else if (action.equalsIgnoreCase("PRINTBYNAME")) // sorts and prints products by name (alphabetic)
					{
						amazon.PrintByName();
					}
					else if (action.equalsIgnoreCase("SORTCUSTS")) // sort products by name (alphabetic)
					{
						amazon.sortCustomersByName();
					}
					else if (action.equalsIgnoreCase("STATS")) // sorts products by number of times ordered
					{
						amazon.printstats();
					}
					System.out.print("\n>");
				}
			} 
			catch (InvalidcustomeraddressException | InvalidcustomernameException |InvalidordernumberException | InvalidproductoptionsException | ProductoutofstockException | UnknowncustomerException | UnknownproductException e) 
			{
				System.out.println(e.getMessage());
				
			}
		}
	}
}
