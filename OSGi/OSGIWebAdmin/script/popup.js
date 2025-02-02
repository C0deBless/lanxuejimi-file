/*
* popup.js v1.1
* http://fred_xu.cnblogs.com
*
* Updated by Fred Xu
* Date: 2010-04-13
*/
if (!Array.prototype.push) {
    Array.prototype.push = function() {
        var startLength = this.length;
        for (var j = 0; j < arguments.length; j++) {
            this[startLength + j] = arguments[j]
        }
        return this.length
    }
};
function G() {
    var elements = new Array();
    for (var i = 0; i < arguments.length; i++) {
        var element = arguments[i];
        if (typeof element == 'string') element = document.getElementById(element);
        if (arguments.length == 1) return element;
        elements.push(element)
    };
    return elements
};
function getValue(str) {
    var nResult = 0;
    switch (str) {
        case "scrollTop":
            if (typeof (window.pageYOffset) != 'undefined') {
                nResult = window.pageYOffset
            } else if (typeof (document.compatMode) != 'undefined' && document.compatMode != 'BackCompat') {
                nResult = document.documentElement.scrollTop
            } else if (typeof (document.body) != 'undefined') {
                nResult = document.body.scrollTop
            }
            break;
        case "clientWidth":
            if (typeof (document.compatMode) != 'undefined' && document.compatMode != 'BackCompat') {
                nResult = document.documentElement.clientWidth
            } else if (typeof (document.body) != 'undefined') {
                nResult = document.body.clientWidth
            }
            break;
        case "clientHeight":
            if (typeof (document.compatMode) != 'undefined' && document.compatMode != 'BackCompat') {
                nResult = document.documentElement.clientHeight
            } else if (typeof (document.body) != 'undefined') {
                nResult = document.body.clientHeight
            }
            break;
        case "scrollWidth":
            if (typeof (document.compatMode) != 'undefined' && document.compatMode != 'BackCompat') {
                nResult = document.documentElement.scrollWidth
            } else if (typeof (document.body) != 'undefined') {
                nResult = document.body.scrollWidth
            }
            break;
        case "scrollHeight":
            if (typeof (document.compatMode) != 'undefined' && document.compatMode != 'BackCompat') {
                nResult = document.documentElement.scrollHeight
            } else if (typeof (document.body) != 'undefined') {
                nResult = document.body.scrollHeight
            }
            break;
        default:
            break
    }
    return nResult
};
Function.prototype.bind = function(object) {
    var __method = this;
    return function() {
        __method.apply(object, arguments)
    }
};
Function.prototype.bindAsEventListener = function(object) {
    var __method = this;
    return function(event) {
        __method.call(object, event || window.event)
    }
};
Object.extend = function(destination, source) {
    for (property in source) {
        destination[property] = source[property]
    };
    return destination
};
if (!window.Event) {
    var Event = new Object()
};
Object.extend(Event, {
    observers: false,
    element: function(event) {
        return event.target || event.srcElement
    },
    isLeftClick: function(event) {
        return (((event.which) && (event.which == 1)) || ((event.button) && (event.button == 1)))
    },
    pointerX: function(event) {
        return event.pageX || (event.clientX + (document.documentElement.scrollLeft || document.body.scrollLeft))
    },
    pointerY: function(event) {
        return event.pageY || (event.clientY + (document.documentElement.scrollTop || document.body.scrollTop))
    },
    stop: function(event) {
        if (event.preventDefault) {
            event.preventDefault();
            event.stopPropagation()
        } else {
            event.returnValue = false;
            event.cancelBubble = true
        }
    },
    findElement: function(event, tagName) {
        var element = Event.element(event);
        while (element.parentNode && (!element.tagName || (element.tagName.toUpperCase() != tagName.toUpperCase()))) element = element.parentNode;
        return element
    },
    _observeAndCache: function(element, name, observer, useCapture) {
        if (!this.observers) this.observers = [];
        if (element.addEventListener) {
            this.observers.push([element, name, observer, useCapture]);
            element.addEventListener(name, observer, useCapture)
        } else if (element.attachEvent) {
            this.observers.push([element, name, observer, useCapture]);
            element.attachEvent('on' + name, observer)
        }
    },
    unloadCache: function() {
        if (!Event.observers) return;
        for (var j = 0; j < Event.observers.length; j++) {
            Event.stopObserving.apply(this, Event.observers[j]);
            Event.observers[j][0] = null
        };
        Event.observers = false
    },
    observe: function(element, name, observer, useCapture) {
        var element = G(element);
        useCapture = useCapture || false;
        if (name == 'keypress' && (navigator.appVersion.match(/Konqueror|Safari|KHTML/) || element.attachEvent)) name = 'keydown';
        this._observeAndCache(element, name, observer, useCapture)
    },
    stopObserving: function(element, name, observer, useCapture) {
        var element = G(element);
        useCapture = useCapture || false;
        if (name == 'keypress' && (navigator.appVersion.match(/Konqueror|Safari|KHTML/) || element.detachEvent)) name = 'keydown';
        if (element.removeEventListener) {
            element.removeEventListener(name, observer, useCapture)
        } else if (element.detachEvent) {
            element.detachEvent('on' + name, observer)
        }
    }
});
Event.observe(window, 'unload', Event.unloadCache, false);
var Class = function() {
    var _class = function() {
        this.initialize.apply(this, arguments)
    };
    for (j = 0; j < arguments.length; j++) {
        superClass = arguments[j];
        for (member in superClass.prototype) {
            _class.prototype[member] = superClass.prototype[member]
        }
    };
    _class.child = function() {
        return new Class(this)
    };
    _class.extend = function(f) {
        for (property in f) {
            _class.prototype[property] = f[property]
        }
    };
    return _class
};
function space(flag) {
    if (flag == "begin") {
        var ele = document.getElementById("ft");
        if (typeof (ele) != "undefined" && ele != null) ele.id = "ft_popup";
        ele = document.getElementById("usrbar");
        if (typeof (ele) != "undefined" && ele != null) ele.id = "usrbar_popup"
    } else if (flag == "end") {
        var ele = document.getElementById("ft_popup");
        if (typeof (ele) != "undefined" && ele != null) ele.id = "ft";
        ele = document.getElementById("usrbar_popup");
        if (typeof (ele) != "undefined" && ele != null) ele.id = "usrbar"
    }
};
var Popup = new Class();
Popup.prototype = {
    iframeIdName: 'ifr_popup',
    initialize: function(config) {
        this.config = Object.extend({
            contentType: 1,
            isHaveTitle: true,
            scrollType: 'no',
            isBackgroundCanClick: false,
            isSupportDraging: true,
            isShowShadow: true,
            isReloadOnClose: true,
            width: 400,
            height: 300
        },
        config || {});
        this.info = {
            shadowWidth: 4,
            title: "",
            contentUrl: "",
            contentHtml: "",
            callBack: null,
            parameter: null,
            confirmCon: "",
            alertCon: "",
            someHiddenTag: "select,object,embed",
            someHiddenEle: "",
            overlay: 0,
            coverOpacity: 40
        };
        this.color = {
            cColor: "#000000",
            bColor: "#FFFFFF",
            tColor: "#F6F6F6",
            wColor: "#C66653"
        };
        this.dropClass = null;
        this.someToHidden = [];
        if (!this.config.isHaveTitle) {
            this.config.isSupportDraging = false
        }
        this.iniBuild()
    },
    setContent: function(arrt, val) {
        if (val != '') {
            switch (arrt) {
                case 'width':
                    this.config.width = val;
                    break;
                case 'height':
                    this.config.height = val;
                    break;
                case 'title':
                    this.info.title = val;
                    break;
                case 'contentUrl':
                    this.info.contentUrl = val;
                    break;
                case 'contentHtml':
                    this.info.contentHtml = val;
                    break;
                case 'callBack':
                    this.info.callBack = val;
                    break;
                case 'parameter':
                    this.info.parameter = val;
                    break;
                case 'confirmCon':
                    this.info.confirmCon = val;
                    break;
                case 'alertCon':
                    this.info.alertCon = val;
                    break;
                case 'someHiddenTag':
                    this.info.someHiddenTag = val;
                    break;
                case 'someHiddenEle':
                    this.info.someHiddenEle = val;
                    break;
                case 'overlay':
                    this.info.overlay = val
            }
        }
    },
    iniBuild: function() {
        G('dialogCase') ? G('dialogCase').parentNode.removeChild(G('dialogCase')) : function() { };
        var oDiv = document.createElement('span');
        oDiv.id = 'dialogCase';
        document.body.appendChild(oDiv)
    },
    build: function() {
        var baseZIndex = 10001 + this.info.overlay * 10;
        var showZIndex = baseZIndex + 2;
        this.iframeIdName = 'ifr_popup' + this.info.overlay;
        var path = "image/dialogclose.gif";
        var close = '<input type="image" id="dialogBoxClose" src="'+path+'" border="0" width="16" height="16" align="absmiddle" title="关闭"/>';
        var cB = 'filter: alpha(opacity=' + this.info.coverOpacity + ');opacity:' + this.info.coverOpacity / 100 + ';';
        var cover = '<div id="dialogBoxBG" style="position:absolute;top:0px;left:0px;width:100%;height:100%;z-index:' + baseZIndex + ';' + cB + 'background-color:' + this.color.cColor + ';display:none;"></div>';
        var mainBox = '<div id="dialogBox" style="border:1px solid ' + this.color.tColor + ';display:none;z-index:' + showZIndex + ';position:relative;width:' + this.config.width + 'px;"><table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="' + this.color.bColor + '">';
        if (this.config.isHaveTitle) {
            mainBox += '<tr height="30" bgcolor="' + this.color.tColor + '"><td><table style="-moz-user-select:none;height:30px;" width="100%" border="0" cellpadding="0" cellspacing="0" ><tr><td width="6" height="24"></td><td id="dialogBoxTitle" style="color:' + this.color.wColor + ';font-size:14px;">' + this.info.title + '&nbsp;</td><td id="dialogClose" width="20" align="right" valign="middle">' + close + '</td><td width="6"></td></tr></table></td></tr>'
        } else {
            mainBox += '<tr height="10"><td align="right">' + close + '</td></tr>'
        };
        mainBox += '<tr style="height:' + this.config.height + 'px" valign="top"><td id="dialogBody" style="position:relative;"></td></tr></table></div><div id="dialogBoxShadow" style="display:none;z-index:' + baseZIndex + ';"></div>';
        if (!this.config.isBackgroundCanClick) {
            G('dialogCase').innerHTML = cover + mainBox;
            this.setBackgroundSize();
            window.onresize = this.setBackgroundSize
        } else {
            G('dialogCase').innerHTML = mainBox
        }
        Event.observe(G('dialogBoxClose'), "click", this.reset.bindAsEventListener(this), false);
        if (this.config.isSupportDraging) {
            dropClass = new Dragdrop(this.config.width, this.config.height, this.info.shadowWidth, this.config.isSupportDraging, this.config.contentType);
            G("dialogBoxTitle").style.cursor = "move"
        };
        this.lastBuild()
    },
    setBackgroundSize: function() {
        var getValueWidth;
        var getMaxValueWidth = [getValue("clientWidth"), getValue("scrollWidth")];
        getValueWidth = eval("Math.max(" + getMaxValueWidth.toString() + ")");
        G('dialogBoxBG').style.width = getValueWidth;
        var getValueHeight;
        var getMaxValueHeight = [getValue("clientHeight"), getValue("scrollHeight")];
        getValueHeight = eval("Math.max(" + getMaxValueHeight.toString() + ")");
        G('dialogBoxBG').style.height = getValueHeight + "px"
    },
    lastBuild: function() {
        var confirm = '<div style="width:100%;height:100%;text-align:center;"><div style="margin:20px 20px 0 20px;font-size:14px;line-height:16px;color:#000000;">' + this.info.confirmCon + '</div><div style="margin:20px;"><input id="dialogOk" type="button" class="btn" value=" 确定 "/>&nbsp;<input id="dialogCancel" type="button" class="btn" value=" 取消 "/></div></div>';
        var alert = '<div style="width:100%;height:100%;text-align:center;"><div style="margin:20px 20px 0 20px;font-size:14px;line-height:16px;color:#000000;">' + this.info.alertCon + '</div><div style="margin:20px;"><input id="dialogYES" type="button" class="btn" value=" 确定 "/></div></div>';
        var baseZIndex = 10001 + this.info.overlay * 10;
        var coverIfZIndex = baseZIndex + 4;
        if (this.config.contentType == 1) {
            var openIframe = "<iframe width='100%' style='height:" + this.config.height + "px' name='" + this.iframeIdName + "' id='" + this.iframeIdName + "' src='" + this.info.contentUrl + "' frameborder='0' scrolling='" + this.config.scrollType + "'></iframe>";
            var coverIframe = "<div id='iframeBG' style='position:absolute;top:0px;left:0px;width:1px;height:1px;z-index:" + coverIfZIndex + ";filter: alpha(opacity=00);opacity:0.00;background-color:#ffffff;'><div>";
            G("dialogBody").innerHTML = openIframe + coverIframe
        } else if (this.config.contentType == 2) {
            G("dialogBody").innerHTML = this.info.contentHtml
        } else if (this.config.contentType == 3) {
            G("dialogBody").innerHTML = confirm;
            Event.observe(G('dialogOk'), "click", this.forCallback.bindAsEventListener(this), false);
            Event.observe(G('dialogCancel'), "click", this.close.bindAsEventListener(this), false)
        } else if (this.config.contentType == 4) {
            G("dialogBody").innerHTML = alert;
            Event.observe(G('dialogYES'), "click", this.forCallback.bindAsEventListener(this), false)
        }
    },
    reBuild: function() {
        G('dialogBody').height = G('dialogBody').clientHeight;
        this.lastBuild()
    },
    show: function() {
        this.hiddenSome();
        this.middle();
        if (this.config.isShowShadow) this.shadow()
    },
    forCallback: function() {
        return this.info.callBack(this.info.parameter)
    },
    shadow: function() {
        var oShadow = G('dialogBoxShadow');
        var oDialog = G('dialogBox');
        oShadow['style']['position'] = "absolute";
        oShadow['style']['background'] = "#000";
        oShadow['style']['display'] = "";
        oShadow['style']['opacity'] = "0.2";
        oShadow['style']['filter'] = "alpha(opacity=20)";
        oShadow['style']['top'] = oDialog.offsetTop + this.info.shadowWidth + "px";
        oShadow['style']['left'] = oDialog.offsetLeft + this.info.shadowWidth + "px";
        oShadow['style']['width'] = oDialog.offsetWidth + "px";
        oShadow['style']['height'] = oDialog.offsetHeight + "px"
    },
    middle: function() {
        if (!this.config.isBackgroundCanClick) G('dialogBoxBG').style.display = '';
        var oDialog = G('dialogBox');
        oDialog['style']['position'] = "absolute";
        oDialog['style']['display'] = '';
        var sClientWidth = getValue("clientWidth");
        var sClientHeight = getValue("clientHeight");
        var sScrollTop = getValue("scrollTop");
        var sleft = (sClientWidth / 2) - (oDialog.offsetWidth / 2);
        var iTop = (sClientHeight / 2 + sScrollTop) - (oDialog.offsetHeight / 2);
        var sTop = iTop > 0 ? iTop : (sClientHeight / 2 + sScrollTop) - (oDialog.offsetHeight / 2);
        if (sTop < 1) sTop = "20";
        if (sleft < 1) sleft = "20";
        oDialog['style']['left'] = sleft + "px";
        oDialog['style']['top'] = sTop + "px"
    },
    reset: function() {
        if (this.config.isReloadOnClose) {
            top.location.reload()
        };
        this.close()
    },
    close: function() {
        G('dialogBox').style.display = 'none';
        if (!this.config.isBackgroundCanClick) G('dialogBoxBG').style.display = 'none';
        if (this.config.isShowShadow) G('dialogBoxShadow').style.display = 'none';
        G('dialogBody').innerHTML = '';
        this.showSome()
    },
    hiddenSome: function() {
        var tag = this.info.someHiddenTag.split(",");
        if (tag.length == 1 && tag[0] == "") {
            tag.length = 0
        }
        for (var j = 0; j < tag.length; j++) {
            this.hiddenTag(tag[j])
        };
        var ids = this.info.someHiddenEle.split(",");
        if (ids.length == 1 && ids[0] == "") ids.length = 0;
        for (var j = 0; j < ids.length; j++) {
            this.hiddenEle(ids[j])
        };
        space("begin")
    },
    hiddenTag: function(tagName) {
        var ele = document.getElementsByTagName(tagName);
        if (ele != null) {
            for (var j = 0; j < ele.length; j++) {
                if (ele[j].style.display != "none" && ele[j].style.visibility != 'hidden') {
                    ele[j].style.visibility = 'hidden';
                    this.someToHidden.push(ele[j])
                }
            }
        }
    },
    hiddenEle: function(id) {
        var ele = document.getElementById(id);
        if (typeof (ele) != "undefined" && ele != null) {
            ele.style.visibility = 'hidden';
            this.someToHidden.push(ele)
        }
    },
    showSome: function() {
        for (var j = 0; j < this.someToHidden.length; j++) {
            this.someToHidden[j].style.visibility = 'visible'
        };
        space("end")
    }
};
var Dragdrop = new Class();
Dragdrop.prototype = {
    initialize: function(width, height, shadowWidth, showShadow, contentType) {
        this.dragData = null;
        this.dragDataIn = null;
        this.backData = null;
        this.width = width;
        this.height = height;
        this.shadowWidth = shadowWidth;
        this.showShadow = showShadow;
        this.contentType = contentType;
        this.IsDraging = false;
        this.oObj = G('dialogBox');
        Event.observe(G('dialogBoxTitle'), "mousedown", this.moveStart.bindAsEventListener(this), false)
    },
    moveStart: function(event) {
        this.IsDraging = true;
        if (this.contentType == 1) {
            G("iframeBG").style.display = "";
            G("iframeBG").style.width = this.width;
            G("iframeBG").style.height = this.height
        };
        Event.observe(document, "mousemove", this.mousemove.bindAsEventListener(this), false);
        Event.observe(document, "mouseup", this.mouseup.bindAsEventListener(this), false);
        Event.observe(document, "selectstart", this.returnFalse, false);
        this.dragData = {
            x: Event.pointerX(event),
            y: Event.pointerY(event)
        };
        this.backData = {
            x: parseInt(this.oObj.style.left),
            y: parseInt(this.oObj.style.top)
        }
    },
    mousemove: function(event) {
        if (!this.IsDraging) return;
        var iLeft = Event.pointerX(event) - this.dragData["x"] + parseInt(this.oObj.style.left);
        var iTop = Event.pointerY(event) - this.dragData["y"] + parseInt(this.oObj.style.top);
        if (this.dragData["y"] < parseInt(this.oObj.style.top)) iTop = iTop - 12;
        else if (this.dragData["y"] > parseInt(this.oObj.style.top) + 25) iTop = iTop + 12;
        this.oObj.style.left = iLeft + "px";
        this.oObj.style.top = iTop + "px";
        if (this.showShadow) {
            G('dialogBoxShadow').style.left = iLeft + this.shadowWidth + "px";
            G('dialogBoxShadow').style.top = iTop + this.shadowWidth + "px"
        };
        this.dragData = {
            x: Event.pointerX(event),
            y: Event.pointerY(event)
        };
        document.body.style.cursor = "move"
    },
    mouseup: function(event) {
        if (!this.IsDraging) return;
        if (this.contentType == 1) G("iframeBG").style.display = "none";
        document.onmousemove = null;
        document.onmouseup = null;
        var mousX = Event.pointerX(event) - (document.documentElement.scrollLeft || document.body.scrollLeft);
        var mousY = Event.pointerY(event) - (document.documentElement.scrollTop || document.body.scrollTop);
        var oObjBottom;
        var oObjRight;
        if (this.showShadow) {
            oObjBottom = parseInt(this.oObj.style.top) + this.height + this.shadowWidth;
            oObjRight = parseInt(this.oObj.style.left) + this.width + this.shadowWidth
        } else {
            oObjBottom = parseInt(this.oObj.style.top) + this.height;
            oObjRight = parseInt(this.oObj.style.left) + this.width
        };
        if (mousX < 1 || mousY < 1 || mousX > document.body.clientWidth || mousY > document.body.clientHeight || parseInt(this.oObj.style.left) < 0 || parseInt(this.oObj.style.top) < 0 || oObjRight > document.body.clientWidth || oObjBottom > document.body.clientHeight) {
            this.oObj.style.left = this.backData["x"];
            this.oObj.style.top = this.backData["y"];
            if (this.showShadow) {
                G('dialogBoxShadow').style.left = this.backData.x + this.shadowWidth;
                G('dialogBoxShadow').style.top = this.backData.y + this.shadowWidth
            }
        };
        this.IsDraging = false;
        document.body.style.cursor = "";
        Event.stopObserving(document, "selectstart", this.returnFalse, false)
    },
    returnFalse: function() {
        return false
    }
};
function openshow(url, title, w, h, stype) {
    g_pop = new Popup({
        contentType: stype,
        isReloadOnClose: false,
        width: w,
        height: h
    });
    g_pop.setContent("title", title);
    g_pop.setContent("contentUrl", url);
    g_pop.build();
    g_pop.show()
}
function g_close_pop_re() {
    g_pop.close();
    location.reload()
}
function Comment(url, w, h) {
    g_pop = new Popup({
        contentType: 1,
        isReloadOnClose: false,
        width: w,
        height: h
    });
    g_pop.setContent("title", "登陆");
    g_pop.setContent("scrollType", "no");
    g_pop.setContent("contentUrl", url);
    g_pop.build();
    g_pop.show()
}
function g_close_pop() {
    g_pop.close();
    g_pop = null
}

