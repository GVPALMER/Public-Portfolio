//audio
if (annyang){
    const commands = {
        'hello': () => {alert('Hello world!');},
        'change the color to *color': (color) => {changeColor(color);},
        'navigate to home': (page) => {navigateTo('home.html');},
        'navigate to stocks': (page) => {navigateTo('stocks.html');},
        'navigate to dogs': (page) => {navigateTo('dogs.html');},
        'lookup *ticker': (ticker) => {lookupStock(ticker.toUpperCase(), '30 Days');},
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
function lookupStock(ticker) {
    const timeframe = '30 Days';
    lookupStockData(ticker, timeframe);
}

const stockLookupForm = document.getElementById('stockLookup');
const stockChartCanvas = document.getElementById('stockChart');
const stockTable = document.getElementById('stockTable').getElementsByTagName('tbody')[0];
const stockLookupButton = document.getElementById('stockLookupButton');

const API_KEY = 'a99TAY7KQjoKefq5J9_87vSRisE8Afkb'; 
const REDDIT_API_URL = 'https://tradestie.com/api/v1/apps/reddit?date=2022-04-03';

let stockChart = null; 

function convertEpochToDate(epoch) {
    return new Date(epoch * 1000).toLocaleDateString(); 
}

function destroyChart() {
    if (stockChart) {
        stockChart.destroy(); 
    }
}

async function lookupStockData(ticker, timeframe) {
    const days = timeframe.split(' ')[0];

    const endDate = new Date().toISOString().split('T')[0];
    const startDate = new Date();

    startDate.setDate(startDate.getDate() - days); 
    const startDateStr = startDate.toISOString().split('T')[0];

    const url = `https://api.polygon.io/v2/aggs/ticker/${ticker}/range/1/day/${startDateStr}/${endDate}?apiKey=${API_KEY}`;

    const response = await fetch(url);
    const data = await response.json();
    const labels = data.results.map(result => convertEpochToDate(result.t));
    const prices = data.results.map(result => result.c);
    
    destroyChart();
    
    stockChart = new Chart(stockChartCanvas, {
        type: 'line',
        data: {
            labels: labels,
            datasets: [{
                label: `${ticker} Stock Price`,
                data: prices,
                borderColor: 'green',
                borderWidth: 5,
                fill: false
            }]
        }
    });
}

async function fetchRedditStocks() {
    const response = await fetch(REDDIT_API_URL);
    const data = await response.json();
    const topStocks = data.slice(0, 5);

    stockTable.innerHTML = ''; // Clear existing rows

    topStocks.forEach(stock => {
        const row = stockTable.insertRow();
        const tickerColumn = row.insertCell(0);
        const commentColumn = row.insertCell(1);
        const sentimentColumn = row.insertCell(2);

        tickerColumn.innerHTML = `<a href="https://finance.yahoo.com/quote/${stock.ticker}" target="_blank">${stock.ticker}</a>`;
        commentColumn.textContent = stock.no_of_comments;
            
        const sentimentIcon = document.createElement('img');
        sentimentIcon.classList.add('sentiment-icon');
        
        if (stock.sentiment === 'Bullish') {
            sentimentIcon.src = 'https://fayeblake.nl/wp-content/uploads/2016/04/smiling-bull.jpg';
            sentimentColumn.classList.add('bullish');
        } else {
            sentimentIcon.src = 'https://t4.ftcdn.net/jpg/09/23/23/37/360_F_923233789_oLGIKfcNEznEXZdN0ENwO2NWze3SBOAE.jpg';
            sentimentColumn.classList.add('bearish');
        }
        sentimentColumn.appendChild(sentimentIcon);
    });
}

stockLookupForm.onsubmit = (event) => {
    event.preventDefault();
    const ticker = document.getElementById('ticker').value.toUpperCase();
    const timeframe = document.getElementById('timeframe').value;
    lookupStockData(ticker, timeframe);
};

fetchRedditStocks();