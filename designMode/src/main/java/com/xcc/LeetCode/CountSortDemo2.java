package com.xcc.LeetCode;

import java.util.Arrays;
import java.util.Collections;

/**
 * 计数排序的优化
 * 之前写的计数排序有个小问题的，就是以最大值加1设置数组长度。
 * 这样的问题在于要是数组本身就很大，但是最小值也很大，就会白白浪费空间；例如：
 * [99,90,97,92,98,91,93,94]
 * 这个数组中最大值是99，但是最小值是90，要是按之前的逻辑，设置的数组前面的空间不是白白浪费了。
 * 所以可以这样优化一下，不在以最大值加1设置数组大小，而是用最大值-最小值+1设置数组的大小。
 *
 * {95,94,91,98,99,90,99,93,91,92}
 *
 *                      95
 * 0    0   0   0   0   1   0   0   0   0
 * 0    1   2   3   4   5   6   7   8   9
 *
 * 但在实际业务里，例如给学生的考试分数进行排序，遇到相同的分数就会分不清谁是谁。
 * 姓名       成绩
 * 小明       90
 * 小白       99
 * 小红       95
 * 小黑       94
 * 小蓝       95
 *
 * 我们用计数排序得出了统计数组，但是不知道那个是小蓝，那个是小红
 *
 * 1    0   0   0   1   2   0   0   0   1
 * 0    1   2   3   4   5   6   7   8   9
 *
 * 为了解决这样的事情，我们需要将统计数组变一下形
 *
 * 1    0   0   0   1   2   0   0   0   1
 * 0    1   2   3   4   5   6   7   8   9
 *
 * 1    0+1 0+1 0+1 1+1 2+2 0+4 0+4 0+4 1+4
 * 1    1   1   1   2   4   4   4   4   5
 * 0    1   2   3   4   5   6   7   8   9
 *
 * 这样相加的目的，是让统计数组存储的元素值，等于相应整数的最终排序位置的序号。
 * 例如下标是9的元素值为5，代表原始数列的整数9，最终的排序在第5位。
 * 接下来，创建输出数组sortedArray，长度和输入数列一致。然后从后向前遍历输入数列。
 *
 * 第1步，遍历成绩表最后一行的小蓝同学的 成绩。
 * 小蓝的成绩是95，找到countArray下标是5的元素，值是4，代表小蓝的成绩排名位置在第4位。
 *
 * 同时，给countArray下标是5得到元素值减1，从4变成3，代表下次在遇到95的成绩时，最终排名是第3。
 *
 * countArray
 *                      4-1
 * 1    1   1   1   2   4   4   4   4   5
 * 0    1   2   3   4   5   6   7   8   9
 *
 * sortedArray
 *              小蓝
 *              95
 * 0    1   2   3   4
 *
 * 第2步，遍历成绩表倒数第2行的小黑同学的成绩。
 * 小黑的成绩是94，找到countArray下标是4的元素，值是2，代表小黑的成绩排名位置在第2位。
 * 同时，给countArray下标是4的元素值减1，从2变成1，代表下次在遇到94的成绩时，最终排名是第1。
 *
 * countArray
 *                  2-1
 * 1    1   1   1   2   3   4   4   4   5
 * 0    1   2   3   4   5   6   7   8   9
 *
 * sortedArray
 *      小黑     小蓝
 *      94      95
 * 0    1   2   3   4
 *
 * 第3步，遍历成绩表倒数第3行的小红同学的成绩。
 * 小红的成绩是95，找到countArray下标是5的元素，值是3（最初是4，减1变成了3），代表小红
 * 的成绩排名位置在第3位。
 * 同时，给count Array下标是5的元素值减1，从3变成2，代表下次在遇到95的成绩时，最终排名是第2。
 *
 * countArray
 *                      3-1
 * 1    1   1   1   2   3   4   4   4   5
 * 0    1   2   3   4   5   6   7   8   9
 *
 * sortedArray
 *      小黑 小红 小蓝
 *      94  95  95
 * 0    1   2   3   4
 *
 * 按这样的逻辑最后得到的sortArray就是这样的：
 *
 * sortedArray
 * 小明 小黑 小红 小蓝 小白
 * 90   94  95  95  99
 * 0    1   2   3   4
 *
 * 这样一来，同样是95的小红和小蓝就能够清楚地排出顺序了，也正因为此，优化版本的计数排序属于稳定排序。
 *
 */
public class CountSortDemo2 {

    /**
     * 计数排序的优化
     *
     * @param array
     * @return
     */
    public static int[] countSort(int[] array) {
        // 1.得到数列的最大值和最小值，并算出差值d
        int max = array[0];
        int min = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }

            if (array[i] < min) {
                min = array[i];
            }
        }
        int d = max - min;
        // 2.创建统计数组并统计对应元素的个数
        int[] countArray = new int[d + 1];
        for (int i = 0; i < array.length; i++) {
            countArray[array[i] - min]++;
        }
        // 3.统计数组做变形，后面的元素等于前面的元素之和
        for (int i = 1; i < countArray.length; i++) {
            countArray[i] += countArray[i-1];
        }
        // 4.倒序遍历原始数列，从统计数组找到正确位置，输出到结果数组
        int[] sortedArray = new int[array.length];
        for (int i = array.length - 1; i >= 0; i--) {
            sortedArray[countArray[array[i]-min] - 1] = array[i];
            countArray[array[i] - min]--;
        }

        return sortedArray;
    }

    public static void main(String[] args) {
        int[] array = new int[]{95,94,91,98,99,90,99,93,91,92};
        int[] sortedArray = countSort(array);
        System.out.println(Arrays.toString(sortedArray));
    }

    /** 小结
     * 假设原始数列的规模是n，最大和最小整数的差值是m，优化版的计数排序：
     * 代码第1，2，4步都涉及遍历原始数列，运算量都是n，第3步遍历统计数列，运算量是m，所以总体运算量是3n+m，
     * 去掉系数，时间复杂度：O(n+m)
     *  空间复杂度是O(m)
     *
     *  计数排序虽然强，但是计数排序也有明显的局限性，主要表现在这两点上：
     *  1.当数列最大和最小值差距过大时，并步适合用计数排序。
     *  例如给出20哥随机整数，范围在0到10亿之间，这个时候用计数排序，需要创建长度位10亿的数组。
     *  步光浪费空间，而且时间复杂度也会升高。
     *
     *  2.当数列元素不是整数时，也步适合计数排序。
     *  如果数列中的元素都是小数，入3.14或1.414这样数字,则无法创建对应的统计数组。这样也就无法进行计数排序。
     *
     *  为了解决这些局限性，另一种线性时间排序算法做出了弥补，这种算法叫作桶排序。
     */

}
