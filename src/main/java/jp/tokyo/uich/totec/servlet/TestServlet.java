package jp.tokyo.uich.totec.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@SuppressWarnings("javadoc")
@WebServlet(name = "TestServlet", urlPatterns = "/test")
public class TestServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//		req.setCharacterEncoding("UTF-8");
		//		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/plain; charset=UTF-8");
		String value = getValue(req.getParameter("key"));
		//		String value = "テストだよー";
		resp.getWriter().print(value);
	}

	private String getValue(String key) {
		JedisPoolConfig config = new JedisPoolConfig();
		//		JedisPool pool = new JedisPool(config, "54.68.163.231", 6379);
		JedisPool pool = new JedisPool(config, "localhost", 6379);

		Jedis jedis = pool.getResource();
		try {
			return jedis.get(key);
		} finally {
			pool.returnResource(jedis);
			pool.destroy();
		}
	}
}
