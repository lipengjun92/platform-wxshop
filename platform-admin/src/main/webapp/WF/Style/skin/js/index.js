$(function(){
    //菜单点击
    $(".J_menuItem").on('click',function(){
        var url = $(this).attr('href');
        $("#J_iframe").attr('src',url);
        // var this_length = this.parentNode.parentNode.children.length;
        // alert(this.parentNode.parentNode.children.length);
        /*for(var i=0;i<this_length;i++){
            this.parentNode.parentNode.children[i].children[0].style="";
        }*/
        //for(var i=0;i<$("#side-menu a").length;i++){
        //    $("#side-menu a")[i].style="";
        //}

        //this.style.backgroundColor="#1d7dd4";
        //this.style.fontWeight="bold";
        //this.style.color="#fff";
        return false;
    });
});