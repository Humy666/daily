package com.humy.executor.executor;

import java.io.*;
import java.util.Random;

/**
 * 生产随机数
 *
 * @author Humy
 * @date 2020/9/6 18:37
 */
public class RandomNumUtil {

    //随机数范围：0-10000，包含0，不包含10000
    private static final int RANGE = 10000;

    private static final int SIZE = 10000;

    /**
     * 生成随机数，并写进文件里
     * @param filePath 文件路径
     */
    public static void generateRandomNums(String filePath) {
        Random random = new Random();
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(new File(filePath)));
            for (int i = 0; i < SIZE; i++) {
                int num = random.nextInt(RANGE);
                bw.write( (i + 1) + "=" + num);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != bw) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    bw = null;
                }
            }
        }

    }

    /**
     * 从指定文件获取已经生成的随机数集合
     *
     * @param filePath filePath
     * @return
     */
    public static int[] getNumFromFile(String filePath) {
        BufferedReader br = null;
        int[] nums = new int[SIZE];

        try {
            String line;
            int index = 0;
            br = new BufferedReader(new FileReader(new File(filePath)));
            while (null != (line = br.readLine())) {
                nums[index] = Integer.parseInt(line.substring(line.indexOf("=") + 1));
                index++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    br = null;
                }
            }
        }
        return nums;
    }

}
