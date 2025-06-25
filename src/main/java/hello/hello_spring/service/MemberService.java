package hello.hello_spring.service;

import hello.hello_spring.domain.Member;
import hello.hello_spring.repository.MemberRepository;
import hello.hello_spring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    //원래는 이렇게 정의되어 있었으나, test 코드의 repository와 실제 코드의 repository가 달라지는 것을 막기 위하여
    //아래의 코드처럼 repository를 만들기만 하고, 내용은 생성자로 주입받게 수정.
    //private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final MemberRepository memberRepository;

    //memberRepository를 외부에서 넣어주도록 변경
    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    //회원가입
    public long join(Member member) {
        //같은 이름이 있는 중복 회원x
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    public void validateDuplicateMember(Member member) {
        //원래는 result를 만들고, result.ifPresent()를 호출했지만,
        //그렇게 하면 너무 지저분하기 때문에, 차피 memberRepository.findByName이 Optional을 뱉으므로
        //memberRepository.findByName에 바로 이어준다.
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    //전체 회원 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
