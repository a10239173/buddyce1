package com.buddy.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.dysmsapi.transform.v20170525.SendSmsResponseUnmarshaller;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.http.HttpResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.buddy.entity.SMSModel;
import com.google.gson.Gson;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/*
 * 发送手机短信验证码
 */
public class SendMessageUtils {

	    /**
	     * 添加日志支持
	     */
	    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(SendMessageUtils.class);
	 
	    //产品名称:云通信短信API产品,开发者无需替换
	    static final String product = "Dysmsapi";
	    //产品域名,开发者无需替换
	    static final String domain = "dysmsapi.aliyuncs.com";
	 
	    // TODO 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找) 记录下来的
	    static final String accessKeyId = "********";
	    static final String accessKeySecret = "**********";
	 
	    /**
	     * 发送短信
	     * @param models 短信模板变量对象
	     * @return
	     * @throws ClientException 
	     * @throws ServerException 
	     */
	    public static  SendSmsResponse  sendMssage(SMSModel smsModel) throws Exception{
	      
	            //解析传进的对象成json
	            Gson gson = new Gson();
	 
	            //可自助调整超时时间
	            System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
	            System.setProperty("sun.net.client.defaultReadTimeout", "10000");
	 
	            //初始化acsClient,暂不支持region化
	            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
	            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
	            IAcsClient acsClient = new DefaultAcsClient(profile);
	 
	            //组装请求对象-具体描述见控制台-文档部分内容
	            SendSmsRequest request = new SendSmsRequest();
	            //必填:待发送手机号
	            request.setPhoneNumbers(smsModel.getTel());
	            //必填:短信签名-可在短信控制台中找到
	            request.setSignName("***");
	            //必填:短信模板-可在短信控制台中找到
	            request.setTemplateCode("***");
	            //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
	            //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
	            request.setTemplateParam("{\"name\":\"Tom\", \"code\":\"123\"}");
//	            request.setTemplateParam("{\"code\":\"889977\"}");
	            //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
	            request.setOutId("yourOutId");
	 
	            //hint 此处可能会抛出异常，注意catch
	            SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
	            return sendSmsResponse;
	    }
	            	
}

