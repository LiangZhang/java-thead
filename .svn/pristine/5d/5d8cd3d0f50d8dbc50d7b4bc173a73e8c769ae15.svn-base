//package com.zlsoft.yh;
//
//import org.apache.cxf.endpoint.Client;
//import org.apache.cxf.interceptor.LoggingInInterceptor;
//import org.apache.cxf.interceptor.LoggingOutInterceptor;
//import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
//import org.apache.cxf.transport.http.HTTPConduit;
//import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
//
//
//public class LcUtils {
//
//	public static final String WEB_URL = "http://119.6.84.89:8225/xtpt/services/yinHaiBusiness?wsdl";
//	public static final String NAME_URL = "http://yinhai.com";
//	public static final String SYSTEM_KEY = "scjy";
//
//    public static void main(String args[]) throws Exception {
//		String inputxml = "";
//		String result = doWebService("jy_pxsj_001",inputxml);
//    	System.out.println(result);
//    }
//
//    public static String doWebService(String jybh,String input) throws Exception {
//        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
//        // 创建client，wsdlUrl地址格式：业务协同管理平台访问地址+/services/yinHaiBusiness?wsdl
//        // client创建开销比较大，一般需要使用线程池进行缓存
//        Long t1 = System.currentTimeMillis();
//
//        Client client = dcf.createClient(WEB_URL);
//
//        client.getInInterceptors().add(new LoggingInInterceptor());
//		// 输出报文拦截器
//        client.getOutInterceptors().add(new LoggingOutInterceptor());
//        HTTPConduit http = (HTTPConduit) client.getConduit();
//        // 设置连接超时参数
//        HTTPClientPolicy hcp = new HTTPClientPolicy();
//        hcp.setConnectionTimeout(20000);
//        hcp.setReceiveTimeout(200000);
//        http.setClient(hcp);
//        //System.out.println(xmlInput);
//        // 参数签名 1、入参串 2、 证书（不传默认读取bcp.keystore）
////        String sign = RSAUtils.sign(input, "bcp_ww.keystore");
//        //System.out.println(sign);
//        // 设置报文头信息
//        /**
//         * 1、名称空间（固定） 2、接入系统标识（协同平台分配） 3、入参xml 4、参数签名 5、交易服务标识
//         */
//        client.getOutInterceptors().add(new AddSoapHeader(NAME_URL,SYSTEM_KEY, input, sign, jybh));
//
//
//        // 发起调用
//        Object[] obj = client.invoke("callBusiness", input);
//        // 获取返回结果(XML格式)
//        Object result = obj[0];
//
//        Long t2 = System.currentTimeMillis();
//        System.out.println("执行时间："+(t2-t1));
//        return result+"";
//    }
//
//
//}
