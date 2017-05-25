// Show the document's title on the status bar
window.defaultStatus=document.title;

var isDebug = false;

function log(message) {
  if (isDebug) {
      if (!log.window_ || log.window_.closed) {
          var win = window.open("JavaScriptDebug", null, "width=400,height=200," +
                                "scrollbars=yes,resizable=yes,status=no," +
                                "location=no,menubar=no,toolbar=no");
          if (!win){ return;}
          var doc = win.document;
          doc.write("<html><head><title>JavaScript Debug Log</title></head>" +
                    "<body></body></html>");
          doc.close();
          log.window_ = win;
      }
      var logLine = log.window_.document.createElement("div");
      var now = new Date();
      message = "[" + now.getHours() + ":" + now.getMinutes()  + ":" + now.getSeconds() + "]:" + message;
      logLine.appendChild(log.window_.document.createTextNode(message));
      log.window_.document.body.appendChild(logLine);
    }
}


/* This function is used to change the style class of an element */
function swapClass(obj, newStyle) {
   obj.className = newStyle;
}

function checkAll(theForm) { // check all the checkboxes in the list
  for (var i=0;i<theForm.elements.length;i++) {
    var el = theForm.elements[i];
    var eName = el.name;
      if (eName != 'allbox' &&(el.type.indexOf("checkbox") == 0)) {
          el.checked = theForm.allbox.checked;
          var cssName = "checked";
          if(el.checked){
              $(el).parent().addClass(cssName);
          } else{
        	  $(el).parent().removeClass(cssName);
          }
      }
  }
}

//for group checkbox.
//zhj 
function isCheckedByName(form,name) {
    var els=form.elements;
  var isChecked =false;
  for(var i=0;i<els.length;i++){
    if(els[i].name==name && els[i].checked){
       isChecked =true;
       break;
    }
  }
  return isChecked;
}
//for group checkbox.
//zhj
function getCheckedBoxByName(form,name) {
    var rtnArray = new Array();
    var els=form.elements;
  for(var i=0;i<els.length;i++){
    if(els[i].name==name && els[i].checked){
       rtnArray.push(els[i].value);
    }
  }
  return rtnArray;
}
function setDisableGroupElsByName(form,name,disabledV) {
    var els=form.elements;
  for(var i=0;i<els.length;i++){
    if(els[i].name==name){
       els[i].disabled =disabledV;
    }
  }
}
function isChinese(value) {
    if (value.length == 0) {
      return false;
    }
    for(n=0; n < value.length; n++) {
        var c=value.charCodeAt(n);
        if (c>127) {
          return true;
        }
    }
  return false;
}
/* This function is used to select a checkbox by passing
 * in the checkbox id
 */
function toggleChoice(elementId) {
    var element = document.getElementById(elementId);
    if (element.checked) {
        element.checked = false;
    } else {
        element.checked = true;
    }
}

/* This function is used to select a radio button by passing
 * in the radio button id and index you want to select
 */
function toggleRadio(elementId, index) {
    var element = document.getElementsByName(elementId)[index];
    element.checked = true;
}

/* This function is used to open a pop-up window */
function openWindow(url, winTitle, winParams) {
  winName = window.open(url, winTitle, winParams);
    winName.focus();
}


/* This function is to open search results in a pop-up window */
function openSearch(url, winTitle) {
    var screenWidth = parseInt(screen.availWidth);
    var screenHeight = parseInt(screen.availHeight);

    var winParams = "width=" + screenWidth + ",height=" + screenHeight;
        winParams += ",left=0,top=0,toolbar,scrollbars,resizable,status=yes";

    openWindow(url, winTitle, winParams);
}

/* This function is used to set cookies */
function setCookie(name,value,path,expires,domain,secure) {
  document.cookie = name + "=" + escape (value) +
    ((expires) ? "; expires=" + expires.toGMTString() : "") +
    ((path) ? "; path=" + path : "") +
    ((domain) ? "; domain=" + domain : "") + ((secure) ? "; secure" : "");
}

/* This function is used to get cookies */
function getCookie(name) {
  var prefix = name + "="
  var start = document.cookie.indexOf(prefix)

  if (start==-1) {
    return null;
  }

  var end = document.cookie.indexOf(";", start+prefix.length)
  if (end==-1) {
    end=document.cookie.length;
  }

  var value=document.cookie.substring(start+prefix.length, end)
  return unescape(value);
}

/* This function is used to delete cookies */
function deleteCookie(name,path,domain) {
  if (getCookie(name)) {
    document.cookie = name + "=" +
      ((path) ? "; path=" + path : "") +
      ((domain) ? "; domain=" + domain : "") +
      "; expires=Thu, 01-Jan-70 00:00:01 GMT";
  }
}

/* This function is a generic function to create form elements */
function createFormElement(element, type, name, id, value, parent) {
    var e = document.createElement(element);
    e.setAttribute("name", name);
    e.setAttribute("type", type);
    e.setAttribute("id", id);
    e.setAttribute("value", value);
    parent.appendChild(e);
}

/**
 *  same as TAS search in standard GUI.
 */
