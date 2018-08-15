package websocketx.client;

import org.apache.log4j.Logger;

public class RunExample {
	
	private static Logger log = Logger.getLogger(RunExample.class);
	
	public static WebSocketClient client;
	
	public static void main(String[] args) {
		try {
			if(client==null){
				log.info("链接到"+WebSocketClient.serverUrl);
				client = new WebSocketClient( WebSocketClient.serverUrl );
			}  
			client.connect();
			System.out.print("================================"+client.isAlive());
			//client.order();	            //委托下单
			//client.cancelOrder();		    //取消委托
			//client.getOrder();		    //获取委托买单或卖单
			//client.getRechargeRecord();   //获取多个委托买单或卖单，每次请求返回10条记录
			client.getOrders();			//取消tradeType字段过滤，可同时获取买单和卖单，每次请求返回pageSize<100条记录
			//client.getAccountInfo();      //获取用户的信息
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
