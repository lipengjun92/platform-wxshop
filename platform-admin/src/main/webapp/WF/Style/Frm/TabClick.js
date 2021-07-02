var currentTabId = null;
function TabClick(id, url) {
    currentTabId = id;
    var iframesrc = document.getElementById('F' + id).src;
    if (iframesrc.indexOf('htm') > 0) {
        document.getElementById('F' + id).src = url;
    }
    //scop.innerHTML = scop.innerText + "*";
    return;
}

function OnTabChange(id, scope) {
    var tabText = document.getElementById('HL' + id).innerText;
    var lastChar = tabText.substring(tabText.length - 1, tabText.length);
    if (lastChar == "*") {
        var contentWidow = scope.contentWindow;
        contentWidow.SaveDtlData();
        document.getElementById('HL' + id).innerHTML = tabText.substring(0, tabText.length - 1);
    }
}