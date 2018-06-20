package ua.com.juja.cmd.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.com.juja.cmd.model.entity.DbConnection;
import ua.com.juja.cmd.model.entity.UserOperation;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


public class UserOperationRepositoryImpl implements UserOperationRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private DbConnectionRepository dbConnectionRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void createOperation(DBManager dbManager, String operation) {
        String userName = dbManager.getUserName();
        String dbName = dbManager.getDbName();
        DbConnection dbConnection =
                dbConnectionRepository.findByUserNameAndDbName(userName, dbName);
        if (dbConnection == null) {
            dbConnection = new DbConnection(userName, dbName);
            dbConnectionRepository.save(dbConnection);
        }
        entityManager.persist(new UserOperation(dbConnection, operation));
    }
}

