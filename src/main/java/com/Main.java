package com;

import com.task.manager.QuartzManager;
import com.task.manager.SpringQtzDemo2;
import com.test.bean.Sku;
import com.test.dao.SkuMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;

public class Main {

    public static void main(String[] args) throws IOException {


//        initMybatisDemo();
//        initJDBC();
//        initHibernate();
//        String jobName, String jobGroupName,
//                String triggerName, String triggerGroupName, Class jobClass, String cron
        QuartzManager.addJob("jobName_lxh","jobWork_lxh","triggerName_lxh","triggerGroupName_lxh", SpringQtzDemo2.class,"0/10 * * * * ?");
    }

    public static void initJDBC() {
        String dbUrl1 = "jdbc:mysql://localhost:3306/lxhtest?characterEncoding=utf8&useSSL=false";
        //用户名
        String dbUserName = "root";
        //密码
        String dbPassword = "123123";
        //驱动名称
        String jdbcName = "com.mysql.jdbc.Driver";
        String sql = "SELECT * FROM  sku WHERE id= 110020748;";
        String sql_insert = "INSERT INTO sku (spu_id, small_unit_code, unit_transfer, sku_no) VALUE (?, ?, ?, ?);";
        Connection con = null;
        try {
            Class.forName(jdbcName);
            //获取数据库连接
            con = DriverManager.getConnection(dbUrl1, dbUserName, dbPassword);
            //查询语句
            PreparedStatement pstmt = (PreparedStatement) con.prepareStatement(sql);
            ResultSet resultSet = pstmt.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            //插入语句
            PreparedStatement pstmt_insert = con.prepareStatement(sql_insert);
            pstmt_insert.setInt(1, 122333);
            pstmt_insert.setString(2, "333334433");
            pstmt_insert.setInt(3, 10);
            pstmt_insert.setString(4, "3444w334");
            int insertCount = pstmt_insert.executeUpdate();

            System.out.println("列的数量columnCount--->" + columnCount + "--insertCount---->" + insertCount);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }

    public static void initMybatisDemo() {
        // mybatis
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = sqlSessionFactory.openSession();
        SkuMapper skuMapper = session.getMapper(SkuMapper.class);
        Sku sku = skuMapper.selectByPrimaryKey((long) 110020748);
        System.out.println(sku);
    }

    /**
     * 通过Configuration().configure();读取并解析hibernate.cfg.xml配置文件。
     * 由hibernate.cfg.xml中的<mapping resource="com/xx/Xxx.hbm.xml"/>读取解析映射信息。
     * 通过config.buildSessionFactory();得到sessionFactory。
     * sessionFactory.openSession();得到session。
     * session.beginTransaction();开启事务。
     * persistent operate; 执行你自己的操作。
     * session.getTransaction().commit();提交事务。
     * 关闭session。
     * 关闭sessionFactory。
     */
    private static void initHibernate() {
        System.out.println("Maven + Hibernate + MySQL");
        Session session = getSessionFactory().openSession();

        session.beginTransaction();
        Sku sku = new Sku();
        sku.setSpu_id((long) 90930393);
        sku.setSku_no("1111111");
        sku.setUnit_transfer(10);
        session.save(sku);
        session.getTransaction().commit();
    }


    //hibernate
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            return new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        // Close caches and connection pools
        getSessionFactory().close();
    }


}
