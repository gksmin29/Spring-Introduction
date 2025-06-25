package hello.hello_spring.controller;

import hello.hello_spring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

//controller 어노테이션이 있으면
//스프링 컨테이너가 이 클래스를 컨트롤러로 인식하고 관리하기 시작한다.
//Spring Bean이 관리된다.
@Controller
public class MemberController {
    //이렇게 굳이 sevice를 매번 등록해서 쓰기보다는 스프링 컨테이너를 활용할 것
    //private final MemberService memberService = new MemberService();

    private final MemberService memberService;

    //이 어노테이션이 붙으면 자동으로 알맞은 빈을 찾아서 넣어준다.
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
}
