package model.DAO.implemented;

import db.DB;
import db.DbException;
import model.DAO.abstracted.DepartmentDAO;
import model.entities.Department;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class DepartmentImplemented implements DepartmentDAO {

    Connection conn = null;

    public DepartmentImplemented(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Department department) {

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
        Department result = null;

        try {

            st = conn.createStatement();
            rs = st.executeQuery("SELECT * FROM department WHERE id = " + id);
            rs.next();

            result = new Department(
                    rs.getString("name"),
                    rs.getInt("id")
            );

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
}
