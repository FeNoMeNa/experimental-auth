/*
 * Created by IntelliJ IDEA.
 * User: mlesikov
 * Date: 7/22/14
 * Time: 2:00 PM
 */
package com.example.auth.app;

import com.example.auth.app.security.SecurityModule;
import com.example.auth.core.CoreModule;
import com.example.auth.core.Duration;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class OAuthAuthorizationServerModule extends AbstractModule {

  private String url = "";
  private String loginPagePath = "";
  private Duration tokenTimeToLive;
  private Boolean generatesNewRefreshToken = false;

  /**
   * constructs the module
   *
   * @param url
   * @param tokenTimeToLive in seconds
   */
  public OAuthAuthorizationServerModule(String url, Long tokenTimeToLive) {
    this.url = url;
    this.tokenTimeToLive = Duration.seconds(tokenTimeToLive);
  }

  public OAuthAuthorizationServerModule(String url, String loginPagePath, Long tokenTimeToLive) {
    this.url = url;
    this.loginPagePath = loginPagePath;
    this.tokenTimeToLive = Duration.seconds(tokenTimeToLive);
  }

  /**
   * constructs the module
   *
   * @param url
   * @param tokenTimeToLive in seconds
   */
  public OAuthAuthorizationServerModule(String url, Long tokenTimeToLive, Boolean generatesNewRefreshToken) {
    this.url = url;
    this.generatesNewRefreshToken = generatesNewRefreshToken;
    this.tokenTimeToLive = Duration.seconds(tokenTimeToLive);
  }

  /**
   * constructs the module
   *
   * @param url
   * @param tokenTimeToLive in seconds
   */
  public OAuthAuthorizationServerModule(String url, String loginPagePath, Long tokenTimeToLive, Boolean generatesNewRefreshToken) {
    this.url = url;
    this.loginPagePath = loginPagePath;
    this.generatesNewRefreshToken = generatesNewRefreshToken;
    this.tokenTimeToLive = Duration.seconds(tokenTimeToLive);
  }


  protected void configure() {

    install(new CoreModule());
    install(new SecurityModule(url, loginPagePath));
//    install(new SitebricksModule() {
//      @Override
//      protected void configureSitebricks() {
//        at(url + "/register").serve(RegistrationEndpoint.class);
//        at(url + "/authorize").serve(AuthorizationEndpoint.class);
//        at(url + "/login").show(LoginEndpoint.class);
//        at(url + "/token").serve(TokenEndpoint.class);
//        at(url + "/verify/:token").serve(VerificationEndpoint.class);
//        at(url + "/userInfo/:token").serve(UserInfoEndPoint.class);
//      }
//    });
  }


  @Provides
  @TokenTimeToLive
  public Duration getTokenLive() {
    return tokenTimeToLive;
  }

  @Provides
  @GenerateNewRefreshToken
  public Boolean getGeneratesNewRefreshToken() {
    return generatesNewRefreshToken;
  }
}
