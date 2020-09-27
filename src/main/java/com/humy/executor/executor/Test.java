package com.humy.executor.executor;

import org.springframework.util.StopWatch;

/**
 * @author Humy
 * @date 2020/9/12 16:06
 */
public class Test {

    private static final String FILE_PATH = "F:\\GitHub\\Test\\executors.txt";

    public static void main(String[] args) {
        test();
    }

    private static void test() {

        generateRandomNbrs();

        process1();

    }

    private static void generateRandomNbrs() {
        RandomNumUtil.generateRandomNums(FILE_PATH);
    }

    private static void process1() {
        StopWatch watch = new StopWatch();
        watch.start();

        System.out.println("使用ExecutorCompletionService获取top 100");
        ConcurrentCalculator calculator = new ConcurrentCalculator();
        Integer[] top100 = calculator.getTop100(RandomNumUtil.getNumFromFile(FILE_PATH));

        for (int i = 0; i < 100; i++) {
            System.out.println(String.format("top%d = %d", i + 1, top100[i]));
        }
        watch.stop();
        System.out.println(watch.prettyPrint());

    }


}
