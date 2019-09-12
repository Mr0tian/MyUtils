package com.example.daotest.util;

/**
 * @author tian on 2019/9/2
 * 限制短时间内连续点击的方法
 */
public class ContinuousClickUtils {
    /**
     * 两次点击间隔1000毫秒
     */
    public static int SHORT_TIME = 1000;

    /**
     * 两次点击间隔5000毫秒
     */
    public static int LONG_TIME = 5000;

    static long lastTime ;
    /**
     * @param time 两次点击的间隔,(毫秒)
     * 返回true时为可以点击
     */
    public static boolean twoClick(int time){

        long curTime = java.lang.System.currentTimeMillis();
        if ((curTime - lastTime) > time ){
            lastTime = curTime;
            return true;
        }
        return false;
    }

}
