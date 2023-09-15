package github.serliunx.varytalk.project.system.mapper;

import github.serliunx.varytalk.project.system.entity.SystemPermission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SystemPermissionMapper {

    List<SystemPermission> selectList(SystemPermission systemPermission);

    Long insertPermission(SystemPermission systemPermission);

    SystemPermission selectByValue(String value);

    SystemPermission selectByName(String nodeName);

    SystemPermission selectById(Long id);
}
