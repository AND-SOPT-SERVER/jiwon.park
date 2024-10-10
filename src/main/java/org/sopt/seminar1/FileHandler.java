package org.sopt.seminar1;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    private final String filePath;

    FileHandler(final String filePath) {
        this.filePath = filePath;
    }

    boolean saveToFile(String data, boolean isNew) {
        File file = new File(filePath);

        if (!file.exists()) {
            file.getParentFile().mkdirs(); // 상위 디렉토리 생성
            try {
                file.createNewFile(); // 파일이 없으면 생성
            } catch (Exception e) {
                System.out.println("파일 생성 중 오류 발생: " + e.getMessage());
                return false;
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, isNew))) {
            writer.write(data);
            writer.newLine(); // 데이터를 작성하고 줄바꿈 추가
            return true; // 성공적으로 저장됨
        } catch (Exception e) {
            System.out.println("파일 저장 중 오류가 발생했습니다: " + e.getMessage());
            return false; // 오류 발생 시 false 반환
        }
    }

    List<String> readFromFile() throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) { // 빈 줄을 무시
                    lines.add(line);
                }
            }
        }
        return lines;
    }

    List<Diary> getDiaryList() {
        List<String> lines;
        try {
            lines = readFromFile();
        } catch (IOException e) {
            System.out.println("일기 파일을 읽는 중 오류가 발생했습니다: " + e.getMessage());
            return new ArrayList<>();
        }

        List<Diary> diaries = new ArrayList<>();

        for (String line : lines) {
            String[] data = line.split("/");
            //저장형식이 [id/body/delYn/updateCount] 이기 때문
            if (data.length == 4) {
                try {
                    long id = Long.parseLong(data[0]);
                    String body = data[1];
                    boolean isDeleted = Boolean.parseBoolean(data[2]);
                    int updateCount = Integer.parseInt(data[3]);

                    Diary diary = new Diary(id, body, isDeleted, updateCount);
                    diaries.add(diary);
                } catch (NumberFormatException e) {
                    System.out.println("잘못된 형식의 데이터가 있습니다: " + line);
                }
            } else {
                System.out.println("데이터 형식이 올바르지 않습니다: " + line);
            }
        }
        return diaries;
    }
}
