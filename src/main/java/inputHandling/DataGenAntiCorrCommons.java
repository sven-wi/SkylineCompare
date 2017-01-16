/*
 * Copyright 2016 Sven Wiemann
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use 
 * this file except in compliance with the License. You may obtain a copy of the 
 * License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed 
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES 
 * OR CONDITIONS OF ANY KIND, either express or implied. See the License for 
 * the specific language governing permissions and limitations under the License.
 */
package inputHandling;

import java.util.logging.Logger;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.random.CorrelatedRandomVectorGenerator;
import org.apache.commons.math3.random.JDKRandomGenerator;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.UniformRandomGenerator;

import framework.Tuple;
import framework.TupleList;
import inputHandling.InputHandler.DataGenInterface;

/**
 * Generator for strong Anti-Correlated Data, with the Generators from the Apache.Commons Framework.
 * @author Sven Wiemann
 *
 */
public class DataGenAntiCorrCommons implements DataGenInterface{
	private static final Logger logger = Logger.getLogger(DataGenAntiCorr.class.getName());
	// Matrices used by the Apache.commons Framework
	// The Covariance Matrix is calculated based on the used Correlation Coefficients
	double[][] cov;
	double[] mean;
	double coeff = 0.5;
	long seed;
	// Standard deviation and center of the Data Generators
	private float std = 0.6f, center = 2f;

	/**
	 * Uses a Generator with the standard Values (coefficient: -0.5, std = 0.6, center = 2) 
	 * @param seed
	 */
	public DataGenAntiCorrCommons(long seed) {
		this.seed = seed;
	}
	
	/**
	 * Uses a Generator with the standard Values (coefficient: -0.5, std = 0.6, center = 2).
	 * Seed is the current Time.
	 */
	public DataGenAntiCorrCommons() {
		this(System.currentTimeMillis());
	}
	
	/**
	 * Uses a Generator with the standard coefficient (-0.5)
	 * @param seed
	 * @param std
	 * @param center
	 */
	public DataGenAntiCorrCommons(long seed, float std, float center) {
		this.seed = seed;
		this.std = std;
		this.center = center;
	}
	
	/**
	 * Uses a Generator with the standard coefficient (-0.5)
	 * Seed is the current Time.
	 * @param std
	 * @param center
	 */
	public DataGenAntiCorrCommons(float std, float center) {
		this(System.currentTimeMillis());
		this.std = std;
		this.center = center;
	}
	
	public DataGenAntiCorrCommons(long seed, float std, float center, double coeff) {
		this.seed = seed;
		this.std = std;
		this.center = center;
		this.coeff = coeff;
	}
	
	public DataGenAntiCorrCommons(float std, float center, double coeff) {
		this(System.currentTimeMillis());
		this.std = std;
		this.center = center;
		this.coeff = coeff;
	}
	
	public DataGenAntiCorrCommons(long seed, double coeff) {
		this.seed = seed;
		this.coeff = coeff;
	}
	
	public DataGenAntiCorrCommons(double coeff) {
		this(System.currentTimeMillis());
		this.coeff = coeff;
	}

	@Override
	public TupleList genData(int dimensions, int tupleCount) {
		logger.info("Generating uniform anti-correlated Data with " + tupleCount + " Tuples in " + dimensions + " dimensions, Coeff.: -" + this.coeff);
		genMatrices(dimensions);
		RealMatrix covariance = MatrixUtils.createRealMatrix(cov);
		RandomGenerator rg = new JDKRandomGenerator(Math.round(seed));
		UniformRandomGenerator rawGenerator = new UniformRandomGenerator(rg);
		double small = 1.0e-12 * covariance.getNorm();
		CorrelatedRandomVectorGenerator generator = new CorrelatedRandomVectorGenerator(mean, covariance, small, rawGenerator);
		
		TupleList tupleList = new TupleList(dimensions);
		// Invert the Values, to receive Anti-Correlation
		for(int j = 0; j < tupleCount; j++){
			double[] randomVector = generator.nextVector();
			for(int i = 0; i < dimensions; i++){
				if(j % 2 == 0 && i % 2 == 0)
					randomVector[i] = 2*mean[i] - randomVector[i];					
				else if(j % 2 != 0 && i % 2 != 0)
					randomVector[i] = 2*mean[i] - randomVector[i];
				else
					randomVector[i] = randomVector[i];
			}
			Tuple tuple = new Tuple(randomVector);
			tupleList.add(tuple);
		}
		return tupleList;
	}
	
	/**
	 * Generate the Covariance-Matrix used by Apache Commons, based upon our Correlation-Coefficient
	 * @param dimensions
	 */
	private void genMatrices(int dimensions) {
		cov = new double[dimensions][dimensions];
		for(int i = 0; i < dimensions; i++){
			for(int j = 0; j < dimensions; j++){
				if(i == j)
					// Major Diagonal Entries, covariance Matrix: Cov(i,i) = Var(i) = std_i^2
					cov[i][j] = std*std;
				else
					// non Major Diagonal Entries, covariance Matrix: Cov(i,j) = r_i,j * std_i * std_j (Def. Pearson's r)
					cov[i][j] = std*std*coeff;
			}
		}
		this.mean = new double[dimensions];
		for(int i = 0; i < dimensions; i++)
			this.mean[i] = center;
	}
	
	public double getCoeff() {
		return coeff;
	}

	public void setCoeff(double coeff) {
		this.coeff = coeff;
	}

	public long getSeed() {
		return seed;
	}

	public void setSeed(long seed) {
		this.seed = seed;
	}

	public float getStd() {
		return std;
	}

	public void setStd(float std) {
		this.std = std;
	}

	public float getCenter() {
		return center;
	}

	public void setCenter(float center) {
		this.center = center;
	}
}
