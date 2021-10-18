package com.sample.velocitydemo;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Service;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FileGenerator {

    private static String inputPath = "src/main/resources/";
    private static String templateFile = "index.vm";

    public void generateFile() {
        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.init();

        Template t = velocityEngine.getTemplate(inputPath + templateFile);

        VelocityContext context = new VelocityContext();
        context.put("model", getModel());
        context.put("months", getMonths());
        context.put("employees", getEmployees());
        StringWriter writer = new StringWriter();
        t.merge( context, writer );

        System.out.println(writer.toString());
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
}
