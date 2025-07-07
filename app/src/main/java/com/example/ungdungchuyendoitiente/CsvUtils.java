package com.example.ungdungchuyendoitiente;

import android.content.Context;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CsvUtils {
    /**
     * Lấy danh sách ngày đã có của slug (ví dụ USD/VND)
     */
    public static Set<String> getExistingDates(Context context, String slug) {
        Set<String> dates = new HashSet<>();
        File file = new File(context.getFilesDir(), "FileDuLieu.csv");
        if (!file.exists()) return dates;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty() || line.startsWith("slug")) continue;
                String[] parts = line.split(",");
                if (parts.length > 1 && parts[0].equals(slug)) {
                    dates.add(parts[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dates;
    }

    /**
     * Append 1 dòng mới
     */
    public static void appendLine(Context context, String line) {
        File file = new File(context.getFilesDir(), "FileDuLieu.csv");
        boolean fileExists = file.exists();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
            if (!fileExists) {
                bw.write("slug,date,open,high,low,close,currency");
                bw.newLine();
            }
            bw.write(line);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String convertToMdyyyy(String input) {
        try {
            SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat formatter = new SimpleDateFormat("M/d/yyyy");
            return formatter.format(parser.parse(input));
        } catch (Exception e) {
            e.printStackTrace();
            return input;
        }
    }
    public static void sortCsvFile(Context context, String slug) {
        File file = new File(context.getFilesDir(), "FileDuLieu.csv");
        if (!file.exists()) return;

        List<String[]> rows = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean isFirst = true;
            while ((line = br.readLine()) != null) {
                if (isFirst) {
                    isFirst = false; // Bỏ tiêu đề
                    continue;
                }
                String[] parts = line.split(",");
                if (parts.length > 1 && parts[0].equalsIgnoreCase(slug)) {
                    rows.add(parts);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Sắp xếp theo ngày
        rows.sort(Comparator.comparing(o -> normalizeDate(o[1])));

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write("slug,date,open,high,low,close,currency");
            bw.newLine();
            for (String[] parts : rows) {
                bw.write(String.join(",", parts));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Chuyển M/d/yyyy thành yyyyMMdd để sort
    private static String normalizeDate(String date) {
        try {
            SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
            return formatter.format(parser.parse(date));
        } catch (Exception e) {
            return date;
        }
    }



}