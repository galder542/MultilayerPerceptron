package ehes.praktika6.inferentzia;

import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;

public class Baseline {
	public void baselineEgin(Instances test, Instances train, String path) throws Exception {
		Instances osoa = new Instances(test);
		osoa.addAll(train);
		System.out.println("Naive Bayes aplikatu:");
		ModeloaEraiki m = new ModeloaEraiki();
		m.ebaluazioEzZintzoa(osoa, new NaiveBayes(), path);
		m.trainVStest(test, train, new NaiveBayes());
		m.holdOut7030(osoa, new NaiveBayes());
		m.crossValidation(osoa, new NaiveBayes());
	}
}
