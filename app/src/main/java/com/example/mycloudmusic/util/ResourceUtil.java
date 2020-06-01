package com.example.mycloudmusic.util;

/**
 * 资源工具类
 */
public class ResourceUtil {
    /**
     * 将相对资源转为绝对路径
     * @param uri
     * @return
     */
    public static String resourceUri(String uri){
        return String.format(Constant.RESOURCE_ENDPOINT, uri);
    }
}
