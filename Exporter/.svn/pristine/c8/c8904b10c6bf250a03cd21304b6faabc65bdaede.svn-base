package de.CAA.utils;

public class Sort {

	/**
	 * call by reference
	 */
	public static void bubbleSort(int[] x){
		boolean unsortiert=true;
		int temp;
		while (unsortiert){
			unsortiert = false;
			for (int i=0; i < x.length-1; i++) 
				if (x[i] > x[i+1]) {                      
					temp       = x[i];
					x[i]       = x[i+1];
					x[i+1]     = temp;
					unsortiert = true;
				}          
		} 

	}

	/**
	 * call by reference
	 */
	public static void bubbleSortWithLabels(int[] x, String[] y){
		boolean unsortiert=true;
		int temp;
		while (unsortiert){
			unsortiert = false;
			for (int i=0; i < x.length-1; i++) 
				if (x[i] > x[i+1]) {                      
					temp       = x[i];
					x[i]       = x[i+1];
					x[i+1]     = temp;


					String tempi = y[i];
					y[i] = y[i+1];
					y[i+1] = tempi;

					unsortiert = true;
				}          
		} 
	}

}
