package hello.hello_spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data", "spring!");
        //resources의 templates의 hello.html을 찾아간다.(thymeleaf)
        return "hello";
    }

    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam(value = "name") String name, Model model) {
        model.addAttribute("name", name);
        return "hello-template";
    }

    @GetMapping("hello-string")
    //http의 body부에 이 내용을 직접 넣어주겠다.
    @ResponseBody
    public String helloString(@RequestParam("name") String name) {
        //템플릿을 거치지 않고 그냥 입력한 내용을 바로 띄워버림
        return "hello" + name;
    }

    //문자가 아닌 데이터를 내놓으려면
    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) {
        //만약 객체를 넘긴다면?
        //{"name":"spring!!!!!!!!!!!!!!"}
        //이렇게 json방식으로 나온다.
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    }

    static class Hello {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }


}
