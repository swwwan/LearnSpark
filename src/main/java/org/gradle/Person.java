package org.gradle;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.list.GrowthList;

public class Person {
	private final String name;

	public Person(String name) {
		this.name = name;
		new GrowthList();
	}

	public String getName() {
		return name;
	}

	public static void main(String[] args) {
		String regex1 = "^([\\d.]+)"; // Client IP
		String regex2 = " \"([^\"]+)\""; // corpId
		String regex3 = " \"([^\"]+)\""; // userId
		String regex4 = " \\[([\\w:/]+\\s[+\\-]\\d{4})\\]"; // Date
		String regex5 = " \"(.+?)\""; // request method and url
		String regex6 = " (\\d{3})"; // HTTP code
		String regex7 = " (\\d+)"; // Number of bytes
		String regex8 = " \"([^\"]+)\""; // Referer
		String regex9 = " \"([^\"]+)\""; // Agent
	

		String regex =  regex1+regex2+regex3+regex4+regex5+regex6+regex7+regex8+regex9;
		String line = "192.168.0.134 \"b66b0fb1-6b6a-4293-beb3-365bf5e13267\" \"4f7ee017-87d5-4e40-9d37-e0256d87e610\" [11/Mar/2016:14:23:20 +0800] \"GET /api/activity/count?filter=%7B%22wherePart%22:%22+bd_activity.corp_id+%3D+%27b66b0fb1-6b6a-4293-beb3-365bf5e13267%27+and+bd_activity.begin_date%3E%3D%272015-09-11%27+and+bd_activity.end_date+%3C%3D%272016-09-11%27%22%7D HTTP/1.1\" 200 27 \"http://192.168.0.53/\" \"Mozilla/5.0 (Linux; Android 5.0; SM-G900P Build/LRX21T) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.23 Mobile Safari/537.36\"";
		final Pattern apacheLogRegex = Pattern.compile(regex);
		Matcher m = apacheLogRegex.matcher(line);
		if (m.find()) {
			System.out.println(m.group(1));
			System.out.println(m.group(2));
			System.out.println(m.group(3));
			System.out.println(m.group(4));
			System.out.println(m.group(5));
			System.out.println(m.group(6));
			System.out.println(m.group(7));
			System.out.println(m.group(8));
			System.out.println(m.group(9));
		}
	}
}
