package org.sopt.Diary.service;

import org.sopt.Diary.api.DiaryResponse;
import org.sopt.Diary.dto.DiariesResponse;
import org.sopt.Diary.dto.DiaryRequest;
import org.sopt.Diary.repository.DiaryEntity;
import org.sopt.Diary.repository.DiaryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;



@Component
public class DiaryService {

    private final DiaryRepository diaryRepository;

    //추상황된 인터페이스에 구현체 가져오는 것
    public DiaryService(DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

    public void createDiary(DiaryRequest diaryRequest){
        Diary diary = new Diary(diaryRequest.getTitle(),diaryRequest.getContent(), LocalDateTime.now());
        diaryRepository.save(new DiaryEntity(diary.getTitle(),diary.getContent()));
    }


    public List<DiariesResponse> getDiaryList(){
        List<DiaryEntity> diaryEntityList = diaryRepository.findTop10ByDelYnFalseOrderByDateDesc();
        List<DiariesResponse> diariesResponses = new ArrayList<>();

        for(DiaryEntity diary : diaryEntityList){
            DiariesResponse diariesResponse = new DiariesResponse(diary.getId(), diary.getTitle());
            diariesResponses.add(diariesResponse);
        }
        return diariesResponses;
    }

    public Diary getDiary(Long id) {

        DiaryEntity diaryEntity = diaryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if(diaryEntity.getDelYn()){
            throw new ResponseStatusException(HttpStatus.GONE);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = diaryEntity.getDate().format(formatter);
        return new Diary(diaryEntity.getId(), diaryEntity.getTitle(), diaryEntity.getContent(),formattedDate);
    }

    public void patchDiary(Long id, String content) {

        DiaryEntity diaryEntity = diaryRepository.findByIdAndDelYnFalse(id);
        if(diaryEntity==null){
          throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        diaryRepository.save( new DiaryEntity(
                diaryEntity.getId(),
                diaryEntity.getTitle(),
                content,
                diaryEntity.getDate()

        ));
    }

    public void deleteDiary(Long id) {
        DiaryEntity diaryEntity = diaryRepository.findByIdAndDelYnFalse(id);

        if(diaryEntity==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        diaryEntity.delete();
        diaryRepository.save(diaryEntity);
    }
}
