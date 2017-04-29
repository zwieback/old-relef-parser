var v = w.frameCacheVars = {
    'CACHE_MODE': 'HTMLCACHE',
    'banner': {
        'url': 'http://www.1c-bitrix.ru/composite/',
        'text': 'Быстро с 1С-Битрикс',
        'bgcolor': '#FFFFFF',
        'style': 'grey'
    },
    'storageBlocks': ['bxdynamic_cc-area-bookmark', 'bxdynamic_cc-area-personal_contact', 'cc-area-manager', 'cc-area-loyalty_balance', 'bxdynamic_r8Kull']
};
var r = w.XMLHttpRequest ? new XMLHttpRequest() : (w.ActiveXObject ? new w.ActiveXObject("Microsoft.XMLHTTP") : null);
if (!r) {
    return;
}
w.frameRequestStart = true;
var m = v.CACHE_MODE;
var l = w.location;
var x = new Date().getTime();
var q = "?bxrand=" + x + (l.search.length > 0 ? "&" + l.search.substring(1) : "");
var u = l.protocol + "//" + l.host + l.pathname + q;
r.open("GET", u, true);
r.setRequestHeader("BX-ACTION-TYPE", "get_dynamic");
r.setRequestHeader("BX-REF", document.referrer || "");
r.setRequestHeader("BX-CACHE-MODE", m);
if (m === "APPCACHE") {
    r.setRequestHeader("BX-APPCACHE-PARAMS", JSON.stringify(v.PARAMS));
    r.setRequestHeader("BX-APPCACHE-URL", v.PAGE_URL ? v.PAGE_URL : "");
}
r.onreadystatechange = function() {
    if (r.readyState != 4) {
        return;
    }
    var a = r.getResponseHeader("BX-RAND");
    var b = w.BX && w.BX.frameCache ? w.BX.frameCache : false;
    if (a != x || !((r.status >= 200 && r.status < 300) || r.status === 304 || r.status === 1223 || r.status === 0)) {
        var f = {
            error: true,
            reason: a != x ? "bad_rand" : "bad_status",
            url: u,
            xhr: r,
            status: r.status
        };
        if (w.BX && w.BX.ready) {
            BX.ready(function() {
                BX.onCustomEvent("onFrameDataRequestFail", [f]);
            });
        } else {
            w.frameRequestFail = f;
        }
        return;
    }
    if (b) {
        b.onFrameDataReceived(r.responseText);
        if (!w.frameUpdateInvoked) {
            b.update(false);
        }
        w.frameUpdateInvoked = true;
    } else {
        w.frameDataString = r.responseText;
    }
};
r.send();
}
