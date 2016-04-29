package ehes.praktika6.inferentzia;

import java.util.Random;

import weka.classifiers.functions.LibSVM;
import weka.classifiers.meta.CVParameterSelection;
import weka.core.Instances;
import weka.core.Utils;

public class SupportVectorMachine {
	public void svmEkortu(Instances osoa, Instances train, Instances test, String path, boolean ezZintzoa)
			throws Exception {
		System.out.println("\nSupport Vector Machineren parametroak ekortu:");
		LibSVM svm = new LibSVM();
		CVParameterSelection cv = new CVParameterSelection();
		cv.setClassifier(svm);
		cv.setNumFolds(3);
		cv.addCVParameter("G 0.01 0.1 5");
		cv.addCVParameter("K 0 3 4");
		cv.addCVParameter("D 0 5 6");
		cv.setSeed(new Random().nextInt(100));
		cv.setNumDecimalPlaces(2);
		cv.buildClassifier(osoa);
		String[] hoberenak = cv.getBestClassifierOptions();
		System.out.println("SVMren parametro hoberenak:");
		System.out.println(Utils.joinOptions(hoberenak));
		this.svmLortu(osoa, test, train, path, ezZintzoa, hoberenak);
	}

	private void svmLortu(Instances osoa, Instances test, Instances train, String path, boolean ezZintzoa,
			String[] hoberenak) throws Exception {
		ModeloaEraiki m = new ModeloaEraiki();
		LibSVM svm = new LibSVM();
		svm.setOptions(hoberenak);
		if (ezZintzoa)
			m.ebaluazioEzZintzoa(osoa, svm, path);
		m.trainVStest(test, train, svm);
	}
}
