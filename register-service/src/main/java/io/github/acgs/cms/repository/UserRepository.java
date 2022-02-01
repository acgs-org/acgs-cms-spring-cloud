package io.github.acgs.cms.repository;

import io.github.acgs.cms.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *     用户信息仓储层
 * </p>
 *
 * @author TierneyJohn@ACGS
 * <p>
 * file created on 2022/2/1
 * </p>
 */
@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {

    /**
     * <p>
     * 通过用户名称获取用户信息
     * </p>
     * @param username 用户名称
     * @return 查询到的用户信息 | null
     */
    User findUserByUsername(String username);

}
