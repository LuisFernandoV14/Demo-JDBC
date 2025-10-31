package view;

import db.DbException;
import db.DbIntegrityException;
import model.DAO.abstracted.DepartmentDAO;
import model.DAO.abstracted.SellerDAO;
import model.DAO.implemented.DAOFactory;
import model.entities.Department;
import model.entities.Seller;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.time.LocalDate;
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

        System.out.print("""
               Which table do you want to use?""
                1 - Seller
                2 - Department
                0 - End Program\s
               R: \s""");
        int choice = Integer.parseInt(sc.nextLine());

        switch(choice) {

            case 1:
                sellerMenu();
            break;

            case 2:
            break;

            case 0:
            break;
        }

    }

    public void sellerMenu() {
        cleanScreen();
        System.out.print("""
                What operation would you like to do in Seller Table?""
                 1 - Insert
                 2 - Update
                 3 - Delete
                 4 - View all
                 5 - View by id
                 0 - Back to first menu
                R: \s""");

        int choice = Integer.parseInt(sc.nextLine());

        switch(choice) {
            case 1:
                cleanScreen();

                System.out.print("Enter with seller name: ");
                String sellerName = sc.nextLine();

                System.out.print("Enter with seller email: ");
                String sellerEmail = sc.nextLine();

                System.out.print("Enter with seller birth date (yyyy-mm-dd): ");
                String sellerBirthDate = sc.nextLine();

                System.out.print("Enter with seller salary: ");
                Double  sellerSalary = Double.parseDouble(sc.nextLine());

                System.out.print("Enter with seller department ID: ");
                Department dep = departmentDAO.findById(Integer.parseInt(sc.nextLine()));

                sellerDAO.insert(new Seller( (sellerDAO.getLastAdded().getId() + 1), sellerName, sellerEmail, sellerBirthDate, sellerSalary, dep) );

                sellerMenu();
            break;

            case 2:
                cleanScreen();

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
            break;
        }

    }

    public void cleanScreen() {
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


