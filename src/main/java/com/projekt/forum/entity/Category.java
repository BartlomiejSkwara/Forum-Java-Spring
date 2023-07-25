package com.projekt.forum.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Objects;

@Entity
@Table(schema = "forum", name = "category")
public class Category {

    @Id()
    @GeneratedValue(strategy = GenerationType.UUID )
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "idcategory" , length = 45)
    String idcategory;
    @Column(name = "name", length = 45)
    String name;

    @Column(name = "description", length = 90)
    String description;

    public Category(String idcategory, String name, String description) {
        this.idcategory = idcategory;
        this.name = name;
        this.description = description;
    }

    public String getIdcategory() {
        return idcategory;
    }

    public void setIdcategory(String idcategory) {
        this.idcategory = idcategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category(){

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(idcategory, category.idcategory) && Objects.equals(name, category.name) && Objects.equals(description, category.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idcategory, name, description);
    }
}
