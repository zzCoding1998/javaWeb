package com.zz.config;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id = "2016100100642656";
	
	// 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQC2bBlRQdknwxVx1i/pzwp/tPkRXX15ei61I54/OZ8lXUyR4L0/eqYCxRTPQPc23qvKSl7HTwBaXV24GH3nAMfbiojRK4chmmsp61X6WBGiH4nF4h4g/dZasQW3jteJh40buKNV34wHlXpq0IJPJB86XvV+B143/aTfCHLLq+wskYuLFJMHQGpWSJfkwY59ejRChuRSaViOYgHrg27Kzc7ixMfN8lM5K0DCv5bjwx2A6zvE0ZtNU3c5qEr7Hjw21t9QJt7GAZ3kcDvXGm7lDr3H//jeIxfeIOG4GRnUPElSMVegKjez4fBOB+jQTNva2Lv4yzwfCW4u2rKJstPPOzLBAgMBAAECggEALEqCux/pwaW0Bzeh2nrSab9e2cCBPoVT4dcMDP3IjiHg/Wo7iLYWqIWBT/DOFRxrSWpVKL8drtMMdi48I2IlJEC2h1KyNI5xhnQFJAtN/Lb7lHgaKJh4+vwQD0k3O8UVla12utrT/ONmHZkSinSSwHFd2YOgdKe46A39EUZkyU0DqQsJ3xidKlNF72zs9LoFaWYRjCp52l6Cr8ADiOHg9fCuKXLiTv8g/7lOspFM5BkLwPXK6F2FwkixdlNu8wJ8MGgHfLWjvZ3GBMUlrzZ5Ron18fJD4xR4waokeRT7SKsxJgxpc7+aaG9cRoCE+vRoSZXKn7k+Jo9kmhtGlLZWAQKBgQDxRlmjrlynYaXsmFipuCyHL+fq/rgWwSB8o98GfrjtJeQjEkznNTesTsnlGFfvzQTvdbzZNZZrt7NY5PdHaTpVOQd5BxWpvx0i9xNoMLpentEm4ZGRgt95d/tLzFuI8OQ5tvY40BvyaXNX5aN8gO/3ofKV2aml139geYFgxEnTyQKBgQDBjj5Mf1VXkOE3i/IGmCS2adQ2K3iHWtQqkZsWbK+wDzlizOBPSIeYE0NUrSwe0IGFrivdBbZh365afAKTZXa2Ub7eRNL6JIcqVqUrL/NyYuuw+Xngn5f3RmBgcs+QJwVltcJayNtCG4KZkxAnb6N4TiADwL4o4BWCj9+UgcozOQKBgBzho/dQ9uxeZwNEVVCzqDjihit+JEEtCxuAbkETyEUzBPUhyqzelPR+VfKjkFs3OoEDogCKRqBMVB6vAd69fOsYsYlOTMyOAQnfc6ZENvr+neC+nbNCbEfZEe7H3qks1NUHzr+MQdwpvVHnPkO4F4GYFDbV4FXmzuuYf+E6fe/pAoGAJkLTB9Yn7Z5jFtLgLfOHVlPHMkoXklIfHKcZzC4B8MChrGkpciK5QxOTvLpavWCkKePLIe7OJbzMv3BCJhbph5LTMybRucbcvT7ReZ8J9bOeOaanNAL4TFYqGQa5Bxrfk0KBv+ZyiTfc8jTC/biwzMhMxdElBfl95wg/vyCE8ukCgYBWVwLIZwTEoGtppKFztzzjItvOeIjghrtyvjs1m4nTf843wZst+ytcPGk0SnWrYby3FsU/dKw97IyXhMdGtqI14wa0u0vRcf5zJmbZyQURvp5BZ1WDEqJhe/jL/cH1UsRFQSSnBAX0tZUxjJ3RCd5HZ+GcZo8lxBt0Noyqx9FqPQ==";
	
	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAyZxh0WrJazqcG3cdZo0i23We66yBDMeVe4SGKNTmcLiCrGS18PVRD2t47rg4PlPDe19bEeA9/OhNvUNvxWe2r04okQG1QekW3ZSXR2ogSkFGcGuPhh7L0UHBTqrZeIgzbPaiULA5JlsZuJP3L4i92WAvOO3hzMdSNr6heGzvaEYibporm8MIb9wl53rIx7R1qWOXcsOWGw09kf+8XXlURyRWhccOxdGZIdj1mi5bcethS0X7xFyBUmeeQ2v3FNr3ehSsRzHfj68SR4nhjs6D2XNTJbAMBpsS286+OpjboLS7ejClOSg1rC7Gf7OJJH0gvZNQRMlSRgrkp3PEAl16UwIDAQAB";

	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://localhost:8080/shop/alipay/notify_url.jsp";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "http://localhost:8080/shop/order?method=orderSuccess";

	// 签名方式
	public static String sign_type = "RSA2";
	
	// 字符编码格式
	public static String charset = "utf-8";
	
	// 支付宝网关
	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
	
	// 日志记录文件
	//public static String log_path = "D:\\eclipse\\JavaEEWorkSpace\\alipay.trade.page.pay-JAVA-UTF-8";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /** 
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
	/*
	 * public static void logResult(String sWord) { FileWriter writer = null; try {
	 * writer = new FileWriter(log_path + "alipay_log_" +
	 * System.currentTimeMillis()+".txt"); writer.write(sWord); } catch (Exception
	 * e) { e.printStackTrace(); } finally { if (writer != null) { try {
	 * writer.close(); } catch (IOException e) { e.printStackTrace(); } } } }
	 */
}

