package org.sopt.Diary.service;

import org.sopt.Diary.repository.DiaryEntity;
import org.sopt.Diary.repository.DiaryRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DiaryService {

    private final DiaryRepository diaryRepository;

    //추상황된 인터페이스에 구현체 가져오는 것
    public DiaryService(DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

    public void createDiary(){
        diaryRepository.save(new DiaryEntity("지원"));
    }
    public List<Diary> getList(){
        //Repository 에서 DiaryEntity 가져옴
        List<DiaryEntity> diaryEntityList = diaryRepository.findAll();

        //DiaryEntity 를 Diary 로 변환해주는 작업
        final List<Diary> diaryList = new ArrayList<>();

        for(DiaryEntity diaryEntity : diaryEntityList){
            diaryList.add(
                    new Diary(diaryEntity.getId(), diaryEntity.getName())
            );
        }
        return diaryList;
    }

}
