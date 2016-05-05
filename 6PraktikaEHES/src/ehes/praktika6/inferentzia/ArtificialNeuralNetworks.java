package ehes.praktika6.inferentzia;

import java.util.Random;

import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.meta.CVParameterSelection;
import weka.core.Instances;
import weka.core.Utils;

/*Klase honen helburua, gure Multilayer Perceptron-aren parametro ekorketa burutzea da 
eta era berean ModeloaEraiki klasea deitu egiten du. Modeloaren inferentzia egiten da.*/

public class ArtificialNeuralNetworks {
	private MultilayerPerceptron mp;

	public ArtificialNeuralNetworks() {
		mp = new MultilayerPerceptron();
	}

	private void annEgin(Instances test, Instances train, Instances multzoOsoa, String path,
			String[] parametroHoberenak) throws Exception {
		System.out.println("MultilayerPerceptron aplikatu:");
		ModeloaEraiki m = new ModeloaEraiki();
		m.trainVStest(test, train, this.sailkatzaileaEraiki(parametroHoberenak, 60));
	}

	public void parametroakEkortu(Instances multzoOsoa, Instances test, Instances train, String path, int kop)
			throws Exception {
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
		cv.buildClassifier(multzoOsoa);
		String[] parametroHoberenak = cv.getBestClassifierOptions();
		String aukerak = Utils.joinOptions(parametroHoberenak);
		System.out.println("Parametro egokienak: " + aukerak);
		this.annEgin(test, train, multzoOsoa, path, parametroHoberenak);
	}

	private MultilayerPerceptron sailkatzaileaEraiki(String[] aukerak, int denbora) throws Exception {
		this.mp = new MultilayerPerceptron();
		this.mp.setOptions(aukerak);
		this.mp.setTrainingTime(denbora);
		return this.mp;
	}

	public void sailkatzaileaLortu(Instances multzoOsoa, Instances test, Instances train, String path, int kop) {
		try {
			this.parametroakEkortu(multzoOsoa, test, train, path, kop);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
