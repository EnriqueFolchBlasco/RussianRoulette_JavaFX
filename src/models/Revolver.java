package models;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Random;


public class Revolver {	
	
	Random random = new Random();
    Queue<Integer> tambor;
    private int indexActual;

    public Revolver() {
        this.tambor = new ArrayDeque<>();
        this.random = new Random();
        this.indexActual = 0;

        boolean balaAnadida = false;
        int posicioBala = random.nextInt(6);

        for (int i = 0; i < 6; i++) {
            if (i == posicioBala) {
                tambor.add(1);
                balaAnadida = true;
            } else {
                tambor.add(0);
            }
        }
    }


	public Random getRandom() {
		return random;
	}

	public void setRandom(Random random) {
		this.random = random;
	}

	public Queue<Integer> getTambor() {
		return tambor;
	}

	public void setTambor(Queue<Integer> tambor) {
		this.tambor = tambor;
	}
	
    public void spinYdisparo() {
        int spins;
        
        
        do {
        	
            spins = random.nextInt(6); 
            for (int i = 0; i < spins; i++) {
                tambor.add(tambor.poll()); 
            }
            
        } while (tambor.peek() == 1); 

        indexActual = 0;
    }
	
	public boolean disparo() {
	    int currentBullet = tambor.peek();

	    indexActual = (indexActual + 1) % 6;

	    tambor.add(tambor.poll());

	    return currentBullet == 1;
	}


    public boolean spinAndShoot() {
        int spins = random.nextInt(6);
        
        for (int i = 0; i < spins; i++) {
        	
            tambor.add(tambor.poll());
        }

        indexActual = 0; 

        return disparo();
    }

	
	

}
