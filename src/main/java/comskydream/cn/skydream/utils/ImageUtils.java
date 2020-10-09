package comskydream.cn.skydream.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * 图片转换base64编码工具类
 *
 * @author Jayson
 * @date 2020/10/9 8:53
 */
public final class ImageUtils {

    private static final BASE64Encoder ENCODER = new BASE64Encoder();
    private static final BASE64Decoder DECODER = new BASE64Decoder();

    /**
     * 将图片转成Base64编码
     *
     * @param imgPath 图片路径
     * @return base64编码
     * @throws Exception 异常信息
     */
    public static String imageToBase64(String imgPath) throws Exception {
        File file = new File(imgPath);
        if (!file.exists()) {
            throw new RuntimeException("the image path is not correct!");
        }
        BufferedImage read = ImageIO.read(file);
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        ImageIO.write(read, "jpg", bao);
        byte[] bytes = bao.toByteArray();
        return ENCODER.encodeBuffer(bytes).trim();
    }

    /**
     * 将图片base64编码转换成图片
     * @param base64Code 图片base64编码
     * @throws Exception 异常信息
     */
    public static void base64ToImage(String base64Code) throws Exception {
        byte[] bytes = DECODER.decodeBuffer(base64Code);
        ByteArrayInputStream bai = new ByteArrayInputStream(bytes);
        BufferedImage read = ImageIO.read(bai);
        File f1 = new File("d://jay.jpg");
        ImageIO.write(read, "jpg", f1);
    }
}
