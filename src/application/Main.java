package application;

import db.DbException;
import model.DAO.implemented.DAOFactory;
import model.DAO.abstracted.DepartmentDAO;
import model.DAO.abstracted.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

import java.util.List;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);

        // Testing Department Insert | Testando Insert em Department
        DepartmentDAO departmentDAO = DAOFactory.constructDepartment();
        // ---
        Department dep = new Department("Sports", 2);
        departmentDAO.insert(dep); // Results in error | Resulta em erro
        // ---
        dep.setId(5);
        departmentDAO.insert(dep); // Insert new Department | Insere Department novo

        System.out.println("\n/* ---------------------- */\n");

        // Testing Seller Insert | Testando Insert em Seller
        SellerDAO sellerDAO = DAOFactory.constructSeller();
        // ---
        Seller sel = new Seller(1, "Sarah Orange", "sarah@gmail.com", "1994-06-12", 700.00, departmentDAO.findById(5));
        sellerDAO.insert(sel); // Gives an error | Resulta em erro
        // ---
        sel.setId(7);
        sellerDAO.insert(sel); // Insert new Seller | Insere Seller novo

        System.out.println("\n/* ---------------------- */\n");

        // Testing findAll from departments | Testando findAll para departamentos
        List<Department> departments = departmentDAO.findAll();

        departments.forEach(System.out::println);

        System.out.println("\n/* ---------------------- */\n");

        // Testing findAll from sellers | Testando findAll para vendedores
        List<Seller> sellers = sellerDAO.findAll();

        sellers.forEach(System.out::println);

        System.out.println("\n/* ---------------------- */\n");

        // Testing delete | Testando delete
        try {
            departmentDAO.deleteById(5); // Results in error. Can't delete it while it is a FK (Seller.departmentId). | Retorna erro. Não dá pra deletar enquanto ele é uma FK (Seller.departmentID)
        } catch (DbException e) {
            System.out.println(e.getMessage());
            sellerDAO.deleteById(7); // Delete the FK | Deleta a FK
            departmentDAO.deleteById(5); // Works | Funciona
        }

        sellerDAO.deleteById(999);
        departmentDAO.deleteById(999);

    }
}