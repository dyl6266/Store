/**
 * 자료형에 상관없이 값이 비어있는지 확인한다.
 * 
 * @param value - 타겟 밸류
 * @returns true or false
 */
function isEmpty(value) {
	if (value == null || value == "" || value == undefined || value.length == 0 || (value != null && typeof value == "object" && !Object.keys(value).length)) {
		return true;
	}

	return false;
}

/**
 * 문자열의 마지막 문자의 종성을 반환한다.
 * 
 * @param str - 타겟 문자열
 * @returns 문자열의 마지막 문자의 종성
 */
function charToUnicode(str) {
	return (str.charCodeAt(str.length - 1) - 0xAC00) % 28;
}

/**
 * 필드1, 필드2의 값이 다르면 해당 필드2로 focus한 다음, 메시지 출력
 * 
 * @param field1 - 타겟 필드1
 * @param field2 - 타겟 필드2
 * @param fieldName - 필드 이름
 * 
 * @returns 메시지
 */
function isEquals(field1, field2, fieldName) {
	if (field1.value !== field2.value) {
		if (isEmpty(fieldName) == false) {
			/* 종성으로 조사(을 또는 를) 구분 */
			var message = (charToUnicode(fieldName) > 0) ? fieldName + "이 서로 일치하지 않습니다." : fieldName + "가 서로 일치하지 않습니다.";
			field2.focus();
			alert(message);
		}

		return false;
	}

	return true;
}

/**
 * field의 값이 올바른 형식인지 체크 (정규표현식 사용)
 * 
 * @param field - 타겟 필드
 * @param fieldName - 필드 이름 (null 허용)
 * @param focusField - 포커스할 필드 (null 허용)
 * 
 * @returns 메시지
 */
function isValid(field, fieldName, focusField) {

	if (isEmpty(field.value) == true) {
		/* 종성으로 조사(을 또는 를) 구분 */
		var message = (charToUnicode(fieldName) > 0) ? fieldName + "을 확인해 주세요." : fieldName + "를 확인해 주세요."; 
		field.focus();
		alert(message);
		return false;
	}

	return true;
}

/**
 * 둘 중 비어있지 않은 value를 반환한다.
 * 
 * @param value1 - 타겟 밸류1
 * @param value2 - 타겟 밸류2
 * @returns 비어있지 않은 value
 */
function nvl(value1, value2) {
	return (isEmpty(value1) == false ? value1 : value2);
}

/**
 * 입력받은 숫자에 콤마를 포함해 반환한다.
 * 
 * @param obj
 * @returns 콤마가 붙은 숫자
 */
function makeCommas(target) {
	var regexp = /\B(?=(\d{3})+(?!\d))/g;
	target.value = target.value.replace(regexp, ',');
}

/**
 * Form에 존재하는 input, textarea를 포함한 모든 입력 필드의 name, value를 오브젝트에 key, value 형태로 담아서 반환
 * Ajax를 사용할 때, 파라미터를 하나씩 추가하지 않고, form을 인자로 넘겨서 사용
 * 
 * @param form - Form 객체
 * @returns
 */
function makeAjaxRequestData(form) {

	/* 필드의 name, value를 key, value 형태로 담는 오브젝트 */
	var obj = new Object();
	/* Form 데이터를 배열 형태로 serialize */
	var datas = $(form).serializeArray();
	/* 요소 개수만큼 params에 key, value 형태로 저장 */
	$(datas).each(function(idx, elem) {
		if (isEmpty(elem.value) == false) {
			obj[elem.name] = elem.value;
		}
	});

	/* spring security csrf 토큰 제거 => header에 정보를 담아서 전송하기 때문에 문제가 되지 않음 */
	obj._csrf = null;
	return obj;
}

/**
 * 파일의 확장자가 이미지 타입인지 확인
 * 
 * @param filename - 파일명
 * @returns true OR false
 */
function isImageExtension(filename) {

	if (isEmpty(filename)) {
		return false;
	}

	var extension = filename.substring(filename.lastIndexOf(".") + 1, filename.length).toUpperCase();
	if (extension != "JPEG" && extension != "JPG" && extension != "GIF" && extension != "PNG") {
		return false;
	}

	return true;
}

/**
 * PushState를 지원하는 브라우저인지 확인
 * 
 * @returns boolean
 */
function isPushStateAvailable() {
	return (typeof(history.pushState) == "function");
}

/**
 * 스프링 시큐리티 CSRF 토큰 반환
 * 
 * @returns CSRF 토큰
 */
function getToken() {
	return document.getElementById("_csrf").getAttribute("content");
}

/**
 * Ajax 호출에 필요한 헤더 반환
 * 
 * @param method - HTTP 요청 메소드
 * @returns Headers
 */
function getHeaders(method) {

	var headers = new Object();
	headers["Content-Type"] = "application/json";
	headers["X-HTTP-Method-Override"] = method;
	headers["X-CSRF-TOKEN"] = getToken();

	return headers;
}

/**
 * 페이징 결과 HTML 반환
 * 
 * @param uri - 요청 URI
 * @param queryString - 파라미터 쿼리 스트링
 * @param element - html() 함수를 적용할 엘러먼트
 * @returns 페이징 결과 HTML
 */
function movePage(uri, queryString, element) {

	/* 검색 유형, 검색 키워드, Ajax 요청 여부를 포함한 요청 URI */
	var requestUri = uri + queryString + "&isAjax=true";

	$.get(requestUri, function(response) {
		/* HTML 또는 요소가 비어있는 경우 쿼리 스트링이 존재하지 않는 기본 URI로 이동 */
		if (isEmpty(response) == true || element == (null || undefined)) {
			location.href = uri;
		} else {
			/* 결과 HTML Overwrite */
			$(element).html(response);

			/* 비동기 처리에서 도메인(주소) 변경 */
			if (isPushStateAvailable() == true) {
				/* uri를 requestUri로 지정하면 isAjax=true가 파라미터로 포함되기 때문에 새로고침 시 list-ajax를 반환하는 문제가 발생 */
				history.pushState(null, null, uri + queryString); /*[- state, title, uri -]*/
			}
		}
	}, "html");
}