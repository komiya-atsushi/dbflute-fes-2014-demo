package biz.k11i.demo.ml;

import org.apache.commons.math3.ml.clustering.CentroidCluster;
import org.apache.commons.math3.ml.clustering.Clusterable;
import org.apache.commons.math3.ml.clustering.KMeansPlusPlusClusterer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * K-means++ によるクラスタリングのデモ。
 */
public class ClusteringDemo {
	// アヤメデータは以下からダウンロードしました
	// https://archive.ics.uci.edu/ml/datasets/Iris
	static class Iris implements Clusterable {
		final double[] values;
		final String label;

		Iris(String line) {
			String[] elems = line.split(",");

			this.values = Arrays.stream(elems, 0, 4)
					.mapToDouble(Double::parseDouble)
					.toArray();
			this.label = elems[4];
		}

		@Override
		public double[] getPoint() {
			return values;
		}

		public String getLabel() {
			return label;
		}
	}

	public static void main(String[] args) throws IOException {
		List<Iris> irisData = Files.readAllLines(new File("data/iris.txt").toPath())
				.stream()
				.map(Iris::new)
				.collect(Collectors.toList());
		Collections.shuffle(irisData);

		KMeansPlusPlusClusterer<Iris> clusterer = new KMeansPlusPlusClusterer<>(3);
		List<CentroidCluster<Iris>> result = clusterer.cluster(irisData);

		result.forEach(cluster -> {
			System.out.println("クラスタ中心 : " + cluster.getCenter().toString());
			cluster.getPoints().stream()
					.map(Iris::getLabel)
					.forEach(System.out::println);
		});
	}
}
