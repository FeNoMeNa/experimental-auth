package com.example.auth.app;

import com.google.common.collect.Multimap;
import com.google.inject.TypeLiteral;
import com.google.sitebricks.headless.Request;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.io.BaseEncoding.base64;

/**
* @author Miroslav Genov (mgenov@gmail.com)
*/
class RequestBuilder {
  private Map<String, String> parameters = newHashMap();
  private Map<String, String> headers = newHashMap();

  static RequestBuilder newRequest() {
    return new RequestBuilder();
  }

  public RequestBuilder withParameters(Map<String, String> parameters) {
    this.parameters = parameters;
    return this;
  }

  public RequestBuilder auth(String username, String password) {
    String credentials = base64().encode((username + ":" + password).getBytes());
    headers.put("Authorization", "Basic " + credentials);
    return this;
  }

  public Request build() {
    return new FakeRequest(parameters, headers);
  }

  private class FakeRequest implements Request {
    private Map<String, String> parameters;
    private Map<String, String> headers;

    public FakeRequest(Map<String, String> parameters, Map<String, String> headers) {
      this.parameters = parameters;
      this.headers = headers;
    }

    @Override
    public <E> RequestRead<E> read(Class<E> type) {
      return null;
    }

    @Override
    public <E> RequestRead<E> read(TypeLiteral<E> type) {
      return null;
    }

    @Override
    public void readTo(OutputStream out) throws IOException {

    }

    @Override
    public Multimap<String, String> headers() {
      return null;
    }

    @Override
    public Multimap<String, String> params() {
      return null;
    }

    @Override
    public Multimap<String, String> matrix() {
      return null;
    }

    @Override
    public String matrixParam(String name) {
      return null;
    }

    @Override
    public String param(String name) {
      return parameters.get(name);
    }

    @Override
    public String header(String name) {
      return headers.get(name);
    }

    @Override
    public String uri() {
      return null;
    }

    @Override
    public String path() {
      return null;
    }

    @Override
    public String context() {
      return null;
    }

    @Override
    public String method() {
      return null;
    }
  }
}