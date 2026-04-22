package com.sprint.bookinventorymgmt.ordermgmt.controller;

import com.sprint.bookinventorymgmt.common.ResponseBuilder;
import com.sprint.bookinventorymgmt.common.PaginatedResponse;
import com.sprint.bookinventorymgmt.common.PaginationUtils;
import com.sprint.bookinventorymgmt.common.ResponseStructure;
import com.sprint.bookinventorymgmt.ordermgmt.dto.requestDto.ShoppingCartRequestDTO;
import com.sprint.bookinventorymgmt.ordermgmt.dto.responseDto.ShoppingCartResponseDTO;
import com.sprint.bookinventorymgmt.ordermgmt.service.IShoppingCartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
public class ShoppingCartController {

    private final IShoppingCartService service;

    public ShoppingCartController(IShoppingCartService service) {
        this.service = service;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ResponseStructure<PaginatedResponse<ShoppingCartResponseDTO>>> getByUser(
            @PathVariable Integer userId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        return ResponseEntity.ok(
                ResponseBuilder.success(
                        HttpStatus.OK.value(),
                        "Cart items fetched successfully",
                        PaginationUtils.paginate(service.getByUserId(userId), page, size)
                )
        );
    }

    @PostMapping("/{userId}/items/{isbn}")
    public ResponseEntity<ResponseStructure<ShoppingCartResponseDTO>> addCart(
            @PathVariable Integer userId,
            @PathVariable String isbn) {
        ShoppingCartRequestDTO requestDTO = new ShoppingCartRequestDTO();
        requestDTO.setUserId(userId);
        requestDTO.setIsbn(isbn);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ResponseBuilder.success(
                                HttpStatus.CREATED.value(),
                                "Cart item created successfully",
                                service.addCart(requestDTO)
                        )
                );
    }

    @DeleteMapping("/{userId}/items/{isbn}")
    public ResponseEntity<ResponseStructure<String>> delete(
            @PathVariable Integer userId,
            @PathVariable String isbn) {
        return ResponseEntity.ok(
                ResponseBuilder.success(
                        HttpStatus.OK.value(),
                        "Cart item deleted successfully",
                        service.delete(userId, isbn)
                )
        );
    }
}
