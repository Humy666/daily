package com.humy.executor.executor;

import java.util.Arrays;
import java.util.concurrent.*;

/**
 * @author Humy
 * @date 2020/9/9 23:30
 */
public class ConcurrentCalculator {

    private ExecutorService pool;

    private ExecutorCompletionService<Integer[]> completionService;

    private int availableProcessors;

    /**
     * 获取可用的处理器数量，并创建该数量线程池
     */
    public ConcurrentCalculator() {
        availableProcessors = getAvailableProcessors();
        this.pool = Executors.newFixedThreadPool(availableProcessors);
        this.completionService = new ExecutorCompletionService<>(pool);
    }

    private int getAvailableProcessors() {
        return Runtime.getRuntime().availableProcessors();
    }

    /**
     * 获取10000个随机数中top 100的数。
     *
     * @param values
     * @return
     */
    public Integer[] getTop100(int[] values) {

        // 用10个线程，每个线程处理1000个数
        for (int i = 0; i < 10; i++) {
            completionService.submit(new TaskForEveryThread(values, i * 1000, i * 1000 + 1000 - 1));
        }
        shutdown();
        return populateTop100();
    }

    private Integer[] populateTop100() {
        // 初始化top100 的数组，并且都赋值为零；
        Integer[] top100 = new Integer[100];
        for (int i = 0; i < 100; i++) {
            top100[i] = 0;
        }

        for (int i = 0; i < 10; i++) {
            try {
                Future<Integer[]> future = completionService.take();
                comopareAndGetTop100(top100, future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        return top100;
    }

    private void comopareAndGetTop100(Integer[] top100, Integer[] currentTop100) {

        Integer[] top200 = new Integer[200];
        System.arraycopy(top100, 0, top200, 0, 100);
        System.arraycopy(currentTop100, 0, top200, 100, 100);

        Arrays.sort(top200);
        for (int i = 0; i < 100; i++) {
            top100[i] = top200[top200.length - i - 1];
        }


    }


    private void shutdown() {
        pool.shutdown();
    }

}
