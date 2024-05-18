package org.insa.graphs.algorithm.shortestpath;


import org.junit.Test;
import org.junit.BeforeClass;

 //org.insa.graphs.algorithm.shortestpath;

import static org.junit.Assert.assertEquals;


import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;

import org.insa.graphs.algorithm.ArcInspector;
import org.insa.graphs.algorithm.ArcInspectorFactory;

//import org.insa.graphs.gui.drawing.Drawing;
//import org.insa.graphs.gui.drawing.components.BasicDrawing;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.GraphReader;

/*
 * N'est pas des JUnit
 * 2 options : faire des JUnit en se basant sur PathTest.java et Path.java
 * faire simplement des System.out.println
 * Le plus long consiste à simuler des executions de la map
 * AStar et Djikstra c'est presque pareil donc en faire qu'un seul
 * 
 */


public class AStarTest  {

	

	@BeforeClass
  // typeEvaluation : 0 = temps, 1 = distance
	public double testScenario(String map, int typeEvaluation, int origine, int destination, char algo) throws Exception {
		//return 0.0 if origin=destination
		//return -1.0 if Pas de solution
		//return the cost if therre is a solution

		final String mapName="/mnt/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/"+map;
		// Create a graph reader.
		GraphReader reader = new BinaryGraphReader(
				new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));

		// Read the graph.
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
				
				
				//System.out.println("Chemin de la carte : "+mapName);
				System.out.println("Origine : " + origine);
				System.out.println("Destination : " + destination);
				
				if(origine==destination) {
					System.out.println("Origine et Destination identiques");
					System.out.println("Cout solution: 0");
					res = 0.0;
					
				} else {			
					ShortestPathData data = new ShortestPathData(graph, graph.get(origine),graph.get(destination), arcInspectorDijkstra);
					
					ShortestPathSolution solution;
					if (algo=='A'){
						AStarAlgorithm A = new AStarAlgorithm(data);
						solution = A.run();
					} else if (algo=='D'){
						DijkstraAlgorithm D = new DijkstraAlgorithm(data);
						solution = D.run();
					} else {
						solution=null;
					}
					
					
					if (solution.getPath() == null) {
						//assertEquals(expected.getPath(), solution.getPath());
						System.out.println("PAS DE SOLUTION");
						System.out.println("(infini) ");
						res = -1.0;
					}
					// Un plus court chemin trouve 
					else {
						double costSolution;
						//double costExpected;
						if(typeEvaluation == 0) { //Temps
							//Calcul du cout de la solution 
							costSolution = solution.getPath().getMinimumTravelTime();
							//costExpected = expected.getPath().getMinimumTravelTime();
						} else {
							costSolution = solution.getPath().getLength();
							//costExpected = expected.getPath().getLength();
						}
						//assertEquals(costExpected, costSolution, 0.001);
						System.out.println("Cout solution: " + costSolution);
						res = costSolution;
					}
				}
			}
		}
		System.out.println();
		System.out.println();
        reader.close();
		return res;
	}


	@Test
	public void NoPath() throws Exception {
		//We check that it's the same result for Djikstra and A*
		//check les noms et mettre les bonnes coordonnées
		assertEquals(testScenario("France.mapgr", 0, 0, 0, 'A'), testScenario("France.mapgr", 0, 0, 0, 'D') , 0.001 );
        assertEquals(testScenario("France.mapgr", 0, 0, 0, 'A'), testScenario("France.mapgr", 1, 0, 0, 'D') , 0.001);
    }

	@Test
	public void NullPath() throws Exception {
		//We check that it's the same result for Djikstra and A*
		//put the same origin and destination
		assertEquals(testScenario("France.mapgr", 0, 0, 0, 'A'), testScenario("France.mapgr", 0, 0, 0, 'D') , 0.001 );
        assertEquals(testScenario("France.mapgr", 0, 0, 0, 'A'), testScenario("France.mapgr", 1, 0, 0, 'D') , 0.001);
    }

	@Test
	public void SImplePath() throws Exception {
		//We check that it's the same result for Djikstra and A*
		//choose sommets
		assertEquals(testScenario("France.mapgr", 0, 0, 0, 'A'), testScenario("France.mapgr", 0, 0, 0, 'D') , 0.001 );
        assertEquals(testScenario("France.mapgr", 0, 0, 0, 'A'), testScenario("France.mapgr", 1, 0, 0, 'D') , 0.001);
    }

	@Test
	public void SommetsInexistants() throws Exception {
		//We check that it's the same result for Djikstra and A*
		//Choose sommets
		assertEquals(testScenario("France.mapgr", 0, 0, 0, 'A'), testScenario("France.mapgr", 0, 0, 0, 'D') , 0.001 );
        assertEquals(testScenario("France.mapgr", 0, 0, 0, 'A'), testScenario("France.mapgr", 1, 0, 0, 'D') , 0.001);
    }

	@Test
	public void LongPath() throws Exception {
		//We check that it's the same result for Djikstra and A*
		//Choose sommets
		assertEquals(testScenario("France.mapgr", 0, 0, 0, 'A'), testScenario("France.mapgr", 0, 0, 0, 'D') , 0.001 );
        assertEquals(testScenario("France.mapgr", 0, 0, 0, 'A'), testScenario("France.mapgr", 1, 0, 0, 'D') , 0.001);
    }





}


