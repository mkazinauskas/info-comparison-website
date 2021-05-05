$(document).ready(function () {
    console.log("document loaded");
    $('#firstItem').keyup(function (event) {
        suggest(event.target, $('#firstItemSuggestions'));
    });

    $('#secondItem').keyup(function () {
        suggest(event.target, $('#secondItemSuggestions'));
    });

    $('#search-items').keyup(function () {
        suggest(event.target, $('#search-items-suggestions'));
    });

    function suggest(queryElementName, targetElement) {
        var enteredData = $(queryElementName).val();
        if (enteredData && enteredData.length >= 3) {
            $.getJSON(encodeURI('/suggest/?name=' + enteredData), function (data) {
                if (data._embedded && data._embedded.itemBeanList) {
                    var items = [];
                    $.each(data._embedded.itemBeanList, function (key, val) {
                        items.push('<option value="' + val.name + '">' + val.name + '</option>');
                    });
                    targetElement.empty();
                    targetElement.append(items.join(""));
                    reassignSubmit();
                }
            });
        }
    }

    function reassignSubmit() {
        $('#search-items').on('input', function () {
            var text = this.value;
            if ($('#search-items-suggestions')
                    .find('option')
                    .filter(function () {
                        return this.value.toUpperCase() === text.toUpperCase();
                    }).length) {
                $('#search-form').submit();
            }
        });
    }
});