package utiles;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class WarPackageUtil {

    public static Class<?> getWarPackageClass(String warPath,String projectName ,String servletClass,String version,String suffix){
        //String path="E:/jboss-5.1.0.GA/server/default/deploy/backend.war/WEB-INF/classes/org/b3mn/poem/handler/";
        JarFile jar = null;
        Class<?> aClass = null;
        try {
            //String warPath = "D:\\userTool\\apache-tomcat-6.0.20sssssss\\webapps\\11111\\backend.war";
            String warAllPath = warPath+ File.separator + projectName + version + suffix;
            List<String > size=new ArrayList<String>();
            jar = new JarFile(warAllPath);
            Enumeration<JarEntry> entries = jar.entries();
            File file=new File(warAllPath);
            URL url=file.toURI().toURL();
            ClassLoader  loader=new URLClassLoader(new URL[]{url});
            aClass = loader.loadClass(servletClass);

        } catch (IOException e) {
            e.printStackTrace();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return aClass;
    }

    private static void process(Object obj,List<String > size ,ClassLoader loader) {

        JarEntry entry = (JarEntry)obj;
        String name = entry.getName();

        //格式化
        try {
            formatName( size, name, loader);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    private static void formatName(List<String> size, String name, ClassLoader loader) throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {

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
                size.add(names);

            }

        }
    }
}
