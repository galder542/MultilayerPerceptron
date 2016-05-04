package ehes.praktika6.inferentzia;

import weka.core.Instances;

public interface Sailkatzailea {
	public void sailkatzaileaLortu(Instances osoa, Instances test, Instances train, String path, boolean ezZintzoa, int kop);

}
