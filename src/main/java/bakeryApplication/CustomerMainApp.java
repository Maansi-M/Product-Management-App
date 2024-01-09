package bakeryApplication;
import DAO.CustomerService;
import DTO.Order;
import DTO.Product;
import DTO.User;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class CustomerMainApp {
    static Scanner sc = new Scanner(System.in);
    static CustomerService service = new CustomerService();
    public static void main(User user){

        System.out.println("WELCOME, "+user.getUserName());

        System.out.println("\nSELECT OPERATION");
        System.out.println("1. DISPLAY ALL PRODUCTS");
        System.out.println("2. PLACE ORDER ");
        System.out.println("3. CANCEL ORDER ");
        System.out.println("4. DISPLAY ALL ORDERS ");
        System.out.println("5. SIGN OUT ");
        System.out.println();
        int ch = sc.nextInt() ;

        switch (ch){
            case 1:
                displayAllProducts();
                break;
            case 2:
                placeOrder(user);
                break;
            case 3 :
                cancelOrder(user) ;
                break;
            case 4:
                displayAllOrders(user);
                break;
            case 5 :
                user = null ;
                return;
            default:
                System.out.println("INVALID INPUT ");
        }

        main(user);
    }
    private static void displayAllProducts() {
        List<Product> productList = service.displayAllProduct() ;

        System.out.println("NAME  \t\t\t\t  PRICE ");
        System.out.println("***********************************");
        for (Product p : productList)
        {
            System.out.println(p.getProductName() +"\t\t\t "+p.getProductPrice());
        }
        System.out.println("__________________________________________");
    }

    private static void placeOrder(User user) {

        Order ord = new Order(user);

        do {
            System.out.println("ENTER PRODUCT NAME ");
            String productName = sc.next();
            System.out.println("ENTER ORDER QTY ");
            int orderQty = sc.nextInt();
            Product product = new Product(productName, orderQty);
            ord.addProduct(product);

            System.out.println("ADD MORE PRODUCT (Y/N)");
            char ch = sc.next().charAt(0);
            if (ch == 'n' || ch == 'N')
                break;

        }while (true);

        boolean status = false;
        try {
            status = service.placeOrder(ord);
        } catch (Exception e) {
            System.out.println(e);
        }
        if (status)
            System.out.println("ORDER PLACED !!");
        else
            System.out.println("ORDER NOT PLACED !!");

    }
    private static void cancelOrder(User user) {
        displayAllOrders(user);
        System.out.println("SELECT ORDER ");
        int ordId = sc.nextInt() ;
        for (Product p :service.displayAllProduct(ordId))
            System.out.println(p.getProductId() +"     "+ p.getProductName());
    }

    private static void displayAllOrders(User user) {
        for (Order o :service.displayAllOrders(user))
            System.out.println(o.getOrderId());
    }
}