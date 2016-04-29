package ehes.praktika6;

public class Aukeratzailea {
	public void aukeratu(Nagusia nireNagusia) {
		String[] args = nireNagusia.getArgumentuak();
		System.out.println("Sartutako argumentuak:");
		for (int i = 0; i < args.length; i++) {
			System.out.println(args[i]);
			if (args[i].toLowerCase().contains("-e"))
				nireNagusia.setEzZintzoa(true);
			else if (args[i].toLowerCase().contains("-a"))
				nireNagusia.setAnnEgin(true);
			else if (args[i].toLowerCase().contains("-s"))
				nireNagusia.setSvmEgin(true);
			else if (args[i].toLowerCase().contains("train"))
				nireNagusia.setTrainPath(args[i]);
			else if (args[i].toLowerCase().contains("test"))
				nireNagusia.setTestPath(args[i]);
			else if (args[i].toLowerCase().contains("-b"))
				nireNagusia.setBaselineEgin(true);
		}
		if (nireNagusia.getTestPath() == null || nireNagusia.getTrainPath() == null) {
			System.err.println("Train edo test fitxategiaren path-a ez da lortu!");
			System.exit(1);
		}
	}

}
