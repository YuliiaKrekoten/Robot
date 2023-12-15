package netology;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Main {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();
    public static void main(String[] args) throws InterruptedException{




                int numThreads = 1000;
                Thread[] threads = new Thread[numThreads];
                for (int i = 0; i < numThreads; i++) {
                    threads[i] = new Thread(new RouteGenerator());
                    threads[i].start();
                }

                for (int i = 0; i < numThreads; i++) {
                    threads[i].join();
                }

                printResult();
            }

            public static void updateMap(int size) {
                synchronized (sizeToFreq) {
                    sizeToFreq.put(size, sizeToFreq.getOrDefault(size, 0) + 1);
                }
            }

            public static void printResult() {
                int maxFreq = 0;
                Map<Integer, Integer> otherSizes = new HashMap<>();

                synchronized (sizeToFreq) {
                    for (int size : sizeToFreq.keySet()) {
                        int freq = sizeToFreq.get(size);
                        if (freq > maxFreq) {
                            maxFreq = freq;
                        } else {
                            otherSizes.put(size, freq);
                        }
                    }
                }

                System.out.println("Самое частое количество повторений " + maxFreq + " (встретилось " + maxFreq + " раз)");
                System.out.println("Другие размеры:");
                for (int size : otherSizes.keySet()) {
                    System.out.println("- " + size + " (" + otherSizes.get(size) + " раз)");
                }
            }

            public static class RouteGenerator implements Runnable {
                private static final String letters = "RLRFR";
                private static final int length = 100;

                @Override
                public void run() {
                    String route = generateRoute(letters, length);
                    int count = countRightTurns(route);
                    updateMap(count);
                }

                public static String generateRoute(String letters, int length) {
                    Random random = new Random();
                    StringBuilder route = new StringBuilder();
                    for (int i = 0; i < length; i++) {
                        route.append(letters.charAt(random.nextInt(letters.length())));
                    }
                    return route.toString();
                }

                public static int countRightTurns(String route) {
                    int count = 0;
                    for (char c : route.toCharArray()) {
                        if (c == 'R') {
                            count++;
                        }
                    }
                    return count;
                }
            }
        }






