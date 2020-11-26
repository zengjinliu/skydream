package comskydream.cn.skydream.security;

import comskydream.cn.skydream.constant.ActionConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 参数加密解密属性
 * @author Jayson
 * @date 2020/11/20 14:27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ParamEncodeProperty {

    /**
     * 私钥 ，加密方式rsa
     */
    private String rsaPrivateKey;


    /**
     * 是否加密
     * <br>{@link  comskydream.cn.skydream.constant.ActionConstant#RESULT_JSON_ENCRYPTION} 加密
     * <br>  {@link  comskydream.cn.skydream.constant.ActionConstant#RESULT_JSON_NOT_ENCRYPTION} 不加密
     */
    private boolean encodeType;

    /**
     * 需要解密的请求体参数并且响应体不需要加密的URI
     */

    private String privilegeURI;

    /**
     * 解析好的不需要解密的请求体参数并且响应体不需要加密的URI
     */
    private String[] privilegeURIs;


    public void setPrivilegeURI(String privilegeURI) {
        this.privilegeURI = privilegeURI;
        splitURI();
    }

    public boolean getEncodeType() {
        return encodeType;
    }

    public void setEncodeType(boolean encodeType) {
        this.encodeType = encodeType;
    }

    /**
     * 拆分不需要解密参数的URI
     */
    private void splitURI() {

        if (this.privilegeURI != null) {
            privilegeURIs = privilegeURI.split(",");
        }
    }

    /**
     * 判定该URL 是否需要解密参数
     *
     * @param requestURI
     * @return 返回true 该URL需要解密参数，返回false 不需要解密参数
     */
    public boolean uriIsDecrypt(String requestURI) {
        if (this.privilegeURIs != null && this.privilegeURIs.length > 0) {
            for (String uri : privilegeURIs) {
                int index = requestURI.indexOf(uri);
                if (index != -1) {
                    return false;
                }
            }
        }

        return true;
    }


}
