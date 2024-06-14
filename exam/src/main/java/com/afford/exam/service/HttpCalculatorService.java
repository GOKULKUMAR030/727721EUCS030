package com.afford.exam.service;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HttpCalculatorService {
    private static final int WINDOW_SIZE = 10;
    private static final String TEST_SERVER_URL = "http://20.244.56.144/numbers/";

    private final RestTemplate restTemplate;
    private final ConcurrentLinkedQueue<Integer> numberWindow;

    public HttpCalculatorService() {
        this.restTemplate = createRestTemplate();
        this.numberWindow = new ConcurrentLinkedQueue<>();
    }

    private RestTemplate createRestTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(500);
        factory.setReadTimeout(500);
        return new RestTemplate(factory);
    }

    public synchronized Response fetchAndCalculate(String numberId) {
        String url = TEST_SERVER_URL + numberId;
        long startTime = System.currentTimeMillis();

        Set<Integer> previousState = new LinkedHashSet<>(numberWindow);
        Set<Integer> newNumbers = new LinkedHashSet<>();

        try {
            ResponseEntity<Integer[]> response = restTemplate.getForEntity(url, Integer[].class);
            if (System.currentTimeMillis() - startTime > 500) {
                throw new Exception("Response took too long");
            }

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                for (Integer number : response.getBody()) {
                    if (numberWindow.size() >= WINDOW_SIZE) {
                        numberWindow.poll();
                    }
                    if (!numberWindow.contains(number)) {
                        numberWindow.add(number);
                        newNumbers.add(number);
                    }
                }
            }

            Set<Integer> currentState = new LinkedHashSet<>(numberWindow);
            double average = numberWindow.stream().mapToInt(Integer::intValue).average().orElse(0.0);

            return new Response(newNumbers, previousState, currentState, average);

        } catch (Exception e) {
            // Handle errors
            return new Response(newNumbers, previousState, previousState, 0);
        }
    }

    public static class Response {
        private final Set<Integer> numbers;
        private final Set<Integer> windowPrevState;
        private final Set<Integer> windowCurrState;
        private final double avg;

        public Response(Set<Integer> numbers, Set<Integer> windowPrevState, Set<Integer> windowCurrState, double avg) {
            this.numbers = numbers;
            this.windowPrevState = windowPrevState;
            this.windowCurrState = windowCurrState;
            this.avg = avg;
        }

        public Set<Integer> getNumbers() {
            return numbers;
        }

        public Set<Integer> getWindowPrevState() {
            return windowPrevState;
        }

        public Set<Integer> getWindowCurrState() {
            return windowCurrState;
        }

        public double getAvg() {
            return avg;
        }
    }
}
