package com.sample.velocitydemo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Service;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class FileGenerator {
    private final AppConfig appConfig;

    public void generateFile() {
        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.init();
        Template template = velocityEngine.getTemplate(appConfig.getInputPath() + appConfig.getTemplateFile());
        createFiles(template);
    }

    private void createFiles(Template t) {
        List<AnnuityMasterData> annuityMasterDataList = getAnnuitiesMasterData();
        appConfig.getFiles().keySet().iterator();

        for (Map.Entry<String, Integer> entry : appConfig.getFiles().entrySet()) {
            String fileName = entry.getKey();
            int numberOfAnnuitiesRequired = entry.getValue();
            VelocityContext context = new VelocityContext();
            context.put("annuitees", getAnnuitees(fileName, annuityMasterDataList, numberOfAnnuitiesRequired));
            context.put("annuityDate", getDate());
            StringWriter writer = new StringWriter();
            t.merge( context, writer );
            writeToFile(appConfig.getOutputPath() + fileName + ".json", getPrettyPrintJson(writer.toString()));
        }
    }

    private List<Annuity> getAnnuitees(String fileName, List<AnnuityMasterData> annuityMasterDataList, int numberOfAnnuitiesRequired) {
        List<Annuity> annuities = new ArrayList<>();
        int start = (fileName.contains("artial")) ? 0 : 1;

        for (int i = start; (i < annuityMasterDataList.size() && numberOfAnnuitiesRequired > annuities.size()); i++ ) {
            final AnnuityMasterData annuityMasterData = annuityMasterDataList.get(i);
            Annuity annuity = new Annuity();
            if (i < 1) {
                annuity.setYearMonth(getYearMonth(0));
            } else {
                annuity.setYearMonth(getYearMonth(i));
            }
            annuity.setInsuredAmount(annuityMasterData.getVerzekerdBedrag());
            if (fileName.contains("BD") || fileName.contains("LA")) {
                annuity.setVariablePremium(annuityMasterData.getVariabelePremie());
            }
            if (fileName.contains("BD") || fileName.contains("ZR")) {
                annuity.setFixedPremium(annuityMasterData.getVastePremie());
            }
            annuities.add(annuity);
        }

        return annuities;
    }

    private String getYearMonth(int additionalMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        if (additionalMonth > 0) {
            calendar.add(Calendar.MONTH, additionalMonth);
        }
        int month = calendar.get(Calendar.MONTH) + 1; // We should add 1 as the array starts from zero
        String monthValue = (month < 10) ? ("0" + month) : (StringUtils.EMPTY + month);
        return StringUtils.EMPTY + calendar.get(Calendar.YEAR) + monthValue;
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

    private List<AnnuityMasterData> getAnnuitiesMasterData() {
        try {
            Gson gson = new Gson();
            Reader reader = Files.newBufferedReader(Paths.get(appConfig.getInputPath() + appConfig.getMasterDataFile()));
            List<AnnuityMasterData> annuities = new Gson().fromJson(reader, new TypeToken<List<AnnuityMasterData>>() {}.getType());
            reader.close();
            return annuities;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getPrettyPrintJson(String jsonString) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(jsonString);
        return gson.toJson(je);
//        return jsonString;
    }
}