function toggleSearchCrit() {

    var searchTab = document.getElementById("searchCritTab");
    var noSearchBtns = document.getElementById("noSearchBtns");
    var searchCritOnArr = document.getElementsByName("searchCritOn");

    if (searchCritOnArr[0].value == "false") {
        searchTab.style.display = "";
        if(noSearchBtns){
           noSearchBtns.style.display = "none";
        }
        for (i = 0 ; i < searchCritOnArr.length ; i++) {
            searchCritOnArr[i].value = "true";
        }
    } else {
        searchTab.style.display = "none";
        if(noSearchBtns){
           noSearchBtns.style.display = "block";
        }
        for (i = 0 ; i < searchCritOnArr.length ; i++) {
            searchCritOnArr[i].value = "false";
        }
    }
}

/**
 * used in menuV2.tag. it should be removed in furture.
 */
function toggleMenu(subMenuId) {
    var subMenu = document.getElementById(subMenuId);
    if (subMenu && (subMenu.style.display == "" || subMenu.style.display == "block")) {
        subMenu.style.display = "none";
    } else if (subMenu && subMenu.style.display == "none") {
        subMenu.style.display = "block";
    }
}

/**
 * toggle object display property.
 */
function toggleObj(objId)  {
    var obj = document.getElementById(objId);
    if (obj && (obj.style.display == "" || obj.style.display == "block")) {
        obj.style.display = "none";
    } else if (obj && obj.style.display == "none") {
        obj.style.display = "block";
    }
}

/* Function to clear form's values to empty */
function clearFormFields(frmObj) {
    restoreAllFormElements(originalStyleOfFormEls);
  for (var i = 0; i < frmObj.length; i++) {
        var element = frmObj.elements[i];
        if (element.type.indexOf("text") == 0 ||
            element.type.indexOf("textarea") == 0 ||
            element.type.indexOf("select") == 0 ||
            element.type.indexOf("password") == 0) {
            element.value = "";
    } else if (element.type.indexOf("checkbox") == 0 ||
      element.type.indexOf("radio") == 0) {
      element.checked = false;
    }
    }
}
function trimAllPageElements(){
   //inputs
   trimAllPageElementsByTagName("input");
   //textareas
   trimAllPageElementsByTagName("textarea");
}
function trimAllPageElementsByTagName(tagName){
   if(tagName==null || tagName==undefined){
      return;
   }
   var els = document.getElementsByTagName(tagName);
   for(var i=0; i<els.length; i++){
        handleSingleElement(els[i]);
   }
}
function handleSingleElement(element){
    if(element==null || element==undefined){
       return;
    }
    if (element.type.indexOf("text") == 0 ||
            element.type.indexOf("textarea") == 0 ||
            element.type.indexOf("hidden") == 0) {
              element.value = trim(element.value);
        if(element.className=="inputtext"){
           element.value = element.value.toUpperCase();
        }
    }
    if (element.type.indexOf("password") == 0) {
        element.value = trim(element.value);
  } 
}
/* Function to get a form's values in a string */
function trimFormFields(frmObj) {
    if(frmObj==null || frmObj==undefined){
       return;
    }
  for (var i = 0; i < frmObj.elements.length; i++) {
        handleSingleElement(frmObj.elements[i]);  
    }
}

// This function is for stripping leading and trailing spaces
function trim(str) {
    if (str != null) {
        var i;
        for (i=0; i<str.length; i++) {
            if (str.charAt(i)!=" ") {
                str=str.substring(i,str.length);
                break;
            }
        }

        for (i=str.length-1; i>=0; i--) {
            if (str.charAt(i)!=" ") {
                str=str.substring(0,i+1);
                break;
            }
        }

        if (str.charAt(0)==" ") {
            return "";
        } else {
            return str;
        }
    }
}
function checkSingleElement(element){
    if(element==null || element==undefined){
       return false;
    }
    //check special chars
    var chars = element.value;

    if(chars.indexOf("'")>-1){
         return false;
    }
    return true;    	
}
function checkFormFields(form){
    if(form==null || form==undefined){
       return false;
    }
    trimFormFields(form);
    var rtnValue = true;
    var handleFieldsErrorMsg={};
    for (var i = 0; i < form.elements.length; i++) {
       var el = form.elements[i];
       
       if(!checkSingleElement(el)){
          //handleFieldsErrorMsg.put(el.name,["Invalid inputs! Now system CAN NOT accept ' < > chars."]);       
          rtnValue = false;
       }
    }
    //handleFieldsErrorMsg(form,handleFieldsErrorMsg);
    if(!rtnValue){ 
       alert("Invalid inputs! Now system CAN NOT accept ' char.");
    }
    return rtnValue;
}
/* check digit string */
function isDigit(digStr, fCanEmpty) {
    if (fCanEmpty == null || fCanEmpty==undefined) fCanEmpty = true;
    if (fCanEmpty == true && digStr.length == 0) return true;
    if (digStr.length > 0 && digStr.charAt(0) == '-') {
       digStr = digStr.substr(1);
    }
    var result = digStr.match(/[^0-9]/g);
    if (result || !digStr) return false;
    return true;
}

function isFloat(floatStr, fCanEmpty) {
    if (fCanEmpty == null || fCanEmpty==undefined) fCanEmpty = true;
    if (fCanEmpty == true && floatStr.length == 0) return true;
    if (floatStr.length > 0 && floatStr.charAt(0) == '-') {
       floatStr = floatStr.substr(1);
    }
    var floatPatten = /^[0-9]+(.[0-9]{1,2})?$/;
    if (floatPatten.test(floatStr)) {
        return true;
    }
    return false;
}


