package ehes.praktika6;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.lang3.time.StopWatch;

import ehes.praktika6.inferentzia.ArtificialNeuralNetworks;
import ehes.praktika6.inferentzia.Baseline;
import ehes.praktika6.preprozesatu.Prozesatzailea;
import weka.core.Instances;

public class Nagusia {
	public static void main(String[] args) throws Exception {
		if (args.length < 2) {
			System.err.println(
					"Programak funtzaionatzeko train, test fitxategien path-ak eta algoritmo bat behar ditu gutxienez, edozein ordenetan.");
			System.err.println("Baita ebaluazio ez zintzoa egin dezakezu '-e' parametroa erabiliz.");
			System.err.println("Modeloa gordetzeko ebaluazio ez zintzoa gaituta egon behar da.");
			System.err.println("\nErabili daitezkeen algoritmoak: (konbinatu daitezke)");
			System.err.println(" - '-a': Artificial Neural Network, Multilayer Perceptron");
			System.err.println(" - '-s': Support Vector Machine");
			System.exit(1);
		}
		Nagusia n = new Nagusia(args);
		n.hasieratu();
	}

	private Instances train, test, osoa;
	private String[] argumentuak;
	private InstantziaOperazioak io;
	private Prozesatzailea p;
	private Baseline b;
	private ArtificialNeuralNetworks ann;
	private boolean ezZintzoa;
	private StopWatch s;

	public Nagusia(String[] args) {
		this.argumentuak = args;
		io = new InstantziaOperazioak();
		p = new Prozesatzailea();
		b = new Baseline();
		ann = new ArtificialNeuralNetworks();
		s = new StopWatch();
		ezZintzoa = false;
	}

	private void ezZintzoa() {
		for (int i = 0; i < argumentuak.length; i++) {
			if (argumentuak[i].toLowerCase().contains("-e")) {
				ezZintzoa = true;
				System.out.println("Ebaluazio ez zintzoa egingo da eta modeloak gordeko dira.\n");
				break;
			}
		}
		if (!ezZintzoa)
			System.out.println("Ebaluazio ez zintzoa desgaituta dago.\n");
	}

	private void hasieratu() throws Exception {
		s.start();
		this.ezZintzoa();
		this.instantziakKargatu();
		osoa = new Instances(test);
		this.laburpena(":");
		this.klaseaIpini();
		osoa = new Instances(test);
		osoa.addAll(train);
		this.klaseaIpini();
		osoa = p.normalize(osoa);
		this.laburpena(" normalize aplikatu eta gero:");
		osoa = p.infoGainAE(osoa);
		this.laburpena(" InfoGainAttributeEval aplikatu eta gero:");
		this.instantziakBanatu();
		this.laburpena(" instantziak banatu eta gero:");
		this.instantziakGorde("filtratuta");
		this.klaseaIpini();
		b.baselineEgin(osoa, test, train, argumentuak[0], ezZintzoa);
		ann.parametroakEkortu(osoa, test, train, argumentuak[0], ezZintzoa);
		s.stop();
		System.out.println("Programak behar izan duen denbora modeloa lortzeko: " + (s.getTime() / 1000) + "s");
	}

	private void instantziakBanatu() {
		test = new Instances(osoa, 0, test.numInstances() - 1);
		train = new Instances(osoa, test.numInstances(), test.numInstances() - 1);
	}

	private void instantziakGorde(String ipintzeko) throws IOException {
		for (int i = 0; i < argumentuak.length; i++) {
			if (argumentuak[i].toLowerCase().contains("test"))
				io.instantziakGorde(test, argumentuak[i], ipintzeko);
			else if (argumentuak[i].toLowerCase().contains("train"))
				io.instantziakGorde(train, argumentuak[i], ipintzeko);
		}
	}

	private void instantziakKargatu() throws FileNotFoundException, IOException {
		for (int i = 0; i < argumentuak.length; i++) {
			if (argumentuak[i].toLowerCase().contains("test"))
				this.test = io.instantziakKargatu(argumentuak[i]);
			else if (argumentuak[i].toLowerCase().contains("train"))
				this.train = io.instantziakKargatu(argumentuak[i]);
		}
	}

	private void klaseaIpini() {
		test.setClassIndex(test.numAttributes() - 1);
		train.setClassIndex(train.numAttributes() - 1);
		osoa.setClassIndex(osoa.numAttributes() - 1);
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
		System.out.println("Atributu Kopurua: " + osoa.numAttributes());
		System.out.println("Instantzia Kopurua: " + osoa.numInstances() + "\n");
	}
}
