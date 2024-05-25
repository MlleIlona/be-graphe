package org.insa.graphs.algorithm.shortestpath;


import org.junit.Test;

import static org.junit.Assert.assertEquals;


import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.util.Random;

import org.insa.graphs.algorithm.ArcInspector;
import org.insa.graphs.algorithm.ArcInspectorFactory;

import org.insa.graphs.model.Graph;
import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.GraphReader;



public class DjikstraTest  {


  // typeEvaluation : 0 = temps, 1 = distance
	public double testScenario(String map, int typeEvaluation, int origine, int destination, char algo) throws Exception {
		//return 0.0 if origin=destination
		//return -1.0 if Pas de solution
		//return the cost if therre is a solution

		final String mapName="C:/Users/bourg/OneDrive/Bureau/INSA_3A/S2/BE_Graphes/Maps/"+map;

		GraphReader reader = new BinaryGraphReader(
				new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));


		Graph graph = reader.read();

		double res = 0;

		if (typeEvaluation!=0 && typeEvaluation!=1) {
			System.out.println("Argument invalide : 0 = Temps & 1 = Distance");
		} else {
			if (origine<0 || destination<0 || origine>=(graph.size()-1) || destination>=(graph.size()-1)) { // On est hors du graphe. / Sommets inexistants
				System.out.println("ERREUR : Paramètres invalides ");
				
			} else {
				ArcInspector arcInspectorDijkstra;
				
				if (typeEvaluation == 0) { //Temps
					System.out.println("Mode : Temps");
					arcInspectorDijkstra = ArcInspectorFactory.getAllFilters().get(2);
				} else {
					System.out.println("Mode : Distance");
					arcInspectorDijkstra = ArcInspectorFactory.getAllFilters().get(0);
				}
				
				
				System.out.println("Chemin de la carte : "+mapName);
				System.out.println("Origine : " + origine);
				System.out.println("Destination : " + destination);
				
				if(origine==destination) {
					//System.out.println("Origine et Destination identiques");
					//System.out.println("Cout solution: 0");
					res = 0.0;
					
				} else {			
					ShortestPathData data = new ShortestPathData(graph, graph.get(origine),graph.get(destination), arcInspectorDijkstra);
					
					ShortestPathSolution solution;
					if (algo=='B'){
						BellmanFordAlgorithm B = new BellmanFordAlgorithm(data);
						solution = B.run();
					} else if (algo=='D'){
						DijkstraAlgorithm D = new DijkstraAlgorithm(data);
						solution = D.run();
					} else {
						solution=null;
					}
					
					
					if (solution.getPath() == null) {
						//System.out.println("PAS DE SOLUTION");
						//System.out.println("(infini) ");
						res = -1.0;
					}
					// Un plus court chemin trouve 
					else {
						double costSolution;
						if(typeEvaluation == 0) { //Temps
							//Calcul du cout de la solution 
							costSolution = solution.getPath().getMinimumTravelTime();

						} else {
							costSolution = solution.getPath().getLength();

						}
						res = costSolution;
					}
				}
			}
		}
        reader.close();
		return res;
	}



	@Test
	public void NoPath() throws Exception {
		assertEquals(testScenario("guadeloupe.mapgr", 0, 16032, 16122, 'B'), testScenario("guadeloupe.mapgr", 0, 16032, 16122, 'D') , 0.001 );
        assertEquals(testScenario("guadeloupe.mapgr", 1, 16032, 16122, 'B'), testScenario("guadeloupe.mapgr", 1, 16032, 16122, 'D') , 0.001);
    }

	@Test
	public void NullPath() throws Exception {
		//meme origine et destination
		assertEquals(testScenario("haute-garonne.mapgr", 0, 0, 0, 'B'), testScenario("haute-garonne.mapgr", 0, 0, 0, 'D') , 0.001 );
        assertEquals(testScenario("haute-garonne.mapgr", 1, 0, 0, 'B'), testScenario("haute-garonne.mapgr", 1, 0, 0, 'D') , 0.001);
    }

	//@Test
	public void SimplePath() throws Exception {
		assertEquals(testScenario("haute-garonne.mapgr", 0, 103459, 15464, 'B'), testScenario("haute-garonne.mapgr", 0, 103459, 15464, 'D') , 0.001 );
        assertEquals(testScenario("haute-garonne.mapgr", 1, 103459,15464, 'B'), testScenario("haute-garonne.mapgr", 1, 103459, 15464, 'D') , 0.001);
		//test en random pour en tester une centaine
	}

	@Test
	public void PleinsDeTests() throws Exception {
		Random random = new Random();
		for (int i=0; i<100; i++){
			// On regarde en haute-garonne donc il y a 157890 nodes
			//prend environ 20-30min a se finir
			int x= random.nextInt(157890);
			int y = random.nextInt(157890);
			assertEquals(testScenario("haute-garonne.mapgr", 0, x, y, 'B'), testScenario("haute-garonne.mapgr", 0, x, y, 'D') , 0.001 );
        	assertEquals(testScenario("haute-garonne.mapgr", 1, x, y, 'B'), testScenario("haute-garonne.mapgr", 1, x, y, 'D') , 0.001);
		}
	}

	//@Test
	public void LongPath() throws Exception {
		assertEquals(testScenario("france.mapgr", 0, 3021768, 1054140, 'B'), testScenario("france.mapgr", 0, 3021768, 1054140, 'D') , 0.001 );
        assertEquals(testScenario("france.mapgr", 1, 3021768, 1054140, 'B'), testScenario("france.mapgr", 1, 3021768, 1054140, 'D') , 0.001);
    }



}


