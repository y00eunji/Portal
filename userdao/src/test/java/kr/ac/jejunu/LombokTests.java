package kr.ac.jejunu;

import org.testng.annotations.Test;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class LombokTests {
    @Test
    public void equals(){
        User user1 = User.builder().id(1).name("eunji").password("1234").build();
        User user2 = User.builder().id(1).name("eunji").password("1234").build();
        assertThat(user1, is(user2));
    }
}