/**
 *   Returns true if value is null
 */
function isNull(val) {
  return val==null;
}

/**
 * Returns true if the object is an array, else false
 */
function isArray(obj){
  return (typeof(obj.length)=="undefined")?false:true;
}

/**
 *   Sets a form field to "" if it isBlank()
 */
function setNullIfBlank(obj){
  if (isBlank(obj.value)) {
    obj.value="";
  }
}


/**
 *  Returns true if element's value only contains spaces
 */
function isBlank(element){
  val = element.value;
  if(val == null){
    return true;
  }
  val = trim(val);
  element.value = val;
  for(var i=0;i<val.length;i++) {
    if ((val.charAt(i)!=' ')&&(val.charAt(i)!="\t")&&(val.charAt(i)!="\n")&&(val.charAt(i)!="\r")){
      return false;
    }
  }
  return true;
}


/**
 * Check undefined object
 */
function isUndefined(object) {
    return typeof object == "undefined";
}

/**
 * Get element by Id
 */
function getEl(id) {
  return document.getElementById(id);
}

/**
 * return string bytes count
 */
function getStringLengthByBytes(str) {
    value = trim(str);
    if (value.length == 0) {
      return 0;
    }
    intLength = 0;
    for(n=0; n < value.length; n++) {
        var c=value.charCodeAt(n);
        if (c<128) { // 0-127 => 1byte
            intLength += 1;
        } else if((c>127) && (c<2048)) { // 127 -  2047 => 2byte
            intLength += 2;
        } else { // 2048 - 66536 => 3byte
            intLength += 3;
        }
    }
    return intLength;
}

/**
 * return left string bytes
 */
function leftStringByBytes(str, len) {
    value = trim(str);
  var valueLength = getStringLengthByBytes(value);

  if (valueLength < len || len < 0) {
    return value;
  }
  intLength = 0;
  var rtnString = "";
    for(n=0; (n < valueLength && intLength < len); n++) {
        var c=value.charCodeAt(n);
        rtnString += value.charAt(n);
        if (c<128) { // 0-127 => 1byte
            intLength += 1;
        } else if((c>127) && (c<2048)) { // 127 -  2047 => 2byte
            intLength += 2;
        } else { // 2048 - 66536 => 3byte
            intLength += 3;
        }
    }
  return rtnString;
}

/**
 * Focus form element
 */
function focusElement(element) {
  if (isArray(element) && (typeof(element.type)=="undefined")) {
    element=element[0];
  }
  element.focus();
}

/**
 * check English and number. It is for isMaxWordLength.
 */
function isEngDigit(c) {
  if ( (c >= 0x30 && c <= 0x39) || (c >= 0x41 && c <= 0x5a) || (c >= 0x61 && c<= 0x7a)) {
    return true
  } else {
    return false;
  }
}

/**
 * check textArea maxlength. example:
 * <textarea maxWordLength="30" onkeyup="return isMaxWordLength(this, alertMsg)"
 * cols="32" rows="5" name="logNotes" id="logNotes">
 */
function isMaxWordLength(obj, alertMsg) {
  var maxWordLen=obj.getAttribute? parseInt(obj.getAttribute("maxWordLength")) : ""
  var value = obj.value;
  var wordBInx = -1;
  var wordEInx = -1;
  var errs = new Array();
  for ( var i = 0; i < value.length; i++) {
    if (isEngDigit(value.charCodeAt(i))) {
      wordEInx = i;
    } else {
      //alert("wordBInx=" + wordBInx + " wordEInx=" + wordEInx + " got word: '" + value.substr(wordBInx + 1, wordEInx - wordBInx) + "'");
      if (wordEInx - wordBInx > maxWordLen) {
        errs.push(value.substr(wordBInx + 1, wordEInx - wordBInx));
        //alert("got err: " + err);
      }
      wordBInx = i;
    }
  }
  if (wordEInx - wordBInx > maxWordLen) {
    errs.push(value.substr(wordBInx + 1, wordEInx - wordBInx));
    //alert("got err: " + err);
  }
  if (errs.length > 0) {
    alertMsg += ":\n" + errs.join("\n");
    alert(alertMsg);
    obj.focus();
    return false;
  }
  return true;
}

/**
 * check textArea maxlength. example:
 * <textarea maxlength="1500" onkeyup="return ismaxlength(this, alertmsg)"
 * cols="32" rows="5" name="logNotes" id="logNotes">
 */
function ismaxlength(obj, alertMsg){
  var mlength=obj.getAttribute? parseInt(obj.getAttribute("maxlength")) : "";
  var objValue = obj.value;
  if (obj.getAttribute && objValue.length>mlength) {
    alert(alertMsg)
    obj.value=objValue.substring(0, mlength);
    obj.focus()
  }
}

/**
 * check textArea maxlength by byte. example:
 * <textarea maxlength="1500" onkeyup="return ismaxlengthByBytes(this, alertMsg)"
 * cols="32" rows="5" name="logNotes" id="logNotes">
 */
function ismaxlengthByBytes(obj, alertMsg){
  var mlength=obj.getAttribute? parseInt(obj.getAttribute("maxlength")) : ""
  if (obj.getAttribute && getStringLengthByBytes(obj.value)>mlength) {
    alert(alertMsg)
    obj.value=leftStringByBytes(obj.value,mlength)
    obj.focus()
  }
}

