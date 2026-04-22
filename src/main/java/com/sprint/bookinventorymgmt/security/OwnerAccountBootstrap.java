package com.sprint.bookinventorymgmt.security;

import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Component
public class OwnerAccountBootstrap implements CommandLineRunner {

    private static final int STORE_OWNER_ROLE_ID = 3;
    private static final int APARNA_USER_ID = 1001;
    private static final int MOSES_USER_ID = 1002;
    private static final int SOBIKA_USER_ID = 1003;
    private static final int JANAPRIYA_USER_ID = 1004;
    private static final int SWARNALATHA_USER_ID = 1005;

    private final JdbcTemplate jdbcTemplate;
    private final PasswordEncoder passwordEncoder;
    private final DataSource dataSource;

    public OwnerAccountBootstrap(
            JdbcTemplate jdbcTemplate,
            PasswordEncoder passwordEncoder,
            DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.passwordEncoder = passwordEncoder;
        this.dataSource = dataSource;
    }

    @Override
    public void run(String... args) throws Exception {
        ColumnMap roleColumns = resolveColumns("permrole", Map.of(
                "id", new String[]{"role_number", "rolenumber"},
                "name", new String[]{"perm_role", "permrole"}
        ));

        ColumnMap userColumns = resolveColumns("users", Map.of(
                "id", new String[]{"user_id", "userid"},
                "firstName", new String[]{"first_name", "firstname"},
                "lastName", new String[]{"last_name", "lastname"},
                "phoneNumber", new String[]{"phone_number", "phonenumber"},
                "userName", new String[]{"user_name", "username"},
                "password", new String[]{"password"},
                "roleNumber", new String[]{"role_number", "rolenumber"}
        ));

        ensureRole(roleColumns, STORE_OWNER_ROLE_ID, "StoreOwner");

        ensureOwner(userColumns, APARNA_USER_ID, "Aparna", "Aparna", "Owner", "(716) 555-1001");
        ensureOwner(userColumns, MOSES_USER_ID, "Moses", "Moses", "Owner", "(716) 555-1002");
        ensureOwner(userColumns, SOBIKA_USER_ID, "Sobika", "Sobika", "Owner", "(716) 555-1003");
        ensureOwner(userColumns, JANAPRIYA_USER_ID, "Janapriya", "Janapriya", "Owner", "(716) 555-1004");
        ensureOwner(userColumns, SWARNALATHA_USER_ID, "SwarnaLatha", "SwarnaLatha", "Owner", "(716) 555-1005");
    }

    private void ensureRole(ColumnMap columns, int roleId, String roleName) {
        Integer existingRoleId = jdbcTemplate.query(
                "select " + columns.get("id") + " from permrole where " + columns.get("id") + " = ?",
                rs -> rs.next() ? rs.getInt(1) : null,
                roleId
        );

        if (existingRoleId == null) {
            jdbcTemplate.update(
                    "insert into permrole (" + columns.get("id") + ", " + columns.get("name") + ") values (?, ?)",
                    roleId,
                    roleName
            );
        } else {
            jdbcTemplate.update(
                    "update permrole set " + columns.get("name") + " = ? where " + columns.get("id") + " = ?",
                    roleName,
                    roleId
            );
        }
    }

    private void ensureOwner(
            ColumnMap columns,
            int userId,
            String username,
            String firstName,
            String lastName,
            String phoneNumber) {
        Integer existingUserId = jdbcTemplate.query(
                "select " + columns.get("id") + " from users where lower(" + columns.get("userName") + ") = lower(?)",
                rs -> rs.next() ? rs.getInt(1) : null,
                username
        );

        String encodedPassword = passwordEncoder.encode("password");

        if (existingUserId == null) {
            jdbcTemplate.update(
                    "insert into users (" +
                            columns.get("id") + ", " +
                            columns.get("firstName") + ", " +
                            columns.get("lastName") + ", " +
                            columns.get("phoneNumber") + ", " +
                            columns.get("userName") + ", " +
                            columns.get("password") + ", " +
                            columns.get("roleNumber") +
                            ") values (?, ?, ?, ?, ?, ?, ?)",
                    userId,
                    firstName,
                    lastName,
                    phoneNumber,
                    username,
                    encodedPassword,
                    STORE_OWNER_ROLE_ID
            );
        } else {
            jdbcTemplate.update(
                    "update users set " +
                            columns.get("firstName") + " = ?, " +
                            columns.get("lastName") + " = ?, " +
                            columns.get("phoneNumber") + " = ?, " +
                            columns.get("userName") + " = ?, " +
                            columns.get("password") + " = ?, " +
                            columns.get("roleNumber") + " = ? " +
                            "where " + columns.get("id") + " = ?",
                    firstName,
                    lastName,
                    phoneNumber,
                    username,
                    encodedPassword,
                    STORE_OWNER_ROLE_ID,
                    existingUserId
            );
        }
    }

    private ColumnMap resolveColumns(String tableName, Map<String, String[]> candidates) throws SQLException {
        Map<String, String> actualColumns = new HashMap<>();

        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            try (ResultSet resultSet = metaData.getColumns(null, null, tableName, null)) {
                while (resultSet.next()) {
                    String columnName = resultSet.getString("COLUMN_NAME");
                    actualColumns.put(columnName.toLowerCase(Locale.ROOT), columnName);
                }
            }
        }

        ColumnMap resolved = new ColumnMap();
        for (Map.Entry<String, String[]> entry : candidates.entrySet()) {
            String resolvedName = null;
            for (String candidate : entry.getValue()) {
                resolvedName = actualColumns.get(candidate.toLowerCase(Locale.ROOT));
                if (resolvedName != null) {
                    break;
                }
            }
            if (resolvedName == null) {
                throw new IllegalStateException("Could not resolve column for " + tableName + "." + entry.getKey());
            }
            resolved.put(entry.getKey(), resolvedName);
        }

        return resolved;
    }

    private static final class ColumnMap extends HashMap<String, String> {
    }
}
