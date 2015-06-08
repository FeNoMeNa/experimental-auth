package com.example.auth.app;

import com.example.auth.core.token.ProvidedAuthorizationCode;
import com.example.auth.core.token.ProvidedRefreshToken;
import com.example.auth.core.token.Token;
import com.example.auth.core.token.TokenCreator;
import com.example.auth.core.token.TokenRequest;
import com.google.common.collect.ImmutableMap;
import com.google.sitebricks.headless.Reply;
import com.google.sitebricks.headless.Request;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Date;

import static com.example.auth.app.ReplyMatchers.containsValue;
import static com.example.auth.app.ReplyMatchers.isOk;
import static com.example.auth.app.RequestBuilder.newRequest;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Ivan Stefanov <ivan.stefanov@clouway.com>
 */
public class TokenEndpointTest {
  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  @Mock
  private TokenCreator tokenCreator;

  private TokenEndpoint endpoint;

  @Before
  public void setUp() throws Exception {
    endpoint = new TokenEndpoint(tokenCreator);
  }

  @Test
  public void generatingToken() throws Exception {
    final TokenRequest tokenRequest = new TokenRequest("grant_type", "code", "refresh_token", "client_id", "client_secret");
    final Token token = new Token("token_value", "token_type", "refresh_token_value", "userId", 0l, new Date());
    final TokenDTO tokenDTO = new TokenDTO("token_value", "refresh_value", "token_type", 0l);
    final Request request = newRequest().auth("client_id", "client_secret").withParameters(ImmutableMap.of("grant_type", "grant_type", "code", "code", "refresh_token", "refresh_token")).build();

    context.checking(new Expectations() {{
      oneOf(tokenCreator).create(new ProvidedAuthorizationCode(tokenRequest.code, tokenRequest.clientId, tokenRequest.clientSecret));
      will(returnValue(token));
    }});

    Reply<?> reply = endpoint.generate(request);

    assertThat(reply, isOk());
    assertThat(reply, containsValue(tokenDTO));
  }

  @Test
  public void generatingTokenUsingRefreshToken() throws Exception {
    final TokenRequest tokenRequest = new TokenRequest("refresh_token", "code", "refresh_token_value", "client_id", "client_secret");
    final Token token = new Token("token_value", "token_type", "refresh_token_value", "userId", 0l, new Date());
    final TokenDTO tokenDTO = new TokenDTO("token_value", "refresh_value", "token_type", 0l);
    final Request request = newRequest().auth("client_id", "client_secret").withParameters(ImmutableMap.of("grant_type", "refresh_token", "code", "code", "refresh_token", "refresh_token_value")).build();

    context.checking(new Expectations() {{
      oneOf(tokenCreator).create(new ProvidedRefreshToken(tokenRequest.refreshToken, tokenRequest.clientId, tokenRequest.clientSecret));
      will(returnValue(token));
    }});

    Reply<?> reply = endpoint.generate(request);

    assertThat(reply, isOk());
    assertThat(reply, containsValue(tokenDTO));
  }
}