package comskydream.cn.skydream.config;

import comskydream.cn.skydream.common.ResultJson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.ListUtils;
import org.apache.http.HttpStatus;
import org.springframework.core.annotation.Order;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.SQLException;

/**
 * 全局异常配置
 * @author Jayson
 * @date 2020/9/22 17:02
 */
@ControllerAdvice
@Order(1)
@Slf4j
public class GlobalExceptionConfiguration {


    /**
     * validator验证框架校验错误
     * @param validException
     * @return
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    @ResponseBody
    public ResultJson<String> handlerVaild(Exception validException){
        ResultJson<String> rj = new ResultJson<String>();
        rj.setCode(HttpStatus.SC_BAD_REQUEST);
        //提示信息
        if (validException instanceof MethodArgumentNotValidException) {
            rj.setMsg(getAllErrors(((MethodArgumentNotValidException) validException).getBindingResult()));
        } else {
            rj.setMsg(getAllErrors(((BindException) validException).getBindingResult()));
        }
        return rj;
    }


    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(org.springframework.http.HttpStatus.OK)
    @Order(1)
    @ResponseBody
    public ResultJson<String> handlerRankedException(Exception validException) {
        ResultJson<String> rj = new ResultJson<>();
        rj.setMsg(validException.getMessage());
        rj.setCode(HttpStatus.SC_BAD_REQUEST);
        return rj;
    }

    /**
     * <br>全局异常处理 ，所有的异常处理，
     *<br> 500错误
     * @param
     * @return
     */
    @ExceptionHandler({Exception.class, SQLException.class})
    @ResponseStatus(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR)
    @Order(2)
    @ResponseBody
    public ResultJson<String> handlerException(Exception e) {
        //记录日志
        log.error(e.getMessage(), e);
        ResultJson<String> errorJson = new ResultJson<>();
        errorJson.setMsg(e.getMessage());
        return errorJson;
    }

    /**
     * 获取校验错误信息
     *
     * @param result
     * @return
     */
    private String getAllErrors(BindingResult result) {
        StringBuffer sb = new StringBuffer();
        for (ObjectError error : result.getAllErrors()) {
            sb.append(error.getDefaultMessage() + ",");
        }
        if (sb.length() > 1) {
            sb = sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }



}
