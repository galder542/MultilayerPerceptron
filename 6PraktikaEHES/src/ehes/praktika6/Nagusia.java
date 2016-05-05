package ehes.praktika6;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.lang3.time.StopWatch;

import ehes.praktika6.inferentzia.ArtificialNeuralNetworks;
import ehes.praktika6.preprozesatu.Prozesatzailea;
import weka.core.Instances;

/*Programaren klase nagusia da, izenak adierazten duen moduan. Hemendik deitu egiten dira beste klaseak. 
  Hasteko, train eta test datasetak sartu behar ditugu parametro bidez.
 */
public class Nagusia {
	public static void main(String[] args) throws Exception {
		if (args.length < 2) {
			System.out.println(
					"Programa funtzionatzeko, test eta train datasetak behar dira, edozein ordenean sarturik!!.");
			System.out.println("Fitxategiak sartzeko, -test eta -train parametroak jarri behar dira dataseten path-en aurretik. Adibidez:");
			System.out.println("-test /path/test.arff -train /path/train.arff");
			System.exit(1);
		}
		Nagusia n = new Nagusia(args);
		n.hasieratu();
	}

	private Instances train, test, osoak;
	private String[] argumentuak;
	private InstantziaOperazioak iOperazioak;
	private Prozesatzailea prozes;
	private StopWatch stopWatch;
	private String testPath, trainPath;

	public Nagusia(String[] args) {
		this.argumentuak = args;
		iOperazioak = new InstantziaOperazioak();
		prozes = new Prozesatzailea();
		stopWatch = new StopWatch();
	}
	public String[] getArgumentuak() {
		return argumentuak;
	}

	public String getTestPath() {
		return testPath;
	}

	public String getTrainPath() {
		return trainPath;
	}

	private void hasieratu() throws Exception {
		for (int i = 0; i < argumentuak.length; i++) {
			if (argumentuak[i].toLowerCase().contains("-test")) {
				testPath=argumentuak[i+1];
				
			}else if (argumentuak[i].toLowerCase().contains("-train")) {
				trainPath=argumentuak[i+1];
				
			}
			
		}
		ArtificialNeuralNetworks ann = new ArtificialNeuralNetworks();
		stopWatch.start();
		this.instantziakKargatu();
		osoak = new Instances(test);
		this.laburpena(":");
		this.klaseaEzarri();
		osoak = new Instances(test);
		osoak.addAll(train);
		this.klaseaEzarri();
		osoak = prozes.normalize(osoak);
		this.laburpena(" Instantziak normalizatu ondoren:");
		osoak = prozes.infoGainAE(osoak);
		this.laburpena(" InfoGainAttributeEval aplikatu eta gero:");
		this.instantziakBanatu();
		this.laburpena(" Instantzien banaketa egin eta gero:");
		this.instantziakGorde("filtratuta");
		this.klaseaEzarri();
		ann.parametroakEkortu(osoak, test, train, trainPath,3);
		stopWatch.stop();
		System.out.println("Programak behar izan duen denbora modeloa lortzeko: " + (stopWatch.getTime() / 1000) + "s");
	}

	private void instantziakBanatu() {
		test = new Instances(osoak, 0, test.numInstances() - 1);
		train = new Instances(osoak, test.numInstances(), test.numInstances() - 1);
	}

	private void instantziakGorde(String ipintzeko) throws IOException {
		iOperazioak.instantziakGorde(test, testPath, ipintzeko);
		iOperazioak.instantziakGorde(train, trainPath, ipintzeko);
	}

	private void instantziakKargatu() throws FileNotFoundException, IOException {
		this.test = iOperazioak.instantziakKargatu(testPath);
		this.train = iOperazioak.instantziakKargatu(trainPath);
	}

	private void klaseaEzarri() {
		test.setClassIndex(test.numAttributes() - 1);
		train.setClassIndex(train.numAttributes() - 1);
		osoak.setClassIndex(osoak.numAttributes() - 1);
	}

	private void laburpena(String mezua) {
		System.out.println("\nInstantzien laburpena" + mezua);
		System.out.println("Train multzoa:");
		System.out.println("Atributu Kopurua: " + train.numAttributes());
		System.out.println("Instantzia Kopurua: " + train.numInstances());
		System.out.println("Test multzoa:");
		System.out.println("Atributu Kopurua: " + test.numAttributes());
		System.out.println("Instantzia Kopurua: " + test.numInstances());
		System.out.println("Multzo Osoa:");
		System.out.println("Atributu Kopurua: " + osoak.numAttributes());
		System.out.println("Instantzia Kopurua: " + osoak.numInstances() + "\n");
	}

	public void setTestPath(String testPath) {
		this.testPath = testPath;
	}

	public void setTrainPath(String trainPath) {
		this.trainPath = trainPath;
	}
}
