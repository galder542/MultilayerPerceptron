package ehes.praktika6.preprozesatu;

import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.InterquartileRange;
import weka.filters.unsupervised.attribute.Normalize;
import weka.filters.unsupervised.attribute.Remove;
import weka.filters.unsupervised.instance.Randomize;
import weka.filters.unsupervised.instance.RemoveWithValues;

public class Prozesatzailea {
	public Instances randomize(Instances data) throws Exception {
		Randomize r = new Randomize();
		r.setInputFormat(data);
		return Filter.useFilter(data, r);
	}

	public Instances normalize(Instances data) throws Exception {
		Normalize n = new Normalize();
		n.setInputFormat(data);
		return Filter.useFilter(data, n);
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
}
