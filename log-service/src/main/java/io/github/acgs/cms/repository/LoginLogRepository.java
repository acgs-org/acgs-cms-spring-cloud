package io.github.acgs.cms.repository;

import io.github.acgs.cms.entity.LoginLog;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *     登录日志仓储层
 * </p>
 *
 * @author TierneyJohn@ACGS
 * <p>
 * file created on 2022/3/4
 * </p>
 */
@Repository
public interface LoginLogRepository extends MongoRepository<LoginLog, ObjectId> {
}
