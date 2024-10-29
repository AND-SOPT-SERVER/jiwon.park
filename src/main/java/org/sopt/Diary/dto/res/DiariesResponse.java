package org.sopt.Diary.dto.res;

public record DiariesResponse(Long id, String title){
}
//레코드를 이용하도록 수정-
// - 레코드를 사용하면 모든 필드가 자동으로 final이 되고
// - 기본 생성자를 제공해주어 코드가 더 깔끔해진다
// - getter 뿐만 아니라, toString() 등 다양한 메서드를 지원해준다

