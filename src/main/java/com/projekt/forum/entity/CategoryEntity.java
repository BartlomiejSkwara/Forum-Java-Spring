package com.projekt.forum.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Objects;

@Entity
@Table(schema = "forum", name = "category")
public class CategoryEntity {

    @Id()
    @GeneratedValue(strategy = GenerationType.UUID )
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "idcategory" , length = 45)
    private String idcategory;
    @Column(name = "name", length = 45)
    private String name;

    @Column(name = "description", length = 90)
    private String description;

    public CategoryEntity(String idcategory, String name, String description) {
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

    public CategoryEntity(){

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryEntity categoryEntity = (CategoryEntity) o;
        return Objects.equals(idcategory, categoryEntity.idcategory) && Objects.equals(name, categoryEntity.name) && Objects.equals(description, categoryEntity.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idcategory, name, description);
    }
}
