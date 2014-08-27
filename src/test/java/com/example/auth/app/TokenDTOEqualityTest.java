package com.example.auth.app;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

/**
 * @author Ivan Stefanov <ivan.stefanov@clouway.com>
 */
public class TokenDTOEqualityTest {
  @Test
  public void areEqual() {
    TokenDTO token1 = new TokenDTO("value", "refresh", "type", 1l);
    TokenDTO token2 = new TokenDTO("value", "refresh", "type", 1l);

    assertThat(token1, is(token2));
  }

  @Test
  public void areNotEqual() {
    TokenDTO token1 = new TokenDTO("value1", "refresh", "type1", 1l);
    TokenDTO token2 = new TokenDTO("value2", "refresh", "type2", 1l);

    assertThat(token1, is(not(token2)));
  }
}