const loginUrl = window.location.origin + "/api/v1/auth";
const logoutUrl = window.location.origin + "/api/v1/auth/logout";
const formLogin = document.forms.loginForm;

window.onload = () => {
    if (window.location.pathname === '/view/login')
        formLogin.addEventListener('submit', (evt) => {
            evt.preventDefault();
            auth()
                .then(() => {
                    formLogin.submit();
                });
        });
    const btnLogout = document.querySelector('#btn-logout');
    if (btnLogout) {
        btnLogout.addEventListener('submit', logout);
    }
}

function auth() {
    const userAuth = {
        username: formLogin.username.value,
        password: formLogin.password.value
    };
    return fetch(loginUrl, {
        method: 'POST',
        body: JSON.stringify(userAuth),
        headers: {
            'Content-type': 'application/json'
        }
    })
        .then(resp => resp.json())
        .catch(console.log)
        .then(token => {
            localStorage.setItem("accessToken", token['access_token']);
            localStorage.setItem("refreshToken", token['refresh_token']);
        })
        .catch(console.log);
}

function logout() {
    fetch(logoutUrl, {
        method: 'POST',
        headers: {
            'Content-type': 'application/json',
            "Authorization": 'Bearer ' + localStorage.getItem("accessToken")
        }
    }).then(console.log)
}
