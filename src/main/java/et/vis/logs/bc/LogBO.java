package et.vis.logs.bc;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import et.vis.logs.vo.LogVO;

public class LogBO {
	static DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MMM/yyyy:HH:mm:ss ZZ").withLocale(Locale.ROOT);

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

	public static LogVO getLogObject(String line) {
		Matcher m = nginxLogRegex.matcher(line);
		LogVO lvo = new LogVO();
		if (m.find()) {
			lvo.setRemote_addr(m.group(1));
			lvo.setCorp_id(m.group(2));
			lvo.setUser_id(m.group(3));
			lvo.setDate(formatter.parseDateTime(m.group(4)));
			lvo.setRequest(m.group(5));
			lvo.setStatus(Integer.parseInt(m.group(6)));
			lvo.setBody_bytes_sent(Integer.parseInt(m.group(7)));
			lvo.setHttp_refer(m.group(8));
			lvo.setUser_agent(m.group(9));
		}
		return lvo;
	}

	public static void main(String[] args) {
		String line = "192.168.0.134 \"b66b0fb1-6b6a-4293-beb3-365bf5e13267\" \"4f7ee017-87d5-4e40-9d37-e0256d87e610\" [11/Mar/2016:14:23:20 +0800] \"GET /api/activity/count?filter=%7B%22wherePart%22:%22+bd_activity.corp_id+%3D+%27b66b0fb1-6b6a-4293-beb3-365bf5e13267%27+and+bd_activity.begin_date%3E%3D%272015-09-11%27+and+bd_activity.end_date+%3C%3D%272016-09-11%27%22%7D HTTP/1.1\" 200 27 \"http://192.168.0.53/\" \"Mozilla/5.0 (Linux; Android 5.0; SM-G900P Build/LRX21T) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.23 Mobile Safari/537.36\"";
		LogVO lvo = getLogObject(line);
		System.out.println(lvo.getUser_agent());

	}

}
