package it.uniroma1.lcl.mynn;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;



public class TestReteNeurale {
	
	@Test
	public void testCaricaReteAnd() throws IOException {
		try {
			StringBuilder str = new StringBuilder();
			Files.newBufferedReader(Paths.get(new File(".").getCanonicalPath() + "/reteAnd")).lines().forEach(l -> {
				str.append(l);
				str.append("\n");
			});
			IReteNeurale rete = IReteNeurale.carica(new File(".").getCanonicalPath() + "/reteAnd");
			Assert.assertTrue(Math.abs(rete.process(new double[]{0.0,0.0})[0]) < 0.01);
			Assert.assertTrue(Math.abs(rete.process(new double[]{1.0,0.0})[0]) < 0.01);
			Assert.assertTrue(Math.abs(rete.process(new double[]{0.0,1.0})[0]) < 0.01);
			Assert.assertTrue(Math.abs(1.0 - rete.process(new double[]{1.0,1.0})[0]) < 0.01);
		
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void testCaricaToStringAnd() throws IOException {
		try {
			StringBuilder str = new StringBuilder();
			Files.newBufferedReader(Paths.get(new File(".").getCanonicalPath() + "/reteAnd")).lines().forEach(l -> {
				str.append(l);
				str.append("\n");
			});
			IReteNeurale rete = IReteNeurale.carica(new File(".").getCanonicalPath() + "/reteAnd");
			
			Assert.assertEquals(str.toString().trim(), rete.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void testCaricaNome() throws IOException {
		try {
			StringBuilder str = new StringBuilder();
			Files.newBufferedReader(Paths.get(new File(".").getCanonicalPath() + "/reteAnd")).lines().forEach(l -> {
				str.append(l);
				str.append("\n");
			});
			IReteNeurale rete = IReteNeurale.carica(new File(".").getCanonicalPath() + "/reteAnd");

			Assert.assertEquals("ReteAnd", rete.getNome());
			
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void testCaricaReteOr() throws IOException {
		try {
			StringBuilder str = new StringBuilder();
			Files.newBufferedReader(Paths.get(new File(".").getCanonicalPath() + "/reteOr")).lines().forEach(l -> {
				str.append(l);
				str.append("\n");
			});
			IReteNeurale rete = IReteNeurale.carica(new File(".").getCanonicalPath() + "/reteOr");
			Assert.assertTrue(Math.abs(rete.process(new double[]{0.0,0.0})[0]) < 0.001);
			Assert.assertTrue(Math.abs(1.0 - rete.process(new double[]{1.0,0.0})[0]) < 0.01);
			Assert.assertTrue(Math.abs(1.0 -rete.process(new double[]{0.0,1.0})[0]) < 0.01);
			Assert.assertTrue(Math.abs(1.0 - rete.process(new double[]{1.0,1.0})[0]) < 0.01);
		
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void testCaricaToStringOr() throws IOException {
		try {
			StringBuilder str = new StringBuilder();
			Files.newBufferedReader(Paths.get(new File(".").getCanonicalPath() + "/reteOr")).lines().forEach(l -> {
				str.append(l);
				str.append("\n");
			});
			IReteNeurale rete = IReteNeurale.carica(new File(".").getCanonicalPath() + "/reteOr");
			
			Assert.assertEquals(str.toString().trim(), rete.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void testCaricaReteXor() throws IOException {
		try {
			StringBuilder str = new StringBuilder();
			Files.newBufferedReader(Paths.get(new File(".").getCanonicalPath() + "/reteXor")).lines().forEach(l -> {
				str.append(l);
				str.append("\n");
			});
			IReteNeurale rete = IReteNeurale.carica(new File(".").getCanonicalPath() + "/reteXor");
			Assert.assertTrue(Math.abs(rete.process(new double[]{0.0,0.0})[0]) < 0.001);
			Assert.assertTrue(Math.abs(1.0 - rete.process(new double[]{1.0,0.0})[0]) < 0.01);
			Assert.assertTrue(Math.abs(1.0 - rete.process(new double[]{0.0,1.0})[0]) < 0.01);
			Assert.assertTrue(Math.abs(rete.process(new double[]{1.0,1.0})[0]) < 0.01);
		
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	
/*	@Test
	public void testTrainNewFunction() throws IOException {
		try {
			
			//Training set NewFunction
			double[][] outputs = {
				{ -0.07 },
				{ -0.038136553 },
				{ -0.016171875 },
				{ -0.001916928 },
				{ 0.005625 },
				{ 0.008177697 },
				{ 0.007109375 },
				{ 0.003866072 },
				{ 0 },
				{ -0.003133053 },
				{ -0.003984375 },
				{ -0.001100928 },
				{ 0.006875 },
				{ 0.021681197 },
				{ 0.044296875 },
				{ 0.076932072 },
				{ 0.12 }};
			double[][] inputs = {
				{0.000},
				{0.063},
				{0.125},
				{0.188}, 
				{0.250}, 
				{0.313}, 
				{0.375}, 
				{0.438}, 
				{0.500}, 
				{0.563},
				{0.625}, 
				{0.688}, 
				{0.750}, 
				{0.813}, 
				{0.875},
				{0.938},
				{1.000}};
				
			IReteNeurale rete = IReteNeurale.carica(new File(".").getCanonicalPath() + "/reteNewFunction");
		
			rete.train(inputs, outputs);
			double[] k1 = rete.process(new double[]{0.778850605142699});
			double[] k2 = rete.process(new double[]{0.879355900399087});
			double[] k3 = rete.process(new double[]{0.344955023538766});
			System.out.println(Arrays.toString(k1));
			System.out.println(Arrays.toString(k2));
			System.out.println(Arrays.toString(k3));
			Assert.assertTrue(Math.abs(0.01272750023 - k1[0]) < 0.01);
			Assert.assertTrue(Math.abs(0.04622318462 - k2[0]) < 0.01);			
			Assert.assertTrue(Math.abs(0.00797947544 - k3[0]) < 0.01);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}*/

	
	@Test
	public void testTrain() throws IOException {
		try {
			
			
			

			// provare diversi learning rate. Con 0.2 e la struttura che ho dato funziona bene 
/*
								

			
			//TRAINING SET XOR
		double[][] inputs = {
					{0,0},
					{0,1},
					{1,0},
					{1,1}
			};
		
			double[][] output = {
					{0},
					{1},
					{1},
					{0}
			};
				
			IReteNeurale rete = IReteNeurale.carica(new File(".").getCanonicalPath() + "/reteXor");
		
			rete.train(inputs, output);
			
			
        //TEST XOR
			
			//COMPLETATO CON 1394 SECONDI, BREAK A > 0.01 ED ASSERT A 0.1
           
            double[] k1 = rete.process(new double[]{0, 0});
			double[] k2 = rete.process(new double[]{0, 1});
			double[] k3 = rete.process(new double[]{1, 0});
			double[] k4 = rete.process(new double[]{1, 1});
			System.out.println(Arrays.toString(k1));
			System.out.println(Arrays.toString(k2));
			System.out.println(Arrays.toString(k3));
			System.out.println(Arrays.toString(k4));
			Assert.assertTrue(Math.abs(0 + k1[0]) < 0.01);
			Assert.assertTrue(Math.abs(1 - k2[0]) < 0.01);			
			Assert.assertTrue(Math.abs(1 - k3[0]) < 0.01);
			Assert.assertTrue(Math.abs(0 + k4[0]) < 0.01);

*/
			// RETE SUM 3 STRATI (OK A 0.01 DOPO 3H E 20, ? A 0.03 OK DOPO CIRCA 58 MIN - 1H)
/*			
			IReteNeurale rete = IReteNeurale.carica(new File(".").getCanonicalPath() + "/reteSum");
			
						//Training set Sum
			double[][] inputs = {
						{0.479, 0.371},
						{0.128, 0.406},
						{0.157, 0.157},
						{0.275, 0.218},
						{0.835, 0.780},
						{0.855, 0.752},
						{0.785, 0.641},
						{0.876, 0.845},
						{0.812, 0.544},
						{0.332, 0.155},
						{0.258, 0.134},
						{0.233, 0.235},
						{0.297, 0.317},
						{0.883, 0.897},
						{0.858, 0.588},
						{0.238, 0.208},
						{0.268, 0.397},
						{0.326, 0.269},
						{0.759, 0.985},
						{0.795, 0.636},
						{0.849, 0.834},
						{0.752, 0.732},
						{0.794, 0.847},
						{0.782, 0.738},
						{0.898, 0.824},
						{0.828, 0.943},
						{0.170, 0.163},
						{0.373, 0.437},
						{0.195, 0.109},
						{0.175, 0.349},
						{0.166, 0.306},
						{0.319, 0.240},
						{0.379, 0.223},
						{0.323, 0.305},
						{0.141, 0.248},
						{0.734, 0.540},
						{0.678, 0.740},
						{0.749, 0.671},
						{0.660, 0.850},
						{0.804, 0.784}
						};
				double[][] output = {
						{0,1},
						{0,1},
						{0,1},
						{0,1},
						{1,0},
						{1,0},
						{1,0},
						{1,0},
						{1,0},
						{0,1},
						{0,1},
						{0,1},
						{0,1},
						{1,0},
						{1,0},
						{0,1},
						{0,1},
						{0,1},
						{1,0},
						{1,0},
						{1,0},
						{1,0},
						{1,0},
						{1,0},
						{1,0},
						{1,0},
						{0,1},
						{0,1},
						{0,1},
						{0,1},
						{0,1},
						{0,1},
						{0,1},
						{0,1},
						{0,1},
						{1,0},
						{1,0},
						{1,0},
						{1,0},
						{1,0}
					}; 

			
			rete.train(inputs, output);

			double[] out1 = rete.process(new double[]{0.25,0.25});
			
			System.out.println(Arrays.toString(out1));
			
		    Assert.assertTrue(Math.abs(    out1[0]) < 0.1);
		    Assert.assertTrue(Math.abs(1 - out1[1]) < 0.1);
			
			double[] out2 = rete.process(new double[]{0.75,0.75});

			System.out.println(Arrays.toString(out2));

			
			Assert.assertTrue(Math.abs(1 - out2[0]) < 0.1);
			Assert.assertTrue(Math.abs(    out2[1]) < 0.1);
		
*/	

          // TEST SQUARED (0.014 OK)
			
			//Training set RETE SQUARED
		    double[][] inputs = {
							{0.000}, 
							{0.063}, 
							{0.125},
							{0.188}, 
							{0.250}, 
							{0.313}, 
							{0.375}, 
							{0.438}, 
							{0.500}, 
							{0.563},
							{0.625}, 
							{0.688}, 
							{0.750}, 
							{0.813}, 
							{0.875},
							{0.938},
							{1.000}};
					double[][] output = {
							{0.000},
							{0.004},
							{0.016},
							{0.035},
							{0.063},
							{0.098},
							{0.141},
							{0.191},
							{0.250},
							{0.316},
							{0.391},
							{0.473},
							{0.563},
							{0.660},
							{0.766},
							{0.879},
							{1.000}
						};
			
            IReteNeurale rete = IReteNeurale.carica(new File(".").getCanonicalPath() + "/reteSquared");
            
            System.out.println(rete.toString());
            
            
			rete.train(inputs, output);
/*   
			System.out.println(Arrays.toString(rete.process(new double[]{0.3})));
			System.out.println(Arrays.toString(rete.process(new double[]{0.5})));
			System.out.println(Arrays.toString(rete.process(new double[]{0.7})));

			
			Assert.assertTrue(Math.abs(0.09 - rete.process(new double[]{0.3})[0]) < 0.1);
			Assert.assertTrue(Math.abs(0.25 - rete.process(new double[]{0.5})[0]) < 0.1);			
			Assert.assertTrue(Math.abs(0.49 - rete.process(new double[]{0.7})[0]) < 0.1);
			
*/
            System.out.println(rete.toString());


	
		} catch (Exception e) {
			e.printStackTrace();
			
			Assert.fail();
		}
	} 
	
	

}
