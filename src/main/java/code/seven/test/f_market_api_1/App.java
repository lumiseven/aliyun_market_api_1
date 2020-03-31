package code.seven.test.f_market_api_1;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;

import com.aliyun.fc.runtime.Context;
import com.aliyun.fc.runtime.FunctionInitializer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import com.aliyun.fc.runtime.HttpRequestHandler;

/**
 *
 */
public class App implements HttpRequestHandler, FunctionInitializer {

	private static final String HOST = "host";
	private static final String PATH = "path";
	private static final String METHOD = "method";
	private static final String APPCODE = "appcode";
	private static final String AUTHORIZATION = "Authorization";
	private static final String ContentType = "Content-Type";
	
	public void initialize(Context context) throws IOException {
        //TODO
    }

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response, Context context)
            throws IOException, ServletException {
        dealReqeust(request, response);
    }

    private void dealReqeust(HttpServletRequest request, HttpServletResponse response) throws IOException{
    	String host = request.getHeader(HOST);
    	String path = request.getHeader(PATH);
    	String method = request.getHeader(METHOD);
    	String appcode = request.getHeader(APPCODE);
    	Map<String, String[]> m = request.getParameterMap();
    	Map<String, String> m2 = new HashMap<>();
        m.forEach((k,v) -> {
        	System.out.println(v[0]);
        	m2.put(k, v[0]);
        });
        
    	String data_response = StringUtils.EMPTY;
	    Map<String, String> headers = new HashMap<String, String>();
	    headers.put(AUTHORIZATION, "APPCODE " + appcode);

	    try {
            HttpResponse res = HttpUtils.doGet(host, path, method, headers, m2);
	    	data_response =  EntityUtils.toString(res.getEntity());
//	    	response.getStatusLine().getStatusCode();
	    	response.setStatus(res.getStatusLine().getStatusCode());
	    	response.setHeader(ContentType, res.getLastHeader(ContentType).getValue());
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }

	    OutputStream out = response.getOutputStream();
        out.write((data_response).getBytes());
        out.flush();
        out.close();
    }
}
