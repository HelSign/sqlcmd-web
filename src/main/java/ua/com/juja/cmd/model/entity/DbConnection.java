package ua.com.juja.cmd.model.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "db_connection", schema = "public")
public class DbConnection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "db_name")
    private String dbName;

    @OneToMany(mappedBy = "dbConnection")
    private List<UserOperation> userOperations;

    public DbConnection() {
    }

    public DbConnection(String userName, String dbName) {
        this.userName = userName;
        this.dbName = dbName;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
