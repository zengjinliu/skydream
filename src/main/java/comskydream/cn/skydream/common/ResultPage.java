package comskydream.cn.skydream.common;

/**
 * @author Jayson
 * @date 2020/9/8 10:36
 */
public class ResultPage<T> extends ResultJson<T> {

    private PageModel pageModel;

    public PageModel getPageModel() {
        return pageModel;
    }

    public void setPageModel(PageModel pageModel) {
        this.pageModel = pageModel;
    }
}
