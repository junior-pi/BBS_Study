window.addEventListener('DOMContentLoaded', () => {
    const forgotEmailForm = window.document.getElementById('forgotEmailForm');
    forgotEmailForm['sendCodeButton'].addEventListener('click', () => {
        let inputs = forgotEmailForm.querySelectorAll('input');
        for (let i = 0; i < inputs.length; i++) {
            let input = inputs[i];
            if (input.dataset.regex !== undefined) {
                let regex = new RegExp(input.dataset.regex);
                let name = input.getAttribute('placeholder');
                if (!regex.test(input.value)) {
                    alert(`올바른 ${name}값을 입력해주세요.`);
                    input.focus();
                    return;
                }
            }
        }
        const callback = (resp) => {
            const respJson = JSON.parse(resp);
            switch (respJson['result']) {
                case 'sent':
                    forgotEmailForm.querySelectorAll('input').forEach(input => {
                        if (input.getAttribute('name') !== 'authCode' &&
                            input.getAttribute('name') !== 'submit') {
                            input.setAttribute('disabled', 'disabled');
                        }
                    });
                    forgotEmailForm['contactFirst'].setAttribute('disabled', 'disabled');
                    forgotEmailForm['submit'].removeAttribute('hidden');
                    forgotEmailForm.querySelector('[rel="authCode"]').removeAttribute('hidden');
                    forgotEmailForm['key'].value = respJson['key'];
                    forgotEmailForm['authCode'].focus();
                    alert('인증번호를 전송하였습니다. 5분 이내에 입력해주세요.');
                    break;
                default:
                    alert('입력하신 이름 및 연락처와 일치하는 회원을 찾을 수 없습니다.');
            }
        };
        const fallback = (status) => {
            alert('알 수 없는 이유로 인증코드를 전송하지 못하였습니다.');
        };
        const formData = new FormData(forgotEmailForm);
        Ajax.request('POST', 'forgot-email/send-code', callback, fallback, formData);
    });
    forgotEmailForm.onsubmit = () => {
        const callback = (resp) => {
            const respJson = JSON.parse(resp);
            const email = respJson['email'];
            if (email === '') {
                alert('인증번호가 올바르지 않습니다.');
                forgotEmailForm['authCode'].focus();
            } else {
                alert(`이메일은 ${email}입니다. 확인을 클릭하면 로그인으로 이동합니다.`);
                window.location.href = `login?email=${email}`;
            }
        };
        const fallback = (status) => {
            alert('알 수 없는 이유로 서버와 통신하지 못했습니다.');
        };
        const formData = new FormData();
        formData.append('authCode', forgotEmailForm['authCode'].value);
        formData.append('key', forgotEmailForm['key'].value);
        Ajax.request('POST', window.location.href, callback, fallback, formData);
        return false;
    };
});