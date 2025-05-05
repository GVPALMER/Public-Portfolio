//audio
if (annyang){
    const commands = {
        'hello': () => {alert('Hello world!');},
        'change the color to *color': (color) => {changeColor(color);},
        'navigate to home': (page) => {navigateTo('home.html');},
        'navigate to stocks': (page) => {navigateTo('stocks.html');},
        'navigate to dogs': (page) => {navigateTo('dogs.html');},
    };
    annyang.addCommands(commands);
}
function startListen(){
    annyang.start();
}
function stopListen(){
    annyang.abort();
}
function changeColor(color){
    document.body.style.backgroundColor = color;
}
function navigateTo(page){
    window.location.href = page;
}

//quotes
function loadQuote(url){
    fetch("https://zenquotes.io/api/quotes/")
        .then(response => response.json())
        .then(data => {
            const quoteText = data[0].q;
            const authorText = data[0].a;
            document.getElementById('quote').textContent = `"${quoteText}"`;
            document.getElementById('author').textContent = `- ${authorText}`;

})}
window.onload=loadQuote;