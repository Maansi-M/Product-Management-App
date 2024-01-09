package DAO;
import DTO.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AdminService extends ServiceImpl
{
    @Override
    public List<Product> displayAllProduct() {
        String selectQuery = "SELECT product_name, product_price, product_qty FROM product_info";
        List<Product> productList = new ArrayList<>();
        Statement stmt = null;
        try{
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(selectQuery);
            while (rs.next()) {
                String name = rs.getString("product_name");
                double price = rs.getDouble("product_price");
                int quantity = rs.getInt("product_qty");
                Product pro = new Product(name, price, quantity);
                productList.add(pro);
            }

        } catch (SQLException e) {
            System.out.println(e);
        }

        return productList;
    }


    @Override
    public List<Product> displayProductByName(String productName) {
        String selectProductByNameQuery = "SELECT product_name, product_price, product_qty " +
                "FROM product_info " +
                "WHERE product_name LIKE ?";
        List<Product> productList = new ArrayList<>();

        try {
            PreparedStatement pstmt = conn.prepareStatement(selectProductByNameQuery);
            pstmt.setString(1, "%" + productName + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String name = rs.getString("product_name");
                double price = rs.getDouble("product_price");
                int quantity = rs.getInt("product_qty");
                Product product = new Product(name, price, quantity);
                productList.add(product);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return productList;
    }

    public boolean deleteProduct(String productName) {
        String deleteProductQuery = "DELETE FROM product_info WHERE product_name = ?";
        try  {
            PreparedStatement pstmt = conn.prepareStatement(deleteProductQuery);
            pstmt.setString(1, productName);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }


    public boolean addProduct(Product product) {
        String addProductQuery = "INSERT INTO product_info (product_name, product_price, product_qty) VALUES (?, ?, ?)";
        try{
            PreparedStatement pstmt = conn.prepareStatement(addProductQuery);
            pstmt.setString(1, product.getProductName());
            pstmt.setDouble(2, product.getProductPrice());
            pstmt.setInt(3, product.getProductQty());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }
}



