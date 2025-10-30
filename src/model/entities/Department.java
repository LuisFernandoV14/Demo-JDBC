package model.entities;

import java.io.Serial;
import java.io.Serializable;

public class Department implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    // Attributes
    private Integer id;
    private String name;

    // Constructors
    public Department(String name, Integer id) {
        this.name = name;
        this.id = id;
    }
    public Department() {}

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

    // hashCode and equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Department department = (Department) o;
        return getId().equals(department.getId()) && getName().equals(department.getName());
        /* ---
        Two departments are equal if their Id and Name are the same
        Dois departamentos são iguais se o Id e o Nome forem o mesmo
        --- */
    }
    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getName().hashCode();
        return result;
        /* ---
        Two departments are truly equal if their hashCode are the same and if .equals() returns true
        Dois departamentos serão verdadeiramente iguais se o hashCode deles forem iguais e se .equals() retornar true
         --- */
    }

    // toString
    @Override
    public String toString() {
        return "Department: {" +
                "name = " + name +
                ", id = " + id + "}";
        // Example: "Department: {name = Electronics, id = 1}"
    }

}
