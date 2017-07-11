package io.github.zwieback.relef.web.services;

import io.github.zwieback.relef.configs.WebConfigForTest;
import io.github.zwieback.relef.entities.dto.auth.AuthDto;
import io.github.zwieback.relef.utils.json.AbstractDeserializerTest;
import io.github.zwieback.relef.web.rest.services.RestService;
import io.github.zwieback.relef.web.services.exceptions.AuthException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        WebConfigForTest.class
})
public class AuthServiceTest extends AbstractDeserializerTest {

    @SuppressWarnings("unused")
    @Autowired
    private AuthService authService;

    @SuppressWarnings("unused")
    @Autowired
    private RestService restService;

    @Test
    public void test_auth_should_authorize() {
        setAuthDto(readValue("classpath:json/auth/authorized.json", AuthDto.class));
        authService.auth();
    }

    @Test(expected = AuthException.class)
    public void test_auth_should_throws_exception() {
        setAuthDto(readValue("classpath:json/auth/not_authorized.json", AuthDto.class));
        authService.auth();
    }

    @Test
    public void test_logout_should_logout() {
        setAuthDto(readValue("classpath:json/auth/logout.json", AuthDto.class));
        authService.logout();
    }

    private void setAuthDto(AuthDto authDto) {
        when(restService.post(anyString(), anyObject(), anyObject())).thenReturn(authDto);
    }
}
