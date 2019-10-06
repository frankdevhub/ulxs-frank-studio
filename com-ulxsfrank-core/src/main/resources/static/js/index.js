 $(function(){
            var email_account = getQueryString("account");
            if(email_account != null){
                document.login_form.account.value = email_account;
                emailSubmit();
            }
  });
  function emailSubmit() {
            if(chkEmail(document.login_form.account.value)){//邮箱符合条件
                confirms(document.login_form.account.value,'邮箱确认');
                document.getElementById("confirmY").addEventListener("click",function() {
                    alert("odo 添加跳转链接 获取code");
                    //todo 添加跳转链接 获取code
                });
            }
            else {
                 notice(login_form.account.value,'邮箱格式有误','返 回');
            }
        }