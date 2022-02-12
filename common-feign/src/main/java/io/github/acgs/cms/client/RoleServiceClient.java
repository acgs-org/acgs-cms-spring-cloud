package io.github.acgs.cms.client;

import io.github.acgs.cms.role.Role;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * <p>
 *     角色信息服务 feign 客户端接口
 * </p>
 * @author TierneyJohn@ACGS
 * <p>
 * file created on 2022/2/7
 * </p>
 */
@FeignClient(name = "role-service", path = "/role")
public interface RoleServiceClient {

    /**
     * <p>
     *     通过角色名称获取角色信息
     * </p>
     * @param roleName 角色名称
     * @return 角色信息
     */
    @GetMapping("/{roleName}")
    Role getRoleByName(@PathVariable("roleName") String roleName);

    /**
     * <p>
     *     填加新角色信息
     * </p>
     * @param roleName 角色名称
     * @return 添加结果
     */
    @PostMapping("/{roleName}")
    Boolean add(@PathVariable("roleName") String roleName);

    /**
     * <p>
     *     删除角色信息
     * </p>
     * @param roleName 角色名称
     * @return 删除结果
     */
    @DeleteMapping("/{roleName}")
    Boolean remove(@PathVariable("roleName") String roleName);
}
