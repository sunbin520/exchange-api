

import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;
import utils.EncryptDigestUtil;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by qiudanping on 2017/8/21.
 */
public class RestApiTest {

    private RestTemplate restTemplate = new RestTemplate();

    private String domainUrl = "http://api.goodvalue.one";

    private String accessKey = "";
    private String secretKey = "";



    @Test
    public void ticker() throws Exception {
        System.out.println(restTemplate.getForObject(domainUrl+"/data/v2/ticker?currency=eth_btc", String.class));
    }

    @Test
    public void depth() throws Exception {
        System.out.println(restTemplate.getForObject(domainUrl+"/data/v2/depth?currency=btm_cnyt&size=20&merge=0.1", String.class));
    }

    @Test
    public void trades() throws Exception {
        System.out.println(restTemplate.getForObject(domainUrl+"/data/v2/trades?currency=btm_cnyt", String.class));
    }

    @Test
    public void kline() throws Exception {
        System.out.println(restTemplate.getForObject(domainUrl+"/data/v2/kline?currency=btm_cnyt&type=1&size=1", String.class));
    }

    @Test
    public void order() throws Exception {
        HashMap<String, Object> paramMap = new LinkedHashMap();
        paramMap.put("method", "order");
        paramMap.put("accesskey", accessKey);
        paramMap.put("price", "0.00005418");
        paramMap.put("amount", "1");
        paramMap.put("tradeType", "1");
        paramMap.put("currency", "btm_btc");
        paramMap.put("reqTime",System.currentTimeMillis());
        signUp(paramMap);
        String result = restTemplate.getForObject(domainUrl + "/api/v2/order?method={method}" +
                "&accesskey={accesskey}&price={price}&amount={amount}&tradeType={tradeType}&currency={currency}" +
                "&sign={sign}&reqTime={reqTime}", String.class, paramMap);
        System.out.println(result);
    }

    @Test
    public void cancel() throws Exception {
        HashMap<String, Object> paramMap = new LinkedHashMap();
        paramMap.put("method", "cancel");
        paramMap.put("accesskey", accessKey);
        paramMap.put("id", "578352");
        paramMap.put("currency", "btm_btc");
        paramMap.put("reqTime",System.currentTimeMillis());
        signUp(paramMap);
        String result = restTemplate.getForObject(domainUrl + "/api/v2/cancel?method={method}" +
                "&accesskey={accesskey}&id={id}&currency={currency}" +
                "&sign={sign}&reqTime={reqTime}", String.class, paramMap);
        System.out.println(result);
    }

    @Test
    public void getOrder() throws Exception {
        HashMap<String, Object> paramMap = new LinkedHashMap();
        paramMap.put("method", "getOrder");
        paramMap.put("accesskey", accessKey);
        paramMap.put("id", "578732");
        paramMap.put("currency", "btm_btc");
        paramMap.put("reqTime",System.currentTimeMillis());
        signUp(paramMap);
        String result = restTemplate.getForObject(domainUrl + "/api/v2/getOrder?method={method}" +
                "&accesskey={accesskey}&id={id}&currency={currency}" +
                "&sign={sign}&reqTime={reqTime}", String.class, paramMap);
        System.out.println(result);
    }

    @Test
    public void getOrders() throws Exception {
        HashMap<String, Object> paramMap = new LinkedHashMap();
        paramMap.put("method", "getOrders");
        paramMap.put("accesskey", accessKey);
        paramMap.put("tradeType", "1");
        paramMap.put("currency", "eth_btc");
        paramMap.put("pageIndex", 1);
        paramMap.put("pageSize", 40);
        paramMap.put("reqTime",System.currentTimeMillis());
        signUp(paramMap);
        String result = restTemplate.getForObject(domainUrl + "/api/v2/getOrders?method={method}" +
                "&accesskey={accesskey}&tradeType={tradeType}&currency={currency}&pageIndex={pageIndex}&pageSize={pageSize}" +
                "&sign={sign}&reqTime={reqTime}", String.class, paramMap);
        System.out.println(result);
    }

    @Test
    public void getAccountInfo() throws Exception {
        HashMap<String, Object> paramMap = new LinkedHashMap();
        paramMap.put("method", "getAccountInfo");
        paramMap.put("accesskey", accessKey);
        paramMap.put("reqTime",System.currentTimeMillis());
        signUp(paramMap);
        String result = restTemplate.getForObject(domainUrl + "/api/v2/getAccountInfo?method={method}" +
                "&accesskey={accesskey}" +
                "&sign={sign}&reqTime={reqTime}", String.class, paramMap);
        System.out.println(result);
    }


    private void signUp(HashMap paramMap) {
        try {
            //......
            String params = signUpParam(paramMap);
            String secret = EncryptDigestUtil.digest(secretKey);
            String sign = EncryptDigestUtil.hmacSign(params, secret);
            paramMap.put("sign", sign);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public  String signUpParam(HashMap<String,Object> paramMap){
        StringBuilder sb=new StringBuilder();
        for (String key: paramMap.keySet()) {
            sb.append(key).append("=").append(paramMap.get(key)).append("&");
        }
        return  sb.substring(0,sb.length()-1);
    }


}
