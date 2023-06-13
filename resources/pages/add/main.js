const title = document.querySelector("#title")
const content = document.querySelector("#content")

function send() {
    const xhr = new XMLHttpRequest()
    xhr.open("GET", "CMD<>addArticle<>"+title.value+"<>"+content.value)
    xhr.send()
    xhr.onreadystatechange = () => {
        if (xhr.readyState!=4) return
        if (xhr.responseText==1) {
            alert("Успешно")
            document.location.replace("/")
        } else {
            alert("Ошибка!")
        }
    }
}