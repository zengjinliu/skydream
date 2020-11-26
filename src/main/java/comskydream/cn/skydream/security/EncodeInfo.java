package comskydream.cn.skydream.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 加密信息 ，包装返回的VO数据
 *
 * @author Jayson
 * @date 2020/11/20 17:17
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class EncodeInfo {
    /**
     * 是否加密
     */
    private boolean encodeType;

    /**
     * AES密钥
     */
    private String aesKey;

    private Object vo;

    public boolean getEncodeType() {
        return encodeType;
    }

    public void setEncodeType(boolean encodeType) {
        this.encodeType = encodeType;
    }
}
