package github.serliunx.varytalk.security.controller;

import github.serliunx.varytalk.common.base.BaseController;
import github.serliunx.varytalk.common.base.LoginUser;
import github.serliunx.varytalk.common.result.Result;
import github.serliunx.varytalk.common.util.JwtUtils;
import github.serliunx.varytalk.project.system.entity.SystemUser;
import github.serliunx.varytalk.project.system.service.SystemUserService;
import org.springframework.util.DigestUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController extends BaseController {

    private final SystemUserService systemUserService;
    private final JwtUtils jwtUtils;

    public AuthController(SystemUserService systemUserService, JwtUtils jwtUtils) {
        this.systemUserService = systemUserService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("login")
    public Result login(@RequestBody @Validated LoginUser user){
        SystemUser userFound = systemUserService.selectUserByUsername(user.getUsername());
        if(userFound == null){
            return fail("该用户不存在!");
        }
        String passWord = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        if(!userFound.getPassword().equals(passWord)){
            return fail("密码错误!");
        }
        user.setId(userFound.getId());
        String token = jwtUtils.getLoginToken(user);
        user.setPassword(passWord);
        user.setToken(token);
        user.setId(userFound.getId());
        return systemUserService.loginUser(user);
    }

    @PostMapping("register")
    public Result register(@RequestBody @Validated SystemUser user){
        if(systemUserService.checkUserByUsername(user.getUsername())){
            return fail("该用户名已被使用, 请更换一个用户名试试.");
        }
        if(user.getEmail() != null && systemUserService.checkUserByEmail(user.getEmail())){
            return fail("该邮箱已被其它用户绑定, 换一个试试.");
        }
        if(user.getPhoneNumber() != null && systemUserService.checkUserByPhoneNumber(user.getPhoneNumber())){
            return fail("该手机号已被其它用户绑定, 换一个试试.");
        }
        systemUserService.registerUser(user);
        return success(user.getId(), "注册成功! 你现在可以登录了.");
    }

    public Result logout(){
        return Result.success("成功注销!");
    }
}
