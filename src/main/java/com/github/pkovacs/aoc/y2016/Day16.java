package com.github.pkovacs.aoc.y2016;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.github.pkovacs.util.InputUtils;

public class Day16 {

    public static void main(String[] args) {
        var line = InputUtils.readLines("day16.txt").get(0);

        byte[] input = new byte[line.length()];
        for (int i = 0; i < input.length; i++) {
            input[i] = (byte) (line.charAt(i) == '1' ? 1 : 0);
        }

        System.out.println("Part 1: " + solve(input, 272));
        System.out.println("Part 2: " + solve(input, 35651584)); // 01101100001100100
    }

    private static String solve(byte[] input, int size) {
        byte[] data = input.clone();
        while (data.length < size) {
            data = expand(data);
        }
        data = Arrays.copyOf(data, size);

        byte[] checksum = data.clone();
        while (checksum.length % 2 == 0) {
            checksum = getChecksum(checksum);
        }

        byte[] ch = checksum;
        return IntStream.range(0, input.length)
                .mapToObj(i -> String.valueOf(ch[i]))
                .collect(Collectors.joining());
    }

    private static byte[] expand(byte[] a) {
        byte[] result = Arrays.copyOf(a, a.length + 1 + a.length);
        for (int i = 0, j = result.length - 1; i < a.length; i++, j--) {
            result[j] = (byte) (a[i] ^ 1);
        }
        return result;
    }

    private static byte[] getChecksum(byte[] a) {
        byte[] result = new byte[a.length / 2];
        for (int i = 0, j = 0; i < a.length; i += 2, j++) {
            result[j] = (byte) (a[i] ^ a[i + 1] ^ 1);
        }
        return result;
    }

}
