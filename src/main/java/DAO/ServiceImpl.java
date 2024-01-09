package DAO;

import DTO.User;

import java.sql.*;

public abstract class ServiceImpl implements Service{

    static Connection conn = null ;

    static {
        String url = "jdbc:mysql://localhost:3306/bakerydb";
        String user = "root" ;
        String password = "sql123";

        try {
            conn = DriverManager.getConnection(url , user , password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static User getUser(User user)
    {
        User newUser = null ;
        String getUserQuery = "select * from user_info where user_name = ? and user_pass = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(getUserQuery);
            pstmt.setString(1 , user.getUserName());
            pstmt.setString(2 , user.getUserPassword());
            ResultSet rs = pstmt.executeQuery() ;
            while (rs.next())
            {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String role = rs.getString(4 );
                newUser = new  User(id , name , "*********" , role) ;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return newUser ;
    }

}