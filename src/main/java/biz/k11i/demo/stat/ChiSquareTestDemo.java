package biz.k11i.demo.stat;

import org.apache.commons.math3.stat.inference.ChiSquareTest;

import java.util.Arrays;
import java.util.Random;

/**
 * カイ二乗検定のデモ
 */
public class ChiSquareTestDemo {
	public static void main(String[] args) {
		doTest(rollFairDice(10_000), 0.05);
		doTest(rollUnfairDice(10_000), 0.05);
	}

	static long[] rollFairDice(int times) {
		Random rnd = new Random();

		long[] result = new long[6];
		for (int i = 0; i < times; i++) {
			result[rnd.nextInt(6)]++;
		}

		return result;
	}

	static long[] rollUnfairDice(int times) {
		Random rnd = new Random();

		long[] result = new long[6];
		for (int i = 0; i < times; i++) {
			if (i % 10 == 0) {
				result[0]++;
			} else {
				result[rnd.nextInt(6)]++;
			}
		}

		return result;
	}

	static void doTest(long[] observed, double alpha) {
		double[] expected = new double[6];
		Arrays.fill(expected, 1.0 / 6);

		ChiSquareTest test = new ChiSquareTest();
		boolean result = test.chiSquareTest(expected, observed, alpha);

		System.out.printf("有意水準 %.3f%% での観測結果 %s のカイ二乗検定の結果は %s です。\n",
				alpha * 100,
				Arrays.toString(observed),
				result);

		if (result) {
			System.out.println("よって「さいころの各目が出る確率が等しい」という帰無仮説は棄却されます。");
		} else {
			System.out.println("よって「さいころの各目が出る確率が等しい」という帰無仮説は棄却されません。");
		}
	}
}
