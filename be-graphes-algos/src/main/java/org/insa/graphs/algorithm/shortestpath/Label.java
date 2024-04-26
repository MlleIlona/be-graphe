package org.insa.graphs.algorithm.shortestpath;
import org.insa.graphs.model.Node;


public class Label implements Comparable<Label> {
    protected float realised_cost;
    private boolean marked; // vrai si le cout min de ce sommet est définitivement connu par l'algo
    private Node father;
    private Node sommet;
    private boolean exist;

    public Label(Node noeud){
    this.sommet = noeud;
    this.marked = false;
    this.realised_cost = Float.POSITIVE_INFINITY;
    this.father = null;
    this.exist = false;
}

/*Getter*/

public boolean get_mark(){
    return marked;
}

public Node get_father(){
    return father;
}

public Node get_sommet(){
    return sommet;
}

public float getCost(){
    return realised_cost;


//Est sensé retourné le cout de ce label
}

public boolean getExist(){
    return exist;
}

/*Setter*/
public void setMark() {
    this.marked = true;
}

public void setCost(float cout) {
    this.realised_cost = cout;
}

public void setFather(Node father) {
    this.father = father;
}

public void setExist() {
    this.exist = true;
}

public int compareTo(Label autre) {
    int res;
    if (this.getCost() < autre.getCost()) {
        res = -1;
    }
    else if (this.getCost() == autre.getCost()) {
        res = 0;
    }
    else {
        res = 1;
    }
    return res;
}


}
