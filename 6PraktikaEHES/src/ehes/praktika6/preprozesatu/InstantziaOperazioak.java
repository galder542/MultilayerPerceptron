package ehes.praktika6.preprozesatu;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import weka.core.Instances;

public class InstantziaOperazioak {
	public Instances instantziakKargatu(String path) throws FileNotFoundException, IOException {
		return new Instances(new BufferedReader(new FileReader(new File(path))));
	}

	public void instantziakGorde(Instances data, String path, String ipintzeko) throws IOException {
		BufferedWriter bw = new BufferedWriter(
				new FileWriter(new File(path.replace(".arff", "_" + ipintzeko + ".arff"))));
		bw.write(data.toString());
		bw.close();
	}
}
