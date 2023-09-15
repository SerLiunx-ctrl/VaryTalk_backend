package github.serliunx.varytalk.common.exception.handler;

import com.auth0.jwt.exceptions.JWTVerificationException;
import github.serliunx.varytalk.common.exception.ServiceException;
import github.serliunx.varytalk.common.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Component
public class GlobalExceptionHandler {

    private final Logger logger;

    public GlobalExceptionHandler() {
        this.logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    }

    /**
     * 数据校验消息处理
     * @param exception 异常
     * @return 结果
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result doNoAuthException(MethodArgumentNotValidException exception){
        BindingResult bindingResult = exception.getBindingResult();
        //只需取第一个参数的错误文本
        String message = bindingResult.getAllErrors().get(0).getDefaultMessage();
        return Result.fail(null, message, 400);
    }

    /**
     * 请求参数异常处理
     * @return 结果
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result doHttpMessageNotReadableException(){
        return Result.fail(null, "请求参数有误!", 400);
    }

    /**
     * token有效验证
     * @return 结果
     */
    @ExceptionHandler(JWTVerificationException.class)
    public Result doJWTVerificationException(){
        return Result.fail("token无效!");
    }

    /**
     * 服务异常
     * @param e 异常
     * @return 结果
     */
    @ExceptionHandler(ServiceException.class)
    public Result doServiceException(ServiceException e){
        return Result.fail(null, e.getMessage(), e.getStatus());
    }

    /**
     * 其它异常, 会将错误信息打印至控制台中.
     * @param e 异常
     * @return 结果
     */
    @ExceptionHandler(RuntimeException.class)
    public Result doDefaultException(RuntimeException e){
        logger.error("发生异常-> {}: {}", e.getClass().getName(), e.getMessage());
        return Result.fail(null, "发生了未知错误, 请联系管理员!", 400);
    }
}
