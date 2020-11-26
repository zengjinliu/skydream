package comskydream.cn.skydream.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

/**
 * @author Jayson
 * @date 2020/11/20 17:17
 */
@Slf4j
public class DecodeParametersWrapper extends HttpServletRequestWrapper {

    /**
     * 所有参数的Map集合
     */
    private Map<String, String[]> parameterMap;
    /**
     * aes Key
     */
    private String aseKey;


    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request
     * @param aseKey  对称密钥
     * @throws IllegalArgumentException if the request is null
     */
    public DecodeParametersWrapper(HttpServletRequest request, String aseKey) {
        super(request);
        this.aseKey = aseKey;
        //TODO vue axios 前端是采用原生ajax提交的content-type:application/json,顾再请求中获取参数</br>
        // 获取不到,所以建议前端采用form表单的提交方式，后端采用@RequestParam注解而不是@RequestBody注解
        parameterMap = request.getParameterMap();
        decryptData();
    }

    /**
     * 解密参数
     */
    private void decryptData() {
        Set<Map.Entry<String, String[]>> entries = parameterMap.entrySet();
        for (Map.Entry<String, String[]> entry : entries) {
            String[] results = entry.getValue();
            int length = results.length;
            for (int i = 0; i < length; i++) {
                String value = results[i];
                if (!StringUtils.isEmpty(value)) {
                    results[i] = decryptValue(value);
                }
            }
        }

    }

    /**
     * 包装getParameterNames方法
     *
     * @return 返回ParameterNames
     */
    @Override
    public Enumeration<String> getParameterNames() {
        Vector<String> vector = new Vector<>(parameterMap.keySet());
        return vector.elements();
    }

    /**
     * 包装getParameter方法
     *
     * @param name
     * @return
     */
    @Override
    public String getParameter(String name) {
        String[] results = this.parameterMap.get(name);
        if (results == null || results.length <= 0) {
            return null;
        } else {
            return results[0];
        }


    }

    /**
     * 获取指定参数名的所有值的数组，如：checkbox的所有数据
     * 接收数组变量 ，如checkobx类型
     */
    @Override
    public String[] getParameterValues(String name) {
        String[] results = parameterMap.get(name);
        return results;
    }


    /**
     * 解密参数值
     *
     * @param string 原参数的值
     * @return 解密后的参数的值
     * @auther minte
     * @date 2019/3/24 10:58
     */

    private String decryptValue(String string) {
        try {
            //获取aes解密密钥
            String result = AESECBUtil.decrypt(string, aseKey);
            log.debug(" from parameter value: " + string + "; change parameter value:" + result);
            return result;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            //参数解密失败
            throw new RuntimeException("parameter value  decryption failure!");
        }

    }
}
