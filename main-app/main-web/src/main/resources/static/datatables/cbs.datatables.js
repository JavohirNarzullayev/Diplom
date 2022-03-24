function renderLocalDateTime(data) {
    let month;
    let day;
    let year;
    try {
        let d = new Date(data * 1000);
        month = '' + (d.getMonth() + 1);
        day = '' + d.getDate();
        year = d.getFullYear();

        if (month.length < 2)
            month = '0' + month;
        if (day.length < 2)
            day = '0' + day;

        return [year, month, day].join('-');
    } catch (e) {
        return ""
    }
}

function renderRegistered(data, type, row, meta) {
    console.log(data)
    if (data) {
        return `<span class="badge btn-sm badge-success text-white">${data.fio}</span>`;
    } else {
        return '';
    }
}


function renderSampColumn(data) {
    return '<samp>' + data + '</samp>';
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