package com.example.realmsample.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.blankj.utilcode.util.ToastUtils;
import com.example.realmsample.bean.Cat;
import com.example.realmsample.bean.Dog;
import com.example.realmsample.bean.User;
import com.example.realmsample.utils.Migration;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmChangeListener;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * 测试sample
 */
public class TestActivity extends AppCompatActivity {

    Realm mRealm;
    private RealmAsyncTask addTask;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRealm = Realm.getDefaultInstance();

        // TODO 增
        // （1）实现方法一：事务操作
        // 类型一 ：新建一个对象，并进行存储
//        Realm realm = Realm.getDefaultInstance();
//        realm.beginTransaction();
//        User user = realm.createObject(User.class); // Create a new object
//        user.setName("John");
//        user.setEmail("john@corporation.com");
//        realm.commitTransaction();

        // 类型二：复制一个对象到Realm数据库
//        Realm realm = Realm.getDefaultInstance();
//        User user = new User("John");
//        user.setEmail("john@corporation.com");
//        // Copy the object to Realm. Any further changes must happen on realmUser
//        realm.beginTransaction();
//        realm.copyToRealm(user);
//        realm.commitTransaction();

        //（2）实现方法二：使用事务块
//        Realm mRealm = Realm.getDefaultInstance();
//        final User user = new User("John");
//        user.setEmail("john@corporation.com");
//        mRealm.executeTransactionAsync(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                realm.copyToRealm(user);
//            }
//        }, new Realm.Transaction.OnSuccess() {
//            @Override
//            public void onSuccess() {
//
//            }
//        }, new Realm.Transaction.OnError() {
//            @Override
//            public void onError(Throwable error) {
//
//            }
//        });

        // 删
        Realm mRealm = Realm.getDefaultInstance();
        final RealmResults<Dog> dogs = mRealm.where(Dog.class).findAll();
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Dog dog = dogs.get(5);
                dog.deleteFromRealm();
                //删除第一个数据
                dogs.deleteFirstFromRealm();
                //删除最后一个数据
                dogs.deleteLastFromRealm();
                //删除位置为1的数据
                dogs.deleteFromRealm(1);
                //删除所有数据
                dogs.deleteAllFromRealm();
            }
        });
        // 同样也可以使用同上的beginTransaction和commitTransaction方法进行删除

        // 改
//        Realm mRealm = Realm.getDefaultInstance();
//        Dog dog = mRealm.where(Dog.class).equalTo("id", id).findFirst();
//        mRealm.beginTransaction();
//        dog.setName(newName);
//        mRealm.commitTransaction();
        // 同样也可以用事物块来更新数据

    }


    /**
     * 查
     * 常见的条件如下（详细资料请查官方文档）：
     * <p>
     * between(), greaterThan(), lessThan(), greaterThanOrEqualTo() & lessThanOrEqualTo()
     * equalTo() & notEqualTo()
     * contains(), beginsWith() & endsWith()
     * isNull() & isNotNull()
     * isEmpty() & isNotEmpty()
     *
     * @return
     */
    //（1）查询全部
    // 查询结果为RealmResults<T>，可以使用mRealm.copyFromRealm(dogs)方法将它转为List<T>
    public List<Dog> queryAllDog() {
        Realm mRealm = Realm.getDefaultInstance();
        RealmResults<Dog> dogs = mRealm.where(Dog.class).findAll();
        return mRealm.copyFromRealm(dogs);
    }

    // （2）条件查询
    public Dog queryDogById(String id) {
        Realm mRealm = Realm.getDefaultInstance();
        Dog dog = mRealm.where(Dog.class).equalTo("id", id).findFirst();
        return dog;
    }

    //（3）对查询结果进行排序

    /**
     * query （查询所有）
     */
    public List<Dog> queryAllDogSort() {
        RealmResults<Dog> dogs = mRealm.where(Dog.class).findAll();
        /**
         * 对查询结果，按Id进行排序，只能对查询结果进行排序
         */
        //增序排列
        dogs = dogs.sort("id");
        //降序排列
        dogs = dogs.sort("id", Sort.DESCENDING);
        return mRealm.copyFromRealm(dogs);
    }

    //（4）其他查询
    // sum，min，max，average只支持整型数据字段

    /**
     * 查询平均年龄
     */
    private void getAverageAge() {
        double avgAge = mRealm.where(Dog.class).findAll().average("age");
    }

    /**
     * 查询总年龄
     */
    private void getSumAge() {
        Number sum = mRealm.where(Dog.class).findAll().sum("age");
        int sumAge = sum.intValue();
    }

    /**
     * 查询最大年龄
     */
    private void getMaxId() {
        Number max = mRealm.where(Dog.class).findAll().max("age");
        int maxAge = max.intValue();
    }

    // 九、异步操作
    // 大多数情况下，Realm的增删改查操作足够快，可以在UI线程中执行操作。但是如果遇到较复杂的增删改查，或增删改查操作的数据较多时，就可以子线程进行操作。
    // （1）异步增：
    private void addCat(final Cat cat) {
        addTask = mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(cat);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                ToastUtils.showShort("收藏成功");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                ToastUtils.showShort("收藏失败");
            }
        });

    }


    // （2）异步删
    private void deleteCat(final String id, final ImageView imageView) {
        RealmAsyncTask deleteTask = mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Cat cat = realm.where(Cat.class).equalTo("id", id).findFirst();
                cat.deleteFromRealm();
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                ToastUtils.showShort("取消收藏");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                ToastUtils.showShort("取消收藏失败");
            }
        });
    }

    //（3）异步改
    private void updateCat(final String mId, final String name) {
        RealmAsyncTask updateTask = mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Cat cat = realm.where(Cat.class).equalTo("id", mId).findFirst();
                cat.setName(name);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                ToastUtils.showShort("更新成功");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                ToastUtils.showShort("更新失败");
            }
        });
    }

    //（4）异步查
    private void queryAsync() {
        RealmResults<Cat> cats = mRealm.where(Cat.class).findAllAsync();
        cats.addChangeListener(new RealmChangeListener<RealmResults<Cat>>() {
            @Override
            public void onChange(RealmResults<Cat> element) {
                element = element.sort("id");
                List<Cat> datas = mRealm.copyFromRealm(element);
            }
        });
    }


    // 最后在销毁Activity或Fragment时，要取消掉异步任务
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (addTask != null && !addTask.isCancelled()) {
            addTask.cancel();
        }
    }

    // 十、数据迁移（版本升级）
    //方法一、删除旧版本的数据
//    RealmConfiguration config = new RealmConfiguration.Builder()
//            .deleteRealmIfMigrationNeeded()
//            .build();
//    // 方法二、设置schema 版本和 migration，对改变的数据进行处理
//    RealmConfiguration config = new RealmConfiguration.Builder()
//            .schemaVersion(2) // Must be bumped when the schema changes
//            .migration(new Migration()) // Migration to run instead of throwing an exception
//            .build();


}
