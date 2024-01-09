package bakeryApplication;

import DAO.AdminService;
import DAO.ServiceImpl;
import DTO.Product;
import DTO.User;
import java.util.List;
import java.util.Scanner;

public class AdminMainApp {
    static Scanner sc = new Scanner(System.in);
    static AdminService service = new AdminService();

    public static void main(User user) {
        System.out.println("Welcome to Admin Page, " + user.getUserName());

        System.out.println("\nSELECT OPERATION");
        System.out.println("1. DISPLAY ALL PRODUCTS");
        System.out.println("2. Display Product By Name");
        System.out.println("3. DELETE PRODUCT");
        System.out.println("4. ADD PRODUCT");
        System.out.println("5. SIGN OUT");
        System.out.println();
        int ch = sc.nextInt();

        switch (ch) {
            case 1:
                displayAllProducts();
                break;
            case 2:
                displayProductByName();
                break;
            case 3:
                deleteProduct();
                break;
            case 5:
                user = null;
                return;
            case 4:
                addProduct();
                break;
            default:
                System.out.println("INVALID INPUT");
        }

        main(user);
    }

    private static void displayAllProducts() {
        List<Product> productList = service.displayAllProduct();

        System.out.println("NAME  \t\t\t\t  PRICE \t\t\t\t Quantity");
        System.out.println("***********************************");
        for (Product p : productList) {
            System.out.println(p.getProductName() + "\t\t\t\t\t " + p.getProductPrice()+ "\t\t\t\t\t " + p.getProductQty());

        }
        System.out.println("_______________________________");
    }

    private static void displayProductByName() {
        System.out.println("ENTER PRODUCT NAME ");
        String productName = sc.next();
        List<Product> productList = service.displayProductByName(productName);

        System.out.println("***********************************");
        System.out.println("NAME  \t\t\t\t PRICE ");
        System.out.println("***********************************");
        for (Product p : productList) {
            System.out.println(p.getProductName() + "\t\t\t\t\t  " + p.getProductPrice());
        }
        System.out.println("_______________________________");
    }

    private static void deleteProduct() {
        System.out.println("ENTER PRODUCT NAME TO DELETE ");
        String productName = sc.next();
        boolean status = service.deleteProduct(productName);

        if (status)
            System.out.println("PRODUCT DELETED!!");
        else
            System.out.println("PRODUCT NOT FOUND!!");
    }



    private static void addProduct() {
        System.out.println("ENTER PRODUCT NAME: ");
        String productName = sc.next();

        System.out.println("ENTER PRODUCT PRICE: ");
        double productPrice = sc.nextDouble();

        System.out.println("ENTER PRODUCT QUANTITY: ");
        int productQuantity = sc.nextInt();
        System.out.println("_______________________________");

        Product newProduct = new Product(productName, productPrice, productQuantity);

        boolean status = service.addProduct(newProduct);

        if (status) {
            System.out.println("PRODUCT ADDED!!");
        } else {
            System.out.println("FAILED TO ADD PRODUCT!!");
        }
    }
}