/*
function ShowAlert(title, content, w, h) {
    var pop = new Popup({
        contentType: 4,
        isReloadOnClose: false,
        width: w,
        height: h
    });
    pop.setContent("title", title);
    pop.setContent("alertCon", content);
    pop.build();
    pop.show()
}
function Wclose() {
    g_pop.close();
    g_pop = null
} //通过popup.js打开模态对话框
var popp;
function ShowIframe(url, w, h, t) //显示有滚动条的iframe
{
    var pop = new Popup({
        contentType: 1,
        scrollType: 'yes',
        isReloadOnClose: false,
        width: w,
        height: h
    });
    pop.setContent("contentUrl", url);
    pop.setContent("title", t);
    pop.build();
    pop.show();
    popp = pop;
}

function ShowIframeNoScroll(url, w, h, t) //显示没有滚动条的iframe
{
    var pop = new Popup({
        contentType: 1,
        scrollType: 'no',
        isReloadOnClose: false,
        width: w,
        height: h
    });
    pop.setContent("contentUrl", url);
    pop.setContent("title", t);
    pop.build();
    pop.show();
    popp = pop;
}


function ShowAlertAndRedirect(title, content, target) //显示警示对话框
{
    var pop = new Popup({
        contentType: 4,
        isReloadOnClose: false,
        width: 350,
        height: 110
    });
    pop.setContent("title", title);
    pop.setContent("alertCon", content);
    pop.setContent("callBack", Redirect); //回调函数
    pop.setContent("parameter", {
        str: target,
        obj: pop
    });
    pop.build();
    pop.show();
    popp = pop;
    return false;
}

function ShowConfirm(title, content, callBack)//显示确认对话框
{
    var pop = new Popup({
        contentType : 3,
        isReloadOnClose : false,
        width : 400,
        height : 200
    });
    pop.setContent("title", title);
    pop.setContent("confirmCon", content);
    pop.setContent("callBack", callBack);
    //回调函数
    
    //    pop.setContent("para3eter", {
    //        id: "divCall",
    //        str: target,
    //        obj: pop
    //    });
    pop.build();
    pop.show();
    popp = pop;
    return false;
}

function ShowConfirmAndRedirect(title, content, url)//显示确认对话框
{
    var pop = new Popup({
        contentType : 3,
        isReloadOnClose : false,
        width : 350,
        height : 110
    });
    pop.setContent("title", title);
    pop.setContent("confirmCon", content);
    pop.setContent("callBack", Redirect);
    //回调函数
    pop.setContent("parameter", 
    {
        id : "divCall",
        str : url,
        obj : pop
    });
    
    pop.build();
    pop.show();
    popp = pop;
    return false;
    
}

function ShowHtmlString(strHtml,title){
    var pop = new Popup({
        contentType : 2,
        isReloadOnClose : false,
        width : 340,
        height : 300
    });
    pop.setContent("contentHtml", strHtml);
    pop.setContent("title", title);
    pop.build();
    pop.show();
    popp = pop;
    return false;
}


function ShowCallBackServer(para){
    var str = para["str"];
    if("" != str && null != str){
        str = GetEachBtnName(str);
        
        if("" != str && null != str){
            //alert(str);
            __doPostBack(str, '');
        }
    }
    ClosePop();
}

function Redirect(para){
    var str = para["str"];
    top.location = str;
}

function ShowCallBackClient(){
    var id = arguments[0];
    var val = arguments[1];
    document.getElementById(id).value = val;
}



function ShowCallBackServerIFrame(para){
    var str = para;
    if("" != str && null != str){
        str = GetEachBtnName(str);
        
        if("" != str && null != str){
            __doPostBack(str, '');
        }
    }
    closeWin();
}

function ShowIFrame(){
    parent.frames["content"].window.ShowCallBackServerIFrame(temp);
    //parent.window.iframe.ShowCallBackServer();
}

function ShowConfirmIFrame(title, content, callBack)//显示确认对话框
{
    var pop = new Popup({
        contentType : 3,
        isReloadOnClose : false,
        width : 350,
        height : 130
    });
    pop.setContent("title", title);
    pop.setContent("confirmCon", content);
    pop.setContent("callBack", callBack);
    //回调函数
    
    //    pop.setContent("parameter", {
    //        id: "divCall",
    //        str: target,
    //        obj: pop
    //    });
    //    temp = target;
    pop.build();
    pop.show();
    popp = pop;
    return false;
}

*/
function handle(callback){
    var tmp = callback;
    return tmp;
}

