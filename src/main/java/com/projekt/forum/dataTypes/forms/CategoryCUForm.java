package com.projekt.forum.dataTypes.forms;

import com.projekt.forum.utility.ValidationUtility;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.CreditCardNumber;

public class CategoryCUForm {
    @NotNull(message = "Nie przekazano żadnej nazwy kategorii")
    //@NotBlank(message = "Pusta nazwa kategorii")
    @Size(  min = 1,
            max = 45,
            message = "Nazwa powinna mieć liczbę znaków w zakresie od 1 do 45")
    @Pattern(   regexp = ValidationUtility.URL_REGEX,
                message = "Wartość w polu \"Nazwa Kategorii\" zawiera jeden z zakazanych znaków: \" \\' & < > \\ /")
    String categoryName;


    @NotNull(message = "Nie przekazano żadnego opisu kategorii")
    //@NotBlank(message = "Pusty opis kategorii")
    @Size(  min = 1,
            max = 90,
            message = "Opis powinien mieć liczbę znaków w zakresie od 1 do 90")
    @Pattern(   regexp = ValidationUtility.UNIVERSAL_REGEX,
                message = "Wartość w polu \" Opis Kategorii \" zawiera jeden z zakazanych znaków:  \" \' & < > ")
    String categoryDescription;

    Integer categoryID;
    String categoryUrl;

    public CategoryCUForm(Integer categoryID, String categoryName, String categoryDescription){
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;
        this.categoryID = categoryID;
        this.categoryUrl = this.categoryName.toLowerCase().replace(' ','-');
    }


    public String getCategoryName() {
        return categoryName;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public Integer getCategoryID(){return  categoryID;}

    public String getCategoryUrl() {
        return categoryUrl;
    }



}
