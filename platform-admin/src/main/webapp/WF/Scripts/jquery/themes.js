$(function () {
    var themeName = $.cookie("themeName");
    if (themeName == null || themeName == undefined)
    { $.easyui.theme("default"); }
    else {
        $.easyui.theme(themeName);
    } 
});