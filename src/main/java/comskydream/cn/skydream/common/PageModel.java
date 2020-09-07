package comskydream.cn.skydream.common;


import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Jayson
 * @date 2020/9/7 13:41
 */
@Data
@Accessors(chain = true)
public class PageModel {
    /**
     * 升序
     */
    private static final String ASC = "ASC";

    /**
     * 降序
     */
    private static final String DESC = "DESC";

    /**
     * 当前页
     */
    private Integer page;
    /**
     * 每页的数量
     */
    private Integer rows;
    /**
     * 当前页的数量
     */
    private Integer size;
    /**
     * 总记录数据
     */
    private Integer total;

    /**
     * 总页数
     */
    private Integer pageCount;

    /**
     * 排序
     */
    private String orderBy;

    public PageModel() {
        if(this.page == null || this.rows == null){
            this.page = 1;
            this.rows = 20;
        }
    }
}
