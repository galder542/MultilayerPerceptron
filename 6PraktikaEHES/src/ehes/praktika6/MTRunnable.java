package ehes.praktika6;

import ehes.praktika6.inferentzia.Sailkatzailea;
import weka.core.Instances;

public class MTRunnable implements Runnable {
	private Sailkatzailea s;
	private Instances osoa, test, train;
	private String path;
	private boolean ezZintzoa;
	private int kop;

	public MTRunnable(Sailkatzailea s, Instances osoa, Instances test, Instances train, String path, boolean ezZintzoa,
			int kop) {
		this.s = s;
		this.osoa = osoa;
		this.train = train;
		this.test = test;
		this.path = path;
		this.ezZintzoa = ezZintzoa;
		this.kop = kop;
	}

	public void run() {
		System.out.println(s.getClass());
		s.sailkatzaileaLortu(osoa, test, train, path, ezZintzoa, kop);
	}

}
