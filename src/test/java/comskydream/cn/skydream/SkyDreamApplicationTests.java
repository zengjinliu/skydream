package comskydream.cn.skydream;

import comskydream.cn.skydream.component.MessageSendConfiguration;
import comskydream.cn.skydream.converter.SysUserConverter;
import comskydream.cn.skydream.entity.SysMenu;
import comskydream.cn.skydream.entity.SysUser;
import comskydream.cn.skydream.model.vo.SysUserVo;
import comskydream.cn.skydream.service.sys.SysMenuService;
import comskydream.cn.skydream.utils.DateUtils;
import comskydream.cn.skydream.utils.HttpUtils;
import comskydream.cn.skydream.utils.SkyCollectionUtils;
import comskydream.cn.skydream.utils.SkyStringUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SkyDreamApplicationTests {

    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private SysUserConverter sysUserConverter;
    @Autowired
    private HttpUtils httpUtils;
    @Autowired
    private MessageSendConfiguration messageSendConfiguration;


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

    }

    @Test
    public void testGetRequest() throws Exception {

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

    @Test
    public void test6(){
       messageSendConfiguration.sendMsg("18379254458", "543212");

    }
    @Test
    public void test7() throws Exception{
        String apiUrl = "https://api.weibo.com";
        String path ="/oauth2/access_token";
        Map<String,Object> params = new HashMap<>(16);
        params.put("client_id","3075657313");
        params.put("client_secret","6a650b37c2d1ec667e94d5ce5c0635ad");
        params.put("grant_type","authorization_code");
        params.put("redirect_uri","http://skydream.com/third/weibo/success");
        params.put("code","37926993c460108a83657283b88efb8e");
         httpUtils.doPost(apiUrl, path, "POST", null, null,params);

    }
    @Test
    public void test8(){
        String str = "a,b,c";
        List<String> strings = SkyStringUtils.splitToList(str);
        strings.forEach(System.out::println);
        System.out.println(SkyCollectionUtils.split(strings, 2));
    }

}
