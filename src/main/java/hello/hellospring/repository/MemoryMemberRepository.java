package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

//어떤 자료구조를 그대로 사용하기보단 클래스로 한번 묶어서 사용하면 좀더 직관적인 함수를 만들 수 있음
@Repository
public class MemoryMemberRepository implements MemberRepository{
    private static Map<Long, Member> store = new HashMap<>();
    private  static  long sequence = 0L;

    @Override
    //member에 이름은 이미 저장되었다고 가정
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    //
    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    //store을 돌면서 name을 찾는 함수->stream??
    //Map.values() -> Collection<T>
    //stream() 을통해 Stream 객체로
    //filter()을 통해 람다함수로 필터링함.
    //findAny()를 통해 stream에서 가장 먼저 발견되는 객체를 return
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
    }
    @Override
    public Optional<Member> findMember(String name, String password){
        return null;
    }
    @Override
    //자바에서는 실무에 list를 많이 씀
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore(){
        store.clear();
    }

}
