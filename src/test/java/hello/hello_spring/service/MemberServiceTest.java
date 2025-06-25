package hello.hello_spring.service;

import hello.hello_spring.domain.Member;
import hello.hello_spring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    //MemberService memberService = new MemberService();
    //MemoryMemberRepository memberRepository = new MemoryMemberRepository();
    //다만, 이렇게 하면 실제 케이스와 테스트 케이스의 repository가 다른 상황이 발생
    //왜? test에서도 repository를 만들고 service 내부에서도 repository를 만들기 때문
    //(물론 이 프로젝트에서는 MemoryMemberRepository의 store가 static으로 선언 되어있기 때문에 문제는 발생하지 않지만)
    //원래는 같은 repository로 테스트 하는 게 맞다.


    //따라서 이렇게 DI방식으로 해결한다.
    //각각 인스턴스를 생성한 후
    MemberService memberService;
    MemoryMemberRepository memberRepository;
    //Dependency Injection으로 외부에서 속성을 주입해준다.
    //beforeEach -> 각 테스트 케이스의 가장 첫 단계에 실행
    @BeforeEach
    public void beforeEach() {
        //repository를 만들고
        memberRepository = new MemoryMemberRepository();
        //memberService를 만든 후, repository를 주입
        //이렇
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clear();
    }

    @Test
    void 회원가입() {
        //given
        Member member = new Member();
        member.setName("hello");
        //when
        Long savedId = memberService.join(member);

        //then
        Member findMember = memberService.findOne(savedId).get();
        Assertions.assertThat(member.getName()).isEqualTo(findMember.getName());
    }


    //중복 회원을 잘 걸러낼 수 있는가
    @Test
    public void 중복_회원_예외() {
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

       /* try {
            memberService.join(member2);
            fail();
        } catch (IllegalStateException e) {
            Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }*/

        //then
    }
    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}