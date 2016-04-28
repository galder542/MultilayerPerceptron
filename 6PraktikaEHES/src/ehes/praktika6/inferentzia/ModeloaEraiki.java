package ehes.praktika6.inferentzia;

import ehes.praktika6.ModeloOperazioak;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;

public class ModeloaEraiki {

	public void ebaluazioEzZintzoa(Instances data, Classifier sailkatzailea, String path) throws Exception {
		System.out.println("\nEbaluazio ez zintzoa:");
		sailkatzailea.buildClassifier(data);
		Evaluation ebaluatzailea = new Evaluation(data);
		ebaluatzailea.evaluateModel(sailkatzailea, data);
		this.inprimatuWeka(ebaluatzailea, sailkatzailea);
		ModeloOperazioak mo = new ModeloOperazioak();
		mo.modeloaIdatzi(path, sailkatzailea);
	}

	private void inprimatuWeka(Evaluation ebaluatzailea, Classifier sailkatzailea) throws Exception {
		System.out.println("\n=== Summary ===");
		System.out.println(ebaluatzailea.toSummaryString());
		System.out.println(ebaluatzailea.toClassDetailsString());
		System.out.println(ebaluatzailea.toMatrixString());
	}

	public void trainVStest(Instances test, Instances train, Classifier sailkatzailea) throws Exception {
		System.out.println("\nTrain vs test:");
		Evaluation ebaluatzailea = new Evaluation(test);
		sailkatzailea.buildClassifier(train);
		ebaluatzailea.evaluateModel(sailkatzailea, test);
		this.inprimatuWeka(ebaluatzailea, sailkatzailea);
	}
}
