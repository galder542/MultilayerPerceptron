package ehes.praktika6.preprozesatu;

import weka.attributeSelection.AttributeSelection;
import weka.attributeSelection.InfoGainAttributeEval;
import weka.attributeSelection.Ranker;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.InterquartileRange;
import weka.filters.unsupervised.attribute.Normalize;
import weka.filters.unsupervised.attribute.Remove;
import weka.filters.unsupervised.instance.RemoveWithValues;

//Klase hau, instantziei filtroak aplikatzeko erabiltzen da, preprozesamendua: Filtroak + InfoGain Hautaketa:


public class Prozesatzailea {
	private Instances atributuakKendu(Instances data, int[] lista) throws Exception {
		Remove r = new Remove();
		r.setAttributeIndicesArray(lista);
		r.setInvertSelection(true);
		r.setInputFormat(data);
		return Filter.useFilter(data, r);
	}

	public Instances filtroakIpini(Instances data) throws Exception {
		InterquartileRange ir = new InterquartileRange();
		ir.setInputFormat(data);
		ir.setExtremeValuesAsOutliers(true);
		data = Filter.useFilter(data, ir);
		RemoveWithValues rmv = new RemoveWithValues();
		rmv.setAttributeIndex(String.valueOf(data.numAttributes() - 2));
		rmv.setInputFormat(data);
		data = Filter.useFilter(data, rmv);
		Remove rm = new Remove();
		rm.setInputFormat(data);
		rm.setAttributeIndices(
				new String(String.valueOf(data.numAttributes() - 1) + "," + String.valueOf(data.numAttributes() - 2)));
		data = Filter.useFilter(data, rm);
		return data;
	}

	public Instances infoGainAE(Instances data) throws Exception {
		AttributeSelection attSelection = new AttributeSelection();
		InfoGainAttributeEval igae = new InfoGainAttributeEval();
		Ranker r = new Ranker();
		data.setClassIndex(data.numAttributes() - 1);
		r.setNumToSelect(-1);
		r.setThreshold(0.25);
		attSelection.setSearch(r);
		attSelection.setEvaluator(igae);
		attSelection.SelectAttributes(data);
		int[] lista = attSelection.selectedAttributes();
		return this.atributuakKendu(data, lista);
	}

	public Instances normalize(Instances data) throws Exception {
		Normalize n = new Normalize();
		n.setInputFormat(data);
		return Filter.useFilter(data, n);
	}
}
