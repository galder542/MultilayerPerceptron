package ehes.praktika6.inferentzia;

import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;

public class Baseline {
	public void baselineEgin(Instances osoa, Instances test, Instances train, String path, boolean ezZintzoa)
			throws Exception {
		System.out.println("\nNaive Bayes aplikatu:");
		ModeloaEraiki m = new ModeloaEraiki();
		if (ezZintzoa)
			m.ebaluazioEzZintzoa(osoa, new NaiveBayes(), path);
		m.trainVStest(test, train, new NaiveBayes());
	}
}
