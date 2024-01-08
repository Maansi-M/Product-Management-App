package DTO;

public class Product {
    private int productId ;
    private String productName ;
    private double productPrice ;
    private int productQty ;

    public Product() {
    }

    public Product(int productId, String productName) {
        this.productId = productId;
        this.productName = productName;
    }

    public Product(String productName, double productPrice) {
        this.productName = productName;
        this.productPrice = productPrice;
    }

    public Product(String productName, double productPrice, int productQty) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productQty = productQty;
    }

    public Product(int productId, String productName, double productPrice, int productQty) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productQty = productQty;
    }

    public Product(String productName, int productQty) {
        this.productName = productName;
        this.productQty = productQty;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public int getProductQty() {
        return productQty;
    }
}