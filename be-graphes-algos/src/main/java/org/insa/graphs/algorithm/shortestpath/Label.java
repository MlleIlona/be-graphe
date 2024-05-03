package org.insa.graphs.algorithm.shortestpath;
import org.insa.graphs.model.Node;


public class Label implements Comparable<Label> {
    protected float realised_cost;
    private boolean marked; // vrai si le cout min de ce sommet est définitivement connu par l'algo
    private Node father;
    private Node sommet;
    private boolean exist;
    protected float TotalCost;

    public Label(Node noeud){
    this.sommet = noeud;
    this.marked = false;
    this.realised_cost = Float.POSITIVE_INFINITY;
    this.father = null;
    this.exist = false;
    this.TotalCost=realised_cost;
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

public float getTotalCost(){
    return realised_cost;
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

public void setTotalCost(float cout){
    this.TotalCost=cout;
}

public void setFather(Node father) {
    this.father = father;
}

public void setExist() {
    this.exist = true;
}


//MARCHE PAS BORNEiNF N'Y EST PAS
public int compareTo(Label autre) {
    System.out.println("label\n");
    System.out.println("ARGGGGGGGGGGGGGGGGGGGHHHHHHHHHHH cout : \n"+ this.getClass().getTypeName());
    int res;
    if (this.getTotalCost() < autre.getTotalCost()) {
        res = -1;
    }
    else if (this.getTotalCost() == autre.getTotalCost()) {
        if (this.getClass().getTypeName()=="org.insa.graphs.algorithm.shortestpath.LabelStar"){
            if (this.borneInf<autre.borneInf){
                res= 1;
            } else {
                res = -1;
            }
        }
        res = 1;
    }
    else {
        res = 1;
    }
    return res;
}

}