/**
 * Get event in IE and fireforx
 */
function getEvent(){
   if(document.all)    return window.event;
   func=getEvent.caller;
   while(func!=null){
       var arg0=func.arguments[0];
      if(arg0){
           if((arg0.constructor==Event || arg0.constructor ==MouseEvent)
               || (typeof(arg0)=="object" && arg0.preventDefault && arg0.stopPropagation)){
               return arg0;
           }
       }
       func=func.caller;
   }
   return null;
}
/**
 * check option
 */
function hasOptions(obj) {
  return (obj!=null && typeof(obj.options)!="undefined" && obj.options!=null);
}
//contains one option
function isContainedValueInSelect(select, value) {
    var len=select.length;
    var rtnResult = false;
    for(var i=0;i<len;i++){
       if (select.options[i].value==value) {
          rtnResult = true;
          break;
       }
    }
    return rtnResult;
}
/**
 * Add option to SELECT object
 */
function optionAddChildNode(obj, value, display) {
  //duplicate
    var len=obj.length;
    for(var i=0;i<len;i++)
    {
       if (obj.options[i].value==value) {
          return;
       }
    }

  //add
  obj.options.add(new Option(display, value))
}

/**
 * Remove selected options from SELECT object
 */
function optionRemoveChildNodes(obj)
{
    var len=obj.length;
    for(var i=len-1;i>=0;i--)
    {
        if(obj.options[i].selected)
        {
            obj.remove(i);
        }
    }
}

/**
 * Select all options on SELECT object
 */
function optionSelectByValue(obj,expectedValue)
{
    var len=obj.length;
    var opts = obj.options;
    for(var i=0;i<len;i++){
    	//alert(opts[i].value);
    	if(opts[i].value==expectedValue){
    		opts[i].selected=true;
    	}        
    }
}

/**
 * Select all options on SELECT object
 */
function optionSelectAll(obj)
{
    var len=obj.length;
    for(var i=0;i<len;i++)
    {
        obj.options[i].selected=true;
    }
}

/**
 * Unselect all options on SELECT object
 */
function optionUnSelectAll(obj)
{
    var len=obj.length;
    for(var i=0;i<len;i++)
    {
        obj.options[i].selected=false;
    }
}

/**
 * Remove all options from SELECT object
 */
function optionClearListObj(obj)
{
    var len=obj.length;
    obj.length = 0;
    //for(var i=0;i<len;i++) obj.remove(0);
}

/**
 * Remove options from SELECT object when user click 'del' key. example:
 * <SELECT NAME="targetSelect" size="10" style="WIDTH: 12em" multiple
 * onkeydown="optionDelete(this)">
 */
function optionDelete(obj) {
  var ev = getEvent();
  if (ev.keyCode == 46) {
    optionRemoveChildNodes(obj);
    obj.focus();
  }
}

/**
 * Move all select options from source to target
 */
function optionMoveAll(source, target) {
  optionSelectAll(source);
  optionCopy(source, target);
  optionRemoveChildNodes(source);
}

/**
 * Move selected option from source to target
 */
function optionMove(source, target) {
  optionCopy(source, target);
  optionRemoveChildNodes(source);
}

/**
 * Copy selected options
 */
function optionCopy(source, target) {
  if (!hasOptions(source)) {
    return false;
  }
  var options = new Object();
  if (hasOptions(target)) {
    for (var i=0; i<target.options.length; i++) {
      options[target.options[i].value] = target.options[i].text;
    }
  }
  for (var i=0; i<source.options.length; i++) {
    var o = source.options[i];
    if (o.selected) {
      if (typeof(options[o.value])=="undefined" || options[o.value]==null || options[o.value]!=o.text) {
        var index = (!hasOptions(target))?0:target.options.length;
        target.options[index] = new Option( o.text, o.value, false, false);
      }
    }
  }
  return true;
}


/**
 * Sort options
 */
function sortOptions(obj) {
  var o = [];
  if (!hasOptions(obj)) { return false; }
  for (var i=0; i<obj.options.length; i++) {
    o[o.length] = new Option( obj.options[i].text, obj.options[i].value, obj.options[i].defaultSelected, obj.options[i].selected) ;
  }
  if (o.length==0) { return true; }
  o = o.sort(
    function(a,b) {
      if ((a.text+"") < (b.text+"")) { return -1; }
      if ((a.text+"") > (b.text+"")) { return 1; }
      return 0;
    }
  );

  for (var i=0; i<o.length; i++) {
    obj.options[i] = new Option(o[i].text, o[i].value, o[i].defaultSelected, o[i].selected);
  }
  return true;
}

/**
 * Swap options
 */
function swapOptions(obj,i,j) {
  if (!hasOptions(obj)) { return false; }
  var o = obj.options;
  if (i<0 || i>=o.length || j<0 || j>=o.length) { return false; }
  var i_selected = o[i].selected;
  var j_selected = o[j].selected;
  var temp = new Option(o[i].text, o[i].value, o[i].defaultSelected, o[i].selected);
  var temp2= new Option(o[j].text, o[j].value, o[j].defaultSelected, o[j].selected);
  o[i] = temp2;
  o[j] = temp;
  o[i].selected = j_selected;
  o[j].selected = i_selected;
  return true;
}

