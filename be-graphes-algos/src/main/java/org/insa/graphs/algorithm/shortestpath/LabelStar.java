package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.algorithm.AbstractInputData;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Point;
import org.insa.graphs.algorithm.shortestpath.ShortestPathData;

public class LabelStar extends Label {
    
    private float borneInf;

    public LabelStar(Node noeud, ShortestPathData data){
        super(noeud);
        System.out.println("heu\n");
        if (data.getMode() == AbstractInputData.Mode.LENGTH) {
            this.borneInf = (float)Point.distance(noeud.getPoint(), data.getDestination().getPoint());
        } else {
            int carsKatchow = data.getGraph().getGraphInformation().getMaximumSpeed();
            this.borneInf = (float)Point.distance(noeud.getPoint(), data.getDestination().getPoint())/(carsKatchow*1000.0f/3600.0f);
        }
    }

    @Override
    public float getTotalCost() {
        return this.TotalCost;
    }

    @Override
    public void setTotalCost(float cout) {
        this.TotalCost=this.borneInf+cout;
    }

    //Utilise la comparaison de label pour le djisktra
    @Override
    public int compareTo(LabelStar autre) {
        System.out.println("labelstar\n");
        int res;
        if (this.getTotalCost() < autre.getTotalCost()) {
            res = -1;
        }
        else if (this.getTotalCost() == autre.getTotalCost()) {
            if (this.borneInf<autre.borneInf){
                res= 1;
            } else {
                res = -1;
            }
        }
        else {
            res = 1;
        }
        return res;
    }

}
