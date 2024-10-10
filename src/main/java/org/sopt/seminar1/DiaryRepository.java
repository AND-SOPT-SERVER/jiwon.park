package org.sopt.seminar1;


import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class DiaryRepository {

    private final String filePath = "diary.txt";
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
                if (diary.getId() == updatedDiary.getId() && !diary.getIsDelete()) {
                    // 수정 횟수가 2보다 작은 경우에만 업데이트
                    if(diary.getUpdateCount()<2) {
                        updatedDiary.setUpdateCount(diary.getUpdateCount() + 1);
                        diaryListAll.set(i, updatedDiary); // 인덱스 i 에 있는 다이어리 updatedDiary로 교체
                        //해당 ID update count 증가

                        found = true;
                        break;
                    }
                    else{
                        System.out.println("수정할 수 없습니다");
                        return;
                    }
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
     * 모두 다 Update
     */
    void updateAll() {
        List<Diary> diaryListAll = getDiaryList();


        for (int i = 0; i < diaryListAll.size(); i++) {
            Diary diary = diaryListAll.get(i);

            diary.setUpdateCount(0);
            diaryListAll.set(i, diary);
            }

        // 파일 초기화 후, 수정된 다이어리 리스트를 저장
        fileHandler.saveToFile("", false); // 파일 초기화
        saveDiaryList(diaryListAll); // 수정된 리스트 저장
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
                //삭제된 다이어리가 아닌 경우
                if(!diary.getIsDelete()) {
                    diary.setDelete(true);
                    updatedList.add(diary);
                    found = true; // 삭제할 다이어리 발견
                }
                else{
                    System.out.println("이미 삭제된 다리어리 입니다");
                   return;
                }
            }
        }

        if (found) {
            // 기존 파일을 초기화하고, 삭제 처리 된 다이어리 리스트 저장
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

        //StingBuilder 사용
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(diary.getId()).append("/");
        stringBuilder.append(diary.getBody()).append("/");
        stringBuilder.append(diary.getIsDelete()).append("/");
        stringBuilder.append(diary.getUpdateCount());

        String str = stringBuilder.toString();
        return str;
    }

    /**
     * 다이어리 복구하는 메소드
     * @param id
     */
    public void restore(long id) {
        List<Diary> diaryListAll = getDiaryList();
        List<Diary> updatedList = new ArrayList<>();
        boolean found = false;

        for (Diary diary : diaryListAll) {
            if (diary.getId() != id) {
                updatedList.add(diary); // 해당 ID 제외한 다이어리 리스트 생성
            } else {
                // 삭제하지 않은 다이어리일 경우 복구 불가능하게 설정
                if(diary.getIsDelete()) {
                    diary.setDelete(false);
                    diary.setUpdateCount(0);
                    updatedList.add(diary);
                    found = true;
                }else{
                    System.out.println("삭제하지 않은 다이어리 입니다");
                    return;
                }
            }
        }

        if (found) {
            // 기존 파일을 초기화하고, 복구 후 처리한 다이러리 리스트 저장
            fileHandler.saveToFile("", false); // 파일 초기화
            saveDiaryList(updatedList); // 수정된 리스트 저장
        } else {
            System.out.println("ID를 찾을 수 없습니다.");
        }


    }

    public void timer(){
        Date now = new Date();
        Date today = new Date(now.getYear(),now.getHours(),now.getDay()+ 1, 0, 0, 0);
//        long delay = today.getTime()-now.getTime(); //첫 실행 시간 -> 다음날 자정
//        long period = 86400000; //24시간

        long delay = 3000; // 3분 (3분 * 60초 * 1000밀리초)
        long period = 86400000; // 24시간 (24시간 * 60분 * 60초 * 1000밀리초)

        Timer t = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                updateAll();
                System.out.println("초기화되었습니다");
            }
        };
        t.scheduleAtFixedRate(task, delay, period);

    }
}
