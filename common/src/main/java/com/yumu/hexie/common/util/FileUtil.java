package com.yumu.hexie.common.util;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);

    public static boolean createFile(String filePath) {
        try {
            File f = new File(filePath);
            if (!f.getParentFile().exists() && !createDir(f.getParent())) {
                LOGGER.error("文件创建失败{0}", filePath);
                return false;
            } else {
                return f.createNewFile();
            }
        } catch (Exception e) {
            LOGGER.error("文件创建失败{0}", filePath, e);
            return false;
        }
    }

//    public static boolean writeFileForce(String filePath, String templatePath,  Map<String, Object> properties) {
//
//        try {
//            if (!createFileWithCover(filePath)) {
//                return false;
//            }
//            Properties p = new Properties();
//            String tempBasePath =  FileUtil.class.getResource("/template").getPath();
//            p.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH,tempBasePath);
//
//            p.setProperty(Velocity.INPUT_ENCODING,"utf-8");
//            p.setProperty(Velocity.OUTPUT_ENCODING,"utf-8");
//
//            p.setProperty("directive.foreach.counter.name","loopCounter");
//            p.setProperty("directive.foreach.counter.initial.value","0");
//            
//            p.setProperty("default.contentType", "text/html;charset=utf-8");
//            
//            VelocityEngine ve = new VelocityEngine();
//            ve.init(p);
//            VelocityContext context = new VelocityContext(properties);
//
//            context.put("dateTool", new DateTool());
//            //路径跟前面设置的path相对应
//            Template template = ve.getTemplate(templatePath);
//            //将获得的对象放入Velocity的环境上下文中方便取用
//            PrintWriter pw = new PrintWriter( new OutputStreamWriter(
//                    new FileOutputStream(filePath),"utf-8"));
//            template.setEncoding("utf-8");
//            template.merge(context, pw);
//            pw.close();
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }

    public static long getFileModifyTime(String path) {
        File f = new File(path);
        if (f == null || !f.exists()) {
            return 0;
        }
        return f.lastModified();
    }

    public static boolean createFileWithCover(String filePath) {
        try {
            File f = new File(filePath);
            if (f.exists()) {
                f.delete();
            }
            return createFile(filePath);
        } catch (Exception e) {
            LOGGER.error("文件创建失败{0}", filePath, e);
            return false;
        }

    }

    public static boolean createDir(String dir) {
        File f = new File(dir);
        if (f.exists()) {
            if (f.isDirectory()) {
                return true;
            } else {
                return false;
            }
        }
        File p = f.getParentFile();
        if (!p.exists() && !createDir(p.getPath())) {
            return false;
        }
        return f.mkdir();
    }
}
