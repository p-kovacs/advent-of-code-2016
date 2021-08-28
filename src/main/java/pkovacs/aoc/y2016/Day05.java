package pkovacs.aoc.y2016;

import java.nio.charset.StandardCharsets;

import com.google.common.hash.Hashing;
import pkovacs.aoc.util.InputUtils;

public class Day05 {

    public static void main(String[] args) {
        var lines = InputUtils.readLines("y2016/day05.txt");
        var id = lines.get(0);

        int i = 0;
        var pwd1 = new StringBuilder();
        var pwd2 = new char[] { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' };
        int pwd2Cnt = 0;
        while (pwd2Cnt < 8) {
            var hash = getMd5Hash(id + i);
            if (hash.startsWith("00000")) {
                char a = hash.charAt(5);
                char b = hash.charAt(6);
                if (pwd1.length() < 8) {
                    pwd1.append(a);
                }
                if (a >= '0' && a < '8' && pwd2[a - '0'] == ' ') {
                    pwd2[a - '0'] = b;
                    pwd2Cnt++;
                }
            }
            i++;
        }

        System.out.println("Part 1: " + pwd1.toString());
        System.out.println("Part 2: " + new String(pwd2));
    }

    @SuppressWarnings({ "deprecated", "UnstableApiUsage" })
    private static String getMd5Hash(String s) {
        return Hashing.md5().hashString(s, StandardCharsets.UTF_8).toString();
    }

}
