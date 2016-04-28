package ehes.praktika6;

import java.io.FileNotFoundException;
import java.io.IOException;

import ehes.praktika6.inferentzia.ArtificialNeuralNetworks;
import ehes.praktika6.inferentzia.Baseline;
import ehes.praktika6.preprozesatu.Prozesatzailea;
import weka.core.Instances;

public class Nagusia {
	private Instances train, test, osoa;
	private String[] argumentuak;
	private InstantziaOperazioak io;
	private Prozesatzailea p;
	private Baseline b;
	ArtificialNeuralNetworks ann;

	public static void main(String[] args) throws Exception {
		if (args.length < 2) {
			System.err.println(
					"Programak funtzaionatzeko train eta test fitxategien path-ak behar ditu, edozein ordenetan");
			System.exit(1);
		}
		Nagusia n = new Nagusia(args);
		n.hasieratu();
	}

	public Nagusia(String[] args) {
		this.argumentuak = args;
		io = new InstantziaOperazioak();
		p = new Prozesatzailea();
		b = new Baseline();
		ann = new ArtificialNeuralNetworks();
	}

	private void hasieratu() throws Exception {
		this.instantziakKargatu();
		osoa = new Instances(test);
		this.laburpena(":");
		this.klaseaIpini();
		this.test = p.randomize(test);
		this.train = p.randomize(train);
		this.laburpena(" randomize aplikatu eta gero:");
		osoa = new Instances(test);
		osoa.addAll(train);
		this.klaseaIpini();
		osoa = p.normalize(osoa);
		this.laburpena(" normalize aplikatu eta gero:");
		osoa = p.infoGainAE(osoa);
		this.laburpena(" InfoGainAttributeEval aplikatu eta gero:");
		this.instantziakBanatu();
		this.instantziakGorde("filtratuta");
		this.laburpena(" instantziak banatu eta gero:");
		this.klaseaIpini();
		b.baselineEgin(test, train, argumentuak[0]);
		ann.parametroakEkortu(osoa, test, train, argumentuak[0]);
	}

	private void instantziakKargatu() throws FileNotFoundException, IOException {
		for (int i = 0; i < argumentuak.length; i++) {
			if (argumentuak[i].contains("test"))
				this.test = io.instantziakKargatu(argumentuak[i]);
			else
				this.train = io.instantziakKargatu(argumentuak[i]);
		}
	}

	private void laburpena(String mezua) {
		System.out.println("Instantzien laburpena" + mezua);
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

	private void instantziakGorde(String ipintzeko) throws IOException {
		for (int i = 0; i < argumentuak.length; i++) {
			if (argumentuak[i].contains("test"))
				io.instantziakGorde(test, argumentuak[i], ipintzeko);
			else
				io.instantziakGorde(train, argumentuak[i], ipintzeko);
		}
	}

	private void klaseaIpini() {
		test.setClassIndex(test.numAttributes() - 1);
		train.setClassIndex(train.numAttributes() - 1);
		osoa.setClassIndex(osoa.numAttributes() - 1);
	}

	private void instantziakBanatu() {
		test = new Instances(osoa, 0, test.numInstances() - 1);
		train = new Instances(osoa, test.numInstances(), test.numInstances() - 1);
	}
}
