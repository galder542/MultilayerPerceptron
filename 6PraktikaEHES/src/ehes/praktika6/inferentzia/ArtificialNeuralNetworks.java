package ehes.praktika6.inferentzia;

import java.util.Random;

import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.meta.CVParameterSelection;
import weka.core.Instances;
import weka.core.Utils;

public class ArtificialNeuralNetworks implements Sailkatzailea {
	private MultilayerPerceptron mp;

	public ArtificialNeuralNetworks() {
		mp = new MultilayerPerceptron();
	}

	private void annEgin(Instances test, Instances train, Instances osoa, String path, String[] aukeraHoberenak,
			boolean ezZintzoa, int denbora) throws Exception {
		System.out.println("MultilayerPerceptron aplikatu:");
		ModeloaEraiki m = new ModeloaEraiki();
		if (ezZintzoa)
			m.ebaluazioEzZintzoa(osoa, this.sailkatzaileaEraiki(aukeraHoberenak, denbora), path);
		m.trainVStest(test, train, this.sailkatzaileaEraiki(aukeraHoberenak, denbora));
	}

	private void parametroakEkortu(Instances osoa, Instances test, Instances train, String path, boolean ezZintzoa,
			int kop) throws Exception {
		System.out.println("\nMultilayerPerceptron sailkatzailearen parametro egokienak bilatzen...");
		CVParameterSelection cv = new CVParameterSelection();
		cv.addCVParameter("L 0 1 6");
		cv.addCVParameter("M 0 1 6");
		cv.setSeed(new Random().nextInt(Integer.MAX_VALUE));
		cv.setNumDecimalPlaces(2);
		cv.setNumFolds(kop);
		cv.setSeed(new Random().nextInt(100));
		cv.setClassifier(mp);
		this.mp.setTrainingTime(kop / 2);
		cv.buildClassifier(osoa);
		String[] hoberenak = cv.getBestClassifierOptions();
		String aukerak = Utils.joinOptions(hoberenak);
		System.out.println("Parametro egokienak: " + aukerak);
		this.annEgin(test, train, osoa, path, hoberenak, ezZintzoa, kop * 10);
	}

	private MultilayerPerceptron sailkatzaileaEraiki(String[] aukerak, int denbora) throws Exception {
		this.mp = new MultilayerPerceptron();
		this.mp.setOptions(aukerak);
		this.mp.setTrainingTime(denbora);
		return this.mp;
	}

	public void sailkatzaileaLortu(Instances osoa, Instances test, Instances train, String path, boolean ezZintzoa,
			int kop) {
		try {
			this.parametroakEkortu(osoa, test, train, path, ezZintzoa, kop);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
