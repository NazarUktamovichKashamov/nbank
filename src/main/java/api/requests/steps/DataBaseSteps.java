package api.requests.steps;

import api.dao.TransactionDao;
import api.dao.UserDao;
import api.database.Condition;
import api.database.DBRequest;
import common.helps.StepLogger;

public class DataBaseSteps {
    public static UserDao getUserByUsername(String username) {
        return StepLogger.log(("Get user from database by username: ") + username, () -> {
            return DBRequest.builder()
                    .requestType(DBRequest.RequestType.SELECT)
                    .table("customers")
                    .where(Condition.equalTo("username", username))
                    .extractAs(UserDao.class);
        });
    }

    public static TransactionDao getTransactionBySenderAccount(int accountId) {
        return StepLogger.log("Get transaction from DB by sender account_id: " + accountId, () -> {
            return DBRequest.builder()
                    .requestType(DBRequest.RequestType.SELECT)
                    .table("transactions")
                    .where(Condition.equalTo("account_id", accountId))
                    .and(Condition.equalTo("type", "TRANSFER_OUT"))
                    .extractAs(TransactionDao.class);
        });
    }

    public static TransactionDao getTransactionByReceiverAccount(int accountId) {
        return StepLogger.log("Get transaction from DB by sender account_id: " + accountId, () -> {
            return DBRequest.builder()
                    .requestType(DBRequest.RequestType.SELECT)
                    .table("transactions")
                    .where(Condition.equalTo("account_id", accountId))
                    .and(Condition.equalTo("type", "TRANSFER_IN"))
                    .extractAs(TransactionDao.class);
        });
    }
}
