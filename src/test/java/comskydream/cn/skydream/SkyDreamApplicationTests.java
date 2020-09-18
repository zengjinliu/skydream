package comskydream.cn.skydream;

import com.sun.xml.internal.bind.v2.runtime.output.SAXOutput;
import comskydream.cn.skydream.converter.SysUserConverter;
import comskydream.cn.skydream.entity.SysMenu;
import comskydream.cn.skydream.entity.SysUser;
import comskydream.cn.skydream.model.SysUserVo;
import comskydream.cn.skydream.service.SysMenuService;
import comskydream.cn.skydream.utils.DateUtils;
import comskydream.cn.skydream.utils.HttpUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class SkyDreamApplicationTests {

    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private SysUserConverter sysUserConverter;
    @Autowired
    private HttpUtils httpUtils;

    @Test
    void getNavMenuList() {
        List<SysMenu> sysMenus = sysMenuService.queryAllMenu("1");
        Assert.notNull(sysMenus, "this list must not be null");
    }

    @Test
    public void test1() {
        Date date = DateUtils.localDate2Date(LocalDate.now());
        System.out.println(date);
        System.out.println(DateUtils.date2LocalDate(date));
        String dataStr = "2020-09-10";
        LocalDate localDate = DateUtils.str2LocalDate(dataStr, "yyyy-MM-dd");
        System.out.println(localDate);
    }

    @Test
    public void test2() {
        SysUser build = SysUser.builder().userId("1").password("12").build();
        SysUserVo sysUserVo = sysUserConverter.toVo(build);
        System.out.println(sysUserVo);
    }

    @Test
    public void test3() throws Exception{
        String apiUrl = "https://devapi.heweather.net/v7/air/now";
        Map<String,Object> map = new HashMap<>();
        map.put("location ","101010100");
        map.put("key ","e91649bf638149369fbcae6f93727614");
        String s = httpUtils.doGet(apiUrl,map);
        System.out.println(s);
    }
    @Test
    public void test4() throws Exception{
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = LocalDateTime.now().plusDays(1);
        System.out.println(start.isBefore(end));
    }
    @Test
    public void test5(){
        String salt = RandomStringUtils.randomAlphanumeric(6);
        System.out.println(salt);
    }

}
