package study.market.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.market.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
