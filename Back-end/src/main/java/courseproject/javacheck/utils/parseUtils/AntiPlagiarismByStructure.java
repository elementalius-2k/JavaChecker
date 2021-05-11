package courseproject.javacheck.utils.parseUtils;

import courseproject.javacheck.model.elasticsearchModels.Work;
import org.jgrapht.Graph;
import org.jgrapht.alg.isomorphism.IsomorphismInspector;
import org.jgrapht.alg.isomorphism.IsomorphismUndecidableException;
import org.jgrapht.alg.isomorphism.VF2GraphIsomorphismInspector;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.nio.ExportException;
import org.jgrapht.nio.ImportException;
import org.jgrapht.nio.json.JSONExporter;
import org.jgrapht.nio.json.JSONImporter;


import java.io.*;
import java.util.*;
import java.util.function.Function;

public class AntiPlagiarismByStructure {
    /**
     * Get JSON structure of class graph from set of methods calls
     * @param edges Set of method calls
     * @return JSON structure of class graph
     */
    public static String getStructure(Collection<String> edges) {
        return exportGraph(buildGraph(edges));
    }

    /**
     * Build structure graph and get plagiarism rate of work by checking for isomorphism
     * @param edges All methods calls of checking work
     * @param works All works to compare
     * @return List of plagiarism works
     */
    public static String getPlagiarismRangeByStructure(Collection<String> edges, Collection<Work> works) {
        Graph<String, DefaultEdge> graph = buildGraph(edges);
        StringBuilder sb = new StringBuilder();
        getPlagiarism(graph, works).forEach(name -> sb.append("\n").append(name));
        return sb.toString();
    }

    /**
     * Get plagiarism rate of work by structure of classes and methods calls
     * @param graph All methods calls
     * @param works All works to compare
     * @return List of plagiarism works
     */
    private static List<String> getPlagiarism(Graph<String, DefaultEdge> graph, Collection<Work> works) {
        List<String> plagiarismWorks = new ArrayList<>();
        IsomorphismInspector<String, DefaultEdge> inspector;
        Graph<String, DefaultEdge> isoGraph;
        try {
            for (Work work : works) {
                isoGraph = importGraph(work.getStructure());
                inspector = new VF2GraphIsomorphismInspector<>(graph, isoGraph);
                if (inspector.isomorphismExists())
                    plagiarismWorks.add(work.getFullName());
            }
        } catch (IsomorphismUndecidableException e) {
            e.printStackTrace();
        }
        return plagiarismWorks;
    }

    /**
     * Build new graph from set of edges
     * @param edges Set of edges
     * @return Built graph
     */
    private static Graph<String, DefaultEdge> buildGraph(Collection<String> edges) {
        Graph<String, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
        getAllVertexes(edges).forEach(graph::addVertex);
        for (String edge : edges) {
            String first = edge.substring(0, edge.indexOf('>') - 1);
            String second = edge.substring(edge.indexOf('>') + 1);
            graph.addEdge(first, second);
        }
        return graph;
    }

    /**
     * Get all vertexes from edges set
     * @param edges Set of graph edges
     * @return Set of all vertexes
     */
    private static Set<String> getAllVertexes(Collection<String> edges) {
        Set<String> set = new HashSet<>();
        edges.forEach(edge -> {
            String first = edge.substring(0, edge.indexOf('>') - 1);
            String second = edge.substring(edge.indexOf('>') + 1);
            if (!first.isEmpty() && !second.isEmpty()) {
                set.add(first);
                set.add(second);
            }
        });
        return set;
    }

    /**
     * Export DefaultDirectedGraph to JSON string
     * @param graph Graph to export
     * @return Exported string
     */
    private static String exportGraph(Graph<String, DefaultEdge> graph) {
        JSONExporter<String, DefaultEdge> exporter = new JSONExporter<>();
        exporter.setVertexIdProvider((v) -> v);
        try (Writer writer = new StringWriter()) {
            exporter.exportGraph(graph, writer);
            return writer.toString();
        } catch (IOException | ExportException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Import DefaultDirectedGraph from JSON string
     * @param structure JSON structure
     * @return DefaultDirectedGraph imported from string
     */
    private static Graph<String, DefaultEdge> importGraph(String structure) {
        JSONImporter<String, DefaultEdge> importer = new JSONImporter<>();
        Graph<String, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
        Function<String, String> vertexFactory = x -> x;
        importer.setVertexFactory(vertexFactory);
        try {
            importer.importGraph(graph, new ByteArrayInputStream(structure.getBytes()));
        } catch (ImportException | UnsupportedOperationException e) {
            e.printStackTrace();
        }
        return graph;
    }
}
