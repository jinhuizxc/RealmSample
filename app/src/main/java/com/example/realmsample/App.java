package com.example.realmsample;

import android.app.Application;
import android.util.Log;

import com.example.realmsample.utils.LogUtil;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 *  https://realm.io/docs/java/latest/
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //初始化realm
        Realm.init(this);
        // 从线程获取realm的实例
//        Realm realm = Realm.getDefaultInstance();

        //配置realm方式一：
        //这时候会创建一个叫做default.realm的Realm文件，一般来说，
        // 这个文件位于/data/data/包名/files/。通过realm.getPath()来获得该Realm的绝对路径
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("myrealm.realm") //文件名
                .schemaVersion(0) //版本号
                .build();
        Realm.setDefaultConfiguration(config);
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
