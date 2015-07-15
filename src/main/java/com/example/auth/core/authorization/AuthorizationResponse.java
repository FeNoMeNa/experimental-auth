package com.example.auth.core.authorization;

/**
 * @author Ivan Stefanov <ivan.stefanov@clouway.com>
 */
public class AuthorizationResponse {
  private final String code;
  private final String currentPage;
  private final String redirectURI;

  public AuthorizationResponse(String code, String currentPage, String redirectURI) {
    this.code = code;
    this.currentPage = currentPage;
    this.redirectURI = redirectURI;
  }

  public String buildURI() {
    return redirectURI + "?code=" + code + "&state=" + currentPage;
  }
}