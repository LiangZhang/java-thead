//package com.zlsoft.yh;
//
//import java.util.List;
//
//import javax.xml.namespace.QName;
//
//import org.apache.cxf.binding.soap.SoapHeader;
//import org.apache.cxf.binding.soap.SoapMessage;
//import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
//import org.apache.cxf.headers.Header;
//import org.apache.cxf.helpers.DOMUtils;
//import org.apache.cxf.interceptor.Fault;
//import org.apache.cxf.phase.Phase;
//import org.w3c.dom.Document;
//import org.w3c.dom.Element;
//
///**
// *
// * @Title:在发送消息前，封装Soap Header 信息
// *
// * @Description:
// *
// * @Copyright:
// *
// * @author zz
// * @version 1.00.000
// *
// */
//
//public class AddSoapHeader extends AbstractSoapInterceptor {
//	/** 名称空间 */
//	private String nameURI;
//	/** 接入系统标识 **/
//	private String systemKey;
//	/** 需要签名的数据 **/
//	private String source;
//	/** 签名 **/
//	private String signature;
//	/** 交易验证码 */
//	private String jyyzm;
//	/** 交易流水号 */
//	private String jylsh;
//	/** 交易编号 */
//	private String jybh;
//	/** 访问流水号 */
//	private String fwlsh;
//	/** 内部访问验证码 */
//	private String nbffyzm;
//	/** 服务调用序号 */
//	private String fwdysxh;
//
//	public AddSoapHeader() {
//		super(Phase.WRITE);
//	}
//    /**
//     *
//     * @param nameURI 名称空间
//     * @param systemKey 接入系统标识
//     * @param source 需要签名的数据
//     * @param signature 签名
//     * @param jybh 交易编号
//     */
//	public AddSoapHeader(String nameURI, String systemKey, String source,
//			String signature,String jybh) {
//		super(Phase.WRITE);
//		this.nameURI = nameURI;
//		this.systemKey = systemKey;
//		this.source = source;
//		this.signature = signature;
//		this.jybh = jybh;
//
//	}
//
//	/**
//	 *
//     * @param nameURI 名称空间
//     * @param systemKey 接入系统标识
//     * @param source 需要签名的数据
//     * @param signature 签名
//	 * @param jyyzm 交易验证码
//	 * @param jylsh 交易流水号
//	 * @param jybh  交易编号
//	 */
//	public AddSoapHeader(String nameURI, String systemKey, String source,
//			String signature, String jylsh, String jybh) {
//		super(Phase.WRITE);
//		this.nameURI = nameURI;
//		this.systemKey = systemKey;
//		this.source = source;
//		this.signature = signature;
//		this.jylsh = jylsh;
//		this.jybh = jybh;
//	}
//
//	public void handleMessage(SoapMessage message) throws Fault {
//
//		QName qname = new QName("RequestSOAPHeader");
//		Document doc = DOMUtils.createDocument();
//
//		// 接入系统标识
//		Element el_systemKey = doc.createElement("systemKey");
//		el_systemKey.setTextContent(systemKey);
//
//		// 需要签名的数据
//		Element el_source = doc.createElement("source");
//		el_source.setTextContent(source);
//
//		// 签名
//		Element el_signature = doc.createElement("signature");
//		el_signature.setTextContent(signature);
//
//		// 交易流水号
//		Element el_jylsh = doc.createElement("jylsh");
//		el_jylsh.setTextContent(jylsh);
//		// 交易验证码
//		Element el_jyyzm = doc.createElement("jyyzm");
//		el_jyyzm.setTextContent(jyyzm);
//		// 交易编号
//		Element el_jybh = doc.createElement("jybh");
//		el_jybh.setTextContent(jybh);
//
//		// 访问流水号
//		Element el_fwlsh = doc.createElement("fwlsh");
//		el_fwlsh.setTextContent(fwlsh);
//
//		// 内部访问验证码
//		Element el_nbffyzm = doc.createElement("nbffyzm");
//		el_nbffyzm.setTextContent(nbffyzm);
//
//		// 服务调用序号
//		Element el_fwdysxh = doc.createElement("fwdysxh");
//		el_fwdysxh.setTextContent(fwdysxh);
//
//		Element root = doc.createElementNS(nameURI, "in:system");
//		root.appendChild(el_systemKey);
//		root.appendChild(el_source);
//		root.appendChild(el_signature);
//		root.appendChild(el_jylsh);
//		root.appendChild(el_jyyzm);
//		root.appendChild(el_jybh);
//		root.appendChild(el_fwlsh);
//		root.appendChild(el_nbffyzm);
//		root.appendChild(el_fwdysxh);
//
//		SoapHeader head = new SoapHeader(qname, root);
//		List<Header> headers = message.getHeaders();
//		headers.add(head);
//	}
//
//}
