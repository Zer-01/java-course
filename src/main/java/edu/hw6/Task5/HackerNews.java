package edu.hw6.Task5;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HackerNews {
    private final HttpClient httpClient;
    private final static String TOP_STORIES_LINK = "https://hacker-news.firebaseio.com/v0/topstories.json";
    private final static String NEWS_LINK_TEMPLATE = "https://hacker-news.firebaseio.com/v0/item/%d.json";

    public HackerNews() {
        httpClient = HttpClient.newHttpClient();
    }

    public HackerNews(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public long[] hackerNewsTopStories() {
        HttpRequest request;
        try {
            request = HttpRequest.newBuilder()
                .uri(new URI(TOP_STORIES_LINK))
                .GET()
                .build();
        } catch (URISyntaxException e) {
            return new long[0];
        }
        String stringResponse;
        try {
            var response = httpClient
                .send(request, HttpResponse.BodyHandlers.ofString());
            stringResponse = response.body();
        } catch (IOException | InterruptedException e) {
            return new long[0];
        }

        String[] numStrings = stringResponse
            .replaceAll("[\\[\\]]", "")
            .split(",");
        long[] result = new long[numStrings.length];
        for (int i = 0; i < numStrings.length; i++) {
            result[i] = Long.parseLong(numStrings[i]);
        }
        return result;
    }

    public String news(long id) {
        HttpRequest request;
        try {
            request = HttpRequest.newBuilder()
                .uri(new URI(NEWS_LINK_TEMPLATE.formatted(id)))
                .GET()
                .build();
        } catch (URISyntaxException e) {
            return "";
        }

        String stringResponse;
        try {
            var response = httpClient
                .send(request, HttpResponse.BodyHandlers.ofString());
            stringResponse = response.body();
        } catch (IOException | InterruptedException e) {
            return "";
        }
        Pattern pattern = Pattern.compile("\"title\":\"([^\"]+)\"");
        Matcher matcher = pattern.matcher(stringResponse);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return "";
        }
    }
}
