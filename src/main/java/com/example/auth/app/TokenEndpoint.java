package com.example.auth.app;

import com.example.auth.core.token.ProvidedAuthorizationCode;
import com.example.auth.core.token.ProvidedRefreshToken;
import com.example.auth.core.token.Token;
import com.example.auth.core.token.TokenCreator;
import com.example.auth.core.token.TokenErrorResponse;
import com.example.auth.core.token.TokenRequest;
import com.google.inject.Inject;
import com.google.sitebricks.headless.Reply;
import com.google.sitebricks.headless.Request;
import com.google.sitebricks.headless.Service;
import com.google.sitebricks.http.Post;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;

/**
 * @author Ivan Stefanov <ivan.stefanov@clouway.com>
 */
@Service
public class TokenEndpoint {
  private final TokenCreator tokenCreator;

  @Inject
  public TokenEndpoint(
          TokenCreator tokenCreator
  ) {
    this.tokenCreator = tokenCreator;
  }

  @Post
  public Reply<?> generate(Request request) {
    try {
      TokenRequest tokenRequest = read(request);


      Token token = null;

      if ("refresh_token".equals(tokenRequest.grantType)) {

        token = tokenCreator.create(new ProvidedRefreshToken(tokenRequest.refreshToken, tokenRequest.clientId, tokenRequest.clientSecret));

      } else {

        token = tokenCreator.create(new ProvidedAuthorizationCode(tokenRequest.code, tokenRequest.clientId, tokenRequest.clientSecret));

      }

      return Reply.with(adapt(token)).as(Json.class).ok();

    } catch (TokenErrorResponse exception) {

      return Reply.with(adapt(exception)).status(SC_BAD_REQUEST).as(Json.class);

    }
  }

  private TokenRequest read(Request request) {
    String grantType = request.param("grant_type");
    String code = request.param("code");
    String clientId = request.param("client_id");
    String clientSecret = request.param("client_secret");
    String refreshToken = request.param("refresh_token");

    return new TokenRequest(grantType, code, refreshToken, clientId, clientSecret);
  }

  private TokenDTO adapt(Token token) {
    return new TokenDTO(token.value, token.refreshToken, token.type, token.expiresInSeconds);
  }

  private ErrorResponseDTO adapt(TokenErrorResponse exception) {
    return new ErrorResponseDTO(exception.code, exception.description);
  }
}