function gaga(id, val){
    document.getElementById(id).value = val;
    return true;
}

function GetEachBtnName(obj){
    return obj.name == '' || obj.name == null ? obj.id : obj.name;
}

//父页面关闭popup
function ClosePop(){
    popp.close();
}
//调用父页面关闭popup方法
function closeWin(){
    parent.ClosePop();
}

var temp;

//自定义扩展方法
function ShowDom(dom, title, width,height,isFullScreen) {
	if(isFullScreen==null){
		isFullScreen=true;
	}
    if (width == null) { width = 340 };
    if (height == null) { height = 300 };
    var pop = new Popup({
        contentType: 2,
        isReloadOnClose: false,
        width: width,
        height: height,
		isBackgroundCanClick:!isFullScreen
    });
    var tempdiv = document.createElement("div");
    tempdiv.appendChild(dom);
    var strHtml = tempdiv.innerHTML;
    pop.setContent("contentHtml", strHtml);
    pop.setContent("title", title);
    pop.build();
    pop.show();
    popp = pop;
    return false;
}

function ShowAlert(content,width,height,timeout,callback){
    if (width == null) { width = 200 };
    if (height == null) { height = 70 };
	var div=document.createElement("div");
	
	div.innerHTML="<h3 style='color:red;text-align:center'>"+content+"</h3>";
	ShowDom(div, "警告", width,height,true);
	if(timeout){
		setTimeout(function(){
			if(callback){
				callback();
			}
			CloseDialog();
		},timeout);
	}
	
}

