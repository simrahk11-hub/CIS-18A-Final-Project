// Simrah Kashif - CIS18A Final
// MyShoeStore - Business Program

/* This requires any class that uses it to have a method that saves
    data to a file, which is used to store the order summary */
interface Savable 
{
    void saveToFile();
}

// This class is just a product sold by this catalog and stored the info for it
class Product 
{
    protected String name;
    protected int price;

    // Constructor
    Product(String n, int p) 
    {
        name = n;
        price = p;
    }
}

/* This class is a shoe product and adds specific info for the shoe 
- like sizes, etc */
class Shoe extends Product 
{
    int[] sizes;

    // Constructor
    Shoe(String n, int p, int[] s) 
    {
        super(n, p);
        sizes = s;
    }

    // It displays the shoe name, price, and available sizes to the user
    void display() 
    {
        System.out.print(name + " - $ " + price + " | Sizes: ");
        for (int i = 0; i < sizes.length; i++) 
        {
            System.out.print(sizes[i] + " ");
        }
        System.out.println();
    }
}

/* This class is a customer order and saves and stores the shoe, shoe size, 
    quantity, and delivery details */
class Order implements Savable 
{
    Shoe shoe;
    int size;
    int quantity;
    String date;
    String time;

    Order(Shoe s, int si, int q) 
    {
        shoe = s;
        size = si;
        quantity = q;
        date = "Not scheduled.";
        time = "Not scheduled.";
    }

    // Calculates and returns the total for the order
    int getTotal() 
    {
        return shoe.price * quantity;
    }

    // Saves the order summary and delivery info - option 5
    public void saveToFile() 
    {
        try 
        {
            java.io.FileWriter writer = new java.io.FileWriter("order.txt");
            
            writer.write("\nShoe: " + shoe.name + "\n");
            writer.write("Size: " + size + "\n");
            writer.write("Quantity: " + quantity + "\n");
            writer.write("Delivery Date: " + date + "\n");
            writer.write("Delivery Time: " + time + "\n");
            writer.write("Total: $" + getTotal() + "\n");
            writer.close();
            
            System.out.println("Order was saved to file.");
        }
        catch (Exception e) 
        {
            // Error message
            System.out.println("File error!!");
        }
    }
}

// This class is the shoe store program and handles all user interaction
class ShoeStore 
{
    // to read the input from keyboard
    java.util.Scanner input;

    // The shoe catalog
    Shoe[] catalog;

    // Stores the order placed by user
    Order userOrder;

    ShoeStore()
    {
        input = new java.util.Scanner(System.in);

        catalog = new Shoe[] 
        {
            new Shoe("Sneakers", 120, new int[]{7,8,9,10}),
            new Shoe("Heels", 85, new int[]{6,7,8}),
            new Shoe("Boots", 100, new int[]{7,8,9})
        };

        userOrder = null;
    }

    // the main menu for the user - intro
    void showMenu() 
    {
        System.out.println("\n---MyShoeStore - By Simrah Kashif---\n");
        
        System.out.println("1 - View Shoe Catalog");
        System.out.println("2 - Place Order");
        System.out.println("3 - Schedule Delivery");
        System.out.println("4 - View Order Receipt");
        System.out.println("5 - Save Order");
        System.out.println("6 - Exit");
    }

    void displayCatalog()       // option 1
    {
        for (int i = 0; i < catalog.length; i++) 
        {
            System.out.print((i + 1) + " ");
            catalog[i].display();
        }
    }

    /*  The user can place an order - to select the shoe, size, and number of 
    items, and can create a new order - option 2 */
    void placeOrder() 
    {
        displayCatalog();
        System.out.print("\nSelect the shoe you want: ");
        int choice = input.nextInt();

        if (choice < 1 || choice > catalog.length)
        {
            System.out.println("Invalid shoe choice.");
            return;
        }

        System.out.print("Enter size: ");
        int size = input.nextInt();

        System.out.print("Enter quantity: ");
        int qty = input.nextInt();

        userOrder = new Order(catalog[choice - 1], size, qty);
        System.out.println("Order placed!");
    }

    // The user enters the delivery time, date, and an error message - option 3
    void scheduleDelivery() 
    {
        if (userOrder == null) 
        {
            System.out.println("No Order Placed.");
            return;
        }

        System.out.print("\nEnter delivery date: ");
        userOrder.date = input.next();

        System.out.print("Enter delivery time: ");
        userOrder.time = input.next();

        System.out.println("Delivery scheduled!\n");
    }

    /* Shows the order summary for the current order to the user, 
    and has an error message just incase - option 4*/
    void showSummary() 
    {
        if (userOrder == null) 
        {
            System.out.println("No Order Placed.");
            return;
        }

        System.out.println("\n Order Summary \n");
        System.out.println("Shoe: " + userOrder.shoe.name);
        System.out.println("Size: " + userOrder.size);
        System.out.println("Quantity: " + userOrder.quantity);
        System.out.println("Total: $" + userOrder.getTotal() + "\n");
    }

    void run()
    {
        int choice;

        do 
        {
            showMenu();
            choice = input.nextInt();
            input.nextLine();

            switch (choice) 
            {
                case 1:
                    displayCatalog();
                    break;
                case 2:
                    placeOrder();
                    break;
                case 3:
                    scheduleDelivery();
                    break;
                case 4:
                    showSummary();
                    break;
                case 5:
                    // Checks if an order exists before saving
                    if (userOrder != null)
                        userOrder.saveToFile();
                    else
                        System.out.println("No Order Placed.");
                    break;
                case 6:
                    System.out.println("Thank you so much, Bye!");
                    break;
                default:
                    // Displays message for invalid menu selections
                    System.out.println("Invalid.");
            }
        } 
        while (choice != 6);
    }

    public static void main(String[] args) 
    {
        ShoeStore store = new ShoeStore();
        store.run();
    }
}
