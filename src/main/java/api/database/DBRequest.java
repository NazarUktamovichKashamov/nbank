package api.database;

import api.configs.Config;
import api.dao.AccountDao;
import api.dao.TransactionDao;
import api.dao.UserDao;
import lombok.Builder;
import lombok.Data;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class DBRequest {
    private RequestType requestType;
    private String table;
    private List<Condition> conditions;
    private Class<?> extractAsClass;

    public enum RequestType {
        SELECT, INSERT, UPDATE, DELETE
    }

    /**
     * Выполняет запрос и возвращает результат, преобразованный в указанный тип.
     */
    public <T> T extractAs(Class<T> clazz) {
        this.extractAsClass = clazz;
        return executeQuery(clazz);
    }

    /**
     * Основной метод выполнения SQL-запроса и маппинга результата.
     */
    private <T> T executeQuery(Class<T> clazz) {
        String sql = buildSQL();

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // Подставляем параметры из условий
            if (conditions != null) {
                for (int i = 0; i < conditions.size(); i++) {
                    statement.setObject(i + 1, conditions.get(i).getValue());
                }
            }

            try (ResultSet resultSet = statement.executeQuery()) {
                if (clazz == UserDao.class) {
                    return (T) mapToUserDao(resultSet);
                } else if (clazz == AccountDao.class) {
                    return (T) mapToAccountDao(resultSet);
                } else if (clazz == TransactionDao.class) {
                    return (T) mapToTransactionDao(resultSet);
                } else {
                    throw new UnsupportedOperationException(
                            "Mapping for " + clazz.getSimpleName() + " not implemented"
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database query failed", e);
        }
    }

    // === Методы маппинга ===

    private UserDao mapToUserDao(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return UserDao.builder()
                    .id(resultSet.getLong("id"))
                    .username(resultSet.getString("username"))
                    .password(resultSet.getString("password"))
                    .role(resultSet.getString("role"))
                    .name(resultSet.getString("name"))
                    .build();
        }
        return null;
    }

    private AccountDao mapToAccountDao(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return AccountDao.builder()
                    .id(resultSet.getLong("id"))
                    .accountNumber(resultSet.getString("account_number"))
                    .balance(resultSet.getDouble("balance"))
                    .customerId(resultSet.getLong("customer_id"))
                    .build();
        }
        return null;
    }

    private TransactionDao mapToTransactionDao(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            String type = resultSet.getString("type");
            int accountId = resultSet.getInt("account_id");
            int relatedAccountId = resultSet.getInt("related_account_id");
            double amount = resultSet.getDouble("amount");

            // Определяем отправителя и получателя по типу операции
            int senderAccountId = "TRANSFER_OUT".equals(type) ? accountId : relatedAccountId;
            int receiverAccountId = "TRANSFER_IN".equals(type) ? accountId : relatedAccountId;

            return TransactionDao.builder()
                    .id(resultSet.getInt("id"))
                    .senderAccountId(senderAccountId)
                    .receiverAccountId(receiverAccountId)
                    .amount(amount)
                    .status(type) // можно использовать как status
                    .createdAt(resultSet.getTimestamp("created_at").toLocalDateTime().toString()) // или сохранить как LocalDateTime
                    .build();
        }
        return null;
    }

    // === Построение SQL ===

    private String buildSQL() {
        StringBuilder sql = new StringBuilder();

        switch (requestType) {
            case SELECT:
                sql.append("SELECT * FROM ").append(table);
                if (conditions != null && !conditions.isEmpty()) {
                    sql.append(" WHERE ");
                    for (int i = 0; i < conditions.size(); i++) {
                        if (i > 0) sql.append(" AND ");
                        sql.append(conditions.get(i).getColumn())
                                .append(" ")
                                .append(conditions.get(i).getOperator())
                                .append(" ?");
                    }
                }
                sql.append(" ORDER BY id DESC LIMIT 1");
                break;
            default:
                throw new UnsupportedOperationException("Request type " + requestType + " not implemented");
        }

        return sql.toString();
    }

    // === Подключение к БД ===

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                Config.getProperty("db.url"),
                Config.getProperty("db.username"),
                Config.getProperty("db.password")
        );
    }

    // === Builder ===

    public static DBRequestBuilder builder() {
        return new DBRequestBuilder();
    }

    public static class DBRequestBuilder {
        private RequestType requestType;
        private String table;
        private List<Condition> conditions = new ArrayList<>();
        private Class<?> extractAsClass;

        public DBRequestBuilder requestType(RequestType requestType) {
            this.requestType = requestType;
            return this;
        }

        public DBRequestBuilder table(String table) {
            this.table = table;
            return this;
        }

        public DBRequestBuilder and(Condition condition) {
            this.conditions.add(condition);
            return this;
        }

        public DBRequestBuilder where(Condition condition) {
            this.conditions.add(condition);
            return this;
        }

        public <T> T extractAs(Class<T> clazz) {
            this.extractAsClass = clazz;
            DBRequest request = DBRequest.builder()
                    .requestType(requestType)
                    .table(table)
                    .conditions(new ArrayList<>(conditions))
                    .extractAsClass(extractAsClass)
                    .build();
            return request.extractAs(clazz);
        }
    }
}