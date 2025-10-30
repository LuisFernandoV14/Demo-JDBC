package model.DAO.implemented;

import db.DB;

public class DAOFactory {

    public static SellerImplemented constructSeller() {
        return new SellerImplemented(DB.getConnection());
    }

    public static DepartmentImplemented constructDepartment() {
        return new DepartmentImplemented(DB.getConnection());
    }

}
