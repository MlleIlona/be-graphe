package org.insa.graphs.algorithm.shortestpath;

import org.insa.algo.utils.Label;
import org.insa.algo.utils.LabelStar;
import org.insa.graph.Node;

public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }

    //On veut utiliser LabelStar au lieu de Label ici
    protected Label newLabel(Node node, ShortestPathData data) {
		return new LabelStar(node, data);
	}

}
