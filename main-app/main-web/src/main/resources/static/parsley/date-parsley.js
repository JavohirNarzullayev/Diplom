/**
 * Created by sirnornur on 15.09.17.
 */
function parseDate(input, format) {
    format = format || 'dd.mm.yyyy'; // some default format
    var parts = input.match(/(\d+)/g),
        i = 0,
        fmt = {};
    // extract date-part indexes from the format
    format.replace(/(yyyy|dd|mm)/g, function(part) { fmt[part] = i++; });
    return new Date(parts[fmt['yyyy']], parts[fmt['mm']]-1, parts[fmt['dd']]);
}

window.ParsleyValidator.addValidator('mindate', {
    requirementType: 'string',
    validateString: function (value, requirement, obj) {
        var reqParts = requirement.split('|');
        var format = $(obj.$element[0]).attr('data-date-format');
        if (format == undefined) format = 'dd.mm.yyyy';
        if (reqParts.length > 1) {
            format = reqParts[1];
        }


        // is valid date?
        var timestamp = parseDate(value, format),
            minTs = parseDate(requirement, format);

        return isNaN(timestamp) ? false : timestamp > minTs;
    },
    messages: {
        'en': 'This date should be greater than %s.',
        'ru': 'Число должно быть позднее %s.',
        'uz': '%s dan keyingi sana kiritilishi kerak.'
    }
});

window.ParsleyValidator.addValidator('maxdate', {
    requirementType: 'string',
    validateString: function (value, requirement, obj) {
        var format = $(obj.$element[0]).attr('data-date-format');
        if (format == undefined) format = 'dd.mm.yyyy';

        // is valid date?
        var timestamp = parseDate(value, format),
            minTs = parseDate(requirement, format);

        return isNaN(timestamp) ? false : timestamp < minTs;
    },
    messages: {
        'en': 'This date should be less than %s.',
        'ru': 'Число должно быть ранее %s.',
        'uz': '%s dan oldingi sana kiritilishi kerak.'
    }
});