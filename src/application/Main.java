package application;

import model.entities.Department;
import model.entities.Seller;

import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);

        Department department = new Department("Electronics", 1 );
        Seller seller = new Seller(1, "John", "john.1406@gmail.com", "1990-01-31", 2000.00, department);

        System.out.println(department);
        System.out.println(seller);

    }
}