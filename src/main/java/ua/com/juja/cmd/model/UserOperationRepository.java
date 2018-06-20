package ua.com.juja.cmd.model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ua.com.juja.cmd.model.entity.UserOperation;

import java.util.List;

public interface UserOperationRepository extends CrudRepository<UserOperation, Integer>, UserOperationRepositoryCustom {
    @Query(value = "SELECT uo FROM UserOperation uo WHERE uo.dbConnection.userName = :userName")
    List<UserOperation> findByUserName(@Param( value="userName") String userName);
}
