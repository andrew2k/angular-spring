
/* unblu enterprise offline snippet V4.2.20160712 */
unblu = {APIKEY: "MZsy5sFESYqU7MawXZgR_w", SERVER: "http://95.110.227.45:8081"};
(function() {
	unblu.c = [];
	unblu.registerApiConsumer = function(callback, errorCallback, apiNames) {
		if (callback instanceof Array) {
			if (window["Promise"]) {
				return new Promise(function(resolve, reject) {
					unblu.c.push({a: callback, c: resolve, e: reject});
				});
			} else {
				throw new Error("Promise not available");
			}
		}
		
		unblu.c.push({a: apiNames, c: callback, e: errorCallback});
	}	
	unblu.setLocale = function(locale) {
		unblu.l = locale;
	}
	function r(url) {
		var rewritingFn = null;
		if (rewritingFn) url = rewritingFn(url);
		return url;
	}
	if (!window["x-unblu-tmp-window-name"]) window["x-unblu-tmp-window-name"]=window.name;
	var q = document.getElementsByTagName("script")[0];
	if (q) {
		var g = document.createElement("script"); 
		g.setAttribute("src", r(unblu.SERVER + "/unblu/starter.js"));
		g.setAttribute("type", "text/javascript"); 
		g.setAttribute("defer","defer");
		q.parentNode.appendChild(g);
	}
})();
