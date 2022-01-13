package com.technogise.interns;

import org.junit.jupiter.api.*;

    public class CalculatorTest {

        @BeforeAll
        static void setup(){
            System.out.println("@BeforeAll executed");
        }

        @BeforeEach
        void setupThis(){
            System.out.println("@BeforeEach executed");
        }

        @Test
        void testCalcOne()
        {
            System.out.println("======TEST ONE EXECUTED=======");

        }

        @Test
        void testCalcTwo()
        {
            System.out.println("======TEST TWO EXECUTED=======");

        }

        @AfterEach
        void tearThis(){
            System.out.println("@AfterEach executed");
        }

        @AfterAll
        static void tear(){
            System.out.println("@AfterAll executed");
        }

    }
