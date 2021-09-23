package com.sergeysitnikov.infotecs_academy;


import com.sergeysitnikov.infotecs_academy.Model.MyModel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;


@Slf4j
@RestController("/")
@AllArgsConstructor
public class MyController {
    private MyService myService;

    @GetMapping("/{key}")
    public String get(@PathVariable int key) {
        return myService.getValue(key);
    }

    @PostMapping("/{key}")
    public String set(@PathVariable int key, @RequestBody MyModel model) {
        return myService.put(key, model);
    }

    @DeleteMapping("/{key}")
    public String remove(@PathVariable int key) {
        return myService.remove(key);
    }

    @GetMapping("/save")
    public File dump() throws IOException {
        return myService.saveMap();
    }

    @GetMapping("/load")
    public void load() {
        myService.load();
    }

}
