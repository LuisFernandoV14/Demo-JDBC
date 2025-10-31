package model.DAO.implemented;

import db.DB;
import db.DbException;
import db.DbIntegrityException;
import model.DAO.abstracted.DepartmentDAO;
import model.DAO.abstracted.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SellerImplemented implements SellerDAO{

    Connection conn;

    public SellerImplemented(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Seller seller) {
        PreparedStatement ps = null;

        try {

            conn.setAutoCommit(false);

            if (findById(seller.getId()) == null) {

                System.out.println("Creating Seller with id " + seller.getId());

                ps = conn.prepareStatement("INSERT INTO seller (id, name, email, birthDate, baseSalary, departmentId) VALUES (?, ?, ?, ?, ?, ?)");
                ps.setInt(1, seller.getId());
                ps.setString(2, seller.getName());
                ps.setString(3, seller.getEmail());
                ps.setDate(4, Date.valueOf(seller.getBirthDate()));
                ps.setDouble(5, seller.getSalary());
                ps.setInt(6, seller.getDepartment().getId());

                ps.executeUpdate();

                conn.commit();

            } else {
                System.out.println("A seller with id '" + seller.getId() + "' already exists in the Seller Table");
                System.out.println("The last Seller added has the Id: " + getLastAdded().getId());
            }

            conn.setAutoCommit(true);

        } catch (SQLException e) {

            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new DbIntegrityException("Could not rollback Insert. Reason: " + e.getMessage());
            }

            throw new DbException("Insert got rolled back. Reason: " + e.getMessage());

        } finally {
            DB.closeStatement(ps);
        }

    }

    @Override
    public void update(Seller seller) {
        PreparedStatement st = null;

        try {
            conn.setAutoCommit(false);

            if (findById(seller.getId()) != null) {
                st = conn.prepareStatement("UPDATE Seller" +
                        " SET name = ?" +
                        ", email = ?" +
                        ", birthDate = ?" +
                        ", baseSalary = ?" +
                        ", departmentId = ?" +
                        " WHERE id = ?");

                st.setString(1, seller.getName());
                st.setString(2, seller.getEmail());
                st.setDate(3, Date.valueOf(seller.getBirthDate()));
                st.setDouble(4, seller.getSalary());
                st.setInt(5, seller.getDepartment().getId());
                st.setInt(6, seller.getId());
                st.executeUpdate();

                System.out.println("Seller with id '" +  seller.getId() + "' updated");
                conn.commit();
            } else System.out.println("There is no Seller with id '" + seller.getId() + "' in the Seller Table");

            conn.setAutoCommit(true);

        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new DbIntegrityException("Could not rollback update. Reason: " + e.getMessage());
            }
            throw new DbException("Update got rolled back. Reason: " + e.getMessage());
        } finally {
            DB.closeStatement(st);
        }

    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;

        try {
            conn.setAutoCommit(false);

            if (findById(id) != null) {
                st = conn.prepareStatement("DELETE FROM Seller WHERE id = ?");
                st.setInt(1, id);
                st.executeUpdate();
                System.out.println("Deleted Seller with id " + id);
                conn.commit();
            } else {
                System.out.println("There is no seller with id " + id);
            }

            conn.setAutoCommit(true);

        } catch (SQLException e) {

            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new DbIntegrityException("Could not rollback Delete. Reason: " + e.getMessage());
            }

            throw new DbException("Delete got rolled back. Reason: " + e.getMessage());
        } finally {
            DB.closeStatement(st);
        }


    }

    @Override
    public Seller findById(Integer id) {

        Statement st = null;
        ResultSet rs = null;
        Seller result;
        DepartmentDAO dep = DAOFactory.constructDepartment();

        try {
            st = conn.createStatement();
            rs = st.executeQuery("SELECT * FROM seller WHERE id = " + id);

            if (rs.next()) {
                result = new Seller(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        String.valueOf(rs.getDate("birthDate")),
                        rs.getDouble("basesalary"),
                        dep.findById(rs.getInt("departmentId"))
                );
            } else {
                return null;
            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }

        return result;
    }

    @Override
    public List<Seller> findAll() {
        List<Seller> result = new ArrayList<>();
        int i = 1;

        while (findById(i) != null) {
            result.add(findById(i));
            i++;
        }

        return result;
    }

    @Override
    public Seller getLastAdded() {
        List<Seller> sellers = findAll();

        if (sellers.isEmpty()) {
            System.out.println("No seller was added");
            return null;
        }

        return sellers.getLast();
    }
}
