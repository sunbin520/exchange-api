/*
 * Copyright 2014 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package websocketx.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

import java.math.BigDecimal;
import java.net.URI;
import java.nio.channels.UnsupportedAddressTypeException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import com.alibaba.fastjson.JSONObject;




public class WebSocketClient {
	private String url;
	private EventLoopGroup group;
	private Channel channel;
	
	public final String accessKey = "";
	public final String secretKey = "";
	public static String serverUrl = "ws://ws.ikedou.net/depth.ws?uuid=740";

	public static String payPass = "112233";
	
	public WebSocketClient(String url){
		this.url=url;
	}

	public void connect() throws Exception {
		if (url == null || "".equals(url.trim())) {
			throw new NullPointerException("the url can not be empty");
		}
		URI uri = new URI(url);
		
		String scheme = uri.getScheme() == null ? "http" : uri.getScheme();
		final String host = uri.getHost() == null ? "127.0.0.1" : uri.getHost();
		final int port;
		if (uri.getPort() == -1) {
			if ("http".equalsIgnoreCase(scheme)) {
				port = 80;
			} else if ("wss".equalsIgnoreCase(scheme)) {
				port = 443;
			} else {
				port = 80;
			}
		} else {
			port = uri.getPort();
		}

		if (!"ws".equalsIgnoreCase(scheme) && !"wss".equalsIgnoreCase(scheme)) {
			System.err.println("Only WS(S) is supported.");
			throw new UnsupportedAddressTypeException();
		}
		final boolean ssl = "wss".equalsIgnoreCase(scheme);
		final SslContext sslCtx;
		if (ssl) {
			sslCtx = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();
		} else {
			sslCtx = null;
		}
		group = new NioEventLoopGroup();
		try {
			final WebSocketClientHandler handler = new WebSocketClientHandler(
					WebSocketClientHandshakerFactory.newHandshaker(uri,
							WebSocketVersion.V13, null, false,
							new DefaultHttpHeaders())) {
				@Override
				public void onReceive(String msg) {
					System.out.println("channel获取消息：" + msg);
				}
			};
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class)
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) {
							ChannelPipeline p = ch.pipeline();
							if (sslCtx != null) {
								p.addLast(sslCtx.newHandler(ch.alloc(), host,
										port));
							}
							p.addLast(new HttpClientCodec(),
									new HttpObjectAggregator(8192), handler);
						}
					});

			channel = b.connect(uri.getHost(), port).sync().channel();
			// ChannelFuture f = channel.closeFuture().await();
			handler.handshakeFuture().sync();
		} catch (Exception e) {
			this.cancel();
			throw e;
		}
	}
	
	/**
	 * 下单  委托下单
	 * @param accessKey
	 * @param secretKey
	 */
	public void order(){
		JSONObject data=new JSONObject();
		HashMap<String, String> signMap = new LinkedHashMap<>();
		data.put("event", "addChannel");	
		data.put("channel","order");		//频道
		data.put("accesskey", accessKey);	//公钥
		//data.put("type","211");	
		data.put("currency","qtum_btc");		//交易类型
		data.put("tradeType",1);		// 0  买单   | 1  卖单
		data.put("price",100000);				//价格
		data.put("amount",0.1);			//数量
		data.put("reqTime",String.valueOf(System.currentTimeMillis()));
		
		for(Map.Entry<String,Object> entry:data.entrySet()){
            String key = entry.getKey();
            signMap.put(key, entry.getValue().toString());
        }
		String secret = EncryDigestUtil.digest(secretKey);
		String sign = EncryDigestUtil.getPlainSignString(signMap);
		String mySign = EncryDigestUtil.sign(sign, secret);

		data.put("sign", mySign);
		System.out.println(data);
        this.channel.writeAndFlush(new TextWebSocketFrame(data.toString()));
	}
	
	/**
	 * 下单  委托下单
	 * @param accessKey
	 * @param secretKey
	 */
	public void cancelOrder(){
		JSONObject data=new JSONObject();
		HashMap<String, String> signMap = new LinkedHashMap<>();
		data.put("event", "addChannel");	
		data.put("channel","cancel");		//频道
		data.put("accesskey", accessKey);	//公钥
		data.put("id","105853");			//下单时候返回的id
		data.put("currency","qtum_btc");		//交易类型
		data.put("reqTime",String.valueOf(System.currentTimeMillis()));
		for(Map.Entry<String,Object> entry:data.entrySet()){
            String key = entry.getKey();
            signMap.put(key, entry.getValue().toString());
        }
		String secret = EncryDigestUtil.digest(secretKey);
		String sign = EncryDigestUtil.getPlainSignString(signMap);
		String mySign = EncryDigestUtil.sign(sign, secret);

		data.put("sign", mySign);
		System.out.println(data);
        this.channel.writeAndFlush(new TextWebSocketFrame(data.toString()));
	}
	
	/**
	 * 获取委托买单或卖单
	 * @param accessKey
	 * @param secretKey
	 */
	public void getOrder(){
		JSONObject data=new JSONObject();
		HashMap<String, String> signMap = new LinkedHashMap<>();
		data.put("event", "addChannel");	
		data.put("channel","getOrder");		//频道
		data.put("accesskey", accessKey);	//公钥
		//data.put("type","211");	
		data.put("currency","qtum_qc");		//交易类型
		//data.put("tradeType",0);		// 0  买单   | 1  卖单
		data.put("id","87933");				//下单时候返回的id
		data.put("reqTime",String.valueOf(System.currentTimeMillis()));
		for(Map.Entry<String,Object> entry:data.entrySet()){
            String key = entry.getKey();
            signMap.put(key, entry.getValue().toString());
        }
		String secret = EncryDigestUtil.digest(secretKey);
		String sign = EncryDigestUtil.getPlainSignString(signMap);
		String mySign = EncryDigestUtil.sign(sign, secret);

		data.put("sign", mySign);
		System.out.println(data);
        this.channel.writeAndFlush(new TextWebSocketFrame(data.toString()));
	}
	
	/**
	 * 取消tradeType字段过滤，可同时获取买单和卖单，每次请求返回pageSize<100条记录
	 * @param accessKey
	 * @param secretKey
	 */
	public void getOrders(){
		JSONObject data=new JSONObject();
		HashMap<String, String> signMap = new LinkedHashMap<>();
		data.put("event", "addChannel");	
		data.put("channel","getOrders");		//频道
		data.put("accesskey", accessKey);	//公钥	
		//data.put("pageSize","10");		//显示多少条  默认100
		data.put("currency","eth_btc");		//交易类型
		data.put("tradeType",0);		// 0  买单   | 1  卖单
		//data.put("id","87933");				//下单时候返回的id
		data.put("reqTime",String.valueOf(System.currentTimeMillis()));
		for(Map.Entry<String,Object> entry:data.entrySet()){
            String key = entry.getKey();
            signMap.put(key, entry.getValue().toString());
        }
		String secret = EncryDigestUtil.digest(secretKey);
		String sign = EncryDigestUtil.getPlainSignString(signMap);
		String mySign = EncryDigestUtil.sign(sign, secret);

		data.put("sign", mySign);
		System.out.println(data);
        this.channel.writeAndFlush(new TextWebSocketFrame(data.toString()));
	}

	/**
	 * 获取多个委托买单或卖单，每次请求返回10条记录
	 * @param accessKey
	 * @param secretKey
	 */
	public void getRechargeRecord(){
		JSONObject data=new JSONObject();
		HashMap<String, String> signMap = new LinkedHashMap<>();
		data.put("event", "addChannel");	
		data.put("channel","getRechargeRecord");		//频道
		data.put("accesskey", accessKey);	//公钥	
		//data.put("pageSize","10");		//显示多少条  默认10
		data.put("currency","qtum_qc");		//交易类型
		//data.put("tradeType",0);		// 0  买单   | 1  卖单
		//data.put("id","87933");				//下单时候返回的id
		data.put("reqTime",String.valueOf(System.currentTimeMillis()));
		for(Map.Entry<String,Object> entry:data.entrySet()){
            String key = entry.getKey();
            signMap.put(key, entry.getValue().toString());
        }
		String secret = EncryDigestUtil.digest(secretKey);
		String sign = EncryDigestUtil.getPlainSignString(signMap);
		String mySign = EncryDigestUtil.sign(sign, secret);

		data.put("sign", mySign);
		System.out.println(data);
        this.channel.writeAndFlush(new TextWebSocketFrame(data.toString()));
	}
	

	

	
	/**
	 * 获取用户信息
	 * @param accessKey
	 * @param secretKey
	 */
	public void getAccountInfo(){
		JSONObject data=new JSONObject();
		data.put("event", "addChannel");	
		data.put("channel","getAccountInfo");		//频道
		data.put("accesskey", accessKey);	//公钥	
		data.put("reqTime",String.valueOf(System.currentTimeMillis()));
		String secret = EncryDigestUtil.digest(secretKey);
		String sign = EncryDigestUtil.getPlainSignString(JsonToMap(data));
		String mySign = EncryDigestUtil.sign(sign, secret);

		data.put("sign", mySign);
		System.out.println(data);
        this.channel.writeAndFlush(new TextWebSocketFrame(data.toString()));
	}

	
	/**
	 * 注销客户端
	 */
	public void cancel(){
		if(group!=null)group.shutdownGracefully();
	}
	/**
	 * 判断客户端是否保持激活状态
	 * @return
	 */
	public boolean isAlive(){
		return this.channel!=null&&this.channel.isActive()?true:false;
	}
	
	/**
	 *  json 转换成  Map
	 * @param data
	 * @return
	 */
	public static HashMap<String, String> JsonToMap(JSONObject data){
		HashMap<String, String> signMap = new LinkedHashMap<>();
		for(Map.Entry<String,Object> entry:data.entrySet()){
            String key = entry.getKey();
            signMap.put(key, entry.getValue().toString());
        }
		return signMap;
	}
    
}
