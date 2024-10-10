package org.sopt.seminar1;

import java.util.ArrayList;
import java.util.List;

public class DiaryRepository {
    private final String filePath = "diary.txt";
    private final FileHandler fileHandler = new FileHandler(filePath);

    /**
     * 단일 Diary 저장 하는 메서드
     * @param diary
     * @return
     */
    boolean save(final Diary diary) {
        List<Diary> diaryList = getDiaryList();
        diary.setId(getNextDiaryId(diaryList));
        return fileHandler.saveToFile(diaryToString(diary), true);
    }

    /**
     * 다음 ID 찾기
     * @param diaryList
     * @return
     */
     long getNextDiaryId(List<Diary> diaryList) {
        return diaryList.stream()
                .mapToLong(Diary::getId)
                .max()
                .orElse(0) + 1;
    }

    /**
     * 삭제하지 않은 다이어리 리스트 반환
     * @return
     */
     List<Diary> findAll() {
        return getDiaryList().stream()
                .filter(diary -> !diary.getIsDelete())
                .toList();
    }

    /**
     * 모든 다이어리 리스트 반환
     * @return
     */
     List<Diary> getDiaryList() {
        return fileHandler.getDiaryList();
    }

    /**
     * 업데이트 로직
     * @param updatedDiary
     */

     void update(final Diary updatedDiary) {
        List<Diary> diaryListAll = getDiaryList();
        boolean found = false;

        for (int i = 0; i < diaryListAll.size(); i++) {
            Diary diary = diaryListAll.get(i);
            if (diary.getId() == updatedDiary.getId() && !diary.getIsDelete()) {
                //수정 횟수 2번으로 제한
                if (diary.getUpdateCount() < 2) {
                    updatedDiary.setUpdateCount(diary.getUpdateCount() + 1);
                    diaryListAll.set(i, updatedDiary);
                    found = true;
                    break;
                } else {
                    System.out.println("수정할 수 없습니다.");
                    return;
                }
            }
        }

        if (found) {
            fileHandler.saveToFile("", false);
            saveDiaryList(diaryListAll);
        } else {
            System.out.println("ID를 찾을 수 없습니다.");
        }
    }

     void delete(final long id) {
        List<Diary> diaryListAll = getDiaryList();
        List<Diary> updatedList = new ArrayList<>();
        boolean found = false;

        for (Diary diary : diaryListAll) {
            // 1. 삭제하려는  ID 가 아닐 경우
            if (diary.getId() != id) {
                updatedList.add(diary);
            }
            // 2. 삭제하려는 ID 일 경우
            // 2-1 . 이미 삭제 되었는지 확인
            else {
                if (!diary.getIsDelete()) {
                    diary.setDelete(true);
                    updatedList.add(diary);
                    found = true;
                } else {
                    System.out.println("이미 삭제된 다리어리 입니다");
                    return;
                }
            }
        }
        //삭제하려는 ID를 찾았을 경우
        if (found) {
            fileHandler.saveToFile("", false);
            saveDiaryList(updatedList);
        } else {
            System.out.println("ID를 찾을 수 없습니다.");
        }
    }

    /**
     * 다이어리 리스트 저장
     * @param diaryList
     */
     void saveDiaryList(final List<Diary> diaryList) {
        for (Diary diary : diaryList) {
            String data = diaryToString(diary);
            fileHandler.saveToFile(data, true);
        }
    }

    /**
     * Diary 객체  String 으로 형번환
     * @param diary
     * @return
     */
    String diaryToString(final Diary diary) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(diary.getId()).append("/")
                .append(diary.getBody()).append("/")
                .append(diary.getIsDelete()).append("/")
                .append(diary.getUpdateCount());
        return stringBuilder.toString();
    }

    /**
     * 복구하는 메서드
     * @param id
     */
    void restore(long id) {
        List<Diary> diaryListAll = getDiaryList();
        List<Diary> updatedList = new ArrayList<>();
        boolean found = false;

        for (Diary diary : diaryListAll) {
            // 1. 복구하려는 ID가 아닐 경우
            if (diary.getId() != id) {
                updatedList.add(diary);
            }
            // 2. 복구하려는 아이디일 경우
            else {
                // 2-1 . 삭제된 다이어리만 복구 진행
                if (diary.getIsDelete()) {
                    diary.setDelete(false);
                    diary.setUpdateCount(0);
                    updatedList.add(diary);
                    found = true;
                } else {
                    System.out.println("삭제하지 않은 다이어리 입니다");
                    return;
                }
            }
        }

        if (found) {
            fileHandler.saveToFile("", false);
            saveDiaryList(updatedList);
        } else {
            System.out.println("ID를 찾을 수 없습니다.");
        }
    }
}