/**
 * Move first selected option to Top
 */
function moveOptionTop(obj) {
  if (!hasOptions(obj)) { return false; }
  var isAllatTop = false;
  moveOptionUp(obj);
  while(!isAllatTop) {
    isAllatTop = true;
    for (i=0; i<obj.options.length; i++) {
      if (obj.options[i].selected) {
        if (i>0 && !obj.options[i-1].selected) {
          isAllatTop = false;
          moveOptionTop(obj);
        }
      }
    }
  }
}

/**
 * Move first selected option to Bottom
 */
function moveOptionBottom(obj) {
  if (!hasOptions(obj)) { return false; }
  var isAllatBottom = false;
  moveOptionDown(obj);
  while(!isAllatBottom) {
    isAllatBottom = true;
    for (i=0; i<obj.options.length; i++) {
      if (obj.options[i].selected) {
        if (i != (obj.options.length-1) && ! obj.options[i+1].selected) {
          isAllatBottom = false;
          moveOptionDown(obj);
        }
      }
    }
  }
}

/**
 * move option up
 */
function moveOptionUp(obj) {
  if (!hasOptions(obj)) { return false; }
  for (i=0; i<obj.options.length; i++) {
    if (obj.options[i].selected) {
      if (i>0 && !obj.options[i-1].selected) {
        swapOptions(obj,i,i-1);
        obj.options[i-1].selected = true;
      }
    }
  }
  return true;
}

/**
 * move option down
 */
function moveOptionDown(obj) {
  if (!hasOptions(obj)) { return false; }
  for (i=obj.options.length-1; i>=0; i--) {
    if (obj.options[i].selected) {
      if (i != (obj.options.length-1) && ! obj.options[i+1].selected) {
        swapOptions(obj,i,i+1);
        obj.options[i+1].selected = true;
      }
    }
  }
  return true;
}

/*
 * example: callAjax({url:url,paras:parameters,async:false,cbMethod:callBackMethod});
 * attribute name must matching.
 */
function callAjax(obj){
   new Ajax.Request(
        obj.url,
        {
           method : obj.method||'post',
           onSuccess : obj.cbMethod,
           parameters : obj.paras||'',
           asynchronous : obj.async!=false,
           //time out after waiting 20s
           requestTimeout:20,
           onTimeout:function(){ alert('System Error! Request Timed Out!');},
           onFailure: function(){
              alert('Something went wrong...');
           }
        }
   );
}

/**
 * Check Email format. null will return true.
 */
function checkEmail(elm) {
  var emailStr;
    if (elm.value == null) { //it's just a variable
        elm = trim(elm);
        emailStr = elm;
    } else { //it's a control
        elm.value = trim(elm.value);
        emailStr = elm.value;
    }
   if (emailStr.length == 0) {
       return true;
   }
   var emailPat=/^(.+)@(.+)$/;
   var specialChars="\\(\\)<>@,;:\\\\\\\"\\.\\[\\]";
   var validChars="\[^\\s" + specialChars + "\]";
   var quotedUser="(\"[^\"]*\")";
   var ipDomainPat=/^(\d{1,3})[.](\d{1,3})[.](\d{1,3})[.](\d{1,3})$/;
   var atom=validChars + '+';
   var word="(" + atom + "|" + quotedUser + ")";
   var userPat=new RegExp("^" + word + "(\\." + word + ")*$");
   var domainPat=new RegExp("^" + atom + "(\\." + atom + ")*$");
   var matchArray=emailStr.match(emailPat);
   if (matchArray == null) {
       return false;
   }
   var user=matchArray[1];
   var domain=matchArray[2];
   if (user.match(userPat) == null) {
       return false;
   }
   var IPArray = domain.match(ipDomainPat);
   if (IPArray != null) {
       for (var i = 1; i <= 4; i++) {
          if (IPArray[i] > 255) {
             return false;
          }
       }
       return true;
   }
   var domainArray=domain.match(domainPat);
   if (domainArray == null) {
       return false;
   }
   var atomPat=new RegExp(atom,"g");
   var domArr=domain.match(atomPat);
   var len=domArr.length;
   if ((domArr[domArr.length-1].length < 2) ||
       (domArr[domArr.length-1].length > 3)) {
       return false;
   }
   if (len < 2) {
       return false;
   }
   return true;
}

/**
 * Plsase use this method intead of form.submit()
 */
function submitForm(form) {
  if (isUndefined(form.onsubmit)) {
    form.submit();
  }
  if(form.onsubmit() != false) {
    form.submit();
  }
}
/**
 * Check msisdn format for Taiwan
 */
function isTaiWanMsisdn(elm, canEmpty) {
  if (canEmpty == null) {
    canEmpty = true;
  }
  if (isBlank(elm)) {
    if (canEmpty) {
      return true;
    } else {
      return false;
    }
  }
  var msisdnPartten = /((^8869)|(^([+](8869)))|(^09)|(^9))\d{8}$/;
  return msisdnPartten.test(elm.value);
}

/**
 * Check msisdns format for Taiwan
 * separated by ,
 */
