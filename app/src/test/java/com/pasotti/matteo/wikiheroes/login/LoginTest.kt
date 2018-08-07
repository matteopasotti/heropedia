package com.pasotti.matteo.wikiheroes.login

import org.junit.Test

class LoginTest {


    //In Kotlin we can name test with natural names

    /*
    Each test should be created from the following blocks:

    Arrange/Given - in which we will prepare all needed data required to perform test

    Act/When - in which we will call single method on tested object

    Assert/Then - in which we will check result of the test, either pass or fail
     */


    @Test
    fun `login with correct login and password`() {

        //given
        val objectUnderTest = LoginTest()       //test object
        val correctLogin = "matteopasotti"     //test parameter
        val correctPassword = "Password123"   //test parameter

        //when

        //then
    }
}