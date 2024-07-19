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
package com.platform.modules.gen.utils;

import com.platform.common.exception.BusinessException;
import com.platform.common.utils.DateUtils;
import com.platform.modules.gen.entity.ColumnEntity;
import com.platform.modules.gen.entity.ResultMapEntity;
import com.platform.modules.gen.entity.TableEntity;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.File;
import java.io.StringWriter;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成器   工具类
 *
 * @author lipengjun
 * @email 939961241@qq.com
 * @since 2017年1月3日 下午6:35:28
 */
public class GenUtils {

    private static List<String> getTemplates() {
        List<String> templates = new ArrayList<String>();
        templates.add("gen/template/Entity.java.vm");
        templates.add("gen/template/Dao.java.vm");
        templates.add("gen/template/Dao.xml.vm");
        templates.add("gen/template/Service.java.vm");
        templates.add("gen/template/ServiceImpl.java.vm");
        templates.add("gen/template/Controller.java.vm");
        templates.add("gen/template/menu.sql.vm");
        templates.add("gen/template/vue.vm");
        templates.add("gen/template/add-or-update.vue.vm");
        return templates;
    }

    /**
     * 生成代码
     */
    public static void generatorCode(ResultMapEntity table,
                                     List<ColumnEntity> columns, ZipOutputStream zip, String projectName, String packageName, String author, String tablePrefix) throws Exception {
        //配置信息
        Configuration config = getConfig();

        //表信息
        TableEntity tableEntity = new TableEntity();
        tableEntity.setTableName(table.getTableName());
        tableEntity.setComments(table.getTableComment());
        //表名转换成Java类名
        String className = tableToJava(tableEntity.getTableName());
        tableEntity.setClassName(className);
        tableEntity.setClassname(StringUtils.uncapitalize(className));

        //列信息
        List<ColumnEntity> columsList = new ArrayList<>();
        boolean hasDate = false;
        boolean hasBigDecimal = false;
        for (ColumnEntity column : columns) {
            ColumnEntity columnEntity = new ColumnEntity();
            columnEntity.setColumnName(column.getColumnName().toUpperCase());
            columnEntity.setDataType(column.getDataType());
            columnEntity.setComments(null == column.getComments() ? column.getColumnName() : column.getComments());

            //列名转换成Java属性名
            String attrName = columnToJava(columnEntity.getColumnName());
            columnEntity.setGetMethod("get" + attrName + "()");
            columnEntity.setAttrName(attrName);
            columnEntity.setAttrname(StringUtils.uncapitalize(attrName));

            //列的数据类型，转换成Java类型
            String attrType = config.getString(columnEntity.getDataType().split("\\(")[0], "unknowType");
            if ("number(22)".equalsIgnoreCase(columnEntity.getDataType())) {
                attrType = "Integer";
            }
            columnEntity.setAttrType(attrType);

            if ("Date".equals(attrType)) {
                hasDate = true;
            }
            if ("BigDecimal".equals(attrType)) {
                hasBigDecimal = true;
            }
            //是否主键
            if ((column.getColumnName().equalsIgnoreCase(column.getColumnKey()) && tableEntity.getPk() == null)) {
                tableEntity.setPk(columnEntity);
            }

            columsList.add(columnEntity);
        }
        tableEntity.setColumns(columsList);

        //若没主键
        if (tableEntity.getPk() == null) {
            boolean flag = true;
            //设置columnName为id的为主键
            for (ColumnEntity columnEntity : tableEntity.getColumns()) {
                if ("id".equals(columnEntity.getAttrname())) {
                    tableEntity.setPk(columnEntity);
                    flag = false;
                    break;
                }
            }
            //若无id字段则第一个字段为主键
            if (flag) {
                tableEntity.setPk(tableEntity.getColumns().get(0));
            }
        }
        codeFactory(tablePrefix, tableEntity, packageName, projectName, author, hasDate, hasBigDecimal, zip);
    }

