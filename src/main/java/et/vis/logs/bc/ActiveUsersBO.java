package et.vis.logs.bc;

import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;

import scala.Tuple2;

public class ActiveUsersBO {

	public static void main(String[] args) {
		SparkConf sparkConf = VISSparkConf.getSparkConf("activeUser");
		JavaSparkContext jsc = new JavaSparkContext(sparkConf);

		JavaRDD<String> dataSet = jsc.textFile("E:\\access.log");

		JavaPairRDD<String, Integer> extracted = dataSet.mapToPair(new PairFunction<String, String, Integer>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public Tuple2<String, Integer> call(String s) {
				return new Tuple2<String, Integer>(LogBO.getLogObject(s).getUser_id(), 1);
			}
		});

		JavaPairRDD<String, Integer> counts = extracted.reduceByKey(new Function2<Integer, Integer, Integer>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 191730080104591695L;

			public Integer call(Integer one, Integer other) {
				return one + other;
			}
		});
		
		List<Tuple2<String, Integer>> output = counts.sortByKey().collect();
		for (Tuple2<?, ?> t : output) {
			System.out.println(t._1() + "\t" + t._2());
		}
		jsc.stop();
		jsc.close();
	}
}
