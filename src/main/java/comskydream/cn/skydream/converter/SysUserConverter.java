package comskydream.cn.skydream.converter;

import comskydream.cn.skydream.common.BaseBeanConverter;
import comskydream.cn.skydream.entity.SysUser;
import comskydream.cn.skydream.model.vo.SysUserVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * @Description
 * @Author Jayson
 * @Date 2020/9/14 14:41
 */
@Mapper(componentModel = "spring")
public interface SysUserConverter extends BaseBeanConverter<SysUser, SysUserVo> {


    @Mappings({
            @Mapping(source = "createTime",target = "createTime",dateFormat = "yyyy-MM-dd")
    })
    @Override
    SysUserVo toVo(SysUser pojo);
}
