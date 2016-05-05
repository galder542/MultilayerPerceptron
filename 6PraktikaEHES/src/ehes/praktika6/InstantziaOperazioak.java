package ehes.praktika6;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import weka.core.Instances;

/*Instantziak gorde eta kargatzeko klasea da hau, gure programak, filtroak aplikatu ondoren, 
instantzia berriak gordetzen ditu.
*/
public class InstantziaOperazioak {
	public void instantziakGorde(Instances data, String path, String ipintzeko) throws IOException {
		String pathBerria = path.replace(".arff", "_" + ipintzeko + ".arff");
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(pathBerria)));
		System.out.println("Instantziak " + pathBerria + " karpetara idatziko dira.");
		bw.write(data.toString());
		bw.close();
	}

	public Instances instantziakKargatu(String path) throws FileNotFoundException, IOException {
		System.out.println("Instantziak " + path + " kargatuko dira.");
		return new Instances(new BufferedReader(new FileReader(new File(path))));
	}
}
