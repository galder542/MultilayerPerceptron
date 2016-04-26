package ehes.praktika6.inferentzia;

import java.util.Random;

import ehes.praktika6.ModeloOperazioak;
import ehes.praktika6.Praktika6Constants;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.instance.Randomize;

public class ModeloaEraiki {

	public void baselineEgin(Instances data, Instances dev, Instances train, String path) throws Exception {

	}

	public void crossValidation(Instances data, Classifier sailkatzailea) throws Exception {
		System.out.println("\n" + Praktika6Constants.CROSS_VALIDATE_ROUNDS + " fold cross validation:");
		Evaluation ebaluatzailea = new Evaluation(data);
		ebaluatzailea.crossValidateModel(sailkatzailea, data, Praktika6Constants.CROSS_VALIDATE_ROUNDS, new Random());
		this.inprimatuWeka(ebaluatzailea, sailkatzailea);
	}

	public void ebaluazioEzZintzoa(Instances data, Classifier sailkatzailea, String path) throws Exception {
		System.out.println("\nEbaluazio ez zintzoa:");
		sailkatzailea.buildClassifier(data);
		Evaluation ebaluatzailea = new Evaluation(data);
		ebaluatzailea.evaluateModel(sailkatzailea, data);
		this.inprimatuWeka(ebaluatzailea, sailkatzailea);
		ModeloOperazioak mo = new ModeloOperazioak();
		mo.modeloaIdatzi(path, sailkatzailea);
	}

	public void holdOut7030(Instances data, Classifier sailkatzailea) throws Exception {
		Randomize r = new Randomize();
		r.setInputFormat(data);
		Instances randomized = Filter.useFilter(data, r);
		int hasiera = (int) (data.numInstances() * 0.7);
		int kopiatzeko = (int) (data.numInstances() * 0.3) - 1;
		Instances probatzeko = new Instances(randomized, hasiera, kopiatzeko);
		for (int i = data.numInstances() - 1; i > hasiera; --i) {
			randomized.remove(i);
		}
		System.out.println("\nHold-out 70/30:");
		sailkatzailea.buildClassifier(randomized);
		Evaluation ebaluatzailea = new Evaluation(probatzeko);
		ebaluatzailea.evaluateModel(sailkatzailea, probatzeko);
		this.inprimatuWeka(ebaluatzailea, sailkatzailea);
	}

	private void inprimatuWeka(Evaluation ebaluatzailea, Classifier sailkatzailea) throws Exception {
		System.out.println("\n=== Summary ===");
		System.out.println(ebaluatzailea.toSummaryString());
		System.out.println(ebaluatzailea.toClassDetailsString());
		System.out.println(ebaluatzailea.toMatrixString());
	}

	public void trainVStest(Instances test, Instances train, Classifier sailkatzailea) throws Exception {
		System.out.println("\nTrain vs dev:");
		Evaluation ebaluatzailea = new Evaluation(test);
		sailkatzailea.buildClassifier(train);
		ebaluatzailea.evaluateModel(sailkatzailea, test);
		this.inprimatuWeka(ebaluatzailea, sailkatzailea);
	}
}