    private static void codeFactory(String tablePrefix, TableEntity tableEntity, String packageName, String projectName, String author, boolean hasDate, boolean hasBigDecimal, ZipOutputStream zip) throws Exception {

        String pre = tablePrefix.replace("_", "").toLowerCase();
        //设置velocity资源加载器
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(prop);

        //封装模板数据
        /*
         * map中14个元素，与2的n次方最接近的数是16，但是这里如果设置容量为16的话 14/16=0.875,
         * 已经超过默认加载因子(0.75)的大小了。因此会resize一次，变成32。所以最好的结果还是32。
         */
        Map<String, Object> map = new HashMap<>(32);
        //取表前缀用于分包
        map.put("tableName", tableEntity.getTableName().toUpperCase());
        map.put("comments", null == tableEntity.getComments() ? "" : tableEntity.getComments());
        map.put("pk", tableEntity.getPk());
        map.put("className", tableEntity.getClassName());
        map.put("classname", tableEntity.getClassname());
        map.put("pathName", tableEntity.getClassname().replaceFirst(pre, "").toLowerCase());
        map.put("columns", tableEntity.getColumns());
        map.put("package", packageName + "." + pre);
        map.put("projectName", projectName);
        map.put("author", author);
        map.put("datetime", DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN));
        map.put("pre", pre);
        map.put("hasDate", hasDate);
        map.put("hasBigDecimal", hasBigDecimal);
        VelocityContext context = new VelocityContext(map);

        //获取模板列表
        List<String> templates = getTemplates();
        for (String template : templates) {
            //渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, "UTF-8");
            tpl.merge(context, sw);

            try {
                //添加到zip
                zip.putNextEntry(new ZipEntry(Objects.requireNonNull(getFileName(template, tableEntity.getClassName(), packageName, pre))));
                IOUtils.write(sw.toString(), zip, "UTF-8");
                IOUtils.closeQuietly(sw);
                zip.closeEntry();
            } catch (Exception e) {
                throw new BusinessException("渲染模板失败，表名：" + tableEntity.getTableName(), e);
            }
        }
    }

    /**
     * 列名转换成Java属性名
     */
    private static String columnToJava(String columnName) {
        return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "");
    }

    /**
     * 表名转换成Java类名
     */
    private static String tableToJava(String tableName) {
        return columnToJava(tableName);
    }

    /**
     * 获取配置信息
     */
    private static Configuration getConfig() {
        try {
            return new PropertiesConfiguration("gen/generator.properties");
        } catch (ConfigurationException e) {
            throw new BusinessException("获取配置文件失败，", e);
        }
    }

    /**
     * 获取文件名
     */
    private static String getFileName(String template, String className, String packageName, String tablePrefix) {

        String strEjv = "Entity.java.vm";
        String strDjv = "Dao.java.vm";
        String strDxv = "Dao.xml.vm";
        String strSjv = "Service.java.vm";
        String strSijv = "ServiceImpl.java.vm";
        String strCjv = "Controller.java.vm";
        String strAouVv = "add-or-update.vue.vm";
        String strVv = "vue.vm";
        String strMsv = "menu.sql.vm";

        String packagePath = "main" + File.separator + "java" + File.separator;
        if (StringUtils.isNotBlank(packageName)) {
            packagePath += packageName.replace(".", File.separator) + File.separator + tablePrefix + File.separator;
        }

        if (template.contains(strEjv)) {
            return packagePath + "entity" + File.separator + className + "Entity.java";
        }

        if (template.contains(strDjv)) {
            return packagePath + "dao" + File.separator + className + "Dao.java";
        }

        if (template.contains(strDxv)) {
            return "main" + File.separator + "resources" + File.separator + "mapper" + File.separator + tablePrefix + File.separator + className + "Dao.xml";
        }

        if (template.contains(strSjv)) {
            return packagePath + "service" + File.separator + className + "Service.java";
        }

        if (template.contains(strSijv)) {
            return packagePath + "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
        }

        if (template.contains(strCjv)) {
            return packagePath + "controller" + File.separator + className + "Controller.java";
        }

        if (template.contains(strAouVv)) {
            return "main" + File.separator + "modules" + File.separator + tablePrefix + File.separator + className.toLowerCase().replaceFirst(tablePrefix, "") + "-add-or-update.vue";
        }

        if (template.contains(strVv)) {
            return "main" + File.separator + "modules" + File.separator + tablePrefix + File.separator + className.toLowerCase().replaceFirst(tablePrefix, "") + ".vue";
        }

        if (template.contains(strMsv)) {
            return className + ".sql";
        }

        return null;
    }
}
