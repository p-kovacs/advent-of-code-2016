package pkovacs.aoc.alg;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Implements a general shortest path algorithm, which is a more efficient version of the classic Bellman-Ford
 * algorithm. Namely, it is the
 * <a href="https://en.wikipedia.org/wiki/Shortest_Path_Faster_Algorithm">SPFA algorithm</a>.
 * <p>
 * The input is an "implicit" directed graph with long integer edge weights (defined by an edge provider function) and
 * one or more source nodes. The edge provider function provides a collection of (node, weight) pairs for each node,
 * describing the outgoing directed edges of the node. An optional target predicate can also be specified in order to
 * get path result for a single node instead of all nodes. However, it does not make the search process faster.
 * The predicate can also accept multiple target nodes, in which case a {@link PathResult} for one of them will be
 * provided.
 * <p>
 * The algorithm also supports negative edge weights, but the graph must not contain a directed cycle with negative
 * total weight. The current implementation might not terminate for such input!
 *
 * @see Bfs
 */
public final class ShortestPath {

    /**
     * Represents an outgoing directed edge of a node being evaluated (expanded) by this algorithm. It is a record
     * containing the endpoint (target node) and the weight of the edge.
     */
    public static record Edge<T>(T node, long weight) {

        public static <T> Edge<T> of(T node, long weight) {
            return new Edge<T>(node, weight);
        }

    }

    private ShortestPath() {
    }

    public static <T> Optional<PathResult<T>> run(T source,
            Function<T, Iterable<Edge<T>>> edgeProvider,
            Predicate<T> targetPredicate) {
        var map = run(Collections.singleton(source), edgeProvider, targetPredicate);
        return map.values().stream().filter(PathResult::isTarget).findFirst();
    }

    public static <T> Map<T, PathResult<T>> run(T source,
            Function<T, Iterable<Edge<T>>> edgeProvider) {
        return run(Collections.singleton(source), edgeProvider, n -> false);
    }

    public static <T> Map<T, PathResult<T>> run(Iterable<T> sources,
            Function<T, Iterable<Edge<T>>> edgeProvider,
            Predicate<T> targetPredicate) {
        var results = new HashMap<T, PathResult<T>>();

        var queue = new ArrayDeque<T>();
        for (T source : sources) {
            results.put(source, new PathResult<>(source, 0, targetPredicate.test(source), null));
            queue.add(source);
        }

        while (!queue.isEmpty()) {
            T node = queue.poll();
            var result = results.get(node);
            for (var edge : edgeProvider.apply(node)) {
                var neighbor = edge.node();
                var dist = result.getDist() + edge.weight();
                var current = results.get(neighbor);
                if (current == null || dist < current.getDist()) {
                    results.put(neighbor, new PathResult<>(neighbor, dist, targetPredicate.test(neighbor), result));
                    queue.add(neighbor);
                }
            }
        }

        return results;
    }

}
