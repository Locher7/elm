//获取当前时间（xxxx-xx-xx）
export function getCurDate() {
	const now = new Date();
	const year = now.getFullYear();
	const month = now.getMonth() + 1;
	const day = now.getDate();
	const formattedMonth = month < 10 ? "0" + month : month;
	const formattedDay = day < 10 ? "0" + day : day;
	return `${year}-${formattedMonth}-${formattedDay}`;
}

//向前端sessionStorage中存储一个JSON对象
export function setSessionStorage(keyStr, value) {
	sessionStorage.setItem(keyStr, JSON.stringify(value));
}

//从sessionStorage中获取一个JSON对象（取不到时返回null）
export function getSessionStorage(keyStr) {
	const str = sessionStorage.getItem(keyStr);
	return str && str !== 'null' ? JSON.parse(str) : null;
}

//从sessionStorage中移除一个JSON对象
export function removeSessionStorage(keyStr) {
	sessionStorage.removeItem(keyStr);
}

//向永久存储localStorage中存储一个JSON对象
export function setLocalStorage(keyStr, value) {
	localStorage.setItem(keyStr, JSON.stringify(value));
}

//从localStorage中获取一个JSON对象（取不到时返回null）
export function getLocalStorage(keyStr) {
	const str = localStorage.getItem(keyStr);
	return str && str !== 'null' ? JSON.parse(str) : null;
}

//从localStorage中移除一个JSON对象
export function removeLocalStorage(keyStr) {
	localStorage.removeItem(keyStr);
}
