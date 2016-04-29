package ehes.praktika6.inferentzia;

import java.util.Random;

import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.meta.CVParameterSelection;
import weka.core.Instances;
import weka.core.Utils;

public class ArtificialNeuralNetworks {
	private MultilayerPerceptron mp;

	public ArtificialNeuralNetworks() {
		mp = new MultilayerPerceptron();
	}

	private void annEgin(Instances test, Instances train, Instances osoa, String path, String[] aukeraHoberenak)
			throws Exception {
		System.out.println("MultilayerPerceptron aplikatu:");
		ModeloaEraiki m = new ModeloaEraiki();
		m.ebaluazioEzZintzoa(osoa, this.sailkatzaileaEraiki(aukeraHoberenak), path);
		m.trainVStest(test, train, this.sailkatzaileaEraiki(aukeraHoberenak));
	}

	public void parametroakEkortu(Instances osoa, Instances test, Instances train, String path) throws Exception {
		System.out.println("\nMultilayerPerceptron sailkatzailearen parametro egokienak bilatzen...");
		CVParameterSelection cv = new CVParameterSelection();
		cv.addCVParameter("L 0 1 6");
		cv.addCVParameter("M 0 1 6");
		cv.setSeed(new Random().nextInt(Integer.MAX_VALUE));
		cv.setNumDecimalPlaces(2);
		cv.setNumFolds(2);
		cv.setClassifier(mp);
		this.mp.setTrainingTime(1);
		cv.buildClassifier(osoa);
		String[] hoberenak = cv.getBestClassifierOptions();
		String aukerak = Utils.joinOptions(hoberenak);
		System.out.println("Parametrorik egokienak: " + aukerak);
		this.annEgin(test, train, osoa, path, hoberenak);
	}

	private MultilayerPerceptron sailkatzaileaEraiki(String[] aukerak) throws Exception {
		this.mp = new MultilayerPerceptron();
		this.mp.setOptions(aukerak);
		this.mp.setTrainingTime(30);
		return this.mp;
	}
}
