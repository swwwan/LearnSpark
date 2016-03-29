/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle;

import scala.Tuple2;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Executes a roll up-style query against Apache logs.
 * 
 * Usage: JavaLogQuery [logFile]
 */
public final class JavaLogQuery {

	static String regex1 = "^([\\d.]+)"; // Client IP
	static String regex2 = " \"([^\"]+)\""; // corpId
	static String regex3 = " \"([^\"]+)\""; // userId
	static String regex4 = " \\[([\\w:/]+\\s[+\\-]\\d{4})\\]"; // Date
	static String regex5 = " \"(.+?)\""; // request method and url
	static String regex6 = " (\\d{3})"; // HTTP code
	static String regex7 = " (\\d+)"; // Number of bytes
	static String regex8 = " \"([^\"]+)\""; // Referer
	static String regex9 = " \"([^\"]+)\""; // Agent
	static String regex = regex1 + regex2 + regex3 + regex4 + regex5 + regex6 + regex7 + regex8 + regex9;
	public static final Pattern nginxLogRegex = Pattern.compile(regex);

	public static String extractKey(String line) {
		Matcher m = nginxLogRegex.matcher(line);
		String user = null;
		if (m.find()) {
			user = m.group(3);
			if (!user.equals("-")) {
				return user;
			}
		}
		return user;
	}

	@SuppressWarnings("resource")
	public static void main(String[] args) {

		SparkConf sparkConf = new SparkConf().setMaster("local").setAppName("JavaLogQuery");
		JavaSparkContext jsc = new JavaSparkContext(sparkConf);

		JavaRDD<String> dataSet = jsc.textFile("E:\\access.log");

		JavaPairRDD<String, Integer> extracted = dataSet.mapToPair(new PairFunction<String, String, Integer>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public Tuple2<String, Integer> call(String s) {
				return new Tuple2<String, Integer>(extractKey(s), 1);
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

		List<Tuple2<String, Integer>> output = counts.collect();
		for (Tuple2<?, ?> t : output) {
			System.out.println(t._1() + "\t" + t._2());
		}
		jsc.stop();
	}
}
