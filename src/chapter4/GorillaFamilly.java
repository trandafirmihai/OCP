package chapter4;

public class GorillaFamilly {
	
	String walk = "walk";
	
	void everyonePlay(boolean baby)  {
		String approach = "amble";
//		approach = "run";
		
 		play(() -> walk);
 		play(() -> baby ? "hitch a ride" : "run");
 		play(() -> approach);
 		// If I uncomment the line 9 and reassign variable
 		// then the line 13 will be a compiler error.
 		// The lambda expression can't access the variable because
 		// it will not be final or effectively final anymore
	}
	
	void play(Gorilla g) {
		System.out.println(g.move());
	}

}
