package ehes.praktika6.inferentzia;

import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;

public class Baseline {
	public void baselineEgin(Instances test, Instances train, String path) throws Exception {
		Instances osoa = new Instances(test);
		osoa.addAll(train);
		System.out.println("Naive Bayes aplikatu:");
		ModeloaEraiki m = new ModeloaEraiki();
		m.ebaluazioEzZintzoa(osoa, this.naiveBayesBerriaLortu(), path);
		m.trainVStest(test, train, this.naiveBayesBerriaLortu());
		m.holdOut7030(osoa, this.naiveBayesBerriaLortu());
		m.crossValidation(osoa, this.naiveBayesBerriaLortu());
	}

	private NaiveBayes naiveBayesBerriaLortu() {
		NaiveBayes nv = new NaiveBayes();
		nv.setUseKernelEstimator(true);
		return nv;
	}

}
