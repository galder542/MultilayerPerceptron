package ehes.praktika6;

import ehes.praktika6.preprozesatu.InstantziaOperazioak;
import ehes.praktika6.preprozesatu.Prozesatzailea;
import weka.core.Instances;

public class Nagusia {
	private Instances train, test;
	private String[] argumentuak;
	private InstantziaOperazioak io;
	private Prozesatzailea p;

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
	}

	private void hasieratu() throws Exception {
		for (int i = 0; i < argumentuak.length; i++) {
			if (argumentuak[i].contains("test"))
				this.test = io.instantziakKargatu(argumentuak[i]);
			else
				this.train = io.instantziakKargatu(argumentuak[i]);
		}
		this.test.setClassIndex(test.numAttributes() - 1);
		this.train.setClassIndex(train.numAttributes() - 1);
		this.test = p.randomize(test);
		this.train = p.randomize(train);
		this.test = p.normalize(test);
		this.train = p.normalize(train);
		
	}
}
