package bloomFilter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

//implementation of a bit array to store data while taking up as little memory as possible

class BitArray {

	public int[] theArray;
	
	public BitArray(int m) 
	{
		if(m < 0)
		{
			throw new IllegalArgumentException("Size of array must be greater than 0");
		}
		else
		{
			this.theArray = new int[(int) m/32];
			Arrays.fill(this.theArray, 0);
		}
	}

//used to check the value of the bit at given index
	
	public boolean get(int n)
	{
		int x = this.theArray[(int) Math.floor(n/32)];
		if((x & (1 << ((n % 32) - 1))) != 0)
		{
			return true;
		}
		return false;
	}
	
//used to set the value of a bit at the given index

	public void set(int bit)
	{
		int num = (int) Math.floor(bit/32);
		int m = 1 << ((bit % 32) - 1);
		this.theArray[num] = ((this.theArray[num]) | m);
	}
		
}
	

class BloomFilter {
	public BitArray theArr;
	public int sz;
	public int words;
	
//constructor for the bloom filter array

	public BloomFilter(int m)
	{
		if(m < 0)
		{
			throw new IllegalArgumentException("Argument must be greater than zero");
		}
		else
		{
			this.theArr = new BitArray(m);
			this.sz = m;
			this.words = 0;
		}
	}

//several different hash functions used to encode given words
//can add more if needed
	
	private int h1(String w)
	{
		int h = 0;
		for (int i = 0; i < w.length(); i++)
		{
			h = h + 9941 * 5 + w.charAt(i);
		}
		return (h % this.sz);
	}
	
	private int h2(String w)
	{
		int h = 0;
		for (int i = 0; i < w.length(); i++)
		{
			h = h + 2933 * 7 + w.charAt(i);
		}
		return (h % this.sz);
	}

	private int h3(String w)
	{
		int h = 0;
		for (int i = 0; i < w.length(); i++)
		{
			h = h + 5 * 3 + w.charAt(i);
		}
		return (h % this.sz);
	}
	
	private int h4(String w)
	{
		int h = 0;
		for (int i = 0; i < w.length(); i++)
		{
			h = h + 2 * w.charAt(i);
		}
		return (h % this.sz);
	}
	
	private int h5(String w)
	{
		int h = 0;
		for (int i = 0; i < w.length(); i++)
		{
			h = h + 9 * w.charAt(i);
		}
		return (h % this.sz);
	}

//adds given string to bloom filter
	
	public void add(String w)
	{
		int ch1 = h1(w);
		int ch2 = h2(w);
		int ch3 = h3(w);
		int ch4 = h4(w);
		int ch5 = h5(w);
		this.theArr.set(ch1);
		this.theArr.set(ch2);
		this.theArr.set(ch3);
		this.theArr.set(ch4);
		this.theArr.set(ch5);
		this.words++;
	}

//checks if word is in bloom filter array
	
	public boolean isIn(String w)
	{
		int ch1 = h1(w);
		int ch2 = h2(w);
		int ch3 = h3(w);
		int ch4 = h4(w);
		int ch5 = h5(w);
		if (this.theArr.get(ch1) & this.theArr.get(ch2) & this.theArr.get(ch3) & this.theArr.get(ch4) & this.theArr.get(ch5))
		{
			return true;
		}
		return false;
	}

//checks accuracy base on how many hash functions and words are provided
	
	public double accuracy()
	{
	    double p = 1.0 - Math.pow(Math.pow(2.718281828, (-5.0 * (850.0 / (32.0 * 5000.0)))), 5.0);
		return p;
	}
	

}


public class Driver 
{
	public static void main(String[] args) throws FileNotFoundException, IOException
	{
		String basic = args[0];
		BloomFilter bf = new BloomFilter(32 * 5000);
	    BufferedReader in = new BufferedReader(new FileReader(basic));

	    String inputLine;
	    while ((inputLine = in.readLine()) != null)
	    {
            bf.add(inputLine);
	    }
	    in.close();
	    in = new BufferedReader(new FileReader(basic));

	    while ((inputLine = in.readLine()) != null)
	    {
		if(bf.isIn(inputLine) == false)
		    {
			System.out.println(inputLine);
		    }
	    }
		System.out.println(bf.accuracy());
	}		
}