function isTaiWanMsisdnValues(elmValues) {
  var values = elmValues.split(",");
  var msisdnPartten = /((^8869)|(^([+](8869)))|(^09)|(^9))\d{8}$/;
    var vv;
    for(var i=0;i<values.length;i++){
       vv = values[i];
       if(!msisdnPartten.test(vv)){
          return false;
       }
    }
    return true;
}
/**
 * Check ip address format
 */
function checkIP(elm, canEmpty) {
    if (canEmpty == null) canEmpty = true;
    if (canEmpty == true && isBlank(elm)) return true;
    ipPartten = /^\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}$/;
    ipText = elm.value;
    if (ipPartten.test(ipText)) {
      var values = ipText.split(".");
      for (var i = 0; i < values.length; i++) {
          var chkee = new Number(values[i]);
          if (chkee <= 0 || chkee > 255) {
            return false;
          }
      }
      return true;
  }
  return false;
}

/**
 * Check ip address format
 */
function checkIPStr(ipAddress, canEmpty) {
    if (canEmpty == null) canEmpty = true;
    if (canEmpty == true && trim(ipAddress)<=0) return true;
    ipPartten = /^\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}$/;
    ipText = ipAddress;
    if (ipPartten.test(ipText)) {
      var values = ipText.split(".");
      for (var i = 0; i < values.length; i++) {
          var chkee = new Number(values[i]);
          if(i==0){
            if (chkee <= 0 || chkee > 255) {
              return false;
            }
          } else {
            if (chkee < 0 || chkee > 255) {
              return false;
            }
          }
      }
      return true;
  }
  return false;
}


