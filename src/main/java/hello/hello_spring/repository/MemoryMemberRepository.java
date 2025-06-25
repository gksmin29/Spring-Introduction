package hello.hello_spring.repository;

import hello.hello_spring.domain.Member;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository{

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        //member의 아이디 값 할당
        member.setId(++sequence);
        //map에 저장
        store.put(member.getId(), member);
        //저장된 결과를 반환
        return member;

    }

    @Override
    public Optional<Member> findById(Long id) {
        //null을 가질 수도 있으므로 Optional로 감싼다.
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clear() {
        store.clear();
    }


}
