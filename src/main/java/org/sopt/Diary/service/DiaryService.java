package org.sopt.Diary.service;

import org.sopt.Diary.dto.res.DiariesResponse;
import org.sopt.Diary.dto.req.DiaryRequest;
import org.sopt.Diary.repository.Category;
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

    private final static int limitM = 5;
    private final DiaryRepository diaryRepository;

    //추상황된 인터페이스에 구현체 가져오는 것
    public DiaryService(DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

    public void validateTitle(String title){
        if(diaryRepository.existsByTitle(title)){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    public void createDiary(DiaryRequest diaryRequest){

        validateTitle(diaryRequest.getTitle());

        //Repo에서 가장 최신의 다이어리 찾아와서 현재 시간과의 차이를 구함
        DiaryEntity latestDiary = diaryRepository.findTop1ByDelYnFalseOrderByDateDesc();
        if(latestDiary!=null){
            int remain = LocalDateTime.now().getMinute() - latestDiary.getDate().getMinute();
            if( remain<limitM){
                throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS);
            }
        }

        Diary diary = new Diary(diaryRequest.getTitle(),diaryRequest.getContent(), LocalDateTime.now(),diaryRequest.getCategory());
        diaryRepository.save(new DiaryEntity(diary.getTitle(),diary.getContent(),diary.getCategory()));
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

    public List<DiariesResponse> getDiaryListSortByContent(){

        List<DiaryEntity> diaryEntityList = diaryRepository.findTopByContentLengthAndDelYnFalse();
        List<DiariesResponse> diariesResponses = new ArrayList<>();

        for (int i=0; i<10;i++){
            DiaryEntity diary = diaryEntityList.get(i);
            DiariesResponse diariesResponse = new DiariesResponse(diary.getId(), diary.getTitle());
            diariesResponses.add(diariesResponse);
        }
        return diariesResponses;
    }

    public List<DiariesResponse> getDiaryListSortByCategory(Category category){

        List<DiaryEntity> diaryEntityList = diaryRepository.findByCategoryAndDelYnFalse(category);
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

        Category category = diaryEntity.getCategory();

        return new Diary(diaryEntity.getId(), diaryEntity.getTitle(), diaryEntity.getContent(),formattedDate,category);
    }

    public void patchDiary(Long id, String content, Category category) {

        DiaryEntity diaryEntity = diaryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if(diaryEntity.getDelYn()){
            throw new ResponseStatusException(HttpStatus.GONE);
        }

        diaryRepository.save( new DiaryEntity(
                diaryEntity.getId(),
                diaryEntity.getTitle(),
                content,
                diaryEntity.getDate(),
                category
        ));
    }

    public void deleteDiary(Long id) {
        DiaryEntity diaryEntity = diaryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if(diaryEntity.getDelYn()){
            throw new ResponseStatusException(HttpStatus.GONE);
        }

        diaryEntity.delete();
        diaryRepository.save(diaryEntity);
    }


}
