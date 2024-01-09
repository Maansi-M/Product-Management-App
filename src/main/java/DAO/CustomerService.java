package DAO;

import DTO.Order;
import DTO.Product;
import DTO.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerService extends ServiceImpl{

    @Override
    public List<Product> displayAllProduct() {
        String selectQuery = "SELECT product_name , product_price FROM product_info ";
        List<Product> productList = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(selectQuery);
            while (rs.next()) {
                String name = rs.getString(1);
                double price = rs.getDouble(2);
                Product pro = new Product(name , price);
                productList.add(pro);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return productList;
    }

    @Override
    public List<Product> displayProductByName(String productName) {
        String selectProductByNameQuery = "SELECT product_id, product_name, product_price " +
                "FROM product_info " +
                "WHERE product_name LIKE ?";
        List<Product> productList = new ArrayList<>();

        try {
            PreparedStatement pstmt = conn.prepareStatement(selectProductByNameQuery);
            pstmt.setString(1, "%" + productName + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int productId = rs.getInt("product_id");
                String name = rs.getString("product_name");
                double price = rs.getDouble("product_price");
                Product product = new Product(name, price, productId);
                productList.add(product);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return productList;
    }


    public List<Product> displayAllProduct(int ordId) {
        List<Product> productList = new ArrayList<>();

        String selectProductQuery = "select p.product_id , p.product_name \n" +
                "from order_product op inner join product_info p\n" +
                "on op.product_id = p.product_id \n" +
                "where order_id = ? ;";

        try {
            PreparedStatement pstmt = conn.prepareStatement(selectProductQuery);
            pstmt.setInt(1 , ordId);
            ResultSet rs = pstmt.executeQuery() ;

            while (rs.next())
            {
                int pId = rs.getInt(1);
                String pname = rs.getString(2);
                Product p = new Product(pId , pname);
                productList.add(p);
            }

        } catch (SQLException e) {
            System.out.println(e);
        }

        return productList;
    }

    public  List<Order> displayAllOrders(User user)
    {
        List<Order> orderList = new ArrayList<>();
        String selectQuery = "SELECT order_id from order_info where user_id = "+user.getUserId() ;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(selectQuery);
            while (rs.next())
            {
                int ordId = rs.getInt(1);
                Order o1 = new Order();
                o1.setOrderId(ordId);
                orderList.add(o1);
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
        return orderList;
    }

    public boolean placeOrder(Order ord) {
        String insertUserProcedure = "{call insertUser(?)}";
        String placeOrderProcedure = "{call placeOrder(? , ? , ?)}";
        CallableStatement cstmt = null;
        try {
            cstmt = conn.prepareCall(insertUserProcedure);
            cstmt.setInt(1, ord.getUser().getUserId());
            cstmt.execute();
            ResultSet rs = cstmt.getResultSet();

            int ordId = 0;
            while (rs.next())
                ordId = rs.getInt(1);

            cstmt = conn.prepareCall(placeOrderProcedure);

            for (Product p : ord.getProductList()) {
                cstmt.setInt(1, ordId);
                cstmt.setString(2, p.getProductName());
                cstmt.setInt(3, p.getProductQty());
                cstmt.execute();
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
        return true;
    }
    public void cancelOrder(Order ord) {
        String cancelOrderQuery = "DELETE FROM order_info WHERE order_id = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(cancelOrderQuery);
            pstmt.setInt(1, ord.getOrderId());
            pstmt.executeUpdate();
        }

        catch (SQLException e) {
            System.out.println(e);
        }
    }
}