package utiles;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.*;
public class JarDir {
    public static void main1 (String args[])
            throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {

        List<String > size=new ArrayList<String>();

        //JarFile jarFile = new JarFile("D:/projectList2017_10_24reconciliation_ba_pageconfig/reconciliation_ba/target/classes/deploy/1311-v1.0.0.jar");
        String des="G:/MiniCat/yingdian/webapps";
        String namess="dome1-1.0-SNAPSHOT.jar";
        @SuppressWarnings("resource")
        JarFile jarFile = new JarFile(des+ File.separator+namess);
        File file=new File(des+File.separator+namess);
        URL url=file.toURI().toURL();
        ClassLoader  loader=new URLClassLoader(new URL[]{url});

        //"C:/Users/zhangrui/Desktop/对账系统/131-v1.0.0.jar"
        Enumeration<?> files  = jarFile.entries();
        while (files .hasMoreElements()) {
            process(files .nextElement(),size,loader);
        }
        System.out.println(size.toString());
    }


    private static void process(Object obj,List<String > size ,ClassLoader loader) throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {

        JarEntry entry = (JarEntry)obj;
        String name = entry.getName();

        //格式化
        formatName( size, name, loader);
    }


    private static void formatName(List<String> size, String name,ClassLoader loader) throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {

        if(!name.endsWith(".MF")){
            if(name.endsWith(".class")){
                String d=name.replaceAll("/",".");
                int n=6;
                //第一个参数是开始截取的位置，第二个是结束位置。
                String names=d.substring(0,name.length()-n);
                /*动态加载指定jar包调用其中某个类的方法*/
                Class<?> cls=loader.loadClass(names);//加载指定类，注意一定要带上类的包名
                /*Class<?>  lifeCycle= com.qbao.pay.balance.core.deploy.LifeCycle.class;
                System.out.println(names+"="+lifeCycle.isAssignableFrom(cls));
                if(lifeCycle.isAssignableFrom(cls)){
                    size.add(names);
                }*/

            }

        }
    }

    public static void main(String[] args) throws Exception {
        URL u = new URL("file:/G:/MiniCat/yingdian/webapps/dome1-1.0-SNAPSHOT.jar");
        URLClassLoader loader = new URLClassLoader(new URL[] {u});
        Class c = loader.loadClass("com.lagou.servlet.Dome1Controller");

        /*File file = new File(jar文件全路径);
        URL url = file.toURL();
        URLClassLoader loader = new URLClassLoader(new URL[] { url });
        Class tidyClazz = loader.loadClass(所需class的含包名的全名);*/
    }
}