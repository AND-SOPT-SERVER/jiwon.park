package org.sopt.Diary.api;

public class DiaryResponse {
    private  long id;
    private  String title;
    private String content;
    private String writeDate;

    // 모든 필드를 초기화하는 생성자
    public DiaryResponse(long id, String title, String content, String writeDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writeDate = writeDate;
    }

    public DiaryResponse(long id, String title) {
        this.id= id;
        this.title = title;
    }

    // Getter 메서드들
    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getWriteDate() {
        return writeDate;
    }
}