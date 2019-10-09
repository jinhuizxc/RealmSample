package com.example.realmsample.utils;

import android.content.Context;

import com.example.realmsample.bean.Dog;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import io.realm.Sort;

public class RealmHelper {

    public static final String DB_NAME = "myRealm.realm";
    private Realm mRealm;

    // 获取实例
    public RealmHelper(Context context) {
        mRealm = Realm.getDefaultInstance();
    }

    /**
     * add （增）
     */
    public void addDog(Dog dog) {
        mRealm.beginTransaction();
        mRealm.copyToRealm(dog);
        mRealm.commitTransaction();
    }

    /**
     * 更新
     * @param dog
     */
    public void updateDog(Dog dog) {
        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(dog);
        mRealm.commitTransaction();
    }

    /**
     * delete （删）
     */
    public void deleteDog(String id) {
        Dog dog = mRealm.where(Dog.class).equalTo("id", id).findFirst();
        mRealm.beginTransaction();
        dog.deleteFromRealm();
        mRealm.commitTransaction();
    }

    /**
     * update （改）
     */
    public void updateDog(String id, String newName) {
        Dog dog = mRealm.where(Dog.class).equalTo("id", id).findFirst();
        mRealm.beginTransaction();
        dog.setName(newName);
        mRealm.commitTransaction();
    }

    /**
     * query （查询所有）
     */
    public List<Dog> queryAllDog() {
        RealmResults<Dog> dogs = mRealm.where(Dog.class).findAll();
        /**
         * 对查询结果，按Id进行排序，只能对查询结果进行排序
         */
        //增序排列
        dogs = dogs.sort("id");
//        //降序排列
//        dogs = dogs.sort("id", Sort.DESCENDING);
        return mRealm.copyFromRealm(dogs);  // 将RealmResults转换为List
    }

    /**
     * query （根据Id（主键）查）
     */
    public Dog queryDogById(String id) {
        Dog dog = mRealm.where(Dog.class).equalTo("id", id).findFirst();
        return dog;
    }

    /**
     * query （根据age查）
     */
    public List<Dog> queryDogByAge(int age) {
        RealmResults<Dog> dogs = mRealm.where(Dog.class).equalTo("age", age).findAll();
        return mRealm.copyFromRealm(dogs);
    }

    /**
     * 判断是否存在(通过id查)
     *
     * @param id
     * @return
     */
    public boolean isDogExist(String id) {
        Dog dog = mRealm.where(Dog.class).equalTo("id", id).findFirst();
        if (dog == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 获取realm对象
     *
     * @return
     */
    public Realm getRealm() {
        return mRealm;
    }

    /**
     * 关闭realm
     */
    public void close() {
        if (mRealm != null) {
            mRealm.close();
        }
    }

}
