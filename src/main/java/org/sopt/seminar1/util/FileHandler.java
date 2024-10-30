package org.sopt.seminar1.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    private final String filePath;

    public FileHandler(final String filePath) {
        this.filePath = filePath;
    }

    /**
     * 파일로 저장하는 메서드
     * @param data 저장할 데이터
     * @param isNew 새로운 데이터인지 아닌지
     * @return boolean 저장성공했는지 여부
     */
    public boolean saveToFile(String data, boolean isNew) {
        File file = new File(filePath);

        if (!file.exists()) {
            if (file.mkdirs()) {
                System.out.println("디렉터리가 생성되었습니다: " + file.getPath());
            } else {
                System.out.println("디렉터리 생성에 실패했습니다.");
                return false;
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, isNew))) {
            writer.write(data);
            writer.newLine();
            return true;
        } catch (IOException e) {
            System.out.println("파일 저장 중 오류가 발생했습니다: " + e.getMessage());
            return false;
        }
    }

    /**
     * 파일로부터 모든 줄을 읽어 리스트로 반환
     * @return 파일 내용의 각 줄 리스트
     */
    public List<String> readFromFile() throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) { // 빈 줄 무시
                    lines.add(line);
                }
            }
        }
        return lines;
    }

}
