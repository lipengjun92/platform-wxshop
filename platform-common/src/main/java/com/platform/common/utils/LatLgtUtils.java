/*
 *
 *      Copyright (c) 2018-2099, lipengjun All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the fly2you.cn developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: lipengjun (939961241@qq.com)
 *
 */
package com.platform.common.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 计算周边商家工具类
 *
 * @author 李鹏军
 */
public class LatLgtUtils {

    private static final double EARTH_RADIUS = 6378137;

    /**
     * 获取四个顶点的list
     *
     * @param lgt      经度
     * @param lat      纬度
     * @param distance 距离
     * @return List
     */
    public static List<Double[]> getPointsList(double lgt, double lat, double distance) {

        List<Double[]> pointsList = new ArrayList<>(4);

        double dlng = 2 * Math.asin(Math.sin(distance / (2 * EARTH_RADIUS)) / Math.cos(rad(lat)));
        dlng = degrees(dlng);

        double dlat = distance / EARTH_RADIUS;
        dlat = degrees(dlat);

        // key:纬度  value:经度
        Double[] points = new Double[]{lat + dlat, lgt - dlng};
        // 左上角的顶点
        pointsList.add(points);

        // 右上角的顶点
        points = new Double[]{lat + dlat, lgt + dlng};
        pointsList.add(points);

        // 右下角的顶点
        points = new Double[]{lat - dlat, lgt + dlng};
        pointsList.add(points);

        // 左下角的顶点
        points = new Double[]{lat - dlat, lgt - dlng};
        pointsList.add(points);

        return pointsList;

    }

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    private static double degrees(double d) {
        return d * (180 / Math.PI);
    }

    /**
     * 传入经纬度计算距离，单位为km,保留2位小数
     *
     * @param lng1 lng1
     * @param lat1 lat1
     * @param lng2 lng2
     * @param lat2 lat2
     * @return String
     */
    public static String calDistance(float lng1, float lat1, float lng2, float lat2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS / 1000;
        return String.format("%.2f", s);
    }
}
