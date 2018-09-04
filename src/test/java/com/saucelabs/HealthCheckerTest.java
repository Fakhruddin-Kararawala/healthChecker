package com.saucelabs;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import junit.framework.TestCase;


public class HealthCheckerTest extends TestCase {
    
	@Test(expected = IOException.class)
    public void testUrlServiceisUP() throws IOException {
		HealthCheckerThread healthCheckerThread = new HealthCheckerThread();
		 try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
	            HttpGet httpget = new HttpGet(healthCheckerThread.getServiceUrl());

	            ResponseHandler<String> responseHandler = response -> {
	                int status = response.getStatusLine().getStatusCode();
                        System.out.println("status code "+ status);
	                HttpEntity entity = response.getEntity();
	                String result = entity != null ? EntityUtils.toString(entity) : null;
                        System.out.println("result "+ result);
	                if (status >= 200 && status < 300) {
	                    assertTrue("Magnificent is UP", status >= 200 && status < 300);
	                } else {
	                    assertFalse("Magnificent is DOWN" , status > 200);
	                }

	                return result;
	            };
	            httpclient.execute(httpget, responseHandler);
	        } catch (IOException e) {
	        	throw new IOException("Magnificent is not responding");
	        }
    }
}
