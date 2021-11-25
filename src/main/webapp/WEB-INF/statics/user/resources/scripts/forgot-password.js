window.addEventListener('DOMContentLoaded', () => {
    const forgotPasswordForm = window.document.getElementById('forgotPasswordForm');
    forgotPasswordForm['sendCodeButton'].addEventListener('click', () => {
        let inputs = forgotPasswordForm.querySelectorAll('input');
        for (let i = 0; i < inputs.length; i++) {
            let input = inputs[i];
            if (input.dataset.regex !== undefined) {
                let regex = new RegExp(input.dataset.regex);
                let name = input.getAttribute('placeholder');
                if (input.getAttribute('name') !== 'password' &&
                    !regex.test(input.value)) {
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
                    forgotPasswordForm.querySelectorAll('input').forEach(input => {
                        if (input.getAttribute('name') !== 'authCode' &&
                            input.getAttribute('name') !== 'password' &&
                            input.getAttribute('name') !== 'passwordCheck' &&
                            input.getAttribute('name') !== 'submit') {
                            input.setAttribute('disabled', 'disabled');
                        }
                    });
                    forgotPasswordForm['contactFirst'].setAttribute('disabled', 'disabled');
                    forgotPasswordForm['submit'].removeAttribute('hidden');
                    forgotPasswordForm.querySelector('[rel="authCode"]').removeAttribute('hidden');
                    forgotPasswordForm.querySelector('[rel="password"]').removeAttribute('hidden');
                    forgotPasswordForm.querySelector('[rel="passwordCheck"]').removeAttribute('hidden');
                    forgotPasswordForm['key'].value = respJson['key'];
                    forgotPasswordForm['authCode'].focus();
                    alert('인증번호를 전송하였습니다. 5분 이내에 입력해주세요.');
                    break;
                default:
                    alert('입력하신 이메일 및 연락처와 일치하는 회원을 찾을 수 없습니다.');
            }
        };
        const fallback = (status) => {
            alert('알 수 없는 이유로 인증코드를 전송하지 못하였습니다.');
        };
        const formData = new FormData(forgotPasswordForm);
        Ajax.request('POST', 'forgot-password/send-code', callback, fallback, formData);
    });
    forgotPasswordForm.onsubmit = () => {
        const callback = (resp) => {
            const respJson = JSON.parse(resp);
            const result = respJson['result'];
            switch (result) {
                case 'success':
                    alert(`비밀번호가 성공적으로 변경되었습니다. 확인을 클릭하면 로그인 페이지로 이동합니다.`);
                    window.location.href = `login?email=${forgotPasswordForm['email'].value}`;
                    break;
                default:
                    alert('인증번호가 올바르지 않습니다.');
                    forgotPasswordForm['authCode'].focus();
            }
        };
        const fallback = (status) => {
            alert('알 수 없는 이유로 서버와 통신하지 못했습니다.');
        };
        const formData = new FormData();
        formData.append('authCode', forgotPasswordForm['authCode'].value);
        formData.append('key', forgotPasswordForm['key'].value);
        formData.append('password', forgotPasswordForm['password'].value);
        Ajax.request('POST', window.location.href, callback, fallback, formData);
        return false;
    };
});