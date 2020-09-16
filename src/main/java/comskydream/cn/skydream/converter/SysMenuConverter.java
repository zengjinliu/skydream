package comskydream.cn.skydream.converter;

import comskydream.cn.skydream.common.BaseBeanConverter;
import comskydream.cn.skydream.entity.SysMenu;
import comskydream.cn.skydream.model.SysMenuVo;
import org.mapstruct.Mapper;

/**
 * @Description
 * @Author Jayson
 * @Date 2020/9/16 15:22
 */
@Mapper(componentModel = "spring")
public interface SysMenuConverter extends BaseBeanConverter<SysMenu, SysMenuVo> {
}
