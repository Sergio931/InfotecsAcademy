package com.sergeysitnikov.infotecs_academy;

import com.sergeysitnikov.infotecs_academy.Model.MyModel;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class MyServiceTest {
    private MyRepository myRepository = new MyRepository();
    private MyService myService = new MyService(myRepository);

    @Test
    void getValue() {
        myService.load();
        final String value = myService.getValue(1);
        assertEquals(value, "Первое значение");
    }

    @Test
    void put() {
        MyModel model = new MyModel("Первое значение", 500);
        myService.put(1, model);
        final String value = myService.getValue(1);
        assertEquals(value, "Первое значение");
    }

    @Test
    void remove() {
        MyModel model = new MyModel("Первое значение", 500);
        myService.put(1, model);
        myService.remove(1);
        final String value = myService.getValue(1);
        assertEquals(value, null);
    }

    @Test
    void saveMap() {
        MyModel model = new MyModel("Второе значение", 500);
        myService.put(2, model);
        try {
            myService.saveMap();
        } catch (IOException e) {
            e.printStackTrace();
        }
        MyRepository myRepository1 = new MyRepository();
        MyService myService1 = new MyService(myRepository1);
        myService1.load();
        final String value = myService1.getValue(2);
        assertEquals(value, "Второе значение");

    }

    @Test
    void load() {
        MyModel model = new MyModel("Первое значение", 500);
        myService.put(1, model);
        try {
            myService.saveMap();
        } catch (IOException e) {
            e.printStackTrace();
        }
        myService.load();
        final String value = myService.getValue(1);
        assertEquals(value, "Первое значение");

    }
}