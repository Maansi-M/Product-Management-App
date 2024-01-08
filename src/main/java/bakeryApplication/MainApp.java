package bakeryApplication;

import DAO.ServiceImpl;
import DTO.User;

import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("ENTER USERNAME ");
        String username = sc.next() ;
        System.out.println("ENTER PASSWORD ");
        String password = sc.next() ;
        User user = new User(username , password);
        user =  ServiceImpl.getUser(user);

        if (user != null) {
            if (user.getUserRole().equalsIgnoreCase("admin"))
                AdminMainApp.main(user);
            else if (user.getUserRole().equalsIgnoreCase("customer"))
                CustomerMainApp.main(user);
        }else {
            System.out.println("invalid password ");
        }


        main(args);


    }
}