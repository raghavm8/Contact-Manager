
let currentTheme = getTheme();
changeTheme();
console.log(currentTheme);
// document.addEventListener("DOMContentLoaded", () => {
//     changeTheme();
// });

function changeTheme(){
    document.querySelector('html').classList.add(currentTheme);
    const changeThemeButton = document.querySelector('#theme_change_button');
    changeThemeButton.querySelector('span').textContent = currentTheme !== 'dark' ? 'dark' : 'light';
    changeThemeButton.addEventListener('click', (event) => {
        const oldTheme = currentTheme
        currentTheme = currentTheme !== 'dark' ? 'dark' : 'light';
        setTheme(currentTheme);
        document.querySelector('html').classList.remove(oldTheme);
        document.querySelector('html').classList.add(currentTheme);
        changeThemeButton.querySelector('span').textContent = currentTheme !== 'dark' ? 'dark' : 'light';
    })
}

// set theme in local storage
function setTheme(theme){
    localStorage.setItem('theme', theme);
}

// get theme from local storage
function getTheme(){
    let theme = localStorage.getItem('theme');
    if(theme)
        return theme;
    return 'light';
}
