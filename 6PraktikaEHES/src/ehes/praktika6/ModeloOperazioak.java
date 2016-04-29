package ehes.praktika6;

import weka.classifiers.Classifier;
import weka.core.SerializationHelper;

public class ModeloOperazioak {

	public void modeloaIdatzi(String path, Classifier sailkatzailea) throws Exception {
		SerializationHelper.write(this.modeloarenPathLortu(path, sailkatzailea), sailkatzailea);
	}

	private String modeloarenPathLortu(String pathZaharra, Classifier sailkatzailea) {
		for (int i = pathZaharra.length() - 1; i > 0; --i) {
			if (pathZaharra.charAt(i) == '/' || pathZaharra.charAt(i) == '\\') {
				return pathZaharra.substring(0, i) + "/" + sailkatzailea.getClass().getSimpleName() + ".model";
			}
		}
		return new String();
	}
}
