window.addEventListener('DOMContentLoaded', () => {
    const modifyForm = window.document.getElementById('modifyForm');
    modifyForm['addressPostFindButton'].addEventListener('click', () => {
        new daum.Postcode({
            oncomplete: (data) => {
                modifyForm['addressPost'].value = data['zonecode'];
                modifyForm['addressPrimary'].value = data['address'];
                modifyForm['addressSecondary'].value = '';
                modifyForm['addressSecondary'].focus();
            }
        }).open();
    });

    modifyForm.onsubmit = () => {
        let inputs = modifyForm.querySelectorAll('input');
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
        if (modifyForm['passwordNew'].value !== modifyForm['passwordNewCheck'].value) {
            alert('비밀번호가 서로 일치하지 않습니다.');
            modifyForm['passwordCheck'].focus();
            return false;
        }
        if (modifyForm['addressPost'].value === '') {
            alert('우편번호 찾기 버튼을 클릭하여 우편번호를 선택해주세요.');
            modifyForm['addressPostFindButton'].focus();
            return false;
        }
    };
});