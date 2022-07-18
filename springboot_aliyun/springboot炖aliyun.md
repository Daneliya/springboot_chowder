## springboot炖aliyun

### 1. 先睹为快


### 2. 短信发送 demo

#### 2.1 新建项目


#### 2.2 创建maven目录结构，以及pom.xml文件


#### 2.3 pom.xml文件中加入依赖

```xml
<!--阿里短信服务-->
<dependency>
    <groupId>com.aliyun</groupId>
    <artifactId>aliyun-java-sdk-core</artifactId>
    <version>3.7.1</version>
</dependency>
<dependency>
    <groupId>com.aliyun</groupId>
    <artifactId>aliyun-java-sdk-dysmsapi</artifactId>
    <version>1.1.0</version>
</dependency>
```

### 3. 回执 demo
~~~java
package com.alicom.mns.sample;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alicom.mns.tools.DefaultAlicomMessagePuller;
import com.alicom.mns.tools.MessageListener;
import com.aliyun.mns.model.Message;
import com.google.gson.Gson;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 只能用于接收云通信的消息，不能用于接收其他业务的消息
 * 短信上行消息接收demo
 */
public class ReceiveDemo {

	private static Logger logger = Logger.getLogger(ReceiveDemo.class.getName());
	
	static class MyMessageListener implements MessageListener{
		private Gson gson=new Gson();

		@Override
		public boolean dealMessage(Message message) {

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//消息的几个关键值
			System.out.println("message receiver time from mns:" + format.format(new Date()));
			System.out.println("message handle: " + message.getReceiptHandle());
            System.out.println("message body: " + message.getMessageBodyAsString());
            System.out.println("message id: " + message.getMessageId());
            System.out.println("message dequeue count:" + message.getDequeueCount());
            System.out.println("Thread:" + Thread.currentThread().getName());
            try{
            	Map<String,Object> contentMap=gson.fromJson(message.getMessageBodyAsString(), HashMap.class);

				//TODO 根据文档中具体的消息格式进行消息体的解析
                String arg = (String) contentMap.get("arg");

                //TODO 这里开始编写您的业务代码

            }catch(com.google.gson.JsonSyntaxException e){
            	logger.log(Level.SEVERE, "error_json_format:"+message.getMessageBodyAsString(),e);
				//理论上不会出现格式错误的情况，所以遇见格式错误的消息，只能先delete,否则重新推送也会一直报错
				return true;
            } catch (Throwable e) {
				//您自己的代码部分导致的异常，应该return false,这样消息不会被delete掉，而会根据策略进行重推
				return false;
			}

			//消息处理成功，返回true, SDK将调用MNS的delete方法将消息从队列中删除掉
			return true;
		}

	}

	public static void main(String[] args) throws Exception, ParseException {

		DefaultAlicomMessagePuller puller=new DefaultAlicomMessagePuller();

		//设置异步线程池大小及任务队列的大小，还有无数据线程休眠时间
		puller.setConsumeMinThreadSize(6);
		puller.setConsumeMaxThreadSize(16);
		puller.setThreadQueueSize(200);
		puller.setPullMsgThreadSize(1);
		//和服务端联调问题时开启,平时无需开启，消耗性能
		puller.openDebugLog(false);

		//TODO 此处需要替换成开发者自己的AK信息
		String accessKeyId="accessKeyId";
		String accessKeySecret="accessKeySecret";

		/*
		* TODO 将messageType和queueName替换成您需要的消息类型名称和对应的队列名称
		* 云通信产品下所有的回执消息类型:
		* 短信服务
		* 1:短信回执：SmsReport，
		* 2:短息上行：SmsUp
		* 3:国际短信回执：GlobeSmsReport
		*
		* 号码隐私保护服务
		* 1.呼叫发起时话单报告：SecretStartReport
		* 2.呼叫响铃时报告：SecretRingReport
		* 3.呼叫接听时报告：SecretPickUpReport
		* 4.呼叫结束后话单报告：SecretReport
		* 5.录音状态报告：SecretRecording
		* 6.录音ASR状态报告：SecretAsrReport
		* 7.短信内容报告：SecretSmsIntercept
		* 8.计费通话报告：SecretBillingCallReport
		* 9.计费短信报告：SecretBillingSmsReport
		* 10.异常号码状态推送：SecretExceptionPhoneReport
		* 11.放音录音状态报告：SecretRingRecording
		* 12.电商物流详情报告：SmartLogisticsReport
		* 13.号码管理信息：NumberManagementReport
		*
		* 语音服务
		* 1.呼叫记录消息：VoiceReport
		* 2.呼叫中间状态消息：VoiceCallReport
		* 3.录音记录消息：VoiceRecordReport
		* 4.实时ASR消息：VoiceRTASRReport
		* 5.融合通信呼叫记录消息：ArtcCdrReport
		* 6.融合通信呼叫中间状态：ArtcTempStatusReport
		*/
		String messageType="messageType";//此处应该替换成相应产品的消息类型
		String queueName="queueName";//在云通信页面开通相应业务消息后，就能在页面上获得对应的queueName,格式类似Alicom-Queue-xxxxxx-SmsReport

		puller.startReceiveMsg(accessKeyId,accessKeySecret, messageType, queueName, new MyMessageListener());
    }
}

~~~



### 参考资料

[1]. 阿里云短信发送 https://help.aliyun.com/document_detail/112148.html
[2]. 阿里云消息回执 https://help.aliyun.com/document_detail/101508.html
