const username = document.querySelector("#username")
const password = document.querySelector("#password")

function login() {
    const xhr = new XMLHttpRequest()
    xhr.open('GET', 'CMD<>login<>'+username.value+'<>'+password.value)
    xhr.send()
    xhr.onreadystatechange = () => {
        if (xhr.readyState!=4) return
        if (xhr.responseText==1) {
            location.reload()
        } else {
            alert("Неверный логин или пароль")
        }
    }
}