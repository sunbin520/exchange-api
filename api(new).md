# 1.Rest API
## 访问地址：http://api.ikedou.net

## 公共API
[GET /data/v2/symbols](http://note.youdao.com/)查询平台可用交易对
###### 代码示例
```
//# Request
    GET http://api.ikedou.net/data/v2/symbols
    //# Response
    {
        "data": {
            {
            "symbol":"VNS_QC",
            "priceplace":"8"
            },
            {
            "symbol":"VNS_QC",
            "priceplace":"8"
            }
        }
    }
```
###### 返回说明

```
  symbol:平台可用交易对
  priceplace:保留小数位
```

[GET /data/v2/currencys](http://note.youdao.com/)查询平台可用交易对
###### 代码示例
```
//# Request
    GET http://api.ikedou.net/data/v2/currencys
    //# Response
    {
        "data": {
           ["cnyt","usdt","hsr","qc","wv","etc","lzc","vns","btc","aaa","eth","qtum","san","sc","asch","smb"] 
        }
    }
```

## 1-1 行情API
[1-1.1 GET /data/v2/ticker](http://note.youdao.com/) 行情
###### 请求说明
| 参数名 | 描述 |   
| ------------ | :-----: | 
|currency|平台可用交易对|

currency：

###### 代码示例
```
//# Request
    GET http://api.ikedou.net/data/v2/ticker?currency=btm_cnyt
    //# Response
    {
        "ticker": {
            "high": "0.033153",
            "low": "0.030352",
            "buy": "0.030728",
            "sell": "0.030751",
            "last": "0.030729",
            "vol": "838.98"
        }
    }
```
###### 返回说明

```
    high : 最高价
    low : 最低价
    buy : 买一价
    sell : 卖一价
    last : 最新成交价
    vol : 成交量(最近的24小时)                                    
```

[1-1.2 GET /data/v2/depth](http://note.youdao.com/) 深度
###### 请求说明
| 参数名 | 描述 |   
| ------------ | :-----: | 
|currency平台可用交易对|
|size|档位1-50，如果有合并深度，只能返回5档深度|
|merge|合并深度|

###### 代码示例
```
//# Request
    GET http://api.ikedou.net/data/v2/depth?currency=btm_cnyt&size=3&merge=0.1
    //# Response
    {
        "asks": [
            [
                83.28,
                11.8
            ]...
        ],
        "bids": [
            [
                81.91,
                3.65
            ]...
        ],
        "timestamp" : 时间戳
    }
                       
```
###### 返回说明
```
asks : 卖方深度
    bids : 买方深度
    timestamp : 此次深度的产生时间戳
```
[1-1.3 GET /data/v2/trades]() 历史成交
###### 请求说明
| 参数名 | 描述 |   
| ------------ | :-----: | 
|currency|平台可用交易对|
###### 代码示例
```
//# Request
    GET http://api.ikedou.net/data/v2/trades?currency=btm_cnyt
    //# Response
    [
        {
            "amount": 0.541,
            "date": 1472711925,
            "price": 81.87,
            "tid": 16497097,
            "trade_type": "ask",
            "type": "sell"
        }...
    ]
```
###### 返回说明
```
date : 交易时间(时间戳)
    price : 交易价格
    amount : 交易数量
    tid : 交易生成ID
    type : 交易类型，buy(买)/sell(卖)
    trade_type : 委托类型，ask(卖)/bid(买)
```

[1-1.4 GET /data/v2/kline]() k线
###### 请求说明
| 参数名 | 描述 |   
| ------------ | :-----: | 
|currency|平台可用交易对|
|size|返回数据的条数限制(默认为1000，如果返回数据多于1000条，那么只返回1000条)|
|type|描述如下（type）|
type:
```
1 : 1分钟
3 : 3分钟
5 : 5分钟
15 : 15分钟
30 : 30分钟
1440 : 1日
10080 : 1周
60 : 1小时
120 : 2小时
240 : 4小时
360 : 6小时
720 : 12小时
```
###### 代码示例
```
//# Request
    GET http://api.ikedou.net/data/v2/kline?currency=btc_cnyt
    //# Response
    {
    "data": [
        [
            1472107500000,
            3840.46,
            3843.56,
            3839.58,
            3843.3,
            492.456
        ]...
    ]
    }
```
###### 返回说明
```
data : K线内容
    data : 内容说明
    [
    1417536000000, 时间戳
    2370.16, 开
    2380, 高
    2352, 低
    2367.37, 收
    17259.83 交易量
    ]
```
## 1-2 交易API
**请求参数说明**(加密签名请根据这个顺序签名,sign和reqTime不用加入待签名字符串)

[1-2.1 GET /api/v2/order]() 委托下单
###### 请求说明
| 参数名 | 描述 |   
| ------------ | :-----: | 
|method|直接赋值order|
|accesskey|accesskey|
|price|价格，保留小数位根据币种的不同而不同|
|amount|交易数量|
|tradeType|交易类型0/1[buy/sell]|
|currency|平台可用交易对|
|sign|请求加密签名串|
|reqTime|当前时间毫秒数|
###### 代码示例
```
//# Request
    GET  http://api.ikedou.net/api/v2/order?method=order
        &accesskey=accesskey&price=1024&amount=1.5&tradeType=1&currency=btc_cnyt
        &sign=请求加密签名串&reqTime=当前时间毫秒数
    //# Response
    {
        "code": "0000",
        "message": "操作成功。",
        "id": "20131228361867"
    }
```
###### 返回说明
```
code : 返回代码
    message : 提示信息
    id : 委托挂单号
```
[1-2.2 GET /api/v2/cancel]() 取消委托
###### 请求说明
| 参数名 | 描述 |   
| ------------ | :-----: | 
|method|直接赋值order|
|accesskey|accesskey|
|id|委托挂单号|
|currency|平台可用交易对|
|sign|请求加密签名串|
|reqTime|当前时间毫秒数|

###### 代码示例
```
//# Request
    GET  http://api.ikedou.net/api/v2/cancel?method=cancel
        &accesskey=your_access_key&id=123456789&currency=btc_cnyt
        &sign=请求加密签名串&reqTime=当前时间毫秒数
    //# Response
    {
        "code": "0000",
        "message": "操作成功。"
    }
```
###### 返回说明
```
code : 返回代码
    message : 提示信息
```
[1-2.3 GET /api/v2/getOrder]() 获取委托买单或卖单
###### 请求说明
| 参数名 | 描述 |   
| ------------ | :-----: | 
|method|直接赋值getOrder|
|accesskey|accesskey|
|id|委托挂单号|
|currency|平台可用交易对|
|sign|请求加密签名串|
|reqTime|当前时间毫秒数|

###### 代码示例
```
//# Request
    GET  http://api.ikedou.net/api/v2/getOrder?method=getOrder
        &accesskey=your_access_key&id=123456789&currency=btc_cnyt
        &sign=请求加密签名串&reqTime=当前时间毫秒数
    //# Response
    {
        "currency": "btc",
        "id": "20150928158614292",
        "price": 1560,
        "status": 3,
        "total_amount": 0.1,
        "trade_amount": 0,
        "trade_price" : 6000,
        "trade_date": 1443410396717,
        "trade_money": 0,
        "type": 0,
    }
```
###### 返回说明
```
    currency : 交易类型
    id : 委托挂单号
    price : 单价
    status : 挂单状态(1：待成交,2：待成交未交易部份,3：交易完成,5：已取消)
    total_amount : 挂单总数量
    trade_amount : 已成交数量
    trade_date : 委托时间
    trade_money : 已成交总金额
    trade_price : 成交均价
    type : 挂单类型 0/1[buy/sell]
```
[1-2.4.GET /api/v2/getOrders]() API获取多个委托买或卖单，每次请求返回PageSize小于100条记录
###### 请求说明
| 参数名 | 描述 |   
| ------------ | :-----: | 
|method|直接赋值getOrders|
|accesskey|accesskey|
|tradeType|交易类型0/1[buy/sell]|
|currency|平台可用交易对|
|pageIndex|当前页数|
|pageSize|每页数量|
|sign|请求加密签名串|
|reqTime|当前时间毫秒数|

###### 代码示例
```
//# Request
    GET  http://api.ikedou.net/api/v2/getOrders?method=getOrders
        &accesskey=your_access_key&tradeType=1&currency=btc_cnyt&pageIndex=1
        &pageSize=20&sign=请求加密签名串&reqTime=当前时间毫秒数
    //# Response
    [
        {
            "currency": "btc",
            "id": "20150928158614292",
            "price": 1560,
            "status": 3,
            "total_amount": 0.1,
            "trade_amount": 0,
            "trade_price" : 6000,
            "trade_date": 1443410396717,
            "trade_money": 0,
            "type": 0
        }...
    ]
```
###### 返回说明
```
currency : 交易类型
    id : 委托挂单号
    price : 单价
    status : 挂单状态(1：待成交,2：待成交未交易部份,3：交易完成,5：已取消)
    total_amount : 挂单总数量
    trade_amount : 已成交数量
    trade_date : 委托时间
    trade_money : 已成交总金额
    trade_price : 成交均价
    type : 挂单类型 0/1[buy/sell]
```
[1-2.5 GET /api/v2/getAccountInfo]() 获取用户信息
###### 请求说明
| 参数名 | 描述 |   
| ------------ | :-----: | 
|method|getAccountInfo|
|accesskey|accesskey|
|sign|请求加密签名串|
|reqTime|当前时间毫秒数|

###### 代码示例
```
//# Request
    GET  http://api.ikedou.net/api/v2/getUserAddress?method=getUserAddress
        &accesskey=your_access_key&currency=btc
        &sign=请求加密签名串&reqTime=当前时间毫秒数
    //# Response
    {
        "key": "0x0af7f36b8f09410f3df62c81e5846da673d4d9a9"
    }
```
###### 返回说明
```
key : 地址
```
[1-2.6 GET /api/v2/getUserAddress]() 获取用户充值地址
###### 请求说明
| 参数名 | 描述 |   
| ------------ | :-----: | 
|method|直接赋值getAccountInfo|
|accesskey|accesskey|
|currency|平台可用交易对|
|sign|请求加密签名串|
|reqTime|当前时间毫秒数|

###### 代码示例
```
//# Request
    GET  http://api.ikedou.net/api/v2/getUserAddress?method=getUserAddress
        &accesskey=your_access_key&currency=btc
        &sign=请求加密签名串&reqTime=当前时间毫秒数
    //# Response
    {
        "key": "0x0af7f36b8f09410f3df62c81e5846da673d4d9a9"
    }
```
###### 返回说明
```
key : 地址
```
[1-2.7 GET /api/v2/getWithdrawRecord]() 获取数字资产提现记录
###### 请求说明
| 参数名 | 描述 |   
| ------------ | :-----: | 
|method|直接赋值getWithdrawRecord|
|accesskey|accesskey|
|currency|平台可用币种|
|pageIndex|当前页数|
|pageSize|每页数量|
|sign|请求加密签名串|
|reqTime|当前时间毫秒数|

###### 代码示例
```
//# Request
    GET  http://api.ikedou.net/api/v2/getWithdrawRecord?method=getWithdrawRecord
        &accesskey=your_access_key&currency=btc&pageIndex=1&pageSize=10
        &sign=请求加密签名串&reqTime=当前时间毫秒数
    //# Response
    {
        "list": [
                    {
                        "amount": 0.01,
                        "fees": 0.001,
                        "id": 2016042556231,
                        "manageTime": 1461579340000,
                        "status": 3,
                        "submitTime": 1461579288000,
                        "toAddress": "14fxEPirL9fyfw1i9EF439Pq6gQ5xijUmp"
                    }...
                ],
        "pageIndex": 1,
        "pageSize": 10,
        "totalCount": 4,
        "totalPage": 1

    }
             
```
###### 返回说明
```
code : 返回代码
    message : 提示信息
    amount : 提现金额
    fees : 提现手续费
    id : 提现记录id
    manageTime : 提现处理的时间的时间戳
    submitTime : 提现发起的时间的时间戳
    toAddress : 提现的接收地址
```
[1-2.8 GET /api/v2/getChargeRecord]() 获取数字资产充值记录
###### 请求说明
| 参数名 | 描述 |   
| ------------ | :-----: | 
|method|直接赋值getChargeRecord|
|accesskey|accesskey|
|currency|平台可用币种|
|pageIndex|当前页数|
|pageSize|每页数量|
|sign|请求加密签名串|
|reqTime|当前时间毫秒数|


###### 代码示例
```
//# Request
    GET  http://api.ikedou.net/api/v2/getChargeRecord?method=getChargeRecord
        &accesskey=your_access_key&currency=btc&pageIndex=1&pageSize=10
        &sign=请求加密签名串&reqTime=当前时间毫秒数
    //# Response
    {

        "list": [
            {
                "address": "1FKN1DZqCm8HaTujDioRL2Aezdh7Qj7xxx",
                "amount": "1.00000000",
                "confirmTimes": 1,
                "currency": "BTC",
                "description": "确认成功",
                "hash": "7ce842de187c379abafadd64a5fe66c5c61c8a21fb04edff9532234a1dae6xxx",
                "id": 558,
                "status": 2,
                "submit_time": "2016-12-07 18:51:57"
            }...
        ],
        "pageIndex": 1,
        "pageSize": 10,
        "total": 8

    }
```
###### 返回说明
```
code : 返回代码
    message : 提示信息
    amount : 充值金额
    confirmTimes : 充值确认次数
    currency : 充值货币类型(大写)
    description : 充值记录状态描述
    hash : 充值交易号
    id : 充值记录id
    status : 状态(0等待确认，1充值失败，2充值成功)
    submit_time : 充值时间
    address : 充值地址
```
[1-2.9 GET /api/v2/getCnyWithdrawRecord]() 获取人民币提现记录
###### 请求说明
| 参数名 | 描述 |   
| ------------ | :-----: | 
|method|直接赋值getChargeRecord|
|accesskey|accesskey|
|pageIndex|当前页数|
|pageSize|每页数量|
|sign|请求加密签名串|
|reqTime|当前时间毫秒数|
###### 代码示例
```
//# Request
    GET  http://api.ikedou.net/api/v2/getCnyWithdrawRecord?method=getCnyWithdrawRecord
        &accesskey=your_access_key&pageIndex=1&pageSize=10
        &sign=请求加密签名串&reqTime=当前时间毫秒数
    //# Response
    {

        "list": [
            {
                "account": "****4523",
                "amount": 1000,
                "bank": "中国邮政银行",
                "date": 1473155402000,
                "description": "预计09月07日24点前到账<p class=\"auditico2\"></p>",
                "fee": 5,
                "serial_number": 2016090686300,
                "status": 3,
                "type": "易宝支付"
            }...
        ],
        "pageIndex": 1,
        "pageSize": 10,
        "total": 20


    }
                   
```
###### 返回说明
```
code : 返回代码
    message : 提示信息
    account : 收款账号(只显示后4位)
    amount : 提现金额
    date : 提现时间，格式为时间戳
    description : 提现记录状态描述
    fees : 提现手续费
    serial_number : 提现记录id
    status : 提现记录状态(10等待实名认证，6等待确认，4提现成功，14提现中，3提现中，5提现失败)
    type : 提现类型
```
[1-2.10 GET /api/v2/getCnyChargeRecord]() 获取人民币充值记录
###### 请求说明
| 参数名 | 描述 |   
| ------------ | :-----: | 
|method|直接赋值getChargeRecord|
|accesskey|accesskey|
|pageIndex|当前页数|
|pageSize|每页数量|
|sign|请求加密签名串|
|reqTime|当前时间毫秒数|
###### 代码示例
```
//# Request
    GET  http://api.ikedou.net/api/v2/getCnyChargeRecord?method=getCnyChargeRecord
        &accesskey=your_access_key&pageIndex=1&pageSize=10
        &sign=请求加密签名串&reqTime=当前时间毫秒数
    //# Response
    {
        "list": [
            {
                "account": "6212867603158157",
                "amount": 1234,
                "date": 1460102487000,
                "description": "确认成功<p class=\"auditico3\"></p>",
                "serial_number": 20160408241,
                "status": 0,
                "type": "线下银行汇款"
            }...
        ],
        "pageIndex": 1,
        "pageSize": 10,
        "total": 32

    }
```
###### 返回说明
```
code : 返回代码
    message : 提示信息
    account : 充值账号
    amount : 充值金额
    date : 充值时间
    description : 充值状态描述
    serial_number : 充值记录id
    status : 充值状态(9等待处理，1充值失败，0充值成功，13充值已过期，8已取消，2处理中，10等待实名认证)
    type : 充值类型
```

## 1-3 错误代码
所有API方法调用在请求失败或遇到未知错误时会返回JSON错误对象。
| 参数名 | 描述 |   
| ------------ | :-----: | 
|0000|调用成功|
|1000|系统异常|
|2000|非法访问|
|2001|accessKey不能为空|
|2002|accessKey非法|
|2011|timestamp格式不正确|
|2012|timestamp与服务器时间相差太多|
|2021|sign不能为空|
|2022|签名不一致|
|2023|签名失败|
|2031|业务参数不能为空|
|3000|用户状态异常|
|3001|钱包余额不足|
|3100|不支持的交易|
|3101|当前时间段停止交易|
|3102|交易价格不在合理范围内|
|3103|交易金额不在合理范围内|
|3104|委单失败|
|3105|撤销委单失败|
|3106|撤销所有委单失败|
|3107|获取委单信息失败|
|3108|不支持的币种|
|3109|超过每日提现次数|
|3110|超过每日提现最大数量|
|3111|需绑定手机或者邮箱|
|3112|需要实名认证|
|3113|账号异常被冻结，如有疑问请联系客服|
|3114|用户禁止提现，如有疑问请联系客服|
|3115|平仓中|
|3116|提现小于最小值|
|3117|需要设置交易密码|
|3118|交易密码错误超过当天最大次数|
|3119|交易密码错误|
|3120|用户余额不足|
|3121|提现地址未验证|
|3122|请求频率太高,请稍后|
|3123|无对应地址|

## 1-4 示例代码
目前支持JAVA 版本。其他语言版本会相继支持。如果在使用过程中有任何问题请联系我们API技术QQ群： ，我们将在第一时间帮您解决技术问题。

签名方式： 先用sha加密secretkey，然后根据加密过的secretkey把请求的参数签名，请求参数按照上述接口参数列表排序加密，通过md5填充16位加密

```
//......
public void order() throws Exception {
    HashMap<String, Object> paramMap = new LinkedHashMap();
    paramMap.put("method", "order");
    paramMap.put("accesskey", accessKey);
    paramMap.put("price", "0.00005418");
    paramMap.put("amount", "1");
    paramMap.put("tradeType", "0");
    paramMap.put("currency", "btm_btc");
    signUp(paramMap);
    String result = restTemplate.getForObject(domainUrl + "order?method={method}" +
            "&accesskey={accesskey}&price={price}&amount={amount}&tradeType={tradeType}¤cy={currency}" +
            "&sign={sign}&reqTime={reqTime}", String.class, paramMap);
    System.out.println(result);
}
//......
           
```

## 1-5 文档下载

## 1-6 常见问题
### 访问限制
1.单个用户限制每1秒秒只能请求一次数据。

# 2.WebSocket API (已开启)

## WebSocket服务地址
蝌蚪 WebSocket服务连接地址：ws://api.winpro.vip/depth.ws
## 2-1 行情API
[2-1.1 ticker]() 行情
###### 请求说明
| 参数名 | 描述 |   
| ------------ | :-----: | 
|currency|平台可用交易对|

###### 代码示例
```
//# Request
{
  "event":"addChannel",
  "channel":"ticker"
  "currency":"qtum_qc"
}

//# Response
{
  "channel": "ticker",
  "code": 0000,
  "date": {
  		"ticker":{ 
			  			  "buy": "3826.94",
			              "high": "3838.22",
			              "last": "3826.94",
			              "low": "3802.0",
			              "sell": "3828.25",
			              "vol": "90151.83"
            		 		},
        "message": "操作成功。"
       				}
}
        
```
###### 返回说明
```
date: 返回数据时服务器时间
high : 最高价
low : 最低价
buy : 买一价
sell : 卖一价
last : 最新成交价
vol : 成交量(最近的24小时)
channel : 当前请求的channel
```
[2-1.2 depth ]() 深度
###### 请求说明
| 参数名 | 描述 |   
| ------------ | :-----: | 
|currency|平台可用交易对|

###### 代码示例
```
//# Request
{
  "event": "addChannel",
  "channel": "depth",
  "currency":"qtum_qc"
}

//# Response
{	
"channel": "depth",
"code": "0000"
"data":{
		  "asks": [ [ 3846.94, 0.659 ]... ],
		  "bids": [ [ 3826.94, 4.843 ]... ],
		  "timestamp": "1522046641"
		},
"message": "操作成功。"
}
```
###### 返回说明
```
asks : 卖方深度
bids : 买方深度
channel : 当前请求的channel
```
[2-1.3 trades]() 历史成交
###### 请求说明
| 参数名 | 描述 |   
| ------------ | :-----: | 
|currency|平台可用交易对|

###### 代码示例
```
//# Request
{
  "event":"addChannel",
  "channel":"trades",
  "currency":"qtum_qc"
}

//# Response
{
"channel": "trades",
"code": "0000"
"data":
          [
            { "date": "1443428902",
              "price": "1565.91",
              "amount": "0.553",
              "tid": "37594617",
              "type": "sell",
              "trade_type": "ask"
            }
            ...
          ],
  "message": "操作成功。"
}
                   
```
###### 返回说明
```
date : 交易时间
price : 交易价格
amount : 交易数量
tid : 交易生成ID
type : buy/sell
trade_type : 委托类型，ask(卖)/bid(买)
channel : 当前请求的channel 
```
[2-1.4]() k线
###### 请求说明
| 参数名 | 描述 |   
| ------------ | :-----: | 
|currency|平台可用交易对|

###### 代码示例
```
//# Request
{
  "event":"addChannel",
  "channel":"kline",
  "currency":"qtum_qc"
}

//# Response
{
"channel": "kline",
"code": "0000"
"data":
          [
            { 
 				1472107500000,
           		3840.46,
	            3843.56,
	            3839.58,
	            3843.3,
	            492.456
            }
            ...
          ],
  "message": "操作成功。"
}
            
```
###### 返回说明
```
data : 内容说明
    [
    1417536000000, 时间戳
    2370.16, 开
    2380, 高
    2352, 低
    2367.37, 收
    17259.83 交易量
    ]
		
```

## 2-2 交易API

**请求参数说明**(加密签名请根据这个顺序签名,sign和reqTime不用加入待签名字符串)

[2-2.1 order]() 委托下单
###### 请求说明
| 参数名 | 描述 |   
| ------------ | :-----: | 
|accesskey|accesskey|
|amount|交易数量|
|channel|请求的频道|
|currency|平台可用交易对|
|event|直接赋值 addChannel|
|id|请求的唯一标识，用于在返回内容时区分|
|price|单价|
|reqTime|请求时间|
|sign|请求加密签名串|
|tradeType|交易类型 1/0[buy/sell]|

###### 代码示例
```
//# Request
{
  "accesskey": your key,
  "amount": 0.01,
  "channel": "order",
  "currency": "qtum_btc",
  "event": "addChannel",
  "price": 100000,
  "reqTime": "1522052965561",
  "sign": 签名,
  "tradeType": 1
}

//# Response
{
  "channel": "order",
  "code": 1000,
  "data": 201711133673,
  "id": 201711133673,
  "message":"操作成功。"
}
```
###### 返回说明
```
code : 返回代码
message : 提示信息
Id : 委托挂单号
channel : 请求的频道
data : 请求的唯一标识，用于在返回内容时区分
```

[2-2.2 cancelOrder]() 取消委托
###### 请求说明
| 参数名 | 描述 |   
| ------------ | :-----: | 
|accesskey|accesskey|
|channel|请求的频道|
|currency|平台可用交易对|
|event|直接赋值 addChannel|
|id|委托订单号|
|price|单价|
|tradeType|交易类型 1/0[buy/sell]|
|sign|请求加密签名串|
|reqTime|请求时间|

###### 代码示例
```
//# Request
{
  "accesskey": your key,
  "amount": 0.01,
  "channel": "order",
  "currency": "qtum_btc",
  "event": "addChannel",
  "price": 100000,
  "reqTime": "1522052965561",
  "sign": 签名,
  "tradeType": 1
}

//# Response
{
  "channel": "order",
  "code": 1000,
  "data": 201711133673,
  "id": 201711133673,
  "message":"操作成功。"
}
```
###### 返回说明
```
code : 返回代码
message : 提示信息
Id : 委托挂单号
channel : 请求的频道
data : 请求的唯一标识，用于在返回内容时区分
```
[2-2.3 getOrder]() 获取委托买单或卖单
###### 请求说明
| 参数名 | 描述 |   
| ------------ | :-----: | 
|accesskey|accesskey|
|channel|请求的频道|
|currency|平台可用交易对|
|event|直接赋值 addChannel|
|id|委托挂单号|
|sign|请求加密签名串|
|reqTime|请求时间|

###### 代码示例
```
//# Request
{
  "accesskey": your key,
  "channel": "getOrder",
  "event": "addChannel",
  "id": 20160902387645980,
  "currency": "qtum_qc",
  "reqTime": "1522052965561",
  "sign": 签名
}

//# Response
{
"channel": "getorder",
"code": "0000",
"data": {
		        "currency": "btc",
		        "id": "20150928158614292",
		        "price": 1560,
		        "status": 3,
		        "total_amount": 0.1,
		        "trade_amount": 0,
		        "trade_price" : 6000,
		        "trade_date": 1443410396717,
		        "trade_money": 0,
		        "type": 0,
          },
  "message": "操作成功。",
}
    
```
###### 返回说明
```
 currency : 交易类型
    id : 委托挂单号
    price : 单价
    status : 挂单状态(1：待成交,2：待成交未交易部份,3：交易完成,5：已取消)
    total_amount : 挂单总数量
    trade_amount : 已成交数量
    trade_date : 委托时间
    trade_money : 已成交总金额
    trade_price : 成交均价
    type : 挂单类型 0/1[buy/sell]
```

[2-2.4 getOrders]() API获取多个委托买或卖单，每次请求返回PageSize小于100条记录
###### 请求说明
| 参数名 | 描述 |   
| ------------ | :-----: | 
|accesskey|accesskey|
|channel|请求的频道|
|currency|平台可用交易对|
|event|直接赋值 addChannel|
|tradeType|交易类型 1/0[buy/sell]|
|sign|请求加密签名串|
|reqTime|请求时间|

###### 代码示例
```
//# Request
{
"accesskey": "your key",
"channel": "getOrders",
"event": "addChannel",
"currency": "qtum_qc",
"reqTime": "1522052965561",
"sign": 签名,
"tradeType": 1
}

//# Response
{
"channel": "getOrders",
  "code": 1000,
  "data":
          [
            {
              "currency": "qtum_qc",
              "fees": 0,
              "id": "20160901385862136",
              "price": 3700,
              "status": 0,
              "total_amount": 1.845,
              "trade_amount": 0,
              "trade_price": 6000,
              "trade_date": 1472706387742,
              "trade_money": 0,
              "type": 1
            }
            ...
          ],
  "message": "操作成功。",
}
                                                    
    
```
###### 返回说明
```
 currency : 交易类型
    id : 委托挂单号
    price : 单价
    status : 挂单状态(1：待成交,2：待成交未交易部份,3：交易完成,5：已取消)
    total_amount : 挂单总数量
    trade_amount : 已成交数量
    trade_date : 委托时间
    trade_money : 已成交总金额
    trade_price : 成交均价
    type : 挂单类型 0/1[buy/sell]
```

[2-2.5 getAccountInfo]() 获取用户信息
###### 请求说明
| 参数名 | 描述 |   
| ------------ | :-----: | 
|accesskey|accesskey|
|channel|请求的频道|
|event|直接赋值 addChannel|
|sign|请求加密签名串|
|reqTime|请求时间|
###### 代码示例
```
 //# Request
{
  "accesskey": "your key",
  "channel": "getAccountInfo",
  "event": "addChannel",
  "reqTime": "1522052965561",
  "sign": 签名,
}

//# Response
{
  "channel": "getAccountInfo",
  "code": 0000,
  "data":
	[{
	"totalAssets" : "123.551",
	"balance": {
	   
	            BTC:0
	           
				}
					
	}],
          
  
  "message": "操作成功。",
}
                           
```
###### 返回说明
```
 totalAssets : 折合总资产(RMB)
    balance(余额):

      BTCbtc:余额
    
                   
```

[2-2.6 getUserAddress]() 获取用户充值地址
###### 请求说明
| 参数名 | 描述 |   
| ------------ | :-----: | 
|accesskey|accesskey|
|channel|请求的频道|
|event|直接赋值 addChannel|
|currency|平台可用币种|
|sign|请求加密签名串|
|reqTime|请求时间|
###### 代码示例
```
//# Request
{
  "accesskey": "your key",
  "channel": "getUserAddress",
  "event": "addChannel",
  "currency": "btc",
  "sign": 签名,
  "reqTime":  "1522055674049"
}

//# Response
{
  "channel": "getUserAddress",
  "code": 1000,
  "data":
            {
            "key": "1CanG32LphhbabWrmtrsb6R5K4ZdVCD7V6",
            }
  
  "message": "操作成功。",
}
                           
```
###### 返回说明
```
 key : 地址
```

[2-2.7 getWithdrawRecord]() 获取数字资产提现记录
###### 请求说明
| 参数名 | 描述 |   
| ------------ | :-----: | 
|accesskey|accesskey|
|channel|请求的频道|
|currency|平台可用币种|
|event|直接赋值 addChannel|
|pageIndex|当前页数|
|pageSize|每页数量|
|sign|请求加密签名串|
|reqTime|请求时间|

###### 代码示例
```
//# Request
{
  "accesskey": "your key",
  "channel": "getWithdrawRecord",
  "event": "addChannel",
  "currency": "btc",
   "reqTime": "1522055993352",
  "sign": "签名"
}
//# Response
{
  "channel": "getWithdrawRecord",
  "code": 0000,
  "data":
             {
             "list": 
                    [{
                        "amount": 0.01,
                        "fees": 0.001,
                        "id": 2016042556231,
                        "manageTime": 1461579340000,
                        "status": 3,
                        "submitTime": 1461579288000,
                        "toAddress": "14fxEPirL9fyfw1i9EF439Pq6gQ5xijUmp"
                    }...
                ],
        "pageIndex": 1,
        "pageSize": 10,
        "totalCount": 4,
        "totalPage": 1

             }         
                      
  "message": "操作成功",
}
                        
```
###### 返回说明
```
 code : 返回代码
    message : 提示信息
    amount : 提现金额
    fees : 提现手续费
    id : 提现记录id
    manageTime : 提现处理的时间的时间戳
    submitTime : 提现发起的时间的时间戳
    toAddress : 提现的接收地址 
```

[2-2.8 getChargeRecord]() 获取数字资产提现记录
###### 请求说明
| 参数名 | 描述 |   
| ------------ | :-----: | 
|accesskey|accesskey|
|channel|请求的频道|
|currency|平台可用币种|
|event|直接赋值 addChannel|
|pageIndex|当前页数|
|pageSize|每页数量|
|sign|请求加密签名串|
|reqTime|请求时间|

###### 代码示例
```
//# Request
{
  "accesskey": "your key",
  "channel": "getChargeRecord",
  "event": "addChannel",
  "currency": "btc",
   "reqTime": "1522055993352",
  "sign": "签名"
}
//# Response
{
  "channel": "getChargeRecord",
  "code": 0000,
  "data":
             {
             "list": 
                    [{
                        "address": "1FKN1DZqCm8HaTujDioRL2Aezdh7Qj7xxx",
		                "amount": "1.00000000",
		                "confirmTimes": 1,
		                "currency": "BTC",
		                "description": "确认成功",
		                "hash": "7ce842de187c379abafadd64a5fe66c5c61c8a21fb04edff9532234a1dae6xxx",
		                "id": 558,
		                "status": 2,
		                "submit_time": "2016-12-07 18:51:57"
                    }...
                ],
        "pageIndex": 1,
        "pageSize": 10,
        "total": 8

             }         
                      
  "message": "操作成功",
}
```
###### 返回说明
```
 code : 返回代码
    message : 提示信息
    amount : 充值金额
    confirmTimes : 充值确认次数
    currency : 充值货币类型(大写)
    description : 充值记录状态描述
    hash : 充值交易号
    id : 充值记录id
    status : 状态(0等待确认，1充值失败，2充值成功)
    submit_time : 充值时间
    address : 充值地址
                   
```

## 2-3 错误代码
 失败或遇到未知错误时会返回JSON错误对象。
| 参数名 | 描述 |   
| ------------ | :-----: | 
|0000|调用成功|
|1000|系统异常|
|2000|非法访问|
|2001|accessKey不能为空|
|2002|accessKey非法|
|2011|timestamp格式不正确|
|2012|timestamp与服务器时间相差太多|
|2021|sign不能为空|
|2022|签名不一致|
|2023|签名失败|
|2031|业务参数不能为空|
|3000|用户状态异常|
|3001|钱包余额不足|
|3100|不支持的交易|
|3101|当前时间段停止交易|
|3102|交易价格不在合理范围内|
|3103|交易金额不在合理范围内|
|3104|委单失败|
|3105|撤销委单失败|
|3106|撤销所有委单失败|
|3107|获取委单信息失败|
|3108|不支持的币种|
|3109|超过每日提现次数|
|3110|超过每日提现最大数量|
|3111|需绑定手机或者邮箱|
|3112|需要实名认证|
|3113|账号异常被冻结，如有疑问请联系客服|
|3114|用户禁止提现，如有疑问请联系客服|
|3115|平仓中|
|3116|提现小于最小值|
|3117|需要设置交易密码|
|3118|交易密码错误超过当天最大次数|
|3119|交易密码错误|
|3120|用户余额不足|
|3121|提现地址未验证|
|3122|请求频率太高,请稍后|
|3123|无对应地址|

## 2-4 示例代码

目前支持C#、C++、HTML、JAVA、Python、VB 版本。其他语言版本会相继支持。如果在使用过程中有任何问题请联系我们API技术QQ群： 247789217，我们将在第一时间帮您解决技术问题。


```
签名方式： 先用sha加密secretkey，然后根据加密过的secretkey把请求的参数签名，请求参数按照ascii值排序加密，通过hmac MD5加密
```

```
//......
/**
 * 获取用户资金信息
 * @param accessKey
 * @param secretKey
 */
public void getAccountInfo(){
    JSONObject data = new JSONObject(3,true);//3个参数的有序排列
    data.put("event", "addChannel");
    data.put("channel","getaccountinfo");
    data.put("accesskey", accessKey);
    
    String secret = EncryDigestUtil.digest(secretKey);//1.先用sha加密
    String sign = EncryDigestUtil.hmacSign(data.toString(), secret);//2.通过hmac MD5加密
    
    data.put("sign", sign);
    this.channel.writeAndFlush(new TextWebSocketFrame(data.toString()));
}
//......
```

## 2-5 文档下载
[下载DEMO以及开发文档]()

## 2-6 常见问题

#### 访问限制
```
1.单个用户限制每秒钟30次访问，一秒钟内30次以上的请求，将会视作无效。
```














