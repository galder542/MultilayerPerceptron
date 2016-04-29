package ehes.praktika6.inferentzia;

import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;

public class Baseline {
	public void baselineEgin(Instances osoa, Instances test, Instances train, String path) throws Exception {
		System.out.println("Naive Bayes aplikatu:");
		ModeloaEraiki m = new ModeloaEraiki();
		m.ebaluazioEzZintzoa(osoa, new NaiveBayes(), path);
		m.trainVStest(test, train, new NaiveBayes());
	}
}
