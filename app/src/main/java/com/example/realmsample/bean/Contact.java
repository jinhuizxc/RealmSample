package com.example.realmsample.bean;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * 多对多的关系：
 */
public class Contact extends RealmObject {

    public String name;
    public RealmList<Email> emails;


    public class Email extends RealmObject {
        public String address;
        public boolean active;
    }
}

