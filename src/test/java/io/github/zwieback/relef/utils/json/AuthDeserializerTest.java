package io.github.zwieback.relef.utils.json;

import io.github.zwieback.relef.entities.dto.auth.AuthDto;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthDeserializerTest extends AbstractDeserializerTest {

    @Test
    public void test_deserialize_should_return_sid() {
        AuthDto authDto = readValue("classpath:json/auth/sid.json", AuthDto.class);
        assertThat(authDto.getAction()).isEmpty();
        assertThat(authDto.getSid()).isEmpty();
        assertThat(authDto.getUserId()).isNull();
        assertThat(authDto.getMark()).isNotNull();
        assertThat(authDto.getMark().getSid()).isNotEmpty();
    }

    @Test
    public void test_deserialize_should_return_authorized() {
        AuthDto authDto = readValue("classpath:json/auth/authorized.json", AuthDto.class);
        assertThat(authDto.getAction()).isEqualTo("auth");
        assertThat(authDto.getSid()).isNotEmpty();
        assertThat(authDto.getUserId()).isNotEmpty();
        assertThat(authDto.getMark()).isNotNull();
        assertThat(authDto.getMark().getAuth()).isTrue();
        assertThat(authDto.getMark().getBuy()).isTrue();
    }

    @Test
    public void test_deserialize_should_return_not_authorized() {
        AuthDto authDto = readValue("classpath:json/auth/not_authorized.json", AuthDto.class);
        assertThat(authDto.getAction()).isEqualTo("auth");
        assertThat(authDto.getSid()).isNotEmpty();
        assertThat(authDto.getUserId()).isNull();
        assertThat(authDto.getMark()).isNull();
    }

    @Test
    public void test_deserialize_should_return_logout() {
        AuthDto authDto = readValue("classpath:json/auth/logout.json", AuthDto.class);
        assertThat(authDto.getAction()).isEqualTo("logout");
        assertThat(authDto.getSid()).isEmpty();
        assertThat(authDto.getUserId()).isEqualTo("0");
        assertThat(authDto.getMark()).isNull();
    }
}
