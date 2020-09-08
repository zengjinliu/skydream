package comskydream.cn.skydream;

import comskydream.cn.skydream.entity.SysMenu;
import comskydream.cn.skydream.service.SysMenuService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;

@SpringBootTest
class SkyDreamApplicationTests {

    @Autowired
    private SysMenuService sysMenuService;

    @Test
    void getNavMenuList() {
        List<SysMenu> sysMenus = sysMenuService.queryAllMenu("1");
        Assert.notNull(sysMenus, "this list must not be null");

    }

}
