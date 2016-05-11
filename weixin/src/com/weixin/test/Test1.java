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
      //ʹ��"spring.xml"��"spring-mybatis.xml"�����������ļ�����Spring������
		 @SuppressWarnings("resource")
		ApplicationContext ac = new FileSystemXmlApplicationContext(new String[]{"WebContent/WEB-INF/conf/spring/spring-main.xml",
				 "WebContent/WEB-INF/conf/spring/weixin-service-beans.xml"});
     //��Spring�����и���bean��idȡ������Ҫʹ�õ�userService����
     umi = (AdminService) ac.getBean("userService");
	 }

	 
//	@Test
//	public void run() throws IOException {
//        //mybatis�������ļ�
//        String resource = "conf.xml";
//        //ʹ�������������mybatis�������ļ�����Ҳ���ع�����ӳ���ļ���
//        InputStream is = Test1.class.getClassLoader().getResourceAsStream(resource);
//        //����sqlSession�Ĺ���
//        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(is);
//        //ʹ��MyBatis�ṩ��Resources�����mybatis�������ļ�����Ҳ���ع�����ӳ���ļ���
//        //Reader reader = Resources.getResourceAsReader(resource); 
//        //����sqlSession�Ĺ���
//        //SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
//        //������ִ��ӳ���ļ���sql��sqlSession
//        SqlSession session = sessionFactory.openSession();
//        /**
//         * ӳ��sql�ı�ʶ�ַ�����
//         * me.gacl.mapping.userMapper��userMapper.xml�ļ���mapper��ǩ��namespace���Ե�ֵ��
//         * getUser��select��ǩ��id����ֵ��ͨ��select��ǩ��id����ֵ�Ϳ����ҵ�Ҫִ�е�SQL
//         */
//        String statement = "com.weixin.gacl.mapping.userMapper.getUsername";//ӳ��sql�ı�ʶ�ַ���
//        //ִ�в�ѯ����һ��Ψһuser�����sql
//        Admin user = session.selectOne(statement, "admin");
//        System.out.println(user);
//    }
	
	@Test
    public void testAdd(){
//        SqlSession sqlSession = MyBatisUtil.getSqlSession(true);
//        //�õ�UserMapperI�ӿڵ�ʵ�������UserMapperI�ӿڵ�ʵ���������sqlSession.getMapper(UserMapperI.class)��̬��������
//        UserMapperI mapper = sqlSession.getMapper(UserMapperI.class);
        Admin Admin = new Admin();
        Admin.setUsername("admin222");
        Admin.setPassword("aaabbb");
        umi.addUser(Admin);
//        int add = mapper.add(Admin);
        //ʹ��SqlSessionִ����SQL֮����Ҫ�ر�SqlSession
//        sqlSession.close();
//        System.out.println(add);
    }
    
//    @Test
//    public void testUpdate(){
//        SqlSession sqlSession = MyBatisUtil.getSqlSession(true);
//        //�õ�UserMapperI�ӿڵ�ʵ�������UserMapperI�ӿڵ�ʵ���������sqlSession.getMapper(UserMapperI.class)��̬��������
//        UserMapperI mapper = sqlSession.getMapper(UserMapperI.class);
//        Admin user = new Admin();
//        user.setUsername("admin111");;
//        user.setPassword("111111");
//        //ִ���޸Ĳ���
//        int retResult = mapper.update(user);
//        //ʹ��SqlSessionִ����SQL֮����Ҫ�ر�SqlSession
//        sqlSession.close();
//        System.out.println(retResult);
//    }
    
//    @Test
//    public void testDelete(){
//        SqlSession sqlSession = MyBatisUtil.getSqlSession(true);
//        //�õ�UserMapperI�ӿڵ�ʵ�������UserMapperI�ӿڵ�ʵ���������sqlSession.getMapper(UserMapperI.class)��̬��������
//        UserMapperI mapper = sqlSession.getMapper(UserMapperI.class);
//        //ִ��ɾ������
//        int retResult = mapper.deleteById(7);
//        //ʹ��SqlSessionִ����SQL֮����Ҫ�ر�SqlSession
//        sqlSession.close();
//        System.out.println(retResult);
//    }
    
//    @Test
//    public void testGetUser(){
//        SqlSession sqlSession = MyBatisUtil.getSqlSession();
//        //�õ�UserMapperI�ӿڵ�ʵ�������UserMapperI�ӿڵ�ʵ���������sqlSession.getMapper(UserMapperI.class)��̬��������
//        UserMapperI mapper = sqlSession.getMapper(UserMapperI.class);
//        //ִ�в�ѯ����������ѯ����Զ���װ��User����
//        Admin user = mapper.getById(8);
//        //ʹ��SqlSessionִ����SQL֮����Ҫ�ر�SqlSession
//        sqlSession.close();
//        System.out.println(user);
//    }
    
//    @Test
//    public void testGetAll(){
//        SqlSession sqlSession = MyBatisUtil.getSqlSession();
//        //�õ�UserMapperI�ӿڵ�ʵ�������UserMapperI�ӿڵ�ʵ���������sqlSession.getMapper(UserMapperI.class)��̬��������
//        UserMapperI mapper = sqlSession.getMapper(UserMapperI.class);
//        //ִ�в�ѯ����������ѯ����Զ���װ��List<User>����
//        List<Admin> lstUsers = mapper.getAll();
//        //ʹ��SqlSessionִ����SQL֮����Ҫ�ر�SqlSession
//        sqlSession.close();
//        System.out.println(lstUsers);
//    }
}
