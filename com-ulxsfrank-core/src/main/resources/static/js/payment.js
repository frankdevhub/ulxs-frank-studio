 function getQueryString(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
		var r = window.location.search.substr(1).match(reg);
		if (r != null) {
			return unescape(r[2]);
		}
		return null;
   }
   
   var appId,timeStamp,nonceStr,package,signType,paySign; 
   function do_payment(){
    var code = getQueryString("code");
  	console.log("code",code);
  	if(code){
  		var url = "http://jilu-samplestudio.com/payment/order?code="+code+"";
  	  	$.post(url,function(result) {
    			appId = result.appid;
  	  			console.log("appId", appId);
  				timeStamp = result.timeStamp;
  				nonceStr = result.nonceStr;
  				package = result.package;
  				signType = result.signType;
  				paySign = result.paySign;
  				
  				if (typeof WeixinJSBridge == "undefined") {
  					if (document.addEventListener) {
  						document.addEventListener('WeixinJSBridgeReady',
  								onBridgeReady, false);
  					} else if (document.attachEvent) {
  						document.attachEvent('WeixinJSBridgeReady',
  								onBridgeReady);
  						document.attachEvent('onWeixinJSBridgeReady',
  								onBridgeReady);
  					}
  				} else {
  					onBridgeReady();
  				}
  			});
  		} else {
  			alert("server internal error.")
  		}
  	}
  function onBridgeReady(){
  	  WeixinJSBridge.invoke( 'getBrandWCPayRequest', {
  		  "appId":appId,    
            "timeStamp":timeStamp,        
            "nonceStr":nonceStr,    
            "package":package,     
            "signType":signType,             
            "paySign":paySign  
           }, 
           function(res){      
        	   if(res.err_msg == "get_brand_wcpay_request:ok" ) {
                   console.log('pay success.');
               }else if(res.err_msg == "get_brand_wcpay_request:cancel"){
            	   console.log('pay canceled.');
               }else if(res.err_msg == "get_brand_wcpay_request:fail"){
            	   console.log('pay failure.');
                   WeixinJSBridge.call('closeWindow');
               } 
     });   
  }