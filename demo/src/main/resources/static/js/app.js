// app.js
const API_URL = '/api';

function getToken() {
    return localStorage.getItem('token');
}

function getAuthHeader() {
    const token = getToken();
    return token ? { 'Authorization': 'Bearer ' + token } : {};
}

function getUser() {
    const userStr = localStorage.getItem('user');
    return userStr ? JSON.parse(userStr) : null;
}

function logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    window.location.href = '/login.html';
}

function checkAuth(role) {
    const user = getUser();
    if (!user || !getToken()) {
        window.location.href = '/login.html';
        return false;
    }
    if (role && !user.roles.includes(role)) {
        if(user.roles.includes('ROLE_ADMIN')) {
            window.location.href = '/admin-dashboard.html';
        } else {
            window.location.href = '/employee-dashboard.html';
        }
        return false;
    }
    
    // Update UI
    const usernameDisplay = document.getElementById('username-display');
    if(usernameDisplay) {
        usernameDisplay.textContent = user.name;
    }
    return true;
}

// Show Alert
function showAlert(message, type = 'success') {
    alert(message); // Could be replaced with a nice toast notification
}
