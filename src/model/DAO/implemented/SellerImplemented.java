package model.DAO.implemented;

import db.DB;
import db.DbException;
import model.DAO.abstracted.DepartmentDAO;
import model.DAO.abstracted.SellerDAO;
import model.entities.Seller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class SellerImplemented implements SellerDAO{

    Connection conn = null;

    public SellerImplemented(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Seller seller) {

    }

    @Override
    public void update(Seller seller) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Seller findById(Integer id) {

        Statement st = null;
        ResultSet rs = null;
        Seller result = null;
        DepartmentDAO dep = DAOFactory.constructDepartment();

        try {
            st = conn.createStatement();
            rs = st.executeQuery("SELECT * FROM seller WHERE id = " + id);
            rs.next();

            result = new Seller(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    String.valueOf(rs.getDate("birthDate")),
                    rs.getDouble("basesalary"),
                    dep.findById(rs.getInt("departmentId"))
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
    public List<Seller> findAll() {
        return List.of();
    }
}
