package com.sprint.bookinventorymgmt.ordermgmt.dto.responseDto;
import lombok.Data;

@Data
public class ShoppingCartResponseDTO {

    private Integer userId;
    private String isbn;

    public ShoppingCartResponseDTO() {
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public  Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public ShoppingCartResponseDTO(String isbn,Integer UserId) {
        this.isbn = isbn;
        this.userId=userId;
    }
}