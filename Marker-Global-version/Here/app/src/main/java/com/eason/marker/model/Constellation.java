package com.eason.marker.model;

import android.content.Context;

import com.eason.marker.R;

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
    public static String getConstellation(Context context,int month, int day) {

        if (month == 1) {
            if (day <= 20) {
                return context.getResources().getString(R.string.constellation_capricorn);
            } else {
                return context.getResources().getString(R.string.constellation_aquarius);
            }
        } else if (month == 2) {
            if (day <= 19) {
                return context.getResources().getString(R.string.constellation_aquarius);
            } else {
                return context.getResources().getString(R.string.constellation_pisces);
            }
        } else if (month == 3) {
            if (day <= 21) {
                return context.getResources().getString(R.string.constellation_pisces);
            } else {
                return context.getResources().getString(R.string.constellation_aries);
            }
        } else if (month == 4) {
            if (day <= 20) {
                return context.getResources().getString(R.string.constellation_aries);
            } else {
                return context.getResources().getString(R.string.constellation_taurus);
            }
        } else if (month == 5) {
            if (day <= 21) {
                return context.getResources().getString(R.string.constellation_taurus);
            } else {
                return context.getResources().getString(R.string.constellation_gemini);
            }
        } else if (month == 6) {
            if (day <= 21) {
                return context.getResources().getString(R.string.constellation_gemini);
            } else {
                return context.getResources().getString(R.string.constellation_cancer);
            }
        } else if (month == 7) {
            if (day <= 22) {
                return context.getResources().getString(R.string.constellation_cancer);
            } else {
                return context.getResources().getString(R.string.constellation_leo);
            }
        } else if (month == 8) {
            if (day <= 23) {
                return context.getResources().getString(R.string.constellation_leo);
            } else {
                return context.getResources().getString(R.string.constellation_virgo);
            }
        } else if (month == 9) {
            if (day <= 23) {
                return context.getResources().getString(R.string.constellation_virgo);
            } else {
                return context.getResources().getString(R.string.constellation_balance);
            }
        } else if (month == 10) {
            if (day <= 23) {
                return context.getResources().getString(R.string.constellation_balance);
            } else {
                return context.getResources().getString(R.string.constellation_scorpio);
            }
        }else if (month == 11) {
            if (day <= 22) {
                return context.getResources().getString(R.string.constellation_scorpio);
            } else {
                return context.getResources().getString(R.string.constellation_sagittarius);
            }
        }else if (month == 12) {
            if (day <= 21) {
                return context.getResources().getString(R.string.constellation_sagittarius);
            } else {
                return context.getResources().getString(R.string.constellation_capricorn);
            }
        }

        return "";
    }
}
