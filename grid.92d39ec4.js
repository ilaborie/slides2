parcelRequire=function(e,r,t,n){var i,o="function"==typeof parcelRequire&&parcelRequire,u="function"==typeof require&&require;function f(t,n){if(!r[t]){if(!e[t]){var i="function"==typeof parcelRequire&&parcelRequire;if(!n&&i)return i(t,!0);if(o)return o(t,!0);if(u&&"string"==typeof t)return u(t);var c=new Error("Cannot find module '"+t+"'");throw c.code="MODULE_NOT_FOUND",c}p.resolve=function(r){return e[t][1][r]||r},p.cache={};var l=r[t]=new f.Module(t);e[t][0].call(l.exports,p,l,l.exports,this)}return r[t].exports;function p(e){return f(p.resolve(e))}}f.isParcelRequire=!0,f.Module=function(e){this.id=e,this.bundle=f,this.exports={}},f.modules=e,f.cache=r,f.parent=o,f.register=function(r,t){e[r]=[function(e,r){r.exports=t},{}]};for(var c=0;c<t.length;c++)try{f(t[c])}catch(e){i||(i=e)}if(t.length){var l=f(t[t.length-1]);"object"==typeof exports&&"undefined"!=typeof module?module.exports=l:"function"==typeof define&&define.amd?define(function(){return l}):n&&(this[n]=l)}if(parcelRequire=f,i)throw i;return f}({"dTpH":[function(require,module,exports) {
"use strict";Object.defineProperty(exports,"__esModule",{value:!0}),exports.defer=o,exports.navigateTo=exports.keymap=void 0;var e=function(e){document.addEventListener("keydown",function(t){if("textarea"!==t.target.type){var o=t.code;e[o]&&(e[o](t),t.stopPropagation())}})};exports.keymap=e;var t=function(e){var t=document.querySelector(e);t&&t.click()};function o(){var e=arguments.length>0&&void 0!==arguments[0]?arguments[0]:0;return function(t){return setTimeout(t,e)}}exports.navigateTo=t;
},{}],"oaQ/":[function(require,module,exports) {
"use strict";var e=require("./slide"),c=document.getElementById("tocGrid");document.querySelector("label[for=tocGrid]").style.display="inline-block";var t=function(t){return function(){return c&&c.checked&&(c.checked=!1,(0,e.navigateTo)("a[href='#".concat(t,"']")),document.getElementById(t).scrollIntoView()),!1}};document.querySelectorAll("main > section").forEach(function(e){return e.addEventListener("click",t(e.id))}),(0,e.keymap)({Escape:function(){var e=document.getElementById("tocGrid");e&&(e.checked=!e.checked)}});
},{"./slide":"dTpH"}]},{},["oaQ/"], null)