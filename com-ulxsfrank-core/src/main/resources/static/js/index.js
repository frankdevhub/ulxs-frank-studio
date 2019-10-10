/************************************************************************************************************************************
**@author ulxsfrankstudio
*************************************************************************************************************************************/ 
$(function(){
            var email_account = getQueryString("account");
            if(email_account != null && email_account != ""){
                $.ajaxSettings.async = false;
                document.login_form.account.value = email_account;
                $.ajaxSettings.async = true;
                emailSubmit();
            }
            $("#account").changeTips({
                divTip:".on_changes"
            });
             $("input[id='account']").focus (function () {
                document.getElementById('login_input_div').style.setProperty('border' ,"1px solid #44b549");
            });
             $("input[id='account']").blur  (function () {
                document.getElementById('login_input_div').style.setProperty('border' ,"1px solid #e7e7eb");
            });
 });
        function emailSubmit() {
            if(chkEmail(document.login_form.account.value)){//邮箱符合条件
                confirms(document.login_form.account.value,'邮箱确认');
                document.getElementById("confirmY").addEventListener("click",function() {
                    window.location.href = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx15c*********&redirect_uri=http%3a%2f%2fwww.***.com%2fpay.jsp&response_type=code&scope=snsapi_base#wechat_redirect";
                });
            }
            else {
                 notice(login_form.account.value,'邮箱格式有误','返 回');
            }
        }

 