package com.huahansoft.hhsoftlibrarykit.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;

/**
 * @类说明 HHSoftSharedPreferencesUtils-保存信息配置类
 * @作者 hhsoft
 * @创建日期 2019/8/20 9:05
 * 注意事项
 * 一、完整路径为/data/data/项目包/shared_prefs/名称.xml
 */
public class HHSoftSharedPreferencesUtils {
    private static final String AES_KEY = "1862c0zle369e73a";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public HHSoftSharedPreferencesUtils(Context context, String name) {
        sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    /**
     * 获取SharedPreferences对象
     *
     * @param context
     * @param name    存储类名称
     * @return
     */
    public static SharedPreferences sharedPreferences(Context context, String name) {
        SharedPreferences preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        return preferences;
    }


    /**
     * 把java类序列化以流的方式存储进去。
     * 实体类一定要实现Serializable序列化接口
     *
     * @param key
     * @param t
     * @param <T>
     */
    public <T> void saveBean(String key, T t) {
        ByteArrayOutputStream bos;
        ObjectOutputStream oos = null;
        try {
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(t);
            byte[] bytes = bos.toByteArray();
            String objStr = HHSoftStreamUtils.toHexString(bytes);
            editor.putString(key, HHSoftEncryptUtils.encryptAESWithKey(objStr, AES_KEY));
            editor.commit();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (oos != null) {
                try {
                    oos.flush();
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取java类对象的方法
     * 实体类一定要实现Serializable序列化接口
     *
     * @param key
     * @param <T>
     * @return
     */
    public <T extends Object> T getBean(String key) {
        String content = HHSoftEncryptUtils.decryptAESWithKey(sharedPreferences.getString(key, ""), AES_KEY);
        byte[] bytes = HHSoftStreamUtils.toByteArray(content);
        ByteArrayInputStream bis;
        ObjectInputStream ois = null;
        T obj = null;
        try {
            bis = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bis);
            obj = (T) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return obj;
    }

    /**
     * 保存java类对象的方法(Parcelable序列化)
     *
     * @param key
     * @param object
     */
    public void saveBeanWithParcelable(String key, Parcelable object) {
        Parcel parcel = Parcel.obtain();
        object.writeToParcel(parcel, 0);
        byte[] bytes = parcel.marshall();
        parcel.recycle();
        String objStr = HHSoftStreamUtils.toHexString(bytes);
        editor.putString(key, HHSoftEncryptUtils.encryptAESWithKey(objStr, AES_KEY));
        editor.commit();
    }

    /**
     * 获取java类对象的方法(Parcelable序列化)
     *
     * @param key
     * @param creator
     * @param <T>
     * @return
     */
    public <T> T getBeanWithParcelable(String key, Parcelable.Creator<T> creator) {
        String content = HHSoftEncryptUtils.decryptAESWithKey(sharedPreferences.getString(key, ""), AES_KEY);
        byte[] bytes = HHSoftStreamUtils.toByteArray(content);
        Parcel parcel = Parcel.obtain();
        parcel.unmarshall(bytes, 0, bytes.length);
        parcel.setDataPosition(0);
        return creator.createFromParcel(parcel);
    }

    /**
     * 存储
     *
     * @param key   保存的字段的名字
     * @param value 保存的字段的值
     */
    public void put(String key, String value) {
        if (!TextUtils.isEmpty(value)) {
            editor.putString(key, HHSoftEncryptUtils.encryptAESWithKey(value, AES_KEY));
            editor.commit();
        }
    }

    /**
     * 获取保存的数据
     *
     * @param key          保存的字段的名字
     * @param defaultValue 默认值
     * @return
     */
    public String getSharedPreference(String key, String defaultValue) {
        String value = sharedPreferences.getString(key, defaultValue);
        if (!TextUtils.isEmpty(value)) {
            value = HHSoftEncryptUtils.decryptAESWithKey(value, AES_KEY);
        }
        return value;
    }


    /**
     * 保存java类对象（反射）
     * @param model
     * @param <T>
     */
    public <T> void saveBean(T model) {
        if (model != null) {
            try {
                Class<T> clazz = (Class<T>) model.getClass();
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    Object object = field.get(model);
                    String value = "";
                    if (object != null) {
                        value = object.toString();
                    }
                    editor.putString(field.getName(), HHSoftEncryptUtils.encryptAESWithKey(value,AES_KEY));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            editor.commit();
        }
    }
    public <T> T getBean(T model){
        if (model!=null){
            try {
                Class<T> clazz = (Class<T>) model.getClass();
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return model;
    }



    /**
     * 存储
     *
     * @param key    保存的字段的名字
     * @param object 保存的字段的值
     */
    public void put(String key, Object object) {
        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }
        editor.commit();
    }


    /**
     * 获取保存的数据
     *
     * @param key           保存的字段的名字
     * @param defaultObject 默认值
     * @return
     */
    public Object getSharedPreference(String key, Object defaultObject) {
        if (defaultObject instanceof String) {
            return sharedPreferences.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sharedPreferences.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sharedPreferences.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sharedPreferences.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sharedPreferences.getLong(key, (Long) defaultObject);
        } else {
            return sharedPreferences.getString(key, "");
        }
    }
    /**
     * 移除某个key值已经对应的值
     *
     * @param key
     */
    public void remove(String key) {
        editor.remove(key);
        editor.commit();
    }

    /**
     * 清除所有数据
     */
    public void clear() {
        editor.clear();
        editor.commit();
    }
}
