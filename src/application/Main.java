package application;

import model.DAO.implemented.DAOFactory;
import model.DAO.abstracted.DepartmentDAO;
import model.DAO.abstracted.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);

        DepartmentDAO departmentDAO = DAOFactory.constructDepartment();
        Department department = departmentDAO.findById(1);

        SellerDAO sellerDAO = DAOFactory.constructSeller();
        Seller seller = sellerDAO.findById(1);

        System.out.println();
        System.out.println(department);
        System.out.println();
        System.out.println(seller);

    }
}