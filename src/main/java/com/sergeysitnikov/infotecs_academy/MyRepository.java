package com.sergeysitnikov.infotecs_academy;

import com.sergeysitnikov.infotecs_academy.Model.MyModel;
import org.springframework.stereotype.Repository;


import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Repository
public class MyRepository {
    private HashMap<Integer, String> storage = new HashMap<>(); // Нужный нам Hashmap содержит значение типа String по ключу
    private HashMap<Integer, Long> timeTtl = new HashMap<>(); // Содержит время жизни по ключу
    private HashMap<Integer, Long> timeStart = new HashMap<>(); //Содержит время создание по ключу

    private String fileName = "storage.txt";//имя файла в котором хранится нужное нам храшилище
    private String fileTtl = "ttlTime.txt";//имя файла в котором храниться время жизни по ключу
    private String fileStartTime = "startTime.txt";//имя файла в котором храниться время жизни по ключу
    private final long deufalttTtl = 500;
    private File file;

    public String getByKey(int key) {
        checkTime();
        return storage.get(key);
    }

    public String put(int key, MyModel model) {

        checkTime();
        String value = model.getValue();
        Long ttl = model.getTtl();
        if (ttl == 0) {
            ttl = deufalttTtl;
        }
        storage.put(key, value);
        timeTtl.put(key, ttl);
        long start = System.nanoTime();
        timeStart.put(key, start);
        if (storage.containsKey(key) && storage.containsValue(value)) {
            return "Ключ: " + key + " и значение: " + value + " успешно добавлены на " + ttl + " секунд";
        } else {
            return "Возникла ошибка при добавлении";
        }
    }

    public File saveMap() throws IOException {
        checkTime();
        file = new File(fileName);
        try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(file))) {
            os.writeObject(storage);
        }
        try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fileStartTime))) {
            os.writeObject(timeStart);
        }
        try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fileTtl))) {
            os.writeObject(timeTtl);
        }
        return file;
    }

    public String remove(int key) {
        checkTime();
        String Value = storage.get(key);
        storage.remove(key);
        timeTtl.remove(key);
        timeStart.remove(key);
        return Value;
    }

    public void load() {
        checkTime();
        try (ObjectInputStream is = new ObjectInputStream(new FileInputStream(fileName))) {
            storage = (HashMap<Integer, String>) is.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (ObjectInputStream is = new ObjectInputStream(new FileInputStream(fileStartTime))) {
            timeStart = (HashMap<Integer, Long>) is.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (ObjectInputStream is = new ObjectInputStream(new FileInputStream(fileTtl))) {
            timeTtl = (HashMap<Integer, Long>) is.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //Проверяем время жизни всех объектов нашего хранилища
    public void checkTime() {
        for (Map.Entry<Integer, Long> pair : timeTtl.entrySet()) {
            Long currentTime = System.nanoTime();
            if ((currentTime - timeStart.get(pair.getKey())) / 1000000000 > pair.getValue()) {
                storage.remove(pair.getKey());
                timeTtl.remove(pair.getKey());
                timeStart.remove(pair.getKey());
            }
        }
    }
}
