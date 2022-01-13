function renderLocalDateTime(date){
    try{
       return date[0]+"-"+date[1]+"-"+date[2]
    }catch (e){
        return ""
    }
}

function renderDateColumn(data) {
    if(data == null) return '';
    var date = new Date(data);
    date.setMilliseconds(0);
    return date.toLocaleDateString().replace('T', ' ').replace('Z', '').replace('.000', '');
}

function renderTiyinAmountColumn(data) {
    /*return '<samp>' + formatPrice(data * 1.0 / 100, 2, '.', ' ') + '</samp>';*/
    return '<samp>' + formatPrice(data * 1.0, 2, '.', ' ') + '</samp>';
}

function renderRoleColumn(data) {
    console.log(data.length);
    return data;
}

function renderSampColumn(data) {
    return '<samp>' + data + '</samp>';p
}

function renderSampColumnPan(data) {
    data = data.substr(0, data.length - 10) + new Array(data.length - 9).join('*') + data.substr(data.length - 4, 4);
    return '<samp>' + data + '</samp>';
}

function renderSampColumnPhone(data) {
    if(data.length===9){
        data = data.substr(0, data.length - 7) + new Array(data.length - 3).join('*') + data.substr(data.length - 2, 2);
    }
    else{
        data = data.substr(0, data.length - 7) + new Array(data.length - 6).join('*') + data.substr(data.length - 2, 2);
    }
    //data = data.substr(0, data.length - 7) + new Array(data.length - 6).join('*') + data.substr(data.length - 2, 2);
    return '<samp>' + data + '</samp>';
}

var formatPrice = function (number, c, d, t) {
    var c = isNaN(c = Math.abs(c)) ? 2 : c,
        d = d == undefined ? "." : d,
        t = t == undefined ? "," : t,
        s = number < 0 ? "-" : "",
        i = parseInt(number = Math.abs(+number || 0).toFixed(c)) + "",
        j = (j = i.length) > 3 ? j % 3 : 0;
    return s + (j ? i.substr(0, j) + t : "") + i.substr(j).replace(/(\d{3})(?=\d)/g, "$1" + t) + (c ? d + Math.abs(number - i).toFixed(c).slice(2) : "");
};

function setDateToday(){
    var createdAt = new Date();
    var month = (createdAt.getMonth()+1);
    if(month<10) month = '0'+month;
    var day = createdAt.getDate();
    if(day<10) day = '0'+day;
    var date_from = createdAt.getFullYear()+'-';
    date_from += month+'-';
    date_from += day;
    $('input[name=dateFrom]').val(date_from);
    $('input[name=dateTo]').val('');
}

function setCurrentMonth(){
    var createdAt = new Date();
    var month = (createdAt.getMonth()+1);
    if(month<10) month = '0'+month;
    var date_from = createdAt.getFullYear() + '-';
    date_from += month + '-';
    date_from += '01';

    var tomorrow = new Date((new Date).getTime()+24*60*60*1000);

    var next_month = tomorrow.getMonth() + 1;
    var next_year = tomorrow.getFullYear();
    if(next_month<10) next_month = '0'+next_month;
    var day = tomorrow.getDate();
    if(day<10) day = '0'+day;

    var date_to = next_year + '-' + next_month + '-' + day;
    $('input[name=dateFrom]').val(date_from);
    $('input[name=dateTo]').val(date_to);
}