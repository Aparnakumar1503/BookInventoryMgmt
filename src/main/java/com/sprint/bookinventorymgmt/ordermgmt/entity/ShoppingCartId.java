package com.sprint.BookInventoryMgmt.ordermgmt.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ShoppingCartId implements Serializable {

    private Integer userId;
    private String isbn;

    public ShoppingCartId() {}

    public ShoppingCartId(Integer userId, String isbn) {
        this.userId = userId;
        this.isbn = isbn;
    }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ShoppingCartId)) return false;
        ShoppingCartId that = (ShoppingCartId) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(isbn, that.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, isbn);
    }
}
