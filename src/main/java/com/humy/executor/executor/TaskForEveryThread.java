package com.humy.executor.executor;

import java.util.Arrays;
import java.util.concurrent.Callable;

/**
 *  实现Callable接口，计算指定数据集范围内的top 100.
 *
 * @author Humy
 * @date 2020/9/6 18:39
 */
public class TaskForEveryThread implements Callable<Integer[]> {


    //待处理的数据
    private int[] values;

    //起始索引
    private int startIndex;

    //结束索引
    private int endIndex;

    public TaskForEveryThread(int[] values, int startIndex, int endIndex) {
        this.values = values;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    @Override
    public Integer[] call() throws Exception {

        //获取指定下标区间内的数组元素
        int[] subValues = new int[endIndex - startIndex + 1];
        System.arraycopy(values, startIndex, subValues, 0, endIndex - startIndex + 1);
        Arrays.sort(subValues);

        //将排序后的数组，取出top100
        Integer[] top100 = new Integer[100];
        for (int i = 0; i < 100; i++) {
            top100[i] = subValues[subValues.length - i - 1];
        }

        return top100;
    }

    public int[] getValues() {
        return values;
    }

    public void setValues(int[] values) {
        this.values = values;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }
}
