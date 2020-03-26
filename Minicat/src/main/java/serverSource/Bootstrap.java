package serverSource;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import pojo.*;
import utiles.WarPackageUtil;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Minicat的主类
 */
public class Bootstrap {

    /**定义socket监听的端口号*/
    private int port = 8080;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }


    /**
     * Minicat启动需要初始化展开的一些操作
     */
    public void start() throws Exception {
        //MiniCat4.0
        //解析MiniCat核心配置文件server.xml
        //根据包含的关系，依次加载容器：host->context->wrapper->servlet
        loadContainer();
        System.out.println(server);
        // 加载解析相关的配置，web.xml
        for (Service service : server.getServiceList()) {
            loadServlet(service);
        }
        //loadServlet();


        // 定义一个线程池
        int corePoolSize = 10;
        int maximumPoolSize =50;
        long keepAliveTime = 100L;
        TimeUnit unit = TimeUnit.SECONDS;
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(50);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();


        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                unit,
                workQueue,
                threadFactory,
                handler
        );





        /*
            完成Minicat 1.0版本
            需求：浏览器请求http://localhost:8080,返回一个固定的字符串到页面"Hello Minicat!"
         */
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("=====>>>Minicat start on port：" + port);

        /*while(true) {
            Socket socket = serverSocket.accept();
            // 有了socket，接收到请求，获取输出流
            OutputStream outputStream = socket.getOutputStream();
            String data = "Hello Minicat!";
            String responseText = HttpProtocolUtil.getHttpHeader200(data.getBytes().length) + data;
            outputStream.write(responseText.getBytes());
            socket.close();
        }*/


        /**
         * 完成Minicat 2.0版本
         * 需求：封装Request和Response对象，返回html静态资源文件
         */
        /*while(true) {
            Socket socket = serverSocket.accept();
            InputStream inputStream = socket.getInputStream();

            // 封装Request对象和Response对象
            Request request = new Request(inputStream);
            Response response = new Response(socket.getOutputStream());

            response.outputHtml(request.getUrl());
            socket.close();

        }*/


        /**
         * 完成Minicat 3.0版本
         * 需求：可以请求动态资源（Servlet）
         */
        /*while(true) {
            Socket socket = serverSocket.accept();
            InputStream inputStream = socket.getInputStream();

            // 封装Request对象和Response对象
            Request request = new Request(inputStream);
            Response response = new Response(socket.getOutputStream());

            // 静态资源处理
            if(servletMap.get(request.getUrl()) == null) {
                response.outputHtml(request.getUrl());
            }else{
                // 动态资源servlet请求
                HttpServlet httpServlet = servletMap.get(request.getUrl());
                httpServlet.service(request,response);
            }

            socket.close();

        }
*/

        /*
            多线程改造（不使用线程池）
         */
        /*while(true) {
            Socket socket = serverSocket.accept();
            RequestProcessor requestProcessor = new RequestProcessor(socket,servletMap);
            requestProcessor.start();
        }*/



        System.out.println("=========>>>>>>使用线程池进行多线程改造");
        /*
            多线程改造（使用线程池）
         */
        while(true) {

            Socket socket = serverSocket.accept();
            RequestProcessor requestProcessor = new RequestProcessor(socket,servletMap);
            //requestProcessor.start();
            threadPoolExecutor.execute(requestProcessor);
        }
    }
    private static Server server = new Server();

    /**
     * 解析
     */
    private void loadContainer() {
        InputStream resourceAsStream = Bootstrap.class.getClassLoader().getResourceAsStream("server.xml");
        SAXReader saxReader = new SAXReader();

        try {
            Document document = saxReader.read(resourceAsStream);
            Element rootElement = document.getRootElement();
            List<Element> serverNodes = rootElement.selectNodes("//server");
            for (Element serverNode : serverNodes) {
                Service service = new Service();
                Element serviceElement = (Element)serverNode.selectSingleNode("service");
                List<Element> connectorList = serviceElement.selectNodes("//connector");
                for (Element connectorElement : connectorList) {
                    Connector connector = new Connector();
                    List<Element> engineList = connectorElement.selectNodes("engine");
                    for (Element engineElement : engineList) {
                        Engine engine = new Engine();
                        List<Element> hostList = engineElement.selectNodes("host");
                        for (Element hostElement : hostList) {
                            Host host = new Host();
                            host.setName(hostElement.attributeValue("name"));
                            host.setAppBase(hostElement.attributeValue("appBase"));
                            List<Element> contextElementList = hostElement.selectNodes("context");
                            for (Element contextElement : contextElementList) {
                                Context context = new Context();
                                context.setWrapperName(contextElement.getTextTrim());
                                host.getContextList().add(context);
                            }
                            engine.getHostList().add(host);
                        }
                        connector.setEngine(engine);
                    }
                    connector.setProt(Integer.parseInt(connectorElement.attributeValue("prot")));
                    port = connector.getProt();
                    service.setConnector(connector);
                }
                server.getServiceList().add(service);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }

    private Map<String,HttpServlet> servletMap = new HashMap<String,HttpServlet>();

    /**
     * 加载解析web.xml，初始化Servlet
     */
    private void loadServlet(Service service) {
        Host host = service.getConnector().getEngine().getHostList().get(0);
        List<Context> contextList = host.getContextList();
        for (Context context : contextList) {
            String webPath = host.getAppBase() + "\\" + context.getWrapperName() + "\\src\\main\\webapp\\WEB-INF\\web.xml";
            InputStream resourceAsStream = null;
            try {
                resourceAsStream = new FileInputStream(new File(webPath));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            //InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("web.xml");
            SAXReader saxReader = new SAXReader();

            try {
                Document document = saxReader.read(resourceAsStream);
                Element rootElement = document.getRootElement();

                List<Element> selectNodes = rootElement.selectNodes("//servlet");
                for (int i = 0; i < selectNodes.size(); i++) {
                    Element element = selectNodes.get(i);
                    // <servlet-name>lagou</servlet-name>
                    Element servletnameElement = (Element) element.selectSingleNode("servlet-name");
                    String servletName = servletnameElement.getStringValue();
                    // <servlet-class>serverSource.LagouServlet</servlet-class>
                    Element servletclassElement = (Element) element.selectSingleNode("servlet-class");
                    String servletClass = servletclassElement.getStringValue();
                    // 根据servlet-name的值找到url-pattern
                    Element servletMapping = (Element) rootElement.selectSingleNode("/web-app/servlet-mapping[servlet-name='" + servletName + "']");
                    // /lagou
                    String urlPattern = servletMapping.selectSingleNode("url-pattern").getStringValue();
                    Class<?> servletContestClass = WarPackageUtil.getWarPackageClass(host.getAppBase(), context.getWrapperName(), servletClass, "-1.0-SNAPSHOT", ".jar");
                    servletMap.put(urlPattern, (HttpServlet) servletContestClass.newInstance());
                }
            } catch (DocumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Minicat 的程序启动入口
     * @param args
     */
    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();
        try {
            // 启动Minicat
            bootstrap.start();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
