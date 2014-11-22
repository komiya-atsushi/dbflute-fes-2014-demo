package biz.k11i.demo.stat;

import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 相関係数のデモ。
 */
public class CorrelationDemo {
	public static void main(String[] args) throws IOException {
		List<double[]> rows = Files.readAllLines(new File("data/beer.txt").toPath())
				.stream()
				.filter(line -> !line.startsWith("#"))
				.map(line -> {
					String[] elems = line.split("\t");
					return new double[]{
							Double.parseDouble(elems[0]),
							Double.parseDouble(elems[1])
					};
				})
				.collect(Collectors.toList());
		double[] temperature = rows.stream()
				.mapToDouble(row -> row[0])
				.toArray();
		double[] sales = rows.stream()
				.mapToDouble(row -> row[1])
				.toArray();

		PearsonsCorrelation correlation = new PearsonsCorrelation();
		System.out.printf("気温と売上の相関係数 : %.3f\n", correlation.correlation(temperature, sales));
	}
}
