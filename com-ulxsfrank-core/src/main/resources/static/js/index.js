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
 
 
 
 (function(){
		var email_account = getQueryString("account");
	    if(email_account != null){
	        document.login_form.account.value = email_account;
	        emailSubmit();
	    }
	
	    var emails = [{domain: '@qq.com', label: 'qq邮箱',icon:'../img/gmail.png'}, 
	                {domain: '@163.com',  label: '163邮箱',icon:'../img/gmail.png'},
	                {domain: '@126.com', label: '126邮箱',icon:'../img/gmail.png'},
	                {domain: '@hotmail.com', label: 'hotmail邮箱',icon:'../img/gmail.png'},
	                {domain: '@sina.com', label:'sina邮箱',icon:'../img/gmail.png'},
	                {domain: '@gmail.com',label:'gmail邮箱',icon:'../img/gmail.png'}, 
	                {domain: '@139.com', label: '139邮箱',icon:'../img/gmail.png'}, 
	                {domain:'@yahoo.com.cn', label: 'yahoo中国邮箱',icon:'../img/gmail.png'}];
	                
	    var addEmailHelp = function(id, otherAddress){
	        var inputObj = $('input#' + id);
	        if(inputObj.length == 0){
	            return;
	        }
	        
	        inputObj.keyup(function (ev) {
	            var val = $(inputObj).val();
	            var lastInputKey = val.charAt(val.length - 1);
	            
	            if (lastInputKey == '@') {
	                var indexOfAt = val.indexOf('@');
	                var username = val.substring(0, indexOfAt);

	                if ($('datalist#emailList').length > 0) {
	                    $('datalist#emailList').remove();
	                }

	                $(inputObj).parent().append('<datalist id="emailList"><img src="/img/gmail.png" /></datalist>');
	                for (var i in emails) {
	                    $('datalist#emailList').append('<option value="' + username + emails[i].domain + '" label="' + emails[i].label + '"/>');
	                }
	                
	                if(otherAddress != null && typeof otherAddress != 'undefined'){
	                    for (var i in otherAddress) {
	                    $('datalist#emailList').append('<option value="' + username + otherAddress[i].domain + '" label="' + otherAddress[i].label + '" />');
	                    }
	                }

	                $(inputObj).attr('list', 'emailList');
	            }
	        })
	    };
	    
	    window.addEmailHelp = addEmailHelp;
	})();
 
 $(document).ready(function(){
     addEmailHelp('email_input');
 });
 