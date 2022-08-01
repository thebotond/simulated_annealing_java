package tspsa;

import java.util.ArrayList;

public class Equations {

	public Equations() {
		// TODO Auto-generated constructor stub
	}

	public int distance(int x1, int x2, int y1, int y2)
	{
		int dist = (int) Math.sqrt(Math.pow(Math.abs(x2 - x1), 2) + Math.pow(Math.abs(y2 - y1), 2));
		return dist;
	}
	
	public float coolingSchedule(int option, float initTemp, float currTemp, float targetTemp, int i, int vertices)
	{
		float newTemp = 0;
		float A, B;
		float target = targetTemp;
		float init = initTemp;
		int scale = vertices;
		switch(option)
		{
		case 1: //linear
			newTemp = init - ((target - init)*i)/(-100000 * scale);
			break;
		case 2: //exponential
			A = (init + ((target - init)*i)/(100000*scale) * (currTemp + i));
			B = (i + 1)/(i);
			newTemp = (float) Math.pow(A, B);
			break;
		}
		return newTemp;
	}
	
	
	public boolean acceptance(int prevCost, int newCost, double temp, double initTemp)
	{
		double rand = Math.random();
		double prob = 1/(1 + Math.exp((newCost - prevCost)/initTemp));
		if(rand <= prob)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public int cost(ArrayList<Integer> solutionIndicies, ArrayList<Integer> xCoords, ArrayList<Integer> yCoords)
	{
		int i = 0;
		int cost = 0;
		int currentIndex, nextIndex, x1, x2, y1, y2;
		while(i < solutionIndicies.size() - 1)
		{
			currentIndex = solutionIndicies.get(i);
			nextIndex = solutionIndicies.get(i + 1);
			x1 = xCoords.get(currentIndex);
			x2 = xCoords.get(nextIndex);
			y1 = yCoords.get(currentIndex);
			y2 = yCoords.get(nextIndex);
			
			cost += this.distance(x1, x2, y1, y2);
			i++;
		}
		return cost;
	}
}
