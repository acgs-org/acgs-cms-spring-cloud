package io.github.acgs.cms.service;

import io.github.acgs.cms.entity.User;
import io.github.acgs.cms.entity.token.Tokens;

/**
 * <p>
 *     用户信息服务类
 * </p>
 * @author TierneyJohn@ACGS
 * <p>
 * file created on 2022/2/13
 * </p>
 */
public interface UserService {

    /**
     * 初始化数据库数据方法
     */
    void initDatabase();

    /**
     * <p>
     *     验证登录账号名称是否存在
     * </p>
     * @param username 待校验登录账号
     * @return 检索结果
     */
    boolean checkUsername(String username);

    /**
     * <p>
     *     验证登录账号和密码是否匹配
     * </p>
     * @param username 登录账户
     * @param password 登录密码
     * @return 验证结果
     */
    boolean checkUsernameAndPassword(String username, String password);

    /**
     * <p>
     *     验证角色名称是否存在
     * </p>
     * @param roleName 角色名称
     * @return 验证结果
     */
    boolean checkRoleName(String roleName);

    /**
     * <p>
     *     通过登录账号获取用户信息
     * </p>
     * @param username 登录账号
     * @return 所属用户信息
     */
    User getUserByUsername(String username);

    /**
     * <p>
     *     通过登录账号获取 Tokens 令牌
     * </p>
     * @param username 登录账号
     * @return Tokens 令牌
     */
    Tokens getTokensByUsername(String username);

    /**
     * <p>
     *     添加用户信息方法
     * </p>
     * @param user 带添加用户信息
     * @return 添加结果
     */
    boolean addUser(User user);
}
