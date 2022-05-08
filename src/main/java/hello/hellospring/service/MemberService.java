package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     *
     * @param member
     * @return member id
     */

    //ctrl + alt + v -> 함수 호출의 return값을 담을 변수 자동완성
    public Long join(Member member){
        checkDup(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void checkDup(Member member) {
        memberRepository.findByName(member.getName())
            .ifPresent(m->{       //Option객체는 Null을 담을 수 있음.
                throw new IllegalStateException("이미 존재하는 회원입니다");
            });
    }

    public List<Member> findMembers(){
        return memberRepository.findAll();
    }
    public boolean login (Member member){
        return memberRepository.findMember(member.getName(), member.getPassword()).isPresent();
    }

    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }

}
