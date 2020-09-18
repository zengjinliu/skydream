package comskydream.cn.skydream.utils;

import comskydream.cn.skydream.entity.SysUser;
import comskydream.cn.skydream.mapper.SysUserMapper;
import comskydream.cn.skydream.service.SysUserService;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoField;
import java.util.Objects;
import java.util.Random;

/**
 * @author Jayson
 * @date 2020/9/18 14:41
 */
@Component
public class NameGeneratorUtils {

    @Autowired
    private SysUserService sysUserService;

    public String generatorName(){
        String name = generator();
        Boolean exist = sysUserService.checkNameExist(name);
        while (exist){
            name = generator();
        }
        return name;
    }

    private String generator(){
        StringBuilder sb = new StringBuilder();
        long l = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        String substring = String.valueOf(l).substring(0, 4);
        String name = RandomStringUtils.randomAlphanumeric(20);
        sb.append(name).append(substring);
        return sb.toString();
    }


}
