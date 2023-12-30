package edu.hw6;

import edu.hw6.Task5.HackerNews;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class Task5Test {

    @Test
    void topStoriesTest() throws IOException, InterruptedException {
        HttpClient httpMock = mock(HttpClient.class);
        HttpResponse<String> httpResponse = mock(HttpResponse.class);

        String mockResponse = "[38347868,38349716,38346862,38342670,38344973,38332226,38343385,38332585,38348968]";
        when(httpResponse.body()).thenReturn(mockResponse);

        when(httpMock.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
            .thenReturn(httpResponse);

        HackerNews hackerNews = new HackerNews(httpMock);
        long[] expResult = {38347868, 38349716, 38346862, 38342670, 38344973, 38332226, 38343385, 38332585, 38348968};

        long[] result = hackerNews.hackerNewsTopStories();

        assertThat(result)
            .containsExactlyInAnyOrder(expResult);
    }

    @Test
    void topStoriesIOErrorTest() throws IOException, InterruptedException {
        HttpClient httpMock = mock(HttpClient.class);

        when(httpMock.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
            .thenThrow(new IOException());

        HackerNews hackerNews = new HackerNews(httpMock);

        long[] result = hackerNews.hackerNewsTopStories();

        assertThat(result)
            .isEmpty();
    }

    @Test
    void newsTest() throws IOException, InterruptedException {
        HttpClient httpMock = mock(HttpClient.class);
        HttpResponse<String> httpResponse = mock(HttpResponse.class);

        String mockResponse =
            "{\"by\":\"mfiguiere\",\"descendants\":327,\"id\":37570037,\"kids\":[37571340,37570526,37570764,37572319,"
                + "37570601,37570723,37570473,37570974,37570942,37570499,37570993,37571050,37571247,37570385,37571268,"
                + "7570771,37570699,37572964,37570459,37571905,37571462,37607483,37571028,37570905,37570766,37571188,"
                + "37570620],\"score\":246,\"time\":1695132006,\"title\":\"JDK 21 Release Notes\",\"type\":\"story\","
                + "\"url\":\"https://jdk.java.net/21/release-notes\"}";
        when(httpResponse.body()).thenReturn(mockResponse);

        when(httpMock.send(Mockito.argThat(arg -> arg.uri().toString()
            .equals("https://hacker-news.firebaseio.com/v0/item/37570037.json")), any(HttpResponse.BodyHandler.class)))
            .thenReturn(httpResponse);

        HackerNews hackerNews = new HackerNews(httpMock);
        String expResult = "JDK 21 Release Notes";

        String result = hackerNews.news(37570037);

        assertThat(result)
            .isEqualTo(expResult);
    }

    @Test
    void newsIOErrorTest() throws IOException, InterruptedException {
        HttpClient httpMock = mock(HttpClient.class);

        when(httpMock.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
            .thenThrow(new IOException());

        HackerNews hackerNews = new HackerNews(httpMock);

        String result = hackerNews.news(123);

        assertThat(result)
            .isEmpty();
    }
}
