package com.sample.velocitydemo;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CustomRunner implements CommandLineRunner {

    private final FileGenerator fileGenerator;

    @Override
    public void run(String... args) throws Exception {
        fileGenerator.generateFile();
    }
}
