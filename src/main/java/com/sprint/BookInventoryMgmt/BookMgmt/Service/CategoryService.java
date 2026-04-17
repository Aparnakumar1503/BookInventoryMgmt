package com.sprint.BookInventoryMgmt.BookMgmt.Service;

import com.sprint.BookInventoryMgmt.BookMgmt.Entity.Category;
import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    Category getCategoryById(Integer id);
}