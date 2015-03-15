var full = location.protocol + '//' + location.hostname + (location.port ? ':' + location.port: '');

function run() {

jQuery.ajax({
        type: "POST",
        async: true,
        url: full + "/tmis-ws-medipad/rest/tms-registry/apql",
        data:  $( "#query" ).val(),
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function (msg) { $("#result").text(JSON.stringify(msg)) },
        error: function (msg) { $("#result").text(msg.responseText) }
    });
}
$("button").click(run)