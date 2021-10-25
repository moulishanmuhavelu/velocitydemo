package com.sample.velocitydemo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
@ConfigurationProperties(prefix="config")
@Data
public class AppConfig {

    private Map<String, Integer> files;
    private String inputPath;
    private String templateFile;
    private String outputPath;
    private String masterDataFile;

}
