package com.sample.velocitydemo;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Service;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FileGenerator {

    private static String inputPath = "src/main/resources/";
    private static String templateFile = "annuity-json.vm";
    private static String outputFileName = "output.json";

    public void generateFile() {
        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.init();

        Template t = velocityEngine.getTemplate(inputPath + templateFile);

        VelocityContext context = new VelocityContext();
//        context.put("model", getModel());
//        context.put("months", getMonths());
//        context.put("employees", getEmployees());
        context.put("annuitees", getAnnuitees());
        context.put("annuityDate", getDate());
        StringWriter writer = new StringWriter();
        t.merge( context, writer );
        writeToFile(inputPath + outputFileName, writer.toString());
    }

    private Map<String, Object> getModel() {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("time", new Date());
        model.put("message", "Hello Steve");
        return model;
    }

    private List<String> getMonths() {
        return List.of("January","February","March","April");
    }

    private List<Employee> getEmployees() {

        List<Employee> employees = new ArrayList<>();
        Employee employee = new Employee("Steve", "Robert street, 54716");
        employees.add(employee);
        employee = new Employee("Jacob", "Houter street, 36151");
        employees.add(employee);

        return employees;
    }

    private List<Annuity> getAnnuitees() {
        return List.of(
                new Annuity("202109", 32.56, 25.67, 54000.12),
                new Annuity("202110", 33.67, 36.77, 38000.28),
                new Annuity("202111", 34.78, 47.88, 87000.59),
                new Annuity("202112", 17.78, 123.88, 6678700.59),
                new Annuity("202201", 43.78, 567.88, 123700.59),
                new Annuity("202202", 237.78, 341.88, 45500.59)
        );
    }

    private void writeToFile(String outputFilePath, String content) {
        try(FileWriter fileWriter = new FileWriter(outputFilePath)) {
            fileWriter.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        return sdf.format(new Date());
    }
}