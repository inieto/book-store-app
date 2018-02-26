/*
if (window.console) {
  console.log("Welcome to your Play application's JavaScript!");
}
*/

function sendDeleteRequest(event) {
    url = event.target.getAttribute("data-delete-url")
    redirect = event.target.getAttribute("data-redirect-url")
    csrfTokenName = event.target.getAttribute("data-csrf-name")
    csrfTokenValue = event.target.getAttribute("data-csrf-value")
    $.ajax({
        url: url,
        method: "DELETE",
        beforeSend: function(request) {
            //'Csrf-Token' is the expected header name, not $csrfTokenName
            request.setRequestHeader(/*csrfTokenName*/'Csrf-Token', csrfTokenValue);
        },
        success: function() {
            window.location = redirect;
        },
        error: function(xhr, ajaxOptions, thrownError) {
            alert(thrownError + "\r\n" + xhr.statusText + "\r\n" + xhr.responseText);
            window.location.reload();
        }
    })
}

function sendPutRequest(event) {
    formId = event.target.getAttribute("data-form-id")
    redirect = event.target.getAttribute("data-redirect-url")
    var form = $('#'+formId);
    $.ajax({
        url: form.attr('action'),
        method: "PUT",
        data: form.serialize(),
        beforeSend: function(request) {
            var csrfTokenValue = form[0].elements["csrfToken"].defaultValue;
            request.setRequestHeader('Csrf-Token', csrfTokenValue);
        },
        success: function() {
            window.location = redirect;
        },
        error: function(xhr, ajaxOptions, thrownError) {
            alert(thrownError + "\r\n" + xhr.statusText + "\r\n" + xhr.responseText);
            window.location.reload();
        }
    })
}

var deleteBookBtn = document.getElementById("deleteBookBtn");
var editBookBtn = document.getElementById("editBookBtn");

if(deleteBookBtn) deleteBookBtn.addEventListener("click", sendDeleteRequest);
if(editBookBtn) editBookBtn.addEventListener("click", sendPutRequest);
