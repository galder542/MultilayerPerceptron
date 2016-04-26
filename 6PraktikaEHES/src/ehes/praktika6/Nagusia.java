package ehes.praktika6;

import java.io.FileNotFoundException;
import java.io.IOException;

import ehes.praktika6.inferentzia.Baseline;
import ehes.praktika6.preprozesatu.Prozesatzailea;
import weka.core.Instances;

public class Nagusia {
	private Instances train, test;
	private String[] argumentuak;
	private InstantziaOperazioak io;
	private Prozesatzailea p;
	private Baseline b;

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
	}

	private void hasieratu() throws Exception {
		this.instantziakKargatu();
		this.laburpena(":");
		this.test.setClassIndex(test.numAttributes() - 1);
		this.train.setClassIndex(train.numAttributes() - 1);
		this.test = p.randomize(test);
		this.train = p.randomize(train);
		this.laburpena(" randomize aplikatu eta gero:");
		this.test = p.normalize(test);
		this.train = p.normalize(train);
		this.laburpena(" normalize aplikatu eta gero:");
		test = p.infoGainAE(test);
		train = p.infoGainAE(train);
		this.laburpena(" InfoGainAttributeEval aplikatu eta gero:");
		this.instantziakGorde("proba");
		b.baselineEgin(test, train, argumentuak[0]);
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
		System.out.println("Instantzia Kopurua: " + test.numInstances() + "\n");

	}

	private void instantziakGorde(String ipintzeko) throws IOException {
		for (int i = 0; i < argumentuak.length; i++) {
			if (argumentuak[i].contains("test"))
				io.instantziakGorde(test, argumentuak[i], ipintzeko);
			else
				io.instantziakGorde(train, argumentuak[i], ipintzeko);
		}
	}
}
