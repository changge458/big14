package com.oldboy.umeng.util;

/**
 * 将音乐类型映射为数据库表名称
 */
public class MusicTableUtil {

    public static String parseTable(int type) {

        switch (type) {
            case 1:
                return "music_mix";     //流行歌曲
            case 2:
                return "music_folk";    //民谣
            case 3:
                return "music_custom";  //古风
            case 4:
                return "music_old";     //老歌
            case 5:
                return "music_rock1";   //欧美摇滚
            case 6:
                return "music_rock2";   //国与摇滚
            case 7:
                return "music_comic";   //二次元
            case 8:
                return "music_yueyu";   //粤语
            case 9:
                return "music_light";   //轻音乐
            default:
                try {
                    throw new Exception("参数必须为1-9");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
        return null;
    }
}