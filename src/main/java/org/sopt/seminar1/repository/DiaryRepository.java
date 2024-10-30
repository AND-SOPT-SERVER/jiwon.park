package org.sopt.seminar1.repository;

import org.sopt.seminar1.entity.Diary;
import org.sopt.seminar1.util.DiaryFormatter;
import org.sopt.seminar1.util.FileHandler;

import java.util.ArrayList;
import java.util.List;

public class DiaryRepository {

    private final String filePath = "diary.txt";
    private final FileHandler fileHandler = new FileHandler(filePath);
    private final DiaryFormatter diaryFormatter = new DiaryFormatter();
    private final static int  UPDATE_COUNT =2;
    List<Diary> savedDiaryList;
    private boolean isModified = false; // 수정되면 저장

    public DiaryRepository() {
        this.savedDiaryList = loadDiariesFromFile();
    }

    private List<Diary> loadDiariesFromFile() {
        try {
            List<String> lines = fileHandler.readFromFile(); //파일에서 모든 내용을 가져옴(String)
            return diaryFormatter.parseDiaryList(lines); //문자열 -> diaryList
        } catch (Exception e) {
            System.out.println("파일 읽기 중 오류 발생: " + e.getMessage());
            return new ArrayList<>();
        }
    }


    /**
     * 단일 Diary 저장 하는 메서드
     * @return 저장 성공 여부
     */
    public boolean save(final Diary diary) {
        diary.setId(getNextDiaryId());
        savedDiaryList.add(diary);
        isModified=true;
        return true;
    }

    /**
     * 다음 ID 찾기
     */
     long getNextDiaryId() {
        return savedDiaryList.stream()
                .mapToLong(Diary::getId)
                .max()
                .orElse(0) + 1;
    }

    /**
     * 삭제하지 않은 다이어리 리스트 반환
     */
     public List<Diary> findAll() {
         List<Diary> result = new ArrayList<>();
         for (Diary diary : savedDiaryList) {
             if (!diary.getIsDeleted()) {
                 result.add(diary);
             }
         }
         return result;
    }

    /**
     * 모든 다이어리 리스트 반환
     */
    public List<Diary> getDiaryList() {
       return savedDiaryList;
    }

    /**
     * 업데이트 로직
     * @param updatedDiary 새로 업데이트하는 다이어리
     */
    public void update(final Diary updatedDiary) {
        boolean isFound = false;

        for (int i = 0; i < savedDiaryList.size(); i++) {
            Diary diary = savedDiaryList.get(i);
            if (diary.getId().equals(updatedDiary.getId())  && !diary.getIsDeleted()) {
                if (canUpdate(diary)) { // 수정 가능 여부 검증
                    updatedDiary.setUpdateCount(diary.getUpdateCount() + 1);
                    savedDiaryList.set(i, updatedDiary);
                    isFound = true;
                    isModified = true;
                    break;
                } else {
                    System.out.println("수정할 수 없습니다.");
                    return;
                }
            }
        }
        validateId(isFound);
    }

    public void delete(final long id) {

        boolean isFound = false;

        for (Diary diary : savedDiaryList) {
            if (diary.getId() == id) {
                if (!diary.getIsDeleted()) {
                    diary.setDelete(true);
                    isFound = true;
                    isModified = true;

                } else {
                    System.out.println("이미 삭제된 다이어리입니다.");
                    return;
                }
            }
        }
        validateId(isFound);
    }

    /**
     * 복구하는 메서드
     * @param id 복구하려는 ID
     */
    public void restore(final long id) {

        boolean isFound = false;

        for (Diary diary : savedDiaryList) {
            if (diary.getId() == id) {
                if (diary.getIsDeleted()) {
                    diary.setDelete(false);
                    diary.setUpdateCount(0);
                    isFound = true;
                    isModified= true;
                } else {
                    System.out.println("삭제되지 않은 다이어리입니다.");
                    return;
                }
            }
        }
        validateId(isFound);
    }

    public void saveChangesToFile() {
        if (isModified) {
            fileHandler.saveToFile("", false);
            for (Diary diary : savedDiaryList) {
                String data = diaryFormatter.diaryToString(diary); //diary->string
                fileHandler.saveToFile(data, true);
            }
            isModified = false;
            System.out.println("변경사항이 파일에 저장되었습니다.");
        }
    }


    //파일에 다이어리 리스트 저장
    public void saveDiaryList(final List<Diary> diaryList) {
        for (Diary diary : diaryList) {
            String data = diaryFormatter.diaryToString(diary);
            fileHandler.saveToFile(data, true);
        }
    }

    // ID 유효성 검증
    private void validateId(boolean isFound) {
        if (!isFound) {
            System.out.println("ID를 찾을 수 없습니다.");
        }
    }

    //수정 가능 여부
    private boolean canUpdate(Diary diary) {
        return diary.getUpdateCount() < UPDATE_COUNT;
    }

}
