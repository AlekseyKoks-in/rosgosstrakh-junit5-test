package com.alex.study.tests;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class RgsParametrized {

    private static Stream<Arguments> personNames() {
        return Stream.of(
                Arguments.of("Петров", "Петр", "Петрович"),
                Arguments.of("Иванов", "Иван", "Иванович"),
                Arguments.of("Васильев", "Василий", "Васильевич")
        );
    }
}
