package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;


public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    protected int nbSommetsVisites;
    protected int nbSommets;

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
        this.nbSommetsVisites = 0;
    }

    protected Label newLabel(Node node, ShortestPathData data) {
		return new Label(node);
	}

    @Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        
        Graph graph = data.getGraph();
        final int nbNodes = graph.size();

        

        // On associe un label à chaque noeud avec leur ID
        Label tableauLabel[] = new Label[nbNodes];
        // On instancie un tas de label pour la gestion des successeurs
        BinaryHeap<Label> tasLabel = new BinaryHeap<Label>();

        //Initialisation du tableau des prédecesseurs
        Arc[] predecessorArcs = new Arc[nbNodes];

        // Ajout du sommet de départ
        Label debut = newLabel(data.getOrigin(), data);
        tableauLabel[debut.get_sommet().getId()] = debut;
        debut.setExist(); //setExist dis que le Node est déjà dans le tableau : pas besoin de le remettre si on retombe dessus
        tasLabel.insert(debut);
        debut.setCost(0);


        //Previens les observateurs de l'origine
        notifyOriginProcessed(data.getOrigin());


        boolean found = false;
        while(!tasLabel.isEmpty() && !found) {
            Label current = tasLabel.deleteMin();

            notifyNodeMarked(current.get_sommet());
            current.setMark();

            if (current.get_sommet() == data.getDestination()) {
				found = true;
			}
			/* Parcours des successeurs du sommet courant */
			Iterator<Arc> arc = current.get_sommet().getSuccessors().iterator();

            while(arc.hasNext()) {
                Arc arcIter = arc.next();
                if(!data.isAllowed(arcIter)) {
                    continue; // a gérer pour les chemins piétons (canal)
                }
                Node successor = arcIter.getDestination();
                Label successorLabel = tableauLabel[successor.getId()];
                // Si le successor n'est pas encore marqué, on le créé
                if (successorLabel == null) {
                    notifyNodeReached(arcIter.getDestination());
                    successorLabel = newLabel(successor, data);

                    tableauLabel[successorLabel.get_sommet().getId()] = successorLabel;
                    this.nbSommetsVisites++; // incrémentation du nb de sommets visités
                }

                if (!successorLabel.get_mark()) {
					//Si meilleur cout, mise à jour du cout

                    if ((successorLabel.getCost()>(current.getCost()+data.getCost(arcIter)))) {

                            successorLabel.setCost(current.getCost()+(float)data.getCost(arcIter));
                            successorLabel.setTotalCost(successorLabel.getCost());
                            successorLabel.setFather(current.get_sommet());

						/* Si le label est déjà dans le tas */
						/* Alors on met à jour sa position dans le tas */
						if(successorLabel.getExist()) {
							tasLabel.remove(successorLabel);
						}
						/* Sinon on l'ajoute dans le tas */
						else {
							successorLabel.setExist();
						}
						tasLabel.insert(successorLabel);
						predecessorArcs[arcIter.getDestination().getId()] = arcIter;
					}
				}
            }
            
                // Pas possible car la destination n'a pas de predecesseur
            if (predecessorArcs[data.getDestination().getId()] == null) {
                solution = new ShortestPathSolution(data, Status.INFEASIBLE);
            } else {

                // Previens les observateur : destination trouvé
                notifyDestinationReached(data.getDestination());

                // Créer le chemin a partir de l'Array des prédecesseurs
                ArrayList<Arc> arcs = new ArrayList<>();
                Arc arc2 = predecessorArcs[data.getDestination().getId()];

                while (arc2 != null) {
                    arcs.add(arc2);
                    arc2 = predecessorArcs[arc2.getOrigin().getId()];
                }

                // Inverse le chemin (pour avoir de origine à destination)
                Collections.reverse(arcs);

                // Solution finale
                solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, arcs));

            }

        }



        return solution;
    }

}
