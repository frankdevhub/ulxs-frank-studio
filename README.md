# ulxs-frank-studio
## WeChat online jsapi payment service（微信服务号JSAPI支付Demo）
### http://jilu-samplestudio.com/index.html

## User API (JSAPI)
```javascript
function onBridgeReady(){
   WeixinJSBridge.invoke(
      'getBrandWCPayRequest', {
         "appId":"wx2421b1c4370ec43b",     //公众号名称，由商户传入     
         "timeStamp":"1395712654",         //时间戳，自1970年以来的秒数     
         "nonceStr":"e61463f8efa94090b1f366cccfbbb444", //随机串     
         "package":"prepay_id=u802345jgfjsdfgsdg888",     
         "signType":"MD5",         //微信签名方式：     
         "paySign":"70EA570631E4BB79628FBCA90534C63FF7FADD89" //微信签名 
      },
      function(res){
      if(res.err_msg == "get_brand_wcpay_request:ok" ){
      // 使用以上方式判断前端返回,微信团队郑重提示：
            //res.err_msg将在用户支付成功后返回ok，但并不保证它绝对可靠。
      } 
   }); 
}
if (typeof WeixinJSBridge == "undefined"){
   if( document.addEventListener ){
       document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
   }else if (document.attachEvent){
       document.attachEvent('WeixinJSBridgeReady', onBridgeReady); 
       document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
   }
}else{
   onBridgeReady();
}
```
注：JS API的返回结果get_brand_wcpay_request:ok仅在用户成功完成支付时返回。由于前端交互复杂，get_brand_wcpay_request:cancel或者get_brand_wcpay_request:fail可以统一处理为用户遇到错误或者主动放弃，不必细化区分。

## API Document
- 1、网页端接口请求参数列表（参数需要重新进行签名计算，参与签名的参数为：appId、timeStamp、nonceStr、package、signType，参数区分大小写。）

名称 | 变量名 | 必填	| 类型 | 示例值 | 描述
-|-|-|-|-|-
公众号id | appId | 是 | String(16) | wx8888888888888888 | 商户注册具有支付权限的公众号成功后即可获得
时间戳 | timeStamp | 是 | String(32) | 1414561699 | 当前的时间
随机字符串 | nonceStr | 是 | String(32) | 5K8264ILTKCH16CQ2502SI8ZNMTM67VS | 随机字符串，不长于32位。推荐随机数生成算法
订单详情扩展字符串 | package | 是	| String(128) | prepay_id=123456789 | 统一下单接口返回的prepay_id参数值，提交格式如：prepay_id=***
签名方式 | signType | 是 | String(32) | MD5 | 签名类型，默认为MD5，支持HMAC-SHA256和MD5。注意此处需与统一下单的签名类型一致
签名 | paySign | 是 | String(64) | C380BEC2BFD727A4B6845133519F3AD6 | 签名

- 2、返回结果值说明

返回值 | 描述 |
-|-
get_brand_wcpay_request:ok | 支付成功 |
get_brand_wcpay_request:cancel | 支付过程中用户取消 |
get_brand_wcpay_request:fail | 	支付失败 |
调用支付JSAPI缺少参数：total_fee | 	1、请检查预支付会话标识prepay_id是否已失效 2、请求的appid与下单接口的appid是否一致 |



## Index Page
<img src="https://user-images.githubusercontent.com/29160332/66716061-b1151080-edfc-11e9-8901-4c8e59933fa8.png" width="35%" height="35%" />

## Select Payment
<img src="https://user-images.githubusercontent.com/29160332/66716104-ea4d8080-edfc-11e9-8d53-d37df22890be.png" width="35%" height="35%" />

## Payment Page
<img src="https://user-images.githubusercontent.com/29160332/66716011-51b70080-edfc-11e9-805a-607f19a2c5f3.png" width="35%" height="35%" />
