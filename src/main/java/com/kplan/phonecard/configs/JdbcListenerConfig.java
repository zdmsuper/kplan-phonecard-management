package com.kplan.phonecard.configs;

import java.sql.SQLException;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.p6spy.engine.common.ConnectionInformation;
import com.p6spy.engine.event.JdbcEventListener;

import net.ttddyy.dsproxy.ExecutionInfo;
import net.ttddyy.dsproxy.QueryInfo;
import net.ttddyy.dsproxy.listener.QueryExecutionListener;
import net.ttddyy.dsproxy.transform.ParameterReplacer;
import net.ttddyy.dsproxy.transform.ParameterTransformer;
import net.ttddyy.dsproxy.transform.QueryTransformer;
import net.ttddyy.dsproxy.transform.TransformInfo;

@Configuration
public class JdbcListenerConfig {
	@Bean
	public JdbcEventListener myListener() {
	    return new JdbcEventListener() {
	        @Override
	        public void onAfterGetConnection(ConnectionInformation connectionInformation, SQLException e) {
	          //  System.out.println("got connection");
	        }

	        @Override
	        public void onAfterConnectionClose(ConnectionInformation connectionInformation, SQLException e) {
	          //  System.out.println("connection closed");
	        }
	    };
	}
	/*@Bean
	public QueryExecutionListener queryExecutionListener() {
	    return new QueryExecutionListener() {
	        @Override
	        public void beforeQuery(ExecutionInfo execInfo, List<QueryInfo> queryInfoList) {
	            System.out.println("beforeQuery");
	        }

	        @Override
	        public void afterQuery(ExecutionInfo execInfo, List<QueryInfo> queryInfoList) {
	            System.out.println("afterQuery");
	        }
	    };
	}

	@Bean
	public ParameterTransformer parameterTransformer() {
	    return new ParameterTransformer() {

			@Override
			public void transformParameters(ParameterReplacer replacer, TransformInfo transformInfo) {
			
				
			}};
	}

	@Bean
	public QueryTransformer queryTransformer() {
	    return new QueryTransformer() {

			@Override
			public String transformQuery(TransformInfo transformInfo) {
				// TODO Auto-generated method stub
				return transformInfo.toString();
			}};
	}*/
}
