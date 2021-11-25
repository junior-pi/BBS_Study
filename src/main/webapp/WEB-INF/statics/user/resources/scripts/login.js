window.addEventListener('DOMContentLoaded', () => {
    const loginForm = window.document.getElementById('login-form');
    loginForm.onsubmit = () => {
        let inputs = loginForm.querySelectorAll('input');
        for (let i = 0; i < inputs.length; i++) {
            let input = inputs[i];
            if (input.dataset.regex !== undefined) {
                let regex = new RegExp(input.dataset.regex);
                if (!regex.test(input.value)) {
                    alert(`올바른 ${input.getAttribute('placeholder')} 값을 입력해주세요.`);
                    input.focus();
                    return false;
                }
            }
        }
    };
});

window.addEventListener('load', () => {
    const loginForm = window.document.getElementById('login-form');
    const params = (new URL(window.location.href)).searchParams;
    if (params.get('email') !== null) {
        loginForm['email'].value = params.get('email');
        loginForm['password'].focus();
    }
});