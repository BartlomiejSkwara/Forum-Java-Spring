package com.projekt.forum.entity;

import com.projekt.forum.dataTypes.forms.CategoryCUForm;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(schema = "forum", name = "category")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcategory")
    private int categoryID;

    @Column(name = "url" , length = 45)
    private String url;
    @Column(name = "name", length = 45)
    private String name;

    @Column(name = "description", length = 90)
    private String description;


    public CategoryEntity(String name, String description) {
        this.name = name;
        this.url = this.name.toLowerCase().replace(' ','-');
        this.description = description;
    }

    ///
    public CategoryEntity(CategoryCUForm categoryCUForm){
        this(categoryCUForm.getCategoryName(),categoryCUForm.getCategoryDescription());
        if (categoryCUForm.getCategoryID()!=null)
            this.categoryID = categoryCUForm.getCategoryID();
    }

    public CategoryEntity(){

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategoryEntity that)) return false;
        return categoryID == that.categoryID && Objects.equals(url, that.url) && Objects.equals(name, that.name) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryID, url, name, description);
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int idCategory) {
        this.categoryID = idCategory;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
}
