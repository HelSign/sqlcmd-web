package ua.com.juja.cmd.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "user_operation", schema = "public")
public class UserOperation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "operation")
    private String operation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "db_connection_id", nullable = false)
    private DbConnection dbConnection;

    public UserOperation() {
    }

    public UserOperation(DbConnection dbConnection, String operation) {
        this.dbConnection = dbConnection;
        this.operation = operation;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public DbConnection getDbConnection() {
        return dbConnection;
    }

    public void setDbConnection(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }
}
