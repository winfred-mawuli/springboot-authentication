package com.restapi.restapi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class RestApiApplicationTests {
    Calculator underTest = new Calculator();

    @Test
    void itShouldAddNumbers() {
        //given
        int numberOne = 49;
        int numberTwo = 40;
        //when
        int results = underTest.add(numberOne,numberTwo);
        //then
        int expected = 89;
        assertThat(results).isEqualTo(expected);
    }

    class Calculator{
        int add(int a, int b){
            return a + b;
        }
    }

}
