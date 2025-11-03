package view;

import db.DbException;
import db.DbIntegrityException;
import model.DAO.abstracted.DepartmentDAO;
import model.DAO.abstracted.SellerDAO;
import model.DAO.implemented.DAOFactory;
import model.entities.Department;
import model.entities.Seller;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class View {

    Scanner sc = null;
    Connection conn = null;
    SellerDAO sellerDAO = null;
    DepartmentDAO departmentDAO = null;

    public View( Scanner sc, Connection conn ) {
        this.sc = sc;
        this.conn = conn;
        sellerDAO = DAOFactory.constructSeller();
        departmentDAO = DAOFactory.constructDepartment();
    }

    public void menu() {

        int choice;

        while (true) {

            System.out.print("""
                   Which table do you want to use?""
                    1 - Seller
                    2 - Department
                    0 - End Program\s
                   R: \s""");

            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Try again.");
                clearScreen();
                continue;
            }

            if (choice >= 0 && choice <= 2) {break;}
            else {
                System.out.println("Invalid choice. Try again.");
                clearScreen();
            }

        }

        switch(choice) {

            case 1:
                sellerMenu();
            break;

            case 2:
                departmentMenu();
            break;

            case 0:
            break;
        }

    }

    public void sellerMenu() {

        int choice;

        while (true) {
            clearScreen();

            System.out.print("""
                    What operation would you like to do in Seller Table?""
                     1 - Insert
                     2 - Update
                     3 - Delete by id
                     4 - View by id
                     5 - View all
                     0 - Back to first menu
                    R: \s""");

            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Try again.");
                continue;
            }

            if (choice >= 0 && choice <= 5) {break;}
            else System.out.println("Invalid choice. Try again.");

        }

        switch(choice) {
            case 1 -> {

                clearScreen();

                System.out.print("Enter with seller name: ");

                String sellerName = sc.nextLine();

                System.out.print("Enter with seller email: ");
                String sellerEmail = sc.nextLine();

                System.out.print("Enter with seller birth date (yyyy-mm-dd): ");
                String sellerBirthDate = sc.nextLine();

                System.out.print("Enter with seller salary: ");
                Double sellerSalary = Double.parseDouble(sc.nextLine());

                System.out.print("Enter with seller department ID: ");
                Department dep = departmentDAO.findById(Integer.parseInt(sc.nextLine()));

                sellerDAO.insert(new Seller((sellerDAO.getLastAdded().getId() + 1), sellerName, sellerEmail, sellerBirthDate, sellerSalary, dep));

                sellerMenu();
            }

            case 2 -> {

                clearScreen();

                System.out.print("Enter with seller ID: ");
                Seller seller = null;

                try{
                    seller = sellerDAO.findById(Integer.parseInt(sc.nextLine()));
                } catch (DbException | DbIntegrityException e){
                    System.out.println(e.getMessage());
                    sellerMenu();
                }

                if (seller != null) {

                    System.out.print("Enter with new (or old) seller name: ");
                    seller.setName(sc.nextLine());

                    System.out.print("Enter with new (or old) seller email: ");
                    seller.setEmail(sc.nextLine());

                    System.out.print("Enter with new (or old) seller birth date (yyyy-mm-dd): ");
                    seller.setBirthDate(LocalDate.parse(sc.nextLine()));

                    System.out.print("Enter with new (or old) seller salary: ");
                    seller.setSalary(Double.parseDouble(sc.nextLine()));

                    System.out.print("Enter with new (or old) seller department ID: ");
                    seller.setDepartment(departmentDAO.findById(Integer.parseInt(sc.nextLine())));

                    sellerDAO.update(seller);
                }

                sellerMenu();
            }

            case 3 -> {

                clearScreen();

                System.out.print("Enter with seller ID: ");
                Seller seller = sellerDAO.findById(Integer.parseInt(sc.nextLine()));

                while (true) {

                    System.out.print("You really want to delete: " + seller + " ? (Y/N)\nR: ");
                    String option = sc.nextLine().toUpperCase();

                    if (option.charAt(0) == 'Y' || option.charAt(0) == 'N') {

                        if (option.charAt(0) == 'Y') {
                            sellerDAO.deleteById(seller.getId());
                            sellerMenu();
                            break;
                        }
                        else {
                            System.out.println("Going back to seller menu...");
                            sellerMenu();
                            break;
                        }

                    } else {
                        System.out.println("Invalid option. Please, write Y or N.\n");
                    }
                }

            }

            case 4 -> {

                clearScreen();

                while (true) {

                    System.out.println("Enter with seller ID: ");

                    try{
                        Seller seller = sellerDAO.findById(Integer.parseInt(sc.nextLine()));
                        System.out.println(seller);
                    } catch (DbException | DbIntegrityException e){
                        System.out.println(e.getMessage());
                    }

                    String option;

                    while (true) {
                        System.out.println("Want to view another seller? (Y/N)");
                        option = sc.nextLine().toUpperCase();

                        if (option.charAt(0) == 'Y' || option.charAt(0) == 'N') { break; }
                        else { System.out.println("Invalid option. Please, write Y or N.\n"); }
                    }

                    if (option.charAt(0) == 'N') {break;}

                }

                System.out.println("Going back to seller menu...");
                sellerMenu();
            }

            case 5 -> {

                clearScreen();

                System.out.println("Sellers in the Seller table: ");

                List<Seller> sellers = sellerDAO.findAll();
                sellers.forEach(System.out::println);

                sellerMenu();
            }

            case 0 -> {
                System.out.println("Going back to menu...");
                clearScreen();
                menu();
            }

        }

    }

    public void departmentMenu() {
        int choice;

        while (true) {
            clearScreen();

            System.out.print("""
                    What operation would you like to do in Seller Table?""
                     1 - Insert
                     2 - Update
                     3 - Delete by id
                     4 - View by id
                     5 - View all
                     0 - Back to first menu
                    R: \s""");

            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Try again.");
                continue;
            }

            if (choice >= 0 && choice <= 5) {
                break;
            } else System.out.println("Invalid choice. Try again.");

        }

        switch (choice) {
            case 1 -> {

                clearScreen();

                System.out.print("Enter with department name: ");
                String departmentName = sc.nextLine();

                departmentDAO.insert(new Department(departmentName, departmentDAO.getLastAdded().getId() + 1));

                departmentMenu();
            }

            case 2 -> {

                clearScreen();

                System.out.print("Enter with department ID: ");
                Department department = null;

                try{
                    department = departmentDAO.findById(Integer.parseInt(sc.nextLine()));
                } catch (DbException | DbIntegrityException e){
                    System.out.println(e.getMessage());
                    departmentMenu();
                }

                if (department != null) {

                    System.out.print("Enter with new (or old) seller name: ");
                    department.setName(sc.nextLine());

                    departmentDAO.update(department);
                }

                departmentMenu();
            }


        }

    }

    public void clearScreen() {
        System.out.println("\nPress ENTER to continue...");
        sc.nextLine();

        try {
            String os = System.getProperty("os.name");

            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }

        } catch (Exception e1) {
            // If fails, try to clean the screen with ANSI code | Se falhar, tenta limpar via códigos ANSI
            try {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            } catch (Exception e2) {
                // If it also fails, then we print 50 lines (last resort) | Se tudo isso der errado o jeito é imprimir 50 linhas mesmo
                for (int i = 0; i < 50; i++) System.out.println();
            }
        }
    }

}


