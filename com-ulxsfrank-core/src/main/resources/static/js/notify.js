/************************************************************************************************************************************
**@author ulxsfrankstudio
*************************************************************************************************************************************/
(function($){
	$.fn.extend({
		"changeTips":function(value){
			value = $.extend({
				divTip:""
			},value)
			var $this = $(this);
			$(document).click(function(event){
				if(document.getElementById("btn_login").style.display != "block" && $(event.target).attr("email") != null){
					if($(event.target).attr("class") == value.divTip || $(event.target).is("li")){
						$this.val( $(event.target).text());
						blus();
						emailSubmit();
					}
				}else{
					blus();
				}
			})
			
			function blus(){
				document.getElementById("btn_login").style.display="block";
				$(value.divTip).hide();
			}
			function valChange(){
				document.getElementById("btn_login").style.display="none";
				document.getElementById('login_input_div').style.setProperty('border' ,"1px solid #44b549");
				var tex = $this.val();
				var fronts = "";
				var af = /@/;
				var regMail = new RegExp(tex.substring(tex.indexOf("@")));

				if($this.val()==""){
					blus();
				}else{
					$(value.divTip).
					show().
					children().
					each(function(index) {
						var valAttr = $(this).attr("email");
						if(index==1){$(this).text(tex).addClass("active").siblings().removeClass();}
						
						if(index>1){
						
							if(af.test(tex)){
							
								fronts = tex.substring(tex.indexOf("@"),0);
								$(this).text(fronts+valAttr);
								
								if(regMail.test($(this).attr("email"))){
									$(this).show();
								}else{
									if(index>1){
										$(this).hide();
									}
								}

							}
							else{
								$(this).text(tex+valAttr);
							}
						}
	                })
				}
			}
			
			if($.browser.msie){
				$(this).bind("propertychange",function(){
					valChange();
				})
			}else{
				$(this).bind("input",function(){
					valChange();
				})
			}

		}
	})	
})(jQuery)