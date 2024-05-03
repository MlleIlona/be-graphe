package org.insa.graphs.algorithm.shortestpath;


import org.insa.graphs.algorithm.shortestpath.LabelStar;
import org.insa.graphs.model.Node;

public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    //On veut utiliser LabelStar au lieu de Label ici
    protected Label newLabel(Node node, ShortestPathData data) {
		return new LabelStar(node, data);
	}

}
