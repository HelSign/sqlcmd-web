package ua.com.juja.cmd.model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ua.com.juja.cmd.model.entity.DbConnection;

import java.util.List;

public interface DbConnectionRepository extends CrudRepository<DbConnection, Integer> {
    DbConnection findByUserNameAndDbName(String userName, String dbName);
}
