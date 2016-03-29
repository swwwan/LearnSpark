package et.vis.logs.bc;

import org.apache.spark.SparkConf;

public class VISSparkConf {
	public static SparkConf getSparkConf(String appName) {
		return new SparkConf().setMaster("local").setAppName(appName);
	}
}
