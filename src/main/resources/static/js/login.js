const loginUrl = window.location.origin + "/api/v1/auth";
const formLogin = document.forms.loginForm;
let token;

formLogin.addEventListener('submit', (evt) => {
    auth()
});

function auth() {
    const userAuth = {
        username: formLogin.username.value,
        password: formLogin.password.value
    };
    fetch(loginUrl, {
        method: 'POST',
        body: JSON.stringify(userAuth),
        headers: {
            'Content-type': 'application/json'
        }
    })
        .then(resp => resp.json())
        .catch(console.log)
        .then(token => {
            console.log(token);
            localStorage.setItem("accessToken", token['access_token']);
            localStorage.setItem("refreshToken", token['refresh_token']);
        })
        .catch(console.log);
}