function ShowMessage(content,width,height,timeout,callback){
	if (width == null) { width = 200 };
    if (height == null) { height = 70 };
	var div=document.createElement("div");
	
	div.innerHTML="<h3 style='color:Green;text-align:center'>"+content+"</h3>";
	ShowDom(div, "消息", width,height,true);
	if(timeout){
		setTimeout(function(){
			if(callback){
				callback();
			}
			CloseDialog();
		},timeout);
	}
}

function ShowConfirm(content,title,onOK,width,height){
	if (width == null) { width = 200 };
    if (height == null) { height = 70 };
	
	var div=document.createElement("div");
	
	var div_message=document.createElement("div");
	div_message.innerHTML="<h3 style='color:green;text-align:center'>"+content+"</h3>";
	
	var div_bt=document.createElement("div");
	div_bt.style.textAlign="right";
	
	var OK=document.createElement("input");
	OK.id="popupOK";
	OK.setAttribute("class","button");
	OK.type="button";
	OK.value="确定";
	//OK.class="button";
	var NO=document.createElement("input");
	NO.setAttribute("class","button");
	NO.id="popupNO";
	NO.type="button";
	NO.value="取消";
	//NO.class="button";
	
	
	
	div_bt.appendChild(OK);
	div_bt.appendChild(NO);
	
	div.appendChild(div_message);
	div.appendChild(div_bt);
	 
	
	ShowDom(div, "警告", width,height,true);
	document.getElementById("popupNO").onclick=function(){
		CloseDialog();
	}
	document.getElementById("popupOK").onclick=onOK;
}

function CloseDialog() {
    popp.close();
}