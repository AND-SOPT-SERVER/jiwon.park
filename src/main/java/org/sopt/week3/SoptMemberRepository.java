package org.sopt.week3;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // 안 넣어도
public interface SoptMemberRepository extends JpaRepository<SoptMemberEntity,Long> {
}
