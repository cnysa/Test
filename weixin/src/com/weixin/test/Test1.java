package com.weixin.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.weixin.gacl.domain.Admin;
import com.weixin.gacl.servece.AdminService;

public class Test1 {

	private AdminService umi;
	
	 @Before
	 public void before(){
      //使用"spring.xml"和"spring-mybatis.xml"这两个配置文件创建Spring上下文
		 @SuppressWarnings("resource")
		ApplicationContext ac = new FileSystemXmlApplicationContext(new String[]{"WebContent/WEB-INF/conf/spring/spring-main.xml",
				 "WebContent/WEB-INF/conf/spring/weixin-service-beans.xml"});
     //从Spring容器中根据bean的id取出我们要使用的userService对象
     umi = (AdminService) ac.getBean("userService");
	 }

	 
//	@Test
//	public void run() throws IOException {
//        //mybatis的配置文件
//        String resource = "conf.xml";
//        //使用类加载器加载mybatis的配置文件（它也加载关联的映射文件）
//        InputStream is = Test1.class.getClassLoader().getResourceAsStream(resource);
//        //构建sqlSession的工厂
//        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(is);
//        //使用MyBatis提供的Resources类加载mybatis的配置文件（它也加载关联的映射文件）
//        //Reader reader = Resources.getResourceAsReader(resource); 
//        //构建sqlSession的工厂
//        //SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
//        //创建能执行映射文件中sql的sqlSession
//        SqlSession session = sessionFactory.openSession();
//        /**
//         * 映射sql的标识字符串，
//         * me.gacl.mapping.userMapper是userMapper.xml文件中mapper标签的namespace属性的值，
//         * getUser是select标签的id属性值，通过select标签的id属性值就可以找到要执行的SQL
//         */
//        String statement = "com.weixin.gacl.mapping.userMapper.getUsername";//映射sql的标识字符串
//        //执行查询返回一个唯一user对象的sql
//        Admin user = session.selectOne(statement, "admin");
//        System.out.println(user);
//    }
	
	@Test
    public void testAdd(){
//        SqlSession sqlSession = MyBatisUtil.getSqlSession(true);
//        //得到UserMapperI接口的实现类对象，UserMapperI接口的实现类对象由sqlSession.getMapper(UserMapperI.class)动态构建出来
//        UserMapperI mapper = sqlSession.getMapper(UserMapperI.class);
        Admin Admin = new Admin();
        Admin.setUsername("admin222");
        Admin.setPassword("aaabbb");
        umi.addUser(Admin);
//        int add = mapper.add(Admin);
        //使用SqlSession执行完SQL之后需要关闭SqlSession
//        sqlSession.close();
//        System.out.println(add);
    }
    
//    @Test
//    public void testUpdate(){
//        SqlSession sqlSession = MyBatisUtil.getSqlSession(true);
//        //得到UserMapperI接口的实现类对象，UserMapperI接口的实现类对象由sqlSession.getMapper(UserMapperI.class)动态构建出来
//        UserMapperI mapper = sqlSession.getMapper(UserMapperI.class);
//        Admin user = new Admin();
//        user.setUsername("admin111");;
//        user.setPassword("111111");
//        //执行修改操作
//        int retResult = mapper.update(user);
//        //使用SqlSession执行完SQL之后需要关闭SqlSession
//        sqlSession.close();
//        System.out.println(retResult);
//    }
    
//    @Test
//    public void testDelete(){
//        SqlSession sqlSession = MyBatisUtil.getSqlSession(true);
//        //得到UserMapperI接口的实现类对象，UserMapperI接口的实现类对象由sqlSession.getMapper(UserMapperI.class)动态构建出来
//        UserMapperI mapper = sqlSession.getMapper(UserMapperI.class);
//        //执行删除操作
//        int retResult = mapper.deleteById(7);
//        //使用SqlSession执行完SQL之后需要关闭SqlSession
//        sqlSession.close();
//        System.out.println(retResult);
//    }
    
//    @Test
//    public void testGetUser(){
//        SqlSession sqlSession = MyBatisUtil.getSqlSession();
//        //得到UserMapperI接口的实现类对象，UserMapperI接口的实现类对象由sqlSession.getMapper(UserMapperI.class)动态构建出来
//        UserMapperI mapper = sqlSession.getMapper(UserMapperI.class);
//        //执行查询操作，将查询结果自动封装成User返回
//        Admin user = mapper.getById(8);
//        //使用SqlSession执行完SQL之后需要关闭SqlSession
//        sqlSession.close();
//        System.out.println(user);
//    }
    
//    @Test
//    public void testGetAll(){
//        SqlSession sqlSession = MyBatisUtil.getSqlSession();
//        //得到UserMapperI接口的实现类对象，UserMapperI接口的实现类对象由sqlSession.getMapper(UserMapperI.class)动态构建出来
//        UserMapperI mapper = sqlSession.getMapper(UserMapperI.class);
//        //执行查询操作，将查询结果自动封装成List<User>返回
//        List<Admin> lstUsers = mapper.getAll();
//        //使用SqlSession执行完SQL之后需要关闭SqlSession
//        sqlSession.close();
//        System.out.println(lstUsers);
//    }
}
