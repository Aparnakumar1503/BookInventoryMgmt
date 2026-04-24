package com.sprint.bookinventorymgmt.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class PaginationUtils {

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 10;

    private PaginationUtils() {
    }

    public static <T> PaginatedResponse<T> paginate(List<T> source, Integer page, Integer size) {
        List<T> safeSource = source == null ? Collections.emptyList() : source;
        int safePage = page == null || page < 0 ? DEFAULT_PAGE : page;
        int safeSize = size == null || size <= 0 ? DEFAULT_SIZE : size;
        int totalItems = safeSource.size();
        int totalPages = totalItems == 0 ? 0 : (int) Math.ceil((double) totalItems / safeSize);
        int fromIndex = Math.min(safePage * safeSize, totalItems);
        int toIndex = Math.min(fromIndex + safeSize, totalItems);
        List<T> items = fromIndex >= toIndex
                ? Collections.emptyList()
                : new ArrayList<>(safeSource.subList(fromIndex, toIndex));

        return new PaginatedResponse<>(
                items,
                safePage,
                safeSize,
                totalItems,
                totalPages,
                safePage + 1 < totalPages,
                safePage > 0 && totalItems > 0
        );
    }
}
