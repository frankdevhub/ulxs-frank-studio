/************************************************************************************************************************************
**@author ulxsfrankstudio
*************************************************************************************************************************************/        
var code,accessToken,openId

(function($){
            $.fn.textFocus=function(v){
                var range,len,v=v===undefined?0:parseInt(v);
                this.each(function(){
                    if($.browser.msie){
                        range=this.createTextRange();
                        v===0?range.collapse(false):range.move("character",v);
                        range.select();
                    }else{
                        len=this.value.length;
                        v===0?this.setSelectionRange(len,len):this.setSelectionRange(v,v);
                    }
                    this.focus();
                });
                return this;
            }
        })(jQuery);

        $("#cny0000").on("focus", function () {
              cny0000EBI = document.getElementById('cny0000');
              $("#cny0000").textFocus(cny0000EBI.value.split('.')[0].length);
            if (cny0000EBI.value == "¥ _______" ){
              cny0000EBI.value = "¥ .00";
            }
              $("#cny0000").textFocus(cny0000EBI.value.split('.')[0].length);
        });

        $("#cny0000").on("input", function () {
              cny0000EBI = document.getElementById('cny0000');
              $("#cny0000").textFocus(cny0000EBI.value.split('.')[0].length);
              if(cny0000EBI.value.split(' ').length == 1){
                  cny0000EBI.value = '¥ ' + cny0000EBI.value.split('¥')[1];
              }
              cny0000EBI = document.getElementById('cny0000');
              $("#cny0000").textFocus(cny0000EBI.value.split('.')[0].length);
              if(cny0000EBI.value.split('¥').length == 1){
                  cny0000EBI.value = '¥ ' + cny0000EBI.value;
              }
              cny0000EBI = document.getElementById('cny0000');
              $("#cny0000").textFocus(cny0000EBI.value.split('.')[0].length);
              if(cny0000EBI.value.split('.').length == 1){
                  cny0000EBI.value =cny0000EBI.value+'.00';
              }
              cny0000EBI = document.getElementById('cny0000');
              $("#cny0000").textFocus(cny0000EBI.value.split('.')[0].length);
              if(cny0000EBI.value.split('.').length == 2){
                   if(cny0000EBI.value.split('.')[1] != '00'){
                      cny0000EBI.value =cny0000EBI.value.split('.')[0]+'.00';
                   }
              }
              cny0000EBI = document.getElementById('cny0000');
              $("#cny0000").textFocus(cny0000EBI.value.split('.')[0].length);
             document.getElementById('payment_money').value = "支付金额：¥" + cny0000EBI.value.split('¥')[1];
        });

        function hasClass(element,className){
            return element.className.indexOf(className) > -1;
        }
        function cny3500Click() {
            var cny0000EBI = document.getElementById('cny0000');
            var cny3500EBI = document.getElementById('cny3500');
            if(hasClass(cny3500EBI, 'weui-cny_in_input')){
            }else {
                cny0000EBI.className = 'weui-cny_out_input';
                cny3500EBI.className = 'weui-cny_in_input';
                if (cny0000EBI.value == "¥ .00"){
                    cny0000EBI.value = "¥ _______";
                }
            }
             document.getElementById('payment_money').value = "支付金额：¥" + cny3500EBI.value.split(' ')[1];
        }

        function IsNum(e) {
            var k = window.event ? e.keyCode : e.which;
            if (((k >= 48) && (k <= 57)) || k == 8 || k == 0) {
            } else {
                if (window.event) {
                    window.event.returnValue = false;
                }
                else {
                    e.preventDefault(); 
                }
            }
        }

        function cny0000Click() {
            var cny0000EBI = document.getElementById('cny0000');
            var cny3500EBI = document.getElementById('cny3500');
            if(hasClass(cny0000EBI, 'weui-cny_in_input')){
            }else {
                cny3500EBI.className = 'weui-cny_out_input';
                cny0000EBI.className = 'weui-cny_in_input';
                cny0000EBI.readOnly = false;
                cny0000EBI.style.setProperty('caret-color' ,'rgba(0,0,0,0)');
                if (cny0000EBI.value == "¥ _______" ){
                  cny0000EBI.value = "¥ .00";
                }
              $("#cny0000").textFocus(cny0000EBI.value.split('.')[0].length);
            }
             document.getElementById('payment_money').value = "支付金额：¥" + cny0000EBI.value.split(' ')[1];
        }
        function paySubmit() {
                var final_pay_str= document.pay_form.payment_money.value.split('：')[1].replace("¥","").replace(" ","").replace(" ","");
                if(Number(final_pay_str) <= 0){
                 notice(document.pay_form.payment_money.value.split('：')[1],'请填写金额','好');
                }else {
                    confirms( "请核对金额：" + document.pay_form.payment_money.value.split('：')[1],'支付确认');
                    document.getElementById("confirmY").addEventListener("click",function() {
                        final_pay_str = final_pay_str.split('.')[0];
                    	var payment = final_pay_str * 1000;
                    	var accessToken = $("#accessToken").val();
                    	console.log(accessToken);
                    	var openId = $("#openId").val();
                    	console.log(openId);
                    	
                    	var url = "http://www.jilu-samplestudio/payment/order?accessToken="+accessToken+"&openId="+openid+"&currency="+payment+"";
                	  	$.post(url,function(result) {
                  				appId = result.appId;
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
        
                    });
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
                         console.log('支付成功');
                     }else if(res.err_msg == "get_brand_wcpay_request:cancel"){
                  	   console.log('支付取消');
                     }else if(res.err_msg == "get_brand_wcpay_request:fail"){
                  	   console.log('支付失败');
                         WeixinJSBridge.call('closeWindow');
                     } 
           });   
        }
       
