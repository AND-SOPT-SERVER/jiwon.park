package org.sopt.seminar1;

public class Diary {

    private Long id;

    private final String body;

    private boolean isDelete;
    private  int updateCount;

    public Diary(Long id, String body,boolean isDelete,int updateCount){
        this.id = id;
        this.body=body;
        this.isDelete=isDelete;
        this.updateCount = updateCount;
    }

    public Long getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public boolean getIsDelete(){return isDelete;}

    public int getUpdateCount(){return  updateCount;}

    void setDelete(){
        this.isDelete=true;
    }

    void setId(long id){
        this.id = id;
    }
}
