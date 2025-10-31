package model.DAO.implemented;

import db.DB;
import db.DbException;
import db.DbIntegrityException;
import model.DAO.abstracted.DepartmentDAO;
import model.entities.Department;
import model.entities.Seller;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentImplemented implements DepartmentDAO {

    Connection conn;

    public DepartmentImplemented(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Department department) {
        PreparedStatement ps = null;

        try {

            conn.setAutoCommit(false);

            if (findById(department.getId()) == null) {

                System.out.println("Creating Department with id " + department.getId());

                ps = conn.prepareStatement("INSERT INTO department (id, name) VALUES (?, ?)");
                ps.setInt(1, department.getId());
                ps.setString(2, department.getName());

                ps.executeUpdate();

                conn.commit();

            } else {
                System.out.println("A department with id '" + department.getId() + "' already exists in the Department Table");
                System.out.println("The last Department added has the Id: " + getLastAdded().getId());
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
    public void update(Department department) {
        PreparedStatement st = null;

        try {
            conn.setAutoCommit(false);

            if (findById(department.getId()) != null) {
                st = conn.prepareStatement("UPDATE Department" +
                        " SET name = ?" +
                        " WHERE id = ?");

                st.setString(1, department.getName());
                st.setInt(2, department.getId());
                st.executeUpdate();

                System.out.println("Department with id '" +  department.getId() + "' updated");
                conn.commit();
            } else System.out.println("There is no Department with id '" + department.getId() + "' in the Department Table");

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
                st = conn.prepareStatement("DELETE FROM Department WHERE id = ?");
                st.setInt(1, id);
                st.executeUpdate();
                System.out.println("Deleted Department with id " + id);
                conn.commit();
            } else {
                System.out.println("There is no department with id " + id);
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
    public Department findById(Integer id) {

        Statement st = null;
        ResultSet rs = null;
        Department result;

        try {

            st = conn.createStatement();
            rs = st.executeQuery("SELECT * FROM department WHERE id = " + id);

            if (rs.next()) {
                result = new Department(
                    rs.getString("name"),
                    rs.getInt("id")
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
    public List<Department> findAll() {

        List<Department> result = new ArrayList<>();
        int i = 1;

        while (findById(i) != null) {
            result.add(findById(i));
            i++;
        }

        return result;
    }

    @Override
    public Department getLastAdded() {
        List<Department> departments = findAll();

        if (departments.isEmpty()) {
            System.out.println("No Department was added");
            return null;
        }

        return departments.getLast();
    }
}
