package org.sopt.seminar1;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class DiaryRepository {

    private final String filePath = "diary.txt";
    private final AtomicLong numbering = new AtomicLong();
    private final FileHandler fileHandler = new FileHandler(filePath);

    /**
     * 다이어리를 파일에 저장하는 메소드
     */
    boolean save(final Diary diary) {
        List<Diary> diaryList = getDiaryList();

        // 마지막 ID 찾고 새로운 다이어리 ID 설정
        long lastId = diaryList.stream()
                .mapToLong(Diary::getId)
                .max()
                .orElse(0);

        diary.setId(lastId + 1); // ID는 마지막 ID + 1
        String data = diaryToString(diary);

        // 파일에 다이어리 데이터를 저장
        return fileHandler.saveToFile(data, true); // append 모드로 저장
    }

    /**
     * 삭제되지 않은 다이어리 리스트 반환
     */
    List<Diary> findAll() {
        final List<Diary> diaryListAll = getDiaryList();
        final List<Diary> diaryList = new ArrayList<>();

        // 삭제되지 않은 다이어리만 필터링
        for (Diary diary : diaryListAll) {
            if (!diary.getIsDelete()) {
                diaryList.add(diary);
            }
        }
        return diaryList;
    }

    /**
     * 파일에서 전체 다이어리 리스트를 받아오는 메소드
     */
    List<Diary> getDiaryList() {
        return fileHandler.getDiaryList();
    }

    /**
     * 다이어리를 업데이트하는 메소드
     */
    void update(final Diary updatedDiary) {
        List<Diary> diaryListAll = getDiaryList();
        boolean found = false;

        for (int i = 0; i < diaryListAll.size(); i++) {
            Diary diary = diaryListAll.get(i);

            // 해당 ID의 다이어리를 찾아 업데이트
            if (diary.getId() == updatedDiary.getId()) {
                diaryListAll.set(i, updatedDiary); // 업데이트
                found = true;
                break;
            }
        }

        if (found) {
            // 파일 초기화 후, 수정된 다이어리 리스트를 저장
            fileHandler.saveToFile("", false); // 파일 초기화
            saveDiaryList(diaryListAll); // 수정된 리스트 저장
        } else {
            System.out.println("ID를 찾을 수 없습니다.");
        }
    }

    /**
     * 특정 ID의 다이어리를 삭제하는 메소드
     */
    void delete(final long id) {
        List<Diary> diaryListAll = getDiaryList();
        List<Diary> updatedList = new ArrayList<>();
        boolean found = false;

        for (Diary diary : diaryListAll) {
            if (diary.getId() != id) {
                updatedList.add(diary); // 해당 ID 제외한 다이어리 리스트 생성
            } else {
                found = true; // 삭제할 다이어리 발견
            }
        }

        if (found) {
            // 기존 파일을 초기화하고, 삭제 후 남은 다이어리 리스트 저장
            fileHandler.saveToFile("", false); // 파일 초기화
            saveDiaryList(updatedList); // 수정된 리스트 저장
        } else {
            System.out.println("ID를 찾을 수 없습니다.");
        }
    }

    /**
     * 다이어리 리스트를 파일에 저장하는 메소드
     */
    private void saveDiaryList(final List<Diary> diaryList) {
        for (Diary diary : diaryList) {
            String data = diaryToString(diary);
            fileHandler.saveToFile(data, true); // append 모드로 저장
        }
    }

    /**
     * 다이어리를 문자열 포맷으로 변환하는 메소드
     */
    String diaryToString(final Diary diary) {
        return diary.getId() + "/" + diary.getBody() + "/N/" + 0; // N은 삭제되지 않음을 의미
    }
}
