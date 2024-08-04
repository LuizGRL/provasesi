package br.senai.sc.helloworld;


import org.easymock.EasyMockExtension;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(EasyMockExtension.class)
class ExampleEasymockTest {

    @TestSubject
    private HelloWorldBusiness helloWorldBusiness;

    @Mock
    private HelloWorldDao dao;

    @Test
    void testGetHelloWorldPhrase() {
        expect(dao.getHelloWorldPhrase()).andReturn("Hello World!");
        replay(dao);

        String helloWorldPhrase = helloWorldBusiness.getHeloWorldPhrase();
        assertEquals("Hello World!", helloWorldPhrase);
    }
}
