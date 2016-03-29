package et.vis.logs.vo;

import java.io.Serializable;

import org.joda.time.DateTime;
/**
 * 日志相关VO
 * @author AMS
 *
 */
public class LogVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1880987669028846809L;
	private String remote_addr;
	private String corp_id;
	private String user_id;
	private DateTime date;
	private String request;
	private int status;
	private int body_bytes_sent;
	private String http_refer;
	private String user_agent;

	public String getHttp_refer() {
		return http_refer;
	}

	public void setHttp_refer(String http_refer) {
		this.http_refer = http_refer;
	}

	public String getRemote_addr() {
		return remote_addr;
	}

	public void setRemote_addr(String remote_addr) {
		this.remote_addr = remote_addr;
	}

	public String getCorp_id() {
		return corp_id;
	}

	public void setCorp_id(String corp_id) {
		this.corp_id = corp_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getBody_bytes_sent() {
		return body_bytes_sent;
	}

	public void setBody_bytes_sent(int body_bytes_sent) {
		this.body_bytes_sent = body_bytes_sent;
	}

	public String getUser_agent() {
		return user_agent;
	}

	public void setUser_agent(String user_agent) {
		this.user_agent = user_agent;
	}

	public DateTime getDate() {
		return date;
	}

	public void setDate(DateTime date) {
		this.date = date;
	}

}
