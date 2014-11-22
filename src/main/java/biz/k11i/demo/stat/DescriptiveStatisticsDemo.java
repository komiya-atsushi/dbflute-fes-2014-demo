package biz.k11i.demo.stat;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static java.lang.System.out;

/**
 * 基本統計量・５数要約の例
 */
public class DescriptiveStatisticsDemo {
	public static void main(String[] args) throws IOException {
		DescriptiveStatistics stat = new DescriptiveStatistics();

		// 年俸の基本統計量を求めようとしています
		Files.readAllLines(new File("data/baseball-salary-g.txt").toPath())
				.stream()
				.filter(line -> !line.startsWith("#"))
				.map(line -> line.split("\t")[0].replace(",", "").replace("万円", ""))
				.forEach(line -> stat.addValue(Integer.parseInt(line)));

		// 基本統計量
		out.println("-----");
		out.printf("サンプルサイズ : %d\n", stat.getN());
		out.printf("平均　　　　　 : %.2f\n", stat.getMean());
		out.printf("分散　　　　　 : %.2f\n", stat.getVariance());
		out.printf("標準偏差　　　 : %.2f\n", stat.getStandardDeviation());

		// 五数要約 (順序統計量)
		out.println("-----");
		out.printf("最小値　　　 : %.0f\n", stat.getMin());
		out.printf("第１四分位点 : %.2f\n", stat.getPercentile(25));
		out.printf("中央値　　　 : %.2f\n", stat.getPercentile(50));
		out.printf("第３四分位点 : %.2f\n", stat.getPercentile(75));
		out.printf("最大値　　　 : %.0f\n", stat.getMax());

		// もしくは単純に…
		out.println("-----");
		out.println(stat.toString());
	}
}

