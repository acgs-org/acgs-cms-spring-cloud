package io.github.acgs.cms.repository;

import io.github.acgs.cms.entity.Role;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *     角色信息仓储层
 * </p>
 * @author TierneyJohn@ACGS
 * <p>
 * file created on 2022/2/2
 * </p>
 */
@Repository
public interface RoleRepository extends MongoRepository<Role, ObjectId> {

    /**
     * <p>
     *     通过角色名称获取角色信息
     * </p>
     * @param name 角色名称
     * @return 角色信息 | null
     */
    Role findRoleByName(String name);

    /**
     * <p>
     *     通过角色名称删除角色信息
     * </p>
     * @param name 角色名称
     * @return 删除结果 true | false
     */
    int deleteRoleByName(String name);

}
