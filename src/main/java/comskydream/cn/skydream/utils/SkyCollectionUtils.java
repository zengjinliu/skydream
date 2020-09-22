package comskydream.cn.skydream.utils;

import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Jayson
 * @date 2020/9/22 17:42
 */
public class SkyCollectionUtils extends CollectionUtils {

    /**
     * 集合拆分工具类
     * @param origin 原始集合
     * @param size 每个集合存放元素的个数
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> split(final List<T> origin,final int size){
        if (isEmpty(origin)) {
            return Collections.emptyList();
        }
        int block = (origin.size() + size - 1) / size;
        return IntStream.range(0, block).
                boxed().map(i -> {
            int start = i * size;
            int end = Math.min(start + size, origin.size());
            return origin.subList(start, end);
        }).collect(Collectors.toList());
    }



}
