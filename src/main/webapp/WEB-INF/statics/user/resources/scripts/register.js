window.addEventListener('DOMContentLoaded', () => {
    const registerForm = window.document.getElementById('registerForm');
    registerForm['addressPostFindButton'].addEventListener('click', () => {
        new daum.Postcode({
            oncomplete: (data) => {
                registerForm['addressPost'].value = data['zonecode'];
                registerForm['addressPrimary'].value = data['address'];
                registerForm['addressSecondary'].value = '';
                registerForm['addressSecondary'].focus();
            }
        }).open();
    });

    registerForm.onsubmit = () => {
        let inputs = registerForm.querySelectorAll('input');
        for (let i = 0; i < inputs.length; i++) {
            let input = inputs[i];
            if (input.dataset.regex !== undefined) {
                let regex = new RegExp(input.dataset.regex);
                let name = input.getAttribute('placeholder');
                if (!regex.test(input.value)) {
                    alert(`올바른 ${name}값을 입력해주세요.`);
                    input.focus();
                    return false;
                }
            }
        }
        if (registerForm['password'].value !== registerForm['passwordCheck'].value) {
            alert('비밀번호가 서로 일치하지 않습니다.');
            registerForm['passwordCheck'].focus();
            return false;
        }
        if (registerForm['addressPost'].value === '') {
            alert('우편번호 찾기 버튼을 클릭하여 우편번호를 선택해주세요.');
            registerForm['addressPostFindButton'].focus();
            return false;
        }
        if (!registerForm['agreeTerm'].checked) {
            alert('이용 약관을 읽고 동의해주세요.');
            return false;
        }
        if (!registerForm['agreePrivacy'].checked) {
            alert('개인정보 처리방침을 읽고 동의해주세요.');
            return false;
        }
    };
});