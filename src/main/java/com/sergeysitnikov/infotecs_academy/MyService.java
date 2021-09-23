package com.sergeysitnikov.infotecs_academy;

import com.sergeysitnikov.infotecs_academy.Model.MyModel;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
@AllArgsConstructor
public class MyService {
    private final MyRepository myRepository;

    public String getValue(int key) {
        String Value = myRepository.getByKey(key);
        if (Value == null) {
            return "Значение по ключу key= " + key + " пустое";
        } else {
            return Value;
        }
    }


    public String put(int key, MyModel model) {
        return myRepository.put(key, model);
    }

    public String remove(int key) {
        String Value = myRepository.getByKey(key);
        if (Value == null) {
            return "Значение по ключу key= " + key + " пустое";
        } else {
            myRepository.remove(key);
            return "Значение по ключу: " + key + "были удалены";
        }
    }

    public File saveMap() throws IOException {
        return myRepository.saveMap();
    }

    public void load() {
        myRepository.load();
    }
}
