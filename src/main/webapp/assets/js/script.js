// 작성자 : 권태윤
// (19||20)\\d{2}-(0[1-9]||1[012])-(0[1-9]||[12][0-9]||3[01])
document.getElementById('join').addEventListener('click', function() {
function join(){
    const id = document.querySelector('input[name="id"]')
    const pw = document.querySelector('input[name="pw"]')
    const name = document.querySelector('input[name="name"]')
    const day = document.querySelector('input[name="day"]')
    const gender = document.querySelectorAll('input[name="gender"]:checked')
    const number = document.querySelector('input[name="number"]')

    let rePw = /[a-zA-Z0-9]{1,6}/
    let regex = /[a-zA-Z가-힣]/
    let reday = /^(19|20)\d{2}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$/
    let isValid = true

    if(id.value === ''){
        alert('아이디를 입력해주세요')
        id.focus()
        isValid = false
    }else if(pw.value ===''){
        alert('비밀번호를 입력해주세요')
        pw.focus()
        isValid = false
    }else if(!rePw.test(pw.value)){
        alert('6자리 영문+숫자 비밀번호를 입력해주세요. ^^')
        pw.focus()  
        isValid = false     
    }else if(name.value === ''){
        alert('이름을 입력해주세요!')
        name.focus()
        isValid = false
    }else if(!regex.test(name.value)){
        alert('한글 또는 영문 이름을 입력해주세요!')
        name.focus()
        isValid = false
    }else if(day.value === ''){
        alert('생년월일을 입력해주세요!')
        day.focus()
        isValid = false       
    }else if(!reday.test(day.value)){
        alert('올바른 생년월일 입력해주세요!')
        day.focus()
        isValid = false    
    }else if(gender.length === 0){
        alert('성별을 선택해주세요.')
        document.getElementById('man').focus()
        isValid = false
    }else if(number.value === ''){
        alert('전화번호를 입력해주세요.')
        number.focus()
        isValid = false
        alert('회원가입이 완료되었습니다.')
    }
}
join()
document.forms[0].submit()
})