package invaders;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Highscore {

	public static void WriteHighscore(int score)
	{		
		BufferedWriter writer;
		try {
			score = Math.max(score, ReadHighscore());
			writer = new BufferedWriter(new FileWriter("highscore.dat"));
			writer.write(String.valueOf(score));
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
		}
		
	}
	
	public static int ReadHighscore()
	{
		String line;
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader("highscore.dat"));
			line = reader.readLine();
			reader.close();
			return Integer.parseInt(line);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			return 0;
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			return 0;
		}
		catch (Exception e)
		{ return 0; }
		
		
	}
}
