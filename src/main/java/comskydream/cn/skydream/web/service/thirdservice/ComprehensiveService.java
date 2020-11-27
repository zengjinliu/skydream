package comskydream.cn.skydream.web.service.thirdservice;

import comskydream.cn.skydream.model.vo.ComprehensiveVo;

/**
 * 获取第三方登陆对象和系统对象的顶级接口
 * @Description
 * @Author Jayson
 * @Date 2020/9/25 13:23
 */
public interface ComprehensiveService {

     ComprehensiveVo build(String code);
}
