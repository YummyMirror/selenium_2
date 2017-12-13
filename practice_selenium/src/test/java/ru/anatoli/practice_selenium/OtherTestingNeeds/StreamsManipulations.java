package ru.anatoli.practice_selenium.OtherTestingNeeds;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.log4j.Logger;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class StreamsManipulations {
    private static final Logger LOGGER = Logger.getLogger(StreamsManipulations.class);

    public static void main(String[] args) throws IOException {
        List<String> stringList = new ArrayList<String>(0);
        stringList.add("11111");
        stringList.add("11111");
        stringList.add("22222");
        stringList.add("33333");
        stringList.add("44444");
        stringList.add("55555");
        stringList.add("66666");
        stringList.add("77777");
        stringList.add("88888");
        stringList.add("99999");
        stringList.add("101010");
        stringList.forEach(LOGGER::info);

        long count = stringList.stream().filter(l -> l.equals("77777")).count();
        List<String> collect = stringList.stream().distinct().collect(Collectors.toList());
        List<Integer> collect1 = collect.stream().map(Integer::parseInt).collect(Collectors.toList());
        String first = stringList.stream().skip(5).findFirst().get();

        Set<String> set = new HashSet<String>(0);
        set.add("111");
        set.add("555");
        set.add("333");
        set.add("444");
        set.add("222");
        set.forEach(System.out::println);
        set.forEach(LOGGER::info);

        boolean anyMatch = set.stream().anyMatch(el -> el.equals("111"));
        boolean allMatch = set.stream().allMatch(el -> el.equals("111"));

        String min = set.stream().min(String::compareTo).get();
        String max = set.stream().max(String::compareTo).get();
        Integer maxInt1 = collect1.stream().max(Integer::compare).get();
        Integer maxInt2 = collect1.stream().max(Integer::compareTo).get();



        //Saving collection to File
        int number = Integer.parseInt(args[0]);
        String filePath = args[1];
        String fileName = args[2];
        String format = args[3];
        File file = new File(filePath + fileName + "." + format);
        List<String> list = createList(number);
        switch (format) {
            case "csv":
                saveCSV(list, file);
                break;
            case "json":
                saveJSON(list, file);
                break;
            default:
                LOGGER.error("Incorrect format input!");
        }
    }

    private static void saveCSV(List<String> list, File file) throws IOException {
        if (file.exists())
            file.delete();
        FileWriter writer = new FileWriter(file);
        list.forEach(el -> {
            try {
                writer.write(String.format("%s\n", el));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        writer.flush();
        writer.close();
    }

    public static void saveJSON(List<String> list, File file) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(list);
        FileWriter writer = new FileWriter(file);
        writer.write(json);
        writer.flush();
        writer.close();
    }

    private static List<String> createList(int number) {
        List<String> list = new ArrayList<String>(0);
        int count = 1;
        for (int i = 0; i < number; i++) {
            list.add(count + ". String");
            count++;
        }
        return list;
    }
}
