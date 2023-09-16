package github.serliunx.varytalk.project.system.mapper;

import github.serliunx.varytalk.project.system.entity.SystemUserPermission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SystemUserPermissionMapper {

    List<SystemUserPermission> selectList(SystemUserPermission systemUserPermission);

    Long insertUserPermission(SystemUserPermission systemUserPermission);

    SystemUserPermission checkIfGiven(Long userId, Long permissionId);

    List<SystemUserPermission> selectByUserId(Long userId);
}
