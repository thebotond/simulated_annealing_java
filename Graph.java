package tspsa;

import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;

public class Graph {

	public Graph(int cities) {
		this.createRandomCities(cities);
		this.writeToFile();
	}

	ArrayList<Integer> xList = new ArrayList<Integer>();
	ArrayList<Integer> yList = new ArrayList<Integer>();
	
	private void createRandomCities(int numberOfCities)
	{
		int i = 0;
		int maxCoords = 1000;
		int randX, randY;
		
		while(i < numberOfCities)
		{
			randX = (int) (Math.random() * maxCoords);
			randY = (int) (Math.random() * maxCoords);
			while(true)
			{
				while(xList.contains(randX) && yList.contains(randY))
				{
					randX = (int) (Math.random() * maxCoords);
					randY = (int) (Math.random() * maxCoords);
				}
				
				xList.add(randX);
				yList.add(randY);
				break;
			}
			i++;
		}
	}

	private void writeToFile()
	{
		try
		{
			int i = 0;
			FileWriter writer = new FileWriter("xCoords.txt");
			while(i < xList.size())
			{
				writer.write(xList.get(i).toString());
				writer.write('\n');
				i++;
			}
			writer.close();
			System.out.println("X-coordinates have been added to xCoords.txt.");
		}
		catch (IOException e)
		{
			System.out.println("Error.");
			e.printStackTrace();
		}
		try
		{
			int i = 0;
			FileWriter writer = new FileWriter("yCoords.txt");
			while(i < yList.size())
			{
				writer.write(yList.get(i).toString());
				writer.write('\n');
				i++;
			}
			writer.close();
			System.out.println("Y-coordinates have been added to yCoords.txt.");
		}
		catch (IOException e)
		{
			System.out.println("Error.");
			e.printStackTrace();
		}
	}
}
