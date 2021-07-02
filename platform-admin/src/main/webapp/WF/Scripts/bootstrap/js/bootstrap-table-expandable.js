(function ($) {
    $(function () {
        $('.table-expandable').each(function () {
            var table = $(this);
            table.children('thead').children('tr').append('<th></th>');
            table.children('tbody').children('tr').filter(':odd').hide();
            table.children('tbody').children('tr').filter(':even').click(function () {
                var element = $(this);
                element.next('tr').toggle('slow');
                element.next('tr').css('background-color', '#F5F5F5');
                element.find(".table-expandable-arrow").toggleClass("up");
                element.toggleClass("checked");
            });
            table.children('tbody').children('tr').filter(':even').each(function () {
                var element = $(this);
                var element_Id = element.attr('id');
                if (element_Id == "dtl_Sum") {
                    //element.css('background-color', '#F5F5F5');
                } else {
                    element.prepend('<td><div class="table-expandable-arrow"></div></td>');
                }
            });
        });
    });
})(jQuery); 