package br.senai.sc.helloworld;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExampleMockitoTest {

    @InjectMocks
    private HelloWorldBusiness helloWorldBusiness;

    @Mock
    private HelloWorldDao dao;

    @Test
    void testGetHelloWorldPhrase() {
        when(dao.getHelloWorldPhrase()).thenReturn("Hello World!");

        String helloWorldPhrase = helloWorldBusiness.getHeloWorldPhrase();
        assertEquals("Hello World!", helloWorldPhrase);

        verify(dao).getHelloWorldPhrase();
    }
}
