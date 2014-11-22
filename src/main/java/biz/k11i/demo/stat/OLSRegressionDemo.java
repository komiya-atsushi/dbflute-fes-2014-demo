package biz.k11i.demo.stat;

import org.apache.commons.math3.stat.regression.SimpleRegression;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static java.lang.System.out;

/**
 * 最小二乗法による線形回帰のデモ。
 */
public class OLSRegressionDemo {
	public static void main(String[] args) throws IOException {
		SimpleRegression regression = new SimpleRegression();

		Files.readAllLines(new File("data/beer.txt").toPath())
				.stream()
				.filter(line -> !line.startsWith("#"))
				.forEach(line -> {
					String[] elems = line.split("\t");

					double temperature = Double.parseDouble(elems[0]);
					double sales = Double.parseDouble(elems[1]);

					regression.addData(temperature, sales);
				});

		out.println("-----");
		out.printf("相関係数 : %.2f\n", regression.getR());
		out.printf("決定係数 : %.2f\n", regression.getRSquare());
		out.printf("標準誤差 : %.2f\n", regression.getSlopeStdErr());
		out.printf("傾き　　 : %.2f\n", regression.getSlope());
		out.printf("切片　　 : %.2f\n", regression.getIntercept());

		out.println("-----");
		double temperature = 30.0;
		out.printf("気温が %.2f ℃ のときの想定売上 : %.2f\n",
				temperature,
				regression.predict(temperature));

		temperature = 10.0;
		out.printf("気温が %.2f ℃ のときの想定売上 : %.2f\n",
				temperature,
				regression.predict(temperature));
	}
}
