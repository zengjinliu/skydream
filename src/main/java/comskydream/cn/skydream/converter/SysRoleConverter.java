package comskydream.cn.skydream.converter;

import comskydream.cn.skydream.common.BaseBeanConverter;
import comskydream.cn.skydream.entity.SysRole;
import comskydream.cn.skydream.model.vo.SysRoleVo;
import org.mapstruct.Mapper;

/**
 * @Description
 * @Author Jayson
 * @Date 2020/9/15 15:40
 */
@Mapper(componentModel = "spring")
public interface SysRoleConverter extends BaseBeanConverter<SysRole, SysRoleVo> {
}
