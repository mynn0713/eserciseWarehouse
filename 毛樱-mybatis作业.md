**1、Mybatis动态sql是做什么的？都有哪些动态sql？简述一下动态sql的执行原理？**
Mybatis的mapper配置文件，在拼接sql的时候，会遇到一些判断，如空值的判断等，或者循环，这种情况下可以使用<if></if>、<foreach></foreach>。

Mybatis通过SqlSessionFactoryBuilder创建SqlSessionFactory的时候，会通过XMLMapperBuilder解析配置文件中的sql，封装到Configulation中的MappedStatement集合中，这个过程中会将配置文件中的节点解析成sqltest，解析的过程中会判断动态标签，从而进行sql的动态拼接。

 

**2、Mybatis是否支持延迟加载？如果支持，它的实现原理是什么？**

Mybatis通过lazyLoadingEnabled和aggressiveLazyLoading配置标签控制延迟加载和按需加载，关联查询时，关联实体分别创建实体对象，mapper配置文件中创建关联查询映射配置，通过association标签和collection来实现一对一或者一对多的关联查询。当使用延时加载的时候，查询实体的时候，不会查询被关联的实体表，而是在使用到被关联的实体对象的时候，才去查询被关联的实体表。

 

**3、Mybatis都有哪些Executor执行器？它们之间的区别是什么？**

Mybatis有三种基本的Executor执行器: SimpleExecutor、ReuseExecutor、BatchExecutor。

SimpleExecutor：每执行一次update或select，就开启一个Statement对象，用完立刻关闭Statement对象。

ReuseExecutor：执行update或select，以sql作为key查找Statement对象，存在就使用，不存在就创建，用完后，不关闭Statement对象，而是放置于Map内，供下一次使用。简言之，就是重复使用Statement对象。

BatchExecutor：执行update（没有select，JDBC批处理不支持select），将所有sql都添加到批处理中（addBatch()），等待统一执行（executeBatch()），它缓存了多个Statement对象，每个Statement对象都是addBatch()完毕后，等待逐一执行executeBatch()批处理。与JDBC批处理相同。

**4、简述下Mybatis的一级、二级缓存（分别从存储结构、范围、失效场景。三个方面来作答）？**

一级缓存的存储结构是以map的结构存入内存中，他的范围是作用于sqlSession中，当sqlSession失效时，则清空一级缓存，以及当sqlSession做增删改操作时，提交事务后，一级缓存便会失效。

二级缓存也是以map的结构进行存储，二级缓存是默认关闭的，二级缓存的作用范围是mapper中，当查询同一namespaces.id的数据时，会从二级缓存中读取数据，当做响应增删改操作的时候，会清空二级缓存。

**5、简述Mybatis的插件运行原理，以及如何编写一个插件？**

mybatis可以编写针对Executor、StatementHandler、ParameterHandler、ResultSetHandler四个接口的插件，mybatis使用JDK的动态代理为需要拦截的接口生成代理对象，然后实现接口的拦截方法，所以当执行需要拦截的接口方法时，会进入拦截方法。

如何编写一个插件，写一个实现类，实现Intercepror接口，类上面加上@Intercepts注解，注解可以配置多个@Signature注解，拦截多个对象及方法，将想要拦截的对象及方法配置到注解中，编写实现方法。最后将编写好的方法配置到配置文件的配置标签中。