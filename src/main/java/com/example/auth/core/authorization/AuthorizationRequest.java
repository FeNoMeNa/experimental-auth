package com.example.auth.core.authorization;

/**
 * @author Ivan Stefanov <ivan.stefanov@clouway.com>
 */
public class AuthorizationRequest {
  public final String responseType;
  public final String clientId;
  public final String currentPage;
  public final String sessionId;

  public AuthorizationRequest(String responseType, String clientId, String currentPage, String sessionId) {
    this.responseType = responseType;
    this.clientId = clientId;
    this.currentPage = currentPage;
    this.sessionId = sessionId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof AuthorizationRequest)) return false;

    AuthorizationRequest that = (AuthorizationRequest) o;

    if (responseType != null ? !responseType.equals(that.responseType) : that.responseType != null) return false;
    if (clientId != null ? !clientId.equals(that.clientId) : that.clientId != null) return false;
    if (sessionId != null ? !sessionId.equals(that.sessionId) : that.sessionId != null) return false;
    return !(currentPage != null ? !currentPage.equals(that.currentPage) : that.currentPage != null);

  }

  @Override
  public int hashCode() {
    int result = responseType != null ? responseType.hashCode() : 0;
    result = 31 * result + (clientId != null ? clientId.hashCode() : 0);
    result = 31 * result + (sessionId != null ? sessionId.hashCode() : 0);
    result = 31 * result + (currentPage != null ? currentPage.hashCode() : 0);
    return result;
  }
}