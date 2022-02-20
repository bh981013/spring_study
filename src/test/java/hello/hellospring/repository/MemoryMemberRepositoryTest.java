package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;


public class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    //AfterEach를 통해 test함수 각각이 끝난 후 실행되는 함수를 생성함->공용데이터를 깔끔하게 지워줘야함.
   @AfterEach
    public void afterEach(){
       repository.clearStore();
   }

    @Test
    public void save(){
        Member member = new Member();
        member.setName("spring");
        repository.save(member);
        Member result = repository.findById(member.getId()).get();   //Optional로 감싸진 데이터를 get()을 통해 꺼냄
        //Assertions.assertEquals(result, member); //(기대하는것, 확인하는것)
        Assertions.assertThat(member).isEqualTo(result);    //위의 Assertions와 다름. 두가지방법
    }

    @Test
    public void findByName(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);
        
        //shift + f6 을 통해 변수명을 바꿀 수 있음
        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member result = repository.findByName("spring1").get();
        Assertions.assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        //shift + f6 을 통해 변수명을 바꿀 수 있음
        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();

        Assertions.assertThat(result.size()).isEqualTo(2);
    }
}
