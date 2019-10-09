package com.example.realmsample;

import android.app.Application;
import android.util.Log;

import com.example.realmsample.utils.LogUtil;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 *  https://realm.io/docs/java/latest/
 *
 *          // 从线程获取realm的实例
 * //        Realm realm = Realm.getDefaultInstance();
 *
 *
 * 基本使用说明:
 * 其他相关说明
 *
 * 1、支持的数据类型：
 * boolean, byte, short, int, long, float, double, String, Date and byte[]
 * 在Realm中byte, short, int, long最终都被映射成long类型
 *
 * 2、注解说明
 *
 * @PrimaryKey
 * ①字段必须是String、 integer、byte、short、 int、long 以及它们的封装类Byte, Short, Integer, and Long
 *
 * ②使用了该注解之后可以使用copyToRealmOrUpdate()方法，通过主键查询它的对象，如果查询到了，则更新它，否则新建一个对象来代替。
 *
 * ③使用了该注解将默认设置@index注解
 *
 * ④使用了该注解之后，创建和更新数据将会慢一点，查询数据会快一点。
 *
 * @Required
 * 数据不能为null
 *
 * @Ignore
 * 忽略，即该字段不被存储到本地
 *
 * @Index
 * 为这个字段添加一个搜索引擎，这将使插入数据变慢、数据增大，但是查询会变快。建议在需要优化读取性能的情况下使用。
 *
 * 数据库操作:
 *
 * 增
 * （1）实现方法一：事务操作
 * 类型一 ：新建一个对象，并进行存储
 * Realm realm=Realm.getDefaultInstance();
 * realm.beginTransaction();
 * User user = realm.createObject(User.class); // Create a new object
 * user.setName("John");
 * user.setEmail("john@corporation.com");
 * realm.commitTransaction();
 *
 *
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //初始化realm
        Realm.init(this);

        // ①使用默认配置
//        RealmConfiguration config = new RealmConfiguration.Builder().build();
//        Realm.setDefaultConfiguration(config);
        // ②使用自定义配置
//        RealmConfiguration config = new  RealmConfiguration.Builder()
//                .name("myRealm.realm")
//                .deleteRealmIfMigrationNeeded()
//                .build();
//        Realm.setDefaultConfiguration(config);

        //配置realm方式一：
        //这时候会创建一个叫做default.realm的Realm文件，一般来说，
        // 这个文件位于/data/data/包名/files/。通过realm.getPath()来获得该Realm的绝对路径
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("myrealm.realm") //文件名
                .schemaVersion(0) //版本号
                .build();
        Realm realm = Realm.getInstance(config);
        String path = realm.getPath();
        LogUtil.e("获取数据库的绝对路径: " + path);


        //配置方式二：创建保存在内存中的realm，应用关闭后就清除了
//        RealmConfiguration myConfig = new RealmConfiguration.Builder(this)
//                .name("myrealm.realm")
//                .inMemory() .build();

        //配置方式三：创建加密的realm
//        byte[] key = new byte[64];
//        new SecureRandom().nextBytes(key);
//        RealmConfiguration config = new RealmConfiguration.Builder()
//                .encryptionKey(key)
//                .build();
//        Realm realm = Realm.getInstance(config);


    }
}
