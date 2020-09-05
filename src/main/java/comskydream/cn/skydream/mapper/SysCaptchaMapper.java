package comskydream.cn.skydream.mapper;


import comskydream.cn.skydream.entity.SysCaptcha;

public interface SysCaptchaMapper {
    /**
     * 通过主键删除
     * @param uuid 主键id
     * @return 删除成功数量
     */
    int deleteByPrimaryKey(String uuid);

    /**
     * 新增数据插入全部列
     * @param record 新增记录参数
     * @return 新增成功数量
     */
    int insert(SysCaptcha record);


    /**
     * 通过主键查询
     * @param uuid 主键id
     * @return 主键符合的唯一Po记录
     */
    SysCaptcha selectByPrimaryKey(String uuid);

    /**
     * 通过主键修改有数据的列
     * @param record 修改记录参数
     * @return 修改成功数量
     */
    int updateByPrimaryKeySelective(SysCaptcha record);

}