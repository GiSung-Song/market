package study.market.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.market.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

}
