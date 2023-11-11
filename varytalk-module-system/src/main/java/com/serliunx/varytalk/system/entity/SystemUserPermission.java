package com.serliunx.varytalk.system.entity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.serliunx.varytalk.cache.annotation.TagValue;
import com.serliunx.varytalk.common.base.BaseEntity;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonPropertyOrder({"id", "userId", "permissionId", "username", "permissionValue"})
public class SystemUserPermission extends BaseEntity {
    private Long id;

    @NotNull(message = "请指定用户!")
    @TagValue("userId")
    private Long userId;

    @NotNull(message = "请指定权限节点!")
    private Long permissionId;
    private String username;
    private String permissionValue;
}
