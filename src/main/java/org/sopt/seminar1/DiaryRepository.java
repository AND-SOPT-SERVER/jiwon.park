package org.sopt.seminar1;

import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class DiaryRepository {
    private final Map<Long, String> storage = new ConcurrentHashMap<>();
    private final AtomicLong numbering = new AtomicLong();

     void save(final Diary diary) {
        //채번 과정
        final long id = numbering.addAndGet(1);

         // body 가 빈문자열 일 경우
         if ( diary.getBody().equals("")) {
             System.out.println("내용을 정확하게 입력해주세요");

         } else if (diary.getBody().length() > 30) {
             System.out.println("30자 이내로 입력해주세요");
         } else {
             //저장 과정
             storage.put(id, diary.getBody());
         }
    }

    List<Diary> findAll(){
        // (1) 저장할 List 생성
        final List<Diary> diaryList = new ArrayList<>();

        // (2)저장할 값을 불러오는 반복 구조
        for(long index=1; index <= numbering.intValue();index++) {
            final String body = storage.get(index);
            // body 가 null 일 경우 continue
            if(body == null){continue;}

            //(2-1) 불러온 값을 구성한 자료구조로 이관
            diaryList.add(new Diary(index, body));
        }
            // (3) 불러온 자료구조를 응단
            return diaryList;
    }

    void update(final Diary diary) {

        long id = diary.getId();
        // storage 에 해당 diary 의 아이디가 존재하지 않을 경우
        if(id == 0 || !storage.containsKey(id) ){
            System.out.println("잘못된 ID 입니다");

        // body 가 빈문자열 일 경우
        } else if (diary.getBody().equals("")) {
            System.out.println("내용을 정확하게 입력해주세요");
        } else if (diary.getBody().length() > 30) {
            System.out.println("30자 이내로 입력해주세요");
        } else{
            storage.put(diary.getId(), diary.getBody());

        }

    }

    void Delete(final long id) {

        // storage 에 해당 diary 의 아이디가 존재하지 않을 경우
          if( id == 0 || !storage.containsKey(id)){
              System.out.println("잘못된 ID 입니다" );
          }
          else {
              storage.remove(id);
          }
    }

}