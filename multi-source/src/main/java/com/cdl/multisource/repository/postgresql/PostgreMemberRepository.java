package com.cdl.multisource.repository.postgresql;

import com.cdl.multisource.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostgreMemberRepository extends JpaRepository<Member,Long> {
}
