package org.sopt.seminar1;

import java.util.ArrayList;
import java.util.List;

public class DiaryRepository {

    private final String filePath = "diary.txt";
    private final FileHandler fileHandler = new FileHandler(filePath);
    private final DiaryFormatter diaryFormatter = new DiaryFormatter();
    private final static int  UPDATE_COUNT =2;

    /**
     * 단일 Diary 저장 하는 메서드
     * @return 저장 성공 여부
     */
    boolean save(final Diary diary) {
        List<Diary> diaryList = getDiaryList();
        diary.setId(getNextDiaryId(diaryList));
        return fileHandler.saveToFile(diaryFormatter.diaryToString(diary), true);
    }

    /**
     * 다음 ID 찾기
     */
     long getNextDiaryId(List<Diary> diaryList) {
        return diaryList.stream()
                .mapToLong(Diary::getId)
                .max()
                .orElse(0) + 1;
    }

    /**
     * 삭제하지 않은 다이어리 리스트 반환
     */
     List<Diary> findAll() {
        return getDiaryList().stream()
                .filter(diary -> !diary.getIsDeleted())
                .toList();
    }

    /**
     * 모든 다이어리 리스트 반환
     */
    List<Diary> getDiaryList() {
        try {
            List<String> lines = fileHandler.readFromFile();
            return diaryFormatter.parseDiaryList(lines);
        } catch (Exception e) {
            System.out.println("파일 읽기 중 오류 발생: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * 업데이트 로직
     * @param updatedDiary 새로 업데이트하는 다이어리
     */
    public void update(final Diary updatedDiary) {
        List<Diary> diaryList = getDiaryList();
        boolean isFound = false;

        for (int i = 0; i < diaryList.size(); i++) {
            Diary diary = diaryList.get(i);
            if (diary.getId().equals(updatedDiary.getId())  && !diary.getIsDeleted()) {
                if (canUpdate(diary)) { // 수정 가능 여부 검증
                    updatedDiary.setUpdateCount(diary.getUpdateCount() + 1);
                    diaryList.set(i, updatedDiary);
                    isFound = true;
                    break;
                } else {
                    System.out.println("수정할 수 없습니다.");
                    return;
                }
            }
        }

        if (validateId(isFound)) {
            fileHandler.saveToFile("", false);
            saveDiaryList(diaryList);
        }
    }

    public void delete(final long id) {
        List<Diary> diaryList = getDiaryList();
        boolean found = false;

        for (Diary diary : diaryList) {
            if (diary.getId() == id) {
                if (!diary.getIsDeleted()) {
                    diary.setDelete(true);
                    found = true;
                } else {
                    System.out.println("이미 삭제된 다이어리입니다.");
                    return;
                }
            }
        }

        if (validateId(found)) {
            fileHandler.saveToFile("", false);
            saveDiaryList(diaryList);
        }
    }

    /**
     * 복구하는 메서드
     * @param id 복구하려는 ID
     */
    public void restore(final long id) {
        List<Diary> diaryList = getDiaryList();
        boolean found = false;

        for (Diary diary : diaryList) {
            if (diary.getId() == id) {
                if (diary.getIsDeleted()) {
                    diary.setDelete(false);
                    diary.setUpdateCount(0);
                    found = true;
                } else {
                    System.out.println("삭제되지 않은 다이어리입니다.");
                    return;
                }
            }
        }

        if (validateId(found)) {
            fileHandler.saveToFile("", false);
            saveDiaryList(diaryList);
        }
    }

    //파일에 다이어리 리스트 저장
    void saveDiaryList(final List<Diary> diaryList) {
        for (Diary diary : diaryList) {
            String data = diaryFormatter.diaryToString(diary);
            fileHandler.saveToFile(data, true);
        }
    }

    // ID 유효성 검증
    private boolean validateId(boolean isFound) {
        if (!isFound) {
            System.out.println("ID를 찾을 수 없습니다.");
        }
        return isFound;
    }

    //수정 가능 여부
    private boolean canUpdate(Diary diary) {
        return diary.getUpdateCount() < UPDATE_COUNT;
    }

}
