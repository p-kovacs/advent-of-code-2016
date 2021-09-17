package pkovacs.aoc.y2016;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pkovacs.aoc.util.InputUtils;
import pkovacs.aoc.util.Tile;

public class Day02 {

    public static void main(String[] args) {
        var lines = InputUtils.readLines("y2016/day02.txt");

        System.out.println("Part 1: " + solve(initBoard1(), lines));
        System.out.println("Part 2: " + solve(initBoard2(), lines));
    }

    private static String solve(Map<Tile, Character> board, List<String> lines) {
        Tile pos = board.entrySet().stream()
                .filter(e -> e.getValue().equals('5'))
                .findFirst()
                .get().getKey();

        var code = new StringBuilder();
        for (String line : lines) {
            for (int i = 0; i < line.length(); i++) {
                char ch = line.charAt(i);
                Tile newPos = null;
                if (ch == 'U') {
                    newPos = new Tile(pos.row() - 1, pos.col());
                } else if (ch == 'D') {
                    newPos = new Tile(pos.row() + 1, pos.col());
                } else if (ch == 'L') {
                    newPos = new Tile(pos.row(), pos.col() - 1);
                } else if (ch == 'R') {
                    newPos = new Tile(pos.row(), pos.col() + 1);
                }
                if (board.containsKey(newPos)) {
                    pos = newPos;
                }
            }
            code.append(board.get(pos));
        }
        return code.toString();
    }

    private static Map<Tile, Character> initBoard1() {
        var board = new HashMap<Tile, Character>();
        board.put(new Tile(0, 0), '1');
        board.put(new Tile(0, 1), '2');
        board.put(new Tile(0, 2), '3');
        board.put(new Tile(1, 0), '4');
        board.put(new Tile(1, 1), '5');
        board.put(new Tile(1, 2), '6');
        board.put(new Tile(2, 0), '7');
        board.put(new Tile(2, 1), '8');
        board.put(new Tile(2, 2), '9');
        return board;
    }

    private static Map<Tile, Character> initBoard2() {
        Map<Tile, Character> board = new HashMap<>();
        board.put(new Tile(0, 2), '1');
        board.put(new Tile(1, 1), '2');
        board.put(new Tile(1, 2), '3');
        board.put(new Tile(1, 3), '4');
        board.put(new Tile(2, 0), '5');
        board.put(new Tile(2, 1), '6');
        board.put(new Tile(2, 2), '7');
        board.put(new Tile(2, 3), '8');
        board.put(new Tile(2, 4), '9');
        board.put(new Tile(3, 1), 'A');
        board.put(new Tile(3, 2), 'B');
        board.put(new Tile(3, 3), 'C');
        board.put(new Tile(4, 2), 'D');
        return board;
    }

}
