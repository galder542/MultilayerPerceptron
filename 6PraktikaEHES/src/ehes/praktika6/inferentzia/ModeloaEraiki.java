package ehes.praktika6.inferentzia;


import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;

//Klase honen helburua, modeloak egiten dituen operazioak burutzea da, kasu honetan train vs test egingo dugu:

public class ModeloaEraiki {

	private void inprimatuWeka(Evaluation ebaluatzailea, Classifier sailkatzailea) throws Exception {
		System.out.println("\n=== Summary ===");
		System.out.println(ebaluatzailea.toSummaryString());
		System.out.println(ebaluatzailea.toClassDetailsString());
		System.out.println(ebaluatzailea.toMatrixString());
	}

	public void trainVStest(Instances test, Instances train, Classifier sailkatzailea) throws Exception {
		System.out.println("\n" + sailkatzailea.getClass().getSimpleName() + " sailkatzailearekin train vs test:");
		Evaluation ebaluatzailea = new Evaluation(test);
		sailkatzailea.buildClassifier(train);
		ebaluatzailea.evaluateModel(sailkatzailea, test);
		this.inprimatuWeka(ebaluatzailea, sailkatzailea);
	}
}
