package org.sopt.seminar1.entity;

public class Diary {

    private Long id;

    private final String body;

    private boolean isDeleted = false;

    private  int updateCount =0;

    public Diary(Long id, String body,boolean isDeleted,int updateCount){
        this.id = id;
        this.body=body;
        this.isDeleted=isDeleted;
        this.updateCount = updateCount;
    }

    public Diary(Long id, String body){
        this.id = id;
        this.body=body;
    }

    public Long getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public boolean getIsDeleted(){return isDeleted;}

    public int getUpdateCount(){return  updateCount;}

    public void setDelete(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public void setId(long id){
        this.id = id;
    }

    public void setUpdateCount(int i) {
        this.updateCount = i;
    }
}
