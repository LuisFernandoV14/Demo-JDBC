package model.DAO.implemented;

import db.DB;
import db.DbException;
import db.DbIntegrityException;
import model.DAO.abstracted.DepartmentDAO;
import model.entities.Department;

import java.sql.*;
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

            } else System.out.println("A department with id '" + department.getId() + "' already exists in the Department Table");

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

    }

    @Override
    public void deleteById(Integer id) {

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
                System.out.println("No record found with id " + id);
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
        return List.of();
    }

    @Override
    public int getLastAddedById() {
        return 0;
    }
}
