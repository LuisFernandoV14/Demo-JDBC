package model.entities;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

public class Seller implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    // Attributes
    private Integer id;
    private String name;
    private String email;
    private LocalDate birthDate;
    private Double salary;

    private Department department;

    // Constructors
    public Seller(Integer id, String name, String email, String birthDate, Double salary,  Department department) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birthDate = LocalDate.parse(birthDate);
        this.salary = salary;
        this.department = department;
    }
    public Seller() {
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public LocalDate getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
    public Double getSalary() {
        return salary;
    }
    public void setSalary(Double salary) {
        this.salary = salary;
    }

    // hashCode and equals

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Seller seller = (Seller) o;
        return getId().equals(seller.getId());
        /* ---
        Two sellers are equal if their Id are the same
        Dois vendedores são iguais se o Id for o mesmo
        --- */
    }
    @Override
    public int hashCode() {
        return getId().hashCode();
        /* ---
        Two sellers are truly equal if their hashCode are the same and if .equals() returns true
        Dois vendedores serão verdadeiramente iguais se o hashCode deles forem iguais e se .equals() retornar true
         --- */
    }

    // toString
    @Override
    public String toString() {
        return "Seller: {" +
                "name = " + name +
                ", email = " + email +
                ", birthDate = " + birthDate +
                ", salary = $" + String.format("%.2f", salary) +
                ", id = " + id +
                ", " + department +
                '}';
        // Example: "Seller: {name = John, email = john.1406@gmail.com, birthDate = 1990-01-31, salary = $2000.00, id = 1, Department: {name = Electronics, id = 1}}"
    }
}
