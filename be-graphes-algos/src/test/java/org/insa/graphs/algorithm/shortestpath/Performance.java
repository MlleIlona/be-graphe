package org.insa.graphs.algorithm.shortestpath;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;

import org.junit.Test;
import static org.junit.Assert.assertEquals;


import org.insa.graphs.algorithm.ArcInspector;
import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.GraphReader;

public class Performance {

    //Simuler l'execution et avoir le temps dans res

    public double TempsExecution(String map, int typeEvaluation, int origine, int destination, char algo) {

        double temps=0.0;
        final String mapName="C:/Users/bourg/OneDrive/Bureau/INSA_3A/S2/BE_Graphes/Maps/"+map;
		
		try {
			GraphReader reader = new BinaryGraphReader(
					new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));

			Graph graph = reader.read();

			/* Création de l'arcInspector */
			ArcInspector arcInspector;
			/* Si évaluation en temps */
			if (typeEvaluation == 0) {
				arcInspector = ArcInspectorFactory.getAllFilters().get(2);
			}
			/* Sinon évaluation en distance */
			else {
				arcInspector = ArcInspectorFactory.getAllFilters().get(0);
			}

			ShortestPathData data = new ShortestPathData(graph, graph.get(origine),graph.get(destination), arcInspector);

			long tempsDeb;
			long tempsFin;

            int nbSommet;
            if (algo=='A'){
                /* Calcul du temps d'exécution d'AStar */
                AStarAlgorithm A = new AStarAlgorithm(data);
                tempsDeb = System.nanoTime();
                A.run();
                tempsFin = System.nanoTime();
                temps = (tempsFin-tempsDeb)/1000000.0f;//Pour avoir en ms
                nbSommet=A.nbSommetsVisites;
            } else if (algo=='D'){
                /* Calcul du temps d'exécution de Dijkstra */
                DijkstraAlgorithm D = new DijkstraAlgorithm(data);
                tempsDeb = System.nanoTime();//en ns
                D.run();
                tempsFin = System.nanoTime();
                temps = (tempsFin-tempsDeb)/1000000.0f;//met en ms
                nbSommet=D.nbSommetsVisites;
            } else {
                temps=0.0;
                nbSommet=0;
            }
            System.out.println(nbSommet);
            reader.close();
            return temps;
            
		} catch (Exception e) {
			System.out.println(e.getMessage());
            return 0.0;
		}
	}
    

    @Test
    public void Test() throws Exception {
        double tot_temps=0;
        double temps_unitaire=0;


/*----------------Test sur 100 itération de Djikstra sur un chemin court en temps---------------*/
        for (int i=0; i<100; i++){
            tot_temps+=TempsExecution("toulouse.mapgr", 0 ,3167, 6159, 'D');

        }
        temps_unitaire=tot_temps/100.0;
        System.out.println("Temps Djikstra chemin court en temps : " + temps_unitaire + "\n");
        tot_temps=0;
        temps_unitaire=0;

/*----------------Test sur 100 itération de A* sur un chemin court en temps------------*/
        for (int i=0; i<100; i++){
            tot_temps+=TempsExecution("toulouse.mapgr", 0 ,3167, 6159, 'A');
            //Type evaluation : 0 = Temps & 1 = Distance
        }
        temps_unitaire=tot_temps/100.0;
        System.out.println("Temps A* chemin court en temps : " + temps_unitaire + "\n");
        tot_temps=0;
        temps_unitaire=0;


/*---------------Test sur 100 itération de Djikstra sur un chemin moyen en temps-------------*/
        for (int i=0; i<100; i++){
            tot_temps+=TempsExecution("haute-garonne.mapgr", 0 ,78045, 86441, 'D');
        }
        temps_unitaire=tot_temps/100.0;
        System.out.println("Temps Djikstra chemin moyen en temps : " + temps_unitaire + "\n");
        tot_temps=0;
        temps_unitaire=0;

/*---------------Test sur 100 itération de A* sur un chemin moyen en temps ------------------*/
        for (int i=0; i<100; i++){
            tot_temps+=TempsExecution("haute-garonne.mapgr", 0 ,78045, 86441, 'A');

        }
        temps_unitaire=tot_temps/100.0;
        System.out.println("Temps A* chemin moyen en temps : " + temps_unitaire + "\n");
        tot_temps=0;
        temps_unitaire=0;


/*---------------Test sur 10 itération de Djikstra sur un chemin long en temps---------------*/
        for (int i=0; i<10; i++){
            tot_temps+=TempsExecution("haute-garonne.mapgr", 0 ,56839, 147003, 'D');

        }
        temps_unitaire=tot_temps/10.0;
        System.out.println("Temps Djikstra chemin long en temps : " + temps_unitaire + "\n");
        tot_temps=0;
        temps_unitaire=0;


/*---------------Test sur 10 itération de A* sur un chemin long en temps---------------------*/
        for (int i=0; i<10; i++){
            //We tried with France, and it worked, but it's really long
            tot_temps+=TempsExecution("haute-garonne.mapgr", 0 ,56839, 147003, 'A');
        }
        temps_unitaire=tot_temps/10.0;
        System.out.println("Temps A* chemin long en temps : " + temps_unitaire + "\n");
        tot_temps=0;
        temps_unitaire=0;


/*----------------Test sur 100 itération de Djikstra sur un chemin court en distance---------------*/
        for (int i=0; i<100; i++){
            tot_temps+=TempsExecution("toulouse.mapgr", 1 ,3167, 6159, 'D');

        }
        temps_unitaire=tot_temps/100.0;
        System.out.println("Temps Djikstra chemin court en distance : " + temps_unitaire + "\n");
        tot_temps=0;
        temps_unitaire=0;

/*----------------Test sur 100 itération de A* sur un chemin court en distance------------*/
        for (int i=0; i<100; i++){
            tot_temps+=TempsExecution("toulouse.mapgr", 1 , 3167, 6159, 'A');

        }
        temps_unitaire=tot_temps/100.0;
        System.out.println("Temps A* chemin court en distance : " + temps_unitaire + "\n");
        tot_temps=0;
        temps_unitaire=0;


/*---------------Test sur 100 itération de Djikstra sur un chemin moyen en distance-------------*/
        for (int i=0; i<100; i++){
            tot_temps+=TempsExecution("haute-garonne.mapgr", 1 ,78045, 86441, 'D');

        }
        temps_unitaire=tot_temps/100.0;
        System.out.println("Temps Djikstra chemin moyen en distance : " + temps_unitaire + "\n");
        tot_temps=0;
        temps_unitaire=0;

/*---------------Test sur 100 itération de A* sur un chemin moyen en distance ------------------*/
        for (int i=0; i<100; i++){
            tot_temps+=TempsExecution("haute-garonne.mapgr", 1 ,78045, 86441, 'A');

        }
        temps_unitaire=tot_temps/100.0;
        System.out.println("Temps A* chemin moyen en distance : " + temps_unitaire + "\n");
        tot_temps=0;
        temps_unitaire=0;


/*---------------Test sur 10 itération de Djikstra sur un chemin long en distance---------------*/
        for (int i=0; i<10; i++){
            tot_temps+=TempsExecution("haute-garonne.mapgr", 1 ,56839, 147003, 'D');

        }
        temps_unitaire=tot_temps/10.0;
        System.out.println("Temps Djikstra chemin long en distance : " + temps_unitaire + "\n");
        tot_temps=0;
        temps_unitaire=0;


/*---------------Test sur 10 itération de A* sur un chemin long en distance---------------------*/
        for (int i=0; i<10; i++){
            tot_temps+=TempsExecution("haute-garonne.mapgr", 1 ,56839, 147003, 'A');

        }
        temps_unitaire=tot_temps/10.0;
        System.out.println("Temps A* chemin long en distance : " + temps_unitaire + "\n");
        tot_temps=0;
        temps_unitaire=0;


        assertEquals(1,1); //Simule des tests mais nous affiche le temps d'execution
        //Voir en bas en bleu dans la Debug Console


        }

}
