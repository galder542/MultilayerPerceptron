package ehes.praktika6;

public class Aukeratzailea {
	public void aukeratu(Nagusia nireNagusia) {
		String[] args = nireNagusia.getArgumentuak();
		for (int i = 0; i < args.length; i++) {
			if (args[i].toLowerCase().contains("-e")) {
				nireNagusia.setEzZintzoa(true);
				System.out.println("Ebaluazio ez zintzoa egingo da, eta honen modeloak gordeko dira.");
			} else if (args[i].toLowerCase().contains("-a")) {
				nireNagusia.setAnnEgin(true);
				System.out.println("MultilayerPerceptron sailkatzailea ekortuko da.");
			} else if (args[i].toLowerCase().contains("-s")) {
				nireNagusia.setSvmEgin(true);
				System.out.println("Support Vector Machine sailkatzailea ekortuko da.");
			} else if (args[i].toLowerCase().contains("-train")) {
				nireNagusia.setTrainPath(args[i + 1]);
			} else if (args[i].toLowerCase().contains("-test")) {
				nireNagusia.setTestPath(args[i + 1]);
			} else if (args[i].toLowerCase().contains("-b")) {
				nireNagusia.setBaselineEgin(true);
				System.out.println("NaiveBayes sailkatzailea erabiliko da baselinea egiteko.");
			}
		}
		if (nireNagusia.getTestPath() == null || nireNagusia.getTrainPath() == null) {
			System.err.println("Train edo test fitxategiaren path-a ez da lortu!");
			System.exit(1);
		}
	}

}
