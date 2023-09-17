package github.serliunx.varytalk.project.system.service;

import github.serliunx.varytalk.common.base.LoginUser;
import github.serliunx.varytalk.common.result.Result;
import github.serliunx.varytalk.project.system.entity.SystemUser;

import java.util.List;
import java.util.Set;

public interface SystemUserService {

    /**
     * 根据信息获取用户列表
     * @param systemUser 用户信息
     * @return 列表
     */
    List<SystemUser> selectList(SystemUser systemUser);

    /**
     * 根据用户id查找用户信息
     * @param id 用户id
     * @return 用户
     */
    SystemUser selectUserById(Long id);

    /**
     * 根据账户名称查找用户信息
     * @param username 账户名称
     * @return 用户
     */
    SystemUser selectUserByUsername(String username);

    /**
     * 通过账户名称判断用户是否已存在
     * @param username 用户名
     * @return 存在返回真, 否则返回假
     */
    boolean checkUserByUsername(String username);

    /**
     * 检查手机号是否已被使用
     * @param phoneNumber 手机号
     * @return 已被使用返回真, 否则返回假
     */
    boolean checkUserByPhoneNumber(String phoneNumber);

    /**
     * 检查邮箱是否已被使用
     * @param email 邮箱
     * @return 已被使用返回真, 否则返回假
     */
    boolean checkUserByEmail(String email);

    /**
     * 用户登录
     * @param user 用户
     * @return 结果封装
     */
    Result loginUser(LoginUser user);

    /**
     * 用户注册
     * @param systemUser 用户信息
     */
    void registerUser(SystemUser systemUser);

    /**
     * 新增一条用户无数
     * @param systemUser 用户信息
     */
    void insertUser(SystemUser systemUser);

    /**
     * 查询用户信息是否唯一
     *
     * <li> 手机号
     * <li> 用户名
     * <li> 邮箱
     * @param systemUser 用户
     * @return 结果, 结果为null时则验证通过.
     */
    String checkUserInformation(SystemUser systemUser);

    Set<LoginUser> getOnlineUser();

    void updateRole(SystemUser systemUser);
}
