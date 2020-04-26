package com.sxs.taobaounion.utils;

/**
 * @Author: a797s
 * @Date: 2020/4/20 14:01
 * @Desc:
 */
public class UrlUtils {

    public static String createHomePagerUrl(int materialId, int page){
        return "discovery/" + materialId + "/" + page;
    }

    public static String getImagePath(String pict_url) {
        return "https:" + pict_url;
    }

    public static String getImagePath(String pict_url, int size) {
        return "https:" + pict_url + "_" + size + "x" +size + ".jpg";
    }
}
