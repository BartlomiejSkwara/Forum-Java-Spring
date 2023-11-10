package com.projekt.forum.dataTypes.forms;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Sort;

public class CategoryFilterForm {

    @Min(value = 0, message = "Próbujesz dostać się na ujemną stronę !!!")
    private Integer currentPage;
    @NotNull(message = "Niepoprawny kierunek sortowania !!!")
    private Sort.Direction sortDirection;

    @NotNull(message = "Niepoprawny parametr sortowania !!!")
    private String sortProperty;
    private String filter;

    public CategoryFilterForm(Integer page, String sortDirection, String sortBy, String filter) {
        this.currentPage = (page == null)?0:page;
        this.sortDirection = (sortDirection.equals("ASC"))? Sort.Direction.ASC:
                                                        (sortDirection.equals("DESC"))? Sort.Direction.DESC:null;
        this.sortProperty = (sortBy.equals("updateDate")||sortBy.equals("creationDate")||sortBy.equals("messageCount")||sortBy.equals("topic"))?sortBy:null;
        this.filter = filter;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public Sort.Direction getSortDirection() {
        return sortDirection;
    }

    public String getSortProperty() {
        return sortProperty;
    }

    public String getFilter() {
        return filter;
    }
}
