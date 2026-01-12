package com.pjw.tickgettinig.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pjw.tickgettinig.common.exceptions.BusinessException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpUtil {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  public static UrlConnectionBuilder urlConnectionBuilder() {
    return new UrlConnectionBuilder();
  }

  public static class UrlConnectionBuilder {

    private String url;
    private String method = "GET";
    private Map<String, String> headers = new HashMap<>();
    private Object body;
    private MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();

    public UrlConnectionBuilder url(String url) {
      this.url = url;
      return this;
    }

    public UrlConnectionBuilder method(String method) {
      this.method = method.toUpperCase();
      return this;
    }

    public UrlConnectionBuilder header(String name, String value) {
      this.headers.put(name, value);
      return this;
    }

    public UrlConnectionBuilder body(Object body) {
      this.body = body;
      return this;
    }

    public UrlConnectionBuilder param(String name, String value) {
      this.queryParams.add(name, value);
      return this;
    }

    public UrlConnectionBuilder params(Map<String, String> params) {
      this.queryParams.setAll(params);
      return this;
    }

    public <T> T exchange(Class<T> responseType) {
      try {
        StringBuilder requestUrl = new StringBuilder(this.url);
        if (!queryParams.isEmpty()) {
          requestUrl.append("?");
          String queryString = queryParams.entrySet().stream()
              .map(entry -> entry.getKey() + "=" + entry.getValue().get(0))
              .collect(Collectors.joining("&"));
          requestUrl.append(queryString);
        }

        URL urlObj = new URL(requestUrl.toString());
        HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();

        conn.setRequestMethod(method);
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);

        if (!headers.containsKey("Content-Type") && body != null) {
          headers.put("Content-Type", "application/json");
        }

        headers.forEach(conn::setRequestProperty);
        conn.setDoOutput(body != null);

        if (body != null) {
          try (OutputStream os = conn.getOutputStream()) {
            String jsonBody = body instanceof String ? (String) body : objectMapper.writeValueAsString(body);
            byte[] input = jsonBody.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
          }
        }

        int responseCode = conn.getResponseCode();

        try (BufferedReader br = new BufferedReader(
            new InputStreamReader(
                responseCode >= 200 && responseCode < 300 ? conn.getInputStream() : conn.getErrorStream(),
                StandardCharsets.UTF_8))) {

          String responseBody = br.lines().collect(Collectors.joining("\n"));

          return objectMapper.readValue(responseBody, responseType);
        }
      } catch (Exception e) {
        throw new BusinessException("HttpURLConnection request failed", e);
      }
    }
  }
}
