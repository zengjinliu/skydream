package comskydream.cn.skydream.common;

import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @Description
 * @Author Jayson
 * @Date 2020/9/8 10:37
 */
public interface BaseBeanConverter<P,V> {

    /**
     * po -->vo
     *
     * @param pojo 对象
     * @return
     */
    V toVo(P pojo);

    /**
     * po -->vo
     *
     * @param pojo 对象
     * @return
     */
    List<V> toVo(List<P> pojo);

    /**
     * vo ---> po
     *
     * @param vo
     * @return
     */
    P toPo(V vo);

    /**
     * vo ---> po
     *
     * @param vo
     * @return
     */
    List<P> toPo(List<V> vo);

    /**
     * 分页转换
     *
     * @param pageInfo
     * @return
     */
    default ResultPage<List<V>> toPageVo(PageInfo<P> pageInfo) {
        ResultPage<List<V>> resultPage = new ResultPage<>();
        PageModel pageMode = new PageModel();
        pageMode.setTotal(pageInfo.getTotal());
        pageMode.setRows(pageInfo.getPageSize());
        pageMode.setPage(pageInfo.getPageNum());
        pageMode.setPageCount(pageInfo.getPages());
        pageMode.setSize(pageInfo.getSize());
        resultPage.setPageModel(pageMode);
        List<P> poList = pageInfo.getList();
        List<V> voList = toVo(poList);
        resultPage.setDatas(voList);
        return resultPage;
    }


}
