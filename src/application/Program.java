package application;

import db.DB;
import view.View;

import java.sql.Connection;
import java.util.Scanner;

public class Program {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Connection con = DB.getConnection();

        View view = new View(sc, con);

        view.menu();
    }

}
