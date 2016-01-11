package com.eason.marker.model;

/**
 * 有关于星座的工具类
 * Created by Eason on 9/17/15.
 */
public class Constellation {

    public static final String BaiyangZuo = "白羊座";

    public static final String JinNiuZuo = "金牛座";

    public static final String ShuangZiZuo = "双子座";

    public static final String JuXieZuo = "巨蟹座";

    public static final String ShiZiZuo = "狮子座";

    public static final String ChuNvZuo = "处女座";

    public static final String TianPinZuo = "天平座";

    public static final String TianXieZuo = "天蝎座";

    public static final String SheShouZuo = "射手座";

    public static final String MoJieZuo = "摩羯座";

    public static final String ShuiPinZuo = "水瓶座";

    public static final String ShuangYuZuo = "双鱼座";

    /**
     * 根据月份和日期获取相应的星座名称
     * @param month
     * @param day
     * @return
     */
    public static String getConstellation(int month, int day) {

        if (month == 1) {
            if (day <= 20) {
                return MoJieZuo;
            } else {
                return ShuiPinZuo;
            }
        } else if (month == 2) {
            if (day <= 19) {
                return ShuiPinZuo;
            } else {
                return ShuangYuZuo;
            }
        } else if (month == 3) {
            if (day <= 21) {
                return ShuangYuZuo;
            } else {
                return BaiyangZuo;
            }
        } else if (month == 4) {
            if (day <= 20) {
                return BaiyangZuo;
            } else {
                return JinNiuZuo;
            }
        } else if (month == 5) {
            if (day <= 21) {
                return JinNiuZuo;
            } else {
                return ShuangZiZuo;
            }
        } else if (month == 6) {
            if (day <= 21) {
                return ShuangZiZuo;
            } else {
                return JuXieZuo;
            }
        } else if (month == 7) {
            if (day <= 22) {
                return JuXieZuo;
            } else {
                return ShiZiZuo;
            }
        } else if (month == 8) {
            if (day <= 23) {
                return ShiZiZuo;
            } else {
                return ChuNvZuo;
            }
        } else if (month == 9) {
            if (day <= 23) {
                return ChuNvZuo;
            } else {
                return TianPinZuo;
            }
        } else if (month == 10) {
            if (day <= 23) {
                return TianPinZuo;
            } else {
                return TianXieZuo;
            }
        }else if (month == 11) {
            if (day <= 22) {
                return TianXieZuo;
            } else {
                return SheShouZuo;
            }
        }else if (month == 12) {
            if (day <= 21) {
                return SheShouZuo;
            } else {
                return MoJieZuo;
            }
        }

        return "";
    }
}
