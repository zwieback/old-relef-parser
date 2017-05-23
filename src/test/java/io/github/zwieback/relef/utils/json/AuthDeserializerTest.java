package io.github.zwieback.relef.utils.json;

import io.github.zwieback.relef.entities.dto.auth.AuthDto;
import org.junit.Test;
import org.springframework.util.StringUtils;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class AuthDeserializerTest extends AbstractDeserializerTest {

    @Test
    public void test_deserialize_should_return_sid() throws IOException {
        AuthDto authDto = readValue("classpath:json/auth/sid.json", AuthDto.class);
        assertEquals("", authDto.getAction());
        assertEquals("", authDto.getSid());
        assertNull(authDto.getUserId());
        assertNotNull(authDto.getMark());
        assertFalse(StringUtils.isEmpty(authDto.getMark().getSid()));
    }

    @Test
    public void test_deserialize_should_return_authorized() throws IOException {
        AuthDto authDto = readValue("classpath:json/auth/authorized.json", AuthDto.class);
        assertEquals("auth", authDto.getAction());
        assertFalse(StringUtils.isEmpty(authDto.getSid()));
        assertFalse(StringUtils.isEmpty(authDto.getUserId()));
        assertNotNull(authDto.getMark());
        assertTrue(authDto.getMark().getAuth());
        assertTrue(authDto.getMark().getBuy());
    }

    @Test
    public void test_deserialize_should_return_not_authorized() throws IOException {
        AuthDto authDto = readValue("classpath:json/auth/not_authorized.json", AuthDto.class);
        assertEquals("auth", authDto.getAction());
        assertFalse(StringUtils.isEmpty(authDto.getSid()));
        assertNull(authDto.getUserId());
        assertNull(authDto.getMark());
    }

    @Test
    public void test_deserialize_should_return_logout() throws IOException {
        AuthDto authDto = readValue("classpath:json/auth/logout.json", AuthDto.class);
        assertEquals("logout", authDto.getAction());
        assertTrue(StringUtils.isEmpty(authDto.getSid()));
        assertEquals("0", authDto.getUserId());
        assertNull(authDto.getMark());
    }
}
