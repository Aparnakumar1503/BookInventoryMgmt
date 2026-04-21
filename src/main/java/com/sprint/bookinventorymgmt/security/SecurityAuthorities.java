package com.sprint.bookinventorymgmt.security;

import java.util.List;
import java.util.Locale;

public final class SecurityAuthorities {

    public static final String OWNER_USER_AUTHOR = "OWNER_USER_AUTHOR";
    public static final String OWNER_BOOK = "OWNER_BOOK";
    public static final String OWNER_INVENTORY = "OWNER_INVENTORY";
    public static final String OWNER_ORDER = "OWNER_ORDER";
    public static final String OWNER_REVIEW = "OWNER_REVIEW";

    private SecurityAuthorities() {
    }

    public static List<String> moduleAuthoritiesFor(String username) {
        String normalized = normalize(username);

        return switch (normalized) {
            case "aparna" -> List.of(OWNER_USER_AUTHOR);
            case "moses" -> List.of(OWNER_BOOK);
            case "sobika" -> List.of(OWNER_INVENTORY);
            case "janapriya" -> List.of(OWNER_ORDER);
            case "swarnalatha" -> List.of(OWNER_REVIEW);
            default -> List.of();
        };
    }

    public static List<String> moduleIdsFor(String username) {
        String normalized = normalize(username);

        return switch (normalized) {
            case "aparna" -> List.of("users-authors");
            case "moses" -> List.of("books");
            case "sobika" -> List.of("inventory");
            case "janapriya" -> List.of("orders");
            case "swarnalatha" -> List.of("reviews");
            default -> List.of();
        };
    }

    private static String normalize(String username) {
        return username == null ? "" : username.toLowerCase(Locale.ROOT);
    }
}
