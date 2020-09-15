package comskydream.cn.skydream;

import comskydream.cn.skydream.converter.SysUserConverter;
import comskydream.cn.skydream.entity.SysMenu;
import comskydream.cn.skydream.entity.SysUser;
import comskydream.cn.skydream.model.SysUserVo;
import comskydream.cn.skydream.service.SysMenuService;
import comskydream.cn.skydream.utils.DateUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@SpringBootTest
class SkyDreamApplicationTests {

    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private SysUserConverter sysUserConverter;

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

}