//check http url
function checkValidUrl(strUrl) {
    var RegexUrl = /(http|https):\/\/(\w+:{0,1}\w*@)?(\S+)(:[0-9]+)?(\/|\/([\w#!:.?+=&%@!\-\/]))?/
    return RegexUrl.test(strUrl.toLowerCase());
}

//check ftp url
function checkValidFtpUrl(strFtpUrl) {
    var RegexUrl = /(ftp):\/\/(\w+:{0,1}\w*@)?(\S+)(:[0-9]+)?(\/|\/([\w#!:.?+=&%@!\-\/]))?/
    return RegexUrl.test(strFtpUrl.toLowerCase());
}

//this function should be depended on protype.js
//mz 2008/05/29, protype didn't deal with char-"
function unescapeHTMLExtProtype(str){
   if(str==null || str=='null' || str==undefined){
      return str;
   }
   var tempStr = str.unescapeHTML();
   if(tempStr.indexOf("&quot;")>=0){
       tempStr = tempStr.replace("&quot;","\"");
   }
   return tempStr;
}
//check file extension of file name
function checkExtensionOfFileName(fileName,extension){
  if(fileName==null || fileName==undefined || fileName ==""){
     return false;
  }
  if(extension==null || extension==undefined || extension ==""){
     return true;
  }
  var ext =fileName.substring(fileName.lastIndexOf(".")+1);
  ext = ext.toLowerCase();

  //check extension is array
  if(extension.length==undefined){
     return ext==extension.toLowerCase();
  } else {
     var len = extension.length;
     for(var i =0;i<len;i++){
        if(ext==extension[i].toLowerCase()){
           return true;
        }
     }
  }
  return false;
}

//the max length of phone num is 20
//and it can contain 0-9/*/#/+
function checkIfValidUSimPhoneNum(pbkStr,canEmpty){
    var strReg = /[^0-9\+\*#]/;
    if (canEmpty == null) {
    canEmpty = true;
  }
  if (isBlank(pbkStr)) {
    if (canEmpty) {
      return true;
    } else {
      return false;
    }
  }
    if (pbkStr.length>20) {
      return false;
    }
    if (strReg.test(pbkStr.value)) {//if contains invalid chars then return false
        return false
    }
    return true;
}
var originalStyleOfFormEls = {};
function saveOriginalStyleOfAllFormElements(originalStylesMap){
   //inputs
   saveBgColorToMapByTagName(originalStylesMap,"input");
   //selects
   saveBgColorToMapByTagName(originalStylesMap,"select");
   //textareas
   saveBgColorToMapByTagName(originalStylesMap,"textarea");

}
function saveBgColorToMapByTagName(originalStylesMap,tagName){
   if(originalStylesMap==null || originalStylesMap==undefined){
      return;
   }
   if(tagName==null || tagName==undefined){
      return;
   }
   var els = document.getElementsByTagName(tagName);
   for(var i=0; i<els.length; i++){
      var curEl = els[i];
      var curElName = curEl.name;
      originalStylesMap[curElName] = curEl.style.backgroundColor;
      if (curEl.type.indexOf("reset") == 0) {
        Event.observe(curEl, 'click', respondToResetClick);
    log("observe event for" + curEl.value);
      }
   }
}

function respondToResetClick(event) {
  log("Reset click");
  var element = Event.element(event);
  restoreAllFormElements(originalStyleOfFormEls);
}

function restoreAllFormElements(originalStylesMap){
   //inputs
   restoreBgColorFromMapByTagName(originalStylesMap,"input");
   //selects
   restoreBgColorFromMapByTagName(originalStylesMap,"select");
   //textareas
   restoreBgColorFromMapByTagName(originalStylesMap,"textarea");
}
function restoreBgColorFromMapByTagName(originalStylesMap,tagName){
   if(originalStylesMap==null || originalStylesMap==undefined){
      return;
   }
   if(tagName==null || tagName==undefined){
      return;
   }
   var els = document.getElementsByTagName(tagName);
   for(var i=0; i<els.length; i++){
      var curEl = els[i];
      var curElName = curEl.name;
      curEl.style.backgroundColor = originalStylesMap[curElName];
      curEl.title="";
   }
}
//invalidFields is a array
//contains form element
var errorBgColor = "yellow";
function dealWithHighLight(invalidFields){
   //restoreAllFormElements(originalStyleOfFormEls);
   if(invalidFields==null || invalidFields==undefined){
      return;
   }

   if(invalidFields.length==undefined){
      invalidFields.style.backgroundColor = errorBgColor;
      return;
   }
   var inValidLen = invalidFields.length;
   for(var i = 0; i < inValidLen; i++){
      invalidFields[i].style.backgroundColor = errorBgColor;
   }
}
//just for single form element.
//for those can not validate in 'function validateInput(form)'
function dealWithHighLight4SingleElement(elementNeedValidate,isValid){
   if(elementNeedValidate==null || elementNeedValidate==undefined){
      return;
   }
   if(isValid==null || isValid==undefined || isValid==false || isValid =='false'){
      elementNeedValidate.style.backgroundColor = errorBgColor;
      return;
   } else{
      var name = elementNeedValidate.name;
      elementNeedValidate.style.backgroundColor = originalStyleOfFormEls[name];
   }
}
//render error msg in element.title and set the backgroud color of the element with error color
function handleAjaxRespError(theForm,actionErrorMsgObj,fieldsErrorMsgObj){
   //need to restore the elements here
   //because maybe there is no any field error happened after resubmitting but meet actionError.
   restoreAllFormElements(originalStyleOfFormEls);   
   handleFieldsErrorMsg(theForm,fieldsErrorMsgObj);
   if(actionErrorMsgObj==null || actionErrorMsgObj==undefined){
      return;
   }
   //meet action error.
   alert(actionErrorMsgObj);
}
function handleFieldsErrorMsg(theForm,fieldsErrorMsgObj){
   if(fieldsErrorMsgObj==null || fieldsErrorMsgObj==undefined){
      return;
   }
  var invalidFields = new Array();
  for(var key in fieldsErrorMsgObj){
    var errorArr = fieldsErrorMsgObj[key];
    var errorStr = errorArr.join();
    
    for (var i=0;i<theForm.elements.length;i++) {
      var el = theForm.elements[i];
      var eName = el.name;
      var eId = el.id;
      //alert(eName);
      if (eId!=null && eId!=undefined && eId!="" && eId==key) {
          el.title=errorStr;
          invalidFields.push(el);
      } else if (eName==key) {
              el.title=errorStr;
              invalidFields.push(el);
        }
    }
  }
  dealWithHighLight(invalidFields);
}
//return the dimension of an element
function Dimension(element){
     this.x=-1;
     this.y=-1;
     this.w=0;
     this.h=0;
     if (element==document){
        this.x=element.body.scrollLeft;
        this.y=element.body.scrollTop;
        this.w=element.body.clientWidth;
        this.h=element.body.clientHeight;
     }
     else if (element!=null){
        var e=element;
        var left=e.offsetLeft;
        while ((e=e.offsetParent)!=null) {
            left+=e.offsetLeft;
        }
        var e=element;
        var top=e.offsetTop;
        while((e=e.offsetParent)!=null) {
            top+=e.offsetTop;
        }
        this.x=left;
        this.y=top;
        this.w=element.offsetWidth;
        this.h=element.offsetHeight;
    }
}
//set the browser button over to the eventBtn
//for forbid the user to input invalide path to the file textfield.
function fclick(eventBtn,browseBtnObj){
   var upBtElm = eventBtn;
   var uploadButtonLeftTopDim = new Dimension(upBtElm);
   with(browseBtnObj){
     style.top=uploadButtonLeftTopDim.y+"px";
     style.left=uploadButtonLeftTopDim.x+"px";
   }
}
function replaceAll(str,oldStr,reStr){
    return str.split(oldStr).join(reStr);
}

function displaytagform(formName, fields){
    var objfrm = document.forms[formName];
    for (j=fields.length-1;j>=0;j--){
    	var f= objfrm.elements[fields[j].f];
        if (f){
            f.value=fields[j].v;
        }
    }
    objfrm.submit();
}
//弹出层
function openLayerAdv(contentId) {
  var arrayPageSize = getPageSize();//调用getPageSize()函数
  var arrayPageScroll = getPageScroll();//调用getPageScroll()函数

//给这个元素设置属性与样式
  var popupDiv = document.getElementById(contentId);
  popupDiv.style.position = "absolute";
  popupDiv.style.border = "1px solid #ccc";
  popupDiv.style.background = "#fff";
  popupDiv.style.zIndex = 80000;

//让弹出层在页面中垂直左右居中(个性)
  var arrayConSize = getConSize(contentId);
  popupDiv.style.top = arrayPageScroll[1] + (arrayPageSize[3] - arrayConSize[1]) / 2 - 0 + "px";
  popupDiv.style.left = (arrayPageSize[0] - arrayConSize[0]) / 2 - 0 + "px";

  popupDiv.style.display = "block";
  
  //mask div
  
  var bodyBack = document.getElementById("loading-mask");

  //bodyBack.style.position = "absolute";
  bodyBack.style.width = document.body.clientWidth + "px";
  bodyBack.style.height = "100%";// (arrayPageSize[1] + 35 + 'px');
  bodyBack.style.zIndex = 98;
  bodyBack.style.top = 0;
  bodyBack.style.left = 0;
  bodyBack.style.filter = "alpha(opacity=30)";
  bodyBack.style.opacity = 0.3;
  bodyBack.style.background = "#000000";
  //显示背景层
  bodyBack.style.display = "block";
}

//弹出层
function openLayerAdv2(contentId) {
  var arrayPageSize = getPageSize();//调用getPageSize()函数
  var arrayPageScroll = getPageScroll();//调用getPageScroll()函数

//给这个元素设置属性与样式
  var popupDiv = document.getElementById(contentId);
  popupDiv.style.position = "absolute";
  popupDiv.style.border = "1px solid #ccc";
  popupDiv.style.background = "#fff";
  popupDiv.style.zIndex = 90001;

//让弹出层在页面中垂直左右居中(个性)
  var arrayConSize = getConSize(contentId);
  popupDiv.style.top = arrayPageScroll[1] + (arrayPageSize[3] - arrayConSize[1]) / 2 - 0 + "px";
  popupDiv.style.left = (arrayPageSize[0] - arrayConSize[0]) / 2 - 0 + "px";

  popupDiv.style.display = "block";
  
  //mask div
  
  var bodyBack = document.getElementById("loading-mask");

  //bodyBack.style.position = "absolute";
  bodyBack.style.width = document.body.clientWidth + "px";
  bodyBack.style.height = "100%";// (arrayPageSize[1] + 35 + 'px');
  bodyBack.style.zIndex = 90000;
  bodyBack.style.top = 0;
  bodyBack.style.left = 0;
  bodyBack.style.filter = "alpha(opacity=30)";
  bodyBack.style.opacity = 0.3;
  bodyBack.style.background = "#000000";
  //显示背景层
  bodyBack.style.display = "block";
}
function refreshMaskArea(){
	 var bodyBack = document.getElementById("loading-mask");
	 bodyBack.style.height =popupDiv.style.top + popupDiv.style.height +100 ;
}
//获取内容层内容原始尺寸
function getConSize(contentId) {
  var conObj = document.getElementById(contentId);
  conObj.style.position = "absolute";

  conObj.style.left = -1000 + "px";

  conObj.style.display = "";
  var arrayConSize = [conObj.offsetWidth, conObj.offsetHeight];
  conObj.style.display = "none";

  return arrayConSize;
}

//获取滚动条的高度
function getPageScroll() {
  var yScroll;
  if (self.pageYOffset) {
    yScroll = self.pageYOffset;
  } else {
    if (document.documentElement && document.documentElement.scrollTop) {
      yScroll = document.documentElement.scrollTop;
    } else {
      if (document.body) {
        yScroll = document.body.scrollTop;
      }
    }
  }
  arrayPageScroll = new Array("", yScroll);
  return arrayPageScroll;
}
//获取页面实际大小
function getPageSize() {
  var xScroll, yScroll;
  if (window.innerHeight && window.scrollMaxY) {
    xScroll = document.body.scrollWidth;
    yScroll = window.innerHeight + window.scrollMaxY;
  } else {
    if (document.body.scrollHeight > document.body.offsetHeight) {
      xScroll = document.body.scrollWidth;
      yScroll = document.body.scrollHeight;
    } else {
      xScroll = document.body.offsetWidth;
      yScroll = document.body.offsetHeight;
    }
  }
  var windowWidth, windowHeight;
//var pageHeight,pageWidth;
  if (self.innerHeight) {
    windowWidth = self.innerWidth;
    windowHeight = self.innerHeight;
  } else {
    if (document.documentElement && document.documentElement.clientHeight) {
      windowWidth = document.documentElement.clientWidth;
      windowHeight = document.documentElement.clientHeight;
    } else {
      if (document.body) {
        windowWidth = document.body.clientWidth;
        windowHeight = document.body.clientHeight;
      }
    }
  }
  var pageWidth, pageHeight;
  if (yScroll < windowHeight) {
    pageHeight = windowHeight;
  } else {
    pageHeight = yScroll;
  }
  if (xScroll < windowWidth) {
    pageWidth = windowWidth;
  } else {
    pageWidth = xScroll;
  }
  arrayPageSize = new Array(pageWidth, pageHeight, windowWidth, windowHeight);
  return arrayPageSize;
}
//关闭弹出层
function closeLayer(contentId) {
    document.getElementById(contentId).style.display = "none";
    document.getElementById("loading-mask").style.display = "none";
}

//验证输入长度是否达到指定长度
function checkLength(id,len) {
  var val = getEl(id).value;
  if(val.length==len) {
    return true;
  }
  return false;
}

//验证输入长度是否达到或者超过指定长度
function checkLength2(id,len) {
  var val = getEl(id).value;
  if(val.length==len||val.length>len) {
    return true;
  }
  return false;
}

//验证输入串最后一个字符是否为指定的mark
function checkEndBy(id,mark) {
  var val = getEl(id).value;
  var idx = val.lastIndexOf(mark);
  if (idx==val.length-1) {
    return true;
  }
  return false;
}
